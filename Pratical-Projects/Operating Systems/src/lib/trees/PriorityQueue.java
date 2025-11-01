package lib.trees;

import lib.PriorityQueueNode;
import lib.exceptions.EmptyCollectionException;

/**
 * The PriorityQueue class represents a generic priority queue implementation
 * that allows elements to be added and removed based on their priority. It
 * uses a min-heap data structure for efficient retrieval of the element with
 * the highest priority (lowest priority value). Elements are stored as
 * PriorityQueueNode objects, which pair the data with its associated priority value.
 *
 * This class extends the ArrayHeap class and leverages its functionality
 * to manage the heap property. The priority queue is designed for scenarios
 * where elements need to be processed in the order of their associated priorities.
 *
 * @param <T> the type of elements held in this priority queue
 */
public class PriorityQueue<T> extends ArrayHeap<PriorityQueueNode<T>> {

	/**
	 * Constructs an empty PriorityQueue.
	 *
	 **/
	public PriorityQueue() {
		super();
	}

	/**
	 * Adds an element to the priority queue with the specified priority.
	 * Wraps the element and its associated priority into a PriorityQueueNode
	 * and adds it to the underlying heap structure.
	 *
	 * @param element the element to be added to the priority queue
	 * @param priority the priority associated with the element
	 */
	public void addElement(T element, int priority) {
		PriorityQueueNode<T> priorityQueueNode = new PriorityQueueNode<T>(element, priority);

		super.addElement(priorityQueueNode);
	}

	/**
	 * Removes and returns the element with the highest priority from the priority queue.
	 * This method retrieves the minimum element from the internal heap structure,
	 * extracts its associated value, and returns it.
	 *
	 * @return the element of type T with the highest priority in the priority queue
	 * @throws EmptyCollectionException if the priority queue is empty
	 */
	public T removeElement() throws EmptyCollectionException {
		PriorityQueueNode<T> temp = (PriorityQueueNode<T>) super.removeMin();
		return temp.getElement();
	}

}
