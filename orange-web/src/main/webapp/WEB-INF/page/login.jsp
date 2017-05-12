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
    <form action="doLogin" method="post">
        <input name="userName" type="text"/></br>
        <input name="pwd" type="text" /> </br>
        <button type="submit">提交</button>
    </form>
</body>
</html>
