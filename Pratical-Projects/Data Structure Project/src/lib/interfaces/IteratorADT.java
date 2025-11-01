/**
 * @author 8230068, 8230069
**/

package lib.interfaces;

/**
 * An interface representing an abstract data type for an iterator
 * that allows traversal over a collection of elements of type E.
 * Provides methods to check the availability of the next element,
 * retrieve the next element, and optionally remove elements from
 * the underlying collection.
 */
public interface IteratorADT <E> {

	/**
	 * Returns true if the iteration has more elements.
	 *
	 * @return true if the iteration has more elements
	 */
	boolean hasNext();

	/**
	 * Returns the next element in the iteration.
	 *
	 * @return the next element in the iteration
	 */
	E next();

	/**
	 * Removes from the underlying collection the last element returned by this iterator.
	 * This method can be called only once per call to next(). The behavior of an iterator
	 * is unspecified if the underlying collection is modified while the iteration is in
	 * progress in any way other than by calling this method.
	 *
	 * This implementation throws UnsupportedOperationException.
	 *
	 * @throws UnsupportedOperationException if the remove operation is not supported by this iterator
	 */
	default void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
