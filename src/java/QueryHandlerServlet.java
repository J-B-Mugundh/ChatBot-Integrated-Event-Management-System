import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/QueryHandlerServlet")
public class QueryHandlerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

    private String getBotResponse(String userInput) {
        String userInputLowerCase = userInput.toLowerCase();
        // No matching events found, check for specific queries
        if (userInputLowerCase.contains("register events")) {
            // Return registration procedure
            return "<p>To register for an event, please follow the below steps:</p>"
                    + "<ol>"
                    + "<li>Sign in using your Participant account.</li>"
                    + "<li>View events and decide which event to join.</li>"
                    + "<li>Click on the event and register using your details.</li>"
                    + "<li>Pay the event amount.</li>"
                    + "</ol>";
        } else if (userInputLowerCase.contains("events schedule")) {
            // Return event schedule
            return "<p>Day 1: Tech Events</p>"
                    + "<p>Day 2: Non Tech Events</p>"
                    + "<p>Day 3: DJ Party and Celebrity Visit</p>";
        } else {
            // No matching events and no specific queries found
            return "<p>No matching events found.</p>";
        }
    }
}
