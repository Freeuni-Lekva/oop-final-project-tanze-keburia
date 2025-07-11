<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/27/2025
  Time: 1:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
  response.setHeader("Pragma", "no-cache");
  response.setDateHeader("Expires", 0);

String username = (String) session.getAttribute("username");
  if (username == null) {
    response.sendRedirect("login.jsp");
    return;
  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Compose Mail</title>
  <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
  <div class="header-row">
    <h2>Compose Message</h2>
    <a href="MyProfileServlet" class="link link-blue">My Profile</a>
  </div>

  <form method="post" action="SendMail" class="mt-20">
    <div class="mb-15">
      <label for="receiver"><strong>To:</strong></label><br>
      <input type="text" name="receiver" id="receiver" class="input-full" placeholder="Enter username" required />
    </div>

    <div class="mb-15">
      <label for="subject"><strong>Subject:</strong></label><br>
      <input type="text" name="subject" id="subject" class="input-full" placeholder="Subject" required />
    </div>

    <div class="mb-20">
      <label for="content"><strong>Message:</strong></label><br>
      <textarea name="content" id="content" class="input-full" rows="8" placeholder="Write your message..." required
                style="resize: none; overflow-y: auto;"></textarea>
    </div>

    <button type="submit" class="btn btn-purple">Send</button>
  </form>

  <div class="bottom-bar mt-30">
    <a href="Homepage" class="link link-blue">Back to Home</a>
  </div>
</div>
</body>
</html>
