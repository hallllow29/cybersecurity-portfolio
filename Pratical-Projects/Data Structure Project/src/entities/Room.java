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
 * Represents a room in a game or simulation. A room has a unique name
 * and attributes to track the presence of items and enemies, as well
 * as the total count of each. Rooms can be compared based on their unique names.
 */
public class Room implements Comparable<Room> {

	/**
	 * The name of the room. This is a unique identifier for the room,
	 * used for naming and comparing different rooms.
	 */
	private final String name;

	/**
	 * Indicates whether there are enemies currently present in the room.
	 * This variable is used to track the presence of enemies in the room,
	 * allowing the associated logic and behavior to handle enemy-related
	 * functionality accordingly.
	 */
	private boolean enemiesInRoom;

	/**
	 * Represents whether there are items currently in the room.
	 * This variable serves as a flag indicating the presence of items.
	 */
	private boolean itemsInRoom;

	/**
	 * Represents the total number of enemies currently in the room.
	 * Tracks the count of enemies added or removed from the room.
	 */
	private int totalEnemies;

	/**
	 * Represents the total number of items currently in the room.
	 * Tracks the count of items added or removed from the room.
	 */
	private int totalItems;

	/**
	 * Constructs a new Room object with a specified unique name.
	 * Initializes the room with no enemies or items present.
	 *
	 * @param name the unique identifier for the room
	 */
    public Room(String name) {
		this.name = name;
		this.totalEnemies = 0;
		this.totalItems = 0;
    }

	/**
	 * Retrieves the unique name of the room.
	 *
	 * @return the name of the room as a String
	 */
	public String getName()  {
		return this.name;
	}

	/**
	 * Updates the status of whether enemies are currently present in the room.
	 *
	 * @param enemiesInRoom a boolean indicating the presence of enemies in the room.
	 *                       Set to true if enemies are present, otherwise false.
	 */
	public void setEnemies(boolean enemiesInRoom) {
		this.enemiesInRoom = enemiesInRoom;
	}

	/**
	 * Updates the status of whether items are currently present in the room.
	 *
	 * @param itemsInRoom a boolean indicating the presence of items in the room.
	 *                     Set to true if items are present, otherwise false.
	 */
	public void setItemsInRoom(boolean itemsInRoom) {
		this.itemsInRoom = itemsInRoom;
	}

	/**
	 * Increments the total number of enemies in the room by one.
	 * This method is used to update the count of enemies whenever
	 * an enemy is added to the room.
	 */
	public void addEnemy() {
		this.totalEnemies++;
	}

	/**
	 * Decrements the total number of enemies in the room by one.
	 * This method is typically used to update the enemy count
	 * when an enemy is removed from the room.
	 */
	public void removeEnemy(){
		this.totalEnemies--;
	}

	/**
	 * Increments the total number of items in the room by one.
	 * This method is typically used to update the count of items whenever
	 * a new item is added to the room.
	 */
	public void addItem() {
		this.totalItems++;
	}

	/**
	 * Decrements the total number of items in the room by one.
	 * This method is typically used to update the count of items when an item is removed
	 * or picked up from the room.
	 */
	public void removeItem() {
		this.totalItems--;
	}

	/**
	 * Retrieves the total number of enemies present in the room.
	 *
	 * @return the total number of enemies in the room as an integer.
	 */
	public int getTotalEnemies() {
		return this.totalEnemies;
	}

	/**
	 * Retrieves the total number of items present in the room.
	 *
	 * @return the total number of items in the room as an integer.
	 */
	public int getTotalItems() {
		return this.totalItems;
	}

	/**
	 * Checks whether the room contains any items.
	 *
	 * @return true if there are items present in the room, false otherwise.
	 */
	public boolean hasItems() {
		return this.itemsInRoom;
	}

	/**
	 * Checks whether there are currently any enemies present in the room.
	 *
	 * @return true if enemies are present in the room, false otherwise.
	 */
	public boolean hasEnemies() {
		return enemiesInRoom;
	}

	/**
	 * Compares this Room object with the specified Room object for order.
	 * The comparison is based on the lexicographical order of the room names.
	 *
	 * @param other the Room object to be compared with this Room.
	 * @return a negative integer, zero, or a positive integer if the name of this Room
	 */
	@Override
	public int compareTo(Room other) {
		return this.name.compareTo(other.name);
	}

	/**
	 * Returns a string representation of the room.
	 * This representation includes the unique name of the room.
	 *
	 * @return the name of the room as a string.
	 */
	public String toString() {
		return this.name;
	}

	/**
	 * Compares this Room object to the specified object to determine equality.
	 * Two Room objects are considered equal if they have the same name.
	 *
	 * @param obj the object to be compared for equality with this Room
	 * @return true if the specified object is equal to this Room, false otherwise
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Room other = (Room) obj;
		return this.name.equals(other.name);
	}
}
