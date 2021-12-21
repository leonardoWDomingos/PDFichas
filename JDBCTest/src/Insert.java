import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Insert {
    public static void main(String[] args) {
        try {
            //1. Connection to DB;
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "root");

            //2. Create a statement
            Statement myStmt = myConn.createStatement();

            //3. Execute SQL query
            String sql = "insert into employees"
                    + " (last_name, first_name, email)"
                    + " values ('Brown', 'David', 'david.brown@foo.com')";

            myStmt.executeUpdate(sql);

            System.out.println("Insert Complete");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
