package Core;

import Enums.Priority;
import Enums.Status;
import lib.lists.LinkedUnorderedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a task with specific attributes such as name, priority, duration, memory size,
 * required devices, and status. Tasks can be compared by priority.
 */
public class Task implements Comparable<Task> {

	/**
	 * The name of the task, used to uniquely identify or describe the task.
	 * This field is immutable and initialized during the creation of a Task object.
	 */
	private final String name;

	/**
	 * Represents the priority level of the task.
	 * This variable holds a value of the {@link Priority} enum,
	 * indicating whether the task has a HIGH, MEDIUM, or LOW priority.
	 * It is immutable and must be provided during task creation.
	 */
	private final Priority priority;

	/**
	 * Represents the duration of the task in milliseconds.
	 * This value is used to indicate the total time allocated or consumed by the task.
	 */
	private long duration;

	/**
	 * Represents the memory size required for a task.
	 * This field is used to determine the amount of memory allocated
	 * or needed during the execution of the task.
	 */
	private int memorySize;

	/**
	 * Represents an unordered list of devices required for the execution of a task.
	 * This list maintains the names of the devices and allows operations such as
	 * addition, removal, and checking the existence of devices in the list.
	 * The list is implemented using a LinkedUnorderedList for efficient manipulation of elements.
	 */
	private LinkedUnorderedList<String> deviceList;

	/**
	 * Represents the current status of the task.
	 * The status can be one of the following: READY, RUNNING, PAUSED, WAITING, or COMPLETED.
	 */
	private Status status;

	/**
	 * Constructs a new Task with the specified name and priority.
	 * Initializes the task with default values for duration (1000 ms),
	 * memory size (0 MB), an empty list of required devices, and a
	 * status of WAITING.
	 *
	 * @param name The name or description of the task.
	 * @param priority The priority level of the task (e.g., HIGH, MEDIUM, LOW).
	 */
	public Task(String name, Priority priority) {
		this.name = name;
		this.priority = priority;
		this.duration = 1000;
		this.memorySize = 0;
		this.deviceList = new LinkedUnorderedList<>();
		this.status = Status.WAITING;
	}

	/**
	 * Retrieves the name or description of the task.
	 *
	 * @return The name of the task.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Retrieves the amount of memory required by the task.
	 *
	 * @return The memory size required by the task in megabytes (MB).
	 */
	public int getMemorySize() {
		return this.memorySize;
	}

	/**
	 * Retrieves the duration of the task.
	 *
	 * @return The duration of the task in milliseconds.
	 */
	public long getDuration() {
		return this.duration;
	}

	/**
	 * Retrieves the priority level of the task.
	 *
	 * @return The priority of the task, represented as an instance of the Priority enum.
	 */
	public Priority getPriority() {
		return this.priority;
	}

	/**
	 * Retrieves a list of devices associated with the task.
	 *
	 * @return A list of device names as strings.
	 */
	public List<String> getDevices() {
		List<String> devices = new ArrayList<>();
		for (String device : this.deviceList) {
			devices.add(device);
		}
		return devices;
	}

	/**
	 * Sets the memory size required by the task.
	 * The memory size must be a non-negative integer. If a negative value is passed,
	 * an IllegalArgumentException will be thrown.
	 *
	 * @param memorySize The memory size required by the task in megabytes (MB).
	 *                   Must be a non-negative integer.
	 * @throws IllegalArgumentException If the specified memory size is negative.
	 */
	public void setMemorySize(int memorySize) {
		if (memorySize < 0) {
			throw new IllegalArgumentException("Memory size cannot be negative.");
		}
		this.memorySize = memorySize;
	}

	/**
	 * Sets the duration of the task.
	 * The duration must be a positive value, otherwise an IllegalArgumentException is thrown.
	 *
	 * @param duration The duration of the task in milliseconds. Must be greater than zero.
	 * @throws IllegalArgumentException If the specified duration is less than or equal to zero.
	 */
	public void setDuration(long duration) {
		if (duration <= 0) {
			throw new IllegalArgumentException("Duration must be greater than zero.");
		}
		this.duration = duration;
	}

	/**
	 * Sets the status of the task.
	 *
	 * @param status The new status to be assigned to the task. Must be a valid instance of the Status enum.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Compares this task with the specified task based on their priority levels.
	 * The comparison is determined by the ordinal values of the priorities, where
	 * a lower ordinal value signifies a higher priority.
	 *
	 * @param other The task to be compared with this task.
	 *              It must not be null and should contain a valid priority level.
	 * @return A negative integer, zero, or a positive integer as this task's
	 *         priority is higher than, equal to, or lower than the specified task's priority.
	 */
	@Override
	public int compareTo(Task other) {
		return Integer.compare(this.priority.ordinal(), other.getPriority().ordinal());
	}

	/**
	 * Generates a string representation of the Task object.
	 * The string includes the task's name, priority, duration in milliseconds,
	 * memory size in megabytes, current status, and associated devices.
	 *
	 * @return A string representation of the Task object, containing values of
	 *         the task's attributes in a formatted structure.
	 */
	@Override
	public String toString() {
		return String.format("Task{name='%s', priority=%s, duration=%dms, memorySize=%dMB, status=%s, devices=%s}",
				name, priority, duration, memorySize, status, getDevices());
	}
}
