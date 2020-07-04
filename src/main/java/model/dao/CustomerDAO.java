package model.dao;

import control.entities.Customer;
import control.entities.Visit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
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
        try {
            setStatement();
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sql = "SELECT MAX(CUSTOMER_ID) FROM CUSTOMERS";
            ResultSet resultSet = connection.getConnection().prepareStatement(sql).executeQuery();
            resultSet.next();
            return read(resultSet.getInt(1));
        } catch (SQLException e) {
            System.out.println("Error while creating: " + e.getMessage());
        }
        return null;
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

    public Visit requestVisit(List<String> activities, int price) throws SQLException {
        DateFormat simple = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = simple.format(new Date().getTime());
        try {
            int summary_id = createSummary(date);
            sql = "INSERT INTO VISITS (READY, CUSTOMERS_CUSTOMER_ID, \"date\", EMPLOYEES_EMPLOYEE_ID, ACTIVITIES, " +
                    "SUMMARIES_SUMMARY_ID, PAYMENTS_PAYMENT_ID) VALUES (0, ?, TO_DATE(?, 'dd/mm/yyyy hh:mm:ss'), ?, ?, ?, ?)";
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, date);
            try {
                preparedStatement.setInt(3, selectEmployee());
            } catch (SQLException e) {
                System.out.println("Error while selecting employee to visit, probably you doesn't have employees: " +
                        e.getMessage());
                // TODO Create a generic employee to assign tasks on lack of employees case
            }
            preparedStatement.setString(4, setActivities(activities));
            preparedStatement.setInt(5, summary_id);
            try {
                preparedStatement.setInt(6, assignPrice(date, price));
            } catch (SQLException e) {
                System.out.println("Error while assigning price: " + e.getMessage());
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sql = "SELECT * FROM VISITS WHERE VISIT_ID = (SELECT MAX(VISIT_ID) FROM VISITS);";
            ResultSet resultSet = connection.getConnection().prepareStatement(sql).executeQuery();
            resultSet.next();
            return getVisit(resultSet);
        } catch (SQLException e) {
            System.out.println("Error requesting visit: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }

    private Visit getVisit(ResultSet resultSet) throws SQLException {
        Visit visit = new Visit();
        DateFormat simple = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        visit.setId(resultSet.getInt(1));
        visit.setReady(resultSet.getString(2).equals("1")); // String (Char) to a boolean
        visit.setCustomerId(resultSet.getInt(3));
        visit.setDate(simple.format(resultSet.getDate(4))); // Date to a String
        visit.setEmployeeId(resultSet.getInt(5));
        visit.setSummaryId(resultSet.getInt(6));
        visit.setPaymentId(resultSet.getInt(7));
        visit.setActivities(getActivities(resultSet.getString(8))); // Codified string (A;;;;B;;;;C) to a List<String> ([A,B,C])
        return visit;
    }

    private int assignPrice(String date, int price) throws SQLException {
        String sql1 = "INSERT INTO PAYMENTS (\"date\", AMOUNT, READY) " +
                "VALUES (TO_DATE(?, 'dd/MM/yyyy hh:mm:ss'), ?, 0)";
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
            System.out.println("Error while assigning price: " + e.getMessage());
        } finally {
            return resultSet != null ? resultSet.getInt(1) : -1;
        }
    }

    private int selectEmployee() throws SQLException {
        // TODO Improve this selection code - can be giving it two options: default selection or defined selection
        String sql1 = "SELECT EMPLOYEE_ID " +
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
            List<Integer> resultSetIdList = new ArrayList<>();
            while (resultSet.next()) {
                resultSetIdList.add(resultSet.getInt(1));
            }
            return resultSetIdList.get((int) Math.random() * resultSetIdList.size() - 1);
        } catch (SQLException e) {
            System.out.println("Error selecting employee to assign visit: " + e.getMessage());
            return -1;
        }
    }

    private int createSummary(String date) throws SQLException {
        sql = "INSERT INTO SUMMARIES (DESCRIPTION, RATING, \"date\")" +
                "VALUES ('Visita pendiente', -1, TO_DATE(?))";
        int summaryId = -1;
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            System.out.println("antes date");
            preparedStatement.setString(1, date);
            System.out.println("después date");
            preparedStatement.executeUpdate();
            System.out.println("después update");
            preparedStatement.close();
            sql = "SELECT MAX(SUMMARY_ID) FROM SUMMARIES";
            ResultSet resultSet = connection.getConnection().prepareStatement(sql).executeQuery();
            resultSet.next();
            System.out.println(resultSet.getInt(1));
            summaryId = resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error creating empty summary: " + e.getMessage());
        }
        return summaryId;
    }
}
