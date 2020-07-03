package model.dao;

import control.actor.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    public void create() {
        sql = "INSERT INTO CUSTOMERS (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER) VALUES (?,?,?,?,?)";
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
//            int index = 1;
//            for (Object data : customer.getData()) {
//                preparedStatement.setString(index++, data);
//            }
//            preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error while creating: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<Customer> read() throws SQLException {
        sql = "SELECT * FROM CUSTOMERS";
        try (Statement statement = connection.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE); ResultSet resultSet = preparedStatement.executeQuery(sql)) {
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
        sql = "SELECT * FROM COSTUMERS WHERE ID=" + id;
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sqlData(id, resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error while reading: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return customers.get(0);
    }

    private void sqlData(int id, ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        String email = resultSet.getString(4);
        String password = resultSet.getString(5); // It's necessary?
        String phoneNumber = resultSet.getString(6);
//        customers.add(new Customer(id, name, lastName, email, password, phoneNumber));
    }

    @Override
    public void update() {
//        sql = "UPDATE CUSTOMERS SET NAME=?, LAST_NAME=?, EMAIL=?, PASSWORD=?, PHONE_NUMBER=? WHERE CUSTOMER_ID=" + customer.id;
        try {
            preparedStatement = connection.getConnection().prepareStatement(sql);
//            int index = 1;
//            for (Object data : customer.getData()) {
//                preparedStatement.setString(index++, data);
//            }
//            preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error while updating: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete() {
//        sql = "DELETE FROM CUSTOMERS WHERE ID=" + customer.id;
        try {
            connection.getConnection().prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            System.out.println("Error while deleting: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }
}
