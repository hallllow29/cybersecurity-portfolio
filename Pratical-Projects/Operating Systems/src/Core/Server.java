package Core;

import lib.HasTables.HashMap;

/**
 * The Server class is responsible for managing services and running
 * as a threaded server. It initializes, starts, stops, and oversees
 * the execution of multiple services.
 *
 * This class uses the Runnable interface to allow execution in a
 * separate thread and provides synchronization methods to control
 * the server's lifecycle.
 */
public class Server implements Runnable {

    /**
     * A collection of server services, which are managed and initialized by the server.
     * Each service is identified by a unique string key and is responsible for specific
     * operations within the server's lifecycle.
     */
    private final HashMap<String, Service> services;

    /**
     * Represents the thread responsible for running the server's main logic.
     * Used to manage the lifecycle of the server asynchronously.
     */
    private Thread serverThread;

    /**
     * Indicates whether the server thread is currently running.
     * This flag is used to manage and check the server's lifecycle state.
     * It is marked as volatile to ensure thread-safe visibility of changes across threads.
     */
    private volatile boolean isRunning;

    /**
     * Constructs a new Server instance.
     *
     * This constructor initializes the server as not running and sets up
     * an internal map for managing server services. It also invokes the
     * `initServices` method to initialize predefined services associated
     * with the server.
     */
    public Server() {
        this.isRunning = false;
        this.services = new HashMap<>();
        initServices();
    }

    /**
     * Initializes the predefined services for the server.
     *
     * This method sets up a mapping of service names to their corresponding
     * instances and stores them in the `services` map. By default, it adds
     * two services, "HARDWARE" and "MANAGER", initializing them with their
     * respective names. A message indicating that services have been initialized
     * is printed to the console.
     */
    private void initServices() {
        this.services.put("HARDWARE", new Service("HARDWARE"));
        this.services.put("MANAGER", new Service("MANAGER"));
        System.out.println("SERVICES INITIALIZED.");
    }

    /**
     * Starts the server in a synchronized manner.
     *
     * This method initiates the server by creating a dedicated thread for its execution
     * and sets its state to running.
     *
     * The method ensures thread-safety by synchronizing access to the server's state,
     * preventing potential issues that could arise from concurrent modifications.
     *
     */
    public synchronized void start() {
        if (isRunning) {
            System.out.println("SERVER ALREADY STARTED.");
            return;
        }

        System.out.println("SERVER STARTING...");
        isRunning = true;

        this.serverThread = new Thread(this, "Server-Thread");
        this.serverThread.start();

        System.out.println("SERVER STARTED.");
    }

    /**
     * Stops the server in a synchronized manner.
     * This method ensures that the server is stopped only if it is currently running.
     *
     * Any interruption during the thread joining process will result in the interruption
     * being logged, and the thread's interrupted status will be restored.
     *
     * Thread safety is guaranteed as this method synchronizes access to the server's
     * running state.
     */
    public synchronized void stop() {
        if (!isRunning) {
            System.out.println("SERVER ALREADY STOPPED.");
            return;
        }

        System.out.println("SERVER STOPPING...");
        isRunning = false;

        try {
            if (serverThread != null) {
                serverThread.join();
            }
        } catch (InterruptedException e) {
            System.err.println("SERVER STOP INTERRUPTED: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        System.out.println("SERVER STOPPED.");
    }

    /**
     * Executes the server's main operational loop.
     *
     * This method starts by initializing all configured services and then enters a continuous
     * execution loop as long as the server's running state is active. Within this loop:
     *
     * The loop gracefully handles exceptions, including interruptions and unexpected errors, logging relevant
     * messages to the console. In the event of an interruption, the thread's interrupt status is restored, and
     * the loop terminates to prevent further execution.
     *
     * Finally, the method ensures that all services are stopped before exiting.
     */
    @Override
    public void run() {
        startAllServices();

        while (isRunning) {
            try {
                for (Service service : this.services.getValues()) {
                    if (service.asksProcess()) {
                        service.process();
                    }
                }
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.err.println("SERVER INTERRUPTED: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("UNEXPECTED ERROR IN SERVER: " + e.getMessage());
            }
        }

        stopAllServices();
    }

    /**
     * Checks whether the server is currently running.
     *
     * @return true if the server is running, false otherwise.
     */
    public synchronized boolean isRunning() {
        return this.isRunning;
    }

    /**
     * Starts all registered services in the server.
     *
     * This method iterates over all services contained within the server's services collection
     * and invokes their respective start method. If a service is already running, its start
     * method ensures that it is not restarted unnecessarily.
     *
     * This method is typically called during the server's initialization or operational setup
     * to ensure that all services are ready and functioning properly.
     */
    private void startAllServices() {
        System.out.println("STARTING ALL SERVICES...");
        for (Service service : this.services.getValues()) {
            service.start();
        }
    }

    /**
     * Stops all services managed by the server.
     *
     * This method iterates through all registered services within the server's internal
     * collection and invokes their respective `stop` method. Each service is stopped
     * sequentially, ensuring proper shutdown behavior.
     *
     * Typically called during server shutdown or when transitioning to a non-operational state.
     */
    private void stopAllServices() {
        System.out.println("STOPPING ALL SERVICES...");
        for (Service service : this.services.getValues()) {
            service.stop();
        }
    }
}
