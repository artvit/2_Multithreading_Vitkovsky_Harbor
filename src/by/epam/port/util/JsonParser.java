package by.epam.port.util;

import by.epam.port.action.ShipCreator;
import by.epam.port.entity.Ship;
import by.epam.port.exception.JsonParserException;
import by.epam.port.exception.ShipCapacityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    private static final Logger LOGGER = LogManager.getLogger();

    private static JsonParser instance = null;

    private static final String CAPACITY = "capacity";
    private static final String DOCKS = "docks";
    private static final String SHIPS = "ships";
    private static final String LOADING = "loading";
    private static final String UNLOADING = "unloading";

    private JsonObject jsonMainObject;

    private JsonParser(String filename) throws JsonParserException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filename))){
            JsonReader reader = Json.createReader(bufferedReader);
            this.jsonMainObject = reader.readObject();
        } catch (IOException | JsonException e) {
            throw new JsonParserException(MessageManager.getString(MessageName.JSON_PARSER_CREATION), e);
        }
    }

    public static JsonParser createParser(String filename) throws JsonParserException {
        instance = new JsonParser(filename);
        return instance;
    }

    public static JsonParser getInstance(){
        return instance;
    }

    public int readHarborCapacity() throws JsonParserException {
        JsonNumber jsonCapacity = jsonMainObject.getJsonNumber(CAPACITY);
        if (jsonCapacity != null) {
            int capacity = jsonMainObject.getInt(CAPACITY);
            LOGGER.info(MessageManager.getString(MessageName.CAPACITY_EXTRACTED));
            return capacity;
        } else {
            throw new JsonParserException(MessageManager.getString(MessageName.CAPACITY_EXTRACTION_FAIL));
        }
    }

    public int readHarborDocksNumber() throws JsonParserException {
        try {
            int terminalsNUmber = jsonMainObject.getInt(DOCKS);
            LOGGER.info(MessageManager.getString(MessageName.DOCKS_EXTRACTED));
            return terminalsNUmber;
        } catch (Exception e) {
            throw new JsonParserException(MessageManager.getString(MessageName.DOCKS_EXTRACTION_FAIL));
        }
    }

    public List<Ship> readShipsWithDelay() throws JsonParserException {
        ArrayList<Ship> ships = new ArrayList<>();
        JsonArray array = jsonMainObject.getJsonArray(SHIPS);
        for (JsonObject jsonObject : array.getValuesAs(JsonObject.class)) {
            try {
                Ship ship = parseShip(jsonObject);
                ships.add(ship);
            } catch (JsonParserException e) {
                LOGGER.error(MessageManager.getString(MessageName.SHIP_PARSE_ERROR), e);
            }
        }
        LOGGER.info(MessageManager.getString(MessageName.SHIPS_READ));
        return ships;
    }

    private Ship parseShip(JsonObject jsonObject) throws JsonParserException {
        try {
            JsonNumber jsonCapacity = jsonObject.getJsonNumber(CAPACITY);
            JsonNumber jsonLoading = jsonObject.getJsonNumber(LOADING);
            JsonNumber jsonUnloading = jsonObject.getJsonNumber(UNLOADING);
            if (jsonCapacity != null && jsonLoading != null && jsonUnloading != null) {
                int capacity = jsonCapacity.intValue();
                int loading = jsonLoading.intValue();
                int unloading = jsonUnloading.intValue();
                return ShipCreator.createShip(capacity, loading, unloading);
            } else {
                throw new JsonParserException(MessageManager.getString(MessageName.WRONG_ATTRIBUTE_EXCEPTION));
            }
        } catch (ClassCastException | ShipCapacityException e) {
            throw new JsonParserException(MessageManager.getString(MessageName.WRONG_ATTRIBUTE_EXCEPTION), e);
        }
    }
}
