package fishmaple.Service;

import fishmaple.DTO.Blog;

import java.util.List;

public interface BlogService{
    public void save(String content,String title,String aid,List<String> tags,
                     int useDictionary,String cover,Integer isOriginal,Integer topicId);
    boolean update(String id,String content,String title,String aid,List<String> tags,int useDictionary,
                   String cover,Integer isOriginal,Integer topicId);
    List<Blog> getBlogList(int page);
    List<Blog> getBlogList();
    List<Blog> getBlogListByTopicId(Integer topicId);
    List<Blog> getBlogList(int count,int flag);
    List<Blog> getBlogList(int page,String tag);
    List<String> getTagsByType(int type);
    Blog getBlogById(String id,boolean appAbled);
    int getCount();
    int getCount(String tag);
    String deleteBlog(String bid);
    boolean autoSave(String bid,String uid,String content);
    public String blogLine(Blog blog);
}
