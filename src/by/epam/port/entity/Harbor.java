package by.epam.port.entity;

import by.epam.port.exception.JsonParserException;
import by.epam.port.exception.HarborCreationException;
import by.epam.port.exception.ShipCapacityException;
import by.epam.port.util.JsonParser;
import by.epam.port.util.MessageManager;
import by.epam.port.util.MessageName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Harbor {
    private static final Logger LOGGER = LogManager.getLogger();

    private int docksNumber;
    private Semaphore docksAccess;
    private Warehouse warehouse;

    private static ReentrantLock lock = new ReentrantLock();
    private static Harbor instance;
    private static AtomicBoolean created = new AtomicBoolean(false);

    private Harbor(int maxCapacity, int docksNumber) {
        this.warehouse = new Warehouse(maxCapacity);
        this.docksNumber = docksNumber;
        this.docksAccess = new Semaphore(docksNumber);
    }

    public static Harbor getInstance() throws HarborCreationException {
        if (!created.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    JsonParser parser = JsonParser.getInstance();
                    try {
                        instance = new Harbor(parser.readHarborCapacity(), parser.readHarborDocksNumber());
                        created.set(true);
                    } catch (JsonParserException e) {
                        throw new HarborCreationException(MessageManager.getString(MessageName.HARBOR_CREATION_ERROR));
                    }
                    LOGGER.info(MessageManager.getString(MessageName.HARBOR_CREATION_ERROR));
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void handleShip(Ship ship) {
        try {
            docksAccess.acquire();
            try {
                if (ship.getUnloadingContainersNumber() > 0) {
                    if (warehouse.addGoods(ship.getUnloadingContainersNumber())) {
                        LOGGER.info(ship + " " + MessageManager.getString(MessageName.UNLOADING_SUCCESS));
                        ship.setUnloadingContainersNumber(0);
                    } else {
                        LOGGER.info(ship + " " + MessageManager.getString(MessageName.UNLOADING_FAIL));
                    }
                }
                if (ship.getLoadingContainersNumber() > 0) {
                    if (warehouse.getGoods(ship.getLoadingContainersNumber())) {
                        LOGGER.info(ship + " " + MessageManager.getString(MessageName.LOADING_SUCCESS));
                        ship.setLoadingContainersNumber(0);
                    } else {
                        LOGGER.info(ship + " " + MessageManager.getString(MessageName.LOADING_FAIL));
                    }

                }
                LOGGER.info(ship + " " + MessageManager.getString(MessageName.HANDLE_SUCCESS));
            } catch (ShipCapacityException e) {
                LOGGER.error(MessageManager.getString(MessageName.ERROR_WITH_SHIP), e);
            } finally {
                docksAccess.release();
            }
        } catch (InterruptedException e) {
            LOGGER.error(MessageManager.getString(MessageName.SHIP_NOT_HANDLED), e);
        }
    }

    public int getDocksNumber() {
        return docksNumber;
    }
}
