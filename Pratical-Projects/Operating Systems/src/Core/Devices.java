package Core;

import Enums.DeviceType;
import lib.HasTables.HashMap;
import lib.exceptions.EmptyCollectionException;

/**
 * The Devices class manages a collection of devices,*/
public class Devices {

    /**
     * A map storing devices where the key represents the unique name of the device
     * and the value is the corresponding {@code Device} instance.
     * The collection supports synchronized operations to ensure thread-safety when performing
     * actions on devices in multi-threaded environments.
     */
    private final HashMap<String, Device> devices;

    /**
     * Indicates whether the devices in the system are currently running.
     */
    private boolean isRunning;

    /**
     * Constructs a new Devices instance, initializing the internal device map and setting the
     * running state to false. This constructor also attempts to initialize devices by invoking
     * the `initDevices` method. If device initialization fails due to an empty collection,
     * an error message is printed to the standard error stream.
     * The created instance is ready to manage connected devices through its methods, either
     * starting them, stopping them, or verifying their status.
     */
    public Devices() {
        this.devices = new HashMap<>();
        this.isRunning = false;
        try {
            initDevices();
        } catch (EmptyCollectionException e) {
            System.err.println("INIT DEVICES FAILED: " + e.getMessage());
        }
    }

    /**
     * Initializes the internal collection of devices by populating it with predefined
     * input and output devices. Devices such as "DISPLAY", "KEYBOARD", "MOUSE",
     * "SPEAKER", and "MICROPHONE" are instantiated and added with their respective types.
     *
     * @throws EmptyCollectionException if the internal collection is empty or cannot
     *         hold the provided device mappings.
     */
    private synchronized void initDevices() throws EmptyCollectionException {
        devices.put("DISPLAY", new Device("DISPLAY", DeviceType.OUTPUT));
        devices.put("KEYBOARD", new Device("KEYBOARD", DeviceType.INPUT));
        devices.put("MOUSE", new Device("MOUSE", DeviceType.INPUT));
        devices.put("SPEAKER", new Device("SPEAKER", DeviceType.OUTPUT));
        devices.put("MICROPHONE", new Device("MICROPHONE", DeviceType.INPUT));
        System.out.println("DEVICES INITIALIZED SUCCESSFULLY");
    }

    /**
     * Starts all devices managed by the `Devices` class. This method ensures that the devices are
     * only started if they are not already running.
     * If the devices are already running, a message indicating this state is logged, and the method
     * terminates without any further action. Otherwise, the method initializes the starting sequence
     * by connecting all available devices and marking the `isRunning` flag as true, showing that the
     * devices are now actively managed.
     *
     * Thread-safety is guaranteed by the `synchronized` modifier, ensuring that only one thread
     * can execute this method at a time.
     *
     */
    public synchronized void start() {
        if (this.isRunning) {
            System.out.println("DEVICES ALREADY STARTED");
            return;
        }

        System.out.println("DEVICES STARTING...");
        for (Device device : devices.getValues()) {
            device.connect();
        }
        this.isRunning = true;
        System.out.println("DEVICES STARTED");
    }

    /**
     * Stops the operation of all devices managed by the {@code Devices} instance.
     * This method ensures that the devices are stopped only if they are currently running.
     * If the devices are not in a running state, a message will be logged indicating
     * that they are already stopped, and no further action will be taken.
     * Thread-safety is guaranteed by the synchronized modifier, ensuring that only
     * one thread can execute this method at a time.
     */
    public synchronized void stop() {
        if (!this.isRunning) {
            System.out.println("DEVICES ALREADY STOPPED");
            return;
        }

        System.out.println("DEVICES STOPPING...");
        for (Device device : devices.getValues()) {
            device.disconnect();
        }
        this.isRunning = false;
        System.out.println("DEVICES STOPPED");
    }
}
