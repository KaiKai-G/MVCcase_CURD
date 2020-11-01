package cn.itcase.service;

import cn.itcase.domain.PageBean;
import cn.itcase.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户管理调用接口
 */

public interface UserService {
    /**
     * 调用这个方法找到所有用户对象
     * @return
     */
    public List<User> findAll();//全部用户
    public User login(User user);//登录
    public void addUser(User user);//添加用户
    public void userDelete(int id);//删除id
    public User findUserById(int id);//找到id回显修改页面
    public void updateUser(User user);//修改
    public void checkDel(String[] uids);//删除复选的
    PageBean<User> findUserByPage(Map<String, String[]> condition, String currentPage, String rows);//分页查询
}
