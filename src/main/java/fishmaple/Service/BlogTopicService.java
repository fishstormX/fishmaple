package fishmaple.Service;

import fishmaple.DAO.BlogTopicMapper;
import fishmaple.DTO.BlogTopic;
import fishmaple.Objects.ElementOption;
import fishmaple.shiro.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogTopicService {
    @Autowired
    BlogTopicMapper blogTopicMapper;
    @Autowired
    ShiroService shiroService;
    public List<ElementOption> getGroups(){
        String uid=shiroService.getCurrentUser().getId();
        List<BlogTopic> list = blogTopicMapper.getTopicsByUserIdAndTopicId(uid,-1);
        List<BlogTopic> list2 = blogTopicMapper.getTopicsByUserIdAndTopicId("0",-1);//公共组
        list.addAll(list2);
        List<ElementOption> options =new ArrayList<>();
        for(BlogTopic bt:list){
            ElementOption elementOption = new ElementOption();
            options.add(elementOption);
            elementOption.setValue(bt.getId().toString());
            elementOption.setLabel(bt.getTopic());
        }
        return options;
    }
    public List<ElementOption> getGroupTopics(Integer gid){

        String uid=shiroService.getCurrentUser().getId();
        List<BlogTopic> list = blogTopicMapper.getTopicsByUserIdAndTopicId(uid,gid);
        List<ElementOption> options =new ArrayList<>();
        for(BlogTopic bt:list){
            ElementOption elementOption = new ElementOption();
            options.add(elementOption);
            elementOption.setValue(bt.getId().toString());
            elementOption.setLabel(bt.getTopic());
        }
        return options;
    }

    public List<Map<String,String>> getAllTopics(){
        List<BlogTopic> list = blogTopicMapper.getAllTopics();
        List<Map<String,String>> result=new ArrayList<>();
        for(BlogTopic bt:list){
           Map<String,String> map =new HashMap<>();
           map.put("topic",bt.getTopic());
           map.put("url","/blog/topicBlog?topicId="+bt.getId());
           result.add(map);
        }

        return result;
    }


    public List<ElementOption> getTopics(){
        String uid=shiroService.getCurrentUser().getId();
        List<BlogTopic> list = blogTopicMapper.getTopicsByUserIdAndTopicId(uid,-1);
        List<BlogTopic> list2 = blogTopicMapper.getTopicsByUserIdAndTopicId("0",-1);//公共组
        list.addAll(list2);
        List<ElementOption> options =new ArrayList<>();
        ElementOption elementOption = new ElementOption();
        options.add(elementOption);
        elementOption.setValue("添加新主题　");
        elementOption.setLabel("添加新主题　");
        List<BlogTopic> list3 = blogTopicMapper.getTopicsByUserIdAndTopicId(uid,0);//无组主题
        list.addAll(list3);
        for(BlogTopic bt:list){
                list2 = blogTopicMapper.getTopicsByUserIdAndTopicId(uid,bt.getId());
                elementOption = new ElementOption();
                if(list2.size()==0&&bt.getfTopicId()==-1){
                    continue;
                }
                options.add(elementOption);
                elementOption.setValue(bt.getId().toString());
                elementOption.setLabel(bt.getTopic());

                if(list2.size()>0){
                    elementOption.setChildren(new ArrayList<ElementOption>());
                    for(BlogTopic bt2:list2){
                        ElementOption childElementOption = new ElementOption();
                        childElementOption.setLabel(bt2.getTopic());
                        childElementOption.setValue(bt2.getId().toString());
                        elementOption.getChildren().add(childElementOption);
                    }
                }
        }

        return options;
    }

    public Map<String,Object> setNewTopic(String gid, String group, String topic){
        Map<String ,Object > map =new HashMap<>();
        if(topic==null||topic.equals("")){
            map.put("message","新建主题不可为空");
            return map;
        }
        String uid=shiroService.getCurrentUser().getId();
        BlogTopic blogTopic = new BlogTopic();
        blogTopic.setTopic(topic);//自己创建的重复主题名
        blogTopic.setUserId(uid);
        BlogTopic blogTopic2 = null;
        BlogTopic blogTopic3 = null;
        if(!group.equals("")) {
            blogTopic2 = new BlogTopic();
            blogTopic2.setTopic(group);//重複组名
            blogTopic2.setfTopicId(-1);
            blogTopic2.setUserId("0");
            blogTopic3 = new BlogTopic();
            blogTopic3.setTopic(group);//重複组名
            blogTopic3.setfTopicId(-1);
            blogTopic3.setUserId(uid);
        }
        if(blogTopicMapper.verifyTopic(blogTopic)==0&&(blogTopic2==null||blogTopicMapper.verifyTopic(blogTopic2)==0)&&
                (blogTopic3==null||blogTopicMapper.verifyTopic(blogTopic3)==0)){
            if(group.equals("")&&gid.equals("")){//只存主题
                blogTopic.setfTopicId(0);
                blogTopicMapper.addTopic(blogTopic);
            }else if(gid.equals("")){       //新组和主题
                Integer id=blogTopicMapper.addTopic(blogTopic3);
                blogTopic.setfTopicId(blogTopic3.getId());
                blogTopicMapper.addTopic(blogTopic);
            }else{
                blogTopic.setfTopicId(Integer.parseInt(gid));
                blogTopicMapper.addTopic(blogTopic);
            }
            map.put("message","success");
            map.put("options",this.getTopics());
        } else{
            map.put("message","主题重复，请检查");
        }
       return map;
    }
}
