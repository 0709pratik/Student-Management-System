import java.io.IOException;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();

            // 🔥 Insert user into DB
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users (username, password) VALUES (?, ?)"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            int i = ps.executeUpdate();

            if (i > 0) {

                System.out.println("Signup SUCCESS");

                // ✅ Redirect to login page after signup
                response.sendRedirect("index.html");

            } else {

                System.out.println("Signup FAILED");

                response.getWriter().println("Signup Failed!");

            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}