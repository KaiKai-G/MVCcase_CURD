package cn.itcase.web;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCode")
public class CheckCode extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        //1.将图片存储到内存中
        int width = 100;//宽
        int height = 50;//高
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);//宽、高、类型

        //2.美化图片
        //2.1填充
        Graphics g = image.getGraphics();//获得画笔对象
        g.setColor(Color.BLACK);//画笔颜色：粉色
        g.fillRect(0,0,width,height);//填充矩形 从坐上角（0,0）点
        //2.2边框
        g.setColor(Color.BLUE);
        g.drawRect(0,0,width-1,height-1);//边框
        //字体 大小
        g.setFont(new Font("黑体",Font.BOLD,24));
        //2.3验证码字符
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        //2.4生成随机脚标
        Random rd = new Random();
        //获取一个session来存储验证码信息
        HttpSession session = request.getSession();

        //使用StringBuilder来存储验证码
        StringBuilder sb = new StringBuilder();
        g.setColor(Color.YELLOW);
        for (int i =1 ;i<=4;i++){
            int index = rd.nextInt(str.length());//生成随机的脚标
            char ch = str.charAt(index);//根据脚标生成字符
            sb.append(ch);
            g.drawString(ch+"",width/5*i,30);
        }
        //将验证码信息存储到session中
        session.setAttribute("checkcode_session",sb.toString());

        //2.4画干扰线
        g.setColor(Color.GREEN);

        //随机生成坐标点

        for (int i = 0; i < 10; i++) {
            int x1 = rd.nextInt(width);
            int x2 = rd.nextInt(width);

            int y1 = rd.nextInt(height);
            int y2 = rd.nextInt(height);
            g.drawLine(x1,y1,x2,y2);
        }
        //3.在页面显示图片
        ImageIO.write(image,"jpg",response.getOutputStream());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
