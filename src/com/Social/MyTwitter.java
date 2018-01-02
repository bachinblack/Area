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
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@WebServlet(name = "MyTwitter")
public class MyTwitter extends HttpServlet {

    public static Facebook.State TWstate = Facebook.State.Off;
    public static String post = "there is no post here";
    public static String name = "john doe";
    public static int inbox = 0;

    private String pubkey = "27I79raS7UZzo0iScyhPqKboi";
    private String secret = "7Za10bUgQtTP5RisdzpdSMcTkcHpHyqeMA4LynBPvgD27vUEWN";
    private String token = "empty";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (TWstate == Facebook.State.Off) {
            try {
                Twitter twitter = TwitterFactory.getSingleton();
                twitter.updateStatus("lolmdr");
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            /*
            String resp = "";
            String param = "";

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL("https://api.twitter.com/oauth/request_token").openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                os.write(param.getBytes());
                os.flush();
                os.close();
                InputStream resps = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(resps, java.nio.charset.StandardCharsets.UTF_8.name()));
                for (String line; (line = reader.readLine()) != null;) {
                    resp = resp + line;
                }
                post = resp;
            } catch (Exception e) {
                post = e.toString();
            }
            */
            TWstate = Facebook.State.Connected;
            String url = "http://localhost:8080/AreaGF_war_exploded/lol";
            response.sendRedirect(url);
            //getCode(request, response);
        } else if (TWstate == Facebook.State.Connecting) {
            //getAccessToken(request.getParameter("code"));
            //String url = "http://localhost:8080/AreaGF_war_exploded/lol";
            //response.sendRedirect(url);
        } else {
            String url = "http://localhost:8080/AreaGF_war_exploded/lol";
            response.sendRedirect(url);
        }
    }

    private void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TWstate = Facebook.State.Connecting;
        String paramAuteur = request.getParameter( "auteur" );
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
            URLConnection connection = new URL("https://graph.twitter.com/v2.11/oauth/access_token?" + param).openConnection();
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
        TWstate = Facebook.State.Connected;
        //getData("feed");
        return true;
    }

    public String getData(String dataType) {
        String response = "";

        try {
            URLConnection connection = new URL("https://graph.twitter.com/v2.11/me/" + dataType + "?access_token=" + token).openConnection();
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

    public static void postMessage(String data) {

    }
}
