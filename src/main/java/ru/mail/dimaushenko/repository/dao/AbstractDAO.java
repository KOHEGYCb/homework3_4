package ru.mail.dimaushenko.repository.dao;

import java.util.List;

public interface AbstractDAO<T> {

    public List<T> getAll();

    public void addEntity(T t);
}
