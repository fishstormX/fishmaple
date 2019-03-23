package fishmaple.Service;

import fishmaple.DAO.IssueMapper;
import fishmaple.DTO.Issue;
import fishmaple.DTO.IssueReply;
import fishmaple.shiro.ShiroService;
import fishmaple.utils.EncoderUtil;
import fishmaple.utils.TimeDate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IssueService {
    @Autowired
    private IssueMapper issueMapper;
    @Autowired
    private ShiroService shiroService;

    private String stateFilter(String stateFilter){
        String[] values=stateFilter.split(",");
        String state="";
        for(String value:values){
            if(value.equals("open")){
                state = state+ "0,";
            }else if(value.equals("closed")){
                state = state+ "1,";
            }else if(value.equals("warn")){
                state = state+  "2,";
            }
        }
        return state.equals("")?"":"WHERE state IN ("+state+"6)";
    }

    private String  orderFilter(String order,String desc){
        if(!order.equals("timeline")&&!order.equals("author")&&!order.equals("tag")&&!order.equals("state")
                &&!order.equals("replyCount")
        ){
            return "ORDER BY num";
        }else{
            if(desc.equals("descending")) {
                return "ORDER BY " + order + " DESC";
            } else{
                return "ORDER BY "+order;
            }
        }
    }

    public Integer getCount(String stateFilter,String search){
        return issueMapper.getCount(stateFilter.equals("")?"":stateFilter(stateFilter),search);
    }


    public Map<String,String> saveReply(IssueReply reply){
        Map<String,String> map=new HashMap<>();
        if(reply.getContent().equals("")){
            map.put("message","请继续编辑完善内容");
            return map;
        }
        String id=EncoderUtil.getUUID(0);
        issueMapper.addReply(id,TimeDate.getTimeNowToDb().toString(),reply.getIssueId(),reply.getAuthor(),reply.getContent(),reply.getReplyId());
        map.put("message","success");
        return map;
    }

    public List<IssueReply> getReplyList(String id){
        List<IssueReply> list = issueMapper.getReplyList(id);
        for(IssueReply reply:list){
            if(reply.getReplyId()==null||reply.getReplyId().equals("")){
                reply.setrContent("");
            }else{
                IssueReply issueReply = issueMapper.getReplyDetail(reply.getReplyId());
                Document doc= Jsoup.parseBodyFragment(issueReply.getContent());
                Elements es = doc.getElementsByTag("p");
                StringBuffer content=new StringBuffer("");
                for(Element e:es){
                    String[] strs=e.text().trim().split("。");
                    for(String str:strs) {
                        if ((content.length() < 10 && str.length() < 10) || content.length() < 20){
                            content.append(str).append("  ");}
                        else{
                            break;
                        }
                    }
                }
                String finalContent = "";
                if(content.length()>29){
                    finalContent = content.substring(0,26)+"...";
                }else{
                    finalContent = content.toString();
                }
                reply.setrContent("<span class='reply_rauthor'>"+issueReply.getAuthor() +
                        "</span> : <div style='margin:0 0 -15px 30px'><span>"+
                        finalContent + "</span></div>");
            }
            reply.setTimeline(TimeDate.timestamp2time(new Long(reply.getTimeline())*1000,2));
        }
        return list;
    }

    public Issue getDetail(String id){
        Issue issue = issueMapper.getById(id);
        issue.setTimeline(TimeDate.timestamp2time(new Long(issue.getTimeline())*1000,0));
        return issue;
    }

    public List<Issue> getByPage(Integer page,String stateFilter,String order,String desc,String search){
        List<Issue> list=new ArrayList<>();
        if(!stateFilter.equals("")||!order.equals("")||!search.equals("")){
            list = issueMapper.getByLimitAndState((page-1)*9,9,stateFilter(stateFilter),orderFilter(order,desc),search);
        }else{
            list = issueMapper.getByLimit((page-1)*9,9);
        }
        for(Issue issue:list){
            issue.setTimeline(TimeDate.timestamp2time(new Long(issue.getTimeline())*1000,1));
            if(issue.getState().equals("0")){
                issue.setState("open");
            }else if(issue.getState().equals("1")){
                issue.setState("closed");
            }
        }
        return list;
    }
    public Map<String,String> setNewIssue(String content,String title,String author,int isAnon,String tag){
        Long num=issueMapper.getBiggestNum()==null?0L:issueMapper.getBiggestNum();
        Map<String,String> map=new HashMap<>();
        if(title.equals("")||content.equals("")){
            map.put("message","请继续编辑完善内容");
            return map;
        }

        if(isAnon==1){
            author+="(未登录用户)";
        }else {
            author = shiroService.getUserName();
        }
        String id=EncoderUtil.getUUID(0);
        issueMapper.addIssue(id,TimeDate.getTimeNowToDb().toString(),title,author,tag,content,"0",num+1);
        map.put("message","success");
        return map;
    }

}
