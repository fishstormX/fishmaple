package fishmaple.Service;

import com.alibaba.fastjson.JSON;
import fishmaple.DAO.BlogMapper;
import fishmaple.DAO.TagMapper;
import fishmaple.DTO.Blog;
import fishmaple.shiro.ShiroService;
import fishmaple.utils.EncoderUtil;
import fishmaple.utils.TimeDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    BlogMapper blogMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    ShiroService shiroService;

    @Override
    public void save(String content, String title,String author,List<String> tags,
                     int useDictionary,String cover,Integer isOriginal,Integer topicId) {
        //TODO  权限
        Long timenow=System.currentTimeMillis()/1000;
        String bid=EncoderUtil.getUUID(1);
        List<String> temp=setAnchor(content);           //存储锚点和html
        if(null==cover||cover.equals("")){
            cover=getFirstImg(content);
        }
        Integer todo=0;
        if(content.indexOf("//TODO")>0){
            todo=1;
        }
        blogMapper.save(bid,temp.get(1),title,timenow,author,temp.get(0), useDictionary,cover,isOriginal,todo,topicId);
        for(String tag: tags){
            blogMapper.saveBTags(EncoderUtil.getUUID(1),bid,tag);
        }
    }

    @Override
    public boolean update(String id, String content, String title, String author,
                       List<String> tags, int useDictionary,String cover,Integer isOriginal,Integer topicId) {
        //fish编辑自己的博客
        if(!blogMapper.getById(id).getAuthor().equals(author)&&
                !shiroService.isUserAuthAble("change_world")){
            return false;
        }
        Long timenow=System.currentTimeMillis()/1000;
        List<String> temp=setAnchor(content);
        if(null==cover||cover.equals("")){
            String oldCover=blogMapper.getById(id).getCover();
            if(oldCover==null||oldCover.equals("")){
                cover=oldCover;
            }else{
                cover=getFirstImg(content);
            }
        }
        Integer todo=0;
        if(content.indexOf("//TODO")>0){
            todo=1;
        }
        blogMapper.updateOne(id,temp.get(1),title,timenow,author,temp.get(0),useDictionary,cover,isOriginal,todo,topicId);
        blogMapper.deleteBlogTags(id);
        for(String tag: tags){
            blogMapper.saveBTags(EncoderUtil.getUUID(1),id,tag);
        }
        return true;
    }

    @Override
    public List<Blog> getBlogList(int page) {
        List<Blog> blogs=blogMapper.getByPage((page-1)*7,7);
        blogHandler(blogs);
        return blogs;
    }
    @Override
    public void addLike(String bid,String ip) {
        blogMapper.addLike(bid);
        blogMapper.addLikeLog(bid,TimeDate.getTimeNowToDb(),ip);
    }


    @Override
    public List<Blog> getBlogList() {
        List<Blog> blogs=blogMapper.fastGetAll();

        return blogs;
    }
    @Override
    public List<Blog> getBlogListByTopicId(Integer topicId) {
        List<Blog> blogs=blogMapper.fastGetByTopicId(topicId);

        return blogs;
    }

    @Override
    public List<Blog> getBlogList(int count,int flag) {
        List<Blog> blogs=blogMapper.getByPage(0,count);
        blogHandler(blogs);
        return blogs;
    }

    @Override
    public boolean autoSave(String bid,String uid,String content){
        if(bid==""){
            //最多三条不关联数据
            // TODO 可能穿透超过3条
            if(blogMapper.getBakCount(uid)>3){

                Integer id = blogMapper.getLastBak(uid);
                blogMapper.deleteLastBak(id);
            }
            blogMapper.saveBak(uid,content,TimeDate.getTimeNowToDb());
            return true;
        }else if(blogMapper.getById(bid).getAuthor().equals(shiroService.getUserName())&&
                shiroService.getCurrentUser().getId()==uid
                ){
            if(blogMapper.getNBakCount(bid,uid)>0){
                blogMapper.updateBak(bid,uid,content,TimeDate.getTimeNowToDb());
            }else {
                blogMapper.saveNBak(bid, uid, content,TimeDate.getTimeNowToDb());
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Blog> getBlogList(int page, String tag) {
        List<Blog> blogs=blogMapper.getByPageAndTag((page-1)*7,7,tag);
        blogHandler(blogs);
        return blogs;
    }
    //标签样式 置顶 获取摘要
    private void blogHandler(List<Blog> blogs) {
        for(Blog blog:blogs){
            if(blog.getPriority()>0){
                blog=this.setPriTags(blog);
             }
             List<String> tags=blog.getTags();
            List<String> tagtypes=new ArrayList<String>();
            boolean isPri=false;
            for(int i=0;i<tags.size();i++){
                if(tags.get(i).equals("置顶")){
                    tagtypes.add("el-tag--danger");
                    isPri=true;
                }else if(tagMapper.getTagTypeByName(tags.get(i))==null?false:tagMapper.getTagTypeByName(tags.get(i))==1){
                    if(isPri) {
                        tags.add(1,tags.get(i));
                        tags.remove(i+1);
                        tagtypes.add(1, "el-tag--success");
                    }else{
                        tags.add(0,tags.get(i));
                        tags.remove(i+1);
                        tagtypes.add(0, "el-tag--success");
                    }
                }else{
                    tagtypes.add("");
                }
            }
            blog.setTagTypes(tagtypes);
            blog.setTags(tags);

            Document doc= Jsoup.parseBodyFragment(blog.getContent());
            Elements es = doc.getElementsByTag("p");
            StringBuffer content=new StringBuffer("");
            for(Element e:es){
                String[] strs=e.text().trim().split("。");
                for(String str:strs) {
                    if ((content.length() < 130 && str.length() < 120) || content.length() < 100){
                    content.append(str).append("  ");}
                    else{
                        break;
                    }
                }
            }
            blog.setContent(content.toString());
        }
    }

    public String getLastBlog(){
        return blogMapper.getLast();
    }
    //blog 获取摘要,添加tag的摘要
    @Override
    public String blogLine(Blog blog) {
            Document doc= Jsoup.parseBodyFragment(blog.getContent());
            Elements es = doc.getElementsByTag("p");
            StringBuffer content=new StringBuffer("");
            for(String tag:blog.getTags()){
                content.append(tag+",");
            }
            for(Element e:es){
                String[] strs=e.text().trim().split("。");
                for(String str:strs) {
                    if ((content.length() < 130 && str.length() < 120) || content.length() < 100){
                        content.append(str).append("  ");}
                    else{
                        break;
                    }
                }
            }
            return content.toString();
    }


    private String getFirstImg(String content){
        Document doc=Jsoup.parseBodyFragment(content);
        Elements elements = doc.getElementsByTag("img");

        if(elements.size()>0){
            return elements.get(0).attr("src");

        }
        else{
            return "";
        }
    }

    @Override
    public Blog getBlogById(String bid,boolean appAbled) {
        Blog blog=blogMapper.getById(bid);
        if(appAbled){
            blog.setContent(setBaseUrl(blog.getContent()));
        }
        blog.setTimelineStr(TimeDate.timestamp2time(blog.getTimeline()*1000,0));
        blog.setCreateTimeStr(TimeDate.timestamp2time(Long.parseLong(blog.getId().substring(0,10))*1000,0));
        return blog;
    }

    @Override
    public int getCount() {
        return blogMapper.getCount();
    }

    @Override
    public int getCount(String tag) {
        return blogMapper.getCountByTag(tag);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.SERIALIZABLE,timeout=36000,rollbackFor=Exception.class)
    public String deleteBlog (String bid) {
        Blog blog=blogMapper.getById(bid);


        if(blog==null){
            return "指定博客不存在或已被删除";
        }
        if(!blog.getAuthor().equals(shiroService.getUserName())&&
                !shiroService.getUserAuth().equals("0")){
            return "您没有权限删除这篇博客";
        }

            blogMapper.addRecycle(blog.getId(), blog.getContent(), blog.getTitle(),
                    System.currentTimeMillis() / 1000, blog.getAuthor(), blog.getAnchors(),
                    blog.getTags().toString());

            blogMapper.deleteOneBlog(bid);
            blogMapper.deleteBlogTags(bid);

        return "success";
    }

    @Override
    public List<String> getTagsByType(int type) {
        return tagMapper.getTagsByType(type);
    }
    //资源根目录修正
    private String setBaseUrl(String content){
        Document doc=Jsoup.parseBodyFragment(content);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {

            String src = element.attr("src");
            if(element.parent().tagName().equals("p")) {
                element.parent().html("");
                element.parent().tagName("img")
                        .attr("src","https://www.fishmaple.cn"+src)
                        .attr("style","text-align:center");
            }else{
                element.attr("src","https://www.fishmaple.cn"+src);
            }
        }
        return doc.body().html();
    }
    //自动添加段落锚点
    private List<String> setAnchor(String content){
        content =content.replaceAll("<br/>","<br>");
        Document doc=Jsoup.parseBodyFragment(content);

        Elements elements = doc.getElementsByTag("h2");
        for (Element element : elements) {
            element.attr("class","thinkInside");
        }
        elements = doc.getElementsByTag("h3");
        for (Element element : elements) {
            element.attr("class","thinkInside");
        }

        elements = doc.getElementsByClass("thinkInside");
        List<String> anchors=new ArrayList<>();
        List<String> list=new ArrayList<>();
        for (Element element : elements) {
            String linkText = element.text();
            if(element.tagName().equals("h3")){
                linkText="　• "+linkText;
            }

            element.attr("id",linkText);
            anchors.add(linkText);
        }

        //锚点列表
        list.add(JSON.toJSONString(anchors));
        list.add(doc.body().html());
        return list;
    }

    //设置优先级
    private Blog setPriTags(Blog blog){
        List<String> tags=blog.getTags();
        tags.add(0,"置顶");
        blog.setTags(tags);

        return blog;
    }
}
