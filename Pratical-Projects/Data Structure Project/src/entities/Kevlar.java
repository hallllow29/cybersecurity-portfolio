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
 * Represents a Kevlar item in a game that provides additional health points to
 * the player or entity equipping it. This class extends the abstract Item class
 * and overrides specific methods to define the unique behavior of Kevlar.
 */
public class Kevlar extends Item {

	/**
	 * The amount of extra health points provided by the entities.Kevlar item.
	 */
	private final int extra_hp;

	/**
	 * Constructs a new entities.Kevlar item with the specified name, position, and extra health points.
	 *
	 * @param name the name of the entities.Kevlar item.
	 * @param position the initial position of the entities.Kevlar item as a entities.Room object.
	 * @param extra_hp the extra health points provided by the entities.Kevlar item.
	 */
	public Kevlar(String name, Room position, int extra_hp) {
		super(name, position);
		this.extra_hp = extra_hp;
	}

	/**
	 * Retrieves the extra health points provided by the entities.Kevlar item.
	 *
	 * @return the extra health points as an integer.
	 */
	public int getExtraHp() {
		return extra_hp;
	}

	/**
	 * Retrieves the value of the item, which in this implementation corresponds to
	 * the extra health points provided by the Kevlar item.
	 *
	 * @return the extra health points provided by the Kevlar item as an integer.
	 */
	@Override
	public int getItemValue() {
		return this.extra_hp;
	}

	/**
	 * Retrieves the name of the item, which is always "Kevlar" for this class.
	 *
	 * @return the name "Kevlar" as a String.
	 */
	@Override
	public String getName() {
		return "Kevlar";
	}

	/**
	 * Returns a string representation of the Kevlar object, including its superclass string
	 * representation and the extra health points provided by this Kevlar item.
	 *
	 * @return a string representation of the Kevlar object, including the name, current
	 *         position, and the additional health points it provides.
	 */
	public String toString() {
		return super.toString() + " Extra hp: " + this.extra_hp;
	}
}
