package by.epam.port.util;

import by.epam.port.entity.Harbor;
import by.epam.port.entity.Ship;
import by.epam.port.exception.JsonParserException;
import by.epam.port.exception.HarborCreationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String INPUT_FILE_NAME = "ships.json";

    public static void main(String[] args) {
        MessageManager.changeLocale(new Locale("ru", "RU"));
        JsonParser parser;
        try {
            parser = JsonParser.createParser(INPUT_FILE_NAME);
            LOGGER.info(MessageManager.getString(MessageName.PARSER_CREATED));
        } catch (JsonParserException e) {
            LOGGER.fatal(MessageManager.getString(MessageName.PARSER_NOT_CREATED));
            return;
        }
        try {
            Harbor harbor = Harbor.getInstance();
            List<Ship> ships = parser.readShipsWithDelay();
            HarborRunner runner = new HarborRunner(harbor, ships);
            runner.start();
        } catch (JsonParserException | HarborCreationException e) {
            LOGGER.fatal(MessageManager.getString(MessageName.HARBOR_CREATION_ERROR));
        }
    }
}
