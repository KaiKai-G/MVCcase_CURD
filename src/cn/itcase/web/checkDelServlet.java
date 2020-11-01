package cn.itcase.web;

import cn.itcase.service.Impl.UserServiceImpl;
import cn.itcase.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/checkDelServlet")
public class checkDelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.编码
        request.setCharacterEncoding("utf-8");
        //2.获取全部的uid数组
        String[] uids = request.getParameterValues("uid");
        //3.调用service层的进行删除
        UserService service = new UserServiceImpl();
        service.checkDel(uids);
        //4.进行重定向
        response.sendRedirect(request.getContextPath()+"/findUserByPageServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
