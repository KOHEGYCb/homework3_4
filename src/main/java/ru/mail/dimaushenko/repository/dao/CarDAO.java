package ru.mail.dimaushenko.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    public List<Car> getAll(Connection connection) throws SQLException {
        List<Car> cars = new ArrayList();
        try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.GET_ALL_CARS);) {
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    cars.add(getCar(connection, resultSet));
                }
            }
        }
        return cars;
    }

    @Override
    public Car getEntityById(Connection connection, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Car> getEntitiesByEngineCapacity(Connection connection, int engineCapacity) throws SQLException {
        List<Car> cars = new ArrayList();
        try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.GET_CARS_BY_ENGINE_CAPACITY);) {
            statement.setInt(1, engineCapacity);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    cars.add(getCar(connection, resultSet));
                }
            }
        }
        return cars;
    }

    public List<Car> getEntitiesByMinEngineCapacity(Connection connection) throws SQLException {
        List<Car> cars = new ArrayList();
        try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.GET_CARS_BY_MIN_ENGINE_CAPACITY);) {
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    cars.add(getCar(connection, resultSet));
                }
            }
        }
        return cars;
    }

    public int getCountEntitiesByEngineCapacity(Connection connection, int engineCapacity) throws SQLException {
        int count = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.GET_COUNT_CARS_BY_ENGINE_CAPACITY);) {
            statement.setInt(1, engineCapacity);
            try (ResultSet result = statement.executeQuery();) {
                if (result.next()) {
                    count = result.getInt(SQLColumnsConstants.CARS_COUNT);
                }
            }
        }
        return count;
    }

    public void removeEntity(Connection connection, Car car) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.REMOVE_CAR_BY_ID);) {
            statement.setInt(1, car.getId());
            statement.execute();
        }
    }

    @Override
    public void addEntity(Connection connection, Car car) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.INSERT_CAR);) {
            statement.setString(1, car.getName());
            int carModelId = CarModelDAO.getINSTANCE().getEntityIdByName(connection, car.getCarModel());
            statement.setInt(2, carModelId);
            statement.setInt(3, car.getEngineCapacity());
            statement.execute();
        }
    }

    public void updateEntity(Connection connection, Car car) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.UPDATE_CAR);) {
            statement.setString(1, car.getName());
            int carModelId = CarModelDAO.getINSTANCE().getEntityIdByName(connection, car.getCarModel());
            statement.setInt(2, carModelId);
            statement.setInt(3, car.getEngineCapacity());
            statement.setInt(4, car.getId());
            statement.execute();
        }
    }

    private Car getCar(Connection connection, ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getInt(SQLColumnsConstants.CAR_ID));
        car.setName(resultSet.getString(SQLColumnsConstants.CAR_NAME));
        CarModelEnum carModel = CarModelDAO.getINSTANCE().getEntityById(connection, resultSet.getInt(SQLColumnsConstants.CAR_ID_MODEL));
        car.setCarModel(carModel);
        car.setEngineCapacity(resultSet.getInt(SQLColumnsConstants.CAR_ENGINE_CAPACITY));
        return car;
    }

}
