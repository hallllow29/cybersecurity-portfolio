package pp_tp_er.pickingManagement;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Institution;
import com.estg.core.exceptions.PickingMapException;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.Vehicle;
import pp_tp_er.core.Capacity;
import pp_tp_er.core.AidBoxImpl;
import pp_tp_er.core.InstitutionImpl;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * The type Standard route strategy.
 */
public class StandardRouteStrategy extends AbstractRouteStrategy {

    /**
     * Generates the routes for an institution, collecting containers based on the requirements (perishable food or 80% of capacity)
     * and replacing them with empty containers.
     * @param institution The institution to generate the routes.
     * @return Route[] Array with all the routes generated.
     */
    @Override
    public Route[] generateRoutes(Institution institution) {
        //Get all aid boxes vehicles and empty containers the institution have.
        AidBox[] aidBoxes = ((InstitutionImpl) institution).getAidBoxes();
        Vehicle[] vehicles = institution.getVehicles();
        Container[] emptyContainers = ((InstitutionImpl) institution).getEmptyContainers();

        Route[] routes = new Route[vehicles.length];
        int routeCounter = 0;

        int totalUsedVehicles = 0;
        double totalDistance = 0;
        double totalDuration = 0;

        //A picking map array that will store all picking maps generated for each vehicle.
        PickingMap[] pickingMaps = new PickingMap[vehicles.length];
        int pickingMapCounter = 0;

        //Containers with priority will go first on the collection (perishable food or 80% of capacity filled).
        Container[] priorityContainers = new Container[1000];
        //The rest of the containers.
        Container[] nonPriorityContainers = new Container[1000];
        int priorityIndex = 0;
        int nonPriorityIndex = 0;

        //For each aid box will retrieve all the containers the aid box have.
        for (AidBox aidBox : aidBoxes) {
            for (Container container : aidBox.getContainers()) {
                //Check if the container is already collected and need to be collected.
                if (!this.isContainerCollected(container) && this.needsCollection(container)) {
                    //If true it will store the container on the priority containers array.
                    priorityContainers[priorityIndex++] = container;
                } else if (!this.isContainerCollected(container)) {
                    //Otherwise it will store the container on the non priority containers array.
                    nonPriorityContainers[nonPriorityIndex++] = container;
                }
            }
        }

        //Concatenate all containers to one array, making the priority containers the first ones.
        Container[] allContainers = new Container[priorityIndex + nonPriorityIndex];
        System.arraycopy(priorityContainers, 0, allContainers, 0, priorityIndex);
        System.arraycopy(nonPriorityContainers, 0, allContainers, priorityIndex, nonPriorityIndex);

        //For each vehicle will generate the routes.
        for (Vehicle vehicle : vehicles) {
            //An array with all capacities of the current vehicle.
            Capacity[] capacities = ((VehicleImpl) vehicle).getCapacities();
            //Create an array of aid boxes so the vehicle can know what aid boxes he needs to go.
            AidBox[] aidBoxesInRoute = new AidBoxImpl[aidBoxes.length];
            int aidBoxIndex = 0;

            boolean canVehicleLeaveBase = false;

            for (Container container : allContainers) {
                if (container == null) continue;
                //It will find the aid box for the container.
                AidBox aidBox = this.findAidBoxForContainer(aidBoxes, container);
                //Will check if the aid box is already collected.
                if (aidBox == null || this.isAidBoxCollected(aidBoxesInRoute, aidBox)) {
                    continue;
                }
                //Get the type of the container.
                ContainerType type = container.getType();
                //Get the vehicle capacity based on the type of the container.
                Capacity vehicleCapacity = this.getVehicleCapacityForType(capacities, type);

                //If the vehicle capacity is not null and have space to carry the container.
                if (vehicleCapacity != null && vehicleCapacity.getQuantity() > 0) {
                    boolean emptyContainerFound = false;
                    //Iterate for all empty containers to check if the empty container is equals to the container type, and then it will check if the empty container is used.
                    for (int i = 0; i < emptyContainers.length; i++) {
                        if (emptyContainers[i] != null && emptyContainers[i].getType().equals(type) && !this.isContainerUsed(emptyContainers[i])) {
                            //Add the empty container to the arrays of used empty containers.
                            this.addUsedEmptyContainer(emptyContainers[i]);
                            emptyContainerFound = true;
                            //Print the log saying the vehicle will carry an empty container.
                            System.out.println("* Vehicle " + vehicle.getCode() + " has used empty container " + emptyContainers[i].getCode());
                            break;
                        }
                    }

                    if (emptyContainerFound) {
                        canVehicleLeaveBase = true;
                        //Decrements the capacity of the vehicle based on the type of the container.
                        vehicleCapacity.setQuantity(vehicleCapacity.getQuantity() - 1);
                        //Add the container to the array of collected containers.
                        this.addCollectedContainer(container);
                        //Print the log saying the vehicle has collected a container.
                        System.out.println("* Vehicle " + vehicle.getCode() + " has collected container " + container.getCode() + (emptyContainerFound ? "" : " without empty replacement"));

                        //If the aid box is on in the routes of the aid box routes for the vehicle, it will add the aid box to the route.
                        if (!this.isAidBoxCollected(aidBoxesInRoute, aidBox)) {
                            aidBoxesInRoute[aidBoxIndex++] = aidBox;
                        }

                        //Checks if the vehicle capacity is full, if not continue to the next container until its capacity is full then go to the next vehicle.
                        if (isVehicleFull(capacities)) {
                            break;
                        }
                    }
                }
            }

            if (canVehicleLeaveBase && aidBoxIndex > 0) {
                Route route = new RouteImpl(vehicle, Arrays.copyOf(aidBoxesInRoute, aidBoxIndex), calculateTotalDuration(aidBoxesInRoute), this.calculateTotalDistance(aidBoxesInRoute), null);
                routes[routeCounter++] = route;
                totalUsedVehicles++;
                totalDistance += route.getTotalDistance();
                totalDuration += route.getTotalDuration();

                PickingMap pickingMap = new PickingMapImpl(LocalDateTime.now(), new Route[]{route});
                pickingMaps[pickingMapCounter++] = pickingMap;

                try {
                    institution.addPickingMap(pickingMap);
                } catch (PickingMapException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //The total containers all aid boxes have at the moment.
        int totalContainers = priorityIndex + nonPriorityIndex;
        int totalNonPickedContainers = totalContainers - this.getTotalContainersCollected();

        //Generate a report with the statistics.
        Report report = new ReportImpl(LocalDateTime.now(), totalUsedVehicles, this.getTotalContainersCollected(), totalDistance, totalDuration, totalNonPickedContainers, vehicles.length - totalUsedVehicles);

        for (Route route : routes) {
            if (route != null) {
                ((RouteImpl) route).setReport(report);
            }
        }

        return Arrays.copyOf(routes, routeCounter);
    }
}