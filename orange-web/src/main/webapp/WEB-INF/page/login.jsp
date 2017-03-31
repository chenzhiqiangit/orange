<%--
  Created by IntelliJ IDEA.
  User: chzq
  Date: 2017/3/30
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>你好</title>
</head>
<body>
    <form action="login" method="post">
        <input name="userName" type="text"/></br>
        <input name="pwd" type="text" /> </br>
        <button type="submit">提交</button>
    </form>
</body>
</html>
