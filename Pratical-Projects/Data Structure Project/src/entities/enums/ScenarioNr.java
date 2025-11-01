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
 * Enumeration representing the various scenarios or states within a simulation.
 * Each constant denotes a specific step, phase, or situation encountered during gameplay
 * or simulation progress.
 *
 * The values of this enumeration (NONE, ONE, TWO, THREE, FOUR, FIVE, SIX) are utilized to
 * determine the current scenario and drive the logic and decisions of the game or simulation.
 *
 * Scenarios can dictate player actions, enemy actions, and other in-game processes
 * depending on the current phase or condition being evaluated. They are used in determining
 * scenario transitions, executing specific behaviors, and resolving certain conditions.
 */
public enum ScenarioNr {
	NONE,
	ONE,
	TWO,
	THREE,
	FOUR,
	FIVE,
	SIX,
}
