import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Update {
    public static void main(String[] args) {
        try {
            //1. Connection to DB;
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "root");

            //2. Create a statement
            Statement myStmt = myConn.createStatement();

            //3. Execute SQL query
            String sql = "update employees"
                    + " set email='demo@luv2code.com'"
                    + " where id=4";

            myStmt.executeUpdate(sql);

            System.out.println("Update Complete");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
