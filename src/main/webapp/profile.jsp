<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 11:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%
    String currentUser = (String) session.getAttribute("username");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String profileUser = request.getParameter("username");
    if (profileUser == null || profileUser.isEmpty()) {
        response.sendRedirect("Homepage");
        return;
    }
    List<String> profileFriends = (List<String>) request.getAttribute("profileFriends");
    boolean isFriend = (Boolean) request.getAttribute("isFriend");
    boolean requestAlreadySent = (Boolean) request.getAttribute("requestAlreadySent");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= profileUser %>'s Profile</title>
    <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="profile-view">
    <div class="profile-header">
        <h2><%= profileUser %>'s Profile</h2>
        <div class="profile-actions">
            <% if (isFriend) { %>
            <form method="post" action="RemoveFriendServlet">
                <input type="hidden" name="receiverUsername" value="<%= profileUser %>" />
                <input type="submit" value="Remove Friend" class="btn btn-red"/>
            </form>
            <% } else if (requestAlreadySent) { %>
            <form method="post" action="FriendRequestResponse">
                <input type="hidden" name="receiver" value="<%= profileUser %>" />
                <input type="hidden" name="sender" value="<%= currentUser %>" />
                <input type="hidden" name="status" value="cancel" />
                <input type="submit" value="Cancel Request" class="btn btn-red"/>
            </form>
            <% } else { %>
            <form method="post" action="AddServlet">
                <input type="hidden" name="receiverUsername" value="<%= profileUser %>" />
                <input type="submit" value="Add Friend" class="btn btn-blue"/>
            </form>
            <% } %>
        </div>
    </div>

    <div class="friends-section">
        <h3><%= profileUser %>'s Friends</h3>
        <% if (profileFriends == null || profileFriends.isEmpty()) { %>
        <p class="no-friends">No friends yet.</p>
        <% } else { %>
        <ul class="friends-list">
            <% for (String friend : profileFriends) { %>
            <li><a href="ProfileServlet?username=<%= friend %>"><%= friend %></a></li>
            <% } %>
        </ul>
        <% } %>
    </div>

    <div class="profile-links">
        <% if(isFriend){ %>
        <p><a href="QuizHistoryServlet?username=<%= profileUser %>">View Quiz History</a></p>
        <%} %>
        <p><a href="Homepage">Back to Home</a></p>
    </div>
</div>
</body>
</html>