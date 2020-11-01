<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
    <script>
        //删除单个
        function deleteUser(id) {
            //进行询问是否删除
            if (confirm("您确定要删除数据吗？")){
                //跳转删除了
                location.href="${pageContext.request.contextPath}/deleteServlet?id="+id;
            }
        }
        //删除所选
        window.onload = function () {
            document.getElementById("delSelected").onclick = function () {
                //判断删除所选是否是空,避免空指针异常
                var check_uids = document.getElementsByName("uid");
                var flag = false;//设置一个标记来判断是否有选中的
                for (var i = 0 ; i <= check_uids.length;i++){
                    if ( check_uids[i].checked){//如果有选中的
                        flag = true;
                        break;
                    }
                }
                if (flag){//如果有选中的
                    //提交表单
                    if (confirm("确定要删除所选项吗？")) {
                        document.getElementById("check_onclick").submit();
                    }
                }
            }
            //点击第一个复选框选和下面的复选框状态相同
            document.getElementById("first_checkbox").onclick = function () {
                //1.首先接受所有的uid
                var check_uids = document.getElementsByName("uid");
                //2.遍历所有的uid
                for (var i = 0 ; i <= check_uids.length;i++){
                    //3.将这个点击事件的选中状态给每一个uid
                    check_uids[i].checked = this.checked;
                }
            }
        }

    </script>
</head>
<body>

<div class="container">
    <h3 style="text-align: center">用户信息管理系统</h3>

<%--    内联表--%>
    <div style="float: left; margin: 5px">
        <form class="form-inline" action="${pageContext.request.contextPath}/findUserByPageServlet" method="post" >
            <div class="form-group">
                <label for="exampleInputName1">姓名</label>
                <input type="text" class="form-control" value="${condition.name[0]}" name="name" id="exampleInputName1" >
            </div>
            <div class="form-group">
                <label for="exampleInputName2">籍贯</label>
                <input type="text" class="form-control" value="${condition.address[0]}" name="address" id="exampleInputName2" >
            </div>
            <div class="form-group">
                <label for="exampleInputEmail3">邮箱</label>
                <input type="text" class="form-control" value="${condition.email[0]}" name="email" id="exampleInputEmail3" >
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>
    </div>
<%--    两个按钮--%>
    <div style="float: right;margin: 5px" >

        <a class="btn btn-primary" href="add.jsp">添加联系人</a>
        <a class="btn btn-primary" href="javascript:void(0)" id="delSelected">删除选中</a>

    </div>

<form action="${pageContext.request.contextPath}/checkDelServlet" id="check_onclick" >
<%-- 有边框的表，鼠标悬浮上面会变色--%>
    <table border="1" class="table table-bordered table-hover">
        <%-- 信息蓝色--%>
        <tr class="info">
            <th><input type="checkbox" id="first_checkbox"></th>
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>籍贯</th>
            <th>QQ</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>

        <c:forEach items="${PageBean.list}" var="user" varStatus="s">
        <tr>
            <th><input type="checkbox" name="uid" id="uid" value="${user.id}"></th>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.gender}</td>
            <td>${user.age}</td>
            <td>${user.address}</td>
            <td>${user.qq}</td>
            <td>${user.email}</td>
            <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}&currentPage=${PageBean.currentPage}">修改</a>&nbsp;
                <a class="btn btn-default btn-sm" href="javascript:deleteUser(${user.id})">删除</a></td>
        </tr>
        </c:forEach>
    </table>
</form>
<%-- 分页--%>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination pagination-lg">
                <%--如果当前页面等于1的话，不跳转。--%>
                <li>
                    <c:if test="${PageBean.currentPage-1 ==0}">
                        <a href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        </a>
                    </c:if>
                    <c:if test="${PageBean.currentPage-1 !=0 }">
                        <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${PageBean.currentPage-1}&rows=${condition.rows[0]}&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        </a>
                    </c:if>
                </li>

                <%--点击后根据当前点击的页面值进行跳转--%>
                <c:forEach begin="1" end="${PageBean.totalPage}" var="i">
                    <c:if test="${PageBean.currentPage == i}">
                        <li class="active"><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=${condition.rows[0]}&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>
                    <c:if test="${PageBean.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=${condition.rows[0]}&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>
                </c:forEach>

                <li>
                    <c:if test="${PageBean.totalPage == PageBean.currentPage}">
                        <a href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </c:if>
                    <c:if test="${PageBean.totalPage != PageBean.currentPage}">
                        <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${PageBean.currentPage+1}&rows=${condition.rows[0]}&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </c:if>
                </li>
                <span style="font-size:25px ;margin: 10px;" >
                共${PageBean.totalCount}条，共${PageBean.totalPage}页
                </span>
            </ul>
        </nav>
    </div>

</div>
</body>
</html>
