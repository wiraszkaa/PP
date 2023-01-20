package Lista10.Zad2;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Client implements Runnable {
    private final Semaphore mutex = new Semaphore(1);
    private final int clientId;
    private final Bank bank;
    public Client(int id, Bank bank) {
        clientId = id;
        this.bank = bank;
    }

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
        double value =  Math.round(r.nextDouble() * 100000) / 100.0;
        if (r.nextBoolean()) {
            bank.addMoney(this, value);
        } else {
            bank.withdrawMoney(this, value);
        }
    }

    public int getId() {
        return clientId;
    }
}
