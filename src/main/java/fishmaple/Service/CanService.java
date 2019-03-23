package fishmaple.Service;

import fishmaple.utils.QrCodeUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class CanService {
    public void setMsgQr(String bid, HttpServletResponse response) throws Exception {
        QrCodeUtil.generateQRCode("https://www.fishmaple.cn/m/can?bid="+bid,
               "C:\\Users\\一只‘\\Pictures\\QQ图片20181115095518.jpg"
                ,"罐头——匿名留言板",300,310, "png",response
        ,"blue");
    }
}
