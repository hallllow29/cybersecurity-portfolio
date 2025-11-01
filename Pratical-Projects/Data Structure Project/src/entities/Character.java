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
 * Represents a generic character in the game.
 * This is an abstract class that provides basic properties and behavior
 * for characters, including their name, health, and fire power. Concrete
 * classes such as Player or Enemy can extend this class to provide
 * specific implementations and additional functionality.
 */
public abstract class Character implements CharacterI {

    /**
     * The name of the character.
     * This variable stores the unique identifier or title designated to the character.
     */
    private final String name;

    /**
     * Represents the current health of the character.
     * This value indicates how much health the character has remaining.
     */
    private int current_health;

    /**
     * Represents the offensive capability or attack strength of a character.
     * This value determines the damage a character can inflict on opponents.
     */
    private int fire_power;

    /**
     * Constructs a new Character with the specified name and fire power.
     *
     * @param name the name of the character
     * @param fire_power the fire power of the character
     */
    public Character(String name, int fire_power) {
        this.name = name;
        this.current_health = 100;
        this.fire_power = fire_power;
    }

    /**
     * Retrieves the name of the character.
     *
     * @return the name of the character
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the current health of the character.
     *
     * @return the current health value of the character
     */
    public int getCurrentHealth() {
        return this.current_health;
    }

    /**
     * Retrieves the fire power of the character.
     *
     * @return the fire power value of the character
     */
    public int getFirePower() {
        return this.fire_power;
    }


    /**
     * Sets the current health of the character.
     *
     * @param current_health the new current health value to set
     */
    public void setCurrentHealth(int current_health) {
        this.current_health = current_health;
    }

    /**
     * Sets the fire power of the character.
     *
     * @param fire_power the new fire power value to set
     */
    public void setFirePower(int fire_power) {
        this.fire_power = fire_power;
    }

    /**
     * Reduces the current health of the character based on the damage received.
     * If the resulting health is less than 0, it will be set to 0.
     *
     * @param damage the amount of damage to inflict on the character
     */
    public void takesDamageFrom(int damage) {
        this.current_health -= damage;

        if (this.current_health < 0) {
            this.current_health = 0;
        }
    }

    /**
     * Checks if the character is alive based on their current health.
     *
     * @return true if the character's current health is greater than 0, otherwise false
     */
    public boolean isAlive() {
        return this.current_health > 0;
    }

    /**
     * Returns a string representation of the character, including its name, current health,
     * and fire power.
     *
     * @return a string representing the character's name, current health, and fire power
     */
    public String toString() {
        return "Name :" + this.name +
                "\nHP: " + this.current_health +
                "\nFire power: " + this.fire_power;
    }

    /**
     * Compares this character to the specified object for equality.
     * The comparison is based on the {@code name} attribute of the character.
     *
     * @param obj the object to be compared with this character for equality
     * @return true if the specified object is equal to this character, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null || getClass() != obj.getClass()){
            return false;
        }

        Character other = (Character) obj;
        return this.name.equals(other.name);
    }
}
