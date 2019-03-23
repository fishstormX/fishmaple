package fishmaple.MainController;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class HttpErrorHandler implements ErrorController {

    private final static String ERROR_PATH = "/error";


    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorHtml() {
        return "error";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
