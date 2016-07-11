package by.epam.port.entity;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Warehouse {
    private Semaphore loadingSemaphore;
    private Semaphore unloadingSemaphore;

    public Warehouse(int capacity) {
        this.loadingSemaphore = new Semaphore(0);
        this.unloadingSemaphore = new Semaphore(capacity);

    }

    public boolean addGoods(int value) {
        try {
            if (unloadingSemaphore.tryAcquire(value, 10, TimeUnit.SECONDS)) {
                loadingSemaphore.release(value);
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            return false;
        }
    }

    public boolean getGoods(int value) {
        try {
            if (loadingSemaphore.tryAcquire(value, 10, TimeUnit.SECONDS)) {
                unloadingSemaphore.release(value);
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            return false;
        }
    }
}
