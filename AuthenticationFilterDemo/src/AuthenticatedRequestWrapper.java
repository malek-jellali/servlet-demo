import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class AuthenticatedRequestWrapper extends HttpServletRequestWrapper {
    private boolean authenticated;

    public AuthenticatedRequestWrapper(HttpServletRequest request, boolean authenticated) {
        super(request);
        this.authenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
