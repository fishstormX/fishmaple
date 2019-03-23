package fishmaple.api;

import fishmaple.DAO.CanMapper;
import fishmaple.DTO.Can;
import fishmaple.Service.CanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/can")
public class CanController {
    @Autowired
    private CanService bmService;
    @Autowired
    private CanMapper canMapper;

    @GetMapping("qr")
    public void getQr(@RequestParam  String bid, HttpServletRequest request, HttpServletResponse response) throws Exception {
        bmService.setMsgQr(bid,response);
    }
    @GetMapping("")
    public Can getById(@RequestParam String id){
        return canMapper.getById(id);
    }
}
