/*
 * Nome: Pedro Daniel Gonçalves Antunes
 * Número: 8230068
 * Turma: LSIRCT2
 *
 * Nome: Ruben Xavier Ferreira Nunes
 * Número: 8230069
 * Turma: LSIRCT2
 */
package util;

import com.estg.core.*;
import com.estg.pickingManagement.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pp_tp_er.core.Capacity;
import pp_tp_er.core.InstitutionImpl;
import pp_tp_er.core.MeasurementImpl;
import pp_tp_er.pickingManagement.VehicleImpl;

import java.io.FileWriter;
import java.io.IOException;


/**
 * A class that is responsible to save an {@link pp_tp_er.core.InstitutionImpl Institution}
 * to a JSON File.
 *
 * @author 8230068
 * @author 8230069
 * @version 1.6.9
 * @file SaveToJsonFile.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @since Java SDK 13
 */
public class SaveToJsonFile {

    /**
     * The {@link #FILE_PATH path} to the file.
     */
    private static final String FILE_PATH = "institution.json";

	/**
	 * Saves the {@link pp_tp_er.core.InstitutionImpl Institution} to a json file.
	 *
	 * @param institution The {@link pp_tp_er.core.InstitutionImpl Institution} to save.
	 */
	public static void saveJsonFile(Institution institution) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", institution.getName());

        //Get all aid boxes from the institution to convert each of them to json.
        JSONArray aidBoxArray = new JSONArray();
        for (AidBox box : institution.getAidBoxes()) {
            aidBoxArray.add(convertAidBoxToJson(box));
        }
        jsonObject.put("AidBoxes", aidBoxArray);

        //Get all vehicles of the institution to convert each of them to json.
        JSONArray vehiclesArray = new JSONArray();
        for (Vehicle vehicle : institution.getVehicles()) {
            vehiclesArray.add(convertVehicleToJson(vehicle));
        }
        jsonObject.put("Vehicles", vehiclesArray);

        //Get all picking maps of the institution to convert each of them to json.
        JSONArray pickingMapsArray = new JSONArray();
        for (PickingMap pickingMap : institution.getPickingMaps()) {
            pickingMapsArray.add(convertPickingMapToJson(pickingMap));
        }
        jsonObject.put("PickingMaps", pickingMapsArray);

        //Get all empty containers of the institution to convert each of them to json.
        JSONArray emptyContainersArray = new JSONArray();
        for (Container container : ((InstitutionImpl) institution).getEmptyContainers()) {
            emptyContainersArray.add(convertContainerToJson(container));
        }
        jsonObject.put("EmptyContainers", emptyContainersArray);

        //Get all defective containers of the institution to convert each of them to json.
        JSONArray defectiveContainersArray = new JSONArray();
        for (Container container : ((InstitutionImpl) institution).getDefectiveContainers()) {
            defectiveContainersArray.add(convertContainerToJson(container));
        }
        jsonObject.put("DefectiveContainers", defectiveContainersArray);

        //Write to a json file based on the path.
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(jsonObject.toJSONString());
            System.out.println("Saving data.....");
            file.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Converts a given {@link pp_tp_er.core.AidBoxImpl AidBox} to a {@link JSONObject JSON Object}
     * @param aidBox The given {@link pp_tp_er.core.AidBoxImpl AidBox} to convert to.
     * @return The {@link pp_tp_er.core.AidBoxImpl AidBox} converted to {@link JSONObject JSON Object}
     */
    private static JSONObject convertAidBoxToJson(AidBox aidBox) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Code", aidBox.getCode());
        jsonObject.put("Zone", aidBox.getZone());

        JSONArray containerArray = new JSONArray();
        for (Container container : aidBox.getContainers()) {
            containerArray.add(convertContainerToJson(container));
        }
        jsonObject.put("Containers", containerArray);

        return jsonObject;
    }

    /**
     * Converts the {@link pp_tp_er.core.ContainerImpl Container} to a {@link JSONObject JSON Object}.
     * @param container The given {@link pp_tp_er.core.ContainerImpl Container} to convert to.
     * @return The {@link pp_tp_er.core.ContainerImpl Container} converted to {@link JSONObject JSON Object}.
     */
    private static JSONObject convertContainerToJson(Container container) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Code", container.getCode());
        jsonObject.put("Type", container.getType().toString());
        jsonObject.put("Capacity", container.getCapacity());

        JSONArray measurementArray = new JSONArray();
        for (Measurement measurement : container.getMeasurements()) {
            measurementArray.add(convertMeasurementToJson(measurement));
        }
        jsonObject.put("Measurements", measurementArray);

        return jsonObject;
    }

    /**
     * Converts given {@link pp_tp_er.core.MeasurementImpl Measurement} to a {@link JSONObject JSON Object}.
     * @param measurement The given {@link pp_tp_er.core.MeasurementImpl Measurement} to convert to.
     * @return The {@link pp_tp_er.core.MeasurementImpl Measurement} converted to {@link JSONObject JSON Object}.
     */
    private static JSONObject convertMeasurementToJson(Measurement measurement) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ContainerCode", ((MeasurementImpl) measurement).getContainerCode());
        jsonObject.put("Date", measurement.getDate().toString());
        jsonObject.put("Value", measurement.getValue());
        return jsonObject;
    }

    /**
     * Converts given {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} to a {@link JSONObject JSON Object}.
     * @param vehicle The given {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} to convert to.
     * @return The {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} converted to {@link JSONObject JSON Object}.
     */
    private static JSONObject convertVehicleToJson(Vehicle vehicle) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Code", vehicle.getCode());

        JSONArray capacityArray = new JSONArray();
        for (Capacity capacity : ((VehicleImpl) vehicle).getCapacities()) {
            capacityArray.add(convertCapacityToJson(capacity));
        }
        jsonObject.put("Capacities", capacityArray);

        return jsonObject;
    }

    /**
     * Converts given {@link pp_tp_er.core.Capacity Capacity} to a {@link JSONObject JSON Object}.
     * @param capacity The {@link pp_tp_er.core.Capacity Capacity} to convert to.
     * @return The {@link pp_tp_er.core.Capacity Capacity} converted to {@link JSONObject JSON Object}.
     */
    private static JSONObject convertCapacityToJson(Capacity capacity) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Type", capacity.getContainerType().toString());
        jsonObject.put("Limit", capacity.getLimit());
        jsonObject.put("Quantity", capacity.getQuantity());
        return jsonObject;
    }

    /**
     * Converts given {@link pp_tp_er.pickingManagement.PickingMapImpl PickingMap} to a {@link JSONObject JSON Object}.
     * @param pickingMap The given {@link pp_tp_er.pickingManagement.PickingMapImpl PickingMap} to convert to.
     * @return The {@link pp_tp_er.pickingManagement.PickingMapImpl PickingMap} converted to {@link JSONObject JSON Object}.
     */
    private static JSONObject convertPickingMapToJson(PickingMap pickingMap) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Date", pickingMap.getDate().toString());

        JSONArray routeArray = new JSONArray();
        for (Route route : pickingMap.getRoutes()) {
            routeArray.add(convertRouteToJson(route));
        }
        jsonObject.put("Routes", routeArray);

        return jsonObject;
    }

    /**
     * Convert the {@link pp_tp_er.pickingManagement.RouteImpl Route} to a {@link JSONObject JSON Object}.
     * @param route The given {@link pp_tp_er.pickingManagement.RouteImpl Route} to convert to.
     * @return The {@link pp_tp_er.pickingManagement.RouteImpl Route} converted to {@link JSONObject JSON Object}.
     */
    private static JSONObject convertRouteToJson(Route route) {
        JSONObject jsonObject = new JSONObject();

        JSONArray aidBoxArray = new JSONArray();
        for (AidBox box : route.getRoute()) {
            aidBoxArray.add(convertAidBoxToJson(box));
        }
        jsonObject.put("AidBoxes", aidBoxArray);
        jsonObject.put("Vehicle", convertVehicleToJson(route.getVehicle()));
        jsonObject.put("TotalDistance", route.getTotalDistance());
        jsonObject.put("TotalDuration", route.getTotalDuration());
        jsonObject.put("Report", convertReportToJson(route.getReport()));

        return jsonObject;
    }

    /**
     * Converts given {@link pp_tp_er.pickingManagement.ReportImpl Report} to a {@link JSONObject JSON Object}.
     * @param report The given {@link pp_tp_er.pickingManagement.ReportImpl Report} to convert to.
     * @return The given {@link pp_tp_er.pickingManagement.ReportImpl Report} converted to {@link JSONObject JSON Object}.
     */
    private static JSONObject convertReportToJson(Report report) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Date", report.getDate().toString());
        jsonObject.put("UsedVehicles", report.getUsedVehicles());
        jsonObject.put("NotUsedVehicles", report.getNotUsedVehicles());
        jsonObject.put("PickedContainers", report.getPickedContainers());
        jsonObject.put("NonPickedContainers", report.getNonPickedContainers());
        jsonObject.put("TotalDuration", report.getTotalDuration());
        jsonObject.put("TotalDistance", report.getTotalDistance());
        return jsonObject;
    }
}
