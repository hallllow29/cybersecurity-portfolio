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

package entities.enums;

/**
 * Enum representing different backpack sizes with their respective capacities.
 * It is used to model the storage capacity of a backpack in the application.
 * The available sizes are:
 * - SMALL: Represents a small backpack with a capacity of 1.
 * - MEDIUM: Represents a medium backpack with a capacity of 2.
 * - LARGE: Represents a large backpack with a capacity of 5.
 * - TRY_HARD: Represents a unique case with no storage capacity (capacity of 0).
 */
public enum BackPackSize {
    SMALL(1),
    MEDIUM(2),
    LARGE(5),
    TRY_HARD(0);

    /**
     * Represents the storage capacity associated with a specific backpack size.
     * Defines the fixed capacity value for each size of the backpack.
     */
    private final int capacity;

    /**
     * Constructor for the BackPackSize enumeration.
     *
     * @param capacity the storage capacity associated with the backpack size
     */
    BackPackSize(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the capacity associated with this backpack size.
     *
     * @return the storage capacity of the specific backpack size
     */
    public int getCapacity() {
        return capacity;
    }

}
