package fishmaple.Service;

import fishmaple.Objects.UploadState;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


public interface UploadService {
    String uploadTool(byte[] file, String fileName) throws UnsupportedEncodingException;

    String upload2File(byte[] file,String fileName,String type) throws UnsupportedEncodingException;

    UploadState upload(MultipartFile file, HttpServletRequest request, String type);
}
