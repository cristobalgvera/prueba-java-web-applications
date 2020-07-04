package model.dao;

import control.entities.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDAO extends DAO {
    Customer customer;
    List<Customer> customers;


    public CustomerDAO() {
        customers = new ArrayList<>();
    }

    public CustomerDAO(Customer customer) {
        this.customer = customer;
        customers = new ArrayList<>();
    }

    @Override
    public Object create() throws SQLException {
        sql = "INSERT INTO CUSTOMERS (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER) VALUES (?,?,?,?,?)";
        ResultSet resultSet = null;
        try {
            setStatement();
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sql = "SELECT MAX(CUSTOMER_ID) FROM CUSTOMERS";
            resultSet = connection.getConnection().prepareStatement(sql).executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error while creating: " + e.getMessage());
        } finally {
            return read(resultSet.getInt(1));
        }
    }

    private void setStatement() throws SQLException {
        preparedStatement = connection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getLastName());
        preparedStatement.setString(3, customer.getEmail());
        preparedStatement.setString(4, customer.getPassword());
        preparedStatement.setString(5, customer.getPhoneNumber());
    }

    @Override
    public List<Customer> readAll() throws SQLException {
        sql = "SELECT * FROM CUSTOMERS";
        try (Statement statement = connection.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                sqlData(id, resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error while reading: " + e.getMessage());
        } finally {
            connection.getConnection().close();
            return customers;
        }
    }

    @Override
    public Object read(int id) {
        sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            sqlData(id, resultSet);
        } catch (SQLException e) {
            System.out.println("Error while reading: " + e.getMessage());
        } finally {
            closeConnection();
            return !customers.isEmpty() ? customers.get(0) : -1;
        }
    }

    private void sqlData(int id, ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        String email = resultSet.getString(4);
        String password = resultSet.getString(5); // It's necessary?
        String phoneNumber = resultSet.getString(6);
        customers.add(new Customer(id, name, lastName, email, password, phoneNumber));
    }

    @Override
    public Object exists(String email, String password) throws SQLException {
        sql = "SELECT * FROM CUSTOMERS WHERE EMAIL = ? AND PASSWORD = ?";
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error while testing existence: " + e.getMessage());
        } finally {
            return read(resultSet.getInt(1));
        }
    }

    @Override
    public Object update() {
        sql = "UPDATE CUSTOMERS SET NAME = ?,LAST_NAME = ?, EMAIL = ?, " +
                "PASSWORD = ?, PHONE_NUMBER = ? WHERE CUSTOMER_ID = ?";
        try {
            setStatement();
            preparedStatement.setInt(6, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while trying to update a registry: " + e.getMessage());
        } finally {
            return read(customer.getId());
        }

    }

    @Override
    public void delete(int id) {
        sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public Object requestVisit(String activities, int price) throws SQLException {
        // TODO Return VISIT_ID from DB
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = formatter.format(date);
        ResultSet resultSet = null;
        try {
            int summary_id = createSummary(formattedDate);
            sql = "INSERT INTO VISITS (READY, CUSTOMERS_CUSTOMER_ID, 'date', EMPLOYEES_EMPLOYEE_ID, ACTIVITIES, " +
                    "SUMMARIES_SUMMARY_ID, PAYMENTS_PAYMENT_ID) VALUES (0, ?, TO_DATE(?, 'dd/mm/yyyy hh24:mi:ss'), ?, ?, ?, ?)";
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, formattedDate);
            try {
                preparedStatement.setInt(3, selectEmployee());
            } catch (SQLException e) {
                System.out.println("Error while selecting employee to visit, probably you doesn't have employees: " +
                        e.getMessage());
                // TODO Create a generic employee to assign tasks on lack of employees case
            }
            preparedStatement.setString(4, activities);
            preparedStatement.setInt(5, summary_id);
            try {
                preparedStatement.setInt(6, assignPrice(formattedDate, price));
            } catch (SQLException e) {
                System.out.println("Error while assigning price: " + e.getMessage());
            }
            preparedStatement.executeUpdate();
            sql = "SELECT MAX(VISIT_ID) FROM VISITS";
            resultSet = connection.getConnection().prepareStatement(sql).executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error requesting visit: " + e.getMessage());
        } finally {
            return resultSet.getInt(1);
        }
    }

    private int assignPrice(String date, int price) throws SQLException {
        String sql1 = "INSERT INTO PAYMENT ('date', AMOUNT, READY) " +
                "VALUES (TO_DATE(?, 'dd/mm/yyyy hh24:mi:ss'), ?, 0);";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement1 = connection.getConnection().prepareStatement(sql1);
            preparedStatement1.setString(1, date);
            preparedStatement1.setInt(2, price);
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
            sql1 = "SELECT MAX(PAYMENT_ID) FROM PAYMENTS";
            resultSet = connection.getConnection().prepareStatement(sql1).executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            return -1;
        } finally {
            return resultSet != null ? resultSet.getInt(1) : -1;
        }
    }

    private int selectEmployee() throws SQLException {
        // TODO Improve this selection code - can be giving it two options: default selection or defined selection
        String sql1 = "SELECT EMPLOYEE_ID, COUNT(EMPLOYEE_ID) " +
                "FROM EMPLOYEES FULL JOIN VISITS V on EMPLOYEES.EMPLOYEE_ID = V.EMPLOYEES_EMPLOYEE_ID " +
                "GROUP BY EMPLOYEE_ID " +
                "HAVING COUNT(EMPLOYEE_ID) = (" +
                "   SELECT MIN(mycount)" +
                "   FROM (" +
                "       SELECT EMPLOYEE_ID, COUNT(EMPLOYEE_ID) mycount" +
                "       FROM EMPLOYEES FULL JOIN VISITS V2 on EMPLOYEES.EMPLOYEE_ID = V2.EMPLOYEES_EMPLOYEE_ID" +
                "       GROUP BY EMPLOYEE_ID))";
        ResultSet resultSet;
        try {
            resultSet = connection.getConnection().prepareStatement(sql1).executeQuery();
            while (resultSet.next()) {
                if (!resultSet.next() || Math.random() >= 0.5) {
                    resultSet.previous();
                    break;
                }
                resultSet.previous();
            }
        } catch (SQLException e) {
            return -1;
        }
        return resultSet.getInt(1);
    }

    private int createSummary(String date) throws SQLException {
        sql = "INSERT INTO SUMMARIES (DESCRIPTION, RATING, 'date') " +
                "VALUES ('Visita pendiente', -1, TO_DATE(?, 'dd/mm/yyyy hh24:mi:ss'))";
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, date);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sql = "SELECT MAX(SUMMARY_ID) FROM SUMMARIES";
            resultSet = connection.getConnection().prepareStatement(sql).executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error creating empty summary: " + e.getMessage());
        }
        return resultSet != null ? resultSet.getInt(1) : -1;
    }
}
