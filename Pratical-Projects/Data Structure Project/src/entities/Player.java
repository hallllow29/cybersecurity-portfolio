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

import lib.exceptions.EmptyCollectionException;

/**
 * Represents a player character in the game.
 * The Player class encapsulates attributes and actions specific to player entities,
 * such as their current position, equipped items, health management, and backpack for item storage.
 * It extends the Character class, inheriting the basic properties of a game character like health and fire power.
 */
public class Player extends Character {

	/**
	 * Represents the current position of the player in the game.
	 * This variable holds a reference to the entities.Room object where the player is currently located.
	 */
	private Room position;

	/**
	 * Represents the player's backpack.
	 * This variable holds a reference to the entities.BackPack object containing
	 * the items the player has collected in the game.
	 */
	private final BackPack back_pack;

	/**
	 * Represents the Kevlar item currently equipped by the player.
	 * The Kevlar provides additional health points to enhance the player's durability.
	 * It can be equipped by the player to gain an advantage in the game.
	 */
	private Kevlar kevlar;

	/**
	 * Constructs a new Player instance with the specified name, fire power, and backpack.
	 * This constructor initializes the player with a given name and fire power,
	 * assigns the provided backpack object for storing items, and sets the player's
	 * initial position and equipped Kevlar to null.
	 *
	 * @param name       the name of the player
	 * @param fire_power the fire power of the player
	 * @param back_pack  the backpack object used for storing items
	 */
	public Player(String name, int fire_power, BackPack back_pack) {
		super(name, fire_power);
		this.position = null;
		this.back_pack = back_pack;
		this.kevlar = null;
	}

	/**
	 * Retrieves the current position of the player in the room.
	 *
	 * @return the current position of the player in the room
	 */
	public Room getPosition() {
		return this.position;
	}

	/**
	 * Sets the current position of the player in the specified room.
	 *
	 * @param position the new room where the player will be positioned
	 */
	public void setPosition(Room position) {
		this.position = position;
	}

	/**
	 * Equips the player with a Kevlar item, increasing their current health points
	 * based on the extra health provided by the Kevlar. Updates the player's current
	 * health and returns a message confirming the action.
	 *
	 * @param kevlar the Kevlar item to be equipped, providing additional health points
	 * @return a confirmation message indicating that the Kevlar has been equipped and the updated health
	 */
	public String equipKevlar(Kevlar kevlar) {
		this.kevlar = kevlar;

		super.setCurrentHealth(super.getCurrentHealth() + this.kevlar.getExtraHp());

		return super.getName() + " equipped a kevlar! HP :" + super.getCurrentHealth();
	}

	/**
	 * Adds a MediKit item to the player's backpack. If the backpack is already at its maximum capacity,
	 * the MediKit will not be added, and the operation will not proceed.
	 *
	 * @param kit the MediKit to be added to the player's backpack
	 */
	public void addKitToBackPack(MediKit kit) {
		this.back_pack.addKit(kit);
	}

	/**
	 * Uses a MediKit from the player's backpack to restore health. If the backpack is
	 * empty, an EmptyCollectionException will be thrown. The MediKit restores health
	 * points up to a maximum of 100. If the player's health is already at or above 100,
	 * no health is restored, and a relevant message is returned.
	 *
	 * @return a message indicating the result of using the MediKit. Possible outcomes
	 * include a successful health restoration message or a message stating that the MediKit
	 * cannot be used when health is full.
	 * @throws EmptyCollectionException if there are no MediKits available in the backpack
	 */
	public String useMediKit() throws EmptyCollectionException {
		boolean backpackEmpty = this.back_pack.isBackPackEmpty();

		if (backpackEmpty) {
			throw new EmptyCollectionException("There are no MediKits available to use!");
		}

		if (super.getCurrentHealth() >= 100 ) {
			return "You can't use medikits because your health is already full";
		}

		MediKit kit = back_pack.useKit();

		int newHealth = Math.min(super.getCurrentHealth() + kit.getHealPower(), 100);
		super.setCurrentHealth(newHealth);

		return "Medic kit used! HP: " + super.getCurrentHealth() + "/100";
	}

	/**
	 * Determines whether the player has any recovery items available in their backpack.
	 * This method checks the player's backpack to see if it contains items that can
	 * be used for recovery purposes.
	 *
	 * @return true if the backpack contains recovery items, false otherwise
	 */
	public boolean hasRecoveryItem() {
		return !this.back_pack.isBackPackEmpty();
	}

	/**
	 * Determines whether the player requires the use of a recovery item, such as a MediKit,
	 * based on their current health level and the availability of recovery items in their backpack.
	 * A player is considered to need a recovery item if their health is below or equal to
	 * the critical health threshold (80) and they have at least one recovery item.
	 *
	 * @return true if the player's health is at or below the critical threshold and a recovery
	 *         item is available in the backpack, false otherwise
	 */
	public boolean playerNeedsRecoveryItem() {

		int playerCriticalHealth = 80;

		/**
		 * Se TO CRUZ decide usar um kit...
		 */
		return this.getCurrentHealth() <= playerCriticalHealth && this.hasRecoveryItem();
	}

	/**
	 * Retrieves the player's backpack, which is used to store items such as MediKits.
	 *
	 * @return the BackPack instance associated with the player
	 */
	public BackPack  getBack_pack() {
		return this.back_pack;
	 }
}