package com.main;

import com.Social.Facebook;
import com.Social.LinkedIn;
import com.Social.MyTwitter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Post")
public class Post extends HttpServlet {

    public String toSend = "Sending a message";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Facebook.FBstate == Facebook.State.Connected) {
            Facebook.postMessage(toSend);
        } if (MyTwitter.TWstate == Facebook.State.Connected) {
            MyTwitter.postMessage(toSend);
        } if (LinkedIn.LIstate == Facebook.State.Connected) {
            LinkedIn.postMessage(toSend);
        }
        String url = "http://localhost:8080/AreaGF_war_exploded/lol";
        response.sendRedirect(url);
    }
}
