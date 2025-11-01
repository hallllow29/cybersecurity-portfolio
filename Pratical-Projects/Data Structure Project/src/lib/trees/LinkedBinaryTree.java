package lib.trees;

import lib.BinaryTreeNode;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.BinaryTreeADT;
import lib.lists.LinkedUnorderedList;
import lib.queues.LinkedQueue;

import java.util.Iterator;


public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

	private int count;
	private BinaryTreeNode<T> root;

	public LinkedBinaryTree() {
		this.count = 0;
		root = null;
	}

	public LinkedBinaryTree(T element) {
		count = 1;
		root = new BinaryTreeNode<T>(element);
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public BinaryTreeNode<T> getRoot() {
		return (BinaryTreeNode<T>) this.root;
	}

	public T getRootElement() {
		return this.root.getElement();
	}

	public void setRoot(BinaryTreeNode<T> root) {
		this.root = root;
	}

	@Override
	public boolean isEmpty() {
		return this.count == 0;
	}

	@Override
	public int size() {
		return this.count;
	}

	@Override
	public boolean contains(T target) throws EmptyCollectionException, ElementNotFoundException {
		if (this.isEmpty()) {
            throw new EmptyCollectionException("Binary Tree is empty");
        }

        BinaryTreeNode<T> currentBinaryTreeNode = this.root;

        while (currentBinaryTreeNode != null) {
            int compare = (((Comparable) target).compareTo(currentBinaryTreeNode.getElement()));

            if (compare == 0) {
                return true;
            }

            if (compare < 0) {
                currentBinaryTreeNode = currentBinaryTreeNode.getLeft();
            } else {
                currentBinaryTreeNode = currentBinaryTreeNode.getRight();
            }
        }

        return false;

	}

	@Override
	public T find(T target) throws ElementNotFoundException {
		BinaryTreeNode<T> currentBinaryTreeNode = findAgain(target, this.root);

		if (currentBinaryTreeNode == null) {
			throw new ElementNotFoundException("Element not in Binary Tree");
		}

		return currentBinaryTreeNode.getElement();
	}

	private BinaryTreeNode<T> findAgain(T target, BinaryTreeNode<T> nextBinaryTreeNode) {
		if (nextBinaryTreeNode == null) {
			return null;
		}

		if (nextBinaryTreeNode.getElement().equals(target)){
			return nextBinaryTreeNode;
		}

		BinaryTreeNode<T> tempBinaryTreeNode = findAgain(target, nextBinaryTreeNode.getLeft());

		if (tempBinaryTreeNode == null) {
			tempBinaryTreeNode = findAgain(target, nextBinaryTreeNode.getRight());
		}

		return tempBinaryTreeNode;
	}


	public void inOrder(BinaryTreeNode<T> binaryTreeNode, LinkedUnorderedList<T> linkedList) {
		if (binaryTreeNode != null) {
			inOrder(binaryTreeNode.getLeft(), linkedList);
			linkedList.addToRear(binaryTreeNode.getElement());
			inOrder(binaryTreeNode.getRight(), linkedList);
		}
	}

	@Override
	public Iterator<T> iteratorInOrder() {
		LinkedUnorderedList<T> linkedList = new LinkedUnorderedList<T>();
		inOrder(this.root, linkedList);
		return linkedList.iterator();
	}

	public void postOrder(BinaryTreeNode<T> binaryTreeNode, LinkedUnorderedList<T> linkedList) {
		if (binaryTreeNode != null) {
			postOrder(binaryTreeNode.getLeft(), linkedList);
			postOrder(binaryTreeNode.getRight(), linkedList);
			linkedList.addToRear(binaryTreeNode.getElement());
		}
	}

	@Override
	public Iterator<T> iteratorPostOrder() {
		LinkedUnorderedList<T> linkedList = new LinkedUnorderedList<>();
		postOrder(this.root, linkedList);
		return linkedList.iterator();
	}

	public void preOrder(BinaryTreeNode<T> binaryTreeNode, LinkedUnorderedList<T> linkedList) {
		if (binaryTreeNode != null) {
			linkedList.addToRear(binaryTreeNode.getElement());
			preOrder(binaryTreeNode.getLeft(), linkedList);
			preOrder(binaryTreeNode.getRight(), linkedList);
		}
	}

	@Override
	public Iterator<T> iteratorPreOrder() {
		LinkedUnorderedList<T> linkedList = new LinkedUnorderedList<T>();
		preOrder(this.root, linkedList);
		return linkedList.iterator();

	}

	@Override
	public Iterator<T> iteratorLevelOrder() throws EmptyCollectionException {
		LinkedQueue<T> binaryNodes = new LinkedQueue<T>();
        LinkedUnorderedList<T> results = new LinkedUnorderedList<T>();

        binaryNodes.enqueue(this.root.getElement());

        while (!binaryNodes.isEmpty()) {
            BinaryTreeNode<T> binaryNode = (BinaryTreeNode<T>) binaryNodes.dequeue();
            if (binaryNode != null) {
                results.addToRear(binaryNode.getElement());

                if (binaryNode.getLeft() != null) {
                    binaryNodes.enqueue(binaryNode.getLeft().getElement());
                }

                if (binaryNode.getRight() != null) {
                    binaryNodes.enqueue(binaryNode.getRight().getElement());
                }

            } else {
                results.addToRear(null);
            }
        }
        return results.iterator();
	}
}
