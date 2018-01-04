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
import java.io.OutputStream;
import javax.json.*;
import java.io.StringReader;

@WebServlet(name = "Facebook")
public class Facebook extends HttpServlet {

    public enum State {
        Off,
        Connecting,
        Connected
    }

    public static State FBstate = State.Off;
    public static String post = "there is no post here";
    public static String name = "john doe";
    public static int inbox = 0;

    private String pubkey = "135489233799313";
    private String secret = "fe6b6022f6a166794abe1f1002f8a973";
    private static String token = "empty";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (FBstate == State.Off) {

            getCode(response);
        } else if (FBstate == State.Connecting) {
            getAccessToken(request.getParameter("code"));
            String url = "http://localhost:8080/AreaGF_war_exploded/lol";
            response.sendRedirect(url);
        } else {
            String url = "http://localhost:8080/AreaGF_war_exploded/lol";
            response.sendRedirect(url);
        }
    }

    private void getCode(HttpServletResponse response) throws IOException {
        FBstate = State.Connecting;
        String url = "https://facebook.com/v2.11/dialog/oauth?client_id=" + pubkey + "&redirect_uri=http://localhost:8080/AreaGF_war_exploded/connect/facebook&scope=email,user_posts,publish_actions";
        response.sendRedirect(url);
    }

    private boolean getAccessToken(String code) {

        if (code == null || code.length() == 0) {
            return false;
        }
        token = "lol";

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
            MainServlet.FBtoken = e.toString();
            return false;
        }

        token = token.substring(20);
        token = token.substring(0, token.indexOf('\"'));
        MainServlet.FBtoken = token;
        FBstate = State.Connected;
        setName();
        setLastPost();
        getMailbox();
       return true;
    }

    private String getData(String dataType) {
        String response = "";

        try {
            URLConnection connection = new URL("https://graph.facebook.com/v2.11/me/" + dataType + "access_token=" + token).openConnection();
            InputStream resp = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(resp, java.nio.charset.StandardCharsets.UTF_8.name()));
            for (String line; (line = reader.readLine()) != null;) {
                response = response + line + "  \n";
            }
        } catch (Exception e) {
            post = e.toString();
        }
        return response;
    }

    public void setName() {
        String data = getData("?");
        if (data == null)
            return ;

        data = data.substring(9);
        data = data.substring(0, data.indexOf('\"'));
        name = data;
    }

    public void setLastPost() {
        String data = getData("feed?limit=1&");
        JsonReader reader = Json.createReader(new StringReader(data));
        JsonObject obj = reader.readObject();
        reader.close();
        JsonArray arr = obj.getJsonArray("data");
        obj = arr.getJsonObject(0);
        post = obj.getString("story");
        if (post == null)
            post = obj.getString("message");
        if (post == null)
            post = "failed to retrieve post";
    }

    public void getMailbox() {

    }

    public static void postMessage(String data) {
        String response = "";
        String param = "message=" + data + "&access_token=" + token;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://graph.facebook.com/v2.11/me/feed").openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(param.getBytes());
            os.flush();
            os.close();
            InputStream resp = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(resp, java.nio.charset.StandardCharsets.UTF_8.name()));
            for (String line; (line = reader.readLine()) != null;) {
                response = response + line;
            }
        } catch (Exception e) {
            post = e.toString();
        }
    }

}