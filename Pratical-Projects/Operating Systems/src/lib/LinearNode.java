package lib;


/**
 * Represents a node in a linked data structure where each node contains an element and a
 * reference to the next node in the sequence.
 *
 * @param <T> the type of element held in this node
 */
public class LinearNode <T> {

	private T element;
	private LinearNode<T> next;

	/**
	 * Constructs an empty LinearNode with no element and no next node.
	 */
	public LinearNode() {
		this.element = null;
		this.next = null;
	}

	/**
	 * Constructs a new LinearNode with the specified element.
	 *
	 * @param element the element to be stored in this node
	 */
	public LinearNode(T element) {
		this.element = element;
		this.next = null;
	}

	/**
	 * Returns the next node in the linked list.
	 *
	 * @return the next node in the linked list, or null if there is no next node
	 */
	public LinearNode<T> getNext() {
		return this.next;
	}

	/**
	 * Sets the next node in the linked list.
	 *
	 * @param lnode the node to set as the next node
	 */
	public void setNext(LinearNode<T> lnode) {
		this.next = lnode;
	}

	/**
	 * Returns the element stored in this node.
	 *
	 * @return the element stored in this node
	 */
	public T getElement() {
		return this.element;
	}

	/**
	 * Sets the element stored in this node.
	 *
	 * @param element the element to be stored in this node
	 */
	public void setElement(T element) {
		this.element = element;
	}
}