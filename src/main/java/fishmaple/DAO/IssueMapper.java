package fishmaple.DAO;

import fishmaple.DTO.Issue;
import fishmaple.DTO.IssueReply;
import fishmaple.DTO.IssueTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueMapper {
    public List<IssueTest> findAllIssueTest();
    public Issue getById(@Param("id")String id);
    public List<Issue> getByLimit(@Param("start") int start,@Param("size")int size);
    public List<Issue> getByLimitAndState(@Param("start") int start,@Param("size")int size,@Param("state")String state,
                                          @Param("order")String order,@Param("search")String search);
    public Integer getCount(@Param("state")String state,@Param("search")String search);
    public void addIssue(@Param("id")String id,@Param("timeline")String timeline,@Param("title")String title,
                    @Param("author")String author,@Param("tag")String tag,@Param("content")String content,
                    @Param("state")String state,@Param("num")Long num);
    public Long getBiggestNum();
    public List<IssueReply> getReplyList(@Param("id")String id);
    public IssueReply getReplyDetail(@Param("rid")String rid);
    public void addReply(@Param("id")String id,@Param("timeline")String timeline,@Param("issueId")String issueId,
                         @Param("author")String author,@Param("content")String content, @Param("replyId")String replyId);
}
