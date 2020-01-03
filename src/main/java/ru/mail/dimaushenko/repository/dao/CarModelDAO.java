package ru.mail.dimaushenko.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ru.mail.dimaushenko.repository.constants.SQLColumnsConstants;
import ru.mail.dimaushenko.repository.constants.SQLRequestsConstants;
import ru.mail.dimaushenko.repository.model.CarModelEnum;

public class CarModelDAO implements AbstractDAO<CarModelEnum> {

    private static final CarModelDAO INSTANCE = new CarModelDAO();

    private CarModelDAO() {
    }

    public static CarModelDAO getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public List<CarModelEnum> getAll(Connection connection) throws SQLException {
        List<CarModelEnum> carModels = new ArrayList();
        try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.GET_ALL_CAR_MODELS);) {
            try (ResultSet result = statement.executeQuery();) {
                while (result.next()) {
                    carModels.add(CarModelEnum.valueOf(result.getString(SQLColumnsConstants.CAR_MODEL_NAME)));
                }
            }
        }
        return carModels;
    }

    @Override
    public CarModelEnum getEntityById(Connection connection, int id) throws SQLException {
        CarModelEnum carModelEnum = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.GET_CAR_MODEL_BY_ID);) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery();) {
                if (result.next()) {
                    carModelEnum = CarModelEnum.valueOf(result.getString(SQLColumnsConstants.CAR_MODEL_NAME));
                }
            }
            connection.commit();
        }
        return carModelEnum;
    }

    public int getEntityIdByName(Connection connection, CarModelEnum model) throws SQLException {
        int id = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.GET_CAR_MODEL_BY_NAME);) {
            statement.setString(1, model.name());
            try (ResultSet result = statement.executeQuery();) {
                if (result.next()) {
                    id = result.getInt(SQLColumnsConstants.CAR_MODEL_ID);
                }
            }
        }
        return id;
    }

    @Override
    public void addEntity(Connection connection, CarModelEnum t) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.INSERT_CAR_MODEL);) {
            statement.setString(1, t.name());
            statement.execute();
        }
    }

}
