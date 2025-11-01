package lib.queues;
import lib.Node;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.QueueADT;

public class LinkedQueue <T> implements QueueADT<T> {

	/**
	 * A reference to the front node in the queue.
	 * This pointer indicates the first element in the queue, enabling
	 * operations to retrieve or remove the front element efficiently.
	 * It is updated whenever elements are added to or removed from the queue.
	 */
	private Node<T> front;

	/**
	 * A reference to the rear (or tail) node of the linked queue.
	 * The rear node represents the most recently added element in the queue.
	 * It is updated whenever a new element is enqueued and is used to link
	 * the next node in the queue when additional elements are added.
	 * If the queue is empty, this reference is set to null.
	 */
	private Node<T> rear;

	/**
	 * Represents the current number of elements stored in the queue.
	 * The value of this variable is incremented when an element is added to the queue
	 * and decremented when an element is removed.
	 */
	private int size;

	/**
	 * Constructs an empty LinkedQueue with the front, rear, and size initialized.
	 * The front and rear pointers are set to null, and the size is initialized to zero,
	 * indicating that the queue is empty.
	 */
	public LinkedQueue(){
		front = null;
		rear = null;
		size = 0;
	}

	/**
	 * Adds a new element to the rear of the queue. If the queue is empty,
	 * both the front and rear pointers are set to the new node. Otherwise,
	 * the new node is linked to the current rear node, and the rear pointer
	 * is updated to the new node. The size of the queue is then incremented.
	 *
	 * @param data the element to be added to the rear of the queue
	 */
	public void enqueue(T data) {
		Node<T> newNode = new Node();
		if (isEmpty()) {
			front = newNode;
			rear = newNode;
			newNode.setData(data);
		} else {
			rear.setNext(newNode);
			rear = newNode;
			rear.setData(data);
		}
		size++;
	}

	/**
	 * Removes and returns the element at the front of this queue.
	 * The element is removed from the queue and the size is decremented.
	 *
	 * @return the element at the front of the queue
	 * @throws EmptyCollectionException if the queue is empty
	 */
	public T dequeue() throws EmptyCollectionException {
		if(isEmpty()){
			throw new EmptyCollectionException("Queue");
		}
		Node<T> newFront = front.getNext();
		T result = front.getData();
		front = null;
		front = newFront;
		size--;
		return result;
	}

	/**
	 * Returns the element at the front of the queue without removing it.
	 *
	 * @return the first element in the queue
	 * @throws EmptyCollectionException if the queue is empty
	 */
	@Override
	public T first() throws EmptyCollectionException {
		if (this.isEmpty()) {
			throw new EmptyCollectionException("LinkedQueue");
		}

		return this.front.getData();
	}

	/**
	 * Checks whether the queue is empty.
	 *
	 * @return true if the queue contains no elements, false otherwise
	 */
	public boolean isEmpty(){
		return size() == 0 || front == null;
	}

	/**
	 * Returns the number of elements currently stored in the queue.
	 *
	 * @return the integer representing the number of elements in the queue
	 */
	public int size(){
		return this.size;
	}

	/**
	 * Generates a string representation of the queue.
	 * The representation includes all elements of the queue in order,
	 * from the front to the back of the queue, enclosed in square brackets
	 * and separated by commas, along with markers indicating the front and back.
	 * If the queue is empty, it returns "[]".
	 *
	 * @return a string representation of the queue's contents and structure
	 */
	@Override
	public String toString() {
		if (isEmpty()) {
			return "[]";
		}
		String result = " <- FRONT [";
		Node<T> current = front;
		while (current != null) {
			result += current.getData() + ", ";
			current = current.getNext();
		}
		return result + "] <- BACK";
	}

}