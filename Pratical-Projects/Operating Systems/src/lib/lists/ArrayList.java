package lib.lists;


import lib.exceptions.EmptyCollectionException;
import lib.interfaces.ListADT;

import java.util.Iterator;

/**
 * ArrayList is an abstract base class that provides a generic implementation
 * of a dynamic array-based list data structure. It implements the ListADT
 * interface and includes methods for managing and manipulating elements
 * within the list. The class supports dynamic expansion when the internal
 * capacity is exceeded and allows generic type elements.
 *
 * @param <T> the type of elements maintained in this list
 */
public abstract class ArrayList<T> implements ListADT<T> {

    /**
     * The initial capacity of the underlying array used to store elements in the ArrayList.
     * Represents the default number of elements that the list can hold before the
     * internal array is resized.
     */
    private static final int INITIAL_CAPACITY = 10;

    /**
     * The EXPANSION_FACTOR is a constant that specifies the multiplier used to increase the
     * capacity of the underlying array when the collection reaches its current maximum capacity.
     * It helps optimize the resizing process by reducing the frequency of array expansions,
     * improving performance during dynamic growth of the collection.
     */
    private static final int EXPANSION_FACTOR = 10;

    /**
     * An array of generic type T used to store the elements of the list.
     * This field represents the underlying dynamic array that holds the elements
     * for the {@code ArrayList} implementation. Its capacity is dynamically adjusted
     * based on the current size of the collection and operations performed.
     * The field is protected, allowing access to subclasses.
     */
    protected T[] list;

    /**
     * Represents the current number of elements stored in the collection.
     * This variable is incremented or decremented as elements are added or removed,
     * and it is used for collection size tracking and boundary validations.
     */
    protected int count;

    /**
     * Constructs an empty ArrayList instance with an initial capacity defined by
     * the class constant INITIAL_CAPACITY. The object array used to store
     * list elements is initialized at this capacity, and the count tracking
     * the number of elements in the list is set to zero.
     */
    public ArrayList() {
        this.list = (T[]) (new Object[INITIAL_CAPACITY]);
        this.count = 0;
    }

    /**
     * Removes and returns the first element in the array list. The remaining
     * elements are shifted one position to the left to maintain the order of
     * the list. If the list is empty, this method throws an EmptyCollectionException.
     *
     * @return the first element that was removed from the array list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The List Is Empty");
        }

        T element = this.list[0];

        for (int i = 0; i < this.count - 1; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[--this.count] = null;
        return element;
    }

    /**
     * Removes and returns the last element in the array list. The last element is removed
     * by setting its position in the internal array to null, and the count of elements is
     * decremented. If the list is empty, this method throws an EmptyCollectionException.
     *
     * @return the last element that was removed from the array list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The List Is Empty");
        }

        T element = this.list[this.count - 1];
        this.list[--this.count] = null;

        return element;
    }

    /**
     * Removes the specified element from the array list. The method shifts all subsequent
     * elements to the left to fill the position of the removed element and decrements the
     * count of elements in the list. If the list is empty or the specified element is not
     * found, the method throws an EmptyCollectionException.
     *
     * @param element the element to be removed from the array list
     * @return the element that was removed from the array list
     * @throws EmptyCollectionException if the list is empty or the specified element does not exist
     */
    @Override
    public T remove(T element) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The List Is Empty");
        }

        int index = this.find(element);

        if (index == -1) {
            throw new EmptyCollectionException("The Element Does Not Exists");
        }

        T elementToRemove = this.list[index];

        for (int i = index; i < this.count - 1; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[this.count--] = null;
        return elementToRemove;
    }

    /**
     * Returns the first element in the array list.
     *
     * @return the first element in the array list
     */
    @Override
    public T first() {
        return this.list[0];
    }

    /**
     * Returns the last element in the array list.
     *
     * @return the last element in the array list
     * @throws EmptyCollectionException if the array list is empty
     */
    @Override
    public T last() {
        return this.list[this.count - 1];
    }

    /**
     * Checks whether the specified element is present in the list.
     *
     * @param target the element to be checked for presence in the list
     * @return true if the element is found in the list, false otherwise
     */
    @Override
    public boolean contains(T target) {
        boolean contains = false;

        for (T element : this.list) {
			if (element.equals(target)) {
				contains = true;
				break;
			}
        }

        return contains;
    }

    /**
     * Retrieves the element at the specified index in the list.
     *
     * @param index the position of the element to retrieve
     * @return the element at the specified index, or null if the index is invalid
     */
    public T getElement(int index) {
        T element = null;

        for (int i = 0; i < this.count; i++) {
            if (i == index) {
                element = this.list[i];
                break;
            }
        }

        return element;
    }

    /**
     * Determines if the list is empty by checking if the element count is zero.
     *
     * @return true if the list contains no elements, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    /**
     * Returns the number of elements currently stored in the array list.
     *
     * @return the size of the array list
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Searches for the specified element in the array list and returns its position.
     * Iterates through the elements in the list, checking for equality with the
     * provided element. If the element is found, its index is returned; otherwise,
     * -1 is returned. This method is used internally by the class.
     *
     * @param element the element to be searched for in the array list
     * @return the index of the element if found, or -1 if the element is not present in the list
     */
    private int find(T element) {
        int position = -1;

        for (int i = 0; i < this.count; i++) {
            if (this.list[i].equals(element)) {
                position = i;
                break;
            }
        }

        return position;
    }


    /**
     * Returns an iterator to traverse the elements of this list in sequence.
     *
     * @return an {@code Iterator<T>} to iterate over the elements in the list
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(this.list, this.count);
    }

    /**
     * Expands the capacity of the underlying array used to store elements in the list.
     * This method creates a new array with a size larger than the current array,
     * determined by multiplying the current size by a predefined expansion factor.
     * The elements from the current array are copied into the new array, and the
     * reference to the old array is replaced with the new expanded array.
     *
     * This method is typically called internally when the list reaches its current
     * capacity, ensuring that additional elements can be stored.
     */
    protected void expandCapacity() {
        T[] temp = (T[]) (new Object[this.count * EXPANSION_FACTOR]);
        System.arraycopy(this.list, 0, temp, 0, this.count);
        this.list = temp;
    }

    /**
     * Returns a string representation of the current elements in the list.
     * Each element is appended to the result string followed by a newline character.
     *
     * @return a string containing all elements in the list, separated by newlines
     */
    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < this.count; i++) {
            s += this.list[i] + "\n";
        }

        return s;
    }
}
