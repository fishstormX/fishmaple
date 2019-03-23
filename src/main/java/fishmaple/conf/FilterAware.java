package fishmaple.conf;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName="mobileFilter",urlPatterns="/ssssssss")
public class FilterAware implements Filter {
    private String param1;

    @Override
    public void destroy() {
        System.out.println("销毁容器");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
         filterChain.doFilter(request, response);



    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.param1 = filterConfig.getInitParameter("param1");
    }
}