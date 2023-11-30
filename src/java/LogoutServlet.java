import java.io.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Do not create a new session if it doesn't exist

        if (session != null) {
            // Invalidate the session
            session.invalidate();
        }

        // Redirect to the login page or any other appropriate page after logout
        response.sendRedirect("Plogin.html");
    }
}
