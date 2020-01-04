package ru.mail.dimaushenko.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AbstractDAO<T> {

    public List<T> getAll(Connection connection) throws SQLException;

    public T getEntityById(Connection connection, int id) throws SQLException;

    public void addEntity(Connection connection, T t) throws SQLException;

}
