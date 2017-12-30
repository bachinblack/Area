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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lol = "calotte de vos morts";
        Post  post = new Post();

        post.setPost("celestin", "la calotte de vos morts");
        request.setAttribute( "pute", lol);
        request.setAttribute( "TName", "@Philippe Bouttereux" + id);
        id = id + 1;
        request.setAttribute( "FName", "Philippe Bouttereux");
        request.setAttribute( "TPost", post);
        this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    //        String paramAuteur = request.getParameter( "auteur" );
        //    <c:if test="condition"></c:if>
    }
}
