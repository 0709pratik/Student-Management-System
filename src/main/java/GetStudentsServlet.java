import java.io.IOException;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

@WebServlet("/getStudents")
public class GetStudentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            StringBuilder json = new StringBuilder();
            json.append("[");

            while (rs.next()) {
                json.append("{");

                json.append("\"name\":\"").append(rs.getString("name")).append("\",");
                json.append("\"roll\":").append(rs.getInt("roll")).append(",");
                json.append("\"email\":\"").append(rs.getString("email")).append("\",");
                json.append("\"course\":\"").append(rs.getString("course")).append("\",");
                json.append("\"percentage\":").append(rs.getDouble("percentage")).append(",");

                // 🔥 NEW FIELD ADDED
                json.append("\"track\":\"").append(rs.getString("track")).append("\"");

                json.append("},");
            }

            // remove last comma safely
            if (json.length() > 1 && json.charAt(json.length() - 1) == ',') {
                json.deleteCharAt(json.length() - 1);
            }

            json.append("]");

            response.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("[]");
        }
    }
}