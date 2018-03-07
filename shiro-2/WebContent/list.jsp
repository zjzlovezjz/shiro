<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
    <h4>List Page</h4>
    
    Welcome:<shiro:principal></shiro:principal>
    
    <!-- 如果有admin的权限的话，显示这个链接 -->
    <shiro:hasRole name="admin">
    <br><br>
    <a href="admin.jsp">Admin Page</a>
    </shiro:hasRole>
    
    <!-- 如果是user的权限的话，显示这个链接 -->
    <shiro:hasRole name="user">
    <br><br>
    <a href="user.jsp">User Page</a>
    </shiro:hasRole>
    
    <br><br>
    <a href="shiro/testShiroAnnotation">Test ShiroAnnotation</a>
    
    <br><br>
    <a href="shiro/logout">logout</a>
</body>
</html>