package by.epam.port.util;

import by.epam.port.entity.Harbor;
import by.epam.port.entity.Ship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HarborRunner {
    private static final Logger LOGGER = LogManager.getLogger();

    private Harbor harbor;
    private List<Ship> ships;

    public HarborRunner(Harbor harbor, List<Ship> ships) {
        this.harbor = harbor;
        this.ships = ships;
    }

    public void start() {
        LOGGER.info(MessageManager.getString(MessageName.HARBOR_RUNNER_STARTED));
        ArrayList<Thread> threads = new ArrayList<>();
        for (Ship ship : ships) {
            ship.setHarbor(harbor);
            Thread thread = new Thread(ship);
            thread.start();
            LOGGER.info(MessageManager.getString(MessageName.START_SHIP));
            threads.add(thread);
        }
        for (Thread thread : threads) {
            try {
                TimeUnit.SECONDS.timedJoin(thread, 10);
                LOGGER.info(MessageManager.getString(MessageName.END_SHIP));
            } catch (InterruptedException e) {
                LOGGER.error(MessageManager.getString(MessageName.SHIP_WORK_INTERRUPTED), e);
            }
        }
        LOGGER.info(MessageManager.getString(MessageName.HARBOR_RUNNER_FINISHED));
    }
}
