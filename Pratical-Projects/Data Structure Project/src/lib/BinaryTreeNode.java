package lib;

public class BinaryTreeNode <T> {
	private T element;
	private  BinaryTreeNode<T> left;
	private  BinaryTreeNode<T> right;

	public BinaryTreeNode(T element) {
		this.element = element;
		this.left = null;
		this.right = null;
	}

	public BinaryTreeNode(T element, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
		this.element = element;
		this.left = left;
		this.right = right;
	}


	public T getElement() {
		return this.element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public BinaryTreeNode<T> getLeft() {
		return this.left;
	}

	public void setLeft(BinaryTreeNode<T> leftBinaryTreeNode) {
		this.left = leftBinaryTreeNode;
	}

	public BinaryTreeNode<T> getRight() {
		return this.right;
	}

	public void setRight(BinaryTreeNode<T> rightBinaryTreeNode) {
		this.right = rightBinaryTreeNode;
	}

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
