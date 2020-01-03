package ru.mail.dimaushenko.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AbstractDAO<T> {

    public List<T> getAll();
    
    public T getEntityById(int id);

    public void addEntity(T t);
    
    public void addEntities(List<T> t);
}
