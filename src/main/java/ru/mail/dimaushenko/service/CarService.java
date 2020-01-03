package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.repository.model.Car;

public interface CarService {

    public void addEntities(List<Car> cars);

    public List<Car> getEntitiesByEngineCapacity(int engineCapacity);

    public int getCountCarsByEngineCapacity(int engineCapacity);

    public void removeEntitiesWithMinEngineCapacity();

    public void updateCarNameByEngineCapacity(int engineCapacity, String newName);

}
