package ru.mail.dimaushenko.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.mail.dimaushenko.repository.connectionPool.ConnectionPool;
import ru.mail.dimaushenko.repository.constants.SQLColumnsConstants;
import ru.mail.dimaushenko.repository.constants.SQLRequestsConstants;
import ru.mail.dimaushenko.repository.model.Car;
import ru.mail.dimaushenko.repository.model.CarModelEnum;

public class CarDAO implements AbstractDAO<Car> {

    private static final CarDAO INSTANCE = new CarDAO();

    private CarDAO() {
    }

    public static CarDAO getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList();

        try (Connection connection = ConnectionPool.getINSTANCE().getConnection();) {
            try (
                    PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.GET_ALL_CARS);
                    ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    cars.add(getCar(resultSet));
                }
            } catch (SQLException ex) {
                Logger.getLogger(CarDAO.class.getName()).log(Level.SEVERE, null, ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cars;
    }

    @Override
    public Car getEntityById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Car> getEntitiesByEngineCapacity(int engineCapacity) {
        List<Car> cars = new ArrayList();

        try (Connection connection = ConnectionPool.getINSTANCE().getConnection();) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.GET_CARS_BY_ENGINE_CAPACITY);) {
                statement.setInt(1, engineCapacity);
                try (ResultSet resultSet = statement.executeQuery();) {
                    while (resultSet.next()) {
                        cars.add(getCar(resultSet));
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                Logger.getLogger(CarDAO.class.getName()).log(Level.SEVERE, null, ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cars;
    }

    @Override
    public void addEntity(Car car) {
        try (Connection connection = ConnectionPool.getINSTANCE().getConnection();) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.INSERT_CAR);) {
                statement.setString(1, car.getName());
                int carModelId = CarModelDAO.getINSTANCE().getEntityIdByName(car.getCarModel());
                statement.setInt(2, carModelId);
                statement.setInt(3, car.getEngineCapacity());
                statement.execute();

                connection.commit();
            } catch (SQLException ex) {
                Logger.getLogger(CarDAO.class.getName()).log(Level.SEVERE, null, ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addEntities(List<Car> cars) {
        try (Connection connection = ConnectionPool.getINSTANCE().getConnection();) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.INSERT_CAR);) {
                for (Car car : cars) {
                    statement.setString(1, car.getName());
                    int carModelId = CarModelDAO.getINSTANCE().getEntityIdByName(car.getCarModel());
                    statement.setInt(2, carModelId);
                    statement.setInt(3, car.getEngineCapacity());
                    statement.execute();
                }
                connection.commit();
            } catch (SQLException ex) {
                Logger.getLogger(CarDAO.class.getName()).log(Level.SEVERE, null, ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Car car = new Car();

        car.setId(resultSet.getInt(SQLColumnsConstants.CAR_ID));
        car.setName(resultSet.getString(SQLColumnsConstants.CAR_NAME));

        CarModelEnum carModel = CarModelDAO.getINSTANCE().getEntityById(resultSet.getInt(SQLColumnsConstants.CAR_ID_MODEL));
        car.setCarModel(carModel);
        car.setEngineCapacity(resultSet.getInt(SQLColumnsConstants.CAR_ENGINE_CAPACITY));
//        car.setId(resultSet.getInt(SQLColumns.USER_ID));
//        car.setLogin(resultSet.getString(SQLColumns.USER_LOGIN));
//        car.setPassword(resultSet.getString(SQLColumns.USER_PASSWORD));
//        car.setRole(resultSet.getInt(SQLColumns.USER_ROLE));
//        car.setActive(resultSet.getInt(SQLColumns.USER_ACTIVE));
//        car.setAuthorizate(resultSet.getInt(SQLColumns.USER_AUTHORIZE));

        return car;
    }

}
