package Lista10.Zad1;

import Lista10.Counter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        performRandomOperations(10, 3);
    }

    private static void performRandomOperations(int threads, int seconds) {
        ExecutorService executor = Executors.newFixedThreadPool(threads + 1);

        for (int i = 0; i < threads; i++) {
            executor.execute(new MyThread());
        }
        executor.execute(new Counter(seconds - 1));

        executor.shutdown();

        try {
            Thread.sleep(seconds * 1000L);
        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.printf("Global Number after %ss is %s\n", seconds, MyThread.getNumber());
    }
}