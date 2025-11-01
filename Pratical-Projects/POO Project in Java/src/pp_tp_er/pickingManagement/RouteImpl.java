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

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.exceptions.ContainerException;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;
import pp_tp_er.core.AidBoxImpl;

import java.util.Arrays;

/**
 * Class that implements the route interface.
 *
 * @date 2024 /06/28
 */
public class RouteImpl implements Route {

	/**
	 * Total of aid boxes on the route.
	 */
	private int totalAidBoxes;

	/**
	 * The total distance the route has.
	 */
	private double totalDistance;

	/**
	 * The total duration the route has.
	 */
	private double totalDuration;

	/**
	 * Vehicle responsible for that route.
	 */
	private Vehicle vehicle;

	/**
	 * The report for that route.
	 */
	private Report report;

	/**
	 * Aid boxes belong to that route.
	 */
	private AidBox[] aidBoxes;

	/**
	 * Constructor for the route.
	 *
	 * @param vehicle       Vehicle to which the route belongs.
	 * @param aidBoxes      Array with all aid boxes of the route.
	 * @param totalDuration The total duration of the route.
	 * @param totalDistance The total distance of the route.
	 * @param report        The report for the route.
	 */
	public RouteImpl(Vehicle vehicle, AidBox[] aidBoxes, double totalDuration, double totalDistance, Report report) {
		this.vehicle = vehicle;
		this.aidBoxes = aidBoxes;
		this.totalDuration = totalDuration;
		this.totalDistance = totalDistance;
		this.totalAidBoxes = aidBoxes.length;
		this.report = report;
	}

	/**
	 * Add an aid box to the route.
	 * @param aidBox The aid box to be added.
	 * @throws RouteException If the aid box provided is null.
	 * 						  If the aid box already exists in the route.
	 * 						  If the aid box is compatible with the vehicles route.
	 */
	@Override
	public void addAidBox(AidBox aidBox) throws RouteException {
		if (aidBox == null) {
			throw new RouteException("GIVEN AIDBOX IS NULL");
		}
		if (containsAidBox(aidBox)) {
			throw new RouteException("GIVEN AIDBOX ALREADY EXISTS IN ROUTE");
		}
		if (compatibleAidBox(aidBox)) {
			throw new RouteException("GIVEN AIDBOX IS INCOMPATIBLE");
		}
		if (totalAidBoxes >= aidBoxes.length) {
			aidBoxes = Arrays.copyOf(aidBoxes, 2 * aidBoxes.length);
		}
		aidBoxes[totalAidBoxes++] = aidBox;
		updateRouteMetrics();
	}

	/**
	 * Removes an aid box from the route.
	 * @param aidBox The aid box to remove from the route.
	 * @return AidBox The aid box removed from the route.
	 * @throws RouteException If the aid box is null.
	 * 					      If there are no aid box to be removed.
	 * 					      If the aid box does not exist on the route.
	 * 					      If the aid box not found on the route.
	 *
	 */
	@Override
	public AidBox removeAidBox(AidBox aidBox) throws RouteException {
		if (aidBox == null) {
			throw new RouteException("GIVEN AIDBOX IS NULL");
		}
		if (totalAidBoxes <= 0) {
			throw new RouteException("NO AIDBOX LEFT TO REMOVE");
		}
		if (!containsAidBox(aidBox)) {
			throw new RouteException("GIVEN AIDBOX DOES NOT EXIST");
		}
		int indexAidBox = getIndexAidBox(aidBox);
		if (indexAidBox == -1) {
			throw new RouteException("AIDBOX NOT FOUND IN THE ROUTE");
		}
		AidBox removedAidBox = aidBoxes[indexAidBox];
		shiftAidBoxes(indexAidBox);
		totalAidBoxes--;
		updateRouteMetrics();
		return removedAidBox;
	}

	/**
	 * Checks if the aid box is on the route.
	 * @param aidBox The aid box to check.
	 * @return True, if the aid box is on the route.
	 * 			False, otherwise.
	 */

	@Override
	public boolean containsAidBox(AidBox aidBox) {
		for (int i = 0; i < totalAidBoxes; i++) {
			if (aidBoxes[i].equals(aidBox)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Replace an aid box on the route to another.
	 * @param from The aid box that will be replaced.
	 * @param to The aid box to replace the old one.
	 * @throws RouteException If the one of the aid boxes is null.
	 * 						  If the "from" aid box does not exist on the route.
	 * 						  If the "to" aid box already exist on the route.
	 * 						  If the aid box to replace with is not compatible with the route.
	 */
	@Override
	public void replaceAidBox(AidBox from, AidBox to) throws RouteException {
		if (from == null || to == null) {
			throw new RouteException("ONE OF GIVEN AIDBOX IS NULL");
		}
		if (!containsAidBox(from)) {
			throw new RouteException("GIVEN AIDBOX TO REPLACE DOES NOT EXIST");
		}
		if (containsAidBox(to)) {
			throw new RouteException("GIVEN AIDBOX TO INSERT ALREADY EXISTS");
		}
		if (compatibleAidBox(to)) {
			throw new RouteException("GIVEN AIDBOX TO INSERT IS INCOMPATIBLE");
		}
		int indexAidBox = getIndexAidBox(from);
		aidBoxes[indexAidBox] = to;
		updateRouteMetrics();
	}

	/**
	 * Insert and aid box after another.
	 * @param after The aid box after which the new one will be inserted.
	 * @param toInsert The aid box to insert.
	 * @throws RouteException If the one of the aid boxes is null.
	 * 						  If the "after" aid box does not exist on the route.
	 * 						  If the "toInsert" aid box already exist on the route.
	 * 						  If the aid box to insert after is not compatible with the route.
	 */

	@Override
	public void insertAfter(AidBox after, AidBox toInsert) throws RouteException {
		if (after == null || toInsert == null) {
			throw new RouteException("ONE OF GIVEN AIDBOX IS NULL");
		}
		if (!containsAidBox(after)) {
			throw new RouteException("GIVEN AIDBOX TO INSERT AFTER DOES NOT EXIST");
		}
		if (containsAidBox(toInsert)) {
			throw new RouteException("GIVEN AIDBOX TO INSERT ALREADY EXISTS");
		}
		if (compatibleAidBox(toInsert)) {
			throw new RouteException("GIVEN AIDBOX TO INSERT IS INCOMPATIBLE");
		}
		int indexAidBox = getIndexAidBox(after);
		if (totalAidBoxes >= aidBoxes.length) {
			aidBoxes = Arrays.copyOf(aidBoxes, 2 * aidBoxes.length);
		}
		shiftAidBoxesRight(indexAidBox);
		aidBoxes[indexAidBox + 1] = toInsert;
		totalAidBoxes++;
		updateRouteMetrics();
	}

	/**
	 * Get the routes aid boxes.
	 * @return AidBox[] Array of aid boxes present on the route.
	 */
	@Override
	public AidBox[] getRoute() {
		AidBox[] copyAidBoxes = new AidBox[totalAidBoxes];
		for (int i = 0; i < totalAidBoxes; i++) {
            try {
                copyAidBoxes[i] = new AidBoxImpl((AidBoxImpl) aidBoxes[i]);
            } catch (ContainerException e) {
                throw new RuntimeException(e);
            }
        }
		return copyAidBoxes;
	}

	/**
	 * Get the vehicle of the route.
	 * @return Vehicle The vehicle of the route.
	 */
	@Override
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * Get the total distance
	 * @return Double The total distance of the route.
	 */
	@Override
	public double getTotalDistance() {
		return totalDistance;
	}

	/**
	 * Get the total duration.
	 * @return Double The total duration of the route.
	 */
	@Override
	public double getTotalDuration() {
		return totalDuration;
	}

	/**
	 * Get the report of the route.
	 * @return Report The report of the route.
	 */
	@Override
	public Report getReport() {
		return report;
	}

	/**
	 * Set the report for the route.
	 *
	 * @param report Report to be set.
	 */
	public void setReport(Report report) {
		this.report = report;
	}

	/**
	 * Get the index of and aid box on the route.
	 * @param aidBox The aid box to get the index of.
	 * @return Int The index of the aid box on the route.
	 * 			-1, if the aid box does not exist on the route.
	 */
	private int getIndexAidBox(AidBox aidBox) {
		for (int i = 0; i < totalAidBoxes; i++) {
			if (aidBoxes[i].equals(aidBox)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Shift the aid boxes to the left.
	 * @param index The index of the aid box to start shifting from.
	 */
	private void shiftAidBoxes(int index) {
		for (int i = index; i < totalAidBoxes - 1; i++) {
			aidBoxes[i] = aidBoxes[i + 1];
		}
		aidBoxes[totalAidBoxes - 1] = null;
	}

	/**
	 * Shift the aid boxes to the right.
	 * @param index The index of the aid box to start shifting from.
	 */
	private void shiftAidBoxesRight(int index) {
		for (int i = totalAidBoxes; i > index + 1; i--) {
			aidBoxes[i] = aidBoxes[i - 1];
		}
	}

	/**
	 * Check if the aid box is compatible with the route.
	 * @param aidBox The aid box to check.
	 * @return Boolean True if the aid box is compatible with the route.
	 * 					False if the aid box is not compatible with the route.
	 */
	private boolean compatibleAidBox(AidBox aidBox) {
		VehicleImpl vehicle = (VehicleImpl) getVehicle();
		Container[] containers = aidBox.getContainers();
		for (Container container : containers) {
			if (vehicle.hasContainerType(container.getType())) {
				if (vehicle.hasContainerTypeRoom(container.getType())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Update the distance and duration os the aid boxes.
	 */
	private void updateRouteMetrics() {
		totalDistance = 0;
		totalDuration = 0;
		for (int i = 0; i < totalAidBoxes - 1; i++) {
			try {
				double distance = aidBoxes[i].getDistance(aidBoxes[i + 1]);
				totalDistance += distance;
				totalDuration += distance / 50.0;
			} catch (Exception e) {
				System.out.println("Error calculating distance between aidboxes");
			}
		}
	}

	/**
	 * String representation of the route.
	 * @return String representation of the route.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Aid boxes:\n").append(this.aidBoxToString()).append("\n");
		sb.append("Route for Vehicle ").append(this.vehicle.getCode()).append(":\n");
		sb.append("Total Distance: ").append(this.totalDistance).append(" meters\n");
		sb.append("Total Duration: ").append(this.totalDuration).append(" minutes\n");
		return sb.toString();
	}

	private String aidBoxToString() {
		StringBuilder sb = new StringBuilder();

		for (AidBox box : this.aidBoxes) {
			sb.append(box.getCode()).append("\n");
		}

		return sb.toString();
	}
}
