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
 * Represents an abstract item in a game that has a name and a position.
 * The Item class provides functionality to manage its position and
 * implements the Comparable interface to allow comparison based on the item's value.
 */
public abstract class Item implements Comparable<Item> {

	/**
	 * The name of the item.
	 */
	private final String name;

	/**
	 * The current position of the item represented as a entities.Room object.
	 */
	private Room position;

	/**
	 * Constructs a new entities.Item with the specified name and position.
	 *
	 * @param name the name of the item.
	 * @param position the initial position of the item as a entities.Room object.
	 */
	public Item(String name, Room position) {
		this.name = name;
		this.position = position;
	}

	/**
	 * Retrieves the name of the item.
	 *
	 * @return the name of the item as a String.
	 */
	public abstract String getName();

	/**
	 * Retrieves the value of the item.
	 *
	 * @return the value of the item as an integer.
	 */
	public abstract int getItemValue();


	/**
	 * Retrieves the current position of the item.
	 *
	 * @return the current position of the item as a entities.Room object.
	 */
	public Room getPosition() {
		return this.position;
	}

	/**
	 * Sets the current position of the item.
	 *
	 * @param position the new position of the item represented as a entities.Room object.
	 */
	public void setPosition(Room position) {
		this.position = position;
	}


	/**
	 * Compares this item with the specified item for order based on their values.
	 * The comparison is*/
	public int compareTo(Item other) {
		return Integer.compare(this.getItemValue(), other.getItemValue());
	}

	/**
	 * Returns a string representation of the Item object.
	 * The returned string includes the name and current position
	 * of the item.
	 *
	 * @return a string representation of the Item, including its
	 *         name and position.
	 */
	public String toString() {
		return "Name: " + this.name + " Position: " + this.position;
	}


}
