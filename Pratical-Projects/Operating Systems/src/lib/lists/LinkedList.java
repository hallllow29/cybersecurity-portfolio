package lib.lists;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import lib.interfaces.ListADT;
import lib.LinearNode;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList <T> implements ListADT<T>, Iterable<T> {

	private LinearNode<T> front;
	private LinearNode<T> rear;
	private int size;
	private int modCount;

	public LinkedList() {
		this.front = null;
		this.rear = null;
		this.size = 0;
		this.modCount = 0;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setModCount(int modCount) {
		this.modCount = modCount;
	}

	public void setFront(LinearNode<T> front) {
		this.front = front;

		if (this.front == null) {
			this.rear = null;
		}
	}

	public void setRear(LinearNode<T> rear) {
		this.rear = rear;

		if (this.rear == null) {
			this.front = null;
		}
	}

	public int getSize() {
		return this.size;
	}

	public LinearNode<T> getRear() {
		return this.rear;
	}

	public void add(T element) throws NotElementComparableException {
		LinearNode<T> newNode = new LinearNode<>(element);

		if (isEmpty()) {
			this.front = newNode;
			this.rear = newNode;
			this.rear.setNext(null);
		} else {
			this.rear.setNext(newNode);
			this.rear = newNode;
			this.rear.setNext(null);
		}
		this.size++;
		this.modCount++;
	}

	/**
	 * Removes the specified target element from this LinkedList.
	 *
	 * @param target the element to be removed from the list
	 * @return the removed element from the list
	 *
	 * @throws EmptyCollectionException if the list is empty
	 * @throws ElementNotFoundException if the element is not found in the list
	 */
	@Override
	public T remove(T target) throws EmptyCollectionException, ElementNotFoundException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		LinearNode<T> previousNode = null;
		LinearNode<T> currentNode = this.front;
		boolean found = false;

		while (currentNode != null) {
			if (currentNode.getElement().equals(target)) {
				found = true;
				break;
			}
			previousNode = currentNode;
			currentNode = currentNode.getNext();
		}

		if (!found) {
			throw new ElementNotFoundException("Element not in the list.");
		}

		LinearNode<T> remove = currentNode;
		T removedElement = remove.getElement();

		// O  O  O  0
		// F  o  o  R
		// c  n  o  R
		// x  F  n  R
		if (this.front == currentNode) {
			this.front = currentNode.getNext();



			// Para o ultimo... da lista
			// Porque fica vazia
			if (this.front == null) {
				this.rear = null;
			}

		}

		// HALELUIA!!
		if (previousNode != null) {

			// meio...
			previousNode.setNext(currentNode.getNext());

			// Rear é o que se retira
		} else if (this.rear == currentNode) {

			this.rear = previousNode;

			// Tem que se dizer para apontar para null
			// se depois só falta um
			if (previousNode != null) {
				previousNode.setNext(null);
			}
		}

		currentNode = null;

		this.size--;
		this.modCount++;
		return removedElement;
	}


	/**
	 * Returns the first element in the LinkedList.
	 *
	 * @return the first element in this LinkedList
	 *
	 * @throws EmptyCollectionException if the LinkedList is empty
	 */
	@Override
	public T first() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		return this.front.getElement();
	}

	/**
	 * Returns the last element of this LinkedList.
	 *
	 * @return the last element in this LinkedList
	 *
	 * @throws EmptyCollectionException if the LinkedList is empty
	 */
	@Override
	public T last() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}
		return this.rear.getElement();
	}

	/**
	 * Removes and returns the first element from this LinkedList.
	 *
	 * @return the first element from this LinkedList
	 *
	 * @throws EmptyCollectionException if the LinkedList is empty
	 */
	@Override
	public T removeFirst() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		T removedFirstElement = this.front.getElement();

		if (size() == 1) {
			this.front = this.rear = null;
		} else {
			this.front = this.front.getNext();
		}
		this.size--;
		this.modCount++;

		return removedFirstElement;
	}

	/**
	 * Removes and returns the last element from this LinkedList.
	 *
	 * @return the last element from this LinkedList
	 *
	 * @throws EmptyCollectionException if the LinkedList is empty
	 */
	@Override
	public T removeLast() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		T removedLastElement = this.rear.getElement();
		LinearNode<T> current = this.front;

		while (current.getNext() != this.rear) {
			current = current.getNext();
		}
		current.setNext(null);
		this.rear = current;
		this.size--;
		this.modCount++;
		return removedLastElement;
	}

	/**
	 * Checks if the list contains the specified element.
	 *
	 * @param element the element to be checked for containment
	 * @return true if the list contains the specified element, false otherwise
	 *
	 * @throws EmptyCollectionException if the list is empty
	 */
	@Override
	public boolean contains(T element) throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		LinearNode<T> currentNode = this.front;

		while (currentNode != null) {
			if (currentNode.getElement().equals(element)) {
				return true;
			}
			currentNode = currentNode.getNext();
		}
		return false;
	}

	/**
	 * Checks if the linked list is empty.
	 *
	 * @return true if the linked list contains no elements, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Returns the number of elements in this LinkedList.
	 *
	 * @return the number of elements in this LinkedList
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Returns an iterator over the elements in this LinkedList. The iterator provides
	 * sequential access to the elements of the list.
	 *
	 * @return an iterator over the elements in this LinkedList
	 */
	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator<T>();
	}

	@Override
	public String toString() {
		String result = "";
		Iterator<T> iterator = this.iterator();
		while (iterator.hasNext()) {
			result += iterator.next();
			if (iterator.hasNext()) {
				result += " -> ";
			}
		}

		if (size() == 0) {
			return result;
		}

		return result;
	}

	/**
	 * Recursively prints the elements of the linked list starting from the given node.
	 * Each element is followed by " -> " and the sequence ends with a newline character.
	 *
	 * @param lnode the starting node of the linked list to be printed
	 */
	private void printListRecursive(LinearNode<T> lnode) {
		if (lnode == null) {
			System.out.println();
			return;
		}
		System.out.print(lnode.getElement() + " -> ");
		printListRecursive(lnode.getNext());
	}

	public void printListRecursive() {
		printListRecursive(this.front);
	}

	/**
	 * Returns the front node of the linked list.
	 *
	 * @return the front node of the linked list
	 */
	public LinearNode<T> getFront() {
		return this.front;
	}

	/**
	 * Returns the modification count of the LinkedList.
	 *
	 * @return the number of modifications made to the list
	 */

	/**
	 * Retrieves the element at the specified index in the linked list.
	 *
	 * @param index the position of the element to retrieve, where 0 represents the first element
	 * @return the element at the specified index in the linked list
	 * @throws EmptyCollectionException if the linked list is empty
	 * @throws IndexOutOfBoundsException if the specified index is out of the bounds of the linked list
	 */
	public T getElement(int index) throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("LinkedList");
		}

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}

		LinearNode<T> currentNode = this.front;
		for (int i = 0; i < index; i++) {
			currentNode = currentNode.getNext();

		}

		return currentNode.getElement();
	}
	public int getModCount() {
		return this.modCount;
	}

	public void clear() {
		this.front = null;
		this.rear = null;
		this.size = 0;
		this.modCount++;
	}

	public void addAll(LinkedList<T> otherList) throws NotElementComparableException {
		if (otherList == null) {
			throw new IllegalArgumentException("LinkedList");
		}

		for (T element : otherList) {
			add(element);
		}
	}


	public class LinkedListIterator<E> implements Iterator<T> {

		private LinearNode<T> currentNode;
		private LinearNode<T> previousNode;
		private int expectedModCount;
		private boolean okToRemove;

		/**
		 * Constructs an iterator for a linked list. Initializes the iterator's current
		 * node to the front of the list and sets the expected modification count to the
		 * list's current modification count.
		 */
		public LinkedListIterator() {
			this.currentNode = getFront();
			this.previousNode = null;
			this.expectedModCount = getModCount();
			this.okToRemove = false;
		}

		/**
		 * Returns true if the iteration has more elements. Ensures concurrent
		 * modification check is performed before checking the next element.
		 *
		 * @return true if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			checkForCurrentModification();
			return currentNode != null;
		}

		/**
		 * Returns the next element in the iteration. Advances the iterator to the next
		 * element in the sequence. Checks for concurrent modification before proceeding.
		 *
		 * @return the next element in the iteration
		 *
		 * @throws NoSuchElementException          if the iteration has no more elements
		 * @throws ConcurrentModificationException if the list has been modified
		 *                                         concurrently
		 */
		@Override
		public T next() {
			checkForCurrentModification();
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in the iteration.");
			}
			this.previousNode = currentNode;
			T element = previousNode.getElement();
			if (currentNode.getNext() != null) {
				this.currentNode = currentNode.getNext();
			} else {
				this.currentNode = null;
			}
			this.okToRemove = true;
			return element;
		}

		@Override
		public void remove() {
			if (!okToRemove) {
				throw new UnsupportedOperationException("Remove operation is not supported.");
			}

			try {
				LinkedList.this.remove(this.previousNode.getElement());
			} catch (EmptyCollectionException | ElementNotFoundException e) {
				System.err.println(e.getCause());
			}
			this.expectedModCount++;
			this.okToRemove = false;

		}

		/**
		 * Checks for concurrent modification by comparing the current modification count
		 * with the expected modification count. If they do not match, it indicates that
		 * the list has been modified concurrently and throws a
		 * ConcurrentModificationException.
		 *
		 * @throws ConcurrentModificationException if the list has been modified
		 *                                         concurrently
		 */
		private void checkForCurrentModification() {
			if (modCount != expectedModCount) {
				throw new ConcurrentModificationException("Concurrent Modification Detected");
			}
		}
	}
}
