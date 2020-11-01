package cn.itcase.web;

import cn.itcase.domain.User;
import cn.itcase.service.Impl.UserServiceImpl;
import cn.itcase.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//找到用户信息使它回显到update.jsp
@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //2.找到传来的id参数
        String id = request.getParameter("id");
        String currentPage = request.getParameter("currentPage");//当前页面
        int i = Integer.parseInt(id);
        //3.根据id查询到用户
        UserService service = new UserServiceImpl();
        User userById = service.findUserById(i);
        //4.将user对象存入request
        request.setAttribute("user",userById);
        //5.将接受到的currentPage存储到request
        request.setAttribute("currentPage",currentPage);
        //5.返回update.jsp
        request.getRequestDispatcher("/update.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
