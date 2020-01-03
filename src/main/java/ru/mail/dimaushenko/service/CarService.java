package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.repository.model.Car;

public interface CarService {
    
    public void addEntity(Car car);
    
    public void addEntities(List <Car> cars);
    
    public List<Car> getEntitiesByEngineCapacity(int engineCapacity);
    
}
