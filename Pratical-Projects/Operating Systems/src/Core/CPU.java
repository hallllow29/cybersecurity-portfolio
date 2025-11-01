package Core;

import Enums.CPUState;
import Enums.Status;

/**
 * The CPU class represents the central processing unit responsible for executing Tasks in a system.
 * It maintains its current state, availability, and tracks the number of completed tasks.
 */
public class CPU {

	/**
	 * Represents the current operational state of the CPU.
	 * Used to indicate whether the CPU is in an IDLE, RUNNING, or STOPPED state.
	 * This field is managed internally to track and control CPU behavior during task execution.
	 *
	 * @see CPUState
	 */
	private CPUState state;

	/**
	 * Represents the availability status of the CPU.
	 * The field indicates whether the CPU is currently available to execute tasks.
	 *
	 * True indicates the CPU is available, and false indicates the CPU is busy processing.
	 */
	private boolean isAvailable;

	/**
	 * The number of tasks successfully completed by the CPU.
	 * This field keeps track of how many tasks have been executed to completion while the CPU was in the RUNNING state.
	 * It is incremented whenever a task finishes its execution without being interrupted or paused.
	 */
	private int completedTasks;

	/**
	 * Constructs a new CPU instance.
	 *
	 * The CPU is initialized with its state set to STOPPED, available status set to true,
	 * and the number of completed tasks set to zero. Additionally, a log entry is created
	 * to indicate that the CPU has been initialized with default values.
	 */
	public CPU() {
		this.state = CPUState.STOPPED;
		this.isAvailable = true;
		this.completedTasks = 0;
		Logger.log("CPU INITIALIZED - STATE: STOPPED, AVAILABLE: TRUE");
	}

	/**
	 * Checks whether the CPU is currently available for executing tasks.
	 *
	 * @return true if the CPU is available, false otherwise.
	 */
	public synchronized boolean isAvailable() {
		return this.isAvailable;
	}

	/**
	 * Starts the CPU if it is not already in the RUNNING state.
	 *
	 * When invoked, this method checks the current state of the CPU.
	 * If the CPU is already in the RUNNING state, it logs an informational
	 * message indicating that the CPU is already started and takes no further action.
	 *
	 * If the CPU is not in the RUNNING state, this method transitions the
	 * state of the CPU to RUNNING, sets its availability flag to true, and logs
	 * messages to record the start event. Both log entries and console outputs
	 * are generated to provide information about the transition.
	 *
	 * This method is thread-safe and ensures synchronized access to the
	 * state of the CPU when starting it.
	 */
	public synchronized void start() {
		if (this.state == CPUState.RUNNING) {
			Logger.log("CPU ALREADY STARTED");
			System.out.println("[" + System.currentTimeMillis() + "] CPU ALREADY STARTED");
			return;
		}

		Logger.log("CPU STARTING...");
		System.out.println("[" + System.currentTimeMillis() + "] CPU STARTING...");
		this.state = CPUState.RUNNING;
		this.isAvailable = true;
		Logger.log("CPU STARTED");
		System.out.println("[" + System.currentTimeMillis() + "] CPU STARTED");
	}

	/**
	 * Stops the CPU if it is not already in the STOPPED state.
	 *
	 * This method checks the current state of the CPU and performs the following steps:
	 * - If the CPU is already in the STOPPED state, it logs and outputs a message indicating
	 *   that the CPU is already stopped, and exits without taking further action.
	 * - If the CPU is not in the STOPPED state, it transitions the CPU state to STOPPED,
	 *   sets its availability flag to false, and logs and outputs messages to record the stop event.
	 *
	 * This method is thread-safe, ensuring synchronized access to the state of the CPU
	 * during the stopping process.
	 */
	public synchronized void stop() {
		if (this.state == CPUState.STOPPED) {
			Logger.log("CPU ALREADY STOPPED");
			System.out.println("[" + System.currentTimeMillis() + "] CPU ALREADY STOPPED");
			return;
		}

		Logger.log("CPU STOPPING...");
		System.out.println("[" + System.currentTimeMillis() + "] CPU STOPPING...");
		this.state = CPUState.STOPPED;
		this.isAvailable = false;
		Logger.log("CPU STOPPED");
		System.out.println("[" + System.currentTimeMillis() + "] CPU STOPPED");
	}

	/**
	 * Executes a specified task for a given duration if the CPU is in a running state.
	 * The method ensures that only one task can be executed at a time due to synchronization.
	 * It logs the task execution progress, handles interruptions, and sets the task's status
	 * accordingly. The method also ensures the CPU's availability state is updated during
	 * task execution.
	 *
	 * @param task The task to be executed. Must not be null.
	 * @param duration The duration for which the task should be executed (in milliseconds).
	 *                 Must be a positive value.
	 */
	private synchronized void runTask(Task task, long duration) {
		if (task == null) {
			System.out.println("[" + System.currentTimeMillis() + "] TASK CANNOT BE NULL");
			Logger.log("TASK CANNOT BE NULL");
			return;
		}

		if (this.state != CPUState.RUNNING) {
			Logger.log("CPU IS NOT RUNNING - TASK " + task.getName() + " CANNOT BE EXECUTED");
			System.out.println("[" + System.currentTimeMillis() + "] CPU IS NOT RUNNING");
			return;
		}

		this.isAvailable = false;
		task.setStatus(Status.RUNNING);
		Logger.log("TASK " + task.getName() + " EXECUTING");
		System.out.println("[" + System.currentTimeMillis() + "] CPU EXECUTING TASK " + task.getName());

		Thread taskThread = new Thread(() -> {
			try {
				Thread.sleep(duration);
			} catch (InterruptedException e) {
				task.setStatus(Status.PAUSED);
				Logger.log("TASK " + task.getName() + " INTERRUPTED");
				System.err.println("[" + System.currentTimeMillis() + "] TASK " + task.getName() + " INTERRUPTED");
				Thread.currentThread().interrupt();
			}
		});

		taskThread.start();
		try {
			taskThread.join(duration + 500);
			if (taskThread.isAlive()) {
				taskThread.interrupt();
				Logger.log("TIMEOUT: TASK " + task.getName() + " INTERRUPTED");
				System.out.println("[" + System.currentTimeMillis() + "] TIMEOUT: TASK " + task.getName() + " INTERRUPTED");
				task.setStatus(Status.PAUSED);
			} else {
				task.setStatus(Status.COMPLETED);
				Logger.log("TASK " + task.getName() + " COMPLETED");
				System.out.println("[" + System.currentTimeMillis() + "] TASK " + task.getName() + " COMPLETED");
				this.completedTasks++;
			}
		} catch (InterruptedException e) {
			Logger.log("TASK " + task.getName() + " INTERRUPTED DURING EXECUTION");
			System.err.println("[" + System.currentTimeMillis() + "] TASK " + task.getName() + " INTERRUPTED DURING EXECUTION");
			Thread.currentThread().interrupt();
		} finally {
			this.isAvailable = true;
			Logger.log("CPU AVAILABLE");
		}
	}

	/**
	 * Executes a single task using the CPU.
	 * This method ensures that the task is executed in a thread-safe manner,
	 * allowing only one task to be processed at a time.
	 *
	 * @param task The task to be executed. Must not be null and should have a valid duration set.
	 */
	public synchronized void executeOneTask(Task task) {
		runTask(task, task.getDuration());
	}

}
