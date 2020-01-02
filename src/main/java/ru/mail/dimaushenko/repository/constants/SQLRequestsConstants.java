package ru.mail.dimaushenko.repository.constants;

public class SQLRequestsConstants {

    public static final String GET_ALL_CARS = "SELECT * FROM car;";
    
    public static final String GET_CAR_MODEL_BY_ID = "SELECT * FROM car_model WHERE id = ?;";
    
    public static final String INSERT_CAR = "INSERT INTO car (name, id_car_model, engine_capacity) VALUES (?, ?, ?);";
    public static final String INSERT_CAR_MODEL = "INSERT INTO car_model (name) VALUES (?)";
    
    
}
