import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete {
    public static void main(String[] args) {
        try {
            //1. Connection to DB;
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "root");

            //2. Create a statement
            Statement myStmt = myConn.createStatement();

            //3. Execute SQL query
            String sql = "delete from employees where last_name='Brown'";

            int rowsAffected = myStmt.executeUpdate(sql);

            System.out.println("Rows affected: " + rowsAffected);
            System.out.println("Delete Complete");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
