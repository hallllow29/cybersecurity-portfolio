/**
 * @author 8230068, 8230069
**/

package lib.interfaces;

import lib.BinaryTreeNode;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;

/**
 * Represents a generic abstract data type (ADT) for a binary tree. This
 * interface defines the essential operations that any binary tree implementation
 * must support, such as traversal, element retrieval, and structural queries.
 *
 * @param <T> the type of elements stored in the binary tree
 */
public interface BinaryTreeADT<T> {

	/**
	 * Retrieves the root node of the binary tree.
	 *
	 * @return the root node of the binary tree, or null if the tree is empty
	 */
	BinaryTreeNode<T> getRoot();

	/**
	 * Retrieves the element stored in the root node of the binary tree.
	 *
	 * @return the element held by the root node of the binary tree
	 */
	T getRootElement();

	/**
	 * Checks whether the binary tree contains no elements.
	 *
	 * @return true if the binary tree is empty, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Returns the total number of nodes in the binary tree.
	 *
	 * @return the number of nodes present in the binary tree
	 */
	int size();

	/**
	 * Checks if the specified target element exists in the binary tree.
	 *
	 * @param target the element to search for in the binary tree
	 * @return true if the target element exists in the binary tree, false otherwise
	 * @throws EmptyCollectionException if the binary tree is empty
	 * @throws ElementNotFoundException if the target element is not found in the binary tree
	 */
	boolean contains(T target) throws EmptyCollectionException, ElementNotFoundException;

	/**
	 * Searches for the specified target element in the binary tree and returns it if found.
	 *
	 * @param target the element to be searched within the binary tree
	 * @return the target element if it is found in the binary tree
	 * @throws ElementNotFoundException if the target element is not found in the binary tree
	 */
	T find(T target) throws ElementNotFoundException;

	/**
	 * Returns a string representation of the binary tree. The returned string
	 * provides a textual representation of the tree's structure and elements.
	 *
	 * @return a string representation of the binary tree
	 */
	String toString();

	/**
	 * Returns an iterator that performs an in-order traversal on the elements of the binary tree.
	 * In-order traversal processes the nodes of a binary tree in the order:
	 * left subtree, root node, and then the right subtree.
	 *
	 * @return an iterator of type T that traverses the binary tree in in-order
	 */
	Iterator<T> iteratorInOrder();

	/**
	 * Returns an iterator that performs a pre-order traversal on the elements of the binary tree.
	 * Pre-order traversal processes the nodes of a binary tree in the order:
	 * root node, left subtree, and then the right subtree.
	 *
	 * @return an iterator of type T that traverses the binary tree in pre-order
	 */
	Iterator<T> iteratorPreOrder();

	/**
	 * Returns an iterator that performs a post-order traversal on the elements of the binary tree.
	 * Post-order traversal processes the nodes of a binary tree in the order:
	 * left subtree, right subtree, and then the root node of each subtree.
	 *
	 * @return an iterator of type T that traverses the binary tree in post-order
	 */
	Iterator<T> iteratorPostOrder();

	/**
	 * Returns an iterator that performs a level-order traversal on the elements of the binary tree.
	 * Level-order traversal processes all nodes at each depth level from left to right before moving to the next level.
	 *
	 * @return an iterator of type T that traverses the binary tree in level-order
	 * @throws EmptyCollectionException if the binary tree is empty
	 */
	Iterator<T> iteratorLevelOrder() throws EmptyCollectionException;
}
