package model.dao;

import model.database.OracleConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public abstract class DAO {
    // Database connection
    protected OracleConnection connection = new OracleConnection();
    protected PreparedStatement preparedStatement;
    protected String sql;

    // Standard date format
    protected DateFormat simple = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    // CRUD pattern
    public abstract Object create() throws SQLException;

    public abstract List<?> readAll() throws SQLException;

    public abstract Object read(int id);

    public abstract Object exists(String email, String password) throws SQLException;

    public abstract Object update();

    public abstract void delete(int id);

    protected void closeConnection() {
        try {
            if (preparedStatement != null) preparedStatement.close();
            connection.getConnection().close();
            System.out.println("Successful disconnection");
        } catch (SQLException e) {
            System.out.println("Disconnection error: " + e.getMessage());
        }
    }

    protected List<String> decodeActivities(String activities) {
        return Arrays.asList(activities.split(";;;;"));
    }

    protected String codeActivities(List<String> activities) {
        return String.join(";;;;", activities);
    }
}
