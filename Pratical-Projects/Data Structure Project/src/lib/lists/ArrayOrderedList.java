package lib.lists;

import lib.interfaces.OrderedListADT;

/**
 * ArrayOrderedList represents an array-based implementation of an ordered list
 * where elements are automatically arranged in their natural order as defined
 * by their {@code Comparable} interface.
 *
 * @param <T> the type of elements held in this list; must implement {@code Comparable}
 */
public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    /**
     * Adds the specified element to this ordered list while maintaining its natural order.
     * The element must implement the {@code Comparable} interface to determine its relative position within the list.
     * If the underlying array is full, the capacity is expanded before adding the new element.
     *
     * @param element the element to be added to the ordered list; must be non-null and implement {@code Comparable}
     * @throws IllegalArgumentException if the specified element does not implement {@code Comparable}
     */
    @Override
    public void add(T element) {
        if (!(element instanceof Comparable)) {
            throw new IllegalArgumentException("Element must be Comparable.");
        }

        if (this.count == this.list.length) {
            this.expandCapacity();
        }

        Comparable<T> comparableElement = (Comparable<T>) element;

        int position = 0;
        while (position < this.count && comparableElement.compareTo(this.list[position]) > 0) {
            position++;
        }

        for (int i = this.count; i > position; i--) {
            this.list[i] = this.list[i - 1];
        }

        this.list[position] = element;
        this.count++;
    }

}