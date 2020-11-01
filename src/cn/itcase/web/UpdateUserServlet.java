package cn.itcase.web;

import cn.itcase.domain.User;
import cn.itcase.service.Impl.UserServiceImpl;
import cn.itcase.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/updateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //2.获取全部update.jsp传来的参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //接受currentPage当前页面数据进行跳转
        String currentPage = request.getParameter("currentPage");
        //3.封装进User类
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //4.通过调用service层进行修改
        UserService service = new UserServiceImpl();
        service.updateUser(user);
        //5.重定向
        response.sendRedirect(request.getContextPath()+"/findUserByPageServlet?currentPage="+currentPage);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
