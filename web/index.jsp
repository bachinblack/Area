<%@ page import="com.main.MainServlet" %>
<%@ page import="com.Social.Facebook" %>
<%@ page import="com.Social.LinkedIn" %>
<%@ page import="com.Social.MyTwitter" %>
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
                <% } %>
            </ul>
            <div class="tasks">
                <% if (Facebook.FBstate == Facebook.State.Off) { %>
                <form action="/AreaGF_war_exploded/connect/facebook"><button type="submit">Connect to Facebook</button> </form>
                <% } if (MyTwitter.TWstate == Facebook.State.Off) { %>
                <form action="/AreaGF_war_exploded/connect/twitter"><button type="submit">Connect to Twitter</button> </form>
                <% } if (LinkedIn.LIstate == Facebook.State.Off) { %>
                <form action="/AreaGF_war_exploded/connect/linkedin"><button type="submit">Connect to Linkedin</button> </form>
                <% } %>
                <form action="/AreaGF_war_exploded/post"><button type="submit">Send global message</button> </form>
            </div>
        </div>
    </body>
</html>