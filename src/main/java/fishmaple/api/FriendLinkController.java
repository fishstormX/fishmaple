package fishmaple.api;

import fishmaple.DAO.FriendLinksMapper;
import fishmaple.DTO.FriendLink;
import fishmaple.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/links")
public class FriendLinkController {
    @Autowired
    FriendLinksMapper friendLinksMapper;
    @GetMapping("")
    public List<FriendLink> getLinks(Model model) throws Exception {
        return friendLinksMapper.getAll();
    }
}
