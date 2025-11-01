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
package pp_tp_er.core;

import com.estg.core.*;
import com.estg.core.exceptions.*;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;
import org.json.simple.parser.ParseException;
import pp_tp_er.io.ImporterImpl;
import pp_tp_er.pickingManagement.PickingMapImpl;
import pp_tp_er.pickingManagement.VehicleImpl;
import util.JsonUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.io.File;

/**
 * An implementation class that implements the provided {@link com.estg.core.Institution Institution}
 * interface.
 * The class responsible for the {@link pp_tp_er.core.InstitutionImpl Institution}.
 *
 * @author 8230068, 8230069
 * @file InstitutionImpl.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @see com.estg.core.Institution
 * @since Java SDK 13
 */
public class InstitutionImpl implements Institution {

    /**
     * Name of the JSON file.
     */
    private static final String JSON_FILE = "institution.json";
    /**
     * {@code Initial size} of arrays.
     */
    private static final int INITI_SIZE = 10;

    /**
     * {@code Expand factor} to expand arrays size.
     */
    private static final int EXPAND_FACTOR = 2;

    /**
     * {@code Amount} of empty containers in a {@link #InstitutionImpl(String) Institution}.
     */
    private int totalEmptyContainers;

    /**
     * {@code Amount} of aidBoxes in a {@link #InstitutionImpl(String) Institution}.
     */
    private int totalAidBoxes;

    /**
     * {@code Amount} of vehicles in a {@link #InstitutionImpl(String) Institution}.
     */
    private int totalVehicles;

    /**
     * {@code Amount} of picking maps in a {@link #InstitutionImpl(String) Institution}.
     */
    private int totalPickingMaps;

    /**
     * {@code Amount} of defective container in a {@link #InstitutionImpl(String) Institution}.
     */
    private int totalDefectiveContainers;

    /**
     * {@code Name} of an {@link #InstitutionImpl(String) Institution}.
     */
    private String name;

    /**
     * {@code Collection} of  AidBox in a {@link #InstitutionImpl(String) Institution}.
     */
    private AidBox[] aidBoxes;

    /**
     * {@code Collection} of Vehicle in a {@link #InstitutionImpl(String) Institution}.
     */
    private Vehicle[] vehicles;

    /**
     * {@code Collection} of PickingMap in a {@link #InstitutionImpl(String) Institution}.
     */
    private PickingMap[] pickingMaps;

    /**
     * {@code Collection} of empty Container in a {@link #InstitutionImpl(String) Institution}.
     */
    private Container[] emptyContainers;

    /**
     * {@code Collection} of defective Container in a {@link #InstitutionImpl(String) Institution}.
     */
    private Container[] defectiveContainers;

    /**
     * Constructs an Institution with given name
     *
     * @param name The given name.
     *
     * @throws InstitutionException the institution exception
     * @throws IOException          the io exception
     */
    public InstitutionImpl(String name) throws InstitutionException, IOException {
        this.name = name;
        this.aidBoxes = new AidBoxImpl[INITI_SIZE];
        this.vehicles = new VehicleImpl[INITI_SIZE];
        this.pickingMaps = new PickingMapImpl[INITI_SIZE];
        this.emptyContainers = new ContainerImpl[INITI_SIZE];
        this.defectiveContainers = new ContainerImpl[INITI_SIZE];
        this.totalEmptyContainers = 0;
        this.totalAidBoxes = 0;
        this.totalVehicles = 0;
        this.totalPickingMaps = 0;

        //When create an institution, the first time, the data is loaded from the API otherwise it will load from a file.
        this.loadAllData();

    }

    /**
     * Gets total aid boxes.
     *
     * @return the total aid boxes
     */
    public int getTotalAidBoxes() {
        return this.totalAidBoxes;
    }

    /**
     * Getter for the amount of vehicles of a {@link #InstitutionImpl(String) Institution}.
     *
     * @return The amount of vehicles of a {@link #InstitutionImpl(String) Institution}.
     */
    public int getTotalVehicles() {
        return this.totalVehicles;
    }

    /**
     * Getter for the name of an {@link #InstitutionImpl(String) Institution}.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Adds a given AidBox to the AidBox {@code collection} of an {@link #InstitutionImpl(String) Institution}.
     *
     * @param aidBox The given AidBox
     * @return <ol>
     * <li>True, if the given AidBox was added to AidBox {@code collection}. </li>
     * <li>False, if the given AidBox is equal with an existing AidBox.</li>
     * </ol>
     * @throws AidBoxException <ol>
     *                         <li>If the given AidBox is null.</li>
     *                         </ol>
     */
    @Override
    public boolean addAidBox(AidBox aidBox) throws AidBoxException {
        this.validateAidBox(aidBox);

        if (this.containsAidBox(aidBox)) {
            return false;
        }

        if (this.totalAidBoxes >= this.aidBoxes.length) {
            this.expandAidBoxArray();
        }

        this.aidBoxes[this.totalAidBoxes++] = aidBox;
        return true;
    }

    /**
     * Getter for the deep copy of AidBox {@code collection} of a {@link #InstitutionImpl(String) Institution}.
     *
     * @return The deep copy of AidBox collection of a {@link #InstitutionImpl(String) Institution}.
     */
    @Override
    public AidBox[] getAidBoxes() {
        try {
            return this.copyAidBoxes();
        } catch (ContainerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a given Measurement to the {@link #InstitutionImpl(String) Institution} (considering the Aid Box and a given Container.
     *
     * @param measurement The given Measurement.
     * @param container   The given Container.
     * @return <ol>
     * <li>True, if the given Measurement was added to {@code collection} of Measurement.
     * <li>False, if the date of given Measurement is equal with the date of an existing Measurement. (Each Container has only one Measurement for a given date)</li>
     * </ol>
     * @throws ContainerException   <ol>
     *                              <li>If the given Container is null.</li>
     *                              <li>If the given Container does not exist.</li>
     *                              </ol>
     * @throws MeasurementException <ol>
     *                              <li>If the given Measurement is null.</li>
     *                              <li>If the value of given Measurement is less than zero.</li>
     *                              <li>If the value of given Measurement have higher value then Container capacity</li>
     *                              </ol>
     */
    @Override
    public boolean addMeasurement(Measurement measurement, Container container) throws ContainerException, MeasurementException {
        this.validateContainer(container);
        this.validateMeasurement(measurement, container);

        if (!this.containsContainer(container)) {
            throw new ContainerException("GIVEN CONTAINER DOES NOT EXIST");
        }

        if (!this.isMeasurementAlreadyExistForGivenDate(measurement, container)) {
            container.addMeasurement(measurement);
            return true;
        }

        return false;
    }


    /**
     * Return a copy of an existing container from an Aid Boxes and container type
     *
     * @param aidBox        aidBox - Aid Box for getting measurements
     * @param containerType containerType - item Type for getting measurements
     * @return a copy of an existing containers
     * @throws ContainerException ContainerException - if the Aid Box doesn't exist, or if a container with the given container type doesn't exist
     */
    @Override
    public Container getContainer(AidBox aidBox, ContainerType containerType) throws ContainerException {
        try {
            this.validateAidBox(aidBox);
        } catch (AidBoxException e) {
            throw new RuntimeException(e);
        }

        this.validateContainerType(containerType);

        int indexAidBox = this.getIndexAidBox(aidBox);

        if (indexAidBox == -1) {
            throw new ContainerException("GIVEN AIDBOX DOES NOT EXIST");
        }

        Container container = this.aidBoxes[indexAidBox].getContainer(containerType);

        if (container == null) {
            throw new ContainerException("Aid box with code " + aidBox.getCode() + " does not have a container type with type " + containerType);
        }

        return container;

    }

    /**
     * Get all empty containers of the {@link #InstitutionImpl(String) Institution}.
     *
     * @return Container[] with all empty containers of the {@link #InstitutionImpl(String) Institution}.
     */
    public Container[] getEmptyContainers() {
        return Arrays.copyOf(this.emptyContainers, this.totalEmptyContainers);
    }

    /**
     * Get defective containers container [ ].
     *
     * @return the container [ ]
     */
    public Container[] getDefectiveContainers() {
        return Arrays.copyOf(this.defectiveContainers, this.totalDefectiveContainers);
    }


    /**
     * Add an empty container to the {@code collection}.
     *
     * @param container The container to added.
     *
     * @return True, if the container was added. False, if the container was not added.
     *
     * @throws ContainerException If the given container is null.                            If the given container already exists in the {@code collection}.
     */
    public boolean addEmptyContainer(Container container) throws ContainerException {
        this.validateContainer(container);

        if (this.containsContainer(container)) {
            return false;
        }

        if (this.totalEmptyContainers == this.emptyContainers.length) {
            this.expandEmptyContainerArraySize();
        }

        this.emptyContainers[this.totalEmptyContainers++] = container;
        return true;

    }

    /**
     * Add defective container to the {@code collection}.
     * @param container The container to added.
     * @throws ContainerException If the given container is null.
     */
    public void addDefectiveContainer(Container container) throws ContainerException {
        this.validateContainer(container);

        if (this.totalDefectiveContainers == this.defectiveContainers.length) {
            this.expandDefectiveContainers();
        }

        this.defectiveContainers[this.totalDefectiveContainers++] = container;
    }

    /**
     * Getter for the deep copy of Vehicle {@code collection} of an {@link #InstitutionImpl(String) Institution}.
     *
     * @return The deep copy of Vehicle {@code collection} of an {@link #InstitutionImpl(String) Institution}.
     */
    @Override
    public Vehicle[] getVehicles() {
        return this.copyVehicles();
    }

    /**
     * Adds a given Vehicle to the Vehicle {@code collection} of an {@link #InstitutionImpl(String) Institution}, if the array is full expand it.
     *
     * @param vehicle The given Vehicle.
     * @return <ol>
     * <li>True, if the given Vehicle was added in Vehicle {@code collection}.</li>
     * <li>False, if the given Vehicle is equal with an existing Vehicle.</li>
     * </ol>
     * @throws VehicleException <ol>
     *                          <li>If the given Vehicle is null.</li>
     *                          </ol>
     */
    @Override
    public boolean addVehicle(Vehicle vehicle) throws VehicleException {
        this.validateVehicle(vehicle);

        if (this.containsVehicle(vehicle)) {
            return false;
        }

        if (this.totalVehicles >= this.vehicles.length) {
            this.expandVehicleArray();
        }

        this.vehicles[this.totalVehicles++] = vehicle;
        return true;
    }

    /**
     * Remove vehicle.
     *
     * @param vehicle the vehicle
     *
     * @throws VehicleException the vehicle exception
     */
    public void removeVehicle(Vehicle vehicle) throws VehicleException {
        this.validateVehicle(vehicle);

        if (!this.containsVehicle(vehicle)) {
            throw new VehicleException("GIVEN VEHICLE DOES NOT EXIST");
        }

        int index = getIndexVehicle(vehicle);

        vehicles[index] = vehicles[--totalVehicles];
        vehicles[totalVehicles] = null;
    }

    /**
     * Disables a given Vehicle from the Vehicle {@code collection} of an {@link #InstitutionImpl(String) Institution}.
     *
     * @param vehicle The given Vehicle.
     * @throws VehicleException <ol>
     *                          <li>If, the given Vehicle is null.</li>
     *                          <li>If, the given Vehicle is disabled.</li>
     *                          <li>If, the given Vehicle does not exists in {@code collection} of Vehicle.</li>
     *                          </ol>
     */
    @Override
    public void disableVehicle(Vehicle vehicle) throws VehicleException {
        validateVehicle(vehicle);

        int index = getIndexVehicle(vehicle);
        VehicleImpl v = (VehicleImpl) vehicles[index];

        if (!v.getOnDuty()) {
            throw new VehicleException("GIVEN VEHICLE IS ALREADY DISABLED");
        }

        v.setOnDuty(false);
    }

    /**
     * Enables a given Vehicle of an {@link #InstitutionImpl(String) Institution}.
     *
     * @param vehicle The given Vehicle.
     * @throws VehicleException <ol>
     *                          <li>If, the given Vehicle is null.</li>
     *                          <li>If, the given Vehicle is enabled.</li>
     *                          <li>If, the given Vehicle does not exist in {@code collection} of Vehicle.</li>
     *                          </ol>
     */
    @Override
    public void enableVehicle(Vehicle vehicle) throws VehicleException {
        validateVehicle(vehicle);

        int index = getIndexVehicle(vehicle);
        VehicleImpl v = (VehicleImpl) vehicles[index];

        if (v.getOnDuty()) {
            throw new VehicleException("GIVEN VEHICLE IS ALREADY ENABLED");
        }

        v.setOnDuty(true);
    }


    /**
     * Retrieves with a given index the Vehicle of a {@code collection} of Vehicles in a {@link #InstitutionImpl(String) Institution}.
     *
     * @param index The given index
     *
     * @return The Vehicle
     */
    public Vehicle retrieveVehicle(int index) {
        return this.vehicles[index];
    }


    /**
     * Getter for the destinations of a given AidBox of an {@link #InstitutionImpl(String) Institution}.
     *
     * @param aidBox The given AidBox
     * @return double The distance to the base of the given AidBox.
     * @throws AidBoxException If the given AidBox is null.
     *                         If the given AidBox does not exist in the {@code collection}.
     */
    @Override
    public double getDistance(AidBox aidBox) throws AidBoxException {
        this.validateAidBox(aidBox);

        for (AidBox selected : this.aidBoxes) {
            if (selected.equals(aidBox)) {
                return ((AidBoxImpl) selected).getDistanceToBase();
            }
        }

        throw new AidBoxException("DISTANCE TO BASE NOT FOUND");
    }

    /**
     * Adds a given PickingMap to {@code collection} of PickingMap of a {@link #InstitutionImpl(String) Institution}.
     *
     * @param pickingMap The given PickingMap
     * @return <ul>
     * <li>False, if given PickingMap already exists.</li>
     * <li>True, if given PickingMap was inserted into colection of PickingMap</li>
     * </ul>
     * @throws PickingMapException <ul>
     *                             <li>If, given PickingMap is null</li>
     *                             </ul>
     */
    @Override
    public boolean addPickingMap(PickingMap pickingMap) throws PickingMapException {
        this.validatePickingMap(pickingMap);

        if (this.containsPickingMap(pickingMap)) {
            return false;
        }

        if (this.totalPickingMaps >= this.pickingMaps.length) {
            this.expandPickingMapArray();
        }

        this.pickingMaps[this.totalPickingMaps++] = pickingMap;
        return true;
    }

    /**
     * Getter for the deep copy of PickingMap {@code collection} of a {@link #InstitutionImpl(String) Institution}.
     *
     * @return The deep copy of PickingMap {@code collection} of a {@link #InstitutionImpl(String) Institution}.
     */
    @Override
    public PickingMap[] getPickingMaps() {
        return this.copyPickingMaps();
    }

    /**
     * Getter for the index of a given AidBox of an {@link #InstitutionImpl(String) Institution}.
     *
     * @param aidBox The given AidBox
     * @return <ul>
     * <li>If given AidBox does not exist, returns a -1. </li>
     * <li>If given AidBox does exist, returns the index.</li>
     *
     * </ul>
     */

    /**
     * Getter for the deep copy of PickingMap {@code collection} between two given LocalDateTime of a {@link #InstitutionImpl(String) Institution}.
     *
     * @param from The first given LocalDateTime
     * @param to   The second given LocalDateTime
     * @return The deep copy of PickingMap {@code collection} between two date and time stamps.
     */
    @Override
    public PickingMap[] getPickingMaps(LocalDateTime from, LocalDateTime to) {
        return this.getPickingMapsBetweenDates(from, to);
    }


    /**
     * Gets the current PickingMap (recent) of a PickingMap {@code collection}.
     *
     * @return The last inserted PickingMap
     * @throws PickingMapException If PickingMap {@code collection} has no PickingMap.
     */
    @Override
    public PickingMap getCurrentPickingMap() throws PickingMapException {
        if (this.totalPickingMaps <= 0) {
            throw new PickingMapException("NO PICKINGMAPS LEFT");
        }

        return this.pickingMaps[this.totalPickingMaps - 1];
    }


    /**
     * Gets index aid box.
     *
     * @param aidBox the aid box
     *
     * @return the index aid box
     */
    private int getIndexAidBox(AidBox aidBox) {
        for (int index = 0; index < this.totalAidBoxes; index++) {
            if (this.aidBoxes[index].equals(aidBox)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Verifies if a given AidBox exists in a {@link #InstitutionImpl(String) Institution}.
     *
     * @param aidBox The given AidBox
     *
     * @return <ul> <li>False, if the given AidBox does not exist.</li> <li>True, if the given AidBox does exist.</li> </ul>
     */
    private boolean containsAidBox(AidBox aidBox) {
        if (this.totalAidBoxes == 0) {
            return false;
        }
        for (int counter = 0; counter < this.totalAidBoxes; counter++) {
            if (this.aidBoxes[counter].equals(aidBox)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for the index of a given Vehicle of a {@code collection} of Vehicles in a {@link #InstitutionImpl(String) Institution}.
     *
     * @param vehicle The given Vehicle.
     * @return <ul>
     * <li>The index of a Vehicle, if Vehicle exists.</li>
     * <li>If Vehicle does not exists, a -1</li>
     * </ul>
     */
    private int getIndexVehicle(Vehicle vehicle) {
        for (int index = 0; index < this.totalVehicles; index++) {
            if (this.vehicles[index].equals(vehicle)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Verifies if given PickingMap of a {@code collection} of PickingMaps in a {@link #InstitutionImpl(String) Institution}.
     *
     * @param pickingMap The given PickingMap
     *
     * @return <ul> <li>True, if given PickingMa does exists.</li> <li>False, if given PickingMap does not exists.</li> </ul>
     */
    private boolean containsPickingMap(PickingMap pickingMap) {
        if (this.totalPickingMaps <= 0) {
            return false;
        }
        for (int counter = 0; counter < this.totalPickingMaps; counter++) {
            if (this.pickingMaps[counter].equals(pickingMap)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if a given measurement already exists for the specified date in the container.
     *
     * @param measurement The given Measurement to check.
     * @param container   The given Container to check within.
     * @return <ul>
     * <li>False, if the container contains a null measurement before finding any matching dates.f </li>
     * <li>True, if a measurement with the same date as the given measurement is found in the container.</li>
     * <li>False, if no measurement with the same date is found and the end of the measurements list is reached.</li>
     * </ul>
     */
    private boolean isMeasurementAlreadyExistForGivenDate(Measurement measurement, Container container) {
        Measurement[] measurements = container.getMeasurements();
        for (Measurement selected : measurements) {

            if (selected == null) {
                return false;
            }

            if (selected.getDate().equals(measurement.getDate())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Loads all data for the {@link #InstitutionImpl(String) Institution}, either from a local JSON file, if there are no local files loads from an API.
     *
     * @throws IOException If there is an error in file handling or data parsing.
     */
    private void loadFromAPI() throws IOException {
        try {
            //Get all container types from the API.
            ContainerType[] containerTypes = JsonUtil.getJsonToContainerType();
            //Get all containers from the API.
            Container[] containers = JsonUtil.getJsonToContainer(containerTypes);
            //Get all aid boxes from the API.
            AidBox[] aidBoxes = JsonUtil.getJsonToAidBox(containers);
            //Get all measurements from the API.
            Measurement[] measurements = JsonUtil.getJsonToMeasurement();
            //Get all vehicles from the API.
            Vehicle[] vehicles = JsonUtil.getJsonToVehicle();
            //For all aid boxes loaded from the API, add them to the institution.
            for (AidBox selected : aidBoxes) {
                try {
                    this.addAidBox(selected);
                    System.out.println("Loaded AIDBox: " + selected.getCode());
                } catch (AidBoxException e) {
                    System.out.println("Error loading AIDBox: " + selected.getCode());
                }
            }
            //For all vehicles loaded from the API, add them to the institution.
            for (Vehicle selected : vehicles) {
                try {
                    this.addVehicle(selected);
                    System.out.println("Loaded Vehicle: " + selected.getCode());
                } catch (VehicleException e) {
                    System.out.println("Error loading Vehicle: " + selected.getCode());
                }

            }
            //For all containers loaded from the API, add them to the institution.
            for (Container container : containers) {
                boolean hasValidMeasurement = false;
                for (Measurement measurement : measurements) {
                    //Check if the measurement is valid for the container.
                    if (container.getCode().equals(((MeasurementImpl) measurement).getContainerCode()) && isContainerOnTheAidBox(container)) {
                        hasValidMeasurement = true;
                        try {
                            //If it is valid, add it to the container.
                            this.addMeasurement(measurement, container);
                            System.out.println("Measurement added to the container " + container.getCode() + " with value of: " + measurement.getValue() + " at " + measurement.getDate());
                            break;
                        } catch (ContainerException e) {
                            //If it is not valid, add it to the defective containers array.
                            System.out.println("Error adding measurement to the container " + container.getCode() + ": " + e.getMessage());
                            this.defectiveContainers[this.totalDefectiveContainers++] = container;
                        } catch (MeasurementException e) {
                            System.out.println(e.getMessage());
                            this.defectiveContainers[this.totalDefectiveContainers++] = container;
                        }
                    }
                }
                //If there is no valid measurement for the container, add it to the empty containers array.
                if (!hasValidMeasurement && !hasEmptyContainer(container)) {
                    if (this.totalEmptyContainers >= this.emptyContainers.length) {
                        this.expandContainerArray();
                    }
                    this.emptyContainers[this.totalEmptyContainers++] = container;
                    System.out.println("Container " + container.getCode() + " added to empty containers.");
                }
            }
            System.out.println("Data loaded successfully!");


        } catch (ParseException | ContainerException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Verifies if given Container exists in {@code collection} of Container of a {@link #InstitutionImpl(String) Institution}.
     *
     * @param container The given Container.
     * @return <ul>
     * <li>False, if the given Container does not exists in {@code collection} of Container</li>
     * <li>True, if the given Container does exists in {@code collection} of Container</li>
     * </ul>
     */
    private boolean containsContainer(Container container) {
        if (this.totalAidBoxes == 0) {
            return false;
        }

        for (AidBox selected : this.aidBoxes) {
            Container[] containers = selected.getContainers();
            for (Container contain : containers) {
                if (contain.equals(container)) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Expands the Vehicle {@code collection} in a {@link #InstitutionImpl(String) Institution}.
     */
    private void expandVehicleArray() {
        this.vehicles = Arrays.copyOf(this.vehicles, this.vehicles.length * EXPAND_FACTOR);
    }

    /**
     * Expands the PickingMap {@code collection} in a {@link #InstitutionImpl(String) Institution}.
     */
    private void expandPickingMapArray() {
        this.pickingMaps = Arrays.copyOf(this.pickingMaps, this.pickingMaps.length * EXPAND_FACTOR);
    }

    /**
     * Expands the AidBox {@code collection} in an {@link #InstitutionImpl(String) Institution}.
     */
    private void expandAidBoxArray() {
        this.aidBoxes = Arrays.copyOf(this.aidBoxes, this.aidBoxes.length * EXPAND_FACTOR);
    }

    /**
     * Expands the Container {@code collection} in an {@link #InstitutionImpl(String) Institution}.
     */
    private void expandContainerArray() {
        if (this.totalEmptyContainers >= this.emptyContainers.length) {
            this.emptyContainers = Arrays.copyOf(this.emptyContainers, this.emptyContainers.length * EXPAND_FACTOR);
        }
    }

    /**
     * Expands the defective Container {@code collection} in an {@link #InstitutionImpl(String) Institution}.
     */
    private void expandDefectiveContainers() {
        this.defectiveContainers = Arrays.copyOf(this.defectiveContainers, this.defectiveContainers.length * EXPAND_FACTOR);
    }

    /**
     * Expands the empty Container {@code collection} in an {@link #InstitutionImpl(String) Institution}.
     */
    private void expandEmptyContainerArraySize() {
        this.emptyContainers = Arrays.copyOf(this.emptyContainers, this.emptyContainers.length * EXPAND_FACTOR);
    }

    /**
     * Verifies if given Container exists in empty Container {@code collection} of a {@link #InstitutionImpl(String) Institution}.
     *
     * @param container The given Container
     * @return <ul>
     * <li>True, if the given Container was added.</li>
     * </ul>
     */
    private boolean hasEmptyContainer(Container container) {
        for (int i = 0; i < this.totalEmptyContainers; i++) {
            if (this.emptyContainers[i].equals(container)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifies if given Container exists in AidBox {@code collection} of a {@link #InstitutionImpl(String) Institution}.
     *
     * @param container The given Container.
     * @return <ul>
     * <li>True, if the given Container exists in AidBox {@code collection}</li>
     * <li>False, if the given Container does not exist in AidBox {@code collection}</li>
     * </ul>
     */
    private boolean isContainerOnTheAidBox(Container container) {
        AidBox[] aidBoxes = this.aidBoxes;
        for (AidBox selected : aidBoxes) {
            Container[] containers = selected.getContainers();
            for (Container contain : containers) {
                if (contain.equals(container)) {
                    return true;
                }
            }
        }
        return false;
    }


    private void validateAidBox(AidBox aidBox) throws AidBoxException {
        if (aidBox == null) {
            throw new AidBoxException("GIVEN AIDBOX IS NULL");
        }
    }

    private void validateMeasurement(Measurement measurement, Container container) throws MeasurementException {
        if (measurement == null) {
            throw new MeasurementException("GIVEN MEASUREMENT IS NULL");
        }

        if (measurement.getValue() < 0) {
            throw new MeasurementException("GIVEN MEASUREMENT VALUE IS BELOW ZERO");
        }

        if (container.getCapacity() < measurement.getValue()) {
            throw new MeasurementException("Given measurement value " + measurement.getValue() + " is bigger than container capacity " + container.getCapacity() + " esat the date of " + measurement.getDate());
        }
    }

    private void validateContainer(Container container) throws ContainerException {
        if (container == null) {
            throw new ContainerException("GIVEN CONTAINER IS NULL");
        }
    }

    private void validateContainerType(ContainerType containerType) throws ContainerException {
        if (containerType == null) {
            throw new ContainerException("GIVEN CONTAINER TYPE IS NULL");
        }
    }

    private void validateVehicle(Vehicle vehicle) throws VehicleException {
        if (vehicle == null) {
            throw new VehicleException("GIVEN VEHICLE IS NULL");
        }
    }

    private void validatePickingMap(PickingMap pickingMap) throws PickingMapException {
        if (pickingMap == null) {
            throw new PickingMapException("GIVEN PICKINGMAP IS NULL");
        }
    }

    /**
     * Verifies if given Vehicle exists in a {@link #InstitutionImpl(String) Institution}.
     *
     * @param vehicle The given Vehicle
     * @return <ul>
     * <li>False, if the given Vehicle does not exist.</li>
     * <li>True, if the given Vehicle does exist.</li>
     * </ul>
     */
    private boolean containsVehicle(Vehicle vehicle) {
        if (this.totalVehicles <= 0) {
            return false;
        }

        for (int counter = 0; counter < this.totalVehicles; counter++) {
            if (this.vehicles[counter].equals(vehicle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The copy of all AidBoxes on the institution.
     * @return The copy of AidBoxes.
     * @throws ContainerException If the copy of AidBoxes failed.
     */
    private AidBox[] copyAidBoxes() throws ContainerException {
        AidBox[] copy = new AidBox[totalAidBoxes];
        for (int i = 0; i < totalAidBoxes; i++) {
            copy[i] = new AidBoxImpl((AidBoxImpl) aidBoxes[i]);
        }
        return copy;
    }

    /**
     * The copy of all vehicles on the institution.
     * @return The copy of all vehicles on the institution.
     */
    private Vehicle[] copyVehicles() {
        Vehicle[] copy = new Vehicle[totalVehicles];
        for (int i = 0; i < totalVehicles; i++) {
            copy[i] = new VehicleImpl((VehicleImpl) vehicles[i]);
        }
        return copy;
    }

    /**
     * The copy of all picking maps on the institution.
     * @return The copy of all picking maps on the institution.
     */
    private PickingMap[] copyPickingMaps() {
        PickingMap[] copy = new PickingMap[totalPickingMaps];
        for (int i = 0; i < totalPickingMaps; i++) {
            copy[i] = new PickingMapImpl((PickingMapImpl) pickingMaps[i]);
        }
        return copy;
    }

    /**
     * Returns all PickingMaps between two given dates.
     * @param from The starting date.
     * @param to The ending date.
     * @return All PickingMaps between two given dates.
     */
    private PickingMap[] getPickingMapsBetweenDates(LocalDateTime from, LocalDateTime to) {
        PickingMap[] result = new PickingMap[totalPickingMaps];
        int count = 0;

        for (PickingMap map : pickingMaps) {
            if (map != null && !map.getDate().isBefore(from) && !map.getDate().isAfter(to)) {
                result[count++] = map;
            }
        }

        return Arrays.copyOf(result, count);
    }

    /**
     * Loads all data from file, if it fails load from API.
     * @throws IOException If fails to load from file or API.
     */
    private void loadAllData() throws IOException {
        if (new File(JSON_FILE).exists()) {
            System.out.println("Loading from file...");
            new ImporterImpl().importData(this);
        } else {
            System.out.println("Loading from API...");
            loadFromAPI();
        }
    }


}

