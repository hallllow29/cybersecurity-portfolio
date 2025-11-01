/*
 * Nome: Ruben Xavier Ferreira Nunes
 * Número: 8230069
 * Turma: LSIRCT2
 *
 * Nome: Pedro Daniel Gonçalves Antunes
 * Número: 8230068
 * Turma: LSIRCT2
 */
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
 * The class that extends the abstract class
 * {@link pp_tp_er.pickingManagement.AbstractRouteStrategy AbstractRouteStrategy}
 *
 *
 * @author 8230068
 * @author 8230069
 * @file PerishableRouteStrategy.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @see com.estg.pickingManagement.RouteGenerator
 * @since Java SDK 13
 */
public class PerishableRouteStrategy extends AbstractRouteStrategy {

    /**
     * Generates the {@link pp_tp_er.pickingManagement.RouteImpl Route} for 
     * an {@link pp_tp_er.core.InstitutionImpl Institution}, collecting 
     * {@link com.estg.core.Container Container} based on the requirements {@link pp_tp_er.core.ContainerTypeImpl (ONLY PERISHABLE FOOD)}, we implement
     * this {@link pp_tp_er.pickingManagement.RouteImpl Route} like an emergency {@link pp_tp_er.pickingManagement.RouteImpl Route} so the {@link com.estg.pickingManagement.Vehicle Vehicle} will only collect {@link com.estg.core.Container Container} 
     * that are {@link com.estg.core.ContainerType PERISHABLE} and replace them with empty {@link com.estg.core.Container Container}.
     *
     * @param institution The {@link pp_tp_er.core.InstitutionImpl Institution} to generate the {@link pp_tp_er.pickingManagement.RouteImpl Route}.
     * @return Collection with all the {@link pp_tp_er.pickingManagement.RouteImpl Route} generated.
     */
    @Override
    public Route[] generateRoutes(Institution institution) {
        //Get all aid boxes of the institution.
        AidBox[] aidBoxes = institution.getAidBoxes();
        //Get all vehicles of the institution.
        Vehicle[] vehicles = institution.getVehicles();
        //Get all empty containers of the institution.
        Container[] emptyContainers = ((InstitutionImpl) institution).getEmptyContainers();
        //Use the method "getAllPerishableContainers" to get all empty perishable containers of the institution.
        Container[] allEmptyPerishableContainers = this.getAllPerishableContainers(emptyContainers);
        //Create a routes array for each vehicl.
        Route[] routes = new Route[vehicles.length];
        int routeCounter = 0;

        int totalUsedVehicles = 0;
        double totalDistance = 0;
        double totalDuration = 0;

        boolean isVehicleOnDuty = false;

        PickingMap[] pickingMaps = new PickingMap[vehicles.length];
        int pickingMapCounter = 0;

        //Iterate for each vehicle to create a route.
        for (Vehicle vehicle : vehicles) {
            if (!((VehicleImpl) vehicle).getOnDuty()) {
                System.out.println("Vehicle " + vehicle.getCode() + " is not on duty.");
                continue;
            }
            //Get an array of capacities for each vehicle
            Capacity[] capacities = ((VehicleImpl) vehicle).getCapacities();
            //Create an array of aid boxes that will be used for the route.
            AidBox[] aidBoxesInRoute = new AidBoxImpl[aidBoxes.length];
            int aidBoxIndex = 0;

            //Create an array with all containers.
            Container[] allContainers = new Container[1000];
            int containersCounter = 0;

            for (AidBox box : aidBoxes) {
                for (Container container : box.getContainers()) {
                    allContainers[containersCounter++] = container;
                }
            }

            //With the array of all containers, get the ones that are perishable.
            Container[] perishableContainers = this.getAllPerishableContainers(allContainers);
            boolean canVehicleLeaveBase = false;

            //For each perishable container check if the container is not collected yet.
            for (Container container : perishableContainers) {
                if (container == null || this.isContainerCollected(container)) continue;

                AidBox aidBox = this.findAidBoxForContainer(aidBoxes, container);
                if (aidBox == null || this.isAidBoxCollected(aidBoxesInRoute, aidBox)) {
                    continue;
                }

                //Get the container type, is more secure we know that the container type is perishable, but it's safe got make sure.
                ContainerType type = container.getType();
                //Check if the vehicle is able to carry the container, based on the type of container (we know is perishable, but we need to make sure just in case).
                Capacity vehicleCapacity = this.getVehicleCapacityForType(capacities, type);

                //If the vehicle capacity is not nut and the quantity to collect is greater than 0 the vehicle can collect the container.
                if (vehicleCapacity != null && vehicleCapacity.getQuantity() > 0) {
                    boolean emptyContainerFound = false;
                    //Iterates over the empty perishable containers to find one that is empty and not used yet.
                    for (int i = 0; i < allEmptyPerishableContainers.length; i++) {
                        if (allEmptyPerishableContainers[i] != null && allEmptyPerishableContainers[i].getType().equals(type) && !this.isContainerUsed(allEmptyPerishableContainers[i])) {
                            //If found add it to the array of used empty containers (the array of the class, because we can have simultaneously routes, and if one route already collected that container
                            // every route know that container was already collected).
                            this.addUsedEmptyContainer(allEmptyPerishableContainers[i]);
                            emptyContainerFound = true;
                            //Print the log saying the vehicle is carrying an empty container.
                            System.out.println("* Vehicle " + vehicle.getCode() + " has used empty container " + allEmptyPerishableContainers[i].getCode());
                            break;
                        }
                    }

                    canVehicleLeaveBase = true;
                    //Set the quantity of the vehicle capacity to -1.
                    vehicleCapacity.setQuantity(vehicleCapacity.getQuantity() - 1);
                    //Add the collected container to the array of collected containers.
                    this.addCollectedContainer(container);
                    //Print the log saying the vehicle has collected a container.
                    System.out.println("* Vehicle " + vehicle.getCode() + " has collected container " + container.getCode() + (emptyContainerFound ? "" : " without empty replacement"));

                    //Check if the aid box that have that container is already on the route, if not add the aid box to the aidBoxesInRoute so on the picking map he knows what aid boxes he must go.
                    if (!this.isAidBoxCollected(aidBoxesInRoute, aidBox)) {
                        aidBoxesInRoute[aidBoxIndex++] = aidBox;
                    }
                    //Check if the vehicle is full, if not continue with that vehicle until all capacities are decremented to 0.
                    if (isVehicleFull(capacities)) {
                        break;
                    }
                }
            }
            //So the vehicle can leave the base because he has containers to collect, and he has empty containers to replace.
            if (canVehicleLeaveBase && aidBoxIndex > 0) {
                //Create a route for the vehicle, and add it to the array of routes.
                Route route = new RouteImpl(vehicle, Arrays.copyOf(aidBoxesInRoute, aidBoxIndex), this.calculateTotalDuration(aidBoxesInRoute), this.calculateTotalDistance(aidBoxesInRoute), null);
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

        int totalNonPickedContainers = this.getAllContainers(institution).length - this.getTotalContainersCollected();
        Report report = new ReportImpl(LocalDateTime.now(), totalUsedVehicles, this.getTotalContainersCollected(), totalDistance, totalDuration, totalNonPickedContainers, vehicles.length - totalUsedVehicles);

        for (Route route : routes) {
            if (route != null) {
                ((RouteImpl) route).setReport(report);
            }
        }

        return Arrays.copyOf(routes, routeCounter);
    }

    /**
     * Get all {@link com.estg.core.Container Container} all {@link com.estg.core.AidBox AidBox}
     * have on the {@link pp_tp_er.core.InstitutionImpl Institution}.
     *
     * @param institution The {@link pp_tp_er.core.InstitutionImpl Institution} to get the {@link com.estg.core.Container Container} from.
     * @return A collection of {@link com.estg.core.Container Container} on the {@link pp_tp_er.core.InstitutionImpl Institution}
     */
    private Container[] getAllContainers(Institution institution) {
        AidBox[] aidBoxes = institution.getAidBoxes();
        Container[] allContainers = new Container[1000];
        int containersCounter = 0;

        for (AidBox box : aidBoxes) {
            for (Container container : box.getContainers()) {
                allContainers[containersCounter++] = container;
            }
        }

        return Arrays.copyOf(allContainers, containersCounter);
    }

    /**
     * Checks if the {@link com.estg.core.Container Container} is {@link com.estg.core.ContainerType PERISHABLE}.
     *
     * @param container The {@link com.estg.core.Container Container} to check.
     * @return {@code True}, if the {@link com.estg.core.Container Container} is {@link com.estg.core.ContainerType PERISHABLE}.
     * <ul>
     *     <li>{@code True}, if the {@link com.estg.core.Container Container} is {@link com.estg.core.ContainerType PERISHABLE}.</li>
     *     <li>{@code False}, if the {@link com.estg.core.Container Container} is not {@link com.estg.core.ContainerType PERISHABLE}.</li>
     * </ul>
     * {@code False}, if the {@link com.estg.core.Container Container} is not {@link com.estg.core.ContainerType PERISHABLE}.
     */
    private boolean isContainerPerishable(Container container) {
        ContainerType type = container.getType();

        return type.toString().equals("perishable food");
    }

    /**
     * Get all {@link com.estg.core.Container Container} that are perishable.
     *
     * @param containers All {@link com.estg.core.Container Container} of an {@link pp_tp_er.core.InstitutionImpl Institution}.
     * @return Container[] All {@link com.estg.core.ContainerType PERISHABLE} {@link com.estg.core.Container Container}.
     */

    private Container[] getAllPerishableContainers(Container[] containers) {
        int counter = 0;
        Container[] perishableContainers = new Container[containers.length];
        for (Container container : containers) {
            if (container != null) {
                if (isContainerPerishable(container)) {
                    perishableContainers[counter++] = container;
                }
            }

        }

        return Arrays.copyOf(perishableContainers, counter);
    }

}

