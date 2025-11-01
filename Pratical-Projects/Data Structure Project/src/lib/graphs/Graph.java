package lib.graphs;
import lib.lists.ArrayUnorderedList;
import lib.queues.LinkedQueue;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.GraphADT;
import lib.stacks.LinkedStack;

import java.util.Iterator;

/**
 * The {@code Graph} class represents a graph data structure that can hold vertices
 * and edges. It supports operations for adding and removing vertices and edges,
 * as well as traversal methods and utility functions for graph analysis.
 * This implementation uses an adjacency matrix to represent edges and has dynamic
 * resizing capabilities for storing vertices.
 *
 * @param <T> the type of elements contained in the graph
 */
public class Graph <T> implements GraphADT<T>  {

	/**
	 * The default initial capacity for the graph's internal data structures,
	 * such as the adjacency matrix and the array of vertices. This value
	 * determines the initial number of vertices that can be accommodated
	 * without requiring the graph's capacity to be expanded.
	 */
	protected final int DEFAULT_CAPACITY = 10;

	/**
	 * Represents the number of vertices currently present in the graph.
	 * This value is incremented when a vertex is added and decremented
	 * when a vertex is removed. It provides a reference for the size of
	 * the graph and is used internally for various graph operations.
	 */
	protected int numVertices; // number of vertices in the graph

	/**
	 * The adjacency matrix representation of the graph.
	 * Each cell in the matrix represents the presence or absence of an edge
	 * between two vertices in the graph.
	 *
	 * The value at {@code adjMatrix[i][j]} is {@code true} if there is an edge
	 * from the vertex at index {@code i} to the vertex at index {@code j},
	 * and {@code false} otherwise. The diagonal elements (e.g., {@code adjMatrix[i][i]})
	 * indicate self-loops if set to {@code true}.
	 */
	protected boolean[][] adjMatrix; // adjacency matrix

	/**
	 * An array that stores the vertices of the graph. Each index of the array
	 * corresponds to a vertex in the graph, with its value representing the
	 * vertex itself. The array is dynamically managed to accommodate the
	 * graph's capacity requirements and grows when the capacity is exceeded.
	 */
	protected T[] vertices; // values of vertices

	/**
	 * Constructs an empty graph with an initial default capacity for vertices.
	 * Initializes the adjacency matrix and the array to hold vertex values.
	 */
	public Graph() {
		numVertices = 0;
		this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
		this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
	}

	/**
	 * Adds a vertex to the graph. If the graph has reached its current capacity,
	 * it automatically doubles the capacity to accommodate the new vertex. The
	 * vertex is added to the array of vertices, and the adjacency matrix is
	 * updated to include the new vertex with no edges initially.
	 *
	 * @param vertex the vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is null
	 */
	@Override
	public void addVertex(T vertex) {
		if (vertex == null) {
			throw new IllegalArgumentException("Cant be null");
		}
		if (numVertices == vertices.length)
			expandCapacity();
		vertices[numVertices] = vertex;
		for (int i = 0; i <= numVertices; i++) {
			adjMatrix[numVertices][i] = false;
			adjMatrix[i][numVertices] = false;
		}
		numVertices++;
	}


	/**
	 * Removes a specified vertex from the graph. This method updates the adjacency matrix,
	 * shifts the vertices array, and ensures the graph's structure remains consistent.
	 * If the specified vertex is not found in the graph, an ElementNotFoundException is thrown.
	 *
	 * @param vertex the vertex to be removed from the graph
	 * @throws ElementNotFoundException if the specified vertex is not found in the graph
	 */
	@Override
	public void removeVertex(T vertex) throws ElementNotFoundException {

		// Finding the index of the vertex
		int index = getVertexIndex(vertex);

		if (index == -1) {
			throw new ElementNotFoundException("Element not found");
		}

		// Shifting to the left after removing...
		for (int i = index; i < this.numVertices - 1; i++) {
			this.vertices[i] = this.vertices[i + 1];
		}

		// Shifiting rows in Adjacency matrix...
		for (int row = index; row < this.numVertices - 1; row++) {
			if (this.numVertices >= 0)
				System.arraycopy(this.adjMatrix[row + 1], 0, this.adjMatrix[row], 0, this.numVertices);
		}

		// Fix the removed column of the vertex...
		for (int col = index; col < this.numVertices - 1; col++) {
			for (int row = 0; row < this.numVertices - 1; row++) {
				this.adjMatrix[row][col] = this.adjMatrix[row][col + 1];
			}
		}

		// Last goes null
		this.vertices[numVertices - 1] = null;

		this.numVertices--;
	}


	/**
	 * Adds an edge between two specified vertices in the graph. This method determines the index
	 * of each vertex and uses the corresponding indices to establish the connection in the adjacency matrix.
	 * If either of the vertices is null, the method does not add the edge and prints an error message.
	 *
	 * @param vertex1 the first vertex to be connected by the edge
	 * @param vertex2 the second vertex to be connected by the edge
	 */
	@Override
	public void addEdge(T vertex1, T vertex2) {
		if (vertex1 == null || vertex2 == null) {
			System.out.println("Erro");
			return;
		}
		addEdge(getVertexIndex(vertex1), getVertexIndex(vertex2));
	}

	/**
	 * Adds an edge between two vertices in the graph using their indices. This method
	 * updates the adjacency matrix to indicate the presence of an edge between the
	 * specified vertices. If either of the indices is invalid, the edge is not added.
	 *
	 * @param index1 the index of the first vertex
	 * @param index2 the index of the second vertex
	 */
	public void addEdge(int index1, int index2) {
		if (indexIsValid(index1) && indexIsValid(index2)) {
			adjMatrix[index1][index2] = true;
			adjMatrix[index2][index1] = true;
		}
	}


	/**
	 * Removes an edge between two specified vertices in the graph. This method identifies
	 * the corresponding indices of the vertices in the graph, and updates the adjacency
	 * matrix to remove the edge connecting them. If either vertex is not present in the graph,
	 * an ElementNotFoundException is thrown.
	 *
	 * @param vertex1 the first vertex connected by the edge to be removed
	 * @param vertex2 the second vertex connected by the edge to be removed
	 * @throws ElementNotFoundException if either vertex1 or vertex2 is not found in the graph
	 */
	@Override
	public void removeEdge(T vertex1, T vertex2) throws ElementNotFoundException {

		int index1 = getVertexIndex(vertex1);
		int index2 = getVertexIndex(vertex2);

		if (index1 == -1) {
			throw new ElementNotFoundException("Vertex 1");
		}

		if (index2 == -1) {
			throw new ElementNotFoundException("Vertex 2");
		}

		removeEdge(getVertexIndex(vertex1), getVertexIndex(vertex2));
	}

	/**
	 * Removes an edge between two vertices in the graph using their indices. If the indices are valid,
	 * the adjacency matrix is updated to remove the connection. If either index is invalid, the method
	 * does nothing.
	 *
	 * @param index1 the index of the first vertex
	 * @param index2 the index of the second vertex
	 */
	public void removeEdge(int index1, int index2) {
		if (indexIsValid(index1) && indexIsValid(index2)) {
			adjMatrix[index1][index2] = false;
			adjMatrix[index2][index1] = false;
		}
	}

	/**
	 * Returns an iterator that performs a breadth-first search (BFS) traversal
	 * starting from the specified vertex.
	 *
	 * @param startVertex the vertex to begin the BFS traversal from
	 * @return an iterator that performs a breadth-first traversal starting at the given vertex
	 * @throws EmptyCollectionException if the graph is empty or the traversal cannot be performed
	 */
	public Iterator<T> iteratorBFS(T startVertex) throws EmptyCollectionException {
		int vertexIndex = getVertexIndex(startVertex);
		return iteratorBFS(vertexIndex);
	}

	/**
	 * Returns an iterator that performs a depth-first search (DFS) traversal
	 * starting from the specified vertex. This method determines the index
	 * of the start vertex and delegates the DFS traversal to the overloaded
	 * method that operates on indices.
	 *
	 * @param startVertex the vertex from which to begin the DFS traversal
	 * @return an iterator that performs a depth-first traversal starting at the given vertex
	 * @throws EmptyCollectionException if the graph is empty or the traversal cannot be performed
	 */
	@Override
	public Iterator<T> iteratorDFS(T startVertex) throws EmptyCollectionException {
		int vertexIndex = getVertexIndex(startVertex);
		return iteratorDFS(vertexIndex);
	}

	/**
	 * Returns an iterator that performs a breadth first search
	 * traversal starting at the given index.
	 *
	 * @param startIndex the index to begin the search from
	 * @return an iterator that performs a breadth first traversal
	 */
	public Iterator<T> iteratorBFS(int startIndex) throws EmptyCollectionException {
		Integer x;
		LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
		ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

		if (!indexIsValid(startIndex)) {
			return resultList.iterator();
		}

		boolean[] visited = new boolean[numVertices];

		for (int i = 0; i < numVertices; i++) {
			visited[i] = false;
		}

		traversalQueue.enqueue(Integer.valueOf(startIndex));
		visited[startIndex] = true;

		while (!traversalQueue.isEmpty()) {
			x = traversalQueue.dequeue();
			resultList.addToRear(vertices[x.intValue()]);

			/** Find all vertices adjacent to x that have
			 not been visited and queue them up */
			for (int i = 0; i < numVertices; i++) {
				if (adjMatrix[x.intValue()][i] && !visited[i]) {
					traversalQueue.enqueue(Integer.valueOf(i));
					visited[i] = true;
				}
			}
		}
		return resultList.iterator();
	}

	/**
	 * Returns an iterator that performs a depth first search
	 * traversal starting at the given index.
	 *
	 * @param startIndex the index to begin the search traversal from
	 * @return an iterator that performs a depth first traversal
	 */
	public Iterator<T> iteratorDFS(int startIndex) throws EmptyCollectionException {
		Integer x;
		boolean found;
		LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
		ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
		boolean[] visited = new boolean[numVertices];

		if (!indexIsValid(startIndex)) {
			return resultList.iterator();
		}

		for (int i = 0; i < numVertices; i++) {
			visited[i] = false;
		}

		traversalStack.push(startIndex);
		resultList.addToRear(vertices[startIndex]);
		visited[startIndex] = true;

		while (!traversalStack.isEmpty()) {
			x = traversalStack.peek();
			found = false;

			for (int i = 0; (i < numVertices) && !found; i++) {
				if (adjMatrix[x][i] && !visited[i]) {
					traversalStack.push(i);
					resultList.addToRear(vertices[i]);
					visited[i] = true;
					found = true;
				}
			}
			if (!found && !traversalStack.isEmpty()) {
				traversalStack.pop();
			}
		}
		return resultList.iterator();
	}

	/**
	 * Returns an iterator over the shortest path between two vertices in the graph.
	 * The method applies a modified Dijkstra's algorithm to compute the shortest path
	 * in an unweighted graph and constructs the path if it exists. If either the start
	 * or target vertex is not found, an {@code ElementNotFoundException} is thrown.
	 *
	 * @param startVertex the vertex where the shortest path starts
	 * @param targetVertex the vertex where the shortest path ends
	 * @return an iterator over the vertices in the shortest path from startVertex to targetVertex,
	 *         or an empty iterator if no path exists
	 * @throws ElementNotFoundException if either startVertex or targetVertex is not found in the graph
	 */
	@Override
	public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws ElementNotFoundException {
		int startIndex = getVertexIndex(startVertex);
		int targetIndex = getVertexIndex(targetVertex);

		if (startIndex == -1 || targetIndex == -1) {
			throw new ElementNotFoundException("Um dos vértices não foi encontrado.");
		}

		final int INFINITO = Integer.MAX_VALUE;
		int[] distancias = new int[numVertices];
		int[] anteriores = new int[numVertices];
		boolean[] visitados = new boolean[numVertices];

		for (int i = 0; i < numVertices; i++) {
			distancias[i] = INFINITO;
			anteriores[i] = -1;
			visitados[i] = false;
		}
		distancias[startIndex] = 0;

		for (int i = 0; i < numVertices; i++) {
			int maisProximo = -1;
			for (int j = 0; j < numVertices; j++) {
				if (!visitados[j] && (maisProximo == -1 || distancias[j] < distancias[maisProximo])) {
					maisProximo = j;
				}
			}

			if (maisProximo == -1) break; //
			visitados[maisProximo] = true;

			for (int vizinho = 0; vizinho < numVertices; vizinho++) {
				if (adjMatrix[maisProximo][vizinho] && !visitados[vizinho]) {
					int novaDistancia = distancias[maisProximo] + 1;
					if (novaDistancia < distancias[vizinho]) {
						distancias[vizinho] = novaDistancia;
						anteriores[vizinho] = maisProximo;
					}
				}
			}
		}

		ArrayUnorderedList<T> caminho = new ArrayUnorderedList<>();
		int atual = targetIndex;
		while (atual != -1) {
			caminho.addToFront(vertices[atual]);
			atual = anteriores[atual];
		}

		if (distancias[targetIndex] == INFINITO) {
			return new ArrayUnorderedList<T>().iterator();
		}

		return caminho.iterator();
	}

	/**
	 * Determines whether the graph is empty.
	 *
	 * @return true if the graph contains no vertices, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return numVertices == 0;
	}

	/**
	 * Determines whether the graph is connected. A graph is considered connected if all vertices
	 * are reachable from any single vertex, meaning there exists a path between every pair of vertices.
	 * If the graph contains no vertices, it is deemed not connected.
	 *
	 * @return true if the graph is connected, false otherwise
	 * @throws EmptyCollectionException if the graph is empty or traversal cannot be performed
	 */
	@Override
	public boolean isConnected() throws EmptyCollectionException {
		if (numVertices == 0) {
			return false;
		}

		Iterator<T> it = iteratorBFS(0);
		int count = 0;

		while (it.hasNext()) {
			it.next();
			count++;
		}

		return (count == numVertices);

	}

	/**
	 * Returns the number of vertices in the graph.
	 *
	 * @return the total number of vertices currently present in the graph
	 */
	@Override
	public int size() {
		return numVertices;
	}

	/**
	 * Doubles the capacity of the graph's internal storage structures, including
	 * the adjacency matrix and the array of vertices, to accommodate additional vertices.
	 * This method is invoked automatically when the current capacity is exceeded.

	 * The array of vertices is expanded by creating a new array with double the
	 * length of the current one. All existing vertices are copied into the new array.
	 */
	private void expandCapacity() {
		int newCapacity = vertices.length * 2;

		boolean[][] newAdjMatrix = new boolean[newCapacity][newCapacity];

		for (int i = 0; i < numVertices; i++) {
			System.arraycopy(adjMatrix[i], 0, newAdjMatrix[i], 0, numVertices);
		}

		adjMatrix = newAdjMatrix;

		T[] newVertices = (T[]) new Object[newCapacity];

		if (numVertices >= 0) System.arraycopy(vertices, 0, newVertices, 0, numVertices);

		vertices = newVertices;

	}


	/**
	 * Checks if the given index corresponds to a valid vertex in the graph.
	 *
	 * @param index the index to be checked
	 * @return true if the index is within the range of existing vertices (i.e., 0 <= index < numVertices), false otherwise
	 */
	protected boolean indexIsValid(int index) {
		return ((index < numVertices) && (index >= 0));
	}


	/**
	 * Retrieves the index of the specified vertex within the list of vertices in the graph.
	 * If the vertex is not found, the method returns -1.
	 *
	 * @param vertex the vertex to locate in the graph
	 * @return the index of the specified vertex, or -1 if the vertex is not found
	 */
	protected int getVertexIndex(T vertex) {
		for (int i = 0; i < numVertices; i++) {
			if (vertices[i].equals(vertex)) {
				return i;
			}
		}
		return -1;
	}

}
