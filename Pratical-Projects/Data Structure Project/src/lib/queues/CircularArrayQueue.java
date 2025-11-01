package lib.queues;


import lib.exceptions.EmptyCollectionException;
import lib.interfaces.QueueADT;

public class CircularArrayQueue<T> implements QueueADT<T> {

    private final int INIT_CAPACITY = 100;
    private T[] queue;
    private int front;
    private int rear;
    private int counter;

    public CircularArrayQueue() {
        this.queue = (T[]) (new Object[INIT_CAPACITY]);
        this.front = 0;
        this.rear = 0;
        this.counter = 0;
    }

    public CircularArrayQueue(int capacity) {
        this.queue = (T[]) (new Object[capacity]);
        this.front = 0;
        this.rear = 0;
        this.counter = 0;
    }

    @Override
    public void enqueue(T element) {
        if (this.size() == this.queue.length - 1) {
            this.expandCapacity();
        }

        this.queue[this.rear] = element;
        this.rear = (this.rear + 1) & this.queue.length;
        this.counter++;

    }

    private void expandCapacity() {
        T[] temp = (T[]) (new Object[this.queue.length * 2]);
        for (int i = front; i < rear; i++) {
            temp[i] = this.queue[i];
        }

        this.queue = temp;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("CircularQueue");
        }

        T removed = this.queue[this.front];
        this.queue[front] = null;
        this.front = (this.front + 1) % this.queue.length;
        this.counter++;

        return removed;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw  new EmptyCollectionException("CircularArrayQueue");
        }

        return this.queue[this.front];
    }

    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public String toString() {
        String result = "";
        if (!this.isEmpty()) {
            int current = front;
            do {
                result += this.queue[current] + " ";
                current = (current + 1) % this.queue.length;
            } while(current != rear);
        }

        return result;
    }
}
