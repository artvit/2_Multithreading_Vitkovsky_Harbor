package by.epam.port.action;

import by.epam.port.entity.Ship;
import by.epam.port.exception.ShipCapacityException;
import by.epam.port.util.MessageManager;
import by.epam.port.util.MessageName;

public class ShipCreator {
    public static Ship createShip(int maxCapacity, int loading, int unloading) throws ShipCapacityException {
        if (loading > maxCapacity || unloading > maxCapacity ||
                maxCapacity < 0 || loading < 0 || unloading < 0) {
            throw new ShipCapacityException(MessageManager.getString(MessageName.WRONG_CONTAINERS_NUMBER));
        }
        return new Ship(maxCapacity, loading, unloading);
    }
}
