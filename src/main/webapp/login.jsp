<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="loginStyle.css" />
</head>
<body>

<div class="login-box">
    <h2>Login</h2>
    <form action="LoginServlet" method="post">
        <label for="username">Username:</label><br/>
        <input type="text" id="username" name="username" required /><br/>

        <label for="password">Password:</label><br/>
        <input type="password" id="password" name="password" required /><br/>

        <input type="submit" value="Login" />
    </form>
    <a href="signup.jsp">Create new account</a>
</div>

</body>
</html>
