package lib.interfaces;

import entities.Room;
import lib.lists.ArrayUnorderedList;

/**
 * The {@code CustomNetworkADT} interface defines the contract for a customizable
 * network structure that allows operations on a graph, where vertices and edges
 * store generic types of data. It provides methods for retrieving connected vertices,
 * accessing a specific vertex as a Room, and obtaining all vertices in the graph.
 *
 * @param <T> the type of elements stored in the network
 */
public interface CustomNetworkADT<T> {

    /**
     * Retrieves a list of vertices that are directly connected to the specified vertex in the graph.
     * A connection exists if an edge between the given vertex and another vertex has a weight
     * that is not equal to positive infinity.
     *
     * @param vertex the vertex for which connected vertices are to be retrieved
     * @return an {@code ArrayUnorderedList<T>} containing all vertices directly connected to the specified vertex
     * @throws IllegalArgumentException if the specified vertex is not found in the graph
     */
    ArrayUnorderedList<T> getConnectedVertices(T vertex);


    /**
     * Retrieves a Room object from the graph based on the specified room name.
     *
     * @param name the name of the room to retrieve
     * @return the Room object corresponding to the specified name if found, null otherwise
     */
    public Room getRoom(String name);


    /**
     * Retrieves a list of all vertices present in the graph.
     *
     * @return an {@code ArrayUnorderedList<T>} containing all vertices in the graph
     */
    public ArrayUnorderedList<T> getVertices();


}
