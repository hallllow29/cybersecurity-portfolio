package Core;

/**
 * The Service class represents a basic service that can be started, stopped,
 * and processed. It provides mechanisms to manage the service's state and
 * operations in a thread-safe manner.
 *
 * A Service instance is identified by its name and manages its running state internally.
 * It supports synchronized methods to ensure safe concurrent access to its state across threads.
 */
public class Service {

    /**
     * The name of the service, used to uniquely identify a Service instance.
     * This field is immutable and assigned at the time of object construction.
     */
    private final String name;

    /**
     * Represents the running state of the service.
     * This field is used to determine whether the service is currently active or not.
     *
     * It is managed internally by the service's start and stop methods and ensures
     * thread-safe access through synchronized operations.
     */
    private boolean isRunning;

    /**
     * Constructs a new Service instance with the specified name and initializes its state.
     *
     * @param name The name of the service, used to uniquely identify it.
     */
    public Service(String name) {
        this.name = name;
        this.isRunning = false;
    }

    /**
     * Starts the service if it is not already running.
     *
     * This method checks the current running state of the service.
     *
     * If the service is not running, this method updates its state to running and
     * prints messages indicating that the service is starting and has successfully started.
     *
     * The method is synchronized to ensure thread-safe access to the service's running state
     * when invoked in a concurrent environment.
     */
    public synchronized void start() {
        if (this.isRunning) {
            System.out.println("SERVICE '" + name + "' IS ALREADY RUNNING");
            return;
        }

        System.out.println("SERVICE '" + name + "' STARTING...");
        this.isRunning = true;
        System.out.println("SERVICE '" + name + "' STARTED");
    }

    /**
     * Stops the service if it is currently running.
     *
     * This method checks the current running state of the service.
     *
     * If the service is running, the method updates its state to stopped and prints messages
     * indicating the progress of the stop operation.
     *
     * The method is synchronized to ensure thread-safe interaction with the service's running
     * state when accessed by multiple threads concurrently.
     */
    public synchronized void stop() {
        if (!this.isRunning) {
            System.out.println("SERVICE '" + name + "' IS ALREADY STOPPED");
            return;
        }

        System.out.println("SERVICE '" + name + "' STOPPING...");
        this.isRunning = false;
        System.out.println("SERVICE '" + name + "' STOPPED");
    }

    /**
     * Checks if the service is running and eligible for processing.
     *
     * This method provides a synchronized way to determine whether the service
     * is currently in a running state. It ensures thread-safe access to the
     * service's state in concurrent environments.
     *
     * @return true if the service is running, false otherwise.
     */
    public synchronized boolean asksProcess() {
        return this.isRunning;
    }

    /**
     * Executes the processing logic of the service.
     *
     * This method determines whether the service is in a running state.
     *
     * If the service is running, a message indicating that the service is processing
     * is printed to the console.
     *
     * The method is synchronized to ensure thread-safe checks of the service's state across
     * multiple threads.
     */
    public synchronized void process() {
        if (this.isRunning) {
            System.out.println("SERVICE '" + name + "' IS PROCESSING...");
        } else {
            System.out.println("SERVICE '" + name + "' IS NOT RUNNING. PROCESS CANNOT BE EXECUTED.");
        }
    }
}
