package Lista10.Zad2;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Client implements Runnable {
    private boolean printDetails;
    private final Semaphore mutex = new Semaphore(1);
    private final int clientId;
    private final Bank bank;

    public Client(int id, Bank bank) {
        clientId = id;
        printDetails = false;
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
        switch (r.nextInt(3)) {
            case 0:
                if (bank.addMoney(this, value) && printDetails) {
                        System.out.printf("%s || %.2fzł ADD || %.2fzł\n", getId(), value, bank.getBalance(getId()));
                } else if (printDetails) {
                    System.out.printf("%s || ADD ERROR || %.2fzł\n", getId(), value);
                }
                break;
            case 1:
                if (bank.withdrawMoney(this, value) && printDetails) {
                    System.out.printf("%s || %.2fzł WITHDRAW || %.2fzł\n", getId(), value, bank.getBalance(getId()));
                } else if (printDetails) {
                    System.out.printf("%s || WITHDRAW ERROR || %.2fzł > %.2fzł\n", getId(), value, bank.getBalance(getId()));
                }
            default:
                int receiverId = r.nextInt(bank.getClientsNumber());
                if (bank.transferMoney(this, receiverId, value) && printDetails) {
                    System.out.printf("%s -> %s || %.2fzł TRANSFER || %.2fzł | %.2fzł\n", getId(), receiverId, value, bank.getBalance(getId()), bank.getBalance(receiverId));
                } else if (printDetails) {
                    System.out.printf("%s -> %s || TRANSFER ERROR || %.2fzł > %.2fzł\n", getId(), receiverId, value, bank.getBalance(getId()));
                }
        }
    }

    public int getId() {
        return clientId;
    }

    public void togglePrintDetails() {
        printDetails = !printDetails;
    }
}
