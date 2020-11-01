package cn.itcase.web;

import cn.itcase.domain.User;
import cn.itcase.service.Impl.UserServiceImpl;
import cn.itcase.service.UserService;

import java.io.IOException;
import java.util.List;

@javax.servlet.annotation.WebServlet("/userListServlet")
public class UserListServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //1.实现查询
        UserService service = new UserServiceImpl();
        //2.调用方法返回List集合(里面有全部的User对象)
        List<User> users = service.findAll();
        //3.将users存储到request域中
        request.setAttribute("users",users);
        //4.转发到list.jsp中
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }
}
