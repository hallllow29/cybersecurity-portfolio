/**
 * @author 8230068, 8230069
**/

package lib.interfaces;


import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

/**
 * The BinarySearchTreeADT interface defines the structure and behavior of a binary search tree
 * with generic elements, extending the base operations provided by BinaryTreeADT.
 *
 * This interface includes methods for adding, removing, and searching elements in a way
 * that maintains the properties of a binary search tree, such as ordering and hierarchy of elements.
 *
 * Specific error handling is provided through custom exceptions for operations involving
 * empty collections or elements that cannot be found.
 *
 * @param <T> the type of elements stored in the binary search tree, which must be comparable
 */
public interface BinarySearchTreeADT<T> extends BinaryTreeADT<T> {

	/**
	 * Adds the specified element to the binary search tree in its appropriate position.
	 *
	 * @param element the element to be added to the binary search tree
	 */
	void addElement(T element);

	/**
	 * Removes the specified target element from the binary search tree and returns it.
	 * If the target element is not found in the tree, an ElementNotFoundException is thrown.
	 *
	 * @param target the element to be removed from the binary search tree
	 * @return the removed element from the binary search tree
	 * @throws ElementNotFoundException if the specified target element is not found in the binary search tree
	 */
	T removeElement(T target) throws ElementNotFoundException;

	/**
	 * Removes all occurrences of the specified element from the binary search tree.
	 * If the binary search tree is empty, an EmptyCollectionException is thrown.
	 * If the specified element is not found, an ElementNotFoundException is thrown.
	 *
	 * @param targetElement the element whose all occurrences should be removed from the binary search tree
	 * @throws EmptyCollectionException if the binary search tree is empty
	 * @throws ElementNotFoundException if the specified element is not found in the binary search tree
	 */
	void removeAllOccurrences(T targetElement) throws EmptyCollectionException, ElementNotFoundException;

	/**
	 * Removes and returns the minimum element from the binary search tree.
	 *
	 * @return the minimum element in the binary search tree
	 * @throws EmptyCollectionException if the binary search tree is empty
	 * @throws ElementNotFoundException if the minimum element is not found
	 */
	T removeMin() throws EmptyCollectionException, ElementNotFoundException;

	/**
	 * Removes and returns the maximum element in the binary search tree.
	 *
	 * @return the maximum element in the binary search tree
	 * @throws EmptyCollectionException if the binary search tree is empty
	 * @throws ElementNotFoundException if the maximum element is not found
	 */
	T removeMax() throws EmptyCollectionException, ElementNotFoundException;

	/**
	 * Finds and returns the minimum element in the binary search tree.
	 *
	 * @return the minimum element in the binary search tree
	 * @throws EmptyCollectionException if the binary search tree is empty
	 */
	T findMin() throws EmptyCollectionException;

	/**
	 * Finds and returns the maximum element in the binary search tree.
	 *
	 * @return the maximum element in the binary search tree
	 * @throws EmptyCollectionException if the binary search tree is empty
	 */
	T findMax() throws EmptyCollectionException;
}
