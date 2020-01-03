package ru.mail.dimaushenko.repository.constants;

public class SQLRequestsConstants {

    public static final String GET_ALL_CARS = "SELECT * FROM car;";
    public static final String GET_CARS_BY_ENGINE_CAPACITY = "SELECT * FROM car WHERE engine_capacity = ?;";
    public static final String GET_COUNT_CARS_BY_ENGINE_CAPACITY = "SELECT COUNT(id) as count FROM car WHERE engine_capacity = ?;";
    public static final String GET_CARS_BY_MIN_ENGINE_CAPACITY = "SELECT * FROM car WHERE engine_capacity = (SELECT MIN(engine_capacity) FROM car);";

    public static final String GET_CAR_MODEL_BY_ID = "SELECT * FROM car_model WHERE id = ?;";
    public static final String GET_CAR_MODEL_BY_NAME = "SELECT * FROM car_model WHERE name = ?;";

    public static final String INSERT_CAR = "INSERT INTO car (name, id_car_model, engine_capacity) VALUES (?, ?, ?);";
    public static final String INSERT_CAR_MODEL = "INSERT INTO car_model (name) VALUES (?)";

    public static final String REMOVE_CARS_WITH_MIN_ENGINE_CAPACITY = "DELETE FROM car WHERE engine_capacity = (SELECT * FROM (SELECT MIN(engine_capacity) FROM car) AS t1);";
    public static final String REMOVE_CAR_BY_ID = "DELETE FROM car WHERE id = ?;";

    public static final String UPDATE_CAR = "UPDATE car SET name = ?, id_car_model = ?, engine_capacity = ? WHERE id = ?";

}
