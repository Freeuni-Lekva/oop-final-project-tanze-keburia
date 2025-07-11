<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 10:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<String> myFriends = (List<String>) request.getAttribute("myFriends");
    List<String> requests = (List<String>) request.getAttribute("requests");
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
    <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="profile-container">
    <div class="profile-header">
        <h2>Welcome, <%= username %>!</h2>
    </div>

    <div class="profile-section">
        <h3>My Friends</h3>
        <% if (myFriends.isEmpty()) { %>
        <p class="no-friends">You have no friends.</p>
        <% } else { %>
        <ul class="friends-list">
            <% for (String friend : myFriends) { %>
            <li><a href="ProfileServlet?username=<%= friend %>"><%= friend %></a></li>
            <% } %>
        </ul>
        <% } %>
    </div>

    <div class="profile-section">
        <h3>Friend Requests</h3>
        <% if (requests.isEmpty()) { %>
        <p class="no-requests">No pending requests.</p>
        <% } else { %>
        <ul class="requests-list">
            <% for (String sender : requests) { %>
            <li>
                <span><%= sender %></span>
                <div class="request-actions">
                    <form action="FriendRequestResponse" method="post" style="display:inline;">
                        <input type="hidden" name="sender" value="<%= sender %>" />
                        <input type="hidden" name="receiver" value="<%= username %>" />
                        <button type="submit" name="status" value="accept" class="accept-btn">Accept</button>
                        <button type="submit" name="status" value="reject" class="reject-btn">Reject</button>
                    </form>
                </div>
            </li>
            <% } %>
        </ul>
        <% } %>
    </div>

    <div class="profile-links">
        <p><a href="QuizHistoryServlet">View My Quiz History</a></p>
        <p><a href="Homepage">Back to Home</a></p>
    </div>
</div>
</body>
</html>