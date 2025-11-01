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
package game.briefings;

import entities.Mission;
import entities.Player;
import lib.lists.ArrayUnorderedList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * The Report class represents a detailed record of a game simulation or mission. It
 * encapsulates information such as the mission type, player involved, mission details,
 * timing, entry point, trajectories, enemies encountered, and backpack details. The
 * report is used to track the progress, status, and outcomes of a mission or game
 * session.
 */
public class Report implements Comparable<Report> {

	/**
	 * Represents a unique identifier for a simulation associated with this report.
	 * The simulation ID is assigned during the creation of the report and is used to
	 * distinguish it from other reports within the system.
	 */
	private final int simulationId;

	/**
	 * Represents the timestamp associated with the report. This variable holds the time
	 * at which the report was generated or updated, formatted as a string. It can be used
	 * to track or identify the temporal information related to the report.
	 */
	private final LocalDateTime timestamp;

	/**
	 * Represents the type or category of a report in the simulation. This variable is
	 * used to define and store the specific type of report, providing contextual
	 * information about the nature of the report. For example, it could indicate whether
	 * the report is a mission summary, player status update, or an event log generated
	 * during the simulation.
	 */
	private String type;

	/**
	 * Represents the status of a mission associated with the report. This variable stores
	 * information about the current status or outcome of the mission, such as whether it
	 * is ongoing, completed successfully, or failed.
	 */
	private String missionStatus;

	/**
	 * Represents the player involved in the mission report. This variable holds a
	 * reference to the Player object associated with the report, encapsulating the
	 * player's attributes, position, inventory, and other game-related details.
	 */
	private Player player;

	/**
	 * Represents the mission associated with the report. A mission contains details such
	 * as the battlefield, objectives, enemies, items, and entry/exit points. This
	 * variable links the report to the specific mission in the context of a game or
	 * simulation.
	 */
	private Mission mission;

	/**
	 * Represents the player's starting location or point of entry in the mission. The
	 * entry point identifies the specific area or room from which the mission begins. It
	 * provides context for the mission's starting situation and can be used to track the
	 * player's initial position within the mission environment.
	 */
	private String entryPoint;

	/**
	 * Represents the size of the backpack in the context of a report. This variable is
	 * used to indicate the capacity or size type of the backpack associated with the
	 * player or mission in the report.
	 * <p>
	 * It is expected to hold descriptive values, such as "small", "medium", or "large",
	 * reflecting the backpack's storage capacity.
	 */
	private String backPackSize;

	/**
	 * Represents an unordered list of strings that tracks the sequence of rooms or areas
	 * traversed on the way to the mission's target destination. This field is used to
	 * store the trajectory path to the target in chronological order as strings.
	 */
	private final ArrayUnorderedList<String> trajectoryToTarget;

	/**
	 * Represents the trajectory taken by the player to reach the extraction point during
	 * a mission. The trajectory is stored as an unordered list of room names or waypoints
	 * traversed by the player.
	 */
	private final ArrayUnorderedList<String> trajectoryToExtraction;

	/**
	 * Represents a collection of enemy entities that survived a mission or simulation.
	 * This list is maintained as an unordered collection of strings, where each string
	 * corresponds to the name or identifier of a specific enemy that was not eliminated.
	 * It provides a means to track and retrieve details about enemies that remained at
	 * the end of the scenario represented by the {@code Report} class.
	 */
	private final ArrayUnorderedList<String> enemiesSurvived;

	/**
	 * A list that stores the names of enemies that were killed during a mission. This
	 * field is implemented as an unordered list, allowing flexible addition of enemy
	 * names in no particular order. It helps track the outcomes of a mission and provides
	 * insights into the player's interactions with enemies.
	 */
	private final ArrayUnorderedList<String> enemiesKilled;


	/**
	 * Constructs a new Report instance with the specified type, player, and mission. This
	 * constructor initializes the report with a unique simulation ID, a timestamp, and
	 * references to the player and mission involved. It also initializes several
	 * collections to track various aspects of the mission such as trajectory paths and
	 * enemy states.
	 *
	 * @param type    the type of the report (e.g., simulation, mission result)
	 * @param player  the Player instance associated with the report
	 * @param mission the Mission instance associated with the report
	 */
	public Report(String type, Player player, Mission mission) {
		this.simulationId = new Random().nextInt(22222);
		this.timestamp = LocalDateTime.now();
		this.player = player;
		this.mission = mission;
		this.type = type;
		this.trajectoryToTarget = new ArrayUnorderedList<>();
		this.enemiesSurvived = new ArrayUnorderedList<>();
		this.trajectoryToExtraction = new ArrayUnorderedList<>();
		this.enemiesKilled = new ArrayUnorderedList<>();
		this.entryPoint = null;
		this.missionStatus = null;
		this.backPackSize = null;
	}

	/**
	 * Retrieves the size of the backpack associated with the report.
	 *
	 * @return a string representing the backpack size.
	 */
	public String getBackPackSize() {
		return this.backPackSize;
	}

	/**
	 * Updates the size of the backpack based on the provided value. The backpack size is
	 * represented as a descriptive string corresponding to the given integer value.
	 *
	 * @param backPackSize an integer representing the backpack size. Acceptable values
	 *                     are: 0 - "Try hard mode (No backpack)" 1 - "Small Backpack" 2 -
	 *                     "Medium Backpack" 5 - "Big Backpack" Any other value results in
	 *                     "No backPack used".
	 */
	public void setBackPackSize(int backPackSize) {
		switch (backPackSize) {
			case 0:
				this.backPackSize = "Try hard mode (No backpack)";
				break;
			case 1:
				this.backPackSize = "Small Backpack";
				break;
			case 2:
				this.backPackSize = "Medium Backpack";
				break;
			case 5:
				this.backPackSize = "Big Backpack";
				break;
			default:
				this.backPackSize = "No backPack used";
				break;
		}
	}

	/**
	 * Retrieves the entry point associated with the mission.
	 *
	 * @return a string representing the mission's entry point.
	 */
	public String getEntryPoint() {
		return this.entryPoint;
	}

	/**
	 * Updates the entry point for the mission's report.
	 *
	 * @param roomName the name of the room to be set as the entry point.
	 */
	public void setEntryPoint(String roomName) {
		this.entryPoint = roomName;
	}

	/**
	 * Retrieves the timestamp associated with the report.
	 *
	 * @return the timestamp as a string.
	 */
	public String getTimestamp() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		return this.timestamp.format(formatter);
	}

	/**
	 * Retrieves the type of the report.
	 *
	 * @return a string representing the type of the report.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of the report. The type specifies the category or context of the
	 * report, such as simulation, mission result, or other relevant types.
	 *
	 * @param type a string representing the type of the report
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Retrieves the player associated with the report.
	 *
	 * @return a Player object representing the player associated with this report
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player associated with this report.
	 *
	 * @param player the Player object to be associated with this report
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Retrieves the mission associated with this report.
	 *
	 * @return a Mission object representing the mission associated with this report.
	 */
	public Mission getMission() {
		return mission;
	}

	/**
	 * Retrieves the status of the mission associated with the report.
	 *
	 * @return a string representing the mission status.
	 */
	public String getMissionStatus() {
		return this.missionStatus;
	}

	/**
	 * Updates the status of the mission associated with the report.
	 *
	 * @param status a string representing the mission status to be set. It typically
	 *               indicates the outcome or progress of the mission, such as
	 *               "entities.Mission Accomplished" or "entities.Mission Failed".
	 */
	public void setMissionStatus(String status) {
		this.missionStatus = status;
	}

	/**
	 * Updates the mission associated with this report.
	 *
	 * @param mission the Mission object to be set for this report.
	 */
	public void setMission(Mission mission) {
		this.mission = mission;
	}

	/**
	 * Retrieves the trajectory path leading to the mission target.
	 *
	 * @return an ArrayUnorderedList of strings, where each string represents a step or
	 * location in the trajectory path to the target.
	 */
	public ArrayUnorderedList<String> getTrajectoryToTarget() {
		return trajectoryToTarget;
	}

	/**
	 * Retrieves the unique simulation ID associated with the report.
	 *
	 * @return a string representing the simulation ID.
	 */
	public int getSimulationId() {
		return simulationId;
	}

	/**
	 * Adds a room to the trajectory path leading to the mission target.
	 *
	 * @param roomName the name of the room to be added to the trajectory path
	 */
	public void addRoom(String roomName) {
		this.trajectoryToTarget.addToRear(roomName);
	}

	/**
	 * Adds a room to the trajectory path leading to the extraction point.
	 *
	 * @param roomName the name of the room to be added to the extraction trajectory
	 */
	public void addRoomToExtraction(String roomName) {
		this.trajectoryToExtraction.addToRear(roomName);
	}

	/**
	 * Adds the specified enemy to the list of enemies that survived the mission.
	 *
	 * @param enemyName the name of the enemy to be added to the list of survived enemies
	 */
	public void addEnemy(String enemyName) {
		this.enemiesSurvived.addToRear(enemyName);
	}

	/**
	 * Retrieves the list of enemies that survived the mission.
	 *
	 * @return an ArrayUnorderedList of strings, where each string represents the name of
	 * an enemy that survived the mission.
	 */
	public ArrayUnorderedList<String> getEnemiesSurvived() {
		return this.enemiesSurvived;
	}

	/**
	 * Retrieves the trajectory path leading to the extraction point.
	 *
	 * @return an ArrayUnorderedList of strings, where each string represents a step or
	 * location in the trajectory path to the extraction point.
	 */
	public ArrayUnorderedList<String> getTrajectoryToExtraction() {
		return this.trajectoryToExtraction;
	}

	/**
	 * Retrieves the current timestamp in the format "yyyy-MM-dd'T'HH:mm:ss". The
	 * timestamp represents the current date and time when the method is called.
	 *
	 * @return a string representing the current timestamp in ISO 8601 format.
	 */
	private String getCurrentTimestamp() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		return now.format(formatter);
	}

	/**
	 * Adds the specified enemy to the list of killed enemies in the report.
	 *
	 * @param enemy the name of the enemy to be added to the list of killed enemies
	 */
	public void addEnemyKilled(String enemy) {
		this.enemiesKilled.addToRear(enemy);
	}

	/**
	 * Retrieves the list of enemies that were killed during the mission.
	 *
	 * @return an ArrayUnorderedList of strings, where each string represents the name of
	 * an enemy that was eliminated.
	 */
	public ArrayUnorderedList<String> getEnemiesKilled() {
		return this.enemiesKilled;
	}

	/**
	 * Compares this report object to another report object for order based on the
	 * player's current health. The comparison is done in descending order, where
	 * higher current health comes first.
	 *
	 * @param o the other Report object to be compared
	 * @return a negative integer, zero, or a positive integer as this report's player
	 *         has less than, equal to, or greater current health compared to the other
	 *         report's player
	 */
	@Override
	public int compareTo(Report o) {
		return Integer.compare(o.getPlayer().getCurrentHealth(), this.player.getCurrentHealth());
	}

	/**
	 * Returns a string representation of the Report object.
	 * The string contains details about the report, including the mission version, simulation ID,
	 * timestamp, type, mission status, player information, backpack size, and various
	 * trajectory and mission-related details.
	 *
	 * @return a string representation incorporating the properties of the Report object.
	 */
	@Override
	public String toString() {
		return
			"\nMission Version:\t" + this.mission.getVersion() +
				"\nSimulation ID:\t" + this.simulationId +
				"\nTimestamp:\t " + getTimestamp() +
				"\nType:\t " + this.type +
				"\nMission Status: " + this.missionStatus +
				"\nPlayer: " + this.player.getName() +
				"\nPlayer HP: " + this.player.getCurrentHealth() +
				"\nMission: " + this.mission.getCode() +
				"\nMission Target: " + this.mission.getTarget() +
				"\nEntry Point: " + this.entryPoint +
				"\nBackPack Size: " + this.backPackSize +
				"\n\nPath to Target:\n" + this.trajectoryToTarget +
				"\nPath to Extraction Point:\n" + this.trajectoryToExtraction +
				"\nEnemies Survived:\n" + this.enemiesSurvived +
				"\nEnemies Eliminated:\n" + this.enemiesKilled;

	}
}
