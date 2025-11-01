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
import game.briefings.Report;
import game.io.Display;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.graphs.CustomNetwork;
import lib.graphs.Network;
import lib.lists.ArrayList;
import lib.lists.ArrayUnorderedList;
import lib.lists.LinkedList;


import java.util.Iterator;
import java.util.Random;

/**
 * The Simulation class represents the core functionality to simulate a strategic
 * mission-based game. It provides methods and mechanisms to handle player actions, enemy
 * movements, and overall game progression. The class manages the state of the simulation,
 * including the battlefield, player status, mission objectives, and various other
 * scenarios.
 */
public abstract class Simulation implements SimulationI {

	/**
	 * Represents the mission instance associated with the simulation. Provides essential
	 * details and components of the mission, including battlefield information, targets,
	 * enemies, items, and entry/exit points. This variable works as a core element for
	 * managing and executing the mission's objectives and scenarios during the
	 * simulation.
	 */
	private final Mission mission;
	/**
	 * Represents the battlefield used in the simulation, which is a network of
	 * interconnected rooms. The battlefield is managed as a custom network of rooms and
	 * is used to simulate the environment in which game scenarios, including player and
	 * enemy movements and interactions, take place.
	 */
	private CustomNetwork<Room> battlefield;
	/**
	 * Represents the player participating in the simulation. This variable holds a
	 * reference to the Player instance used during the simulation, encapsulating the
	 * player's attributes, position, inventory, and actions. It is declared as final,
	 * ensuring that the player reference cannot be re-assigned.
	 */
	private final Player player;

	/**
	 * Represents the state of the game, indicating whether the game has ended. This
	 * variable is used to manage the flow of the simulation by checking if the game
	 * conditions have been met to mark its completion.
	 * <p>
	 * A value of {@code true} indicates that the game is over, and no further actions or
	 * turns can be taken. Conversely, a value of {@code false} indicates that the game is
	 * still ongoing.
	 */
	private boolean gameOver;

	/**
	 * Represents the starting room or initial point of entry in the simulation. The
	 * entryPoint serves as the location where the simulation begins, typically
	 * positioning the player at the start of the gameplay or mission.
	 */
	private Room entryPoint;

	/**
	 * Represents the designated room where the player must reach to complete the mission.
	 * This variable is used to store the extraction point within the simulation,
	 * typically marking the end goal or final destination in the gameplay.
	 */
	private Room extractionPoint;

	/**
	 * Represents the state of whether the player is currently returning to the extraction
	 * point in the simulation. This variable is used to track the phase of the mission
	 * where the player has completed their main objective and is navigating towards the
	 * designated exit point.
	 */
	private boolean returningToExtraction;

	/**
	 * Represents the current turn in the simulation, determining whose actions will be
	 * executed in the ongoing game cycle.
	 * <p>
	 * The possible values for this field depend on the Turn enum, which includes:
	 * - Turn.PLAYER: Indicates that it is the player's turn to perform actions.
	 * - Turn.ENEMY: Indicates that it is the enemy's turn to act.
	 */
	private Turn currentTurn;

	/**
	 * Represents the current scenario or state within the simulation.
	 * <p>
	 * This variable is used to track the ongoing phase or situation of the gameplay or
	 * simulation. It determines the current scenario logic, behavior, or sequence of
	 * actions in the simulation, and it can trigger the execution of specific conditions
	 * or transitions to subsequent scenarios.
	 */
	private ScenarioNr currentScenario;

	/**
	 * Indicates whether the mission within the simulation has been successfully
	 * completed. This variable is set to true when all conditions for mission success are
	 * achieved, such as reaching a target, securing objectives, or meeting
	 * scenario-specific goals. It is false by default and reflects the current status of
	 * the mission during the simulation.
	 */
	private boolean missionAccomplished;

	/**
	 * Represents the next objective or target room in the simulation. This variable keeps
	 * track of the Room instance that is the player's current target or goal, guiding the
	 * progression of the simulation's objectives. Updates occur based on the game's
	 * scenarios, player actions, or mission stages.
	 */
	private Room nextObjective;

	/**
	 * A collection of enemies currently present within the simulation. This LinkedList
	 * stores instances of the {@code Enemy} class, where each enemy represents a hostile
	 * entity that the player may encounter during the game. The list is used to manage
	 * and track the enemies' states, positions, and interactions within the game's
	 * environment.
	 */
	private LinkedList<Enemy> enemies;

	/**
	 * Represents the report associated with the simulation. This report maintains the
	 * details of the simulation lifecycle, such as mission status, player performance,
	 * enemies confronted, and paths traversed during the mission.
	 */
	private final Report report;

	/**
	 * Constructs a Simulation instance which initializes the game with the given mission,
	 * player, and report.
	 *
	 * @param mission The mission associated with the simulation, containing details about
	 *                the battlefield, enemies, and items.
	 * @param player  The player participating in the simulation.
	 * @param report  The report that will record the progress and outcomes of the
	 *                simulation.
	 */
	public Simulation(Mission mission, Player player, Report report) {
		this.mission = mission;
		this.battlefield = new CustomNetwork<>();
		this.battlefield = mission.getBattlefield();
		this.player = player;
		this.currentTurn = Turn.PLAYER;
		this.missionAccomplished = false;
		this.enemies = new LinkedList<>();
		this.enemies = mission.getEnemies();
		this.report = report;
	}

	/**
	 * Moves the player to a new position within the simulation. This method is
	 * responsible for determining and executing the player's movement within the
	 * battlefield based on the current state of the game.
	 *
	 * @throws ElementNotFoundException if a required element, such as the target room or
	 *                                  path, is not found during the movement process.
	 * @throws EmptyCollectionException if the collection used for determining the
	 *                                  player's movement, such as paths or possible
	 *                                  moves, is empty.
	 */
	public abstract void movePlayer() throws ElementNotFoundException, EmptyCollectionException;

	/**
	 * Initiates and oversees the core gameplay loop within the simulation. This method is
	 * responsible for managing the sequential flow of a simulation session, executing
	 * actions and managing the state of the game until its conclusion. It integrates
	 * player actions, game state updates, and progression within the mission being
	 * simulated.
	 *
	 * @throws ElementNotFoundException if a required component within the simulation,
	 *                                  such as a target, enemy, or room, cannot be
	 *                                  located during the execution of the game logic.
	 * @throws EmptyCollectionException if an operation within the simulation attempts to
	 *                                  access or manipulate an empty collection critical
	 *                                  to the gameplay mechanics (e.g., paths, enemies,
	 *                                  or items).
	 */
	public abstract void game() throws ElementNotFoundException, EmptyCollectionException;

	/**
	 * Manages the core flow of the game's turn-based mechanics. This method is
	 * responsible for alternating between the player's turn and the enemy's turn until
	 * the game is marked as over. It checks for mission success conditions and updates
	 * the game report upon completion.
	 *
	 * @throws EmptyCollectionException if an operation within the player's or enemy's
	 *                                  turn attempts to access or manipulate an empty
	 *                                  collection critical to the game flow.
	 * @throws ElementNotFoundException if a required element (such as a path, item, or
	 *                                  enemy) cannot be located during the execution of
	 *                                  the player's or enemy's turn.
	 */
	protected void gameFlow() throws EmptyCollectionException, ElementNotFoundException {
		while (!isGameOver()) {


			if (this.getCurrentTurn() == Turn.PLAYER) {
				playerTurn();
			}

			if (isMissionAccomplished()) {
				this.setGameOver(true);
			}

			enemyTurn();

		}

		addStatusToReport();

		// Count enemies which survivived.
		for (Enemy enemy : getEnemies()) {
			this.getReport().addEnemy(enemy.getName());
		}

	}

	/**
	 * Executes the player's turn in the current simulation. This method is responsible
	 * for managing the logic of the player's actions and their interactions with the game
	 * environment, including handling scenarios based on the player's position,
	 * confronting enemies, and moving to the next location.
	 * <p>
	 * If the player is in a room without enemies or in a specific scenario, the player
	 * moves to another room as determined by the game's logic. Otherwise, additional
	 * scenario-based logic is triggered. After the primary actions of the player's turn,
	 * scenario-specific situations and cases are evaluated.
	 *
	 * @throws ElementNotFoundException if a required element is not found.
	 * @throws EmptyCollectionException if an operation is performed on an empty
	 *                                  collection.
	 */
	protected void playerTurn() throws ElementNotFoundException, EmptyCollectionException {
		Room playerPosition = this.getPlayer().getPosition();

		String playerTurnOutput = "";
		if (!playerPosition.hasEnemies() || this.getCurrentScenario() == ScenarioNr.TWO) {
			playerTurnOutput += Display.playerIsLeavingMessage(player.getName(), playerPosition.getName());

			movePlayer();
		} else if (isMissionAccomplished()) {
			scenarioSEIS();
		} else {
			scenarioUM();
		}

		System.out.println(playerTurnOutput);

		// "o jogo se me sequência de ações"
		scenariosSituations();
		scenariosCase(this.getCurrentScenario());
	}

	/**
	 * Checks if the player is currently returning to the extraction point.
	 *
	 * @return true if the player is returning to the extraction point, false otherwise.
	 */
	public boolean isReturningToExit() {
		return this.returningToExtraction;
	}

	/**
	 * Renders the automatic simulation by setting up the next objective, determining the
	 * best entry point, and positioning the player within the game. This method manages
	 * the initial setup and state configuration for an automated simulation process.
	 *
	 * @param player The player participating in the simulation. The player's position is
	 *               updated to the calculated entry point as part of the simulation
	 *               setup.
	 * @param target The target object specifying the next objective room for the
	 *               simulation. The objective room is set to guide the player's
	 *               progression.
	 * @throws ElementNotFoundException If the entry point or required simulation element
	 *                                  cannot be determined or located during
	 *                                  initialization.
	 */
	public void renderAutomaticSimulation(Player player, Target target) throws ElementNotFoundException {
		setNextObjective(target.getRoom());
		entryPoint = findBestEntryPoint();
		setEntryPoint(entryPoint);
		player.setPosition(entryPoint);
		missionAccomplished = false;
		currentTurn = Turn.PLAYER;
		gameOver = false;
	}

	/**
	 * Prepares and starts a manual simulation for the given target room in the game. This
	 * involves setting the next objective, initializing the game state, and configuring
	 * the appropriate turn structure for the simulation.
	 *
	 * @param target The room that the player is tasked to move toward as the next
	 *               objective in the manual simulation.
	 */
	public void renderManualSimulation(Room target) {
		setNextObjective(target);
		missionAccomplished = false;
		currentTurn = Turn.PLAYER;
		gameOver = false;
	}

	/**
	 * Retrieves the report associated with the current simulation.
	 *
	 * @return the report object that contains information about the progress, outcomes,
	 * and details of the simulation.
	 */
	public Report getReport() {
		return this.report;
	}

	/**
	 * Retrieves the battlefield associated with the current simulation. The battlefield
	 * is represented as a custom network of rooms where the simulation takes place.
	 *
	 * @return the CustomNetwork of Room objects representing the battlefield.
	 */
	public CustomNetwork<Room> getBattleField() {
		return this.battlefield;
	}

	/**
	 * Sets the game over status for the simulation.
	 *
	 * @param gameOver A boolean indicating whether the game is over. If true, the
	 *                 simulation is marked as completed.
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Retrieves the current scenario of the simulation. The current scenario represents
	 * the specific phase or condition the simulation is currently in, which may influence
	 * gameplay, mission objectives, or other aspects of the simulation process.
	 *
	 * @return the current scenario of type ScenarioNr that corresponds to the current
	 * phase or state of the simulation.
	 */
	public ScenarioNr getCurrentScenario() {
		return this.currentScenario;
	}

	/**
	 * Retrieves the list of enemies currently present in the simulation. The list
	 * contains all enemies currently active and relevant within the game's battlefield.
	 *
	 * @return a LinkedList of Enemy objects representing the enemies in the simulation.
	 */
	public LinkedList<Enemy> getEnemies() {
		return this.enemies;
	}

	/**
	 * Retrieves the player associated with the current simulation.
	 *
	 * @return the Player object representing the player in the simulation.
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Retrieves the mission associated with the current simulation.
	 *
	 * @return the Mission object representing the mission details of the simulation.
	 */
	public Mission getMission() {
		return this.mission;
	}

	/**
	 * Retrieves the next objective room for the simulation. The next objective represents
	 * the target room that the player is currently tasked to move toward or interact with
	 * during the mission.
	 *
	 * @return the Room object representing the next objective in the simulation.
	 */
	public Room getNextObjective() {
		return this.nextObjective;
	}

	/**
	 * Sets the entry point for the simulation. The entry point represents the starting
	 * room or location in the battlefield where the simulation begins.
	 *
	 * @param entryPoint The Room object representing the entry point for the simulation.
	 */
	public void setEntryPoint(Room entryPoint) {
		this.entryPoint = entryPoint;
	}

	/**
	 * Sets the next objective for the current entity.
	 *
	 * @param nextObjective The Room object representing the next objective to be
	 *                      assigned.
	 */
	public void setNextObjective(Room nextObjective) {
		this.nextObjective = nextObjective;
	}

	/**
	 * Checks whether the current game state indicates that the game is over.
	 *
	 * @return true if the game is over, false otherwise.
	 */
	public boolean isGameOver() {
		return this.gameOver;
	}

	/**
	 * Sets the next turn for the game or process by updating the current turn.
	 *
	 * @param nextTurn the Turn object representing the next turn to be set
	 */
	protected void setNextTurn(Turn nextTurn) {
		this.currentTurn = nextTurn;
	}

	/**
	 * Retrieves the entry point of the room.
	 *
	 * @return the entry point room instance.
	 */
	protected Room getEntryPoint() {
		return this.entryPoint;
	}

	/**
	 * Retrieves the current turn of the game or application.
	 *
	 * @return the current turn as a Turn object
	 */
	public Turn getCurrentTurn() {
		return this.currentTurn;
	}

	/**
	 * Executes the enemy's turn in the game. This method is called during the game loop
	 * to process enemy actions based on the current scenario.
	 * <p>
	 * The method performs the following steps:
	 * 1. If the current scenario is `ScenarioNr.TWO`, it moves the player and sets the
	 * turn to `ENEMY`.
	 * 2. Calls methods to handle specific scenarios and their situations.
	 *
	 * @throws ElementNotFoundException if a required element is not found during
	 *                                  execution.
	 * @throws EmptyCollectionException if an operation involves an empty collection.
	 */
	protected void enemyTurn() throws ElementNotFoundException, EmptyCollectionException {

		if (this.currentScenario == ScenarioNr.TWO) {
			movePlayer();
			setNextTurn(Turn.ENEMY);
			// NAO FAZ SENTIDO, mas é que eu entendo!
		}
		scenariosSituations();
		scenariosCase(this.currentScenario);
	}

	/**
	 * Handles the scenarios and situations of a player's current position. Retrieves the
	 * player's current position, checks if the position contains enemies, and processes
	 * the room's situation based on the findings. This method is intended to manage game
	 * logic related to the player's current room and potential threats.
	 */
	protected void scenariosSituations() {
		Room playerPosition = player.getPosition();
		boolean playerPositionHasEnemies = playerPosition.hasEnemies();
		roomSituation(playerPosition, playerPositionHasEnemies);
	}

	/**
	 * Determines the situation of the room based on the player's position and enemy
	 * presence, and sets the next scenario accordingly.
	 *
	 * @param playerPosition The current position of the player in the room.
	 * @param hasEnemies     A boolean indicating whether there are enemies in the room.
	 */
	protected void roomSituation(Room playerPosition, Boolean hasEnemies) {

		boolean atTarget = isAtTarget(playerPosition, this.nextObjective);

		if (hasEnemies && atTarget) {
			setNextScenario(ScenarioNr.FIVE);

		} else if (hasEnemies && !atTarget) {

			/**
			 * DESPOLETADO
			 */
			setNextScenario(getCurrentTurn() == Turn.PLAYER ? ScenarioNr.ONE : ScenarioNr.THREE);

		} else if (!hasEnemies && !atTarget && !gameOver) {
			setNextScenario(ScenarioNr.TWO);
		} else {
			setNextScenario(ScenarioNr.SIX);
		}
	}

	/**
	 * Sets the next scenario for the application by updating the current scenario.
	 *
	 * @param nextScenario the scenario to set as the next current scenario
	 */
	protected void setNextScenario(ScenarioNr nextScenario) {
		this.currentScenario = nextScenario;
	}

	/**
	 * Sets the mission status to accomplished.
	 *
	 * @param missionAccomplished a boolean flag indicating whether the mission has been
	 *                            accomplished (true) or not (false)
	 */
	protected void setMissionAccomplished(boolean missionAccomplished) {
		this.missionAccomplished = missionAccomplished;
	}

	/**
	 * Executes the corresponding actions based on the provided scenario case.
	 *
	 * @param nextScenario the scenario number indicating which scenario to execute.
	 * @throws ElementNotFoundException if an expected element is missing during scenario
	 *                                  execution.
	 * @throws EmptyCollectionException if an operation is attempted on an empty
	 *                                  collection during scenario execution.
	 */
	protected void scenariosCase(ScenarioNr nextScenario) throws ElementNotFoundException {

		switch (nextScenario) {
			case TWO:
				scenarioDOIS(); setNextTurn(Turn.ENEMY); break;
			case ONE:
				scenarioUM();

				if (!this.player.isAlive()) {
					setMissionAccomplished(false);
					setGameOver(true);
				}
				break;
			case THREE:
				scenarioTRES();
				setNextTurn(Turn.PLAYER);
				break;
			case FOUR:
				scenarioQUATRO();
				setNextTurn(Turn.ENEMY);
				break;
			case FIVE:
				scenarioCINCO();
				setNextTurn(Turn.PLAYER);
				break;
			case SIX:
				scenarioSEIS();
				setNextTurn(Turn.PLAYER);
				break;
		}
	}

	/**
	 * Executes the Scenario UM sequence for the game. The method manages the scenario
	 * where the player enters a room, confronts enemies, and collects items if all
	 * enemies are defeated. It displays messages for the player's actions, the enemy's
	 * actions, and the outcome of the current turn. The method also determines the next
	 * turn in the game based on whether enemies remain in the room or not.
	 */
	protected void scenarioUM() {
		Room playerPosition = player.getPosition();
		boolean enemiesRemained;

		String scenarioUMinfo = Display.playerEntersInMessage(player.getName(), playerPosition.getName());
		scenarioUMinfo += Display.scenarioUMstartMessage(player.getName());
		scenarioUMinfo += Display.playerTurnMessage();
		System.out.print(scenarioUMinfo);

		playerConfronts();

		enemiesRemained = playerPosition.hasEnemies();

		scenarioUMinfo = "";

		if (enemiesRemained) {
			scenarioUMinfo += Display.enemyTurnMessage();

			if (this.enemies.isEmpty()) {
				scenarioUMinfo += Display.enemiesAreMovingMessage();
				moveEnemiesNotInSameRoom();
			}
			setNextTurn(Turn.ENEMY);

		} else {
			scenarioUMinfo += Display.playerEliminatedAllEnemiesInPositionMessage(player.getName(), playerPosition.getName());

			setNextTurn(Turn.PLAYER);
		}

		scenarioUMinfo += Display.scenarioUMendMessage();
		System.out.println(scenarioUMinfo);

	}

	/**
	 * Manages the flow of events in the second scenario (Scenario DOIS). This method
	 * handles the interactions and updates that occur within the scenario, including
	 * player's actions, item collection, and enemy behavior.
	 */
	private void scenarioDOIS() {
		Room playerPosition = player.getPosition();

		String scenarioDOISinfo = Display.playerIsInMessage(player.getName(), playerPosition.getName());
		scenarioDOISinfo += Display.scenarioDOISstartMessage(player.getName());
		scenarioDOISinfo += Display.playerTurnMessage();
		scenarioDOISinfo += Display.playerSearchsMessage(player.getName(), playerPosition.getName());
		System.out.print(scenarioDOISinfo);

		scenarioDOISinfo = "";

		// RECOLHE ITEMS...
		if (playerPosition.hasItems()) {
			scenarioDOISinfo = gatherItems(playerPosition);

		}

		scenarioDOISinfo += Display.enemyTurnMessage();

		if (!this.enemies.isEmpty()) {

			scenarioDOISinfo += Display.enemiesAreMovingMessage();
			moveEnemies();

		} else {

			scenarioDOISinfo += Display.noEnemiesLeftMessage();
		}

		scenarioDOISinfo += Display.scenarioDOISendMessage();

		System.out.println(scenarioDOISinfo);
	}

	/**
	 * Executes the logic for scenario three (TRES) within the game. This method handles
	 * player and enemy interactions, turn-based confrontation sequences, and checks for
	 * victory or defeat conditions. The following actions occur:
	 * <p>
	 * 1. Initializes the scenario with introductory messages and enemy confrontation
	 * setup.
	 * 2. Executes the combat loop while the player is alive and enemies are present in
	 * the room:
	 * - Player's turn: Processes player actions in the confrontation.
	 * - Updates the scenario state and triggers enemy responses.
	 * - Moves enemies not in the same room closer to engage the player.
	 * 3. Concludes the scenario when all enemies are defeated or the player dies:
	 * - Displays appropriate victory or defeat messages.
	 * - Sets game over status if the player has died.
	 */
	private void scenarioTRES() {
		Room playerPosition = player.getPosition();

		String scenarioTRESinfo = Display.playerEntersInMessage(player.getName(), playerPosition.getName());
		scenarioTRESinfo += Display.scenarioTRESstartMessage(player.getName());
		scenarioTRESinfo += Display.enemyTurnMessage();
		scenarioTRESinfo += Display.enemiesEngageConfront(playerPosition.getName());
		System.out.print(scenarioTRESinfo);

		// Tem um trigger para o scenario 4 se o to cruz precisar de items...
		enemiesConfronts(player);

		while (playerPosition.hasEnemies() && player.isAlive()) {

			if (this.currentScenario != ScenarioNr.FOUR &&
				this.currentScenario == ScenarioNr.THREE && player.isAlive()) {

				scenarioTRESinfo = Display.playerTurnMessage();
				System.out.print(scenarioTRESinfo);
				playerConfronts();
			}

			scenarioTRESinfo = "";
			setNextScenario(ScenarioNr.THREE);
			scenarioTRESinfo += Display.enemyTurnMessage();

			if (!playerPosition.hasEnemies()) {
				scenarioTRESinfo += Display.enemiesAreMovingMessage();
				moveEnemiesNotInSameRoom();
				System.out.print(scenarioTRESinfo);
				break;
			}
			else if (!this.enemies.isEmpty()) {
				scenarioTRESinfo += Display.enemiesNotIsTheSamePositionMessage(playerPosition.getName());
				System.out.print(scenarioTRESinfo);
				scenarioTRESinfo= "";

				moveEnemiesNotInSameRoom();
				enemiesConfronts(player);
			} else {
				scenarioTRESinfo += Display.noEnemiesLeftMessage();
			}
			System.out.print(scenarioTRESinfo);
			scenarioTRESinfo = "";
		}

		scenarioTRESinfo = "";
		scenarioTRESinfo += Display.playerTurnMessage();

		if (!playerPosition.hasEnemies() && player.isAlive()) {
			scenarioTRESinfo += Display.playerEliminatedAllEnemiesInPositionMessage(player.getName(), playerPosition.getName());

		} else if (!player.isAlive()) {
			scenarioTRESinfo += Display.playerDiedMessage(player.getName());
			setGameOver(true);
		}

		scenarioTRESinfo += Display.scenarioTRESendMessage();
		System.out.println(scenarioTRESinfo);
	}

	/**
	 * Represents the implementation of the Scenario QUATRO game logic. This scenario
	 * handles the following sequence of events:
	 * - Displays a start message including the player's name.
	 * - Simulates the player's turn with appropriate messages.
	 * - Includes a message when the player checks their backpack.
	 * - Attempts to call the player's use of a MediKit. If the MediKit is unavailable, an
	 * EmptyCollectionException is caught and its message is logged via standard error
	 * output.
	 * - Displays an end message signifying the conclusion of the scenario.
	 * - Sets the next turn to the enemy.
	 * <p>
	 * Method execution is dependent on external systems such as the Display class for
	 * constructing messages and the Player instance for invoking actions.
	 */
	protected void scenarioQUATRO() {

		String scenarioQUATROinfo = Display.scenarioQUATROstartMessage(player.getName());
		scenarioQUATROinfo += Display.playerTurnMessage();
		scenarioQUATROinfo += Display.playerChecksBackPackMessage(player.getName());

		try {
			scenarioQUATROinfo += "\n|\t" + this.player.useMediKit();
		} catch (EmptyCollectionException e) {
			System.err.println(e.getMessage());
		}

		scenarioQUATROinfo += Display.scenarioQUATROendMessage();
		System.out.print(scenarioQUATROinfo);

		setNextTurn(Turn.ENEMY);
	}

	/**
	 * Handles the "Scenario CINCO" logic in the game.
	 * <p>
	 * This method represents a specific scenario where the player enters a room and
	 * confronts potential enemies. The following actions are performed:
	 * <p>
	 * - The player's current position is retrieved.
	 * - A series of informational messages is displayed to describe the events occurring
	 * during the scenario, including the player's actions, enemy actions, and the overall
	 * scenario progression.
	 * - The player initiates a confrontation.
	 * - Checks are made to determine if enemies are still present in the room.
	 * - If enemies remain, appropriate messages are displayed describing the state of the
	 * enemies and their actions, such as taking their turn or moving.
	 * - Final messages summarizing the end of the scenario are displayed.
	 */
	private void scenarioCINCO() {
		Room playerPosition = player.getPosition();
		boolean enemiesRemained;

		String scenarioCINCOinfo = Display.playerEntersInMessage(player.getName(), playerPosition.getName());
		scenarioCINCOinfo += Display.scenarioCINCOstartMessage(player.getName());
		scenarioCINCOinfo += Display.playerTurnMessage();
		System.out.print(scenarioCINCOinfo);

		playerConfronts();

		scenarioCINCOinfo = "";
		enemiesRemained = playerPosition.hasEnemies();

		if (enemiesRemained) {
			scenarioCINCOinfo += Display.enemyTurnMessage();
			scenarioCINCOinfo += Display.enemiesSurvivedAttackMessage(playerPosition.getName());

			if (!this.enemies.isEmpty()) {
				scenarioCINCOinfo += Display.enemiesAreMovingMessage();

			}
		} else {
			scenarioCINCOinfo += Display.playerEliminatedAllEnemiesInPositionMessage(player.getName(), playerPosition.getName());
		}

		scenarioCINCOinfo += Display.scenarioCINCOendMessage();
		System.out.println(scenarioCINCOinfo);
	}

	/**
	 * Executes the scenarioSEIS sequence in the game. This method handles the flow of
	 * events when the player enters the scenario, including interactions with the
	 * environment and determining the outcome of the scenario.
	 *
	 * @throws ElementNotFoundException if an element required for the execution of the
	 *                                  scenario is not found.
	 */
	protected void scenarioSEIS() throws ElementNotFoundException {
		Room playerPosition = this.player.getPosition();

		String scenarioSEISinfo = Display.playerEntersInMessage(player.getName(), playerPosition.getName());
		scenarioSEISinfo += Display.scenarioSEISstartMessage();
		scenarioSEISinfo += Display.playerTurnMessage();
		scenarioSEISinfo += Display.playerSearchsMessage(player.getName(), playerPosition.getName());
		System.out.print(scenarioSEISinfo);

		scenarioSEISinfo = "";
		if (playerPosition.hasItems()) {
			scenarioSEISinfo += gatherItems(playerPosition);
		}

		if (isMissionAccomplished()) {
			scenarioSEISinfo += Display.targetInExtractionPointMessage(player.getName());
			setGameOver(true);

		} else {
			playerReachedTarget();
			scenarioSEISinfo += Display.targetIsSecuredMessage(player.getName(), nextObjective.getName());
		}

		scenarioSEISinfo += Display.scenarioSEISendMessage();
		System.out.println(scenarioSEISinfo);
	}

	/**
	 * Handles the logic executed when the player reaches the target.
	 * <p>
	 * This method marks the mission objective as secured, sets the
	 * returning-to-extraction flag, and identifies the best extraction point based on the
	 * player's current position. It then updates the mission's next objective to the
	 * determined extraction point.
	 *
	 * @throws ElementNotFoundException if the method is unable to find a valid extraction
	 *                                  point.
	 */
	private void playerReachedTarget() throws ElementNotFoundException {
		mission.setTargetSecured(true);
		setReturningToExtraction(true);
		extractionPoint = bestExtractionPoint(player.getPosition());
		setNextObjective(extractionPoint);
	}

	/**
	 * Determines whether the player's current position matches the target position.
	 *
	 * @param playerPosition the current position of the player
	 * @param targetPosition the target position to check against
	 * @return true if the player's current position is the same as the target position,
	 * otherwise false
	 */
	private boolean isAtTarget(Room playerPosition, Room targetPosition) {
		return playerPosition.equals(targetPosition);
	}

	/**
	 * Determines if the mission is accomplished based on the player's status, the
	 * mission's target security, and the player's current position relative to the next
	 * objective.
	 *
	 * @return true if the player is alive, the mission target is secured, and the player
	 * is at the next objective; false otherwise.
	 */
	protected boolean isMissionAccomplished() {
		Room playerPosition = player.getPosition();
		return player.isAlive() && mission.isTargetSecured() && playerPosition.equals(this.nextObjective);
	}

	/**
	 * Identifies and returns the best entry point for the mission based on the shortest
	 * path with the minimal calculated damage to the next objective. It evaluates all
	 * entry/exit points provided by the mission and determines the optimal entry point by
	 * iterating over the battlefield network.
	 *
	 * @return The Room object representing the best entry point with minimal damage to
	 * the next objective. Returns null if no suitable entry point is found.
	 *
	 * @throws ElementNotFoundException if the battlefield network or paths are invalid or
	 *                                  the objective cannot be located.
	 */
	public Room findBestEntryPoint() throws ElementNotFoundException {
		Network<Room> battlefield = mission.getBattlefield();
		double minimalDamage = Double.MAX_VALUE;
		Room bestEntryPoint = null;

		for (Room entryPoint : mission.getEntryExitPoints()) {
			Iterator<Room> entryPointsPaths = battlefield.iteratorShortestPath(entryPoint, nextObjective);
			double calculatedDamage = calculatePathDamage(entryPointsPaths);

			if (calculatedDamage < minimalDamage) {
				minimalDamage = calculatedDamage;
				bestEntryPoint = entryPoint;
			}
		}
		return bestEntryPoint;

	}

	/**
	 * Determines whether the player needs a recovery item based on their current health
	 * and availability of recovery items in their possession.
	 *
	 * @return true if the player has a current health value less than or equal to a
	 * critical threshold and possesses a recovery item; false otherwise.
	 */


	protected void moveEnemies() {
		for (Enemy enemy : this.enemies) {
			moveEnemy(enemy);
		}
	}

	/**
	 * Moves all enemies that are not in the same room as the player. This method iterates
	 * through the list of enemies and checks their positions. If an enemy is located in a
	 * different room than the player's current position, the enemy is moved by invoking
	 * the moveEnemy method.
	 */
	private void moveEnemiesNotInSameRoom() {
		Room playerPosition = player.getPosition();

		for (Enemy enemy : enemies) {
			Room enemyPosition = enemy.getPosition();

			if (!enemyPosition.equals(playerPosition)) {
				moveEnemy(enemy);
			}
		}
	}

	/**
	 * Moves the specified enemy to a new position in a randomly selected, valid room. The
	 * method first determines the possible moves for the enemy, then updates the enemy's
	 * position and adjusts the status of the rooms involved accordingly.
	 *
	 * @param enemy the enemy to be moved. The enemy's current position is used to
	 *              determine possible moves, and its position is updated to a new room if
	 *              valid moves exist.
	 */
	private void moveEnemy(Enemy enemy) {
		Room enemyPosition = enemy.getPosition();
		ArrayList<Room> possibleMoves = getPossibleMoves(enemyPosition);
		final int NONE = 0;

		if (!possibleMoves.isEmpty()) {
			enemyPosition.removeEnemy();

			if (enemyPosition.getTotalEnemies() <= NONE) {
				enemyPosition.setEnemies(false);
			}

			Random random = new Random();
			int random_index = random.nextInt(possibleMoves.size());

			Room nextPosition = possibleMoves.getElement(random_index);

			enemy.setPosition(nextPosition); nextPosition.addEnemy();
			nextPosition.setEnemies(true);
		}
	}

	/**
	 * Determines the possible moves from the specified room within a certain range based
	 * on a breadth-first search traversal of the battlefield.
	 *
	 * @param fromRoom the starting room from which to determine possible moves
	 * @return a list of rooms representing possible moves from the specified room
	 */
	private ArrayUnorderedList<Room> getPossibleMoves(Room fromRoom) {

		ArrayUnorderedList<Room> possibleMoves = new ArrayUnorderedList<>();

		try {
			Iterator<Room> bfsIterator = mission.getBattlefield().iteratorBFS(fromRoom);
			int bfsLevel = 0;

			while (bfsIterator.hasNext() && bfsLevel <= 2) {
				Room toRoom = bfsIterator.next();

				if (bfsLevel == 1 || bfsLevel == 2) {
					if (toRoom != null) {
						possibleMoves.addToRear(toRoom);
					}
				}
				bfsLevel++;
			}
		} catch (EmptyCollectionException e) {
			System.err.println(e.getMessage());
		}

		return possibleMoves;
	}

	/**
	 * Collects and processes items located in the specified room.
	 * Depending on the item type, the method updates the player's inventory or equipment
	 * and generates appropriate display messages.
	 *
	 * @param position The room in which the items are to be gathered.
	 * @return A string containing messages about the items collected by the player
	 *         and their actions (e.g., equipping or storing items).
	 */
	private String gatherItems(Room position) {
		String gatherItemsOutput = "";
		Room playerPosition = player.getPosition();
		Iterator<Item> itemIterator = mission.getItems().iterator();

		gatherItemsOutput += Display.itemsSpottedMessage();
		while (itemIterator.hasNext()) {

			Item item = itemIterator.next();

			if (item.getPosition() != null && item.getPosition().equals(position)) {

				if (item instanceof MediKit && !player.getBack_pack().isBackPackFull()) {
					player.addKitToBackPack((MediKit) item);
					gatherItemsOutput += Display.playerAddsItemMessage(player.getName(), item.getName());

				} else if (item instanceof Kevlar) {
					player.equipKevlar((Kevlar) item);
					gatherItemsOutput +=
						Display.playerEquipsItemMessage(player.getName(), item.getName(), player.getCurrentHealth());
				}
				oneItemGetsPicked(playerPosition, itemIterator);
			}
		}

		allItemsInPositionCollected(playerPosition);

		return gatherItemsOutput;
	}

	/**
	 * Removes an item from the player's current position and also from the provided items
	 * iterator.
	 *
	 * @param playerPosition the current position of the player represented as a Room
	 *                       object
	 * @param items          the iterator for the collection of items from which an item
	 *                       is to be removed
	 */
	private void oneItemGetsPicked(Room playerPosition, Iterator<Item> items) {
		playerPosition.removeItem();
		items.remove();
	}

	/**
	 * Checks if all items in the player's current position (room) have been collected.
	 * Updates the room status to indicate no items are present if none remain.
	 *
	 * @param playerPosition the current room where the player is located
	 */
	private void allItemsInPositionCollected(Room playerPosition) {
		if (playerPosition.getTotalItems() <= 0) {
			playerPosition.setItemsInRoom(false);
		}
	}

	/**
	 * Handles the encounter between the player and enemies in the game. Checks if any
	 * enemy is in the same position as the player, triggering an attack. Also manages the
	 * player's decision to recover and updates the game state accordingly.
	 *
	 * @param player the player currently engaged in the game
	 */
	private void enemiesConfronts(Player player) {
		Room playerPosition = player.getPosition(); Room enemyPosition;
		boolean playerWantsToRecover;

		for (Enemy enemy : getEnemies()) {
			enemyPosition = enemy.getPosition();

			if (enemyPosition.equals(playerPosition)) {
				enemyAttacksPlayer(enemy, player);
				playerWantsToRecover = player.playerNeedsRecoveryItem();

				if (playerWantsToRecover) {
					playerDecidesToRecover();
					return;
				}

				if (!player.isAlive()) {
					setGameOver(true);
					break;
				}
			}
		}
	}

	/**
	 * Handles the confrontation between the player and enemies in the player's position.
	 * This method iterates through the list of enemies, checking if any enemy is in the
	 * same position as the player. If an enemy is found, the player attacks the enemy
	 * using their firepower. If the enemy is killed, it is removed from the list, and the
	 * kill is logged. If the enemy survives, a message indicating the enemy endured the
	 * attack is appended to the confrontation information.
	 * <p>
	 * After handling all confrontations, the method ensures no remaining enemies are
	 * present in the player's position and prints the confrontation's summary.
	 */
	protected void playerConfronts() {
		String playerConfrontsInfo = "";
		Iterator<Enemy> enemies = getEnemies().iterator();
		final int PLAYER_ATTACK = player.getFirePower();

		while (enemies.hasNext()) {
			Enemy enemy = enemies.next();
			Room enemyPosition = enemy.getPosition();

			if (enemyPosition.equals(player.getPosition())) {
				playerAttacksEnemy(player, enemy, PLAYER_ATTACK);

				if (!enemy.isAlive()) {
					report.addEnemyKilled(enemy.getName());
					oneEnemyDies(enemy, enemies);

				} else {
					playerConfrontsInfo += Display.enemyEnduredAttackMessage(enemy.getName(), PLAYER_ATTACK);
				}
			}
		}
		noEnemiesInPlayerPosition(player.getPosition());

		System.out.print(playerConfrontsInfo);

	}

	/**
	 * Checks if there are no enemies in the player's current position and updates the
	 * state of the player's position accordingly.
	 *
	 * @param playerPosition the current position of the player represented as a Room
	 *                       object.
	 */
	private void noEnemiesInPlayerPosition(Room playerPosition) {
		if (playerPosition.getTotalEnemies() <= 0) {
			playerPosition.setEnemies(false);
		}
	}

	/**
	 * Executes an attack by the player on an enemy. The enemy takes damage based on the
	 * player's attack strength, and relevant messages are displayed.
	 *
	 * @param player       The player initiating the attack.
	 * @param enemy        The enemy being attacked.
	 * @param playerAttack The amount of attack strength the player uses on the enemy.
	 */
	private void playerAttacksEnemy(Player player, Enemy enemy, int playerAttack) {
		String playerAttacksEnemyInfo = "";

		enemy.takesDamageFrom(playerAttack);

		playerAttacksEnemyInfo += Display.playerIsAttackingMessage(player.getName(), enemy.getName(), playerAttack);
		playerAttacksEnemyInfo += Display.enemySufferedAttackMessage(enemy.getName(), playerAttack);

		System.out.print(playerAttacksEnemyInfo);
	}

	/**
	 * Handles the logic when one enemy dies during the game. Removes the enemy from its
	 * current position and updates the game state.
	 *
	 * @param enemy   The enemy object that is being removed due to its death.
	 * @param enemies An iterator over the list of enemies, used to remove the deceased
	 *                enemy.
	 */
	private void oneEnemyDies(Enemy enemy, Iterator<Enemy> enemies) {
		String oneEnemyDiesInfo = "";

		oneEnemyDiesInfo += Display.enemyIsDeadMessage();

		enemies.remove();
		enemy.getPosition().removeEnemy();

		System.out.print(oneEnemyDiesInfo);
	}

	/**
	 * Handles the scenario where the player decides to recover. This method transitions
	 * the game to the next scenario (ScenarioNr.FOUR) and executes the corresponding
	 * actions by calling scenarioQUATRO().
	 */
	private void playerDecidesToRecover() {
		setNextScenario(ScenarioNr.FOUR);
		scenarioQUATRO();
	}

	/**
	 * Handles the attack action where an enemy attacks a player, causing the player to
	 * take damage.
	 *
	 * @param enemy  The enemy entity performing the attack. Contains information such as
	 *               name and firepower.
	 * @param player The player entity being attacked. The player will receive damage
	 *               based on the enemy's firepower.
	 */
	private void enemyAttacksPlayer(Enemy enemy, Player player) {
		String enemyAttacksPlayerInfo = "";

		player.takesDamageFrom(enemy.getFirePower());

		enemyAttacksPlayerInfo = Display.enemyIsAttackingMessage(enemy.getName(), player.getName(), enemy.getFirePower());

		System.out.print(enemyAttacksPlayerInfo);
	}

	/**
	 * Determines the best extraction point for the player based on the minimal path
	 * damage from the player's current position to each extraction point in the mission.
	 *
	 * @param playerPosition The current position of the player in the battlefield.
	 * @return The room representing the extraction point with the minimal damage path
	 * from the player's position.
	 *
	 * @throws ElementNotFoundException If no valid path or extraction point is found in
	 *                                  the mission.
	 */
	public Room bestExtractionPoint(Room playerPosition) throws ElementNotFoundException {
		Room bestExtractionPoint = null;
		Iterator<Room> extractionPoints = mission.getEntryExitPoints().iterator();
		double minimalDamage = Double.MAX_VALUE;
		double calculatedDamage = 0.0;

		while (extractionPoints.hasNext()) {
			Room extractionPoint = extractionPoints.next();
			Iterator<Room> extractionPointsPaths = battlefield.iteratorShortestPath(playerPosition, extractionPoint);
			calculatedDamage = calculatePathDamage(extractionPointsPaths);

			if (calculatedDamage < minimalDamage) {
				minimalDamage = calculatedDamage; bestExtractionPoint = extractionPoint;

			}
		} return bestExtractionPoint;
	}

	/**
	 * Calculates the total damage a player would take while traversing a given path. The
	 * method processes each room in the path and considers encounters with enemies and
	 * the effects of items like MediKits and Kevlar.
	 *
	 * @param path an iterator of Room objects representing the path to traverse. Each
	 *             room may contain enemies or items that affect the player's health.
	 * @return the total damage dealt to the player during the traversal.
	 */
	public double calculatePathDamage(Iterator<Room> path) {
		final int PLAYER_MAX_HEALTH = 100;
		double totalDamage = 0;
		int playerHealth = player.getCurrentHealth();

		while (path.hasNext()) {
			Room room = path.next();

			if (room.hasEnemies()) {
				for (Enemy enemy : enemies) {
					if (enemy.getPosition().equals(room)) {
						totalDamage += enemy.getFirePower();

					}
				}
			}

			if (room.hasItems()) {
				for (Item item : mission.getItems()) {
					if (item.getPosition() != null && item.getPosition().equals(room)) {
						if (item instanceof MediKit) {
							playerHealth = Math.min(PLAYER_MAX_HEALTH, playerHealth + ((MediKit) item).getHealPower());
						} else if (item instanceof Kevlar) {
							playerHealth += ((Kevlar) item).getExtraHp();
						}
					}
				}
			}
		}

		return totalDamage;
	}

	/**
	 * Updates the weights of the battlefield edges for enemies. The method iterates over
	 * all rooms (vertices) in the battlefield graph and recalculates the weight of each
	 * edge connecting a room to its adjacent rooms. The new weights are evaluated using
	 * the calculateWeight method and are updated in the battlefield graph using the
	 * addEdge method.
	 * <p>
	 * This process ensures that the graph accurately reflects the current gameplay state,
	 * particularly regarding enemy-related dynamics within the battlefield.
	 */
	protected void updateWeightsForEnemies() {
		double newWeight = 0.0;

		for (Room room : getBattleField().getVertices()) {
			for (Room connectedRoom : getBattleField().getConnectedVertices(room)) {
				newWeight = calculateWeight(connectedRoom);
				getBattleField().addEdge(room, connectedRoom, newWeight);
			}
		}
	}

	/**
	 * Calculates the weight of a given room based on the total damage inflicted by
	 * enemies present in the room.
	 *
	 * @param room the room for which the weight is to be calculated
	 * @return the calculated weight of the room based on the total firepower of enemies
	 * in the room
	 */
	private double calculateWeight(Room room) {
		final double NONE = 0.0; double weight = 0.0; int totalDamage = 0;

		if (room.getTotalEnemies() > 0) {
			for (Enemy enemy : this.enemies) {
				if (enemy.getPosition().equals(room)) {
					totalDamage += enemy.getFirePower();
				}
			} weight += totalDamage;
		} else {
			return weight += NONE;
		} return weight;
	}

	/**
	 * Determines and displays the next mission stage information based on the mission's
	 * current state and whether the user is returning to the exit or proceeding to the
	 * next objective.
	 *
	 * @param isReturningToExit Indicates whether the mission is transitioning back to the
	 *                          exit point (true) or heading towards the next objective
	 *                          (false).
	 */
	protected void nextMissionStageInfo(boolean isReturningToExit) {

		String getBestPathOutput = "";

		if (isReturningToExit) {
			getBestPathOutput += Display.bestPathExtractionMessage();
		} else {
			getBestPathOutput += Display.bestPathObjectiveMessage();
		}

		System.out.println(getBestPathOutput);
	}

	/**
	 * Displays the path from the starting room to the destination room. The path is
	 * calculated based on the shortest path between the provided rooms. If the starting
	 * or destination room is invalid or not found, an exception is thrown.
	 *
	 * @param fromPosition The starting room from which the path begins.
	 * @param toPosition   The destination room to which the path is directed.
	 * @throws ElementNotFoundException If the specified rooms do not exist or the path
	 *                                  cannot be determined.
	 */
	protected void displayPath(Room fromPosition, Room toPosition) throws ElementNotFoundException {
		Iterator<Room> bestPath = getBattleField().iteratorShortestPath(fromPosition, toPosition);
		Room nextRoom;

		StringBuilder displayPathOutput = appendPlayerPositionInfo(bestPath.next());
		while (bestPath.hasNext()) {
			nextRoom = bestPath.next();
			displayPathOutput = appendNextRoomInfo(displayPathOutput, nextRoom);
		} System.out.println(displayPathOutput);
	}

	/**
	 * Appends information about the next room to the given path output.
	 *
	 * @param pathOutput a StringBuilder object that stores the formatted path
	 *                   information
	 * @param nextRoom   the Room object representing the next room to evaluate
	 * @return the updated StringBuilder containing the appended information about the
	 * next room
	 */
	protected StringBuilder appendNextRoomInfo(StringBuilder pathOutput, Room nextRoom) {

		if (!nextRoom.equals(this.nextObjective)) {
			if (nextRoom.hasItems()) {
				pathOutput.append(String.format("\n  | %-21s <--- %-20s", nextRoom.getName(), "ITEM"));

			} else {
				pathOutput.append("\n  |\t").append(nextRoom.getName());
			}

		} else if (returningToExtraction) {
			pathOutput.append(String.format("\n%-25s <--- %-20s", this.nextObjective.getName(), "EXTRACTION POINT"));

		} else {
			pathOutput.append(String.format("\n%-25s <--- %-20s", this.nextObjective.getName(), "OBJECTIVE"));
		}

		return pathOutput;
	}

	/**
	 * Appends formatted player position information to a StringBuilder.
	 *
	 * @param playerPosition the Room object representing the player's current position
	 * @return a StringBuilder containing the formatted player position information
	 */
	protected StringBuilder appendPlayerPositionInfo(Room playerPosition) {
		StringBuilder pathOutput = new StringBuilder();
		pathOutput.append(String.format("\n%-25s <--- %-20s", playerPosition.getName(), player.getName()));
		return pathOutput;
	}

	/**
	 * Adds a room to the report based on the mission's target secured status. If the
	 * target is not secured, the room is added to the general report. If the target is
	 * secured, the room is added to the extraction section of the report.
	 *
	 * @param roomName the name of the room to add to the report
	 */
	protected void addRoomToReport(String roomName) {
		if (!this.mission.isTargetSecured()) {
			getReport().addRoom(roomName);
		} else {
			getReport().addRoomToExtraction(roomName);
		}
	}

	/**
	 * Updates the mission status in the report based on the player's state and the game
	 * status. If the player is alive and the game is over, the mission status is set to
	 * "entities.Mission Accomplished". Otherwise, the mission status is set to
	 * "entities.Mission Failed".
	 */
	protected void addStatusToReport() {
		if (player.isAlive() && isGameOver()) {
			missionAccomplished = true;
			getReport().setMissionStatus("Mission Accomplished");
		} else {
			missionAccomplished = false;
			report.setMissionStatus("Mission Failed");
		}
	}

	/**
	 * Sets the value indicating whether the system is returning to the extraction phase.
	 *
	 * @param returningToExtraction the boolean value to set; true if returning to
	 *                              extraction, false otherwise
	 */
	public void setReturningToExtraction(boolean returningToExtraction) {
		this.returningToExtraction = returningToExtraction;
	}

}