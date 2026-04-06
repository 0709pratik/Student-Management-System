import java.io.IOException;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

@WebServlet("/assignStudents")
public class AssignStudentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String teacher = request.getParameter("teacher");
        String[] students = request.getParameterValues("students");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO teacher_students (teacher_name, student_roll) VALUES (?, ?)"
            );

            for (String roll : students) {
                ps.setString(1, teacher);
                ps.setInt(2, Integer.parseInt(roll));
                ps.executeUpdate();
            }

            response.getWriter().println("Students Assigned Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}