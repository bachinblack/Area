package com.log;

import com.sun.deploy.net.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FacebookLog")
public class FacebookLog extends HttpServlet {

    public enum State {
        Off,
        Connecting,
        Connected
    }

    private String pubkey = "135489233799313";
    private String secret = "151b86019907214f4da79de1a2069616";
    public static State FBstate = State.Off;
    public static String token = "";
    private javax.servlet.http.HttpSession session = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            FBstate = State.Connecting;
        session = request.getSession(true);
        String url = "https://facebook.com/v2.11/dialog/oauth?client_id=" + pubkey + "&redirect_uri=http://localhost:8080/AreaGF_war_exploded/lol&scope=email,user_posts";
        response.sendRedirect(url);
    }

    public void getAccessToken(String verifier)
    {
        String token = "";
       /*
        HttpRequest request = new HttpRequest("/oauth/access_token");
        request.AddParameter("code", verifier);
        request.AddParameter("client_id", PublicKey);
        request.AddParameter("client_secret", SecretKey);
        request.AddParameter("redirect_uri", "http://localhost:50382/callback/facebook.aspx");
        var response = client.Execute(request);

        accessToken = getJson(response.Content, "access_token");
        if (accessToken == null || accessToken.Length == 0)
            return (false);
        StoreValue(databasePos.FACEBOOK_ACCESS_TOKEN, accessToken);
        getUserId();


        */
       if (session != null) {
           session.setAttribute("facebookToken", token);
       }
       FBstate = State.Connected;
    }
}
