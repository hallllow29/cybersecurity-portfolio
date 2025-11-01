package lib.graphs;

import entities.Room;
import lib.lists.ArrayUnorderedList;
import lib.interfaces.CustomNetworkADT;

/**
 * The {@code CustomNetwork} class represents a customizable network
 * structure that extends the functionality of a generic {@code Network}
 * class. It allows for specialized operations on graph data by implementing
 * the {@code CustomNetworkADT} interface.
 *
 * @param <T> the type of elements stored in the network
 */
public class CustomNetwork<T> extends Network<T> implements CustomNetworkADT<T> {

    /**
     * Default constructor for the CustomNetwork class.
     * Initializes a new instance of CustomNetwork by invoking the parent class's constructor.
     * The CustomNetwork class is a specialized extension of the Network class, tailored for handling custom data and connections.
     */
    public CustomNetwork() {
        super();
    }

    /**
     * Retrieves a list of vertices that are directly connected to the specified vertex in the graph.
     * A connection exists if an edge between the given vertex and another vertex has a weight
     * that is not equal to positive infinity.
     *
     * @param vertex the vertex for which connected vertices are to be retrieved
     * @return an {@code ArrayUnorderedList<T>} containing all vertices directly connected to the specified vertex
     * @throws IllegalArgumentException if the specified vertex is not found in the graph
     */
    public ArrayUnorderedList<T> getConnectedVertices(T vertex) {
        int index = super.getVertexIndex(vertex);

        if (index == -1) {
            throw new IllegalArgumentException("Graph");
        }

        ArrayUnorderedList<T> connectedVertices = new ArrayUnorderedList<>();

        for (int i = 0; i < this.numVertices; i++) {
            if (this.getWeightMatrix()[index][i] != Double.POSITIVE_INFINITY) {
                connectedVertices.addToRear(vertices[i]);
            }
        }

        return connectedVertices;
    }


    /**
     * Retrieves a Room object from the graph with the specified name.
     * The method iterates through the vertices of the graph and checks if a vertex
     * corresponds to a Room with the given name.
     *
     * @param name the name of the Room to retrieve
     * @return the Room object with the specified name, or null if no such Room is found
     */
    public Room getRoom(String name) {
        for (T vertex : vertices) {
            if (((Room) vertex).getName().equals(name)) {
                return (Room) vertex;
            }
        }
        return null;
    }


    /**
     * Retrieves a list of all vertices present in the graph.
     *
     * @return an {@code ArrayUnorderedList<T>} containing all vertices in the graph
     */
    public ArrayUnorderedList<T> getVertices() {
        ArrayUnorderedList<T> verticesList = new ArrayUnorderedList<>();
        // Object vertex;

        for (int i = 0; i < numVertices; i++) {
            // vertex = this.vertices[i];

            verticesList.addToRear(this.vertices[i]);
        }

        return verticesList;
    }

}
