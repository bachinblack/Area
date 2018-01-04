package com.Social;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Youtube")
public class Youtube extends HttpServlet {

    public static Facebook.State YTstate = Facebook.State.Off;
    public static String post = "there is no post here";
    public static String name = "john doe";
    public static int inbox = 0;

    private String pubkey = "";
    private String secret = "";
    private static String token = "empty";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (YTstate == Facebook.State.Off) {
        } else {

        }
    }
}
