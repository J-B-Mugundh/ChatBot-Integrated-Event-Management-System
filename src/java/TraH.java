import java.io.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class TraH extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Transactions Page</title>");
        out.println("<link rel=\"stylesheet\" href=\"total.css\">");
        out.println("<link href=\"https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap\" rel=\"stylesheet\">");
        out.println("<style>");
        out.println("body { font-family: 'Balsamiq Sans', sans-serif; color: white; }");
        out.println("h1 { text-align: center; }");
        out.println(".transaction-table { display: flex; justify-content: center; align-items: center; width: 48%%; margin: auto; border-collapse: collapse; margin-top: 20px; }");
        out.println(".transaction-table th, .transaction-table td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        out.println(".transaction-table th { background-color: #4CAF50; color: white; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String conURL = "jdbc:oracle:thin:@localhost:1521:orcl";
            String user = "system";
            String pass = "system";
            Connection con = DriverManager.getConnection(conURL, user, pass);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from card");

            out.println("<h1>Transaction Details</h1>");
            out.println("<div class=\"transaction-table\">");
            out.println("<table>");
            out.println("<tr><th>Event Name</th><th>Event Date</th><th>Participant Name</th><th>Card Number</th></tr>");

            while (rs.next()) {
                // String en = rs.getString("enum");
                String re = rs.getString("ename");
                String pd = rs.getString("edate");
                String name = rs.getString("cname");
                String cno = rs.getString("cardno");

                out.println("<tr><td>" + re + "</td><td>" + pd + "</td><td>" + name + "</td><td>" + cno + "</td></tr>");
            }

            con.commit();
            con.close();
            out.println("</table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception exe) {
            System.out.println("Exception caught");
        }
    }
}
