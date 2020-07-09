package model.dao;

import control.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDAO extends DAO {
    Employee employee;
    List<Employee> employees;

    public EmployeeDAO() {
        employees = new ArrayList<>();
    }

    public EmployeeDAO(Employee employee) {
        this.employee = employee;
        employees = new ArrayList<>();
    }

    @Override
    public Object create() throws SQLException {
        sql = "INSERT INTO EMPLOYEES (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER) VALUES (?,?,?,?,?)";
        ResultSet resultSet = null;
        try {
            setStatement();
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sql = "SELECT MAX(EMPLOYEE_ID) FROM EMPLOYEES";
            resultSet = connection.getConnection().prepareStatement(sql).executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error while creating: " + e.getMessage());
        }
        return read(resultSet.getInt(1));
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
    public List<Employee> readAll() throws SQLException {
        sql = "SELECT * FROM EMPLOYEES";
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
        return employees;
    }

    @Override
    public Object read(int id) {
        sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
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
        return !employees.isEmpty() ? employees.get(0) : -1;
    }

    private void sqlData(int id, ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        String email = resultSet.getString(4);
        String password = resultSet.getString(5); // It's necessary?
        String phoneNumber = resultSet.getString(6);
        employees.add(new Employee(id, name, lastName, email, password, phoneNumber));
    }

    @Override
    public Object exists(String email, String password) throws SQLException {
        sql = "SELECT * FROM EMPLOYEES WHERE EMAIL = ? AND PASSWORD = ?";
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error while testing existence: " + e.getMessage());
        }
        return read(resultSet.getInt(1));
    }

    @Override
    public Object update() {
        sql = "UPDATE EMPLOYEES SET NAME = ?, LAST_NAME = ?, EMAIL = ?, PASSWORD = ?, " +
                "PHONE_NUMBER = ? WHERE EMPLOYEE_ID = ?";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            setStatement();
            preparedStatement.setInt(6, employee.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating: " + e.getMessage());
        }
        return read(employee.getId());
    }

    @Override
    public void delete(int id) {
        sql = "DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
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

    public Visit getVisit(int visitId) {
        return getVisits(-1, visitId).get(0);
    }

    public List<Visit> pendingVisits() {
        return getVisits(0, -1);
    }

    public List<Visit> readyVisits() {
        return getVisits(1, -1);
    }

    public List<Visit> allVisits() {
        return getVisits(-1, -1);
    }

    private List<Visit> getVisits(int ready, int visitId) {
        List<Visit> visitList = new ArrayList<>();
        if (ready != -1 && visitId == -1) {
            sql = "SELECT * FROM VISITS V INNER JOIN EMPLOYEES E on E.EMPLOYEE_ID = V.EMPLOYEES_EMPLOYEE_ID WHERE EMPLOYEE_ID = ? AND READY = " + ready;
        } else if (ready == -1 && visitId > 0) {
            sql = "SELECT * FROM VISITS WHERE VISIT_ID = ?";
        } else {
            sql = "SELECT * FROM VISITS V INNER JOIN EMPLOYEES E on E.EMPLOYEE_ID = V.EMPLOYEES_EMPLOYEE_ID WHERE EMPLOYEE_ID = ?";
        }
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            if (visitId > 0) {
                preparedStatement.setInt(1, visitId);
            } else {
                preparedStatement.setInt(1, employee.getId());
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Visit visit = new Visit();
                Timestamp timestamp = (resultSet.getTimestamp(4));
                String formattedDate = simple.format(timestamp);

                visit.setId(resultSet.getInt(1));
                visit.setReady(resultSet.getString(2).equals("1")); // String (Char) to a boolean
                visit.setCustomerId(resultSet.getInt(3));
                visit.setDate(formattedDate); // resultSet.getData(4)
                visit.setEmployeeId(resultSet.getInt(5));
                visit.setSummaryId(resultSet.getInt(6));
                visit.setPaymentId(resultSet.getInt(7));
                visit.setActivities(decodeActivities(resultSet.getString(8))); // Codified string (A;;;;B;;;;C) to a List<String> ([A,B,C])

                visitList.add(visit);
            }
        } catch (SQLException e) {
            System.out.println("Error while trying to request pending visits: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return visitList;
    }

    public void endVisit(int visitId) {
        sql = "UPDATE VISITS SET READY = 1 WHERE VISIT_ID = ?";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, visitId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error ending visit: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public Summary getSummary(int visitId) {
        sql = "SELECT SUMMARIES_SUMMARY_ID FROM VISITS WHERE VISIT_ID = ?";
        Summary summary = null;
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, visitId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            sql = "SELECT * FROM SUMMARIES WHERE SUMMARY_ID = " + resultSet.getInt(1);
            preparedStatement.close();
            resultSet = connection.getConnection().prepareStatement(sql).executeQuery();
            resultSet.next();
            summary = new Summary(resultSet.getInt(1), resultSet.getInt(3),
                    resultSet.getString(2), simple.format(resultSet.getTimestamp(4)));
        } catch (SQLException e) {
            System.out.println("Error getting summary: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return summary;
    }

    public void setSummary(Summary summary) {
        String date = simple.format(new Date().getTime());
        sql = "UPDATE SUMMARIES SET DESCRIPTION = ?, RATING = ?, \"date\" = TO_DATE(?, 'dd/mm/yyyy HH24:mi:ss') WHERE SUMMARY_ID = ?";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, summary.getDescription());
            preparedStatement.setInt(2, summary.getRating());
            preparedStatement.setString(3, date);
            preparedStatement.setInt(4, summary.getId());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error setting summary: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public List<String> getActivities(int visitId) {
        sql = "SELECT ACTIVITIES FROM VISITS WHERE VISIT_ID = ?";
        List<String> activities = null;
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, visitId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            activities = decodeActivities(resultSet.getString(1));
        } catch (SQLException e) {
            System.out.println("Error getting activities: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return activities;
    }

    public void setActivities(int visitId, List<String> activities) {
        sql = "UPDATE VISITS SET ACTIVITIES = ? WHERE VISIT_ID = ?";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, codeActivities(activities));
            preparedStatement.setInt(2, visitId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error setting activities: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public Address getAddress(int visitId) {
        sql = "SELECT * FROM ADDRESSES A INNER JOIN VISITS V on A.CUSTOMERS_CUSTOMER_ID " +
                "= V.CUSTOMERS_CUSTOMER_ID WHERE VISIT_ID = ?";
        Address address = new Address();
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, visitId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            address.setId(resultSet.getInt(1));
            address.setCountry(resultSet.getString(2));
            address.setCity(resultSet.getString(3));
            address.setStreet(resultSet.getString(4));
            address.setNumber(resultSet.getInt(5));
            address.setBlock(resultSet.getString(6));
            address.setCustomerId(resultSet.getInt(7));
        } catch (SQLException e) {
            System.out.println("Error getting customer address: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return address;
    }

    public Customer getVisitsCustomer(int visitId) {
        Customer customer = null;
        sql = "SELECT CUSTOMER_ID FROM CUSTOMERS C2 INNER JOIN VISITS V on C2.CUSTOMER_ID = V.CUSTOMERS_CUSTOMER_ID WHERE VISIT_ID = ?";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, visitId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int customerId = resultSet.getInt(1);
            closeConnection();
            customer = (Customer) new CustomerDAO().read(customerId);
        } catch (SQLException e) {
            System.out.println("Error getting visit's customer: " + e.getMessage());
        }
        return customer;
    }
}
