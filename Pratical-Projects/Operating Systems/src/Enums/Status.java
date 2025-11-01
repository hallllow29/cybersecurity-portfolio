package Enums;

/**
 * Represents the various states that a task can be in during its lifecycle.
 *
 * Each state provides information about the current progress or status of a task:
 * - READY: The task is prepared to be executed but has not started yet.
 * - RUNNING: The task is currently being executed.
 * - PAUSED: The task execution has been temporarily halted.
 * - WAITING: The task is awaiting resources or conditions to start execution.
 * - COMPLETED: The task has finished its execution.
 */
public enum Status {
	READY,
	RUNNING,
	PAUSED,
	WAITING,
	COMPLETED;
}
