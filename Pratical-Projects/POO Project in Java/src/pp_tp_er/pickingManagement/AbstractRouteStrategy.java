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
package pp_tp_er.pickingManagement;

import com.estg.core.*;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.Route;
import pp_tp_er.core.Capacity;
import pp_tp_er.core.AidBoxImpl;
import pp_tp_er.interfaces.RouteStrategy;

import java.util.Arrays;

/**
 * The abstract class  
 * {@link pp_tp_er.pickingManagement.AbstractRouteStrategy AbstractRouteStrategy}
 * 
 *
 * @author 8230068
 * @author 8230069
 * @file AbstractRouteStrategy.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @see com.estg.pickingManagement.RouteGenerator
 * @since Java SDK 13
 */
public abstract class AbstractRouteStrategy implements RouteStrategy {
    private int totalContainersCollected = 0;
    //Array with all containers that have been collected.
    private Container[] collectedContainers = new Container[1000];
    private int usedEmptyContainersCount = 0;
    //Array with all empty containers that have been used.
    private Container[] usedEmptyContainers = new Container[1000];

    /**
     * Create a new route.
     * @param institution the given {@link pp_tp_er.core.InstitutionImpl Institution}
     *
     * @return The new route.
     */
    public abstract Route[] generateRoutes(Institution institution);
	/**
	 * Checks if a specified {@link pp_tp_er.core.ContainerImpl Container} has been collected already.
	 *
	 * @param container The {@link pp_tp_er.core.ContainerImpl Container} to check.
	 *
	 * @return <ul>
	 *     <li>{@code True}, if the {@link pp_tp_er.core.ContainerImpl Container} has been collected already. </li>
	 *     <li>{@code False}, if the {@link pp_tp_er.core.ContainerImpl Container} has not been collected already. </li>
	 * </ul>
	 */
	protected boolean isContainerCollected(Container container) {
        for (int i = 0; i < this.totalContainersCollected; i++) {
            //It will go the array of the collected containers to check if the container is already there.
            if (this.collectedContainers[i] != null && this.collectedContainers[i].equals(container)) {
                return true;
            }
        }
        return false;
    }

	/**
	 * Checks if an empty {@link pp_tp_er.core.ContainerImpl Container} is already used to substitute for a loaded {@link pp_tp_er.core.ContainerImpl Container}.
	 *
	 * @param container The {@link pp_tp_er.core.ContainerImpl Container} to check.
	 *
	 * @return <ul> 
	 * <li>{@code True}, if the {@link pp_tp_er.core.ContainerImpl Container} is already used.</li> 
	 * <li>{@code False}, if the {@link pp_tp_er.core.ContainerImpl Container} is not used.</li>
	 * </ul>
	 */
	protected boolean isContainerUsed(Container container) {
        for (int i = 0; i < this.usedEmptyContainersCount; i++) {
            //It will go the array of the used empty containers to check if the container is already there.
            if (this.usedEmptyContainers[i] != null && this.usedEmptyContainers[i].equals(container)) {
                return true;
            }
        }
        return false;
    }

	/**
	 * Get the last measurement inserted on the measurements array of a specified {@link pp_tp_er.core.ContainerImpl Container}.
	 *
	 * @param container The {@link pp_tp_er.core.ContainerImpl Container} to get the last measurement.
	 *
	 * @return <ul>
	 * <li>The last measurement of the {@link pp_tp_er.core.ContainerImpl Container} if found.</li>
	 * <li>Null If the {@link pp_tp_er.core.ContainerImpl Container} has no measurements.</li>
	 * </ul>
	 */
	protected Measurement getLastMeasurement(Container container) {
        //Initializes an array of with all the measurements of the array
        Measurement[] measurements = container.getMeasurements();
        if (measurements.length > 0) {
            //Go the array and return the last measurement.
            return measurements[measurements.length - 1];
        }
        //If there are no measurements, return null.
        return null;
    }

	/**
	 * Checks if the {@link pp_tp_er.core.ContainerImpl Container} to be collected needs to be collected, based on the type (Perishable food or if the {@link pp_tp_er.core.ContainerImpl Container}
	 * is with 80% of its {@link pp_tp_er.core.Capacity Capacity} filled).
	 *
	 * @param container The {@link pp_tp_er.core.ContainerImpl Container} to check if it needs to be collected.
	 *
	 * @return <ul>
	 * <li>True, if the {@link pp_tp_er.core.ContainerImpl Container} need to be collected. </li>
	 *  <li>False, if the {@link pp_tp_er.core.ContainerImpl Container} don't meet the requirements to be collected.</li>
	 * </ul>
	 */
	protected boolean needsCollection(Container container) {
        //Get the type of the container.
        ContainerType type = container.getType();
        //Get the last measurement inserted for this container.
        Measurement lastMeasurement = this.getLastMeasurement(container);

        //Check's if the type of the container is Perishable food or if the container is with 80% of its capacity filled.
        if (type.toString().equals("perishable food")) {
            return true;
        } else if (lastMeasurement != null) {
            double filledPercentage = (lastMeasurement.getValue() / container.getCapacity()) * 100;
            return filledPercentage > 80;
        }
        return false;
    }

	/**
	 * Finds the aid box that contains the specified {@link pp_tp_er.core.ContainerImpl Container}.
	 *
	 * @param aidBoxes  The array with all aid boxes.
	 * @param container The {@link pp_tp_er.core.ContainerImpl Container} to find the aid box for.
	 *
	 * @return AidBox The aid box that contains the specified {@link pp_tp_er.core.ContainerImpl Container}. Null If the aid box was not found.
	 */
	protected AidBox findAidBoxForContainer(AidBox[] aidBoxes, Container container) {
        for (AidBox aidBox : aidBoxes) {
            //Get the containers of the aid box.
            for (Container c : aidBox.getContainers()) {
                //Compare if it is equals or not.
                if (c.equals(container)) {
                    return aidBox;
                }
            }
        }
        return null;
    }

	/**
	 * Checks if the specified AidBox has already been collected in the given array of aid boxes.
	 *
	 * @param aidBoxesInRoute Array of aid boxes in the current route.
	 * @param aidBox          The aid box to check.
	 *
	 * @return True, if the aid box already is in the array of aid boxes in the route. False, if the aid box is not in the array of aid boxes in the route.
	 */
	protected boolean isAidBoxCollected(AidBox[] aidBoxesInRoute, AidBox aidBox) {
        for (AidBox box : aidBoxesInRoute) {
            if (box != null && box.equals(aidBox)) {
                return true;
            }
        }
        return false;
    }

	/**
	 * Get the vehicle capacity based on the type.
	 *
	 * @param capacities The array of capacities of the vehicle.
	 * @param type       The type to check if he as a capacity for.
	 *
	 * @return Capacity The capacity of the vehicle for the specified type. Null If the capacity was not found.
	 */
	protected Capacity getVehicleCapacityForType(Capacity[] capacities, ContainerType type) {
        for (Capacity capacity : capacities) {
            if (capacity.getContainerType().equals(type)) {
                return capacity;
            }
        }
        return null;
    }

	/**
	 * Checks if the vehicle is full based on the capacities he had.
	 *
	 * @param capacities Array of the current capacities of the vehicle.
	 *
	 * @return True, if the vehicle is already full (All capacities are on 0). False, if the vehicle can still carry.
	 */
	protected boolean isVehicleFull(Capacity[] capacities) {
        for (Capacity capacity : capacities) {
            if (capacity.getQuantity() > 0) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Calculates the total distance covered by the given array of the aid boxes, for the last aid box the vehicle need's
	 * to return to the base so will calculate the distance to the base.
	 *
	 * @param aidBoxes The array of the aid boxes representing the route.
	 *
	 * @return double The total distance covered by the given array of the aid boxes.
	 */
	protected double calculateTotalDistance(AidBox[] aidBoxes) {
        AidBoxImpl lastAidBox = getLastAidBox(aidBoxes);
        double totalDistance = 0;

        //Iterate for each aid box and calculate the distance from the current aid box to the next one on the list.
        for (int i = 0; i < aidBoxes.length - 1; i++) {
            try {
                if (aidBoxes[i] != null && aidBoxes[i + 1] != null) {
                    totalDistance += aidBoxes[i].getDistance(aidBoxes[i + 1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            //Get the last aid box of the list and calculate the distance to the base so the vehicle can return to the base.
            if (lastAidBox != null) {
                totalDistance += lastAidBox.getDistanceToBase();
            }
        } catch (AidBoxException e) {
            e.printStackTrace();
        }

        return totalDistance;
    }

	/**
	 * Calculates the total duration of the given array of the aid boxes, for the last aid box the vehicle need's
	 *
	 * @param aidBoxes The array of the aid boxes representing the route.
	 *
	 * @return double The total duration of the given array of the aid boxes.
	 */
	protected double calculateTotalDuration(AidBox[] aidBoxes) {
        double totalDuration = 0;
        AidBoxImpl lastAidBox = getLastAidBox(aidBoxes);

        //Iterates over all aid boxes on the route and calculates the duration between the current aid box and the next one on the list.
        for (int i = 0; i < aidBoxes.length - 1; i++) {
            try {
                if (aidBoxes[i] != null && aidBoxes[i + 1] != null) {
                    totalDuration += aidBoxes[i].getDuration(aidBoxes[i + 1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            //If it is the last aid box get the last aid box and calculate the duration to the base so the vehicle can return to the base.
            if (lastAidBox != null) {
                totalDuration += lastAidBox.getDurationToBase();
            }
        } catch (AidBoxException e) {
            e.printStackTrace();
        }

        return totalDuration;
    }

	/**
	 * Get the last aid box on the route.
	 *
	 * @param aidBoxesInRoute Array of aid boxes representing the route.
	 *
	 * @return AidBoxImpl The last aid box on the route.
	 */
	protected AidBoxImpl getLastAidBox(AidBox[] aidBoxesInRoute) {
        for (int i = aidBoxesInRoute.length - 1; i >= 0; i--) {
            if (aidBoxesInRoute[i] != null) {
                return (AidBoxImpl) aidBoxesInRoute[i];
            }
        }
        return null;
    }

	/**
	 * Add collected {@link pp_tp_er.core.ContainerImpl Container}.
	 *
	 * @param container the {@link pp_tp_er.core.ContainerImpl Container}
	 */
	protected void addCollectedContainer(Container container) {
        if (totalContainersCollected >= collectedContainers.length) {
            collectedContainers = Arrays.copyOf(collectedContainers, collectedContainers.length * 2);
        }
        collectedContainers[totalContainersCollected++] = container;
    }

	/**
	 * Add used empty {@link pp_tp_er.core.ContainerImpl Container}.
	 *
	 * @param container the {@link pp_tp_er.core.ContainerImpl Container}
	 */
	protected void addUsedEmptyContainer(Container container) {
        if (usedEmptyContainersCount >= usedEmptyContainers.length) {
            usedEmptyContainers = Arrays.copyOf(usedEmptyContainers, usedEmptyContainers.length * 2);
        }
        usedEmptyContainers[usedEmptyContainersCount++] = container;
    }

	/**
	 * Gets total {@link pp_tp_er.core.ContainerImpl Container}s collected.
	 *
	 * @return the total {@link pp_tp_er.core.ContainerImpl Container}s collected
	 */
	protected int getTotalContainersCollected() {
        return totalContainersCollected;
    }

    /**
     * Generates the {@link pp_tp_er.pickingManagement.RouteImpl Route} for an institution, collecting {@link pp_tp_er.core.ContainerImpl Container} based on the requirements (ONLY PERISHABLE FOOD), we implement
     * this route like an emergency route so the vehicles will only collect {@link pp_tp_er.core.ContainerImpl Container}sthat are perishable and replace them with empty ones.
     *
     * @param institution The institution to generate the {@link pp_tp_er.pickingManagement.RouteImpl Route}.
     * @return Route[] Array with all the {@link pp_tp_er.pickingManagement.RouteImpl Route} generated.
     */

}

