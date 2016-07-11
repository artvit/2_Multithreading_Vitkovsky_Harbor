package test.by.epam.port.action;

import by.epam.port.action.ShipIdGenerator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by artvi on 06/06/2016.
 */
public class ShipIdGeneratorTest {
    @Test
    public void nexIdTest() {
        final long[] id1 = new long[1];
        Thread threadOne = new Thread(() -> {
            id1[0] = ShipIdGenerator.nextId();
        });
        final long[] id2 = new long[1];
        Thread threadTwo = new Thread(() -> {
            id2[0] = ShipIdGenerator.nextId();
        });
        threadOne.start();
        threadTwo.start();
        try {
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            Assert.fail("Exception must not be thrown");
        }
        Assert.assertTrue(Math.abs(id1[0] - id2[0]) == 1);
    }
}
