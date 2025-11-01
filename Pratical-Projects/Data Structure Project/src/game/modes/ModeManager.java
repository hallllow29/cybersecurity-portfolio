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
import entities.enums.BackPackSize;
import game.briefings.MissionReportManager;
import game.briefings.Report;
import game.io.Display;
import game.util.JsonSimpleRead;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import lib.graphs.CustomNetwork;
import org.json.simple.parser.ParseException;


import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * ModeManager is a class responsible for managing the core functionalities of the game,
 * including creating a player, selecting a mission, and starting the game in manual or
 * automatic simulation modes. It also includes functionality to display mission and
 * simulation details such as rooms, connections, enemies, items, entry/exit points, and
 * the target.
 */
public class ModeManager {

	/**
	 * Represents an instance of a Mission utilized within the ModeManager class to manage
	 * mission-related operations during the game's simulation or operation.
	 * <p>
	 * This variable stores the active mission configuration, including details such as
	 * the battlefield, enemies, items, target, and related attributes.
	 * <p>
	 * The Mission object encapsulated by this variable provides functionality to define
	 * and manage key aspects of gameplay, including objectives, challenges, and
	 * resources.
	 */
	private Mission missionImpl;

	/**
	 * A static instance of MissionReportManager used to manage and process mission reports.
	 * This variable holds a singleton-like instance, ensuring that only one MissionReportManager
	 * is maintained and accessed within the application runtime.
	 * It is initialized and accessed statically.
	 */
	private static MissionReportManager repManager;

	/**
	 * Default constructor for the ModeManager class. Initializes the instance of the
	 * ModeManager by setting its internal mission implementation to null. This class
	 * handles the selection of missions, game initialization, and simulation modes.
	 */
	public ModeManager() {
		this.missionImpl = null;
		ModeManager.repManager = new MissionReportManager();
	}

	/**
	 * Retrieves the instance of the MissionReportManager.
	 *
	 * @return the singleton instance of MissionReportManager.
	 */
	public static MissionReportManager getRepManager() {
		return repManager;
	}

	/**
	 * Creates a new player instance by gathering necessary input from the user. This
	 * method displays a welcome banner and prompts the user to enter a name and choose a
	 * backpack size. It then initializes and returns a Player object with the specified
	 * name, default firepower of 100, and the chosen backpack size.
	 *
	 * @return a newly created Player object initialized with the user's input
	 */
	public Player createPlayer() {
		Scanner scanner = new Scanner(System.in);

		String createPlayerInfo = "";
		createPlayerInfo = Display.welcomeIMFbanner();
		createPlayerInfo += Display.enterYourNameMessage();
		System.out.print(createPlayerInfo);

		String playerName = scanner.nextLine();

		return new Player(playerName, 100, chooseBackPackSize(scanner));

	}

	/**
	 * Handles the mission selection process, allowing the user to choose from predefined
	 * missions. The chosen mission is loaded and set as the current mission
	 * implementation.
	 * <p>
	 * This method uses a text-based menu to prompt the user to select a mission.
	 * Depending on the user's choice, it attempts to load the corresponding mission from
	 * a JSON file into the program's network structure.
	 * <p>
	 * If an invalid option is selected, an error message is displayed, and no mission is
	 * loaded.
	 *
	 * @throws NotElementComparableException if the mission data includes elements that
	 *                                       are not comparable, making them incompatible
	 *                                       with the program's requirements.
	 * @throws IOException                   if an I/O error occurs while reading the
	 *                                       mission file.
	 * @throws ParseException                if there is an error parsing the JSON
	 *                                       structure of the mission file.
	 */
	public void selectMission() throws NotElementComparableException, IOException, ParseException {
		int choice;
		Scanner scanner = new Scanner(System.in);

		// SELECT MISSION MENU
		String selectMissionInfo = "";
		selectMissionInfo += Display.selectMissionMenu();
		System.out.print(selectMissionInfo);
		selectMissionInfo = "";

		boolean validSelection = false;
		while (!validSelection) {
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				switch (choice) {
					case 1:
						this.missionImpl = JsonSimpleRead.loadMissionFromJson("mission.json", new CustomNetwork<>());
						validSelection = true;
						break;
					case 2:
						this.missionImpl = JsonSimpleRead.loadMissionFromJson("missao_rato_de_aco.json", new CustomNetwork<>());
						validSelection = true;
						break;
					case 3:
						String inFile = choosePath(false);
						if (inFile == null) {
							validSelection = false;
							System.out.println("Invalid file path.");
						} else {
							this.missionImpl = JsonSimpleRead.loadMissionFromJson(inFile, new CustomNetwork<>());
							validSelection = true;
						}
						break;
					default:
						System.out.println("\nInvalid option!");
						System.out.print("Option: ");
						break;
				}
			} else {
				System.out.println("\nInvalid option!");
				System.out.print("Option: ");
				scanner.next();
			}
		}

	}

	/**
	 * Initializes and starts the game by managing the simulation mode selection and
	 * handling the game flow accordingly.
	 * <p>
	 * This method performs the following steps:
	 * 1. Creates a new player by invoking the `createPlayer` method.
	 * 2. Prompts the user to select a mission using the `selectMission` method.
	 * 3. Displays a menu to the user for selecting the type of simulation mode (automatic
	 * or manual).
	 * 4. Based on the user's choice, triggers either the automatic or manual simulation
	 * by invoking the respective methods.
	 * 5. Displays mission details before starting the simulation.
	 * 6. Handles invalid user inputs by displaying an appropriate error message.
	 * <p>
	 * The method can terminate early if the user selects the exit option from the menu.
	 *
	 * @throws NotElementComparableException if the selected mission contains
	 *                                       non-comparable elements.
	 * @throws IOException                   if an I/O error occurs during mission
	 *                                       selection.
	 * @throws ParseException                if the mission configuration file contains
	 *                                       parsing errors.
	 * @throws ElementNotFoundException      if an element required for the simulation is
	 *                                       not found.
	 * @throws EmptyCollectionException      if an operation on a collection is attempted
	 *                                       but the collection is empty.
	 */
	public void startGame() throws NotElementComparableException, IOException, ParseException, ElementNotFoundException, EmptyCollectionException {
		Player newPlayer = createPlayer();

		this.selectMission();

		// SELECT SIMULATION MENU
		String simulationModeInfo = "";
		simulationModeInfo += Display.selectSimulationMenu();
		System.out.print(simulationModeInfo);
		simulationModeInfo = "";

		Scanner scanner = new Scanner(System.in);
		int choice;
		boolean validSelection = false;
		while (!validSelection) {
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				switch (choice) {
					case 1:
						this.displayMissionDetails();
						runAutomaticSimulation(newPlayer);
						validSelection = true;
						break;
					case 2:
						this.displayMissionDetails();
						runManualSimulation(newPlayer);
						validSelection = true;
						break;
					case 9:
						validSelection = true;
						return;
					default:
						System.out.println("\nInvalid option!");
						System.out.print("Option: ");
				}
			} else {
				System.out.println("\nInvalid option!");
				System.out.print("Option: ");
				scanner.nextInt();
			}
		}
	}

	/**
	 * Executes the automatic simulation process for the player in the current mission.
	 * This method creates a report for the simulation, initializes the automatic mode,
	 * and executes the game logic for the automatic mode. Once the simulation is
	 * complete, it saves the simulation report to a JSON file.
	 *
	 * @param player the player participating in the automatic simulation process
	 * @throws ElementNotFoundException if a required element is not found during the
	 *                                  simulation
	 * @throws EmptyCollectionException if an operation on a collection in the simulation
	 *                                  encounters an empty collection
	 */
	private void runAutomaticSimulation(Player player) throws ElementNotFoundException, EmptyCollectionException {
		Report report = new Report("Automatic", player, missionImpl);
		report.setBackPackSize(player.getBack_pack().getMaxCapacity());
		AutomaticMode autoMode = new AutomaticMode(missionImpl, player, report);

		autoMode.game();

		try {
			ModeManager.repManager.addReport(report);
		} catch (NotElementComparableException e) {
			System.out.println("erro");
		}

	}

	/**
	 * Executes the manual simulation process for the player in the current mission. This
	 * method creates a report for the simulation, initializes manual mode, and allows the
	 * user to interactively play through the mission. Once the simulation is complete, it
	 * saves the simulation report to a JSON file.
	 *
	 * @param player the player participating in the manual simulation process
	 * @throws EmptyCollectionException if an operation on a collection in the simulation
	 *                                  encounters an empty collection
	 * @throws ElementNotFoundException if a required element is not found during the
	 *                                  simulation
	 */
	private void runManualSimulation(Player player) throws EmptyCollectionException, ElementNotFoundException {
		Report report = new Report("Manual", player, missionImpl);
		report.setBackPackSize(player.getBack_pack().getMaxCapacity());
		ManualMode manualMode = new ManualMode(missionImpl, player, report);

		manualMode.game();

		try {
			ModeManager.repManager.addReport(report);
		} catch (NotElementComparableException e) {
			System.err.println("Error with report.");
		}

	}

	/**
	 * Displays details about the current mission to the user.
	 * <p>
	 * This method aggregates and calls several auxiliary display methods to provide a
	 * comprehensive overview of the mission. The information includes the mission code,
	 * details of the rooms in the battlefield, connections between rooms, enemy
	 * intelligence, available items, entry and exit points, and the mission's target.
	 * <p>
	 * The method performs the following operations sequentially:
	 * 1. Displays the mission code using the `displayMission` method.
	 * 2. Shows the rooms that exist in the mission's battlefield by calling
	 * `displayRoomDetails`.
	 * 3. Displays details of connections between the rooms by calling
	 * `displayAdjacentRoomDetails`.
	 * 4. Provides information about enemies in the mission through `displayEnemyIntel`.
	 * 5. Lists the items available in the battlefield using `displayItems`.
	 * 6. Shows entry and exit points in the mission using `displayEntryExitPoints`.
	 * 7. Displays specific target details for the mission through `displayTarget`.
	 */
	private void displayMissionDetails() {
		this.displayMission();
		this.displayRoomDetails(missionImpl.getBattlefield());
		this.displayAdjacentRoomDetails(missionImpl.getBattlefield());
		this.displayEnemyIntel();
		this.displayItems();
		this.displayEntryExitPoints();
		this.displayTarget();
	}

	/**
	 * Displays the mission information by outputting its unique code.
	 * <p>
	 * This method prints a formatted header followed by the mission's code, retrieved
	 * from the mission implementation's `getCode` method. It provides a concise way to
	 * display the mission identifier to the user.
	 */
	private void displayMission() {
		System.out.println("\n\t=========  MISSION  =========");
		System.out.println(this.missionImpl.getCode());
	}

	/**
	 * Displays the details of the rooms in the given graph structure. Iterates through
	 * the vertices of the graph, which represent rooms, and prints their names to the
	 * console.
	 *
	 * @param graph the graph containing the room vertices to be displayed
	 */
	private void displayRoomDetails(CustomNetwork<Room> graph) {
		String result = ("\n\t========= DIVISIONS =========");

		for (Room room : graph.getVertices()) {
			result += "\nRoom: " + room.getName();
		}
		System.out.println(result);
	}

	/**
	 * Displays the details of adjacent room connections in a given graph structure. For
	 * each room in the graph, this method iterates through its connected rooms and
	 * displays their connections by printing a formatted string to the console.
	 *
	 * @param graph the graph containing the room vertices and their connections
	 */
	private void displayAdjacentRoomDetails(CustomNetwork<Room> graph) {

		String result = ("\n\t========= CONEXOES =========");

		for (Room room : graph.getVertices()) {
			for (Room connectedRoom : graph.getConnectedVertices(room)) {
				// System.out.printf("%-20s <-----> %-15s\n",
				// 	room.getName(), connectedRoom.getName());

				result += "\n[" + room.getName() + "] <-----> [" + connectedRoom.getName() + "]";
			}
		}
		System.out.println(result);
	}

	/**
	 * Displays information about enemies present in the current mission.
	 * <p>
	 * This method retrieves a list of enemies from the current mission implementation and
	 * iterates through each enemy to compile their details into a formatted string. The
	 * details displayed for each enemy include:
	 * - Name
	 * - Fire Power
	 * - Position
	 * <p>
	 * After collecting the information, it prints the formatted enemy intelligence to the
	 * console. This method helps provide an overview of enemy attributes and positions
	 * within the mission's context.
	 */
	private void displayEnemyIntel() {
		String result = ("\n\t========= ENEMIES =========");
		for (Enemy enemy : missionImpl.getEnemies()) {
			// System.out.printf("Name: %-10s Fire Power: %-4s Position: %s\n",
			// 	enemy.getName(), enemy.getFirePower(), enemy.getPosition());
			result += "\nName: " + enemy.getName() +
				"\tFire Power: " + enemy.getFirePower() +
				" Position: " + enemy.getPosition();
		}
		System.out.println(result);
	}

	/**
	 * Displays the details of all items available in the mission.
	 * <p>
	 * This method retrieves the list of items from the current mission implementation,
	 * iterates through each item, and compiles their details into a formatted string. The
	 * details displayed for each item include:
	 * - Name
	 * - Value
	 * - Position
	 * <p>
	 * The collected information is then printed to the console, providing an overview of
	 * the items present in the mission for the user's reference.
	 */
	private void displayItems() {
		String result = ("\n\t=========  ITEMS  =========");
		for (Item item : missionImpl.getItems()) {
			result += "\nItem: " + item.getName() +
				"\tValue: " + item.getItemValue() +
				"\tPosition: " + item.getPosition();
		}
		System.out.println(result);
	}

	/**
	 * Displays the entry and exit points of the current mission.
	 * <p>
	 * This method retrieves the list of rooms designated as entry and exit points for the
	 * mission from the mission implementation. It then iterates through the list and
	 * prints the names of these rooms to the console. The output is accompanied by a
	 * header indicating that the displayed rooms are entry and exit points.
	 */
	private void displayEntryExitPoints() {
		System.out.println("\n===== ENTRY & EXIT POINTS =====");
		for (Room roomObj : missionImpl.getEntryExitPoints()) {
			System.out.println(roomObj.getName());
		}
	}

	/**
	 * Displays the target information associated with the current mission.
	 * <p>
	 * This method prints a formatted header followed by details of the mission's target
	 * to the console. The target is retrieved from the mission implementation using the
	 * `getTarget` method, which provides information about the key objective or location
	 * for the mission. This helps the user easily identify and understand the mission's
	 * ultimate goal within the simulation.
	 */
	private void displayTarget() {
		System.out.println("\n==== TARGET ====");
		System.out.println(missionImpl.getTarget());
	}

	/**
	 * Allows the user to choose a backpack size through a console-based selection
	 * process. This method displays a banner and options for backpack selection, accepts
	 * the user's choice, and returns a `BackPack` object initialized with the chosen
	 * size's capacity.
	 *
	 * @param scanner the Scanner object used to read the user's input from the console
	 * @return a BackPack object initialized with the capacity of the selected backpack
	 * size
	 */
	private BackPack chooseBackPackSize(Scanner scanner) {

		// BACKPACKSIZE MENU
		String backPackSizeInfo = "";
		backPackSizeInfo += Display.selectBackPackMenu();
		System.out.print(backPackSizeInfo);
		backPackSizeInfo = "";

		int choice = 0;
		BackPackSize backPackSize = null;
		boolean isValid = false;
		while (!isValid) {
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();

				switch (choice) {
					case 1:
						backPackSize = BackPackSize.SMALL;
						isValid = true;
						break;
					case 2:
						backPackSize = BackPackSize.MEDIUM;
						isValid = true;
						break;
					case 3:
						backPackSize = BackPackSize.LARGE;
						isValid = true;
						break;
					case 4:
						backPackSize = BackPackSize.TRY_HARD;
						isValid = true;
						break;
					default:
						System.out.println("\nInvalid option!");
						System.out.print("Option: ");
				}
			} else {
				System.out.println("\nInvalid option!");
				System.out.print("Option: ");
				scanner.next();
			}
		}

		return new BackPack(backPackSize.getCapacity());
	}

	/**
	 * Allows the user to select a file path either for saving or opening a file.
	 * Displays a file chooser dialog to the user.
	 *
	 * Reference: Trabalgo Pratico de SI do aluno 8230069
	 *
	 * @param save a boolean value indicating the type of dialog to display.
	 *             If {@code true}, a "Save File" dialog is shown.
	 *             If {@code false}, an "Open File" dialog is shown.
	 * @return the absolute path of the selected file if approved, or {@code null} if the user cancels the operation.
	 */
	protected String choosePath(boolean save) {
		JFileChooser jfile = new JFileChooser();
		jfile.setDialogTitle("Select File");
		int dialog = 0;
		if (save) {
			dialog = jfile.showSaveDialog(null);
		} else if (!save) {
			dialog = jfile.showOpenDialog(null);
		}
		if (dialog == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfile.getSelectedFile();
			return selectedFile.getAbsolutePath();
		}
		return null;
	}
}
