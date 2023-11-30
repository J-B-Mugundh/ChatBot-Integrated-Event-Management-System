import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/EventSearchServlet")
public class EventSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC URL, username, and password of oracle server
    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "system";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user input
        String userInput = request.getParameter("userInput");

        // Process user input and generate a response
        String botResponse = getBotResponse(userInput);

        // Set the response type
        response.setContentType("text/html");

        // Write the response to the client
        PrintWriter out = response.getWriter();
        out.println(botResponse);
    }

    private List<String> getEventsFromDatabase() {
        List<String> events = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            
            String query = "SELECT ename, edesc FROM Event";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String eventName = resultSet.getString("ename");
                    String eventDescription = resultSet.getString("edesc");
                    events.add(eventName + ": " + eventDescription);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return events;
    }

    private String getBotResponse(String userInput) {
        List<String> events = getEventsFromDatabase();

        if (userInput != null && !userInput.isEmpty()) {
        String userInputLowerCase = userInput.toLowerCase();

        // Check for matching events
        for (String event : events) {
            if (event.toLowerCase().contains(userInputLowerCase)) {
                return "<p>" + event + "</p>";
            }
        }
        
    } 
        return "<p>No matching events found.</p>";
        
    }       
}
