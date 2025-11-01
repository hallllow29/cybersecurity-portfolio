package lib.interfaces;

import lib.exceptions.EmptyCollectionException;

/**
 * Defines the abstract data type (ADT) for a heap structure. A heap is a
 * complete binary tree where elements are organized based on a specific
 * heap property (e.g., min-heap or max-heap). This interface extends the
 * BinaryTreeADT interface and specifies operations related to heap management.
 *
 * @param <T> the type of elements held in this heap
 */
public interface HeapADT<T> extends BinaryTreeADT<T> {

    /**
     * Adds the specified element to the heap while maintaining the heap's structure and properties.
     *
     * @param obj the element to be added to the heap
     */
    void addElement (T obj);

    /**
     * Removes and returns the element with the smallest value in this heap.
     *
     * @return the element with the smallest value in this heap
     * @throws EmptyCollectionException if the heap is empty
     */
    T removeMin() throws EmptyCollectionException;

    /**
     * Retrieves the element with the smallest value from this heap without
     * removing it. The operation ensures to maintain the heap properties.
     *
     * @return the element with the smallest value in the heap
     * @throws EmptyCollectionException if the heap is empty
     */
    T findMin() throws EmptyCollectionException;

}
