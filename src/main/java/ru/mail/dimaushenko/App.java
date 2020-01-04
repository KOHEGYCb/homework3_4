package ru.mail.dimaushenko;

import ru.mail.dimaushenko.service.FirstTask;
import ru.mail.dimaushenko.service.SecondTask;
import ru.mail.dimaushenko.service.TaskService;

public class App {

    public static void main(String[] args) {

        TaskService first = new FirstTask();
        first.run();

        TaskService second = new SecondTask();
        second.run();

    }

}
