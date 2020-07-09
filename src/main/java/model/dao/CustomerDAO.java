package model.dao;

import control.entities.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CustomerDAO extends DAO {
    Customer employee;
    List<Customer> customers;

    public CustomerDAO() {
        customers = new ArrayList<>();
    }

    public CustomerDAO(Customer customer) {
        this.employee = customer;
        customers = new ArrayList<>();
    }

    @Override
    public Object create() {
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
        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getLastName());
        preparedStatement.setString(3, employee.getEmail());
        preparedStatement.setString(4, employee.getPassword());
        preparedStatement.setString(5, employee.getPhoneNumber());
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
        }
        return customers;
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
        }
        return !customers.isEmpty() ? customers.get(0) : -1;
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
    public Object exists(String email, String password) {
        sql = "SELECT * FROM CUSTOMERS WHERE EMAIL = ? AND PASSWORD = ?";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return read(resultSet.getInt(1));
        } catch (SQLException e) {
            System.out.println("Error while testing existence: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Object update() {
        sql = "UPDATE CUSTOMERS SET NAME = ?,LAST_NAME = ?, EMAIL = ?, " +
                "PASSWORD = ?, PHONE_NUMBER = ? WHERE CUSTOMER_ID = ?";
        try {
            setStatement();
            preparedStatement.setInt(6, employee.getId());
            preparedStatement.executeUpdate();
            return read(employee.getId());
        } catch (SQLException e) {
            System.out.println("Error while trying to update a registry: " + e.getMessage());
        }
        return null;
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

    public void setAddress(Address address) {
        sql = "INSERT INTO ADDRESSES (COUNTRY, CITY, STREET, \"number\", BLOCK, CUSTOMERS_CUSTOMER_ID) " +
                "VALUES (?,?,?,?,?,?)";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setInt(4, address.getNumber());
            preparedStatement.setString(5, address.getBlock());
            preparedStatement.setInt(6, address.getCustomerId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error setting address: " + e.getMessage());
        }
    }

    public Visit requestVisit(List<String> activities, int price) {
        // Random date between today and six months
        String date = simple.format(new Date().getTime() + new Date((Math.abs(new Random().nextLong()) % (1L * 365 * (24 / 2) * 60 * 60 * 1000))).getTime());
        Visit visit = null;
        try {
            int summary_id = createSummary(date);
            sql = "INSERT INTO VISITS (READY, CUSTOMERS_CUSTOMER_ID, \"date\", EMPLOYEES_EMPLOYEE_ID, ACTIVITIES, " +
                    "SUMMARIES_SUMMARY_ID, PAYMENTS_PAYMENT_ID) VALUES (0, ?, TO_DATE(?, 'dd/mm/yyyy HH24:mi:ss'), ?, ?, ?, ?)";
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, employee.getId());
            preparedStatement.setString(2, date);
            try {
                preparedStatement.setInt(3, selectEmployee());
            } catch (SQLException e) {
                System.out.println("Error while selecting employee to visit, probably you doesn't have employees: " +
                        e.getMessage());
                // TODO Create a generic employee to assign tasks on lack of employees case
            }
            preparedStatement.setString(4, codeActivities(activities));
            preparedStatement.setInt(5, summary_id);
            try {
                preparedStatement.setInt(6, assignPrice(date, price));
            } catch (SQLException e) {
                System.out.println("Error while assigning price: " + e.getMessage());
                e.getStackTrace();
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sql = "SELECT * FROM VISITS WHERE VISIT_ID = (SELECT MAX(VISIT_ID) FROM VISITS)";
            ResultSet resultSet = connection.getConnection().prepareStatement(sql).executeQuery();
            resultSet.next();
            visit = getVisit(resultSet);
        } catch (SQLException e) {
            System.out.println("Error requesting visit: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return visit;
    }

    private Visit getVisit(ResultSet resultSet) throws SQLException {
        Visit visit = new Visit();
        visit.setId(resultSet.getInt(1));
        visit.setReady(resultSet.getString(2).equals("1")); // String (Char) to a boolean
        visit.setCustomerId(resultSet.getInt(3));
        visit.setDate(simple.format(resultSet.getTimestamp(4))); // Date to a String
        visit.setEmployeeId(resultSet.getInt(5));
        visit.setSummaryId(resultSet.getInt(6));
        visit.setPaymentId(resultSet.getInt(7));
        visit.setActivities(decodeActivities(resultSet.getString(8))); // Codified string (A;;;;B;;;;C) to a List<String> ([A,B,C])
        return visit;
    }

    public void pay(int paymentId) {
        String date = simple.format(new Date().getTime());
        sql = "UPDATE PAYMENTS SET READY = 1, \"date\" = TO_DATE(?, 'dd/mm/yyyy HH24:mi:ss') WHERE PAYMENT_ID = ?";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, paymentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while paying: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public List<Payment> getPayments() {
        List<Payment> payments = new ArrayList<>();
        sql = "SELECT * FROM PAYMENTS" +
                "         INNER JOIN VISITS V on PAYMENTS.PAYMENT_ID = V.PAYMENTS_PAYMENT_ID" +
                "         INNER JOIN CUSTOMERS C2 on V.CUSTOMERS_CUSTOMER_ID = C2.CUSTOMER_ID " +
                "WHERE CUSTOMER_ID = ?";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, employee.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                payments.add(new Payment(resultSet.getInt(1), resultSet.getInt(3),
                        resultSet.getString(4).equals("1"), simple.format(resultSet.getTimestamp(2))));
            }
        } catch (SQLException e) {
            System.out.println("Error getting payments: " + e.getMessage());
        }
        return payments;
    }

    private int assignPrice(String date, int price) throws SQLException {
        String sql1 = "INSERT INTO PAYMENTS (\"date\", AMOUNT, READY) " +
                "VALUES (TO_DATE(?, 'dd/MM/yyyy HH24:mi:ss'), ?, 0)";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement1 = connection.getConnection().prepareStatement(sql1);
            preparedStatement1.setString(1, date);
            if (price > -1) {
                preparedStatement1.setInt(2, price);
            } else {
                preparedStatement1.setInt(2, (int) Math.floor(Math.random() * (price * 3 + 1) + (Math.abs(price) * 4)));
            }
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
            sql1 = "SELECT MAX(PAYMENT_ID) FROM PAYMENTS";
            resultSet = connection.getConnection().prepareStatement(sql1).executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error while assigning price: " + e.getMessage());
        }
        return resultSet != null ? resultSet.getInt(1) : -1;
    }

    private int selectEmployee() {
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
            return resultSetIdList.get((int) (Math.random() * resultSetIdList.size()));
        } catch (SQLException e) {
            System.out.println("Error selecting employee to assign visit: " + e.getMessage());
            return -1;
        }
    }

    private int createSummary(String date) {
        sql = "INSERT INTO SUMMARIES (DESCRIPTION, RATING, \"date\")" +
                "VALUES ('Visita pendiente', -1, TO_DATE(?, 'dd/mm/yyyy HH24:mi:ss'))";
        int summaryId = -1;
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, date);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sql = "SELECT MAX(SUMMARY_ID) FROM SUMMARIES";
            ResultSet resultSet = connection.getConnection().prepareStatement(sql).executeQuery();
            resultSet.next();
            summaryId = resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error creating empty summary: " + e.getMessage());
        }
        return summaryId;
    }

    public Employee getVisitsEmployee(int visitId) {
        Employee employee = null;
        sql = "SELECT * FROM EMPLOYEES E INNER JOIN VISITS V on E.EMPLOYEE_ID = V.EMPLOYEES_EMPLOYEE_ID WHERE VISIT_ID = ?";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, visitId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int employeeId = resultSet.getInt(1);
            closeConnection();
            employee = (Employee) new EmployeeDAO().read(employeeId);
        } catch (SQLException e) {
            System.out.println("Error getting visit's customer: " + e.getMessage());
        }
        return employee;
    }
}
