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

package pp_tp_er.io;

import com.estg.core.*;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.pickingManagement.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import pp_tp_er.core.Capacity;
import pp_tp_er.core.*;
import pp_tp_er.pickingManagement.PickingMapImpl;
import pp_tp_er.pickingManagement.ReportImpl;
import pp_tp_er.pickingManagement.RouteImpl;
import pp_tp_er.pickingManagement.VehicleImpl;


import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * An implementation class that implements the provided {@link com.estg.io.Importer Importer}
 * interface.
 * <p>
 * Class responsible for import the data from a JSON file
 *
 * @author 8230068
 * @author 8230069
 * @file ImporterImpl.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @see com.estg.io.Importer
 * @see <a href=”https://www.json.org/json-en.html”>JSON</a>
 * @since Java SDK 13
 */
public class ImporterImpl {

    /**
     * The {@link #FILE_PATH path} of the JSON file.
     */
    private static final String FILE_PATH = "institution.json";

    /**
     * Import the data from the JSON file to the {@link pp_tp_er.core.InstitutionImpl Institution}.
     *
     * @param institution the {@link pp_tp_er.core.InstitutionImpl Institution}
     *
     * @throws IOException If an error occurs while reading the file or parsing the JSON data.
     */
    public void importData(Institution institution) throws IOException {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            // Check if the name of the institution matches the name in the JSON file.
            String name = (String) obj.get("Name");
            if (!institution.getName().equals(name)) {
                throw new IllegalArgumentException("Institution name does not match!");
            }

            //Add the aid boxes to the institution from the information that was read from the JSON file.
            JSONArray aidBoxArray = (JSONArray) obj.get("AidBoxes");
            for (Object aidBoxObj : aidBoxArray) {
                AidBox aidBox = convertJsonToAidBox((JSONObject) aidBoxObj);
                institution.addAidBox(aidBox);
            }

            //Add the vehicles to the institution from the information that was read from the JSON file.
            JSONArray vehicleArray = (JSONArray) obj.get("Vehicles");
            for (Object vehicleObj : vehicleArray) {
                Vehicle vehicle = convertJsonToVehicle((JSONObject) vehicleObj);
                institution.addVehicle(vehicle);
            }

            //Add the picking maps to the institution from the information that was read from the JSON file.
            JSONArray pickingMapArray = (JSONArray) obj.get("PickingMaps");
            for (Object pickingMapObj : pickingMapArray) {
                PickingMap pickingMap = convertJsonToPickingMap((JSONObject) pickingMapObj);
                institution.addPickingMap(pickingMap);
            }

            //Add the empty containers to the institution from the information that was read from the JSON file.
            JSONArray emptyContainerArray = (JSONArray) obj.get("EmptyContainers");
            for (Object emptyContainerObj : emptyContainerArray) {
                Container container = convertJsonToContainer((JSONObject) emptyContainerObj);
                ((InstitutionImpl) institution).addEmptyContainer(container);
            }

            //Add the defective containers to the institution from the information that was read from the JSON file.
            JSONArray defectiveContainerArray = (JSONArray) obj.get("DefectiveContainers");
            for (Object defectiveContainerObj : defectiveContainerArray) {
                Container container = convertJsonToContainer((JSONObject) defectiveContainerObj);
                ((InstitutionImpl) institution).addDefectiveContainer(container);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error loading data from file", e);
        }
    }

    /**
     * Convert the {@link JSONObject JSONObject} to an {@link pp_tp_er.core.AidBoxImpl AidBox}.
     * @param jsonObject The {@link JSONObject JSONObject}to convert.
     * @return The converted {@link pp_tp_er.core.AidBoxImpl AidBox}.
     * @throws AidBoxException If an error occurs while creating the {@link pp_tp_er.core.AidBoxImpl AidBox}.
     */
    private AidBox convertJsonToAidBox(JSONObject jsonObject) throws AidBoxException, ContainerException {
        String code = (String) jsonObject.get("Code");
        String zone = (String) jsonObject.get("Zone");

        //Get the containers from the JSON data.
        JSONArray containerArray = (JSONArray) jsonObject.get("Containers");
        Container[] containers = new Container[containerArray.size()];
        for (int i = 0; i < containerArray.size(); i++) {
            containers[i] = convertJsonToContainer((JSONObject) containerArray.get(i));
        }
        return new AidBoxImpl(containers, code, zone);
    }

    /**
     * Convert the {@link JSONObject JSONObject} to a {@link pp_tp_er.core.ContainerImpl Container}.
     * @param jsonObject The {@link JSONObject JSONObject} to convert.
     * @return The converted {@link pp_tp_er.core.ContainerImpl Container}.
     */
    private Container convertJsonToContainer(JSONObject jsonObject) {
        String code = (String) jsonObject.get("Code");
        String type = (String) jsonObject.get("Type");
        double capacity = (double) jsonObject.get("Capacity");

        //Create the container type.
        ContainerType containerType = new ContainerTypeImpl(type);
        Container container = new ContainerImpl(code, capacity, containerType);

        //Get the measurements from the JSON data.
        JSONArray measurementArray = (JSONArray) jsonObject.get("Measurements");
        for (Object measurementObj : measurementArray) {
            Measurement measurement = convertJsonToMeasurement((JSONObject) measurementObj);
            try {
                container.addMeasurement(measurement);
            } catch (MeasurementException e) {
                e.printStackTrace();
            }
        }
        return container;
    }

    /**
     * Convert the {@link JSONObject JSONObject} to a {@link pp_tp_er.core.MeasurementImpl Measurement}.
     * @param jsonObject The {@link JSONObject JSONObject} to convert.
     * @return The converted {@link pp_tp_er.core.MeasurementImpl Measurement}.
     */
    private Measurement convertJsonToMeasurement(JSONObject jsonObject) {
        String containerCode = (String) jsonObject.get("ContainerCode");
        String date = (String) jsonObject.get("Date");
        double value = (double) jsonObject.get("Value");
        return new MeasurementImpl(containerCode, date, value);
    }

    /**
     * Convert the {@link JSONObject JSONObject} to a {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     * @param jsonObject The {@link JSONObject JSONObject} to convert.
     * @return The converted {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     */
    private Vehicle convertJsonToVehicle(JSONObject jsonObject) {
        String code = (String) jsonObject.get("Code");
        JSONArray capacityArray = (JSONArray) jsonObject.get("Capacities");

        Capacity[] capacities = new Capacity[capacityArray.size()];
        for (int i = 0; i < capacityArray.size(); i++) {
            capacities[i] = convertJsonToCapacity((JSONObject) capacityArray.get(i));
        }
        return new VehicleImpl(code, capacities);
    }

    /**
     * Convert the {@link JSONObject JSONObject} to a {@link pp_tp_er.core.Capacity Capacity}.
     * @param jsonObject The {@link JSONObject JSONObject} to convert.
     * @return The converted {@link pp_tp_er.core.Capacity Capacity}.
     */
    private Capacity convertJsonToCapacity(JSONObject jsonObject) {
        String type = (String) jsonObject.get("Type");
        double quantity = (double) jsonObject.get("Quantity");
        ContainerType containerType = new ContainerTypeImpl(type);
        return new Capacity(containerType, quantity);
    }

    /**
     * Convert the {@link JSONObject JSONObject} to a {@link pp_tp_er.pickingManagement.PickingMapImpl PickingMap}.
     * @param jsonObject The {@link JSONObject JSONObject} to convert.
     * @return The converted {@link pp_tp_er.pickingManagement.PickingMapImpl PickingMap}.
     * @throws AidBoxException If there was an error while creating the {@link pp_tp_er.pickingManagement.PickingMapImpl PickingMap}.
     */
    private PickingMap convertJsonToPickingMap(JSONObject jsonObject) throws AidBoxException, ContainerException {
        String date = (String) jsonObject.get("Date");

        //Get the aid boxes of the picking map.
        JSONArray routeArray = (JSONArray) jsonObject.get("Routes");
        Route[] routes = new Route[routeArray.size()];
        for (int i = 0; i < routeArray.size(); i++) {
            routes[i] = convertJsonToRoute((JSONObject) routeArray.get(i));
        }

        return new PickingMapImpl(LocalDateTime.parse(date), routes);
    }

    /**
     * Convert the {@link JSONObject JSONObject} to a {@link pp_tp_er.pickingManagement.RouteImpl Route}.
     * @param jsonObject The {@link JSONObject JSONObject} to convert.
     * @return The converted {@link pp_tp_er.pickingManagement.RouteImpl Route}.
     * @throws AidBoxException If there was an error while creating the {@link pp_tp_er.pickingManagement.RouteImpl Route}.
     */
    private Route convertJsonToRoute(JSONObject jsonObject) throws AidBoxException, ContainerException {
        JSONArray aidBoxArray = (JSONArray) jsonObject.get("AidBoxes");

        // Get the aid boxes of the route.
        AidBox[] aidBoxes = new AidBox[aidBoxArray.size()];
        for (int i = 0; i < aidBoxArray.size(); i++) {
            aidBoxes[i] = convertJsonToAidBox((JSONObject) aidBoxArray.get(i));
        }

        Vehicle vehicle = convertJsonToVehicle((JSONObject) jsonObject.get("Vehicle"));
        Report report = convertJsonToReport((JSONObject) jsonObject.get("Report"));

        double totalDuration = (double) jsonObject.get("TotalDuration");
        double totalDistance = (double) jsonObject.get("TotalDistance");

        return new RouteImpl(vehicle, aidBoxes, totalDuration, totalDistance, report);
    }

    /**
     * Convert the {@link JSONObject JSONObject} to a {@link pp_tp_er.pickingManagement.ReportImpl Report}.
     * @param jsonObject The {@link JSONObject JSONObject} to convert.
     * @return The converted {@link pp_tp_er.pickingManagement.ReportImpl Report}.
     */
    private Report convertJsonToReport(JSONObject jsonObject) {
        String date = (String) jsonObject.get("Date");
        int usedVehicles = ((Long) jsonObject.get("UsedVehicles")).intValue();
        int notUsedVehicles = ((Long) jsonObject.get("NotUsedVehicles")).intValue();
        int pickedContainers = ((Long) jsonObject.get("PickedContainers")).intValue();
        int nonPickedContainers = ((Long) jsonObject.get("NonPickedContainers")).intValue();
        double totalDuration = (double) jsonObject.get("TotalDuration");
        double totalDistance = (double) jsonObject.get("TotalDistance");

        return new ReportImpl(LocalDateTime.parse(date), usedVehicles, pickedContainers, totalDistance, totalDuration, nonPickedContainers, notUsedVehicles);
    }
}
