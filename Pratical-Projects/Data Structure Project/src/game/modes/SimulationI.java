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
package game.modes;

import entities.*;
import entities.enums.ScenarioNr;
import entities.enums.Turn;
import entities.Mission;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.graphs.CustomNetwork;
import lib.lists.LinkedList;

/**
 * Interface defining the structure and behavior of a simulation.
 * It provides methods for player movement, game state management,
 * and access to key elements of the simulation such as the battlefield,
 * player, enemies, and missions.
 */
public interface SimulationI {

    /**
     * Moves the player to the next location in the simulation based on
     * the current game state and objectives. This method handles the logic
     * for updating the player's position and interacting with the game elements
     * such as rooms, items, and enemies.
     *
     * @throws ElementNotFoundException if the required element, such as a room
     *                                   or path, is not found during the operation.
     * @throws EmptyCollectionException if the collection of elements needed to
     *                                   perform the move is empty.
     */
    void movePlayer() throws ElementNotFoundException, EmptyCollectionException;

    /**
     * Executes the main game logic for the simulation. This method is responsible for
     * managing the overall progression of the game, coordinating player interactions,
     * handling game states, and invoking any necessary sub-processes to advance the
     * simulation. It may involve interactions with the battlefield, enemies, objectives,
     * and other game elements.
     *
     * @throws ElementNotFoundException if an expected element within the game or simulation
     *         cannot be found, such as a missing enemy, item, or objective.
     * @throws EmptyCollectionException if a required collection, such as a list of enemies
     *         or items, is empty during a critical operation.
     */
    void game() throws ElementNotFoundException, EmptyCollectionException;

    /**
     * Determines whether the player is currently returning to the entry point or designated exit in the simulation.
     *
     * @return true if the player is returning to the exit, false otherwise
     */
    boolean isReturningToExit();

    /**
     * Checks whether the game is over.
     *
     * @return true if the game has ended, otherwise false.
     */
    boolean isGameOver();

    /**
     * Retrieves the battlefield of the current simulation.
     * The battlefield is represented as a custom network structure
     * containing rooms, where rooms serve as nodes in the network.
     * This method provides access to the underlying representation
     * of the simulated battlefield.
     *
     * @return a CustomNetwork of type Room representing the battlefield
     */
    CustomNetwork<Room> getBattleField();

    /**
     * Retrieves the list of enemies within the simulation.
     *
     * @return a LinkedList containing Enemy objects representing the current enemies in the simulation
     */
    LinkedList<Enemy> getEnemies();

    /**
     * Retrieves the player object associated with the current simulation.
     *
     * @return the Player instance representing the current player in the simulation
     */
    Player getPlayer();

    /**
     * Retrieves the mission associated with the simulation.
     *
     * @return the current mission of the simulation, encapsulating the objectives, battlefield, and entities involved.
     */
    Mission getMission();

    /**
     * Retrieves the next objective room in the simulation.
     * The next objective represents the location or point of interest
     * that the player should navigate towards to progress in the game
     * or simulation. This may be defined by game logic or the current mission.
     *
     * @return the next objective as a Room object, or null if no objective is set
     */
    Room getNextObjective();

    /**
     * Retrieves the current turn in the simulation.
     *
     * @return the current turn, represented as a value of the Turn enum.
     *         It indicates whether it is currently the player's turn
     *         (Turn.PLAYER) or the enemy's turn (Turn.ENEMY) in the game flow.
     */
    Turn getCurrentTurn();

    /**
     * Retrieves the current scenario of the simulation.
     *
     * @return the current scenario of type ScenarioNr, representing the
     *         active state or phase of the simulation.
     */
    ScenarioNr getCurrentScenario();

    /**
     * Sets the game's over state.
     *
     * @param gameOver a boolean value representing the new game over state.
     *                 Set to true if the game is over, otherwise false.
     */
    void setGameOver(boolean gameOver);

    /**
     * Sets the entry point of the simulation to the specified room.
     *
     * @param entryPoint the Room object representing the starting point of the simulation
     */
    void setEntryPoint(Room entryPoint);

    /**
     * Sets the next objective room in the simulation.
     *
     * @param nextObjective the next Room to be designated as the objective
     */
    void setNextObjective(Room nextObjective);

}