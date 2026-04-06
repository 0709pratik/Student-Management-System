import java.io.IOException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

@WebFilter("/*")
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        HttpSession session = request.getSession(false);

        // 🔥 ALLOW PUBLIC PAGES (VERY IMPORTANT)
        if (path.contains("index.html") || 
            path.contains("login") || 
            path.contains("signup") || 
            path.contains("signup.html")) {

            chain.doFilter(req, res);
            return;
        }

        // 🔒 PROTECT OTHER PAGES
        if (session != null && session.getAttribute("user") != null) {
            chain.doFilter(req, res); // allow
        } else {
            response.sendRedirect("index.html"); // block
        }
    }
}