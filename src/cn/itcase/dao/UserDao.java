package cn.itcase.dao;

import cn.itcase.domain.User;

import java.util.List;
import java.util.Map;

/**
 * dao层接口
 */
public interface UserDao {
    public List<User> findAll();//查询全部用户
    public User findUserByUsernameAndPassword(String username,String password);//登录查询
    public void add(User user);//添加用户
    public void delete(int id);//通过id删除
    public User findById(int id);//回显修改页面，通过id
    public void update(User user);//修改
    int findTotalCount(Map<String, String[]> condition);//查询总的条数
    List<User> findByPage(Map<String, String[]> condition, int start, int rows);//根据索引值和需要的条数进行分页查询
}
