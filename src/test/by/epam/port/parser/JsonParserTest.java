package test.by.epam.port.parser;

import by.epam.port.entity.Ship;
import by.epam.port.exception.JsonParserException;
import by.epam.port.util.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class JsonParserTest {
    public static final String INCORRECT_FILE_NAME = "ships_wrong.json";
    public static final String EMPTY_FILE_NAME = "empty.json";

    @Test(expected = JsonParserException.class)
    public void noFileTest() throws JsonParserException {
        JsonParser parser = JsonParser.createParser("123.json");
    }

    @Test (expected = JsonParserException.class)
    public void emptyFileTest() throws JsonParserException {
        JsonParser parser = JsonParser.createParser(EMPTY_FILE_NAME);
    }

    @Test
    public void wrongDataTest() {
        try {
            JsonParser parser = JsonParser.createParser(INCORRECT_FILE_NAME);
            List<Ship> ships = parser.readShipsWithDelay();
            Assert.assertTrue(ships.isEmpty());
        } catch (JsonParserException e) {
            Assert.fail("Exception must not be thrown");
        }
    }
}
