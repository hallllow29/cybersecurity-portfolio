/**
 * @author 8230068, 8230069
**/

package lib.interfaces;

import lib.exceptions.EmptyCollectionException;

/**
 * The SmackStackADT interface defines the contract for a stack-like data structure
 * with an additional operation, smack, that removes and returns the second element
 * from the top of the stack without modifying the top element. This extends the
 * traditional stack operations of push, pop, peek, isEmpty, size, and toString.
 *
 * @param <T> the type of elements stored in the stack
 */
public interface SmackStackADT<T> {

    /**
     * Removes and returns the second element from the top of the stack,
     * if it exists. This operation does not affect the top element of the stack.
     *
     * @return the second element from the top of the stack
     * @throws EmptyCollectionException if the stack does not contain at least two elements
     */
    T smack() throws EmptyCollectionException;

    /**
     * Adds the specified element to the top of the stack.
     *
     * @param element the element to be added to the stack
     */
    void push(T element);

    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return the element that was removed from the top of the stack
     * @throws EmptyCollectionException if the stack is empty
     */
    T pop() throws EmptyCollectionException;

    /**
     * Retrieves, but does not remove, the element at the top of the stack.
     *
     * @return the element at the top of the stack
     * @throws EmptyCollectionException if the stack is empty
     */
    T peek() throws EmptyCollectionException;

    /**
     * Determines whether the stack is empty.
     *
     * @return true if the stack contains no elements, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements currently stored in the stack.
     *
     * @return the total number of elements in the stack
     */
    int size();

    /**
     * Provides a string representation of the stack, showing the elements in their
     * current order from top to bottom.
     *
     * @return a string representation of the stack's contents
     */
    String toString();
}