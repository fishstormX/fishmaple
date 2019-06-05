package fishmaple.api;

import fishmaple.DAO.IssueMapper;
import fishmaple.DTO.Issue;
import fishmaple.DTO.IssueReply;
import fishmaple.Service.IssueService;
import fishmaple.shiro.ShiroService;
import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * @author 鱼鱼
 * issue接口
 * */
@RequestMapping("/api/issue")
@RestController
public class IssueController {
    @Autowired
    ShiroService shiroService;
    @Autowired
    IssueService issueService;
    @Autowired
    IssueMapper issueMapper;

    @RequestMapping("{page}")
    public List<Issue> getByPage(@RequestParam(value="stateFilter",defaultValue="",required=false)String stateFilter,
                                 @RequestParam(value="order",defaultValue="",required=false)String order,
                                 @RequestParam(value="desc",defaultValue="",required=false)String desc,
                                 @RequestParam(value="search",defaultValue="",required=false)String search,
                                 @PathVariable Integer page
    ) {
        return  issueService.getByPage(page,stateFilter,order,desc,search);
    }

    @GetMapping("")
    public List<Issue> getFirstPage() {
        return  issueService.getByPage(1,"","","","");
    }

    @GetMapping("count")
    public Integer getCount(@RequestParam(value="search",defaultValue="",required=false)String search,
            @RequestParam(value="stateFilter",defaultValue="",required=false)String stateFilter) {
        return  issueService.getCount(stateFilter,search);
    }

    @GetMapping("detail")
    public Issue getDetail(@RequestParam(value="id",defaultValue="",required=false)String id) {
        return  issueService.getDetail(id);
    }

    @GetMapping("{id}/reply")
    public List<IssueReply> getReplyListById(@PathVariable String id) {
        return  issueService.getReplyList(id);
    }

    @PostMapping("reply")
    public Map<String, String> saveReply(@RequestBody IssueReply reply) {
        return  issueService.saveReply(reply);
    }

    @PostMapping("")
    public Map setNewIssue(@RequestBody Issue issue){
        return issueService.setNewIssue(issue.getContent(),issue.getTitle(),issue.getAuthor(),shiroService.isLogin()?0:1,issue.getTag());
    }
}
