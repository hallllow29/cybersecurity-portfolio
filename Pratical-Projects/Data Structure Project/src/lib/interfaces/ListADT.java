/**
 * @author 8230068, 8230069
**/

package lib.interfaces;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import java.util.Iterator;

/**
 * A generic interface representing the abstract data type for a list. A list is an
 * ordered collection that allows duplicate elements and supports positional access
 * and modifications.
 *
 * @param <T> the type of elements maintained by this list
 */
public interface ListADT<T> extends Iterable<T> {

	/**
	 * Removes the specified element from the list.
	 *
	 * @param target the element to be removed from the list
	 * @return the removed element
	 * @throws EmptyCollectionException if the list is empty
	 * @throws ElementNotFoundException if the target element is not found in the list
	 */
	T remove(T target) throws EmptyCollectionException, ElementNotFoundException;

	/**
	 * Returns the first element in the list.
	 *
	 * @return the first element in the list
	 * @throws EmptyCollectionException if the list is empty
	 */
	T first() throws EmptyCollectionException;

	/**
	 * Returns the last element in the list.
	 *
	 * @return the last element in the list
	 * @throws EmptyCollectionException if the list is empty
	 */
	T last() throws EmptyCollectionException;

	/**
	 * Removes and returns the first element from the list.
	 *
	 * @return the first element from the list
	 * @throws EmptyCollectionException if the list is empty
	 */
	T removeFirst() throws EmptyCollectionException;

	/**
	 * Removes and returns the last element from the list.
	 *
	 * @return the last element from the list
	 * @throws EmptyCollectionException if the list is empty
	 */
	T removeLast() throws EmptyCollectionException;

	/**
	 * Checks if the specified element is present in the list.
	 *
	 * @param element the element to be checked for presence in the list
	 * @return true if the list contains the specified element, false otherwise
	 *
	 * @throws EmptyCollectionException if the list is empty
	 */
	boolean contains(T element) throws EmptyCollectionException;

	/**
	 * Checks if the list is empty.
	 *
	 * @return true if the list contains no elements, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the list.
	 *
	 * @return the number of elements in the list.
	 */
	int size();

	/**
	 * Returns an iterator over the elements in this collection. The iterator provides
	 * sequential access to the elements in the order they appear in the collection.
	 *
	 * @return an iterator over the elements in this collection
	 */
	Iterator<T> iterator();

	/**
	 * Returns a string representation of the list. The format and content of the
	 * resulting string may vary based on the specific implementation of the list.
	 *
	 * @return a string representation of the list
	 */
	String toString();

}
