package Core;

import Enums.DeviceType;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * A class representing a hardware device with features for connection,
 * availability status, and resource access control using semaphores.
 */
public class Device {

    /**
     * The name of the device. This field is a unique identifier for the device
     * and is used to distinguish it from other devices.
     */
    private final String name;

    /**
     * Represents the type of the device. This variable is a constant and defines
     * whether the device is of type INPUT or OUTPUT, as specified in the {@link DeviceType} enumeration.
     */
    private final DeviceType type;

    /**
     * Indicates whether the device is currently connected.
     */
    private boolean connected;

    /**
     * Indicates whether the device is currently in use or occupied.
     * This field is used to track the status of the device's availability for operations.
     */
    private boolean busy;

    /**
     * A semaphore used to control access to the device's usage state.
     * It ensures thread-safe management of the device's availability.
     */
    private final Semaphore semaphore;

    /**
     * Constructs a new Device with the specified name and type. Initializes the
     * device's state as not connected and not busy.
     *
     * @param name the name of the device
     * @param type the type of the device, either INPUT or OUTPUT
     */
    public Device(String name, DeviceType type) {
        this.name = name;
        this.type = type;
        this.connected = false;
        this.busy = false;
        this.semaphore = new Semaphore(1);
    }

    /**
     * Establishes a connection to the device.
     *
     * If the device is already connected, this method will simply log a message
     * indicating that the device is already connected and take no further action.
     *
     * This method is thread-safe as it is synchronized to prevent concurrent modifications
     * to the connection state of the device.
     */
    public synchronized void connect() {
        if (this.connected) {
            System.out.println("[" + System.currentTimeMillis() + "] Device already connected -> " + this.name);
            return;
        }
        this.busy = true;
        this.connected = true;
        System.out.println("[" + System.currentTimeMillis() + "] Device connected -> " + this.name);
    }

    /**
     * Disconnects the device if it is currently connected.
     *
     * If the device is not connected, a message will be logged indicating
     * that the device is already disconnected, and no further action will
     * be taken.
     *
     * This method is thread-safe as it is synchronized to prevent concurrent
     * modifications to the connection state of the device.
     */
    public synchronized void disconnect() {
        if (!this.connected) {
            System.out.println("[" + System.currentTimeMillis() + "] Device already disconnected -> " + this.name);
            return;
        }
        this.connected = false;
        this.busy = false;
        System.out.println("[" + System.currentTimeMillis() + "] Device disconnected -> " + this.name);
    }

    /**
     * Retrieves the type of the device.
     *
     * @return the type of the device as a {@code DeviceType}, either {@code INPUT} or {@code OUTPUT}
     */
    public DeviceType getType() {
        return this.type;
    }
}
