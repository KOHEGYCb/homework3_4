package ru.mail.dimaushenko.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.dimaushenko.constants.ErrorConstants;

public class FirstTask implements TaskService {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static final String FILE_NAME = "sql";

    @Override
    public void run() {
        File file = new File(FILE_NAME);
        try (Scanner scanner = new Scanner(file);) {
            while (scanner.hasNext()) {
                String msg = scanner.nextLine();
                LOGGER.info(msg);
            }
        } catch (FileNotFoundException ex) {
            LOGGER.error(ErrorConstants.ERR_FILE_NOT_FOUND);
        }
    }

}
