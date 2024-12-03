package bg.softuni.orm.context;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DbContext<E> {
    boolean persist(E entity) throws IllegalAccessException, SQLException; //  Insert | Update

    void doCreate(Class<E> entityClass) throws SQLException;
    void doAlter(Class<E> entity) throws SQLException;

    Iterable<E> find(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException; // Select
    Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException; // Select ... where...

    E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException; // Select ... limit 1
    E findFirst(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException; // Select ... where ... limit 1

    int delete(Class<E> table, String where) throws SQLException;
    int delete(E entity) throws SQLException, IllegalAccessException;
}