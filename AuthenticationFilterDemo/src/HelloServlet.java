import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        System.out.println("------------------------------------------------------");
        System.out.println(" Init method is called in " + this.getClass().getName());
        System.out.println("------------------------------------------------------");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve the session (do not create a new one if it doesn't exist)
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Retrieve the visit count from the session
            Integer visitCount = (Integer) session.getAttribute("visitCount");

            if (visitCount == null) {
                visitCount = 1;
            } else {
                visitCount++;
            }

            // Store the updated visit count in the session
            session.setAttribute("visitCount", visitCount);

            // Display visit count in the response
            out.print("<br>Welcome to HelloServlet<br>");
            out.print("<br>Visit count: " + visitCount + "<br>");
        } else {
            out.print("Session not valid. Please log in.");
        }

        out.close();
    }

    public void destroy() {
        System.out.println("------------------------------------------------------");
        System.out.println(" destroy method is called in " + this.getClass().getName());
        System.out.println("------------------------------------------------------");
    }
}
