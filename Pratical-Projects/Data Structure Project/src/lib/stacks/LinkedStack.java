package lib.stacks;

import lib.Node;
import lib.exceptions.EmptyCollectionException;

import lib.interfaces.StackADT;

public class LinkedStack<T> implements StackADT<T> {

	private Node<T> top;
	private int size;

	public LinkedStack() {
		this.size = 0;
		this.top = null;
	}

	public void push(T data) {
		Node<T> newNode = new Node<>(data);
		newNode.setNext(top);
		top = newNode;
		size++;
	}

	public T pop() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("LinkedStack");
		}
		size--;
		T result = top.getData();
		top = top.getNext();
		return result;
	}

	public T peek() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("LinkedStack");
		}
		return top.getData();
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size() == 0 || top == null;
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "LinkedStack is empty.\n";
		}

		String result = "Size: " + size() + "\n";
		Node<T> current = top;
		while(current != null) {
			result += current.getData() + "\n";
			current = current.getNext();
		}
		return result;
	}
}
