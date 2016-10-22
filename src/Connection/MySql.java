package Connection;

/**
 * Created by SENIAN on 10/20/2016.
 */

import java.sql.Connection;
import java.sql.*;
import java.util.Properties;

public class MySql {

        Connection con = null;


    public Connection getSqlConnection() {
        try {
            //Registreren Driver
            Connection con;
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:2154/Nosql";
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "alibaba");
            this.con = DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.print(e.getException());
        }
            return con;
    }
}

