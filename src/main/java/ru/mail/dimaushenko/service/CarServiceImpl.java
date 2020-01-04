package ru.mail.dimaushenko.service;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import ru.mail.dimaushenko.constants.ErrorConstants;
import ru.mail.dimaushenko.repository.connectionPool.ConnectionPool;
import ru.mail.dimaushenko.repository.dao.CarDAO;
import ru.mail.dimaushenko.repository.model.Car;

public class CarServiceImpl implements CarService {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void addEntities(List<Car> cars) {
        try (Connection connection = ConnectionPool.getINSTANCE().getConnection();) {
            connection.setAutoCommit(false);
            try {
                for (Car car : cars) {
                    CarDAO.getINSTANCE().addEntity(connection, car);
                }
                connection.commit();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
        }
    }

    @Override
    public List<Car> getEntitiesByEngineCapacity(int engineCapacity) {
        List<Car> cars = new ArrayList();
        try (Connection connection = ConnectionPool.getINSTANCE().getConnection();) {
            connection.setAutoCommit(false);
            try {
                cars = CarDAO.getINSTANCE().getEntitiesByEngineCapacity(connection, engineCapacity);
                connection.commit();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
        }
        for (Car car : cars) {
            LOGGER.info(car);
        }
        return cars;
    }

    @Override
    public int getCountCarsByEngineCapacity(int engineCapacity) {
        int countCars = 0;
        try (Connection connection = ConnectionPool.getINSTANCE().getConnection();) {
            connection.setAutoCommit(false);
            try {
                countCars = CarDAO.getINSTANCE().getCountEntitiesByEngineCapacity(connection, engineCapacity);
                connection.commit();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
        }
        LOGGER.info("Count: " + countCars);
        return countCars;
    }

    @Override
    public void removeEntitiesWithMinEngineCapacity() {
        try (Connection connection = ConnectionPool.getINSTANCE().getConnection();) {
            connection.setAutoCommit(false);
            try {
                List<Car> cars = CarDAO.getINSTANCE().getEntitiesByMinEngineCapacity(connection);
                for (Car car : cars) {
                    CarDAO.getINSTANCE().removeEntity(connection, car);
                }
                connection.commit();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }

        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
        }
    }

    @Override
    public void updateCarNameByEngineCapacity(int engineCapacity, String newName) {
        try (Connection connection = ConnectionPool.getINSTANCE().getConnection();) {
            connection.setAutoCommit(false);
            try {
                List<Car> cars = CarDAO.getINSTANCE().getEntitiesByEngineCapacity(connection, engineCapacity);
                for (Car car : cars) {
                    car.setName(newName);
                    CarDAO.getINSTANCE().updateEntity(connection, car);
                    LOGGER.info(car);
                }
                connection.commit();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }

        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
        }
    }

}
