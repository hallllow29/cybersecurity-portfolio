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
package game.util;

import game.briefings.Report;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * The SaveToJsonFile class is responsible for saving a {@link Report} object as a JSON file.
 * The JSON file is stored in a directory named "reports" with a unique filename for each report.
 */
public class SaveToJsonFile {

    /**
     * Represents the directory path where JSON files corresponding to reports are saved.
     * Each saved report is stored in this directory to maintain organization and separation of report data.
     * The directory is created if it does not already exist before saving a file.
     */
    private static final String REPORTS_DIR = "reports/";
    /**
     * A static counter used to generate unique filenames for JSON report files.
     * Each time a report is saved, this counter is incremented to ensure file names are unique
     * within the "reports" directory.
     */
    private static final int counter = 0;

    /**
     * Saves the given {@code Report} object as a JSON file in the specified directory.
     * The method generates JSON data containing details about the report, player, mission,
     * paths, and enemies, and writes it to a file with a unique name.
     *
     * @param report the {@code Report} object containing details to be saved as JSON
     */
    public static void saveJsonFile(Report report) {

        File dir = new File(REPORTS_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }


        String fileName = REPORTS_DIR + report.getMission().getCode()+ "_" + report.getSimulationId() + ".json";

        //Main report info
        JSONObject jsonReport = new JSONObject();
        jsonReport.put("simulationId" , report.getSimulationId());
        jsonReport.put("timestamp", report.getTimestamp());
        jsonReport.put("type", report.getType());

        //Player info
        JSONObject player = new JSONObject();
        player.put("name", report.getPlayer().getName());
        player.put("finalHealth", report.getPlayer().getCurrentHealth());
        player.put("firePower", report.getPlayer().getFirePower());
        player.put("backPack", report.getBackPackSize());
        jsonReport.put("player", player);

        //Mission info
        JSONObject mission = new JSONObject();
        mission.put("code", report.getMission().getCode());
        mission.put("target", report.getMission().getTarget().getRoom().getName());
        mission.put("entryPoint", report.getEntryPoint());
        mission.put("missionStatus", report.getMissionStatus());
        jsonReport.put("mission", mission);

        //Trajectory to target info
        JSONArray pathToTargetArray = new JSONArray();
        for (String room : report.getTrajectoryToTarget()) {
            pathToTargetArray.add(room);
        }
        jsonReport.put("trajetoryToTarget", pathToTargetArray);

        //Trajectory to extraction info
        JSONArray pathToExtraction = new JSONArray();
        for (String room : report.getTrajectoryToExtraction()) {
            pathToExtraction.add(room);
        }

        jsonReport.put("trajectoryToExtraction", pathToExtraction);

        //Enemies survived
        JSONArray enemiesSurvived = new JSONArray();
        for (String enemy : report.getEnemiesSurvived()) {
            enemiesSurvived.add(enemy);
        }
        jsonReport.put("enemiesSurvived", enemiesSurvived);

        //Enemies killed
        JSONArray enemiesKilled = new JSONArray();
        for (String enemy : report.getEnemiesKilled()) {
            enemiesKilled.add(enemy);
        }

        jsonReport.put("enemiesKilled", enemiesKilled);

        //Try saving the report
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(jsonReport.toJSONString());
            System.out.println("Report saved as: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving report");
        }

    }
}
