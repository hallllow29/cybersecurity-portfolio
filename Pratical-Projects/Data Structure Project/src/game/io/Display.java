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
package game.io;

public class Display {

	// ------------------------------ SCENARIOS ---------------------------------------------

	// Scenario 1
	public static String scenarioUMstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 1 START >>] ==========" +
			"\n|\t" + playerName + " makes contact with ENEMIES..." +
			"\n|\tAND has priority of attack over ENEMIES...";
	}

	public static String scenarioUMendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 1  END  >>] ==========";
	}

	// Scenario 2
	public static String scenarioDOISstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 2 START >>] ==========" +
			"\n|\tAND the room " + playerName + " is, is clear...";
	}

	public static String scenarioDOISendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 2  END  >>] ==========";
	}

	// Scenario 3
	public static String scenarioTRESstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 3 START >>] ==========" +
			"\n|\tENEMIES make contact with " + playerName + "..." +
			"\n|\tAND have priority of attack over " + playerName + "...";
	}

	public static String scenarioTRESendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 3  END  >>] ==========";
	}

	// Scenario 4
	public static String scenarioQUATROstartMessage(String playerName) {
		return "\n|" + "\n|========== [<< SCENARIO 4 START >>] ==========" +
			"\n|\t" + playerName + " seems to be injured...";
	}

	public static String scenarioQUATROendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 4  END  >>] ==========";
	}

	// Scenario 5
	public static String scenarioCINCOstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 5 START >>] ==========" +
			"\n|\t" + playerName + " makes contact with ENEMIES..." +
			"\n|\tAND has priority of attack over ENEMIES..." +
			"\n|\tAND in that room there is the TARGET...";
	}

	public static String scenarioCINCOendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 5  END  >>] ==========";
	}

	// Scenario 6
	public static String scenarioSEISstartMessage() {
		return "\n|========== [<< SCENARIO 6 START >>] ==========" +
			"\n|\tAND in that room there is the TARGET..." +
			"\n|\tLOOK AT THAT, it is clear...";
	}

	public static String scenarioSEISendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 6  END  >>] ==========";
	}

	// Generic Scenario Messages
	public static String scenarioNRstartMessage(int scenarioNumber) {
		return "\n|========== [<< SCENARIO " + scenarioNumber + " START >>] ==========";
	}

	public static String scenarioNRendMessage(int scenarioNumber) {
		return "\n|" + "\n|========== [<< SCENARIO " + scenarioNumber + "  END  >>] ==========";
	}

	// ----------------------------- PLAYER -------------------------------------------------

	public static String playerStartsInEntryPoint(String playerName, String missionName, String entryPointName) {
		return "\n" + playerName + " starts mission " + missionName + " in" +
			"\n\t [" + entryPointName + "]";
	}

	public static String playerIsLeavingMessage(String playerName, String playerPositionName) {
		return
			"\n" + playerName + " is moving..." +
			"\n" + playerName + " leaves " + playerPositionName + "...";
	}

	public static String playerTurnMessage() {
		return "\n|" + "\n|\t-------------- PLAYER  TURN --------------";
	}

	public static String playerIsAttackingMessage(String playerName, String enemyName, int playerAttack) {
		return "\n|\t" + playerName + " is attacking " + enemyName + "..." +
			"\n|\twith " + playerAttack + " damage...";
	}

	public static String playerDiedMessage(String playerName) {
		return "\n|\t" + playerName + " DIED DAMN IT!!!!!";
	}

	public static String playerChecksBackPackMessage(String playerName) {
		return "\n|\t" + playerName + " halts and is checking BackPack...";
	}

	public static String playerAddsItemMessage(String playerName, String itemName) {
		return "\n|" + "\n|\t" + playerName + " adds " + itemName + " to his BackPack...";
	}

	public static String playerEquipsItemMessage(String playerName, String itemName, int givenItemValue) {
		return "\n|" + "\n|\t" + playerName + " equips " + itemName + ", current health " + givenItemValue + "...";
	}

	public static String playerSearchsMessage(String playerName, String playerPosition) {
		return "\n|" + "\n|\t" + playerName + " searches the " + playerPosition + "...";
	}

	public static String playerEliminatedAllEnemiesInPositionMessage(String playerName, String playerPositionName) {
		return "\n|" +
			"\n|\t" + playerName + " eliminated all ENEMIES..." +
			"\n|\tin " + playerPositionName + "...";
	}

	public static String playerEntersInMessage(String playerName, String playerPositionName) {
		return "\n" + playerName + " enters in " + playerPositionName + "...";
	}

	public static String playerIsInMessage(String playerName, String playerPositionName) {
		return "\n" + playerName + " is in " + playerPositionName + "...";
	}

	public static String playerHealthFullMessage() {
		return "\nHealth is full!";
	}

	public static String playerHealthStatusMessage(int playerHealth) {
		return "\n|HP: " + playerHealth + "/100";
	}

	public static String backPackContentMessage(String backPackContent) {
		return "\n" + backPackContent;
	}

	public static String backPackNoItemsMessage() {
		return "\n|BackPack has no items";
	}

	public static String noMediKitsBackPackMessage() {
		return "\nNo MediKits in BackPack!";
	}

	public static String playerBanner() {
		return "\n\n\t========  PLAYER  =========";
	}

	// ------------------------------ ENEMY -------------------------------------------------

	public static String enemyEnduredAttackMessage(String enemyName, int playerAttack) {
		return "\n|\tENEMY " + enemyName + " endured " + playerAttack + " of attack..." + "\n|";
	}

	public static String enemySufferedAttackMessage(String enemyName, int playerAttack) {
		return "\n|" + "\n|\tENEMY " + enemyName + " suffered " + playerAttack + " of attack...";
	}

	public static String enemyIsDeadMessage() {
		return "\n|\tAND is now DEAD!!!";
	}

	public static String enemyIsAttackingMessage(String enemyName, String playerName, int enemyAttack) {
		return "\n|" + "\n|\t" + enemyName + " is attacking " + playerName + "..." +
			"\n|\twith " + enemyAttack + " damage...";
	}

	public static String enemyTurnMessage() {
		return "\n|" + "\n|\t-------------- ENEMY  TURN  --------------";
	}

	public static String enemiesSurvivedAttackMessage(String playerPositionName) {
		return "\n|\tENEMIES in " + playerPositionName + " survived the attack..." +
			"\n|\tENEMIES not in " + playerPositionName + " are moving...";
	}

	public static String enemiesNotIsTheSamePositionMessage(String playerPositionName) {
		return "\n|\tENEMIES in " + playerPositionName + " engaging in confront..." +
			"\n|\tENEMIES not in " + playerPositionName + " are moving...";
	}

	public static String enemiesAreMovingMessage() {
		return "\n|\tBUT the enemies are somewhere..." +
			"\n|\tEnemies are moving...";
	}

	public static String noEnemiesLeftMessage() {
		return "\n|\tBUT there are no enemies left...";
	}

	public static String enemiesEngageConfront(String playerPositionName) {
		return "\n|\tENEMIES engage confront in " + playerPositionName + "...";
	}

	public static String enemiesBanner() {
		return "\n\n\t========= ENEMIES =========";
	}

	public static String enemiesIntelMessage(String enemyName, int enemyAttack, String enemyPositionName) {
		return "\n|Name: " + enemyName + "\tFire Power: " + enemyAttack + "\tPosition: " + enemyPositionName;
	}

	// ------------------------------ TARGET -------------------------------------------------

	public static String targetInExtractionPointMessage(String playerName) {
		return "\n|" +
			"\n|\tTARGET is in EXTRACTION POINT..." +
			"\n|\tWELL DONE " + playerName + " is returning to base...";
	}

	public static String targetIsSecuredMessage(String playerName, String nextObjectiveName) {
		return "\n|" + "\n|\tTARGET is now secured..." +
			"\n|\tWELL DONE " + playerName + " return to " + nextObjectiveName + "...";
	}

	// ------------------------------ ITEMS -------------------------------------------------

	public static String itemsSpottedMessage() {
		return "\n|\tAND spots some items in the room...";
	}

	public static String itemsIntelMessage(String itemName, int itemValue, String itemPositionName) {
		return "\n|Item: " + itemName + "\tValue: " + itemValue + "\tPosition: " + itemPositionName;
	}

	public static String mediKitsKevlarsBanner() {
		return "\n\n\t=========  ITEMS  =========";
	}

	public static String noMediKitsLeftMessage() {
		return "\nThere are no more medic kits available on the building!";
	}

	// ------------------------------ PATH ---------------------------------------------------

	public static String bestPathObjectiveMessage() {
		return "\nCalculating best path to OBJECTIVE..." +
			"\nPath to OBJECTIVE";
	}

	public static String bestPathExtractionMessage() {
		return "\nCalculating best path to EXTRACTION POINT..." +
			"\nPath to EXTRACTION POINT";
	}

	public static String closestMediKitBanner() {
		return "\n\n==== BEST PATH TO CLOSEST MEDIC KIT ====";
	}

	// ------------------------------ MENUS -------------------------------------------------

	public static String welcomeMenu() {
		return "\t==== WELCOME TO IMPROBABLE MISSION FORCE ====" +
			"\n[1] Mission Select" +
			"\n[2] Report Menu" +
			"\n[9] Exit" +
			"\n\nOption: ";
	}

	public static String welcomeIMFbanner() {
		return "\n\n\t==== CREATE PLAYER ====";
	}

	public static String selectBackPackMenu() {
		return "\n\n\t==== SELECT BACKPACK SIZE ====" +
			"\n[1] SMALL \t[max 1 item ]" +
			"\n[2] MEDIUM\t[max 2 items]" +
			"\n[3] LARGE \t[max 5 items]" +
			"\n[4] NONE \t[survival]" +
			"\n\n Option: ";
	}

	public static String selectMissionMenu() {
		return "\n\n\t==== SELECT MISSION MODE ====" +
			"\n[1] NORMAL MISSION" +
			"\n[2] RATO DE ACO" +
			"\n[3] CUSTOM" +
			"\n\n Option: ";
	}

	public static String selectSimulationMenu() {
		return "\n\n\t==== SELECT SIMULATION MODE ====" +
			"\n[1] AUTOMATIC" +
			"\n[2] MANUAL" +
			"\n\nOption: ";
	}

	public static String selectReportMenu() {
		return "\n\t==== REPORTS MENU ====" +
			"\n[1] Export all reports" +
			"\n[2] Visualize Report" +
			"\n[3] Display Reports By mission" +
			"\n[4] Back" +
			"\n\nOption: ";
	}

	public static String allPossibleEntriesBanner() {
		return "\n\n\t==== IMF - ENTRY POINT SELECTION ====";
	}

	public static String entryPointSelection(String bestEntryPointName, String entryPointName) {
		return "\nYour best entry point to the target is " + bestEntryPointName +
			"\n[1] Select this entry point: " + entryPointName +
			"\n[2] Next Entry Point" +
			"\n\nOption: ";
	}

	public static String possibleMovesMessage() {
		return "\n\n\t==== POSSIBLE MOVES ====";
	}

	public static String yourNextPositionMessage(String nextPositionName) {
		return "\nYour next position is: [" + nextPositionName + "]";
	}

	public static String youChooseStayMessage() {
		return "\nYou choose to stay.";
	}

	public static String optionNrMessage(int option, String selection) {
		return "\n[" + option + "] " + selection;
	}

	public static String selectMessage(String thing) {
		return "\nPlease select " + thing + ":";
	}

	public static String invalidOptionMessage() {
		return "\nINVALID OPTION.";
	}

	// ------------------------------ BANNERS -------------------------------------------------

	public static String initSimulation() {
		return "\n==== SSS Sophisticated Spy System by IMF ====" +
			"\n|Initializing Simulation..." +
			"\n|Gathering Intel for Simulation...";
	}

	public static String sophisticatedSpySystemBanner() {
		return "\n\n==== SSS Sophisticated Spy System by IMF ====";
	}

	public static String collectingData() {
		return "\n|Collecting Simulation Environment Data..." +
			"\n|Collecting ENEMIES Movement Data";
	}

	public static String renderingNextSituationMessage() {
		return "\n\n\t======== ENVIRONMENT =========" +
			"\n|Rendering next situation...";
	}

	public static String enterYourNameMessage() {
		return "\nPlease enter your codename: ";
	}
}