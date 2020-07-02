package control.dao;

import java.util.List;

public interface DAO {
    // CRUD pattern
    public void create(Object object);
    public List<?> read();
    public void update(int id);
    public void delete(int id);
}
