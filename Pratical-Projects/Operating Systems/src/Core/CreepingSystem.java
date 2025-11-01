package Core;

/**
 * The {@code CreepingSystem} class represents a system that manages the execution
 * of tasks in conjunction with an instance of the {@code Kernel}. It operates
 * in a loop, processing tasks and interacting with the kernel for task management.
 * This class is implemented as a runnable and runs its processing logic in a separate thread.
 */
public class CreepingSystem implements Runnable {

	/**
	 * The duration, in milliseconds, for which the current thread will pause its execution
	 * during various operations within the CreepingSystem.
	 *
	 * The value is fixed and should not be modified to maintain consistent system behavior.
	 */
	private static final int THREAD_SLEEP_TIME = 1000;

	/**
	 * The `kernel` field represents the core processing unit or computational
	 * engine utilized by the `CreepingSystem`.
	 *
	 * The `kernel` is used internally by the `CreepingSystem` to coordinate tasks,
	 * perform operations, and facilitate the execution lifecycle defined by the
	 * `start`, `stop`, and `run` methods.
	 */
	private final Kernel kernel;
	private Thread thread;
	private volatile boolean isRunning;

	/**
	 * Constructs a new CreepingSystem instance with the specified Kernel.
	 *
	 * @param kernel the Kernel instance to be used by this CreepingSystem.
	 *               Must not be null.
	 * @throws IllegalArgumentException if the kernel parameter is null.
	 */
	public CreepingSystem(Kernel kernel) {
		if (kernel == null) {
			throw new IllegalArgumentException("Kernel cannot be null.");
		}
		this.kernel = kernel;
		this.isRunning = false;
	}

	/**
	 * Starts the CreepingSystem instance and its associated thread.
	 *
	 * This method initializes and starts a separate thread to execute the CreepingSystem's
	 * processing logic and invokes the start method of the underlying Kernel instance.
	 *
	 * Thread safety is ensured by synchronizing access to this method.
	 *
	 * Logging statements provide feedback on the progress of the operation, including timestamps
	 * for system start and completion.
	 */
	public synchronized void start() {
		if (this.isRunning) {
			System.out.println("CREEPING SYSTEM ALREADY RUNNING");
			return;
		}

		System.out.println("[" + System.currentTimeMillis() + "] CREEPING SYSTEM STARTING...");
		this.isRunning = true;

		this.thread = new Thread(this, "CreepingSystem-Thread");
		this.thread.start();

		kernel.start();
		System.out.println("[" + System.currentTimeMillis() + "] CREEPING SYSTEM STARTED");
	}

	/**
	 * Stops the CreepingSystem and its associated components in a synchronized manner.
	 *
	 * This method ensures that the CreepingSystem halts its processing safely, interrupting
	 * and joining the internal thread if it is running. It also invokes the `stop` method
	 * of the underlying Kernel instance to perform a complete shutdown of all related resources.
	 *
	 * Thread safety is guaranteed by synchronizing this method to prevent concurrent access,
	 * ensuring a consistent state during the stop operation.
	 *
	 * Exceptions occurring during the thread termination process (specifically `InterruptedException`)
	 * are caught and logged, maintaining system stability while re-interrupting the current thread.
	 */
	public synchronized void stop() {
		if (!this.isRunning) {
			System.out.println("CREEPING SYSTEM ALREADY STOPPED");
			return;
		}

		System.out.println("[" + System.currentTimeMillis() + "] CREEPING SYSTEM STOPPING...");
		this.isRunning = false;

		if (this.thread != null) {
			this.thread.interrupt();
			try {
				this.thread.join();
			} catch (InterruptedException e) {
				System.err.println("ERROR STOPPING CREEPING SYSTEM: " + e.getMessage());
				Thread.currentThread().interrupt();
			}
		}

		kernel.stop();
		System.out.println("[" + System.currentTimeMillis() + "] CREEPING SYSTEM STOPPED");
	}

	/**
	 * Continuously executes the processing logic of the CreepingSystem in a separate thread
	 * until the system is stopped or the thread is interrupted.
	 *
	 * Thread interruption is handled within the loop. If the thread is interrupted, an error
	 * message is logged, and the current thread is re-interrupted to allow proper
	 * shutdown or management by the calling code.
	 *
	 * This method is implemented as part of the `Runnable` interface to allow execution
	 * on a separate thread. It relies on the `isRunning` flag to manage its execution state.
	 */
	@Override
	public void run() {
		while (this.isRunning && !Thread.currentThread().isInterrupted()) {
			try {
				this.kernel.processNextTask();
				Thread.sleep(THREAD_SLEEP_TIME);
			} catch (InterruptedException e) {
				System.err.println("CREEPING SYSTEM INTERRUPTED: " + e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * Adds the specified task to the CreepingSystem, ensuring it is valid and
	 * handled by the underlying Kernel instance.
	 *
	 * @param task the Task object to be added. Must not be null.
	 * @throws IllegalArgumentException if the task is null.
	 */
	public void addTask(Task task) {
		if (task == null) {
			throw new IllegalArgumentException("Task cannot be null.");
		}
		this.kernel.addTask(task);
		System.out.println("TASK " + task.getName() + " ADDED TO CREEPING SYSTEM");
	}
}
