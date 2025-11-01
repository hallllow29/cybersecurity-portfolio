package Enums;

/**
 * Represents the possible states of a CPU.
 *
 * The CPUState enum defines the various operational states that a CPU can have:
 * - `IDLE`: Indicates that the CPU is not performing any tasks and is in a waiting state.
 * - `RUNNING`: Indicates that the CPU is actively executing tasks.
 * - `STOPPED`: Indicates that the CPU has been stopped and is not operational.
 *
 * This enumeration is used to manage and track the state of a CPU in operations such as
 * starting, stopping, and executing tasks. The state helps in determining whether the CPU
 * can take up new tasks or if it needs to transition to a different state.
 */
public enum CPUState {
    IDLE, RUNNING, STOPPED;
}
