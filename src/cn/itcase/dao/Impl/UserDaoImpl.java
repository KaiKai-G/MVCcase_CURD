package cn.itcase.dao.Impl;

import cn.itcase.dao.UserDao;
import cn.itcase.domain.User;
import cn.itcase.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * 进行用户的增删改查操作
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

//    public static void main(String[] args) {
//        UserDaoImpl dao = new UserDaoImpl();
//        User user = new User();
//        user.setId(3);
//        user.setName("威威");
//        user.setGender("男");
//        user.setQq("222222222");
//        user.setAge(22);
//        user.setAddress("ddddddd");
//        dao.update(user);
//    }
    @Override
    public List<User> findAll() {
        //进行jdbc数据库操作
        //查询
        String sql = "select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public User findUserByUsernameAndPassword(String username,String password){
        //登录查询
        try {
            String sql = "select * from user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(User user) {
        //添加用户
        String sql = "insert into user values(null,?,?,?,?,?,?,null,null)";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());
    }

    @Override
    public void delete(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql,id);
    }

    @Override
    public User findById(int id) {
        try {
            String sql = "select * from user where id = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
            return user;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void update(User user) {
        String sql = "update user set name = ?, gender = ? ,age = ?,address = ?,qq = ?, email = ? where id = ?";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition)
    {
        //通过字符拼接实现分页查询
        String sql = "select count(*) from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);//用于拼接字符
        //接收所有key
        Set<String> KeySet = condition.keySet();
        //接收所有的value值,用于以后将value输入到sql语句的？中
        List<Object> params =  new ArrayList<Object>();
        for (String key : KeySet) {
            //要是接受到了currentPage和rows就跳出这个循环继续
            if (key.equals("currentPage") || key.equals("rows")){
                continue;
            }
            //因为value值是数组形式,已知每个key就对应了一个value
            String value = condition.get(key)[0];
            if (value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }
        sql=sb.toString();
//        System.out.println(sql);
//        System.out.println(params);
        System.out.println(template.queryForObject(sql, Integer.class, params.toArray()));
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<User> findByPage(Map<String, String[]> condition, int start, int rows) {
        String sql = "SELECT * FROM USER where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);//用于拼接字符
        //接收所有key
        Set<String> KeySet = condition.keySet();
        //接收所有的value值,用于以后将value输入到sql语句的？中
        List<Object> params =  new ArrayList<Object>();
        for (String key : KeySet) {
            //要是接受到了currentPage和rows就跳出这个循环继续
            if (key.equals("currentPage") || key.equals("rows")){
                continue;
            }
            //因为value值是数组形式,已知每个key就对应了一个value
            String value = condition.get(key)[0];
            if (value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }
        //拼接分页的部分
        sb.append("limit ?,? ");
        params.add(start);
        params.add(rows);

        sql = sb.toString();
//        System.out.println(sb.toString());
//        System.out.println(params);
        return template.query(sql, new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }
}
