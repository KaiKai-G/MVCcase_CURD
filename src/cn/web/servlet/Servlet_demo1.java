package cn.web.servlet;

import cn.web.Utils.Database_method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Servlet_demo1")
public class Servlet_demo1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置参数编码
        request.setCharacterEncoding("utf-8");

        //获得参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("账号："+username+"--"+"密码："+password);
        int ss=0;
        try
        {
            ss = Integer.parseInt(username);
        }catch (Exception e){
            System.out.println("您输入的账号不是数字,数据库存储失败");
            System.out.println("-----------分隔符--------------");
            return;
        }

        Database_method method = new Database_method();
        int i = method.SetUser(ss,"22","男","44",password);
        int j = 0;
        if (i==1){//得到返回值i为1的话就存到数据库
            j = method.InsertUser();
            System.out.println("存入封装类成功...");
        }else {
            System.out.println("存在此账号,存入封装类失败...");
        }

        if (j==1){
            System.out.println("存入数据库成功...");
            System.out.println("-----------分隔符--------------");
        }else {
            System.out.println("存入数据库失败...");
            System.out.println("-----------分隔符--------------");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
