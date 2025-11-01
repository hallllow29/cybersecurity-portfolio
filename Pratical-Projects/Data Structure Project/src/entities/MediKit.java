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
 * Represents a MediKit, an item in a game that can restore health points to an entity.
 * The MediKit is a specific type of item that has a healing power attribute.
 */
public class MediKit extends Item {

	/**
	 * The amount of health points that the entities.MediKit can restore.
	 */
	private final int heal_power;

	/**
	 * Constructs a new entities.MediKit with the specified name, position, and healing
	 * power.
	 *
	 * @param name       the name of the entities.MediKit.
	 * @param position   the initial position of the entities.MediKit as a entities.Room
	 *                   object.
	 * @param heal_power the healing power of the entities.MediKit.
	 */
	public MediKit(String name, Room position, int heal_power) {
		super(name, position);
		this.heal_power = heal_power;
	}

	/**
	 * Retrieves the healing power of the entities.MediKit.
	 *
	 * @return the healing power of the entities.MediKit as an integer.
	 */
	public int getHealPower() {
		return this.heal_power;
	}

	/**
	 * Retrieves the name of the MediKit item.
	 *
	 * @return the name of the MediKit as a String.
	 */
	@Override
	public String getName() {
		return "MediKit";
	}

	/**
	 * Retrieves the value of the item.
	 *
	 * @return the item value as an integer.
	 */
	@Override
	public int getItemValue() {
		return this.heal_power;
	}

	/**
	 * Returns a string representation of the MediKit object. The returned string
	 * includes the name and position of the MediKit from the superclass's toString
	 * method, followed by the healing power of the MediKit.
	 *
	 * @return a string representation of the MediKit, including its name, position,
	 *         and healing power.
	 */
	public String toString() {
		return super.toString() + " HP: " + this.getHealPower();
 	}

}