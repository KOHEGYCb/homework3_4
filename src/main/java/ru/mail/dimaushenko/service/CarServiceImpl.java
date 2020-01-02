package ru.mail.dimaushenko.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.mail.dimaushenko.repository.connectionPool.ConnectionPool;
import ru.mail.dimaushenko.repository.dao.CarDAO;
import ru.mail.dimaushenko.repository.model.Car;

public class CarServiceImpl implements CarService {

    @Override
    public void addEntity(Car car) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addEntities(List<Car> cars) {
        try (
                Connection connection = ConnectionPool.getINSTANCE().getConnection();) {
            connection.setAutoCommit(false);
            for (Car car : cars) {
                CarDAO.getINSTANCE().addEntity(car);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
