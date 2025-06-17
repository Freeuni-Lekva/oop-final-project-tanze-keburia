<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Information Incorrect</title>
</head>
<body>
<h1>Please try again</h1>
<p>Either your username or password is incorrect. Please try again.</p>
<form action="LoginServlet" method="post">
    Username: <input type="text" name="username" required /><br/>
    Password: <input type="password" name="password" required /><br/>
    <input type="submit" value="Login" />
</form>
<p><a href="signup.jsp">Create new account</a></p>
</body>
</html>