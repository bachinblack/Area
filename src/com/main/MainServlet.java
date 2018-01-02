package com.main;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.Social.Post;

@WebServlet(name = "MainServlet")
public class MainServlet extends HttpServlet {

    public static int id = 1;
    public static String FBtoken = null;
    public static String TWtoken = null;
    public static String LItoken = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Post  post = new Post();

        if (LItoken == null)
            post.setPost("celestin", "la calotte de vos morts");
        else
            post.setPost("celestin", LItoken);
        request.setAttribute( "TName", "@Philippe Bouttereux" + id);
        id = id + 1;
        request.setAttribute( "FName", "Philippe Bouttereux");
        request.setAttribute( "TPost", post);
        this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        //    <c:if test="condition"></c:if>
    }
}
