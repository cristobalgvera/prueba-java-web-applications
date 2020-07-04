package model.dao;

import model.database.OracleConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public abstract class DAO {
    // Database connection
    OracleConnection connection = new OracleConnection();
    PreparedStatement preparedStatement;
    String sql;

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

    protected List<String> getActivities(String activities) {
        return Arrays.asList(activities.split(";;;;"));
    }

    protected String setActivities(List<String> activities) {
        return String.join(";;;;", activities);
    }
}
