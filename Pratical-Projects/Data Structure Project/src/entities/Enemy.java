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
 * Represents an enemy character within the game that extends the {@code Character} class.
 * The {@code Enemy} class includes additional properties for tracking the current
 * position of the enemy and implements the {@code Comparable} interface to compare
 * enemies based on their fire power.
 */
public class Enemy extends Character implements Comparable<Enemy> {

	/**
	 * Represents the current position of the enemy as a {@code Room} object.
	 * This variable tracks the specific location or room where the enemy is situated
	 * within the game's environment.
	 */
	private Room position;

	/**
	 * Constructs a new Enemy with the specified name, fire power, and current position.
	 *
	 * @param name the name of the enemy
	 * @param fire_power the fire power of the enemy
	 * @param position the current position of the enemy as a entities.Room object
	 */
	public Enemy(String name, int fire_power, Room position) {
		super(name, fire_power);
		this.position = position;
	}

	/**
	 * Retrieves the current position of the enemy.
	 *
	 * @return the current position of the enemy as a entities.Room object
	 */
	public Room getPosition() {
		return this.position;
	}

	/**
	 * Updates the current position of the enemy.
	 *
	 * @param position the new position of the enemy as a {@code Room} object
	 */
	public void setPosition(Room position) {
		this.position = position;
	}

	/**
	 * Compares this enemy to another enemy based on their fire power.
	 *
	 * @param other the other enemy to compare with
	 * @return a negative integer, zero, or a positive integer as this enemy's fire power
	 *         is less than, equal to, or greater than the specified enemy's fire power
	 */
	@Override
	public int compareTo(Enemy other) {
		return Integer.compare(super.getFirePower(), other.getFirePower());
	}

	/**
	 * Returns a string representation of the enemy, including the name and fire power.
	 *
	 * @return a string containing the name and fire power of the enemy
	 */
	@Override
	public String toString(){
		return "Name: " + super.getName() + " Fire Power: " + super.getFirePower() + " Position: " + this.getPosition();
	}
}
