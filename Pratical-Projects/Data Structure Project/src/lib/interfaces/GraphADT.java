package lib.interfaces;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;

public interface GraphADT <T> {

	/**
	 * Adds a vertex to the graph.
	 *
	 * @param vertex the vertex to be added to the graph
	 */
	public void addVertex (T vertex);

	/**
	 * Removes a specified vertex from the graph along with all edges
	 * associated with it.
	 *
	 * @param vertex the vertex to be removed from the graph
	 * @throws ElementNotFoundException if the specified vertex cannot be found in the graph
	 */
	public void removeVertex(T vertex) throws ElementNotFoundException;

	/**
	 * Adds an edge between two specified vertices in the graph.
	 *
	 * @param vertex1 the first vertex to be connected by the edge
	 * @param vertex2 the second vertex to be connected by the edge
	 */
	public void addEdge (T vertex1, T vertex2);

	/**
	 * Removes an edge between two specified vertices in the graph. If the edge does
	 * not exist, an exception is thrown.
	 *
	 * @param vertex1 the first vertex connected by the edge to be removed
	 * @param vertex2 the second vertex connected by the edge to be removed
	 * @throws ElementNotFoundException if either of the specified vertices cannot be found
	 *         in the graph or if no edge exists between the vertices
	 */
	public void removeEdge(T vertex1, T vertex2) throws ElementNotFoundException;

	/**
	 * Returns an iterator that performs a breadth-first search (BFS) starting from the specified vertex.
	 *
	 * @param startVertex the starting vertex for the BFS traversal
	 * @return an iterator for the BFS traversal of the graph
	 */
	public Iterator<T> iteratorBFS(T startVertex) throws EmptyCollectionException;

	/**
	 * Returns an iterator that performs a depth-first search (DFS) starting from the specified vertex.
	 *
	 * @param startVertex the starting vertex for the DFS traversal
	 * @return an iterator for the DFS traversal of the graph
	 */
	public Iterator iteratorDFS(T startVertex) throws EmptyCollectionException;

	/**
	 * Returns an iterator that provides the shortest path between two vertices in the graph.
	 *
	 * @param startVertex the starting vertex of the path
	 * @param targetVertex the target vertex of the path
	 * @return an iterator for the shortest path from startVertex to targetVertex in the graph
	 */
	public Iterator iteratorShortestPath(T startVertex, T targetVertex) throws ElementNotFoundException;

	/**
	 * Checks if the graph is empty.
	 *
	 * @return true if the graph contains no vertices, false otherwise
	 */
	public boolean isEmpty();

	/**
	 * Checks if the entire graph is connected. A graph is considered connected if there is a path between any two vertices.
	 *
	 * @return true if the graph is connected, false otherwise
	 */
	public boolean isConnected() throws EmptyCollectionException;

	/**
	 * Returns the number of vertices in the graph.
	 *
	 * @return the number of vertices in the graph
	 */
	public int size();

	/**
	 * Returns a string representation of the graph.
	 *
	 * @return a string description of the graph
	 */
	public String toString();

}
