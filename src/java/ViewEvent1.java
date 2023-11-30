import java.io.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class ViewEvent1 extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Event Page</title>");
        out.println("<link rel=\"stylesheet\" href=\"total.css\">");
        out.println("<link href=\"https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap\" rel=\"stylesheet\">");
        out.println("<style>");
        out.println("body { font-family: 'Balsamiq Sans', sans-serif;}");
        out.println("h1 { text-align: center; }");
        out.println(".event-table { margin: 20px auto; width: 48%; border-collapse: collapse;}");
        out.println(".event-table th, .event-table td { border: 1px solid #ddd; padding: 8px; text-align: left;}");
        out.println(".event-table th { background-color: #4CAF50; color: white; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String conURL = "jdbc:oracle:thin:@localhost:1521:orcl";
            String user = "system";
            String pass = "system";
            Connection con = DriverManager.getConnection(conURL, user, pass);

            response.setContentType("text/html");
            Statement stmt1 = con.createStatement();
            ResultSet rp = stmt1.executeQuery("select * from Event where ename is not null");

            out.println("<h1>Event Details</h1>");
            out.println("<div class=\"event-table\">");
            out.println("<table>");
            out.println("<tr><th>EventNumber</th><th>EventName</th><th>Coordinator</th><th>Coordinator Contact</th><th>Fees</th><th>Venue</th><th>Date</th></tr>");
            while (rp.next()) {
                String n = rp.getString("enum");
                String nm = rp.getString("ename");
                String co = rp.getString("coord");
                String cono = rp.getString("coordnum");
                String f = rp.getString("fee");
                String v = rp.getString("venue");
                String d = rp.getString("edate");
                out.println("<tr><td>" + n + "</td><td>" + nm + "</td><td>" + co + "</td><td>" + cono + "</td><td>" + f + "</td><td>" + v + "</td><td>" + d + "</td></tr>");
            }
            con.commit();
            con.close();
            out.println("</table>");
            out.println("</div>");
            out.print("</body>");
            out.print("</html>");
        } catch (Exception exe) {
            System.out.println("Exception caught" + exe);
        }
    }
}
