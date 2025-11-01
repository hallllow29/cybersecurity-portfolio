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
 * Represents a mission in a game or simulation. A mission is defined by a unique code,
 * version, a battlefield, and associated properties such as enemies, items, entry and
 * exit points, and a specific target.
 */
public class Mission implements MissionI {

	/**
	 * The unique identifier for a mission. Each mission is represented by a distinct code
	 * that serves to differentiate it from other missions.
	 */
	private final String code;

	/**
	 * Represents the version of the mission. Versions are utilized to differentiate
	 * between different iterations or updates of a mission. Each mission is assigned an
	 * immutable version at the time of creation.
	 */
	private final int version;

	/**
	 * Represents the specific target for a mission in a game or simulation. The target
	 * could be an object or entity located in a particular room. This variable is used to
	 * define and track the primary goal of the mission.
	 */
	private Target target;

	/**
	 * Indicates whether the target for the mission is currently secured. If true, the
	 * target has been successfully secured; otherwise, it remains unsecured.
	 */
	private boolean targetSecured;

	/**
	 * Represents the battlefield for a mission in a game or simulation. The battlefield
	 * is modeled as a custom network of rooms, capturing the spatial layout and
	 * connections between rooms. It serves as the primary environment where mission
	 * activities take place, including the placement of enemies, items, and entry/exit
	 * points.
	 */
	private final CustomNetwork<Room> battlefield;

	/**
	 * Represents a collection of enemies associated with the mission. Each enemy in the
	 * list is an instance of the {@code Enemy} class. This variable maintains the list of
	 * enemies present within the battlefield or the mission's context.
	 */
	private final LinkedList<Enemy> enemies;

	/**
	 * Represents a collection of items associated with a mission. The items in this list
	 * are instances of the Item class or its subclasses. This collection is used to
	 * manage and track specific objects or resources integral to the mission, such as
	 * tools, collectibles, or mission-critical assets.
	 */
	private final LinkedList<Item> items;

	/**
	 * Represents a collection of entry and exit points in the form of rooms within a
	 * mission. These points serve as designated locations where entities can enter or
	 * leave the mission's battlefield.
	 */
	private final LinkedList<Room> entry_exit_points;

	/**
	 * Creates a new mission with a specified code, version, and battlefield. This
	 * constructor initializes the mission's battlefield, sets the code and version, and
	 * prepares empty lists for enemies, items, and entry/exit points. The target is
	 * initially unset, and the targetSecured status is false.
	 *
	 * @param code        the unique identifier for the mission
	 * @param version     the version number of the mission
	 * @param battlefield a network structure representing the battlefield, composed of
	 *                    connected rooms
	 */
	public Mission(String code, int version, CustomNetwork<Room> battlefield) {
		this.code = code;
		this.version = version;
		this.battlefield = battlefield;
		this.enemies = new LinkedList<>();
		this.items = new LinkedList<>();
		this.entry_exit_points = new LinkedList<>();
		this.target = null;
		this.targetSecured = false;
	}

	/**
	 * Retrieves the version number of the mission.
	 *
	 * @return an integer representing the version of the mission.
	 */
	public int getVersion() {
		return this.version;
	}

	/**
	 * Adds an enemy to the mission's list of enemies.
	 *
	 * @param enemy the enemy to be added to the mission
	 * @throws NotElementComparableException if the enemy cannot be added due to
	 *                                       non-comparability
	 */
	public void setEnemy(Enemy enemy) throws NotElementComparableException {
		this.enemies.add(enemy);
	}

	/**
	 * Adds an item to the mission's list of items.
	 *
	 * @param item the item to be added to the mission
	 * @throws NotElementComparableException if the item cannot be added due to
	 *                                       non-comparability
	 */
	public void setItem(Item item) throws NotElementComparableException {
		this.items.add(item);
	}

	/**
	 * Adds a room to the list of entry and exit points for the mission. This method
	 * allows specifying rooms that can act as both entry and exit points.
	 *
	 * @param entry_exit_point the room to be added as an entry and exit point
	 * @throws NotElementComparableException if the room cannot be added due to
	 *                                       non-comparability
	 */
	public void setEntryExitPoint(Room entry_exit_point) throws NotElementComparableException {
		this.entry_exit_points.add(entry_exit_point);
	}

	/**
	 * Sets the target for the mission. The target specifies the key objective or location
	 * associated with the mission, including details such as the room and type associated
	 * with the target.
	 *
	 * @param target the Target object representing the mission's objective or location
	 */
	public void setTarget(Target target) {
		this.target = target;
	}

	/**
	 * Sets the status indicating whether the mission's target has been secured.
	 *
	 * @param targetSecured a boolean value where {@code true} indicates the target has
	 *                      been secured, and {@code false} indicates it has not.
	 */
	public void setTargetSecured(boolean targetSecured) {
		this.targetSecured = targetSecured;
	}

	/**
	 * Retrieves the list of enemies associated with the mission.
	 *
	 * @return a LinkedList containing all enemies in the mission
	 */
	public LinkedList<Enemy> getEnemies() {
		return this.enemies;
	}

	/**
	 * Retrieves the list of items associated with the mission.
	 *
	 * @return a LinkedList containing all items in the mission
	 */
	public LinkedList<Item> getItems() {
		return this.items;
	}

	/**
	 * Retrieves the list of entry and exit points associated with the mission.
	 *
	 * @return a LinkedList of Room objects that represent the entry and exit points for
	 * the mission.
	 */
	public LinkedList<Room> getEntryExitPoints() {
		return this.entry_exit_points;
	}

	/**
	 * Retrieves the battlefield associated with the mission. The battlefield is
	 * represented as a network of connected rooms.
	 *
	 * @return a CustomNetwork of Room objects representing the mission's battlefield
	 */
	public CustomNetwork<Room> getBattlefield() {
		return this.battlefield;
	}

	/**
	 * Retrieves the target associated with the mission. The target specifies the key
	 * objective or location associated with the mission, typically including details such
	 * as the room and type associated with the target.
	 *
	 * @return the Target object representing the mission's objective or location.
	 */
	public Target getTarget() {
		return this.target;
	}

	/**
	 * Checks whether the mission's target has been secured.
	 *
	 * @return true if the target is secured, false otherwise.
	 */
	public boolean isTargetSecured() {
		return this.targetSecured;
	}

	/**
	 * Retrieves the unique code that identifies the mission.
	 *
	 * @return a String representing the mission's code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Compares this Mission object to another object to determine equality. Two Mission
	 * objects are considered equal if they have the same code and version.
	 *
	 * @param obj the Object to be compared for equality with this Mission
	 * @return true if the specified object is equal to this Mission; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Mission other = (Mission) obj;
		return this.code.equals(other.code);
	}

}
