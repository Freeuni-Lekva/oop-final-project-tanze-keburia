<%--<%@ page import="database.social.FriendsDAO" %>--%>
<%--<%@ page import="database.social.FriendRequestDAO" %>--%>
<%--<%@ page import="javax.servlet.ServletContext" %>--%>
<%--<%@ page import="java.util.List" %>--%>
<%--<%--%>
<%--    String username = (String) session.getAttribute("username");--%>
<%--    if (username == null) {--%>
<%--        response.sendRedirect("login.jsp");--%>
<%--        return;--%>
<%--    }--%>
<%--    List<String> myFriends = (List<String>) request.getAttribute("myFriends");--%>
<%--    List<String> requests = (List<String>) request.getAttribute("requests");--%>
<%--%>--%>

<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <title>My Profile</title>--%>
<%--    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">--%>
<%--</head>--%>
<%--<script>--%>
<%--    document.addEventListener("DOMContentLoaded", function () {--%>
<%--        document.querySelectorAll('.friend-request-form').forEach(form => {--%>
<%--            form.addEventListener('submit', function () {--%>
<%--                const badge = document.getElementById('request-count');--%>
<%--                let count = parseInt(badge.textContent);--%>
<%--                if (count > 0) {--%>
<%--                    badge.textContent = count - 1;--%>
<%--                    if (count - 1 === 0) {--%>
<%--                        badge.style.display = 'none';--%>
<%--                    }--%>
<%--                }--%>
<%--            });--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>
<%--<body>--%>

<%--<h2>Welcome, <%= username %>!</h2>--%>

<%--<div class="flex-container">--%>
<%--    <!-- My Friends Box -->--%>
<%--    <div class="box">--%>
<%--        <h3>My Friends</h3>--%>
<%--        <img src="assets/friends.webp" alt="Friends">--%>
<%--        <% if (myFriends.isEmpty()) { %>--%>
<%--        <p>You have no friends.</p>--%>
<%--        <% } else { %>--%>
<%--        <ul>--%>
<%--            <% for (String friend : myFriends) { %>--%>
<%--            <li><a href="ProfileServlet?username=<%= friend %>"><%= friend %></a></li>--%>
<%--            <% } %>--%>
<%--        </ul>--%>
<%--        <% } %>--%>
<%--    </div>--%>

<%--    <!-- Friend Requests Box -->--%>
<%--    <div class="box">--%>
<%--        <h3>Friend Requests</h3>--%>

<%--        <% if (!requests.isEmpty()) { %>--%>
<%--        <div class="badge-container">--%>
<%--            <img src="assets/friendrequests.webp" alt="Requests Icon">--%>
<%--            <span class="badge" id="request-count"><%= requests.size() %></span>--%>
<%--        </div>--%>
<%--        <% } else { %>--%>
<%--        <div class="badge-container">--%>
<%--            <img src="assets/friendrequests.webp" alt="Requests Icon">--%>
<%--            <!-- No badge if empty -->--%>
<%--        </div>--%>
<%--        <% } %>--%>

<%--        <% if (requests.isEmpty()) { %>--%>
<%--        <p>No pending requests.</p>--%>
<%--        <% } else { %>--%>
<%--        <ul>--%>
<%--            <% for (String sender : requests) { %>--%>
<%--            <li>--%>
<%--                <%= sender %>--%>
<%--                <form action="FriendRequestResponse" method="post" class="friend-request-form">--%>
<%--                    <input type="hidden" name="sender" value="<%= sender %>" />--%>
<%--                    <input type="hidden" name="receiver" value="<%= username %>" />--%>
<%--                    <button type="submit" name="status" value="accept">Accept</button>--%>
<%--                    <button type="submit" name="status" value="reject">Reject</button>--%>
<%--                </form>--%>
<%--            </li>--%>
<%--            <% } %>--%>
<%--        </ul>--%>
<%--        <% } %>--%>
<%--    </div>--%>

<%--</div>--%>

<%--<!-- Bottom Links -->--%>
<%--<div class="bottom-links">--%>
<%--    <form action="QuizHistoryServlet" method="get">--%>
<%--        <button type="submit" class="quiz-history-button">View My Quiz History</button>--%>
<%--    </form>--%>

<%--</div>--%>

<%--<!-- Back to Homepage -->--%>
<%--<a href="Homepage" class="back-link">--%>
<%--    <img src="assets/backtohomepage.webp" alt="Back to Home" />--%>
<%--    Back to Homepage--%>
<%--</a>--%>


<%--</body>--%>
<%--</html>--%>

<%@ page import="database.social.FriendsDAO" %>
<%@ page import="database.social.FriendRequestDAO" %>
<%@ page import="javax.servlet.ServletContext" %>
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
    <meta charset="UTF-8">
    <title>My Profile</title>
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        document.querySelectorAll('.friend-request-form').forEach(form => {
            form.addEventListener('submit', function () {
                const badge = document.getElementById('request-count');
                let count = parseInt(badge.textContent);
                if (count > 0) {
                    badge.textContent = count - 1;
                    if (count - 1 === 0) {
                        badge.style.display = 'none';
                    }
                }
            });
        });
    });
</script>
<body>

<div class="my-profile-wrapper">
    <h2 class="profile-heading">Welcome, <%= username %>!</h2>

    <div class="profile-boxes">
        <!-- My Friends Box -->
        <div class="box">
            <h3>My Friends</h3>
            <img src="assets/friends.webp" alt="Friends">
            <% if (myFriends.isEmpty()) { %>
            <p>You have no friends.</p>
            <% } else { %>
            <ul>
                <% for (String friend : myFriends) { %>
                <li><a href="ProfileServlet?username=<%= friend %>"><%= friend %></a></li>
                <% } %>
            </ul>
            <% } %>
        </div>

        <!-- Friend Requests Box -->
        <div class="box">
            <h3>Friend Requests</h3>
            <div class="badge-container">
                <img src="assets/friendrequests.webp" alt="Requests Icon">
                <% if (!requests.isEmpty()) { %>
                <span class="badge" id="request-count"><%= requests.size() %></span>
                <% } %>
            </div>

            <% if (requests.isEmpty()) { %>
            <p>No pending requests.</p>
            <% } else { %>
            <ul>
                <% for (String sender : requests) { %>
                <li>
                    <%= sender %>
                    <form action="FriendRequestResponse" method="post" class="friend-request-form">
                        <input type="hidden" name="sender" value="<%= sender %>" />
                        <input type="hidden" name="receiver" value="<%= username %>" />
                        <button type="submit" name="status" value="accept">Accept</button>
                        <button type="submit" name="status" value="reject">Reject</button>
                    </form>
                </li>
                <% } %>
            </ul>
            <% } %>
        </div>
    </div>

    <!-- Quiz History Button -->
    <div class="bottom-links">
        <form action="QuizHistoryServlet" method="get">
            <button type="submit" class="quiz-history-button">View My Quiz History</button>
        </form>
    </div>

    <!-- Back to Homepage -->
    <a href="Homepage" class="back-link">
        <img src="assets/backtohomepage.webp" alt="Back to Home" />
        Back to Homepage
    </a>
</div>

</body>
</html>

