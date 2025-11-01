package lib;

/**
 * Represents a node in a heap structure, extending the functionality of a binary tree node.
 * Each HeapNode contains a reference to its parent node in addition to the
 * basic properties of a binary tree node (element, left child, right child).
 *
 * @param <T> The type of the element stored in the heap node.
 */
public class HeapNode<T> extends BinaryTreeNode<T> {

	/**
	 * Holds a reference to the parent node of this heap node.
	 *
	 * This field is used to track the parent in the heap structure,
	 * enabling upward navigation within the tree hierarchy.
	 * It may either point to another HeapNode containing data
	 * and possibly further relations, or it may be null if this
	 * node is the root of the heap.
	 */
	protected HeapNode<T> parent;

	/**
	 * Constructs a new HeapNode with the specified element.
	 * Initializes the parent of this node to null and invokes the constructor
	 * of the superclass (BinaryTreeNode) to set the element.
	 *
	 * @param element The element to be stored in this heap node.
	 */
	public HeapNode(T element) {
		super(element);
		this.parent = null;
	}
}
