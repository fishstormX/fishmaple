package fishmaple.Service;

import fishmaple.DAO.BlogMapper;
import fishmaple.DAO.ToolMapper;
import fishmaple.DTO.Blog;
import fishmaple.DTO.Tool;
import fishmaple.Objects.SearchItem;
import fishmaple.Objects.SearchResult;
import fishmaple.thirdPart.baiduNLP.DTO.NLPConst;
import fishmaple.thirdPart.baiduNLP.DTO.NLPDepObject;
import fishmaple.thirdPart.baiduNLP.DTO.NLPItemObject;
import fishmaple.thirdPart.baiduNLP.service.BaiduNLPHelperService;
import fishmaple.utils.Html2StrFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.*;
/**
 * @author 鱼鱼
 * 搜索类：模糊搜索 全匹配搜索
 *
 */
@Service
public class SearchService {
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    ToolMapper toolMapper;
    @Autowired
    BaiduNLPHelperService baiduNLPHelperService;
    @Autowired
    Html2StrFacade html2StrFacade;

    public SearchResult doSearch(String searchContent) {
        if(searchContent.equals("")){
            return new SearchResult();
        }
        Long timeline=System.currentTimeMillis();
        SearchResult result=doHSearch(searchContent);
        LinkedHashSet<SearchItem> set =result.getItems();
        HashSet<String> keys=result.getKeys();
        int tempb=0,tempt=0;
        tempb=result.getTotalB();
        if(tempb>0)
            keys.add(searchContent);
        tempt=result.getTotalT();
        if(tempt>0)
            keys.add(searchContent);
        //语义分割式标签搜索
        List<NLPDepObject> items=baiduNLPHelperService.depar(searchContent);
        for(NLPDepObject item:items){
            if(!item.getPostag().matches(NLPConst.NO_SEMANTIC_CHARACTER)){      //去除无语义词汇
                LinkedHashSet<Blog> setTempb =  blogMapper.searchBlog(item.getWord());
                setTempb=searchFilter(setTempb,item.getWord());
                LinkedHashSet<Tool> setTempt =  toolMapper.searchTool(item.getWord());
                for(Blog blog: setTempb){
                    String content=blog.getContent();
                    blog.setContent(setKeys(content,item.getWord()));
                    String title=blog.getTitle();
                    for(NLPDepObject itemt:items){
                        if(!itemt.getPostag().matches(NLPConst.NO_SEMANTIC_CHARACTER)){
                            title=setKeys(title, itemt.getWord());
                        }
                    }
                    set.add( new SearchItem(title,blog.getContent(),
                            "/blog/d?bid="+blog.getId(),"博客",blog.getTimeline()));
                }
                if(set.size()>tempb+tempt){
                    keys.add(item.getWord());
                    tempb=set.size()-tempt;
                }
                for(Tool tool: setTempt){
                    String content=tool.getDescribe();
                    tool.setDescribe(setKeys(content,item.getWord()));

                    String name=tool.getName();
                    for(NLPDepObject itemt:items){
                        if(!itemt.getPostag().matches(NLPConst.NO_SEMANTIC_CHARACTER)){
                            name=setKeys(name, itemt.getWord());
                        }
                    }
                    set.add( new SearchItem(name,tool.getDescribe(),
                            "/tool?tid="+tool.getId(),"工具",
                            Long.parseLong(tool.getCreateDate())));
                }
                if(set.size()>tempt+tempb){
                    keys.add(item.getWord());
                    tempt=set.size()-tempb;
                }
                //System.out.println("过滤词 "+item.getWord()+item.getPostag()+" 权重0.5 "+item.getDeprel()+"  共有"+setb.size()+"条结果");
            }
        }

        result.setKeys(keys);
        result.setItems(set);
        result.setTotalT(tempt);
        result.setTotalB(tempb);
        result.setTime(System.currentTimeMillis()-timeline);
        //结果集 赋予权值
        //误差词汇矫正（权值0.1）
        //相近搜索（暂不考虑）
        return result;
    }

    public SearchResult doMSearch(String searchContent) {
        if(searchContent.equals("")){
            return new SearchResult();
        }
        Long timeline=System.currentTimeMillis();
        SearchResult result=doHSearch(searchContent);
        LinkedHashSet<SearchItem> set =result.getItems();
        HashSet<String> keys=result.getKeys();
        int tempb=0,tempt=0;

        tempb=result.getTotalB();
        if(tempb>0)
            keys.add(searchContent);
        tempt=result.getTotalT();
        if(tempt>0)
            keys.add(searchContent);
        //语义分割式2连标签搜索
        List<NLPDepObject> items=baiduNLPHelperService.depar(searchContent);
        Set<String> keywords=getKeys(items);
        if(keywords.size()==0){     //全无语义
            keys.add(searchContent);
        }
        int n=0;            //不满足的个数
        for(String key:keywords){
                LinkedHashSet<Blog> setTempb =  blogMapper.searchBlog(key);
                setTempb=searchFilter(setTempb,key);
                LinkedHashSet<Tool> setTempt =  toolMapper.searchTool(key);
                for(Blog blog: setTempb){
                    String content=blog.getContent();
                    blog.setContent(setKeys(content,key));
                    String title=blog.getTitle();
                    for(NLPDepObject item:items){
                        if(!item.getPostag().matches(NLPConst.NO_SEMANTIC_CHARACTER)){
                            title=setKeys(title, item.getWord());
                            if(blog.getTagTemp().indexOf(item.getWord())<0
                                 &&blog.getTitle().indexOf(item.getWord())<0
                                 &&blog.getContent().indexOf(item.getWord())<0)
                                 n++;
                            }
                        }
                    if(n<1){
                    set.add( new SearchItem(title,blog.getContent(),
                            "/blog/d?bid="+blog.getId(),"博客",blog.getTimeline()));
                    n=0;
                    }
                }
                if(set.size()>tempb+tempt){
                    keys.add(key);
                    tempb=set.size()-tempt;
                }
                for(Tool tool: setTempt){
                    String content=tool.getDescribe();
                    tool.setDescribe(setKeys(content,key));
                    String name=tool.getName();
                    for(NLPDepObject item:items){
                        if(!item.getPostag().matches(NLPConst.NO_SEMANTIC_CHARACTER)) {
                            name=setKeys(name, item.getWord());
                            if (tool.getDescribe().indexOf(item.getWord()) < 0
                                    && tool.getName().indexOf(item.getWord()) < 0)
                                n++;
                        }
                    }
                    if(n<1){
                    set.add( new SearchItem(name,tool.getDescribe(),
                            "/tool?tid="+tool.getId(),"工具",
                            Long.parseLong(tool.getCreateDate())));
                    n=0;
                    }
                }
                if(set.size()>tempt+tempb){
                    keys.add(key);
                    tempt=set.size()-tempb;
                }
                //System.out.println("过滤词 "+item.getWord()+item.getPostag()+" 权重0.5 "+item.getDeprel()+"  共有"+setb.size()+"条结果");

        }
        //保证符合一定比例的满足参数
        for(NLPDepObject item:items){

        }

        result.setKeys(keys);
        result.setItems(set);
        result.setTotalT(tempt);
        result.setTotalB(tempb);
        result.setTime(System.currentTimeMillis()-timeline);
        //结果集 赋予权值
        //误差词汇矫正（权值0.1）
        //相近搜索（暂不考虑）
        return result;
    }

    public SearchResult doHSearch(String searchContent) {
        if(searchContent.equals("")){
            return new SearchResult();
        }
        LinkedHashSet<SearchItem> set =new LinkedHashSet<>();
        SearchResult result=new SearchResult();
        HashSet<String> keys=new HashSet<String>();
        Long timeline=System.currentTimeMillis();
        int tempb=0,tempt=0;
        //原始搜索(100%匹配) 优先级1
        LinkedHashSet<Blog> setb =  blogMapper.searchBlog(searchContent);
        setb=searchFilter(setb,searchContent);
        LinkedHashSet<Tool> sett =  toolMapper.searchTool(searchContent);
        //添加关键词
        for(Blog blog: setb){
            String content=blog.getContent();
            blog.setContent(setKeys(content,searchContent));
            set.add( new SearchItem(setKeys(blog.getTitle(),searchContent),blog.getContent(),
                    "/blog/d?bid="+blog.getId(),"博客",blog.getTimeline()));
        }
        for(Tool tool: sett){
            String content=tool.getDescribe();
            tool.setDescribe(setKeys(content,searchContent));

            set.add( new SearchItem(setKeys(tool.getName(),searchContent),tool.getDescribe(),
                    "/tool?tid="+tool.getId(),"工具",
                    Long.parseLong(tool.getCreateDate())));
        }
        tempb=setb.size();
        tempt=sett.size();
        if(tempb+tempt>0)
        keys.add(searchContent);
        result.setKeys(keys);
        result.setItems(set);
        result.setTotalT(tempt);
        result.setTotalB(tempb);
        result.setTime(System.currentTimeMillis()-timeline);
        //结果集 赋予权值
        //误差词汇矫正（权值0.1）
        //相近搜索（暂不考虑）
        return result;
    }

    public static void main(String args[]){
       SearchService s=new SearchService();
       s.doSearch("java");
    }

    //搜索结果通配
    private LinkedHashSet<SearchItem> changeToItemSet(){
        return null;
    }
    //高亮关键词
    private String setKeys(String content,String key){
        content=html2StrFacade.html2String(content)
                .replace(" ","");
        int index=content.indexOf(key);
        if(index>=0){
            content=content.substring(index>100?index-100:0,index)+
                    "<span class='searchblock-key' >"+key+"</span>"+
                    content.substring(index+key.length(),index<content.length()-250?index+250:content.length());
        }
        else
            content =content.substring(0,content.length()<350?content.length():350);
        return content;
    }
    //滤除特殊结果(只出现在属性值中)
    // TODO
    private LinkedHashSet<Blog> searchFilter(LinkedHashSet<Blog> blogs,String key){
        Iterator<Blog> it = blogs.iterator();
        while (it.hasNext()) {
            Blog blog = it.next();
            if (blog.getTitle().indexOf(key) < 0 && blog.getTagTemp().indexOf(key) < 0) {
                if (html2StrFacade.html2String(blog.getContent()).indexOf(key) < 0) {
                    it.remove();
                    //System.out.println(blog.getTitle());
                }
            }
        }
        return blogs;
    }
    //制定不那么松散的语句合集
    /**
     *有（无）有   的语义连接
     * */
    private Set<String> getKeys( List<NLPDepObject> items){
        Set<String> keys=new HashSet<>();
        String temp="";
        String temp2="";
        boolean isFirst=true;
        for(NLPDepObject item:items){
            if(isFirst) {
                temp=temp2;
                isFirst=false;
            }
            if(item.getPostag().matches(NLPConst.NO_SEMANTIC_CHARACTER)){   //无语义连接
                temp=temp+item.getWord();
                if(items.get(items.size()-1).getWord().equals(item.getWord())){ //末尾无语义 取
                    keys.add(temp);
                    continue;
                }
            }
            else {
                if(temp2.equals(""))            //首位有语义 不取
                {
                    temp2=item.getWord();
                    isFirst=true;
                    continue;
                }
                temp = temp + item.getWord();
                keys.add(temp);
                keys.add(temp2+item.getWord());
                temp2=item.getWord();
                isFirst=true;
            }
        }
        System.out.println(keys);
        return keys;
    }
}
