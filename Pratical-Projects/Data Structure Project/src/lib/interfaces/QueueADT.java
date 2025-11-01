package lib.interfaces;

import lib.exceptions.EmptyCollectionException;

public interface QueueADT<T> {

    /**
     * Adds one element to the rear of this queue.
     *
     * @param element the element to be added to the rear of the queue
     */
    void enqueue(T element);

    /**
     * Removes and returns the element at the front of this queue.
     *
     * @return the element at the front of the queue
     * @throws EmptyCollectionException if the queue is empty
     */
    T dequeue() throws EmptyCollectionException;

    /**
     * Returns without removing the element at the front of this queue.
     *
     * @return the first element in the queue
     * @throws EmptyCollectionException if the queue is empty
     */
    T first() throws EmptyCollectionException;

    /**
     * Returns true if this queue contains no elements.
     *
     * @return true if this queue is empty
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in this queue.
     *
     * @return the integer representation of the size of the queue
     */
    int size();
}