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
package game.util;

import entities.*;

import entities.Mission;
import lib.graphs.CustomNetwork;
import lib.exceptions.NotElementComparableException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The JsonSimpleRead class provides methods to read and parse data from JSON files,
 * specifically to load mission-related information into a graph-based data model.
 * The class handles JSON parsing, object creation, and graph population, including rooms,
 * connections, enemies, items, and entry/exit points.
 */
public class JsonSimpleRead {

    /**
     * Loads a mission from a JSON file, initializes its configuration, and populates the provided graph with
     * rooms, connections, enemies, items, entry/exit points, and mission target based on the JSON data.
     *
     * @param file_in_path the file path of the JSON file containing mission data
     * @param graph the graph structure representing the mission's rooms and their connections
     * @return a Mission object initialized with the configuration and data parsed from the JSON file
     * @throws IOException if there is an issue reading the JSON file
     * @throws ParseException if the JSON file content cannot be parsed
     * @throws NotElementComparableException if there is an issue adding elements to the mission or graph
     */
    public static Mission loadMissionFromJson(String file_in_path, CustomNetwork<Room> graph) throws IOException, ParseException, NotElementComparableException {

        JSONObject jsonObject = parseJsonFile(file_in_path);
        Mission missionImpl = newMission(jsonObject, graph);

        JSONArray blueprint = (JSONArray) jsonObject.get("edificio");
        addRoomsToGraph(blueprint, graph);

        JSONArray connections = (JSONArray) jsonObject.get("ligacoes");
        addConnectionsToGraph(connections, graph);

        JSONArray enemies = (JSONArray) jsonObject.get("inimigos");
        addEnemiesToMission(enemies, graph, missionImpl);


        JSONArray items = (JSONArray) jsonObject.get("itens");
        addItemsToRooms(items, graph, missionImpl);

        JSONArray entries_exits = (JSONArray) jsonObject.get("entradas-saidas");
        addEntryAndExitsPoints(entries_exits, graph, missionImpl);

        JSONObject targetJson = (JSONObject) jsonObject.get("alvo");
        setMissionTarget(targetJson, graph, missionImpl);

        return missionImpl;
    }

    /**
     * Parses a JSON file from the specified file path and returns its content as a JSONObject.
     *
     * @param file_in_path the path to the JSON file to be parsed
     * @return a JSONObject containing the parsed content of the file
     * @throws IOException if an I/O error occurs while reading the file
     * @throws ParseException if the file content cannot be parsed as a valid JSON
     */
    private static JSONObject parseJsonFile(String file_in_path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file_in_path));
        return jsonObject;
    }

    /**
     * Creates a new entities.Mission object based on the information provided in the JSON object.
     *
     * @param jsonObject the JSON object containing the mission data, expected to have keys "cod-missao" and "versao"
     * @return a new entities.Mission instance initialized with the specified code and version
     */
    private static Mission newMission(JSONObject jsonObject, CustomNetwork<Room> network) {
        String mission_code = (String) jsonObject.get("cod-missao");
        int mission_version = ((Long) jsonObject.get("versao")).intValue();
        return new Mission(mission_code, mission_version, network);
    }

    /**
     * Adds room names from a JSONArray to a graph as vertices.
     * If a room name is null, it prints a message indicating this.
     *
     * @param building a JSONArray containing room names to be added as vertices in the graph
     * @param graph a Graph where each valid room name from the JSONArray is added as a vertex
     */
    private static void addRoomsToGraph(JSONArray building, CustomNetwork<Room> graph) {
        for (Object roomObj : building) {

            String room_name = (String) roomObj;

            if (roomObj != null) {
                Room room = new Room(room_name);
                graph.addVertex(room);
            } else {
                System.err.println("Vertice null");
            }
        }
    }

    /**
     * Adds connections between rooms to a graph based on the specified JSON array.
     * Each element in the JSONArray should be a JSONArray itself, containing exactly two elements:
     * the name of the starting room and the name of the destination room.
     * These room names are used to create directed edges in the graph.
     *
     * @param connections a JSONArray where each element is a JSONArray containing two strings: the starting room and the destination room
     * @param graph a Graph to which connections from the JSONArray are added as directed edges
     */
    private static void addConnectionsToGraph(JSONArray connections, CustomNetwork<Room> graph) {
        for (Object connectionObj : connections) {
            JSONArray connectionArray = (JSONArray) connectionObj;

            String from_room_name = (String) connectionArray.get(0);
            String to_room_name = (String) connectionArray.get(1);

            Room from_room = graph.getRoom(from_room_name);
            Room to_room = graph.getRoom(to_room_name);

            if (from_room != null || to_room != null) {
                graph.addEdge(from_room, to_room, 0);
            } else {
                System.err.println("Between " + from_room_name + " and " + to_room_name + " no connection found.");
            }

        }
    }

    /**
     * Adds enemies from the JSONArray to their respective rooms in the graph.
     * For each enemy, attempts to locate the specified room in the graph and
     * add an enemy object with the given name and power to that room.
     * If a room corresponding to the enemy's location cannot be found,
     * it prints a message indicating the missing room.
     *
     * @param enemies a JSONArray containing enemy data; each element is a JSONObject
     *                with information about the enemy's name, power, and location
     * @param graph a Graph<Room> used to find and add enemies to the appropriate rooms
     */
    private static void addEnemiesToMission(JSONArray enemies, CustomNetwork<Room> graph, Mission missionImpl) throws NotElementComparableException {
        for (Object enemyObj : enemies) {
            JSONObject enemyJson = (JSONObject) enemyObj;

            String enemy_name = (String) enemyJson.get("nome");

            // int enemy_power = ((Number) enemyJson.get("poder")).intValue();
            Object power_obj = ((JSONObject) enemyObj).get("poder");
            int enemy_power =  ((Number) power_obj).intValue();

            String enemy_location = (String) enemyJson.get("divisao");

            /*
            Room enemy_location = graph.getRoom(enemy_location_name)
            if (enemy_location != null) {
                Enemy enemy_in_mission = new Enemy(enemy_name, enemy_power, enemy_location);
                enemy_location.addEnemy(enemy_in_mission);
            */

            /*
            Room room = graph.getRoom(enemy_location)
            if (enemy_location != null) {
                Enemy enemy_in_room = new Enemy(enemy_name, enemy_power, enemy_location);
                room.addEnemy(enemy_in_room);
            */

            Room room =  graph.getRoom(enemy_location);
            if (room != null) {
                Enemy enemy_in_mission = new Enemy(enemy_name, enemy_power, room);
                missionImpl.setEnemy(enemy_in_mission);
                room.addEnemy();
                room.setEnemies(true);
            } else {
                System.err.println("Room " + enemy_location + " not found for the enemy");
            }
        }
    }

    /**
     * Adds items from the JSONArray to their respective rooms in the graph.
     * For each item, this method identifies the location in the graph and
     * attempts to create and add an item object with the specified type and points.
     * If a room corresponding to the item's location cannot be found, it prints a message
     * indicating the missing room.
     *
     * @param items a JSONArray containing item data; each element is a JSONObject
     *              with information about the item's location, type, and points
     * @param graph a Graph<Room> used to find and add items to the appropriate rooms
     */
    private static void addItemsToRooms(JSONArray items, CustomNetwork<Room> graph, Mission missionImpl) throws NotElementComparableException {
        for (Object itemObj : items) {
            JSONObject itemJson = (JSONObject) itemObj;

            String item_location = (String) itemJson.get("divisao");


            // Maybe refactoring...? For modularity...
            // int item_points = getItemPoints(JSONObject itemJson);
            int item_points = 0;
            Object points_recovered_obj = itemJson.get("pontos-recuperados");
            Object points_extra_obj = itemJson.get("pontos-extra");
            if (points_recovered_obj != null) {
                item_points = ((Number) points_recovered_obj).intValue();
            } else if (points_extra_obj != null) {
                item_points = ((Number) points_extra_obj).intValue();
            }

            String item_type = (String) itemJson.get("tipo");

            Room room = graph.getRoom(item_location);

            if (room != null) {
               Item item = defineItem(room, item_points, item_type);
               missionImpl.setItem(item);
               room.addItem();
               room.setItemsInRoom(true);
            } else {
                System.err.println("Room " + item_location + " not found for the item " + item_type);
            }
            //*String item_type = (String) itemJson.get("tipo");
            if ("kit de vida".equalsIgnoreCase(item_type)) {
                Item mediKit_in_room = new MediKit("MediKit", room, item_points);
            } else if ("colete".equalsIgnoreCase(item_type)) {
                Item kevlar_in_room = new Kevlar("Kevlar", room, item_points);
            }
        }
    }

    /**
     * Defines and initializes an item within a specified room based on the item's type and points.
     *
     * @param room the Room object in which the item is to be placed.
     * @param item_points an integer representing the points or value associated with the item.
     * @param item_type a String representing the type of item to create, e.g., "kit de vida" for a MediKit or "colete" for a Kevlar.
     * @return an instance of Item based on the specified type and points if recognized; otherwise, null if the item type is unknown.
     */
    private static Item defineItem(Room room, int item_points, String item_type) {
        Item item = null;
        if ("kit de vida".equalsIgnoreCase(item_type)) {
            item = new MediKit("MediKit", room, item_points);
        } else if ("colete".equalsIgnoreCase(item_type)) {
            item = new Kevlar("Kevlar", room, item_points);
        } else {
            System.err.println("Item type is unknown.");
        }
        return item;
    }

    /**
     * Adds entry and exit points to the mission by processing the provided JSONArray.
     * Each element in the JSONArray represents a room name, and the method attempts
     * to retrieve the corresponding Room from the graph. If the room is found, it is
     * added as an entry or exit point in the mission.
     *
     * @param entries_exits a JSONArray containing room names designated as entry and exit points
     * @param graph a Graph<Room> used to retrieve Room objects for the given room names
     * @param missionImpl a entities.Mission object to which the entry and exit points are added
     */
    private static void addEntryAndExitsPoints(JSONArray entries_exits, CustomNetwork<Room> graph, Mission missionImpl) throws NotElementComparableException {
        for (Object entry_exit_obj : entries_exits) {
            String room_name = (String) entry_exit_obj;
            Room room = graph.getRoom(room_name);

            if (room != null) {
                missionImpl.setEntryExitPoint(room);
            } else {
                System.err.println("Room " + room_name + " not found as entry or exit point");
            }
        }
    }

    /**
     * Sets the target for a mission based on the provided JSON object.
     * Attempts to locate the specified target room in the graph and creates
     * a target object with the given room and type. If the room cannot be found,
     * logs an error message.
     *
     * @param targetJson a JSONObject containing the target data, expected to have keys "divisao" and "tipo"
     * @param graph a Graph<Room> used to locate the target room based on the specified room name
     * @param missionImpl the entities.Mission object in which the target is to be set
     */
    private static void setMissionTarget(JSONObject targetJson, CustomNetwork<Room> graph, Mission missionImpl) {
        String target_room = (String) targetJson.get("divisao");
        String target_type = (String) targetJson.get("tipo");

        Room room = graph.getRoom(target_room);

        if (room != null) {
            Target target = new Target(room, target_type);
            missionImpl.setTarget(target);
        } else {
            System.err.println("Room " + target_room + "not found as target room");
        }
    }
}
