package ru.mail.dimaushenko.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.mail.dimaushenko.repository.connectionPool.ConnectionPool;
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
    public List<CarModelEnum> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addEntity(CarModelEnum t){
        try (
                Connection connection = ConnectionPool.getINSTANCE().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.INSERT_CAR_MODEL);) {
            statement.setString(1, t.name());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CarModelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public CarModelEnum getEntityById(int id) {
        try (
                Connection connection = ConnectionPool.getINSTANCE().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQLRequestsConstants.GET_CAR_MODEL_BY_ID);) {
            statement.setInt(1, id);
            try (
                    ResultSet result = statement.executeQuery();) {
                if (result.next()) {
                    return CarModelEnum.valueOf(result.getString(SQLColumnsConstants.CAR_MODEL_NAME));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarModelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
