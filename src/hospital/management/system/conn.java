package hospital.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class conn {
    Connection connection;
    Statement statement;

    public conn() {
        try {
            // Ensure the database credentials are correct
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management_system", "root", "12345");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
