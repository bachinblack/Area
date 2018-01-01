package com.Social;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import com.main.MainServlet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.io.InputStream;

@WebServlet(name = "FacebookLog")
public class FacebookLog extends HttpServlet {

    public enum State {
        Off,
        Connecting,
        Connected
    }

    private String pubkey = "135489233799313";
    private String secret = "fe6b6022f6a166794abe1f1002f8a973";
    public static State FBstate = State.Off;
    private String token = "empty";
    private javax.servlet.http.HttpSession session = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (FBstate == State.Off) {

            getCode(request, response);
        } else if (FBstate == State.Connecting) {
            getAccessToken(request.getParameter("code"));
            String url = "http://localhost:8080/AreaGF_war_exploded/lol";
            response.sendRedirect(url);
        } else {
            String url = "http://localhost:8080/AreaGF_war_exploded/lol";
            response.sendRedirect(url);
        }
    }

    private void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FBstate = State.Connecting;
        String paramAuteur = request.getParameter( "auteur" );
        session = request.getSession(true);
        String url = "https://facebook.com/v2.11/dialog/oauth?client_id=" + pubkey + "&redirect_uri=http://localhost:8080/AreaGF_war_exploded/connect/facebook&scope=email,user_posts";
        response.sendRedirect(url);
    }

    private boolean getAccessToken(String code) {

        if (code == null || code.length() == 0) {
            return false;
        }
        token = "lol";
        MainServlet.FBtoken = "got a code1";

        try {
            String param = "client_id=" + pubkey + "&client_secret=" + secret + "&redirect_uri=http://localhost:8080/AreaGF_war_exploded/connect/facebook" + "&code=" + code;
            URLConnection connection = new URL("https://graph.facebook.com/v2.11/oauth/access_token?" + param).openConnection();
            InputStream response = connection.getInputStream();

            MainServlet.FBtoken = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(response, java.nio.charset.StandardCharsets.UTF_8.name()));
            for (String line; (line = reader.readLine()) != null;) {
                token = token + line;
            }
        } catch (Exception e) {
            MainServlet.FBtoken = "pute " + e.toString();
            return false;
        }

        token = token.substring(20);
        token = token.substring(0, token.indexOf('\"'));
        MainServlet.FBtoken = token;
        FBstate = State.Connected;
        //getData("feed");
       return true;
    }

    public String getData(String dataType) {
        String response = "";

        try {
            URLConnection connection = new URL("https://graph.facebook.com/v2.11/me/" + dataType + "?access_token=" + token).openConnection();
            InputStream resp = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(resp, java.nio.charset.StandardCharsets.UTF_8.name()));
            for (String line; (line = reader.readLine()) != null;) {
                response = response + line + '\n';
            }
            MainServlet.FBtoken = response;
        } catch (Exception e) {
            MainServlet.FBtoken = e.toString();
        }
        return response;
    }

    public void postData() {

    }
}