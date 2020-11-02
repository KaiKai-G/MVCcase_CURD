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
import javax.servlet.http.HttpSession;
import javax.xml.ws.Service;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.因为是post请求所以需要设置编码格式
        request.setCharacterEncoding("utf-8");
        //2.获取login.jsp（用户）传来的验证码数据
        String verifycode = request.getParameter("verifycode");
        //3.从checkCoke取出servlet生成的验证码数据
        HttpSession session = request.getSession();
        String checkcode_session = (String) session.getAttribute("checkcode_session");//因为知道是String类型可以强制转型
        session.removeAttribute("checkcode_session");//在接收到session后立刻删除，避免缓存
        //4.判断用户传来的和servlet生成的是否相同
        if (!checkcode_session.equalsIgnoreCase(verifycode)){//不相同，忽略大小写比较
            request.setAttribute("login_msg","验证码验证失败！");
            //因为存在数据共享，所以用转发
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
        //5.在验证码正确的情况下,接收所有传来的参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //6.创建user对象
        User user = new User();
        //7.使用工具类封装
        try {
            //会将接收到的参数map集合通过set方法封装进User类成员中(获取的参数名和User需要set的成员名一致才行)
            //此次只接收到了 username和password 所以就封装这两个
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //8.调用service层进行查询
        UserService service = new UserServiceImpl();
        //9.将用户登录数据传到login进行数据库查询，返回一个user，判断是否为空
        User LoginUser = service.login(user);
        if (LoginUser == null){//没找到
           request.setAttribute("login_msg","账号或者密码错误！");
           request.getRequestDispatcher("/login.jsp").forward(request,response);

        }else {
            //找到了,将登录用户传入到session中
            session.setAttribute("user_login",LoginUser);
            response.sendRedirect(request.getContextPath()+"/index.jsp");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
