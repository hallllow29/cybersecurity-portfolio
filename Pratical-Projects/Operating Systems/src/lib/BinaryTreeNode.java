package lib;

/**
 * Represents a node in a binary tree structure.
 * Each node contains an element of a generic type, as well as references to
 * its left and right child nodes.
 *
 * @param <T> The type of the element stored in this node.
 */
public class BinaryTreeNode <T> {

	/**
	 * The element stored in the binary tree node.
	 * Represents the value or data encapsulated by this node.
	 */
	private T element;

	/**
	 * Holds a reference to the left child node in this binary tree structure.
	 *
	 * This field represents the left subtree of the current node.
	 * It may either point to another BinaryTreeNode object containing data
	 * and possibly further children, or it may be null, indicating the
	 * absence of a left child.
	 */
	private  BinaryTreeNode<T> left;

	/**
	 * Represents the right child node of this binary tree node.
	 * It may reference another BinaryTreeNode<T> instance or be null
	 * if no right child node exists.
	 */
	private  BinaryTreeNode<T> right;

	/**
	 * Constructs a new BinaryTreeNode with the specified element.
	 *
	 * @param element The element to be stored in this binary tree node.
	 */
	public BinaryTreeNode(T element) {
		this.element = element;
		this.left = null;
		this.right = null;
	}

	/**
	 * Constructs a new BinaryTreeNode with the specified element and child nodes.
	 *
	 * @param element The element to be stored in the node.
	 * @param left The left child node of this node, or null if no left child exists.
	 * @param right The right child node of this node, or null if no right child exists.
	 */
	public BinaryTreeNode(T element, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
		this.element = element;
		this.left = left;
		this.right = right;
	}

	/**
	 * Retrieves the element stored in this binary tree node.
	 *
	 * @return The element stored in this binary tree node.
	 */
	public T getElement() {
		return this.element;
	}

	/**
	 * Sets the element stored in this binary tree node.
	 *
	 * @param element The element to be stored in this node.
	 */
	public void setElement(T element) {
		this.element = element;
	}

	/**
	 * Retrieves the left child node of this binary tree node.
	 *
	 * @return The left child node of this binary tree node, or null if no left child exists.
	 */
	public BinaryTreeNode<T> getLeft() {
		return this.left;
	}

	/**
	 * Sets the left child node of this binary tree node.
	 *
	 * @param leftBinaryTreeNode The node to be set as the left child of this node.
	 */
	public void setLeft(BinaryTreeNode<T> leftBinaryTreeNode) {
		this.left = leftBinaryTreeNode;
	}

	/**
	 * Retrieves the right child node of this binary tree node.
	 *
	 * @return The right child node of this binary tree node, or null if no right child exists.
	 */
	public BinaryTreeNode<T> getRight() {
		return this.right;
	}

	/**
	 * Sets the right child node of this binary tree node.
	 *
	 * @param rightBinaryTreeNode The node to be set as the right child of this node.
	 */
	public void setRight(BinaryTreeNode<T> rightBinaryTreeNode) {
		this.right = rightBinaryTreeNode;
	}

	/**
	 * Calculates the total number of descendants (children) of this node
	 * in the binary tree, including all nodes in the left and right subtrees.
	 *
	 * @return The total number of children nodes in the left and right subtrees.
	 */
	public int numChildren() {
		int children = 0;
		if (this.left != null) {
			children = 1 + this.left.numChildren();
		}

		if (this.right != null) {
			children = children + 1 + this.right.numChildren();
		}
		return children;
	}

}
