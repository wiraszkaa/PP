package Lista10;

public class Counter implements Runnable {
    private final int seconds;
    public Counter(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void run() {
        for (int i = 0; i < seconds; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(i + 1);
        }
    }
}
