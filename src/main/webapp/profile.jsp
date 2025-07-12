<%@ page import="database.social.FriendsDAO" %>
<%@ page import="database.social.FriendRequestDAO" %>
<%@ page import="javax.servlet.ServletContext" %>
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
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="profile-container">
    <img src="assets/profile.webp" alt="Profile Picture" class="profile-image" />
    <div class="profile-username"><%= profileUser %></div>

    <div class="friend-action">
        <% if (isFriend) { %>
        <form method="post" action="RemoveFriendServlet">
            <input type="hidden" name="receiverUsername" value="<%= profileUser %>" />
            <button type="submit">Remove Friend</button>
        </form>
        <% } else if (requestAlreadySent) { %>
        <form method="post" action="FriendRequestResponse">
            <input type="hidden" name="receiver" value="<%= profileUser %>" />
            <input type="hidden" name="sender" value="<%= currentUser %>" />
            <input type="hidden" name="status" value="cancel" />
            <button type="submit">Cancel Request</button>
        </form>
        <% } else { %>
        <form method="post" action="AddServlet">
            <input type="hidden" name="receiverUsername" value="<%= profileUser %>" />
            <button type="submit">Add Friend</button>
        </form>
        <% } %>
    </div>

    <% if (isFriend) { %>
    <div class="quiz-history-link">
        <a href="QuizHistoryServlet?username=<%= profileUser %>">View Quiz History</a>
    </div>
    <% } %>

    <div class="friends-list">
        <h4><%= profileUser %>'s Friends:</h4>
        <% if (profileFriends == null || profileFriends.isEmpty()) { %>
        <p>No friends yet.</p>
        <% } else { %>
        <ul>
            <% for (String friend : profileFriends) { %>
            <li><a href="ProfileServlet?username=<%= friend %>"><%= friend %></a></li>
            <% } %>
        </ul>
        <% } %>
    </div>

    <!-- Back to Homepage -->
    <a href="Homepage" class="back-link">
        <img src="assets/backtohomepage.webp" alt="Back to Home" />
        Back to Homepage
    </a>
</div>
</body>
</html>
