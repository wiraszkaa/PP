package Lista10.Zad1;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class MyThread implements Runnable {
    private final Semaphore mutex = new Semaphore(1);
    private static int number = 0;
    @Override
    public void run() {
        try {
            mutex.acquire();
            randomOperation();
        } catch (InterruptedException e) {
            // exception handling code
        } finally {
            mutex.release();
        }
    }

    private void randomOperation() {
        Random r = new Random();
        if (r.nextBoolean()) {
            number += 1;
        } else {
            number -= 1;
        }
    }

    public static int getNumber() {
        return number;
    }
}
