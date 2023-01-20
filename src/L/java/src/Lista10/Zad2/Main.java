package Lista10.Zad2;

import Lista10.Counter;
import Lista10.Zad1.MyThread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        performRandomOperations(10, 5);
    }

    private static void performRandomOperations(int clients, int seconds) {
        ExecutorService executor = Executors.newFixedThreadPool(clients + 1);
        Bank bank = new Bank();
        Random r = new Random();
        for (int i = 0; i < clients - 1; i++) {
            Client client = new Client(i, bank);
            bank.addClient(client.getId(), r.nextInt(1000));
            executor.execute(client);
        }
        executor.execute(new Counter(seconds - 1));

        executor.shutdown();

        try {
            Thread.sleep(seconds * 1000L);
        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }

        bank.printClientsBalance();
    }
}
