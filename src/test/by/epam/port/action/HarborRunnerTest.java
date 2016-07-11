package test.by.epam.port.action;

import by.epam.port.entity.Harbor;
import by.epam.port.entity.Ship;
import by.epam.port.exception.HarborCreationException;
import by.epam.port.exception.JsonParserException;
import by.epam.port.util.HarborRunner;
import by.epam.port.util.JsonParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class HarborRunnerTest {
    private Harbor harbor;
    private List<Ship> ships;

    @Before
    public void init() {
        try {
            JsonParser parser = JsonParser.createParser("ships.json");
            harbor = Harbor.getInstance();
            ships = parser.readShipsWithDelay();
        } catch (JsonParserException | HarborCreationException e) {
            Assert.fail();
        }
    }

    @Test(timeout = 11000)
    public void runTest() {
        HarborRunner runner = new HarborRunner(harbor, ships);
        runner.start();
    }
}
