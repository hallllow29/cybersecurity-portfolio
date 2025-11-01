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
package entities.enums;

/**
 * The Turn enum represents the two possible states in a turn-based simulation:
 * PLAYER and ENEMY. It is used to determine whose turn it is within the game flow.
 *
 * Turn.PLAYER indicates that it is the player's turn to perform actions.
 * Turn.ENEMY indicates that it is the enemy's turn to perform actions.
 *
 * This enum is integral to the game's turn-based mechanics, controlling the
 * sequencing of actions and ensuring proper alternation between the player and the enemies.
 */
public enum Turn {
    PLAYER,
    ENEMY;
}
