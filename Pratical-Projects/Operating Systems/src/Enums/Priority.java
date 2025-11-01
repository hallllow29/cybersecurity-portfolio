package Enums;

/**
 * The Priority enum represents the priority level for tasks.
 * It provides three predefined levels: HIGH, MEDIUM, and LOW,
 * each associated with an integer value for comparison or display.
 */
public enum Priority {
	HIGH(0),
	MEDIUM(1),
	LOW(2);

	private final int priority;

	Priority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}
}
