
package lib.lists;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * A generic iterator for an array that allows traversal over the array's elements.
 * This iterator provides sequential access to elements stored in a given array up to a specified size.
 *
 * @param <T> the type of elements returned by this iterator
 */
public class ArrayIterator<T> implements Iterator<T> {

    /**
     * Represents the maximum number of elements in the collection
     * that the iterator is allowed to traverse.
     * Acts as the boundary for iteration, ensuring that the iterator
     * does not access elements outside defined limits.
     */
    private int size;

    /**
     * Tracks the current position of the iterator during traversal of the array.
     * Begins at 0 when the iterator is initialized and increments sequentially
     * as elements are accessed. Used to determine the next element
     * to be returned by the iterator and ensure the traversal respects the boundaries
     * established by the size of the collection.
     */
    private int current;

    /**
     * An array holding elements over which the iterator operates.
     * This generic array serves as the data source for iteration, providing sequential access
     * to its elements up to the specified size constraint.
     */
    private T[] items;

    /**
     * Constructs an ArrayIterator object that allows sequential traversal of elements
     * in the specified array up to the given size.
     *
     * @param items an array of elements that the iterator will traverse
     * @param size the maximum number of elements in the array that can be iterated over
     */
    public ArrayIterator(T[] items, int size) {
        this.items = items;
        this.size = size;
        this.current = 0;
    }

    /**
     * Checks if there are more elements available in the iteration.
     * This method determines whether the iterator can produce additional elements
     * by verifying if the current position is less than the defined size boundary.
     *
     * @return true if there are remaining elements to iterate over; false otherwise
     */
    @Override
    public boolean hasNext() {
        return this.current < this.size;
    }

    /**
     * Returns the next element in the iteration and advances the iterator's position.
     * This method retrieves the current element at the iterator's position and then advances
     * the position for subsequent calls. If there are no elements left to iterate, this method
     * throws a {@code NoSuchElementException}.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if no more elements are available for iteration
     */
    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements to iterate");
        }

        return this.items[this.current++];
    }

}
