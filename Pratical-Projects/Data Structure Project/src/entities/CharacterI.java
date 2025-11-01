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
 * This interface represents behavior and properties for a character in a game.
 * The character has a name, current health, and fire power, and can perform various
 * actions related to its state and attributes.
 */
public interface CharacterI {

    /**
     * Retrieves the name of the character.
     *
     * @return the name of the character
     */
    String getName();

    /**
     * Retrieves the current health of the entity.
     *
     * @return the current health value as an integer
     */
    int getCurrentHealth();

    /**
     * Retrieves the fire power of the character or entity.
     *
     * @return the fire power value as an integer
     */
    int getFirePower();

    /**
     * Sets the current health of the character.
     *
     * @param currentHealth the new current health value to set
     */
    void setCurrentHealth(int currentHealth);


    /**
     * Sets the fire power of the character.
     *
     * @param firePower the new fire power value*/
    void setFirePower(int firePower);

    /**
     * Reduces the current health of the character based on the damage received.
     * If the resulting health drops below 0, it will be set to 0.
     *
     * @param damage the amount of damage to inflict on the character
     */
    void takesDamageFrom(int damage);

    /**
     * Determines whether the character is alive based on their current health.
     *
     * @return true if the character's current health is greater than 0, else false
     */
    boolean isAlive();

}
