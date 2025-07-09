<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Information Incorrect</title>
    <link rel="stylesheet" type="text/css" href="loginStyle.css" />
</head>
<body>
<div class="login-box">
    <h1>Try again...</h1>
    <p class = error>Either your username or password is incorrect</p>
    <form action="LoginServlet" method="post">
        Username: <input type="text" name="username" required /><br/>
        Password: <input type="password" name="password" required /><br/>
        <input type="submit" value="Login" />
    </form>
    <a href="signup.jsp">Create new account</a>
</div>
</body>
</html>