package lib;

public class PriorityQueueNode<T> implements Comparable<PriorityQueueNode>  {

	private static int nextorder = 0;
	private int priority;
	private int order;
	private T element;

	public PriorityQueueNode(T element, int priority) {
		this.element = element;
		this.priority = priority;
		this.order = nextorder;
		nextorder++;
	}

	public T getElement() {
		return this.element;
	}

	public int getPriority() {
		return this.priority;
	}

	public int getOrder() {
		return this.order;
	}

	public String toString() {
		String temp  = (element.toString() + " " + this.priority + this.order);
		return temp;
	}

	public int compareTo(PriorityQueueNode other) {
		int result;

		if (this.priority > other.getPriority()) {
			result = 1;
		} else if (this.priority < other.getPriority()) {
			result = -1;
		} else if (this.order > other.getOrder()) {
			result = 1;
		} else {
			result = -1;
		}

		return result;
	}

}
