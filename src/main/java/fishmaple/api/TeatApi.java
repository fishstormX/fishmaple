package fishmaple.api;

import fishmaple.DAO.BlMapper;
import fishmaple.DAO.IssueMapper;
import fishmaple.DTO.IssueTest;
import fishmaple.Objects.SearchResult;
import fishmaple.Service.SearchService;
import fishmaple.thirdPart.bilibiliWebWorm.BlWormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class TeatApi {
    @Autowired
    IssueMapper issueMapper;

    @RequestMapping("/do")
    public String doSearch(@RequestParam(value="content",required=false) String content){
        List<IssueTest> i = issueMapper.findAllIssueTest();
        System.out.println("");
        return "";
    }
}
