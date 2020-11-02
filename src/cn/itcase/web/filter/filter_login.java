package cn.itcase.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class filter_login implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1.要想要获取文件的路径并筛选需要 httpServlet
        HttpServletRequest request = (HttpServletRequest) req;
        //2.获得路径
        String uri = request.getRequestURI();
        //3.筛选 可以直接放行的路径（登录页面需要的） jsp/servlet/验证码图片/css/js/fonts
        if (uri.contains("/login.jsp") || uri.contains("/loginServlet" )|| uri.contains("/checkCode") || uri.contains("/css/" )|| uri.contains("/js/") || uri.contains("/fonts/"))
        {   //放行
            chain.doFilter(req, resp);
        }else {
            //4.如果不是登录页面就判断是否存在user (在LoginServlet将User存入到了Session中)
            HttpSession session = request.getSession();
            Object user_login = session.getAttribute("user_login");
            if (user_login!=null){
                //5.如果存在user就转发放行
                chain.doFilter(req, resp);
            }else{
                //6.要是不存在user，就转发到登录页面
                request.setAttribute("login_msg","还未登录，请登录！");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
