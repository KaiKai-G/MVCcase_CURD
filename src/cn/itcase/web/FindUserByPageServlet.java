package cn.itcase.web;

import cn.itcase.domain.PageBean;
import cn.itcase.domain.User;
import cn.itcase.service.Impl.UserServiceImpl;
import cn.itcase.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 分页查询
 */
@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //0.设置编码格式
        request.setCharacterEncoding("utf-8");
        //1.获取请求参数
        String currentPage = request.getParameter("currentPage");//当前页码数
        String rows = request.getParameter("rows");//每页显示的数据条数
        //1.1第一次跳转时候没有给这两个参数赋值 防止空指针直接赋值
        if (currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }
        if (rows == null || "".equals(rows)){
            rows = "8";
        }
        //接收所有的参数
        Map<String, String[]> condition = request.getParameterMap();
        //2.调用service层进行查询
        UserService service = new UserServiceImpl();
        PageBean<User> pageBean= service.findUserByPage(condition,currentPage,rows);
//        System.out.println(pageBean);
        //3.存储PageBean对象，并转发到list.jsp显示
        request.setAttribute("PageBean",pageBean);
        //存储condition 用于list.jap查询的回显
        request.setAttribute("condition",condition);
        request.getRequestDispatcher("/list.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
