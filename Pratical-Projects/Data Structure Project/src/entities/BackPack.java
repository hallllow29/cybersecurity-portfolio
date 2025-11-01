/*
 * Nome: Pedro Daniel Gonçalves Antunes
 * Número: 8230068
 * Turma: LSIRCT2
 *
 * Nome: Ruben Xavier Ferreira Nunes
 * Número: 8230069
 * Turma: LSIRCT2
 *
 */
package entities;

import lib.stacks.ArrayStack;
import lib.exceptions.EmptyCollectionException;

/**
 * The BackPack class represents a container for storing MediKit items with a fixed maximum capacity.
 * It provides functionality for adding, removing, and managing MediKit objects in a stack-based
 * structure, ensuring that the specified maximum capacity is not exceeded.
 */
public class BackPack {

    /**
     * The maximum capacity of the backpack. This value determines the maximum
     * number of items that the backpack can hold. Once the maximum capacity
     * is reached, no more items can be added to the backpack.
     *
     * This field is initialized when the backpack is constructed and cannot
     * be modified afterward, ensuring that the capacity remains constant.
     */
    private final int maxCapacity;

    /**
     * Represents a stack-based storage system for MediKit objects within the context
     * of a Backpack. This field utilizes an ArrayStack, a custom stack implementation,
     * to manage MediKit objects. The backpack stack has a defined maximum capacity,
     * preventing the addition of any MediKit objects beyond that limit.
     */
    private final ArrayStack<MediKit> back_pack;


    /**
     * Constructs a BackPack with the specified maximum capacity.
     *
     * @param maxCapacity the maximum number of items the backpack can hold
     */
    public BackPack(int maxCapacity) {
        this.back_pack = new ArrayStack<>(maxCapacity);
        this.maxCapacity = maxCapacity;
    }


    /**
     * Adds a MediKit to the backpack. If the backpack is already at its maximum capacity,
     * the method will display a message indicating that no more kits can be added and will
     * not perform the addition.
     *
     * @param kit the MediKit to be added to the backpack
     */
    public void addKit(MediKit kit) {
        if (back_pack.size() >= maxCapacity) {
            System.out.println("BackPack is full! Can't add more kits!");
            return;
        }

        this.back_pack.push(kit);
    }


    /**
     * Removes and returns the top MediKit from the backpack. This method allows a player
     * to use a MediKit for restoring health. If the backpack is empty, an
     * EmptyCollectionException is thrown to indicate the operation cannot proceed.
     *
     * @return the top MediKit from the backpack
     * @throws EmptyCollectionException if the backpack is empty and no MediKit is available
     */
    public MediKit useKit() throws EmptyCollectionException {
        return this.back_pack.pop();
    }


    /**
     * Checks if the backpack is empty.
     *
     * @return true if the backpack is empty, false otherwise
     */
    public boolean isBackPackEmpty() {
        return this.back_pack.isEmpty();
    }


    /**
     * Retrieves the total number of MediKit objects currently stored in the backpack.
     *
     * @return the number of MediKit objects in the backpack as an integer
     */
    public int numberOfKits() {
        return this.back_pack.size();
    }

    /**
     * Returns a string representation of the BackPack object. The representation includes
     * the list of items currently stored in the backpack.
     *
     * @return a string representation of the BackPack and its contents.
     */
    @Override
    public String toString() {
        return "BackPack:\n" + back_pack;
    }


    /**
     * Retrieves the current stack of MediKit objects stored in the backpack.
     *
     * @return an ArrayStack containing all MediKit objects currently in the backpack
     */
    public ArrayStack<MediKit> getListItems() {
        return this.back_pack;
    }


    /**
     * Checks if the backpack is at its maximum capacity.
     *
     * @return true if the backpack contains the maximum number of items it can hold,
     *         false otherwise.
     */
    public boolean isBackPackFull() {
        return this.back_pack.size() == maxCapacity;
    }


    /**
     * Retrieves the maximum capacity of the backpack.
     *
     * @return the maximum number of items the backpack can hold as an integer.
     */
    public int getMaxCapacity() {
        return this.maxCapacity;
    }

}
