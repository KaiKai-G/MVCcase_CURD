package cn.itcase.service.Impl;

import cn.itcase.dao.Impl.UserDaoImpl;
import cn.itcase.dao.UserDao;
import cn.itcase.domain.PageBean;
import cn.itcase.domain.User;
import cn.itcase.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * 实现UserService层的接口方法
 */
public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    /**
     * 调用dao层
     * @return
     */
    @Override
    public List<User> findAll() {
        //完成查询所有用户
        return dao.findAll();
    }

    /**
     * 查询登录界面的用户
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    /**
     * 添加用户
     * @param user
     */
    @Override
    public void addUser(User user) {
        dao.add(user);
    }

    /**
     * 删除用户
     * @param id
     */
    @Override
    public void userDelete(int id) {
        dao.delete(id);
    }

    /**
     *通过id查找进行修改页面的回显
     * @param id
     * @return
     */
    @Override
    public User findUserById(int id) {
        return dao.findById(id);
    }

    /**
     * 修改
     * @param user
     */
    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    /**
     * 删除复选
     * @param uids
     */
    @Override
    public void checkDel(String[] uids) {
        if (uids!=null && uids.length > 0) {
            //1.遍历所有的uid
            for (String uid : uids) {
                //2.调用dao删除每一个
                dao.delete(Integer.parseInt(uid));
            }
        }
    }

    /**
     * 通过接收到的参数当前页面和需要分页的条数进行计算并存储到PageBean中
     *
     * @param condition
     * @param _currentPage
     * @param _rows
     * @return
     */
    @Override
    public PageBean<User> findUserByPage(Map<String, String[]> condition, String _currentPage, String _rows) {
        //1.进行转型
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        //2.得到总条数/分页条数
        int totalCount = dao.findTotalCount(condition);
        //3.得到总的页码数
        int totalPage = totalCount % rows == 0 ? (totalCount/rows) : (totalCount/rows)+1;
        //4.得到索引值 索引值=当前页码（currentPage）-1 然后乘每页的条数rows
        int start = (currentPage-1)*rows;
        //5.得到当前的list集合 通过索引值和需要索引的条数
        List <User> list = dao.findByPage(condition,start,rows);
        //6.封装到PageBean中
        PageBean<User> pageBean = new PageBean<User>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setList(list);
        pageBean.setTotalCount(totalCount);
        pageBean.setRows(rows);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

}
