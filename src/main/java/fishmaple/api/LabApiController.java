package fishmaple.api;

import fishmaple.utils.QrCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
/**
 * @author 鱼鱼
 * lab
 * */
@RequestMapping("/api/lab")
@RestController
public class LabApiController {
    private static Logger log = LoggerFactory.getLogger(LabApiController.class);

    @RequestMapping("/qr/{id}")
    public void getQr(@RequestParam String content, HttpServletResponse response) throws Exception {
        content=content.replaceAll("@and@","&");
        response.setContentType("image/png");
        QrCodeUtil.generateQRCode(content,200,200,"png",response);
    }
    @RequestMapping("/qr/d/{id}")
    public void getQr(@RequestParam String describe,@RequestParam String content, HttpServletResponse response) throws Exception {
        content=content.replaceAll("@and@","&");
        describe=describe.replaceAll("@and@","&");
        response.setContentType("image/png");
        QrCodeUtil.generateQRCode(content,null,describe,200,210,"png",response,"blue");
    }
}
