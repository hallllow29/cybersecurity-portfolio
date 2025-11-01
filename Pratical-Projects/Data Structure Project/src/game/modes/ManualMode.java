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
import lib.lists.ArrayUnorderedList;
import lib.lists.CircularDoubleLinkedList;
import lib.stacks.ArrayStack;

import java.util.Iterator;
import java.util.Scanner;

/**
 * The ManualMode class provides a simulation mode where players can manually interact
 * with the game environment and make decisions, such as selecting routes, using items, or
 * staying at the current position. It extends the Simulation class and incorporates
 * custom logic for user-driven gameplay.
 * <p>
 * The ManualMode gameplay is characterized by:
 * - Manual decision-making for the player's movements.
 * - Interactions with items and enemies within the game environment.
 * - Displaying detailed intel about the game environment (enemies, items, paths, etc.) to
 * the player.
 */
public class ManualMode extends Simulation {

	/**
	 * Constructs a ManualMode instance using the specified mission, player, and report.
	 * This class is responsible for managing the manual gameplay mode and its associated
	 * operations.
	 *
	 * @param missionImpl the mission implementation that defines the game's scenario and
	 *                    objectives
	 * @param player      the player participating in the manual gameplay mode
	 * @param report      the report instance used to log and display game-related
	 *                    information
	 */
	public ManualMode(Mission missionImpl, Player player, Report report) {
		super(missionImpl, player, report);
	}

	/**
	 * Executes the primary game flow for the manual mode. This method performs a series
	 * of steps to initiate and manage the simulation, including rendering manual
	 * simulation, displaying critical game intel, managing room entries, and setting the
	 * player's starting position. Finally, it triggers the overarching game flow logic.
	 *
	 * @throws ElementNotFoundException if a required element is not found during the
	 *                                  execution.
	 * @throws EmptyCollectionException if an operation is attempted on an empty
	 *                                  collection.
	 */
	@Override
	public void game() throws ElementNotFoundException, EmptyCollectionException {
		System.out.print(Display.initSimulation());

		renderManualSimulation(getMission().getTarget().getRoom());
		displaySophisticatedSpySystem();
		Room entryPoint = displayAllEntries();
		super.setEntryPoint(entryPoint);
		super.getPlayer().setPosition(entryPoint);
		getReport().addRoom(getPlayer().getPosition().getName());
		getReport().setEntryPoint(getPlayer().getPosition().getName());

		if (entryPoint.hasItems()){
			scenariosSituations();
			scenariosCase(getCurrentScenario());
		}

		super.setNextTurn(Turn.PLAYER);

		super.gameFlow();
	}

	/**
	 * Moves the player to the next position or stays in the current position based on the game state
	 * and the player's decision. This method manages the overall game progression during the player's turn.
	 *
	 * If the mission is not accomplished, the method:
	 * - Displays relevant game information using the sophisticated spy system.
	 * - Calculates and displays the possible path to the player's next objective.
	 * - Allows the player to select their next position from available options, including staying in the current room.
	 * - Updates the player's position and the turn order based on the player's choice.
	 * - Adds the new room to the game report for tracking movement history.
	 * - Renders additional intel using the sophisticated spy system interface.
	 *
	 * If the mission is accomplished, the game is marked as over.
	 *
	 * @throws ElementNotFoundException if any required game element, such as a room or objective, is missing.
	 */
	@Override
	public void movePlayer() throws ElementNotFoundException {
		Room playerPosition = getPlayer().getPosition();

		if (!isMissionAccomplished()) {

			System.out.println(Display.sophisticatedSpySystemBanner());

			super.displayPath(getPlayer().getPosition(), getNextObjective());
			Room nextPlayerPosition = selectNextPosition();

			// STAY
			if (nextPlayerPosition.equals(playerPosition)) {
				super.getPlayer().setPosition(playerPosition);
				super.setNextTurn(Turn.ENEMY);
			} else {
				super.getPlayer().setPosition(nextPlayerPosition);
				super.setNextTurn(Turn.PLAYER);
			}

			this.displaySophisticatedSpySystem();

			super.addRoomToReport(nextPlayerPosition.getName());

		} else {
			setGameOver(true);
		}
	}

	/**
	 * Allows the player to select the next position to move within the game by
	 * presenting a list of possible moves, the option to stay, and the option to
	 * use a MedicKit. The method interacts with the player via the console to
	 * capture their choice and determines the corresponding action or position
	 * based on the selection.
	 *
	 * @return the selected Room object that represents the player's next position,
	 * or the current position if the player chooses to stay.
	 */
	private Room selectNextPosition() {
		String selectNextPositionInfo = "";
		int choice = -1;
		Scanner scanner = new Scanner(System.in);
		Room selectedRoom = null;
		int lastSelection = 0;

		ArrayUnorderedList<Room> possibleMoves = getMission().getBattlefield().getConnectedVertices(getPlayer().getPosition());
		ArrayUnorderedList<Room> displayPositions = new ArrayUnorderedList<>();

		lastSelection = countPossiblePositions(possibleMoves, displayPositions);

		int stayOption = lastSelection;
		selectNextPositionInfo += optionNrMessage(stayOption, "Stay");

		int medicKitOption = lastSelection + 1;
		selectNextPositionInfo += optionNrMessage(medicKitOption, "Use MedicKit - HP" + currentHealthMessage());
		selectNextPositionInfo += "\n\nOption: ";

		// selectNextPositionInfo = "";

		while (true) {
			System.out.print(selectNextPositionInfo);

			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();

				if (choice >= 0 && choice <= medicKitOption) {

					selectedRoom = decideNextMove(choice, lastSelection, possibleMoves);

					if (selectedRoom != null) {
						break;
					}
				}
			} else {
				System.out.println(Display.invalidOptionMessage());
				scanner.next();
			}
		}

		selectNextPositionInfo = Display.yourNextPositionMessage(selectedRoom.getName());
		System.out.print(selectNextPositionInfo);

		return selectedRoom;
	}

	/**
	 * Counts the possible positions a player or element can move to based on the given list of possible moves
	 * and populates a display list with these positions.
	 *
	 * @param possibleMoves the list of rooms that represent possible move options
	 * @param displayPositions the list to be populated with the positions for display purposes
	 * @return the number of possible positions available for movement
	 */
	private int countPossiblePositions(ArrayUnorderedList<Room> possibleMoves, ArrayUnorderedList<Room> displayPositions) {
		int possiblePositions = 0;
		String possiblePositionInfo = "";
		possiblePositionInfo += Display.possibleMovesMessage();

		for (Room room : possibleMoves) {
			possiblePositionInfo += "\n[" + possiblePositions + "] " + possibleMoves.getElement(possiblePositions);
			displayPositions.addToRear(room);
			possiblePositions++;
		}

		System.out.print(possiblePositionInfo);

		return possiblePositions;
	}

	/**
	 * Generates a formatted string for displaying a numbered option with its
	 * corresponding selection text. This is typically used to create interactive menu
	 * options or selections for the user.
	 *
	 * @param option    the number representing the option to display.
	 * @param selection the text or description associated with the option.
	 * @return a formatted string representing the numbered option and its corresponding
	 * selection.
	 */
	private String optionNrMessage(int option, String selection) {
		return "\n[" + option + "] " + selection;
	}

	/**
	 * Computes the current health message of the player by retrieving the player's
	 * current health and formatting it as a string in the format "currentHealth/100".
	 *
	 * @return A string representing the player's current health in the format "currentHealth/100".
	 */
	private String currentHealthMessage() {
		return getPlayer().getCurrentHealth() + "/100";
	}

	/**
	 * Decides the next move based on the player's choice, current position, and available moves.
	 * This method handles various scenarios such as staying in the current position, using a medic kit,
	 * or moving to a selected room.
	 *
	 * @param choice the player's selected choice for the next move
	 * @param lastSelection the index of the last available room choice
	 * @param possibleMoves a collection of possible rooms the player can move to
	 * @return the selected Room based on the player's choice;
	 *         returns the current room if the player chooses to stay or uses a medic kit
	 *         when applicable, or null for invalid options
	 */
	private Room decideNextMove(int choice, int lastSelection, ArrayUnorderedList<Room> possibleMoves) {

		String decideNextMoveInfo = "";
		Room selectedRoom = null;
		int medicKitOption = lastSelection + 1;

		if (choice >= 0 && choice < lastSelection) {
			selectedRoom = possibleMoves.getElement(choice);

		} else if (choice == lastSelection) {
			decideNextMoveInfo += Display.youChooseStayMessage();
			selectedRoom = getPlayer().getPosition();

		} else if (choice == medicKitOption) {
			if (getPlayer().getBack_pack().isBackPackEmpty()) {
				decideNextMoveInfo += Display.noMediKitsBackPackMessage();

			} else if (getPlayer().getCurrentHealth() >= 100) {
				decideNextMoveInfo += Display.playerHealthFullMessage();
				setNextTurn(Turn.PLAYER);

			} else {
				useMedicKit();
				selectedRoom = getPlayer().getPosition();
			}
		} else {
			decideNextMoveInfo += Display.invalidOptionMessage();
		}

		System.out.println(decideNextMoveInfo);
		return selectedRoom;
	}

	/**
	 * Displays all possible entry points and allows the user to select an entry point
	 * based on the recommendations provided. The user is prompted to make a choice
	 * between selecting an entry point or continuing to view other options.
	 *
	 * @return Returns the selected Room object corresponding to the user's choice.
	 * @throws ElementNotFoundException if an error occurs while fetching entry points.
	 */
	private Room displayAllEntries() throws ElementNotFoundException {
		String displayAllEntiesInfo = "";
		displayAllEntiesInfo += Display.allPossibleEntriesBanner();
		System.out.print(displayAllEntiesInfo);
		Room selectedPosition = null;
		Room ourRecommendation = super.findBestEntryPoint();

		Iterator<Room> roomIterator = getMission().getEntryExitPoints().iterator();

		CircularDoubleLinkedList<Room> entryPoints = new CircularDoubleLinkedList<>();

		while (roomIterator.hasNext()) {
			Room entry = roomIterator.next();
			entryPoints.add(entry);
		}

		Scanner scanner = new Scanner(System.in);
		int choice = 2;

		String displayEntryPointInfo = "";

		while (choice != 1) {
			for (Room entryPoint : entryPoints) {
				displayEntryPointInfo +=
					Display.entryPointSelection(ourRecommendation.getName(), entryPoint.getName());
				System.out.print(displayEntryPointInfo);
				displayEntryPointInfo = "";

				boolean validSelection = false;

				while (!validSelection) {
					if (scanner.hasNextInt()) {
						choice = scanner.nextInt();

						if (choice == 1) {
							selectedPosition = entryPoint;
							validSelection = true;

						} else if (choice == 2) {
							validSelection = true;

						} else {
							System.out.println("\nInvalid option!");
							System.out.print("Option: ");
						}
					} else {
						System.out.println("\nInvalid option!");
						System.out.print("Option: ");
						scanner.next();
					}
				}

				if (choice == 1) {
					break;
				}
			}
		}
		return selectedPosition;
	}

	/**
	 * Displays the sophisticated spy system's interface and renders various aspects of
	 * the game intel, such as enemy and item intelligence, the player's status, and
	 * upcoming scenarios. This method consolidates and outputs critical game information
	 * for the user to observe and analyze.
	 */
	private void displaySophisticatedSpySystem() {
		String gatheringIntelInfo = Display.collectingData();
		System.out.print(gatheringIntelInfo);
		gatheringIntelInfo = "";

		displayEnemiesIntel();
		displayItemsIntel();

		gatheringIntelInfo += Display.playerBanner();
		gatheringIntelInfo += Display.playerHealthStatusMessage(getPlayer().getCurrentHealth());
		System.out.print(gatheringIntelInfo);

		displayMedicKits();

		gatheringIntelInfo = Display.renderingNextSituationMessage();
		System.out.println(gatheringIntelInfo);
	}

	/**
	 * Calculates the closest path to a room containing a medical kit based on the least
	 * amount of damage incurred along the path. It uses Breadth-First Search (BFS) to
	 * explore the battlefield and considers the shortest path to each room with items.
	 * The method determines the path that minimizes the damage and selects the
	 * destination room accordingly.
	 *
	 * @return the room containing a medical kit that is closest to the player's current
	 * position in terms of minimal damage along the path.
	 *
	 * @throws EmptyCollectionException if the battlefield or relevant paths are empty.
	 * @throws ElementNotFoundException if required elements, such as a room or medical
	 *                                  kit, cannot be located during the computation.
	 */
	private Room calculateClosestPathToMedicKit() throws EmptyCollectionException, ElementNotFoundException {
		Room currentRoom = getPlayer().getPosition();
		Room destinationRoom = null;
		double minimalDamage = Double.MAX_VALUE;
		double calculatedDamage = 0;

		Iterator<Room> rooms = getMission().getBattlefield().iteratorBFS(currentRoom);

		while (rooms.hasNext()) {
			Room bestRoom = rooms.next();
			if (bestRoom.hasItems()) {
				Iterator<Room> paths = getMission().getBattlefield().iteratorShortestPath(currentRoom, bestRoom);
				calculatedDamage = calculatePathDamage(paths);
				if (calculatedDamage < minimalDamage) {
					minimalDamage = calculatedDamage;
					destinationRoom = bestRoom;
				}
			}
		}

		return destinationRoom;
	}

	/**
	 * Displays the medical kits available in the player's backpack. This method accesses
	 * the player's backpack (a stack structure containing MediKit objects), checks for
	 * its contents, and prepares a message detailing the medical kits present.
	 * <p>
	 * If the backpack is empty, a message indicating the absence of items is appended.
	 * Otherwise, the contents of the backpack (as a string representation of the stack)
	 * are included.
	 */
	private void displayMedicKits() {
		String displayMedicKitsInfo = "";

		ArrayStack<MediKit> stack = super.getPlayer().getBack_pack().getListItems();

		if (stack.isEmpty()) {
			displayMedicKitsInfo += Display.backPackNoItemsMessage();
		} else {
			displayMedicKitsInfo += Display.backPackContentMessage(stack.toString());
		}

		System.out.print(displayMedicKitsInfo);
	}

	/**
	 * Utilizes a medical kit by invoking the `scenarioQUATRO` method of the superclass.
	 *
	 * This method is designed to handle the application of a medical kit within the game
	 * scenario, potentially affecting the player's health or other game-related parameters
	 * as part of the current mission simulation logic. The specific effects and operations
	 * are managed by the superclass implementation of `scenarioQUATRO`. The exact behavior
	 * may depend on the overarching game mechanics defined in the `Simulation` class.
	 */
	private void useMedicKit() {
		super.scenarioQUATRO();
	}

	/**
	 * Displays intelligence related to items available in the current mission and the
	 * player's surroundings. This method collects and formats information about
	 * available items and outputs it to the console.
	 *
	 * The method performs the following operations:
	 * - Calls the Display class to render banners and messages, such as the available
	 *   medical kits in the mission.
	 * - Iterates through the items in the mission to retrieve and display their
	 *   details, such as name, value, and position.
	 * - Identifies and displays the closest medical kit to the player's position, if
	 *   available, along with the path to it.
	 *
	 * If there are no medical kits left or an error occurs during path calculation,
	 * appropriate messages are displayed to indicate these conditions.
	 *
	 * Any exceptions related to empty collections or missing elements during the closest
	 * medical kit calculation are caught and output as error messages.
	 */
	private void displayItemsIntel() {

		String gatheringItemsInfo = "";
		gatheringItemsInfo += Display.mediKitsKevlarsBanner();

		for (Item item : getMission().getItems()) {
			gatheringItemsInfo += Display.itemsIntelMessage(item.getName(), item.getItemValue(), item.getPosition().getName());
		}

		gatheringItemsInfo += Display.closestMediKitBanner();
		System.out.print(gatheringItemsInfo);
		gatheringItemsInfo = "";

		if (getPlayer().getPosition() != null) {
			try {
				Room toNextMediKit = calculateClosestPathToMedicKit();

				if (toNextMediKit != null) {
					displayPath(getPlayer().getPosition(), toNextMediKit);
				} else {
					gatheringItemsInfo += Display.noMediKitsLeftMessage();
				}
			} catch (EmptyCollectionException | ElementNotFoundException e) {
				System.err.println(e.getMessage());
			}

		}
		System.out.print(gatheringItemsInfo);
	}

	/**
	 * Gathers and displays intelligence information about all known enemies in the game
	 * simulation. This method consolidates enemy details, including their names,
	 * firepower, and positions, into a formatted message.
	 * <p>
	 * This is a utility method to provide players with critical intelligence about enemy
	 * status, aiding in strategic decision-making during gameplay.
	 */
	private void displayEnemiesIntel() {
		String gatheringEnemiesInfo = "";
		gatheringEnemiesInfo += Display.enemiesBanner();

		for (Enemy enemy : getEnemies()) {
			gatheringEnemiesInfo += Display.enemiesIntelMessage(enemy.getName(), enemy.getFirePower(), enemy.getPosition().getName());
		}
		System.out.print(gatheringEnemiesInfo);
	}

	/**
	 * Determines whether the mission is accomplished.
	 * <p>
	 * This method checks if the player is alive, the mission target is secured, and the
	 * player is positioned at the designated extraction point.
	 *
	 * @return true if the mission is accomplished; false otherwise
	 */
	@Override
	protected boolean isMissionAccomplished() {
		Room playerPosition = getPlayer().getPosition();
		Room extractionPoint = null;

		for (Room room : getMission().getEntryExitPoints()) {
			if (room.equals(playerPosition)) {
				extractionPoint = room;
			}
		}

		if (getPlayer().isAlive() && getMission().isTargetSecured() && playerPosition.equals(extractionPoint)) {
			setNextScenario(ScenarioNr.SIX);
			setGameOver(false);
			return true;
		}

		return false;
	}
}