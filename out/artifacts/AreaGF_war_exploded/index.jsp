<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>AREA</title>
        <link rel="stylesheet" type="text/css" href="style.css"/>
    </head>
    <body>
        <div class="nav"> <h2>AREA
          <%
            String attribut = (String) request.getAttribute("pute");
            out.println( attribut );
          %>
        </h2></div>
        <div>
            <ul class="networks-on">
                <li class="elem"> <div class="network">
                    <a href="https://twitter.com"> <img class="logo" src="./resources/tw.png" alt="twitter logo"/> </a>
                    <a href="#" class="name"><% out.println(request.getAttribute("TName"));%></a>
                    <a href="https://twitter.com/?lang=fr"> <img class="msg" src="./resources/msg.png" alt="msg"/> </a>
                    <div class="last-post">
                      <%
                        com.Social.Post TPost = (com.Social.Post) request.getAttribute("TPost");
                        if (TPost != null) {
                          out.println(TPost.getName());
                          out.println(TPost.getMessage());
                        }
                      %>
                    </div>
                </div></li>
                <li class="elem"> <div class="network">
                        <a href="https://facebook.com"> <img class="logo" src="./resources/fb.png" alt="facebook logo"/> </a>
                    <a href="#" class="name"><% out.println(request.getAttribute("FName"));%></a>
                    <a href="https://www.facebook.com/messages/"> <img class="msg" src="./resources/msg.png" alt="msg"/> </a>
                    <div class="last-post">here's the last post for this network</div>
                </div></li>
            </ul>
            <div class="tasks">
              <form action="/AreaGF_war_exploded/tryconnect/facebook"><button type="submit">Connect to Facebook</button> </form>
                <button>Connect to Twitter</button>
                <button>Post a global message</button>
            </div>
        </div>
    </body>
</html>