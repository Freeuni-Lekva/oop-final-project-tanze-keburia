
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Username Already Taken</title>
</head>
<body>
<h1>Username already in use</h1>
<%-- <%=request.getParameter("username")%>   amas davwert rorame(ro shegvedzleba gatestva)--%>
<p>The username you chose is already taken. Please choose another one.</p>
<jsp:include page = "signupForm.jspf" />
</body>
</html>