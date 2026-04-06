import java.io.IOException;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        try {
            Connection con = DBConnection.getConnection();

            System.out.println("Connected: " + con);

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Login SUCCESS");

                HttpSession session = request.getSession();
                session.setAttribute("user", username);

                response.sendRedirect("dashboard.html");

            } else {
                System.out.println("Login FAILED");
                response.getWriter().println("Invalid username or password");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}