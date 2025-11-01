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

/**
 * Represents a target within a mission, consisting of a specific room and a type descriptor.
 * A target may define a particular objective or area of interest in a game's context.
 */
public class Target {

    /**
     * Represents the specific room associated with the target.
     * The room serves as the location or area of interest defined
     * within the target, and it may contain additional contextual
     * information such as items, enemies, or other attributes.
     */
    private final Room room;
    /**
     * Represents the type descriptor of a target within a mission or objective.
     * The type can define additional characteristics or categorization for the target,
     * such as its specific objective type, purpose, or classification.
     */
    private final String type;

    /**
     * Constructs a Target object with the specified room and type.
     * The Target represents an area of interest or objective within a mission,
     * defined by a specific room and a type descriptor.
     *
     * @param room the room associated with the target; represents the location or area involved
     * @param type the type descriptor of the target; defines its characteristics or classification
     */
    public Target(Room room, String type) {
        this.room = room;
        this.type = type;
    }

    /**
     * Retrieves the room associated with the current target.
     *
     * @return the room represented as a {@code Room} object. The room
     *         defines the specific location or area of interest in the
     *         context of the target.
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Returns a string representation of the Target object.
     * The string includes the associated room and type descriptor
     * formatted as "room Type: type".
     *
     * @return a string representation of the Target object,
     *         including the room and type information.
     */
    public String toString() {
        return  "[" + this.room + "] " + this.type;
    }
}
