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

import lib.exceptions.NotElementComparableException;
import lib.graphs.CustomNetwork;
import lib.lists.LinkedList;

/**
 * Represents the structure and behavior of a mission.
 * A mission encapsulates details such as unique code, targets, enemies, items,
 * entry/exit points, and the battlefield. It also defines operations
 * to modify and retrieve these properties.
 */
public interface MissionI {

    /**
     * Retrieves the unique code associated with the mission.
     *
     * @return a string representing the unique identifier for the mission.
     */
    String getCode();

    /**
     * Retrieves the target associated with the current mission.
     *
     * @return the mission's target, which contains information about the specific goal or location.
     */
    Target getTarget();

    /**
     * Checks whether the mission's target has been secured.
     *
     * @return true if the target is secured, false otherwise.
     */
    boolean isTargetSecured();

    /**
     * Retrieves the battlefield representation as a custom network of rooms.
     * The battlefield consists of interconnected rooms, which may contain items, enemies,
     * or other properties based on the associated Room and CustomNetwork definitions.
     *
     * @return a CustomNetwork object containing Room instances that represent the battlefield.
     */
    CustomNetwork<Room> getBattlefield();

    /**
     * Retrieves a list of all enemies associated with the mission.
     *
     * @return a linked list of Enemy objects representing the enemies in the mission
     */
    LinkedList<Enemy> getEnemies();

    /**
     * Retrieves a list of items associated with the mission.
     *
     * @return a LinkedList containing items of type Item.
     */
    LinkedList<Item> getItems();

    /**
     * Retrieves the list of entry and exit points for a mission, represented as rooms.
     * These points are critical for defining the beginning and endpoints of a mission,
     * and may influence navigation or strategy within the mission's context.
     *
     * @return a LinkedList of Room objects representing entry and exit points.
     */
    LinkedList<Room> getEntryExitPoints();

    /**
     * Sets the target for a mission using the provided target object.
     *
     * @param target the Target object representing the mission's goal,
     *               containing details such as the room and target type.
     */
    void setTarget(Target target);

    /**
     * Sets the secured status of the mission target.
     *
     * @param targetSecured boolean value indicating whether the mission target is secured.
     */
    void setTargetSecured(boolean targetSecured);

    /**
     * Adds an enemy to the mission's list of enemies.
     *
     * @param enemy the enemy to be added to the mission
     * @throws NotElementComparableException if the enemy cannot be added due to non-comparability
     */
    void setEnemy(Enemy enemy) throws NotElementComparableException;

    /**
     * Sets an item for the mission. The provided item must be comparable.
     *
     * @param item the item to be set for the mission.
     * @throws NotElementComparableException if the provided item is not comparable.
     */
    void setItem(Item item) throws NotElementComparableException;

    /**
     * Sets a specified Room as the entry/exit point of the mission.
     *
     * @param entry_exit_point the Room object to be designated as the entry/exit point
     * @throws NotElementComparableException if the provided Room cannot be compared
     */
    void setEntryExitPoint(Room entry_exit_point) throws NotElementComparableException;
}
