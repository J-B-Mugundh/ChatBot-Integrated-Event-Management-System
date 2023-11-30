import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/EventsServlet")
public class EventsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC URL, username, and password of oracle server
    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "system";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user input
        String userInput = request.getParameter("userInput");

        // Check if the user input is related to events
        if (isEventQuery(userInput)) {
            // Forward the request to EventSearchServlet
            RequestDispatcher dispatcher = request.getRequestDispatcher("/EventSearchServlet");
            dispatcher.forward(request, response);
        } else {
            // Forward the request to QueryHandlerServlet
            RequestDispatcher dispatcher = request.getRequestDispatcher("/QueryHandlerServlet");
            dispatcher.forward(request, response);
        }
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

    private boolean isEventQuery(String userInput) {
        List<String> events = getEventsFromDatabase();

        if (userInput != null && !userInput.isEmpty()) {
        String userInputLowerCase = userInput.toLowerCase();

        // Check for matching events
        for (String event : events) {
            if (event.toLowerCase().contains(userInputLowerCase)) {
                return true;
            }
        }
        
    } 
        return false;
    }
}
