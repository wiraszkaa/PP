import java.util.ArrayList;

public class Queue<E> implements MyQueue<E> {
    private int f;
    private int r;
    private final ArrayList<E> arr;

    public Queue(int length) {
        arr = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            arr.add(null);
        }
    }

    @Override
    public void enqueue(E x) throws Main.FullException {
        if (isFull()) {
            throw new Main.FullException("Queue is Full");
        }
        arr.set(r, x);
        r = (r + 1) % arr.size();
    }

    @Override
    public void dequeue() {
        arr.set(f, null);
        f = (f + 1) % arr.size();
    }

    @Override
    public E first() throws Main.EmptyException {
        if (isEmpty()) {
            throw new Main.EmptyException("Queue is Empty");
        }
        return arr.get(f);
    }

    @Override
    public boolean isEmpty() {
        return (arr.get(f) == null);
    }

    @Override
    public boolean isFull() {
        return (!isEmpty() && r == f);
    }
}
