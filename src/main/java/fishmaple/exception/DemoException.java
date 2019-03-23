package fishmaple.exception;
/*
*@author:hyxia5
*测试用异常
*/
public class DemoException extends RuntimeException{
    public DemoException() {
        super();
    }

    public DemoException(String message) {
        super(message);
    }

    public DemoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DemoException(Throwable cause) {
        super(cause);
    }
}
