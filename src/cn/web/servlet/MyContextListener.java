package cn.web.servlet;

/**
 * @Description 监听侦：用于解决tomcat停止出现内存泄漏。
 * @Author ICHENY
 */

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.DaemonThreadFactory;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MyContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("webService start");
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("webService stop");
        try {
            //手动取消jdbc程序的注册
            while(DriverManager.getDrivers().hasMoreElements()) {
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            }
            System.out.println("jdbc Driver close");
            //手动停止名为[mysql-cj-abandoned-connection-cleanup]的线程
            AbandonedConnectionCleanupThread.uncheckedShutdown();

            System.out.println("clean thread success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
