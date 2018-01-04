<%@ page import="com.main.MainServlet" %>
<%@ page import="com.Social.Facebook" %>
<%@ page import="com.Social.LinkedIn" %>
<%@ page import="com.Social.MyTwitter" %>
<%@ page import="com.Social.Youtube" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>AREA</title>
        <link rel="stylesheet" type="text/css" href="style.css"/>
    </head>
    <body>
        <div class="nav"> <h2>AREA</h2></div>
        <div>
            <ul class="networks-on">
                <% if (MyTwitter.TWstate == Facebook.State.Connected) { %>
                <li class="elem"> <div class="network">
                    <a href="https://twitter.com"> <img class="logo" src="./resources/tw.png" alt="twitter logo"/> </a>
                    <a href="#" class="name"><% out.println(MyTwitter.name); %></a>
                    <a href="https://twitter.com/?lang=fr"> <img class="msg" src="./resources/msg.png" alt="msg"/> </a>
                    <div class="last-post"><% out.println(MyTwitter.post); %></div>
                </div></li>

                <% } if (Facebook.FBstate == Facebook.State.Connected) { %>
                <li class="elem"> <div class="network">
                        <a href="https://facebook.com"> <img class="logo" src="./resources/fb.png" alt="facebook logo"/> </a>
                    <a href="#" class="name"><% out.println(request.getAttribute("FName"));%></a>
                    <a href="https://www.facebook.com/messages/"> <img class="msg" src="./resources/msg.png" alt="msg"/> </a>
                    <div class="last-post"><% out.println(Facebook.post); %></div>
                </div></li>

                <% } if (LinkedIn.LIstate == Facebook.State.Connected) { %>
                <li class="elem"> <div class="network">
                    <a href="https://linkedin.com"> <img class="logo" src="./resources/li.png" alt="linkedin logo"/> </a>
                    <a href="#" class="name"><% out.println(LinkedIn.name); %></a>
                    <a href="#"> <img class="msg" src="./resources/msg.png" alt="msg"/> </a>
                    <div class="last-post"><% out.println(LinkedIn.post); %></div>
                </div></li>
                <% } if (Youtube.YTstate == Facebook.State.Connected) { %>
                <li class="elem"> <div class="network">
                    <a href="https://youtube.com"> <img class="logo" src="./resources/yt.png" alt="youtube logo"/> </a>
                    <a href="#" class="name"><% out.println(Youtube.name); %></a>
                    <a href="#"> <img class="msg" src="./resources/msg.png" alt="msg"/> </a>
                    <div class="last-post"><% out.println(Youtube.post); %></div>
                </div></li>
                <% } %>
            </ul>
            <div class="tasks">
                <% if (Facebook.FBstate != Facebook.State.Connected) { %>
                <form action="/AreaGF_war_exploded/connect/facebook"><button type="submit">Connect to Facebook</button> </form>
                <% } if (MyTwitter.TWstate != Facebook.State.Connected) { %>
                <form action="/AreaGF_war_exploded/connect/twitter"><button type="submit">Connect to Twitter</button> </form>
                <% } if (LinkedIn.LIstate != Facebook.State.Connected) { %>
                <form action="/AreaGF_war_exploded/connect/linkedin"><button type="submit">Connect to Linkedin</button> </form>
                <% } if (Youtube.YTstate != Facebook.State.Connected) { %>
                <form action="/AreaGF_war_exploded/connect/linkedin"><button type="submit">Connect to Linkedin</button> </form>
                <% } %>
                <button onclick="showPost()">Send global message</button>
                <form action="/AreaGF_war_exploded/lol"><button type="submit">Refresh</button> </form>
            </div>
        </div>
        <form action="/AreaGF_war_exploded/post" id="post" class="entry-popup">
            <textarea name="message" rows="4" cols="50" placeholder="Enter message here"></textarea>
            <input name="url" class="entry" type="text" name="string" placeholder="Add an url">
            <button class="send" type="submit" onclick="hidePost()">Send</button>
        </form>
    </body>

</html>
<script>
    function showPost() {
        document.getElementById("post").className += " open-entry";
    }

    function hidePost() {
        document.getElementById("post").className = "entry-popup";
    }
</script>