public interface MyQueue<E> {
    void enqueue(E x) throws Main.FullException;

    void dequeue();

    E first() throws Main.EmptyException;

    boolean isEmpty();

    boolean isFull();
}