

public class Main {
    static class EmptyException extends Exception {
        public EmptyException() {
            super();
        }
        public EmptyException(String s) {
            super(s);
        }
    }
    static class FullException extends Exception {
        public FullException() {
            super();
        }
        public FullException(String s) {
            super(s);
        }
    }

    public static void main(String[] args) throws FullException, EmptyException {
        int size = 3;
        MyQueue<Integer> queue = new Queue<>(size);
        System.out.println("Empty: " + queue.isEmpty());
        queue.enqueue(1);
        queue.enqueue(2);
        System.out.println("Full: " + queue.isFull());
        queue.enqueue(3);
        System.out.println("Full: " + queue.isFull());
        System.out.println(queue.first());
        queue.dequeue();
        System.out.println(queue.first());
    }
}
