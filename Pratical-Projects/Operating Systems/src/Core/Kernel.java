package Core;

import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import lib.trees.PriorityQueue;

/**
 * This class represents the Kernel of a system, responsible for managing
 * tasks, memory, CPU, devices, and server operations.
 * The Kernel acts as a central point of control for coordinating these components.
 */
public class Kernel {

	/**
	 * Represents the CPU component managed by the Kernel.
	 *
	 */
	private final CPU cpu;

	/**
	 * The memory manager used by the Kernel to handle memory-related operations.
	 *
	 * It serves as the central memory component ensuring proper usage and optimization
	 * of available memory resources.
	 */
	private final Mem memory;

	/**
	 * Represents the collection of hardware devices managed by the Kernel.
	 *
	 * This field provides centralized access to the Devices instance, which facilitates managing
	 * input and output devices such as keyboards, displays, mice, and other peripherals.
	 */
	private final Devices devices;

	/**
	 * Represents a server instance that is managed by the Kernel.
	 *
	 * The server operates as a separate thread and is essential for handling
	 * the tasks and maintaining the proper functionality of the Kernel.
	 */
	private final Server server;

	/**
	 * A thread-safe priority queue used to manage and store tasks based on their priority.
	 * Tasks with higher priority (lower Enums.Priority value) are processed before tasks with lower priority.
	 * This data structure ensures efficient retrieval and management of tasks in the Kernel's scheduling system.
	 */
	private final PriorityQueue<Task> taskPriorityQueue;

	/**
	 * Represents the current running state of the Kernel.
	 *
	 * This flag indicates whether the Kernel is actively running or has been stopped.
	 */
	private boolean isRunning;

	/**
	 * Constructs a new instance of the Kernel class, initializing its core components
	 * and setting up the environment for task scheduling and resource management.
	 *
	 * This includes the initialization of:
	 * - A priority queue for task management.
	 * - A CPU instance for processing tasks.
	 * - Memory management through the Mem instance.
	 * - Device management through the Devices instance.
	 * - A server instance for handling server-related operations.
	 *
	 * By default, the Kernel is not running upon creation. The `start` method must
	 * be invoked to activate it.
	 */
	public Kernel() {
		this.taskPriorityQueue = new PriorityQueue<>();
		this.cpu = new CPU();
		this.memory = new Mem();
		this.devices = new Devices();
		this.server = new Server();
		this.isRunning = false;
	}

	/**
	 * Starts the Kernel and its associated components in a synchronized and orderly manner.
	 *
	 * Once all components are started, the Kernel's state is updated to mark it as running, and
	 * a log message is generated to indicate successful activation.
	 *
	 * This method is thread-safe, ensuring synchronized access and avoiding concurrent modifications
	 * to the Kernel's state during the startup process.
	 */
	public synchronized void start() {
		if (this.isRunning) {
			System.out.println("KERNEL ALREADY STARTED");
			return;
		}

		System.out.println("KERNEL STARTING...");
		this.cpu.start();
		this.memory.start();
		this.devices.start();
		this.server.start();

		this.isRunning = true;
		System.out.println("KERNEL STARTED");
	}

	/**
	 * Stops the Kernel and all of its associated components in a synchronized manner.
	 *
	 * This method ensures that the Kernel is stopped only if it is currently in a running state.
	 * If the Kernel is already stopped, a message is logged and no other actions are performed.
	 *
	 *  After stopping all components, the Kernel's state is set to not running, and a log
	 * message is generated to indicate that the shutdown process has completed.
	 *
	 * This method is thread-safe to ensure synchronized access to the Kernel's running state
	 * during the stop operation.
	 */
	public synchronized void stop() {
		if (!this.isRunning) {
			System.out.println("KERNEL ALREADY STOPPED");
			return;
		}

		System.out.println("KERNEL STOPPING...");
		this.cpu.stop();
		this.memory.stop();
		this.devices.stop();
		this.server.stop();

		this.isRunning = false;
		System.out.println("KERNEL STOPPED");
	}

	/**
	 * Adds a task to the Kernel's priority queue if the kernel is running and
	 * the task is valid. The task is added based on its priority level.
	 *
	 * @param task the Task object to be added to the priority queue
	 */
	public synchronized void addTask(Task task) {
		if (!this.isRunning) {
			System.out.println("KERNEL IS NOT RUNNING. START THE KERNEL FIRST.");
			return;
		}

		if (taskValid(task)) {
			taskPriorityQueue.addElement(task, task.getPriority().ordinal());
			System.out.println("NEW TASK '" + task.getName() + "' ADDED TO PRIORITY QUEUE.");
		}
	}

	/**
	 * Processes the next task in the Kernel's task queue if the Kernel is running.
	 *
	 * This method retrieves the next task from the priority queue, validates the
	 * necessary resources, and executes the task if sufficient resources are
	 * available. If resources are inadequate, it attempts to re-queue the task while
	 * logging relevant information.
	 *
	 * This method is thread-safe.
	 */
	public synchronized void processNextTask() {
		if (!this.isRunning) {
			System.out.println("KERNEL IS NOT RUNNING.");
			return;
		}

		while (!taskPriorityQueue.isEmpty()) {
			try {
				Task nextTask = taskPriorityQueue.removeElement();
				new Thread(() -> {
					if (validateResources(nextTask)) {
						cpu.executeOneTask(nextTask);
						releaseResources(nextTask);
					} else {
						System.out.println("RESOURCES NOT AVAILABLE FOR TASK: " + nextTask.getName());
						synchronized (taskPriorityQueue) {
							if (!memory.hasAvailableMemory(nextTask.getMemorySize())) {
								System.out.println("TASK '" + nextTask.getName() + "' REQUIRES MORE MEMORY THAN AVAILABLE");
							} else {
								taskPriorityQueue.addElement(nextTask, nextTask.getPriority().ordinal());
							}

						}
					}
				}).start();
			} catch (EmptyCollectionException e) {
				System.err.println("TASK QUEUE ERROR: " + e.getMessage());
			}
		}
	}

	/**
	 * Validates whether sufficient resources are available to execute the given task and
	 * allocates memory if available.
	 *
	 * @param task the Task object for which resource availability is being validated
	 * @return true if sufficient resources are available and memory allocation succeeds, false otherwise
	 */
	private boolean validateResources(Task task) {
		if (!cpu.isAvailable()) {
			System.out.println("CPU IS NOT AVAILABLE FOR TASK '" + task.getName() + "'");
			return false;
		}

		if (!memory.hasAvailableMemory(task.getMemorySize())) {
			System.out.println("MEMORY IS NOT AVAILABLE FOR TASK '" + task.getName() + "'");
			return false;
		}

		return memory.allocateFF(task);
	}

	/**
	 * Releases resources associated with the specified task. This includes freeing up
	 * memory allocated for the task and logging the operation's status.
	 *
	 * @param task the Task object whose resources are to be released
	 */
	private void releaseResources(Task task) {
		try {
			this.memory.freeMemory(task.getName());
			System.out.println("RESOURCES RELEASED FOR TASK '" + task.getName() + "'");
			this.memory.printMemoryStatus();
		} catch (Exception e) {
			System.err.println("ERROR RELEASING RESOURCES FOR TASK '" + task.getName() + "': " + e.getMessage());
		} catch (NotElementComparableException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * Validates whether a given task is considered valid based on its properties.
	 *
	 * This method checks the following:
	 * - The task is not null.
	 * - The task has a positive duration.
	 * - The task has a non-null and non-empty name.
	 *
	 * @param task the Task object to be validated
	 * @return true if the task is valid, otherwise false
	 */
	private boolean taskValid(Task task) {
		if (task == null) {
			System.out.println("TASK CANNOT BE NULL");
			return false;
		}
		if (task.getDuration() <= 0) {
			System.out.println("TASK '" + task.getName() + "' HAS INVALID DURATION");
			return false;
		}
		if (task.getName() == null || task.getName().isEmpty()) {
			System.out.println("TASK NAME CANNOT BE NULL OR EMPTY");
			return false;
		}
		return true;
	}
}
