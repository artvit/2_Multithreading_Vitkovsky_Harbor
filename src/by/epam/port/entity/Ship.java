package by.epam.port.entity;

import by.epam.port.action.ShipIdGenerator;
import by.epam.port.exception.ShipCapacityException;
import by.epam.port.util.MessageName;
import by.epam.port.util.MessageManager;

import java.util.Objects;

public class Ship implements Runnable {
    private long shipId;
    private int containersCapacity;
    private int loadingContainersNumber;
    private int unloadingContainersNumber;
    private Harbor harbor;

    public Ship(int containersCapacity, int loadingContainersNumber, int unloadingContainersNumber) {
        this.shipId = ShipIdGenerator.nextId();
        this.containersCapacity = containersCapacity;
        this.loadingContainersNumber = loadingContainersNumber;
        this.unloadingContainersNumber = unloadingContainersNumber;
    }

    public Ship(int containersCapacity, int loadingContainersNumber, int unloadingContainersNumber, Harbor harbor) {
        this(containersCapacity, loadingContainersNumber, unloadingContainersNumber);
        this.harbor = harbor;
    }

    @Override
    public void run() {
        if (harbor != null) {
            harbor.handleShip(this);
        }
    }

    public long getShipId() {
        return shipId;
    }

    public int getContainersCapacity() {
        return containersCapacity;
    }

    public void setContainersCapacity(int containersCapacity) {
        this.containersCapacity = containersCapacity;
    }

    public int getLoadingContainersNumber() {
        return loadingContainersNumber;
    }

    public void setLoadingContainersNumber(int loadingContainersNumber) throws ShipCapacityException {
        if (loadingContainersNumber > this.containersCapacity) {
            throw new ShipCapacityException(MessageManager.getString(MessageName.WRONG_CONTAINERS_NUMBER));
        }
        this.loadingContainersNumber = loadingContainersNumber;
    }

    public int getUnloadingContainersNumber() {
        return unloadingContainersNumber;
    }

    public void setUnloadingContainersNumber(int unloadingContainersNumber) throws ShipCapacityException {
        if (unloadingContainersNumber > this.containersCapacity) {
            throw new ShipCapacityException(MessageManager.getString(MessageName.WRONG_CONTAINERS_NUMBER));
        }
        this.unloadingContainersNumber = unloadingContainersNumber;
    }

    public Harbor getHarbor() {
        return harbor;
    }

    public void setHarbor(Harbor harbor) {
        this.harbor = harbor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ship)) {
            return false;
        }
        Ship ship = (Ship) o;
        return getShipId() == ship.getShipId() &&
                getContainersCapacity() == ship.getContainersCapacity() &&
                getLoadingContainersNumber() == ship.getLoadingContainersNumber() &&
                getUnloadingContainersNumber() == ship.getUnloadingContainersNumber() &&
                Objects.equals(harbor, ship.harbor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShipId(), getContainersCapacity(), getLoadingContainersNumber(), getUnloadingContainersNumber(), harbor);
    }

    @Override
    public String toString() {
        return MessageManager.getString(MessageName.SHIP) + "{" +
                MessageManager.getString(MessageName.ID) + "=" + shipId +
                ", " + MessageManager.getString(MessageName.CAPACITY) + "=" + containersCapacity +
                ", " + MessageManager.getString(MessageName.UNLOADING) + "=" + unloadingContainersNumber +
                ", " + MessageManager.getString(MessageName.LOADING) + "=" + loadingContainersNumber +
                '}';
    }
}
