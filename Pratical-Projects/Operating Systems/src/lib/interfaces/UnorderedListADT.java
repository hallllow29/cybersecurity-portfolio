package lib.interfaces;


import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

public interface UnorderedListADT<T> extends ListADT<T> {

	/**
	 * Adds the specified element to the front of this list.
	 *
	 * @param element the element to be added to the front of the list
	 */
	public void addToFront(T element);

	/**
	 * Adds the specified element to the rear of this list.
	 *
	 * @param element the element to be added to the rear of the list
	 */
	public void addToRear(T element);

	/**
	 * Adds the specified element immediately after the specified target element
	 * in this list. If the target element is not found, an ElementNotFoundException
	 * is thrown.
	 *
	 * @param element the element to be added after the target
	 * @param target the element after which the new element will be added
	 * @throws ElementNotFoundException if the target element is not found in the list
	 */
	public void addAfter(T element, T target) throws ElementNotFoundException, EmptyCollectionException;

}
