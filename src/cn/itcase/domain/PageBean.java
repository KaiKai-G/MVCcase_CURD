package cn.itcase.domain;

import java.util.List;

public class PageBean<T> {//分页工具对象
    //通过一条索引值查询到当前页面要显示的数据，索引值=当前页码（currentPage）-1 然后乘每页的条数rows
    private int totalCount;//总的条数
    private int totalPage;//总的页码数 = 总条数（totalCount）/每页显示条数（rows）然后根据模判断
    private List<T> list;//每一页的数据
    private int currentPage;//当前的页码
    private int rows;//每页显示的条数

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                ", currentPage=" + currentPage +
                ", rows=" + rows +
                '}';
    }
}
