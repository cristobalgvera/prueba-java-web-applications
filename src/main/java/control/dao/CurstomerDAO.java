package control.dao;

import control.actor.Customer;

import java.util.List;

public class CurstomerDAO implements DAO {
    List<Customer> customers;

    @Override
    public void create(Object object) {

    }

    @Override
    public List<Customer> read() {
        return customers;
    }

    @Override
    public void update(int id) {

    }

    @Override
    public void delete(int id) {

    }
}
