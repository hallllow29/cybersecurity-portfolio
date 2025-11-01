package Core;

import lib.HasTables.HashMap;
import lib.LinearNode;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import lib.lists.LinkedOrderedList;

import java.util.logging.Logger;

/**
 * The Mem class represents a memory management system that uses a first-fit
 * allocation algorithm for dynamic memory management. It maintains a list of
 * free memory blocks and a map of allocated memory blocks. The class includes
 * functionality for starting and stopping the memory system, allocating memory,
 * freeing memory, and checking memory status.
 */
public class Mem {

    /**
     * Defines the maximum size of memory that can be allocated or managed in the system.
     * This constant is used to enforce limits on memory usage and ensure system stability.
     * It represents the total amount of memory available for allocation in kilobytes.
     */
    private static final int MAX_MEMORY_SIZE = 1024;

    /**
     * List of free memory blocks managed in ascending order of their starting addresses.
     *
     * Memory blocks in this list are instances of {@link MemoryBlock} and are ordered
     * based on their starting addresses to facilitate operations like merging and searching.
     */
    private final LinkedOrderedList<MemoryBlock> memoryFree;

    /**
     * This structure is utilized to store, manage, and access information about
     * memory blocks during allocation and deallocation operations.
     *
     * It is a thread-safe map when accessed through synchronized methods in its
     * containing class.
     */
    private final HashMap<String, MemoryBlock> memoryBlocks;

    /**
     * Tracks the total amount of memory currently used by the system.
     * This variable is updated as memory is allocated or freed.
     * It is utilized to monitor the memory usage and ensure it does not
     * exceed the maximum allowed memory size.
     */
    private int totalMemoryUsed;

    /**
     * Logger instance used for capturing and logging runtime information, errors, and
     * debugging details within the Mem class.
     */
    private static final Logger logger = Logger.getLogger(Mem.class.getName());

    /**
     * Indicates whether the memory system is currently active or running.
     * This flag is typically controlled by the `start` and `stop` methods
     * to manage the operational state of the memory.
     */
    private boolean isRunning;

    /**
     * Constructs a new Mem object to manage memory operations, including
     * allocation, deallocation, and status monitoring. This constructor
     * initializes the memory management structures and sets up the memory
     * manager's internal state.
     *
     * If an error occurs when attempting to initialize the memory block list,
     * an error message is printed to the standard error stream.
     */
    public Mem() {
        this.memoryFree = new LinkedOrderedList<>();
        this.memoryBlocks = new HashMap<>();
        this.totalMemoryUsed = 0;
        this.isRunning = false;

        try {
            this.memoryFree.add(new MemoryBlock("0000", 0, MAX_MEMORY_SIZE));
        } catch (NotElementComparableException e) {
            System.err.println("ERROR INITIALIZING MEMORY: " + e.getMessage());
        }
    }

    /**
     * Starts the memory management system if it is not already running.
     *
     * This method is synchronized to ensure thread safety when managing the
     * memory system's state. If the memory system is already running, a warning
     * is logged and the method exits without performing any operation.
     *
     */
    public synchronized void start() {
        if (isRunning) {
            logger.warning("MEMORY ALREADY STARTED");
            return;
        }
        logger.info("MEMORY STARTING...");
        isRunning = true;
        printMemoryStatus();
    }

    /**
     * Stops the memory management system in a synchronized manner.
     *
     * This method ensures that the memory system is stopped only if it is currently running.
     * If the memory system is already stopped, a warning is logged and no actions are performed.
     *
     * Thread safety is ensured by synchronizing the method, preventing concurrent access to
     * the memory system's state during the stop operation.
     */
    public synchronized void stop() {
        if (!isRunning) {
            logger.warning("MEMORY ALREADY STOPPED");
            return;
        }
        logger.info("MEMORY STOPPING...");
        memoryBlocks.clear();
        totalMemoryUsed = 0;
        isRunning = false;
        printMemoryStatus();
    }

    /**
     * Allocates memory for a given task using the First-Fit allocation strategy.
     * This method checks the available free memory blocks and attempts to allocate
     * a block that is sufficient to meet the memory requirements of the task.
     *
     * @param task The task requesting memory allocation. The task must specify
     *             the required memory size through its {@code getMemorySize} method.
     * @return {@code true} if the memory was successfully allocated for the task;
     *         {@code false} otherwise, such as in cases of invalid memory size,
     *         insufficient memory, or timeout during allocation.
     */
    public boolean allocateFF(Task task) {
        if (task.getMemorySize() <= 0) {
            System.out.println("INVALID MEMORY SIZE FOR TASK: " + task.getName());
            return false;
        }

        long startTime = System.currentTimeMillis();
        long timeout = 2000;

        synchronized (memoryFree) {
            while (true) {
                for (MemoryBlock block : memoryFree) {
                    if (block.getSize() >= task.getMemorySize()) {
                        int startAddress = block.getStartAddress();
                        block.setStartAddress(startAddress + task.getMemorySize());
                        block.setSize(block.getSize() - task.getMemorySize());

                        MemoryBlock newBlock = new MemoryBlock(task.getName(), startAddress, task.getMemorySize());
                        memoryBlocks.put(newBlock.getId(), newBlock);
                        totalMemoryUsed += task.getMemorySize();
                        System.out.println("MEMORY ALLOCATED FOR TASK: " + task.getName() + " AT ADDRESS: " + startAddress);
                        printMemoryStatus();
                        return true;
                    }
                }

                if (System.currentTimeMillis() - startTime >= timeout) {
                    System.out.println("TIMEOUT: MEMORY NOT AVAILABLE FOR TASK: " + task.getName());
                    return false;
                }

                try {
                    memoryFree.wait(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
    }

    /**
     * Frees a memory block identified by its unique identifier and adds it back to the pool of free memory
     * blocks. If the specified memory block does not exist, the method logs a message and exits without effect.
     * Adjacent free memory blocks are merged to optimize utilization.
     *
     * @param id The unique identifier of the memory block to be freed.
     * @throws EmptyCollectionException Thrown if the internal collection of memory blocks is empty.
     * @throws ElementNotFoundException Thrown if the memory block with the specified identifier is not found.
     * @throws NotElementComparableException Thrown if an error occurs while merging memory blocks due to
     *         incompatible comparability between elements.
     */
    public void freeMemory(String id) throws EmptyCollectionException, ElementNotFoundException, NotElementComparableException {
       MemoryBlock blockToFree;

       synchronized (memoryBlocks) {
              blockToFree = memoryBlocks.get(id);
              if (blockToFree == null) {
                System.out.println("MEMORY BLOCK NOT FOUND...");
                return;
              }
              memoryBlocks.remove(id);
       }

       synchronized (memoryFree) {
              memoryFree.add(blockToFree);
              totalMemoryUsed -= blockToFree.getSize();
              System.out.println("MEMORY BLOCK " + id + " FREED");

              mergeMemoryFreeBlocks();
       }

    }

    /**
     * Checks if there is enough available memory to allocate the requested size.
     *
     * @param requiredSize The amount of memory requested to check for availability.
     *                      Must be a non-negative integer representing the size in
     *                      memory units (e.g., bytes, kilobytes).
     * @return {@code true} if there is sufficient available memory to allocate
     *         the requested size while the system is running; {@code false} otherwise.
     */
    public synchronized boolean hasAvailableMemory(int requiredSize) {
        return isRunning && (totalMemoryUsed + requiredSize <= MAX_MEMORY_SIZE);
    }

    /**
     * Prints the current status of the memory management system, including
     * total memory, used memory, free memory, and the number of allocated memory blocks.
     *
     * This method logs memory statistics to provide insights into the system's
     * memory utilization and allocation status.
     *
     * The memory statistics are displayed in a structured and formatted manner,
     * making it easier to analyze memory usage trends and identify potential issues.
     *
     * This method is synchronized to ensure thread safety when accessing shared
     * memory management resources.
     */
    public synchronized void printMemoryStatus() {
        logger.info("\n==================== MEMORY STATUS ====================");
        logger.info("Total Memory: " + MAX_MEMORY_SIZE + "MB");
        logger.info("Used Memory: " + totalMemoryUsed + "MB");
        logger.info("Free Memory: " + (MAX_MEMORY_SIZE - totalMemoryUsed) + "MB");
        logger.info("Allocated Blocks: " + memoryBlocks.size());
        logger.info("======================================================\n");
    }

    /**
     * Merges adjacent free memory blocks into a single larger block to optimize memory usage.
     *
     * This method iterates through the list of free memory blocks and combines blocks
     * that are contiguous in memory. Contiguous memory blocks are identified by checking
     * if the end address of the current block matches the start address of the next block.
     */
    private void mergeMemoryFreeBlocks() {
        synchronized (memoryFree) {
            if (memoryFree.isEmpty() || memoryFree.size() == 1) {
                return;

            }
        }

        LinearNode<MemoryBlock> current = memoryFree.getFront();
        while (current != null && current.getNext() != null) {
            MemoryBlock currentBlock = current.getElement();
            MemoryBlock nextBlock = current.getNext().getElement();

            if (currentBlock.getStartAddress() + currentBlock.getSize() == nextBlock.getStartAddress()) {
                currentBlock.setSize(currentBlock.getSize() + nextBlock.getSize());
                try {
                    memoryFree.remove(nextBlock);
                } catch (EmptyCollectionException | ElementNotFoundException e) {
                    System.err.println("ERROR MERGING BLOCKS: " + e.getMessage());
                }
            } else {
                current = current.getNext();
            }
        }
    }
}
