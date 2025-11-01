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

import game.util.SaveToJsonFile;
import lib.exceptions.NotElementComparableException;
import lib.lists.LinkedOrderedList;

/**
 * The MissionReportManager class provides functionality to manage mission reports,
 * including listing available reports, visualizing individual reports, and retrieving
 * information about missions and simulations.
 */
public class MissionReportManager {

	private final LinkedOrderedList<Report> reports;

	/**
	 * Constructs a new MissionReportManager instance.
	 * Initializes an internal collection for managing mission reports using a LinkedOrderedList.
	 */
	public MissionReportManager() {
		this.reports = new LinkedOrderedList<>();
	}

	/**
	 * Adds a new report to the collection of mission reports.
	 *
	 * @param report the report to be added to the collection
	 * @throws NotElementComparableException if the report cannot be compared or added
	 *                                       to the underlying collection
	 */
	public void addReport(Report report) throws NotElementComparableException {
		this.reports.add(report);
	}

	/**
	 * Displays all the reports in the collection managed by the MissionReportManager.
	 *
	 * If the collection of reports is null, a message is displayed indicating
	 * that no reports are found. If the collection is empty, a prompt is shown
	 * to add a new report in order to visualize them. Otherwise, each report in
	 * the collection is printed using its string representation.
	 */
	public void viewReports() {
		if (this.reports == null) {
			System.out.println("No reports found");
		} else if (this.reports.isEmpty()) {
			System.out.println("Add a new report, to be able to visualize");
		}

		for (Report report : this.reports) {
			System.out.println(report.toString());
		}
	}

	/**
	 * Saves all the reports in the collection to JSON files.
	 *
	 * This method iterates through the collection of reports and uses the
	 * SaveToJsonFile utility to save each report as a JSON file. If the
	 * collection of reports is empty, a message indicating the absence of
	 * reports is displayed. If the collection contains reports, a confirmation
	 * message is printed after all reports have been successfully saved.
	 */
	public void saveALlReports() {
		if (this.reports.isEmpty()) {
			System.out.println("No reports found");
		} else {
			for (Report report : this.reports) {
				SaveToJsonFile.saveJsonFile(report);
			}
			System.out.println("All reports saved!");

		}

	}

	/**
	 * Displays reports grouped by the mission they belong to, with detailed information about
	 * each mission and its corresponding reports.
	 *
	 * This method organizes the reports from the collection by their associated mission codes
	 * and ensures that duplicates are not displayed. For each unique mission, a header with
	 * the mission code is printed, followed by details of all reports associated with that
	 * mission. Each report displays the mission version, simulation ID, player's current health,
	 * and mission status.
	 *
	 * If the collection of reports is empty, a prompt message is displayed to indicate that
	 * new reports should be added.
	 *
	 * @throws NotElementComparableException if adding a report to the internal linked list
	 *                                       fails due to incompatibility with the required
	 *                                       comparable constraints
	 */
	public void displayReportByMission() throws NotElementComparableException {
		LinkedOrderedList<Report> codes = new LinkedOrderedList<>();

		if (this.reports.isEmpty()) {
			System.out.println("Complete a mission and a add a new report.");
		}

		for (Report report : this.reports) {
			boolean alreadyAdded = false;
			for (Report code : codes) {
				if (code.getMission().getCode().equals(report.getMission().getCode())) {
					alreadyAdded = true;
					break;
				}
			}

			if (!alreadyAdded) {
				codes.add(report);
			}
		}

		for (Report uniqueReport : codes) {
			String missionCode = uniqueReport.getMission().getCode();
			System.out.println("Mission: " + missionCode);
			LinkedOrderedList<Report> missionReports = new LinkedOrderedList<>();

			for (Report report : this.reports) {
				if (report.getMission().getCode().equals(missionCode)) {
					missionReports.add(report);
				}
			}

			for (Report report : missionReports) {
				System.out.println("Version: " + report.getMission().getVersion() + " | ID: " + report.getSimulationId() + " | HP : " + report.getPlayer().getCurrentHealth() + " | Status: " + report.getMissionStatus());
			}

			System.out.println();
		}
	}

}
