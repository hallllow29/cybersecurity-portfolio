/*
 * Nome: Ruben Xavier Ferreira Nunes
 * Número: 8230069
 * Turma: LSIRCT2
 *
 * Nome: Pedro Daniel Gonçalves Antunes
 * Número: 8230068
 * Turma: LSIRCT2
 *
 */

package pp_tp_er.pickingManagement;

import com.estg.pickingManagement.Report;
import java.time.LocalDateTime;

/**
 * An implementation class that implements the provided {@link com.estg.pickingManagement.Report Report}
 * interface.
 * The report implementation class responsible for the report.
 *
 * @author 8230068
 * @author 8230069
 * @file ReportImpl.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @see com.estg.pickingManagement.Report
 * @since Java SDK 13
 */
public class ReportImpl implements Report {

    /**
     * Total used vehicles of a report.
     */
    private int usedVehicles;

    /**
     * Total picked containers of a report.
     */
    private int pickedContainers;

    /**
     * Total non picked containers.
     */
    private int nonPickedContainers;

    /**
     * Total not used {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     */
    private int notUsedVehicles;

    /**
     * Total distance of a report.
     */
    private double totalDistance;

    /**
     * Total duration of a report.
     */
    private double totalDuration;
    /**
     * The {@link LocalDateTime} of the report.
     */
    private LocalDateTime date;

	/**
	 * Constructor responsible for the report.
	 *
	 * @param date                The date of the report.
	 * @param usedVehicles        The number of used {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
	 * @param pickedContainers    The total of picked containers.
	 * @param totalDistance       The total distance.
	 * @param totalDuration       The total duration.
	 * @param nonPickedContainers The number of none picked containers.
	 * @param notUsedVehicles     The number of {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} not used.
	 */
	public ReportImpl(LocalDateTime date, int usedVehicles, int pickedContainers, double totalDistance, double totalDuration, int nonPickedContainers, int notUsedVehicles) {
        this.date = date;
        this.usedVehicles = usedVehicles;
        this.pickedContainers = pickedContainers;
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
        this.nonPickedContainers = nonPickedContainers;
        this.notUsedVehicles = notUsedVehicles;
    }

    /**
     * Getter for the {@link LocalDateTime date} of the report.
     * @return The {@link #date date} of the report.
     */
    @Override
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Getter for the used {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} of the report.
     * @return The used {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} of the report.
     */
    @Override
    public int getUsedVehicles() {
        return usedVehicles;
    }

    /**
     * Getter for the picked containers of the report.
     * @return The picked containers of the report.
     */
    @Override
    public int getPickedContainers() {
        return pickedContainers;
    }

    /**
     * Getter for the {@link #totalDistance total distance} of the report.
     * @return The {@link #totalDistance total distance} of the report.
     */
    @Override
    public double getTotalDistance() {
        return totalDistance;
    }

    /**
     * Getter for the {@link #totalDuration total duration} of the report.
     * @return The {@link #totalDuration total duration} of the report.
     */
    @Override
    public double getTotalDuration() {
        return totalDuration;
    }

    /**
     * Getter for the non-picked containers of the report.
     * @return The non-picked containers of the report.
     */
    @Override
    public int getNonPickedContainers() {
        return nonPickedContainers;
    }

    /**
     * Getter for the not used {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} of the report.
     * @return The not used {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} of the report.
     */
    @Override
    public int getNotUsedVehicles() {
        return notUsedVehicles;
    }

    /**
     * {@link String} representation of the report.
     * @return The string representation of the report.
     */
    public String toString() {
        return "\n--- Report ---\n" +
                "Date: " + date + "\n" +
                "Used Vehicles: " + usedVehicles + "\n" +
                "Picked Containers: " + pickedContainers + "\n" +
                "Total Distance: " + totalDistance + " meters\n" +
                "Total Duration: " + totalDuration + " minutes\n" +
                "Non-Picked Containers: " + nonPickedContainers + "\n" +
                "Not Used Vehicles: " + notUsedVehicles;
    }
}
