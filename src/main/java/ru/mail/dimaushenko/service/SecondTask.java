package ru.mail.dimaushenko.service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.dimaushenko.repository.model.Car;
import ru.mail.dimaushenko.repository.model.CarModelEnum;
import ru.mail.dimaushenko.utils.PrepareDataBase;
import ru.mail.dimaushenko.utils.RandomUtil;

public class SecondTask implements TaskService {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static final CarService CAR_SERVICE = new CarServiceImpl();

    private static final int AMOUNT_CARS = 10;
    private static final int MIN_ENGINE_CAPACITY = 1;
    private static final int MAX_ENGINE_CAPACITY = 10;
    private static final String NEW_NAME = "Good capacity";

    @Override
    public void run() {
        PrepareDataBase.prepareDB();
        runA();
        runB();
        runC();
        runD();
        runE();
    }

    private static void runA() {
        LOGGER.info("Start 2.a");
        List<Car> cars = new ArrayList();
        for (int i = 0; i < AMOUNT_CARS; i++) {
            Car car = new Car();
            car.setName("Car " + i);
            car.setEngineCapacity(RandomUtil.getInt(MIN_ENGINE_CAPACITY, MAX_ENGINE_CAPACITY));
            car.setCarModel(getRandCarModel());
            cars.add(car);
        }
        CAR_SERVICE.addEntities(cars);
    }

    private static void runB() {
        LOGGER.info("Start 2.b");
        int engineCapacity = RandomUtil.getInt(MIN_ENGINE_CAPACITY, MAX_ENGINE_CAPACITY);
        LOGGER.info("Select by EngineCapacity: " + engineCapacity);
        CAR_SERVICE.getEntitiesByEngineCapacity(engineCapacity);
    }

    private static void runC() {
        LOGGER.info("Start 2.c");
        CAR_SERVICE.removeEntitiesWithMinEngineCapacity();
    }

    private static void runD() {
        LOGGER.info("Start 2.d");
        int engineCapacity = RandomUtil.getInt(MIN_ENGINE_CAPACITY, MAX_ENGINE_CAPACITY);
        LOGGER.info("Count by EngineCapacity: " + engineCapacity);
        CAR_SERVICE.getCountCarsByEngineCapacity(engineCapacity);

    }

    private static void runE() {
        LOGGER.info("Start 2.e");
        int engineCapacity = RandomUtil.getInt(MIN_ENGINE_CAPACITY, MAX_ENGINE_CAPACITY);
        LOGGER.info("Change name where EngineCapacity: " + engineCapacity);
        CAR_SERVICE.updateCarNameByEngineCapacity(engineCapacity, NEW_NAME);
    }

    private static CarModelEnum getRandCarModel() {
        return CarModelEnum.values()[new Random().nextInt(CarModelEnum.values().length)];
    }

}
