package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {
    private Connection connection;

    public OracleConnection() {
        connect();
    }

    private void connect() {
        String user = "NOMASACCIDENTABILIDAD";
//        String user = "no_mas_accidentabilidad";
        String pass = "12345";
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String driverPath = "oracle.jdbc.driver.OracleDriver";
        System.out.println("Connecting...");
        try {
            Class.forName(driverPath);
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Successful connection");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
