import java.io.IOException;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

@WebServlet("/addStudent")
public class AddStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔥 Get all form data
        String name = request.getParameter("name");
        int roll = Integer.parseInt(request.getParameter("roll"));
        String email = request.getParameter("email");
        String course = request.getParameter("course");
        double percentage = Double.parseDouble(request.getParameter("percentage"));
        String track = request.getParameter("track");   // ✅ NEW FIELD

        try {
            Connection con = DBConnection.getConnection();
            
            // 🔥 Updated query (includes track)
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO students (name, roll, email, course, percentage, track) VALUES (?, ?, ?, ?, ?, ?)"
            );

            // 🔥 Set values
            ps.setString(1, name);
            ps.setInt(2, roll);
            ps.setString(3, email);
            ps.setString(4, course);
            ps.setDouble(5, percentage);
            ps.setString(6, track);   // ✅ NEW FIELD

            int i = ps.executeUpdate();

            if (i > 0) {
                System.out.println("Student Added Successfully");

                // ✅ Redirect after adding
                response.sendRedirect("students.html");

            } else {
                response.getWriter().println("Failed to add student");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}