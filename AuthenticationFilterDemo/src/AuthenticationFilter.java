import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/hello")
public class AuthenticationFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
        System.out.println("------------------------------------------------------");
        System.out.println("init method is called in " + this.getClass().getName());
        System.out.println("------------------------------------------------------");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("doFilter method is called in " + this.getClass().getName() + "\n\n");

        PrintWriter out = response.getWriter();

        // Cast ServletRequest to HttpServletRequest to use getSession()
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Retrieve the session (create a new one if it doesn't exist)
        HttpSession session = httpRequest.getSession(true);

        // Check if the request is a login attempt
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean authenticated = false;

        if (username != null && password != null) {
            // This is a login attempt, validate the credentials
            if ("admin".equals(username) && "admin".equals(password)) {
                // Valid login
                authenticated = true;
            } else {
                // Invalid login, display an error message
                out.print("Invalid username or password.");
                return;
            }
        }

        // Wrap the request to include authentication information
        AuthenticatedRequestWrapper wrappedRequest = new AuthenticatedRequestWrapper(httpRequest, authenticated);

        if (authenticated) {
            // Valid login, proceed with the filter chain
            chain.doFilter(wrappedRequest, response);
        } else {
            out.print("Session not valid. Please log in.");

            // Redirect to a login page (if needed)
            // ((HttpServletResponse) response).sendRedirect("login.html");
        }
    }

    public void destroy() {
        System.out.println("------------------------------------------------------");
        System.out.println("destroy method is called in " + this.getClass().getName());
        System.out.println("------------------------------------------------------");
    }
}
