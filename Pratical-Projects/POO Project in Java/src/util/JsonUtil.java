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

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.ContainerException;
import com.estg.io.HTTPProvider;
import com.estg.pickingManagement.Vehicle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pp_tp_er.core.*;
import pp_tp_er.pickingManagement.VehicleImpl;

/**
 * A class that is responsible to use the {@link JSONParser}  to extract from {@link JSONObject} as well {@link JSONArray}
 *
 * @author 8230068
 * @author 8230069
 * @version 1.6.9
 * @file JsonUntil.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @since Java SDK 13
 */
public class JsonUtil {

	/**
	 * Get an array of {@link pp_tp_er.core.ContainerTypeImpl ContainerType} objects by parsing JSON data from API.
	 *
	 * @return <ul> <li>An array of {@link pp_tp_er.core.ContainerTypeImpl ContainerType} objects parsed from JSON data</li> </ol>
	 *
	 * @throws ParseException <ol>
	 *     <li>If there is an error parsing the JSON data </li>
	 *     <li>If the JSON data is null</li>
	 * </ol>
	 */
	public static ContainerType[] getJsonToContainerType() throws ParseException {
        HTTPProvider httpProvider = new HTTPProvider();
        String containerTypeURL = "https://data.mongodb-api.com/app/data-docuz/endpoint/types";
        String containerTypeResponse = httpProvider.getFromURL(containerTypeURL);
        if (containerTypeResponse == null) {
            throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "GIVEN JSON IS NULL");
        }

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(containerTypeResponse);
        if (jsonArray != null) {
            JSONObject containerType = (JSONObject) jsonArray.get(0);
            JSONArray containerTypeArray = (JSONArray) containerType.get("types");
            ContainerType[] containersType = new ContainerType[containerTypeArray.size()];

            for (int counter = 0; counter < containerTypeArray.size(); counter++) {
                String typeName = (String) containerTypeArray.get(counter);
                containersType[counter] = new ContainerTypeImpl(typeName);
            }
            return containersType;
        }

        throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "JSONARRAY IS NULL");
    }

	/**
	 * Get an array of the Container object by parsing JSON data from the API.
	 *
	 * @param containersTypes An array of ContainerType objects used to map container types from the JSON data.
	 *
	 * @return <ol> <li>Container[] An array of Container objects parsed from the JSON data</li> </ol>
	 *
	 * @throws ParseException <ol>                        <li>If there is an error parsing the JSON data</li>                        <li>If the JSON data is null</li>                        </ol>
	 */
	public static Container[] getJsonToContainer(ContainerType[] containersTypes) throws ParseException {
        HTTPProvider httpProvider = new HTTPProvider();
        String containerURL = "https://data.mongodb-api.com/app/data-docuz/endpoint/containers";
        String containerResponse = httpProvider.getFromURL(containerURL);
        if (containerResponse == null) {
            throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "GIVEN JSON IS NULL");
        }

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(containerResponse);
        if (jsonArray != null) {
            Container[] containers = new Container[jsonArray.size()];

            for (int counter = 0; counter < jsonArray.size(); counter++) {
                JSONObject jsonContainer = (JSONObject) jsonArray.get(counter);
                String code = (String) jsonContainer.get("code");
                double capacity = (long) jsonContainer.get("capacity");
                String type = (String) jsonContainer.get("type");
                ContainerType containerType = stringToContainerType(type, containersTypes);
                containers[counter] = new ContainerImpl(code, capacity, containerType);
            }
            return containers;
        }

        throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "JSONARRAY IS NULL");
    }

	/**
	 * Get an array of JSONObjects representing the distances by parsing JSON data from the API.
	 *
	 * @return <ol> <li>JSONObject[] An array of JSONObjects representing the distances parsed from the JSON data. </li> </ol>
	 *
	 * @throws ParseException <ol>                        <li>If there is an error parsing the JSON data.</li>                        <li>If the JSON data is null.</li>                        </ol>
	 */
	public static JSONObject[] getJsonToDistance() throws ParseException {
        HTTPProvider httpProvider = new HTTPProvider();
        String distancesURL = "https://data.mongodb-api.com/app/data-docuz/endpoint/distances";
        String distancesResponse = httpProvider.getFromURL(distancesURL);
        if (distancesResponse == null) {
            throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "GIVEN JSON IS NULL");
        }

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(distancesResponse);
        if (jsonArray != null) {
            JSONObject[] distances = new JSONObject[jsonArray.size()];

            for (int counter = 0; counter < jsonArray.size(); counter++) {
                distances[counter] = (JSONObject) jsonArray.get(counter);
            }
            return distances;
        }

        throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "JSONARRAY IS NULL");
    }

	/**
	 * Get an array of AidBox objects by parsing JSON data from the API.
	 *
	 * @param allContainers And array of Container objects used to map containers from the JSON data.
	 *
	 * @return <ol> <li>AidBox[] An array of AidBox objects parsed from the JSON data.</li> </ol>
	 *
	 * @throws ParseException     <ol>                        <li>If there is an error in parsing the JSON data.</li>                        <li>If the JSON data is null.</li>                        </ol>
	 * @throws ContainerException the container exception
	 */
	public static AidBox[] getJsonToAidBox(Container[] allContainers) throws ParseException, ContainerException {
        HTTPProvider httpProvider = new HTTPProvider();
        String aidBoxesURL = "https://data.mongodb-api.com/app/data-docuz/endpoint/aidboxes";
        String distancesResponse = httpProvider.getFromURL(aidBoxesURL);
        if (distancesResponse == null) {
            throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "GIVEN JSON IS NULL");
        }

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(distancesResponse);
        if (jsonArray != null) {
            AidBox[] aidBoxes = new AidBox[jsonArray.size()];
            for (int counter = 0; counter < jsonArray.size(); counter++) {
                JSONObject jsonAidBox = (JSONObject) jsonArray.get(counter);
                String code = (String) jsonAidBox.get("code");
                String zone = (String) jsonAidBox.get("Zona");
                JSONArray containerArray = (JSONArray) jsonAidBox.get("containers");
                if (containerArray != null) {
                    Container[] containers = new Container[containerArray.size()];
                    for (int innerCounter = 0; innerCounter < containerArray.size(); innerCounter++) {
                        String containerCode = (String) containerArray.get(innerCounter);
                        containers[innerCounter] = findContainer(containerCode, allContainers);
                    }
                    aidBoxes[counter] = new AidBoxImpl(containers, code, zone);
                }
            }
            return aidBoxes;
        }

        throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "GIVEN JSON IS NULL");

    }

	/**
	 * Get and array of Vehicle objects by parsing the JSON data from the API.
	 *
	 * @return <ol> <li>Vehicle[] An array of Vehicle objects parsed from the JSON data.</li> </ol>
	 *
	 * @throws ParseException <ol>                        <li>If there is an error parsing the JSON data.</li>                        <li>If the JSON data is null.</li>                        </ol>
	 */
	public static Vehicle[] getJsonToVehicle() throws ParseException {
        HTTPProvider httpProvider = new HTTPProvider();
        String vehiclesURL = "https://data.mongodb-api.com/app/data-docuz/endpoint/vehicles";
        String vehiclesResponse = httpProvider.getFromURL(vehiclesURL);
        if (vehiclesResponse == null) {
            throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "GIVEN JSON IS NULL");
        }

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(vehiclesResponse);
        if (jsonArray != null) {
            Vehicle[] vehicles = new Vehicle[jsonArray.size()];
            for (int counter = 0; counter < jsonArray.size(); counter++) {
                JSONObject jsonVehicle = (JSONObject) jsonArray.get(counter);
                String code = (String) jsonVehicle.get("code");
                JSONObject jsonCapacity = (JSONObject) jsonVehicle.get("capacity");
                Capacity[] capacities = new Capacity[jsonCapacity.size()];

                int index = 0;
                for (Object key : jsonCapacity.keySet()) {
                    String keyString = (String) key;
                    double value = (long) jsonCapacity.get(keyString);
                    ContainerType containerType = new ContainerTypeImpl(keyString);
                    capacities[index++] = new Capacity(containerType, value);
                }
                vehicles[counter] = new VehicleImpl(code, capacities);
            }
            return vehicles;
        }

        throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "GIVEN JSON IS NULL");
    }

	/**
	 * Get and array of Measurement objects by parsing JSON data from the API.
	 *
	 * @return <ol> <li>Measurement[] An array of Measurement objects parsed from the JSON data.</li> </ol>
	 *
	 * @throws ParseException <ol>                        <li>If there is an error in parsing the JSON data</li>                        <li>If the JSON data is null</li>                        </ol>
	 */
	public static Measurement[] getJsonToMeasurement() throws ParseException {
        HTTPProvider httpProvider = new HTTPProvider();
        String measurementsURL = "https://data.mongodb-api.com/app/data-docuz/endpoint/readings";
        String measurementsResponse = httpProvider.getFromURL(measurementsURL);
        if (measurementsResponse == null) {
            throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "GIVEN JSON IS NULL");
        }

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(measurementsResponse);
        if (jsonArray != null) {
            Measurement[] measurements = new Measurement[jsonArray.size()];

            for (int counter = 0; counter < jsonArray.size(); counter++) {
                JSONObject jsonMeasurement = (JSONObject) jsonArray.get(counter);
                String date = (String) jsonMeasurement.get("data");
                double value = (long) jsonMeasurement.get("valor");
                String containerCode = (String) jsonMeasurement.get("contentor");
                measurements[counter] = new MeasurementImpl(containerCode, date, value);
            }
            return measurements;
        }

        throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "GIVEN JSON IS NULL");
    }

    /**
     * Map a String to a ContainerType object.
     *
     * @param type           The String representing the name of the ContainerType to be mapped.
     * @param containerTypes An array of ContainerType objects available for mapping.
     * @return <ol>
     * <li>ContainerType The ContainerType object that matches then provided String</li>
     * <li>new ContainerType With the name "UNKNOWN" if no match is found.</li>
     * </ol>
     */

    private static ContainerType stringToContainerType(String type, ContainerType[] containerTypes) {
        for (ContainerType selected : containerTypes) {
            if (selected.toString().equals(type)) {
                return selected;
            }
        }
        return new ContainerTypeImpl("UNKNOWN");
    }

    /**
     * Finds a Container object in an array based on its code.
     *
     * @param containerCode The code of the container to be found.
     * @param containers    An array of Container objects in which to search.
     * @return <ol>
     * <li>Container The container object that matches the provided code</li>
     * <li>Null If no match is found</li>
     * </ol>
     */
    private static Container findContainer(String containerCode, Container[] containers) {
        for (Container selected : containers) {
            if (selected.getCode().equals(containerCode)) {
                return selected;
            }
        }
        return null;
    }
}
