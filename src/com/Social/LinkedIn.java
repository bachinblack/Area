package com.Social;

import com.main.MainServlet;
import sun.applet.Main;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@WebServlet(name = "LinkedIn")
public class LinkedIn extends HttpServlet {


    public static Facebook.State LIstate = Facebook.State.Off;
    public static String post = "there is no post here";
    public static String name = "john doe";
    public static int inbox = 0;

    private String pubkey = "86i83llvaw37tf";
    private String secret = "Qrdf9rfIPoeqEAN7";
    private static String token = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (LIstate == Facebook.State.Off) {
            getCode(response);
        } else if (LIstate == Facebook.State.Connecting) {
            getAccessToken(request.getParameter("code"));
            String url = "http://localhost:8080/AreaGF_war_exploded/lol";
            response.sendRedirect(url);
        } else {
            String url = "http://localhost:8080/AreaGF_war_exploded/lol";
            response.sendRedirect(url);
        }
    }

    private void getCode(HttpServletResponse response) throws IOException {
        String param =
                "response_type=code" +
                "&client_id=" + pubkey +
                "&redirect_uri=http://localhost:8080/AreaGF_war_exploded/connect/linkedin" +
                "&state=lolmdr" +
                "&scope=r_basicprofile,r_emailaddress,w_share";

        LIstate = Facebook.State.Connecting;
        response.sendRedirect("https://www.linkedin.com/uas/oauth2/authorization?" + param);
    }

    private boolean getAccessToken(String code) {
        if (code == null) {
            return false;
        }
        String param =  "grant_type=authorization_code" +
                        "&code=" + code +
                        "&redirect_uri=http://localhost:8080/AreaGF_war_exploded/connect/linkedin" +
                        "&client_id=" + pubkey +
                        "&client_secret=" + secret;
        try {
            URLConnection connection = new URL("https://www.linkedin.com/oauth/v2/accessToken?" + param).openConnection();
            InputStream resp = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(resp, java.nio.charset.StandardCharsets.UTF_8.name()));
            for (String line; (line = reader.readLine()) != null;) {
                token = token + line + '\n';
            }
        } catch (Exception e) {
            post = e.toString();
        }
        token = token.substring(17);
        token = token.substring(0, token.indexOf('\"'));
        LIstate = Facebook.State.Connected;
        setName();
        return true;
    }

    public String getData(String link) {
        String response = "";

        try {
            URLConnection connection = new URL(link + "oauth2_access_token=" + token).openConnection();
            InputStream resp = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(resp, java.nio.charset.StandardCharsets.UTF_8.name()));
            for (String line; (line = reader.readLine()) != null;) {
                response = response + line + '\n';
            }
        } catch (Exception e) {
            post = e.toString();
        }
        return response;
    }

    public void setName() {
        String data = getData("https://api.linkedin.com/v1/people/~?format=json&");
        if (data == null)
            return ;

        JsonReader reader = Json.createReader(new StringReader(data));
        JsonObject obj = reader.readObject();
        reader.close();

        name = obj.getString("firstName") + ' ' + obj.getString("lastName");
    }

    public void setPost() {
        String data = getData("https://api.linkedin.com/v2/connections?q=viewer&start=0&count=0&");
        if (data == null) {
            post = "lol";
            return ;
        }
        //JsonReader reader = Json.createReader(new StringReader(data));
        //JsonObject obj = reader.readObject();
        //reader.close();

    }

    public static void postMessage(String data, String extraContent) {
        String response = "";
        if (extraContent == null)
            extraContent = "http://cdn.cnn.com/cnnnext/dam/assets/160927210830-tk-ah0927-exlarge-169.jpg";
        String param =
                "{\n" +
                "  \"comment\": \"" + data + "\",\n" +
                "  \"content\": {\n" +
                "    \"title\": \"LinkedIn Developers Resources\",\n" +
                "    \"description\": \"Leverage LinkedIn's APIs to maximize engagement\",\n" +
                "    \"submitted-url\": \"https://developer.linkedin.com\",  \n" +
                "    \"submitted-image-url\": \"" + extraContent + "\"\n" +
                "  },\n" +
                "  \"visibility\": {\n" +
                "    \"code\": \"anyone\"\n" +
                "  }  \n" +
                "}";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.linkedin.com/v1/people/~/shares?oauth2_access_token=" + token).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("x-li-format", "json");
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