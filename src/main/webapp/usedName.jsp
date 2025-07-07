
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Username Already Taken</title>
    <link rel="stylesheet" type="text/css" href="loginStyle.css" />
</head>
<body>
<div class="login-box">
<h1>Username already in use</h1>
<%-- <%=request.getParameter("username")%>   amas davwert rorame(ro shegvedzleba gatestva)--%>
<p>The username you chose is already taken. Please choose another one.</p>
<jsp:include page = "signupForm.jspf" />
<a href="login.jsp">Back to log in</a>
</div>
</body>
</html>