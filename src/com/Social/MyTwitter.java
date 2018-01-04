package com.Social;

import com.main.MainServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

@WebServlet(name = "MyTwitter")
public class MyTwitter extends HttpServlet {

    public static Facebook.State TWstate = Facebook.State.Off;
    public static String post = "there is no post here";
    public static String name = "john doe";
    public static int inbox = 0;

    private String pubkey = "27I79raS7UZzo0iScyhPqKboi";
    private String secret = "7Za10bUgQtTP5RisdzpdSMcTkcHpHyqeMA4LynBPvgD27vUEWN";
    private String token = "empty";
    static private Twitter twitter = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (TWstate == Facebook.State.Off) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("27I79raS7UZzo0iScyhPqKboi")
                    .setOAuthConsumerSecret("7Za10bUgQtTP5RisdzpdSMcTkcHpHyqeMA4LynBPvgD27vUEWN")
                    .setOAuthAccessToken("798920435572310016-ZNJwJXzVDZug1B7BKk4lJ37PLACI1Y6")
                    .setOAuthAccessTokenSecret("IEqEYPag7BzeDyyZbzYIwZOEFSpByiXEVs6mM9JkccelV");
            TwitterFactory tf = new TwitterFactory(cb.build());
            twitter = tf.getInstance();
            setPost();
            TWstate = Facebook.State.Connected;
            try {
                AccountSettings sett = twitter.getAccountSettings();
                User user = twitter.showUser(sett.getScreenName());
                name = user.getName();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            String url = "http://localhost:8080/AreaGF_war_exploded/lol";
            response.sendRedirect(url);
        } else {
            String url = "http://localhost:8080/AreaGF_war_exploded/lol";
            response.sendRedirect(url);
        }
    }

    public static void setPost() {
        List<DirectMessage> messages;
        Paging paging = new Paging(1);

        try {
            messages = twitter.getDirectMessages(paging);
            post = "From: @" + messages.get(0).getSenderScreenName() + " id:" + messages.get(0).getText();
        } catch (TwitterException e) {
            post = e.toString();
        }

    }

    public static void postMessage(String data) {
        try {
            twitter.updateStatus(data);
        } catch (TwitterException e) {
            //post = e.toString();
        }
    }
}
