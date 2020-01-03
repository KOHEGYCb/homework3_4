package ru.mail.dimaushenko;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.mail.dimaushenko.repository.model.Car;
import ru.mail.dimaushenko.repository.model.CarModelEnum;
import ru.mail.dimaushenko.service.CarService;
import ru.mail.dimaushenko.service.CarServiceImpl;
import ru.mail.dimaushenko.utils.PrepareDataBase;
import ru.mail.dimaushenko.utils.RandomUtil;

public class App {

    public static void main(String[] args) {
        PrepareDataBase.prepareDB();

        runA();
        runB();
//        runC();
//        runD();
//        runE();
    }

    private static void runA() {

        int amount = 10;
        int minEngineCapacity = 1;
        int maxEngineCapacity = 10;

        List<Car> cars = new ArrayList();
        for (int i = 0; i < amount; i++) {
            Car car = new Car();
            car.setName("Car " + i);
            car.setEngineCapacity(RandomUtil.getInt(minEngineCapacity, maxEngineCapacity));
            car.setCarModel(getRandCarModel());
            cars.add(car);
        }

        CarService carService = new CarServiceImpl();
        carService.addEntities(cars);
    }

    private static void runB() {
        int minEngineCapacity = 1;
        int maxEngineCapacity = 10;
        
        int EngineCapacity = RandomUtil.getInt(minEngineCapacity, maxEngineCapacity);
        
        CarService carService = new CarServiceImpl();
        List <Car> cars = carService.getEntitiesByEngineCapacity(EngineCapacity);
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    private static void runC() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void runD() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void runE() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static CarModelEnum getRandCarModel() {
        return CarModelEnum.values()[new Random().nextInt(CarModelEnum.values().length)];
    }

}
