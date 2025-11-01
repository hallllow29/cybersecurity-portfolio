package Core;

/**
 * A class that represents a block of memory with a unique identifier, start address, and size.
 * This class provides mechanisms to compare blocks, retrieve information, and enforce integrity
 * constraints for memory management purposes.
 */
public class MemoryBlock implements Comparable<MemoryBlock> {

    /**
     * A unique identifier for the memory block.
     * This value is used to distinguish between different memory blocks.
     */
    private final String id;

    /**
     * The starting address of the memory block.
     * This value represents the beginning location in memory where the block resides.
     * It is used to identify the starting point of the memory block and may be updated
     * if the block is moved or merged with another block.
     */
    private int startAddress;

    /**
     * Represents the size of the memory block in bytes.
     * This field specifies the total allocated space for the memory block.
     * It is initialized during the creation of the MemoryBlock object but
     * can be updated later as needed.
     */
    private int size;

    /**
     * Constructs a new MemoryBlock with the specified unique identifier, start address, and size.
     * Ensures that the size of the memory block is greater than zero.
     *
     * @param id The unique identifier for this memory block.
     * @param startAddress The starting address of the memory block.
     * @param size The size of the memory block. Must be greater than zero.
     * @throws IllegalArgumentException if the size is less than or equal to zero.
     */
    public MemoryBlock(String id, int startAddress, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }
        this.id = id;
        this.startAddress = startAddress;
        this.size = size;
    }

    /**
     * Retrieves the unique identifier of this memory block.
     *
     * @return The unique identifier as a String.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the starting address of this memory block.
     *
     * @return The starting address of the memory block as an integer.
     */
    public int getStartAddress() {
        return this.startAddress;
    }

    /**
     * Retrieves the size of this memory block.
     *
     * @return The size of the memory block as an integer.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Updates the size of this memory block.
     * Ensures that the provided size is non-negative.
     *
     * @param size The new size of the memory block. Must be a non-negative integer.
     * @throws IllegalArgumentException if the provided size is negative.
     */
    public void setSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        this.size = size;
    }

    /**
     * Updates the starting address of this memory block.
     *
     * @param startAddress The new starting address of the memory block, represented as an integer.
     */
    public void setStartAddress(int startAddress) {
        this.startAddress = startAddress;
    }

    /**
     * Compares this MemoryBlock instance with another MemoryBlock instance based on their starting addresses.
     *
     * @param other The other MemoryBlock to compare this MemoryBlock to.
     * @return A negative integer, zero, or a positive integer if this MemoryBlock's starting address is
     *         less than, equal to, or greater than the starting address of the specified MemoryBlock.
     */
    @Override
    public int compareTo(MemoryBlock other) {
        return Integer.compare(this.startAddress, other.startAddress);
    }

    /**
     * Returns a string representation of this MemoryBlock instance.
     * The representation includes the unique identifier, start address,
     * and size of the memory block in a formatted string.
     *
     * @return A string representation of the MemoryBlock containing its id,
     *         start address, and size.
     */
    @Override
    public String toString() {
        return String.format("MemoryBlock{id='%s', startAddress=%d, size=%d}", id, startAddress, size);
    }

    /**
     * Compares the specified object with this MemoryBlock for equality.
     * Returns true if the specified object is also a MemoryBlock and both have the same id,
     * start address, and size.
     *
     * @param obj The object to be compared for equality with this MemoryBlock.
     * @return true if the specified object is equal to this MemoryBlock; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MemoryBlock that = (MemoryBlock) obj;
        return startAddress == that.startAddress && size == that.size && id.equals(that.id);
    }

    /**
     * Computes the hash code for this MemoryBlock instance based on its fields.
     * The calculation incorporates the hash code of the unique identifier,
     * the starting address, and the size of the memory block.
     *
     * @return The computed hash code as an integer.
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + startAddress;
        result = 31 * result + size;
        return result;
    }
}
