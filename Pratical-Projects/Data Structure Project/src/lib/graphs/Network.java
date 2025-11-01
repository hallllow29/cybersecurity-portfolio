package lib.graphs;

import lib.lists.ArrayUnorderedList;
import lib.trees.LinkedHeap;
import lib.queues.LinkedQueue;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.NetworkADT;
import lib.stacks.LinkedStack;

import java.util.Iterator;


/**
 * Represents a weighted network (or graph) where vertices are connected by edges
 * assigned with weights. The Network class extends a graph data structure with additional
 * features to handle edge weights and supports algorithms like finding the shortest paths.
 *
 * @param <T> the type of elements stored in the network
 */
public class Network<T> extends Graph<T> implements NetworkADT<T> {

    /**
     * Represents the adjacency matrix storing edge weights of the network.
     * Each element in the matrix corresponds to the weight of the edge
     * between two vertices in the network. If no edge exists, the value is typically
     * set to a default (e.g., 0 or a representation of infinity).
     */
    private double[][] weightMatrix;

    /**
     * Constructs a new, empty Network.
     * Initializes the network with a default capacity and sets up the internal data structures,
     * including the adjacency matrix for edge weights and a vertex array for storing graph vertices.
     * Initially, the number of vertices in the network is set to zero.
     */
    public Network() {
        numVertices = 0;
        this.weightMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Retrieves the weight matrix of the network. The weight matrix is a 2-dimensional array
     * where each element represents the weight assigned to the edge between two vertices in the network.
     * A weight of positive infinity indicates no direct connection between the vertices.
     *
     * @return a 2-dimensional array of doubles representing the weight matrix of the network
     */
    public double[][] getWeightMatrix() {
        return this.weightMatrix;
    }

    /**
     * Adds an edge to the network by specifying two vertices and the weight of the edge between them.
     * The edge is bidirectional, meaning the weight is stored in both directions in the weight matrix.
     * If either vertex is invalid (not in the network), the edge will not be added.
     *
     * @param vertex1 the first vertex of the edge
     * @param vertex2 the second vertex of the edge
     * @param weight the weight of the edge to be added between vertex1 and vertex2
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        int index1 = super.getVertexIndex(vertex1);
        int index2 = super.getVertexIndex(vertex2);

        if (super.indexIsValid(index1) && super.indexIsValid(index2)) {
            this.weightMatrix[index1][index2] = weight;
            this.weightMatrix[index2][index1] = weight;
        }
    }

    /**
     * Removes the edge between two specified vertices in the network.
     * If the edge exists, the corresponding entries in the weight matrix are reset to Double.MAX_VALUE,
     * indicating no direct connection between the vertices.
     *
     * @param vertex1 the first vertex of the edge to be removed
     * @param vertex2 the second vertex of the edge to be removed
     * @throws ElementNotFoundException if either vertex1 or vertex2 is not found in the network
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) throws ElementNotFoundException {
        int index1 = super.getVertexIndex(vertex1);
        int index2 = super.getVertexIndex(vertex2);

        if (super.indexIsValid(index1) && super.indexIsValid(index2)) {
            this.weightMatrix[index1][index2] = Double.MAX_VALUE;
            this.weightMatrix[index2][index1] = Double.MAX_VALUE;
        }

    }

    /**
     * Adds a new vertex to the network. Expands the network's storage capacity if the current capacity is full.
     * Initializes the corresponding entries in the weight matrix to positive infinity, indicating no edges
     * between the new vertex and existing vertices initially.
     *
     * @param vertex the vertex to be added to the network
     * @throws IllegalArgumentException if the vertex is null
     */
    @Override
    public void addVertex(T vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Network");
        }

        if (numVertices == super.vertices.length) {
            this.expandCapacity();
        }
        this.vertices[numVertices] = vertex;

        for (int i = 0; i <= numVertices; i++) {
            this.weightMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
            this.weightMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
        }

        this.numVertices++;
    }

    /**
     * Removes a specified vertex from the network, including all its associated edges.
     * Updates the weight matrix to reflect the removal by shifting rows and columns
     * and marking the relevant entries as disconnected with a default value.
     *
     * @param vertex the vertex to be removed from the network
     * @throws ElementNotFoundException if the specified vertex is not found in the network
     */
    @Override
    public void removeVertex(T vertex) throws ElementNotFoundException {
        int index = super.getVertexIndex(vertex);
        this.removeVertex(vertex);

        if (index != -1) {
            for (int i = index; i < this.numVertices; i++) {
                System.arraycopy(this.weightMatrix[i + 1], 0, this.weightMatrix[i], 0, this.numVertices);
            }
            for (int i = 0; i < this.numVertices; i++) {
                this.weightMatrix[i][index] = Double.MAX_VALUE;
            }
        }
    }

    /**
     * Provides an iterator that traverses the shortest path between two specified vertices in the network.
     * The shortest path is determined based on the edge weights in the network.
     *
     * @param startVertex the vertex from which the traversal begins
     * @param targetVertex the vertex at which the traversal ends
     * @return an iterator over the vertices in the shortest path from startVertex to targetVertex
     * @throws ElementNotFoundException if either the startVertex or targetVertex is not found in the network
     */
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws ElementNotFoundException {
        int startIndex = getVertexIndex(startVertex);
        int targetIndex = getVertexIndex(targetVertex);

        if (startIndex == -1 || targetIndex == -1) {
            throw new ElementNotFoundException("Network");
        }

        final double INFINITO = Double.MAX_VALUE;
        double[] distancias = new double[super.numVertices];
        double[] anteriores = new double[super.numVertices];
        boolean[] visitados = new boolean[super.numVertices];

        for (int i = 0; i < super.numVertices; i++) {
            distancias[i] = INFINITO;
            anteriores[i] = -1;
            visitados[i] = false;
        }

        distancias[startIndex] = 0;

        for (int i = 0; i < super.numVertices; i++) {
            int maisProximo = -1;
            for (int j = 0; j < super.numVertices; j++) {
                if (!visitados[j] && (maisProximo == -1 || distancias[j] < distancias[maisProximo])) {
                    maisProximo = j;
                }
            }

            if (maisProximo == -1) {
                break;
            }
            visitados[maisProximo] = true;

            for (int vizinho = 0; vizinho < super.numVertices; vizinho++) {
                if (this.weightMatrix[maisProximo][vizinho] != INFINITO && !visitados[vizinho]) {
                    double novaDistancia = distancias[maisProximo] + this.weightMatrix[maisProximo][vizinho];
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
            caminho.addToFront(super.vertices[atual]);
            atual = (int) anteriores[atual];
        }

        if (distancias[targetIndex] == INFINITO) {
            return new ArrayUnorderedList<T>().iterator();
        }

        return caminho.iterator();

    }

    /**
     * Provides a depth-first traversal iterator starting from the specified vertex in the network.
     * The traversal visits vertices following the depth-first search algorithm based on the adjacency
     * information in the network's weight matrix.
     *
     * @param startVertex the starting vertex for the depth-first traversal
     * @return an iterator over the vertices visited during the depth-first traversal
     * @throws EmptyCollectionException if the collection of vertices is empty
     */
    public Iterator<T> iteratorDFS(T startVertex) throws EmptyCollectionException {
        int vertexIndex = super.getVertexIndex(startVertex);
        return iteratorDFS(vertexIndex);
    }

    /**
     * Provides a depth-first traversal iterator starting from the specified vertex index
     * in the network. The traversal visits vertices following the depth-first search
     * algorithm based on the adjacency information in the network's weight matrix.
     *
     * @param startIndex the index of the starting vertex for the depth-first traversal
     * @return an iterator over the vertices visited during the depth-first traversal
     * @throws EmptyCollectionException if the collection of vertices in the network is empty
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
                if ((this.weightMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
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
     * Provides a breadth-first traversal iterator starting from the specified vertex in the network.
     * The traversal visits vertices in a breadth-first manner based on the adjacency
     * information in the network's weight matrix.
     *
     * @param startVertex the starting vertex for the breadth-first traversal
     * @return an iterator over the vertices visited during the breadth-first traversal
     * @throws EmptyCollectionException if the collection of vertices in the network is empty
     */
    public Iterator<T> iteratorBFS(T startVertex) throws EmptyCollectionException {
        int vertexIndex = getVertexIndex(startVertex);
        return iteratorBFS(vertexIndex);
    }

    /**
     * Provides a breadth-first traversal iterator starting from the specified vertex index in the network.
     * The traversal visits vertices in a breadth-first manner based on the adjacency information
     * in the network's weight matrix. The result is returned as an iterator over the visited vertices.
     *
     * @param startIndex the index of the starting vertex for the breadth-first traversal
     * @return an iterator over the vertices visited during the breadth-first traversal
     * @throws EmptyCollectionException if the network is empty or contains no vertices
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

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);

            for (int i = 0; i < numVertices; i++) {
                if ((this.weightMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Calculates the total weight of the shortest path between two vertices in the network.
     * The shortest path is determined based on edge weights stored in the network's weight matrix.
     *
     * @param startVertex the starting vertex of the path
     * @param targetVertex the target vertex of the path
     * @return the total weight of the shortest path between the startVertex and targetVertex,
     *         or -1 if no path exists
     * @throws ElementNotFoundException if either the startVertex or targetVertex is not found in the network
     */
    @Override
    public double shortestPathWeight(T startVertex, T targetVertex) throws ElementNotFoundException {
        Iterator<T> shortPath = iteratorShortestPath(startVertex, targetVertex);

        if (!shortPath.hasNext()) {
            return -1;
        }

        double totalWeight = 0.0;
        T current = shortPath.next();

        while (shortPath.hasNext()) {
            T next = shortPath.next();
            int index1 = super.getVertexIndex(current);
            int index2 = super.getVertexIndex(next);

            totalWeight += this.weightMatrix[index1][index2];

            current = next;
        }

        return totalWeight;
    }

    /**
     * Returns a minimum spanning tree of the network.
     *
     * @return a minimum spanning tree of the network
     */
    public Network mstNetwork() throws EmptyCollectionException {
        int x, y;
        int index;
        double weight;
        int[] edge = new int[2];
        LinkedHeap<Double> minHeap = new LinkedHeap<Double>();
        Network<T> resultGraph = new Network<T>();

        if (isEmpty() || !isConnected()) {
            return resultGraph;
        }

        resultGraph.weightMatrix = new double[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++)
            for (int j = 0; j < numVertices; j++)
                resultGraph.weightMatrix[i][j] = Double.POSITIVE_INFINITY;

        resultGraph.vertices = (T[]) (new Object[numVertices]);

        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++)
            visited[i] = false;
        edge[0] = 0;
        resultGraph.vertices[0] = this.vertices[0];
        resultGraph.numVertices++;
        visited[0] = true;

        /** Add all edges, which are adjacent to the starting vertex,
         to the heap */
        for (int i = 0; i < numVertices; i++)
            minHeap.addElement((weightMatrix[0][i]));

        while ((resultGraph.size() < this.size()) && !minHeap.isEmpty()) {

            /** Get the edge with the smallest weight that has exactly
             * one vertex already in the resultGraph */
            do {
                weight = (minHeap.removeMin()).doubleValue();
                edge = getEdgeWithWeightOf(weight, visited);
            } while (!indexIsValid(edge[0]) || !indexIsValid(edge[1]));
            x = edge[0];
            y = edge[1];

            if (!visited[x])
                index = x;
            else
                index = y;
            /** Add the new edge and vertex to the resultGraph */

            resultGraph.vertices[index] = this.vertices[index];
            visited[index] = true;
            resultGraph.numVertices++;
            resultGraph.adjMatrix[x][y] = this.adjMatrix[x][y];
            resultGraph.adjMatrix[y][x] = this.adjMatrix[y][x];

            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && (this.weightMatrix[i][index] <
                        Double.POSITIVE_INFINITY)) {
                    edge[0] = index;
                    edge[1] = i;
                    minHeap.addElement(this.weightMatrix[index][i]);
                }
            }
        }
        return resultGraph;
    }

    /**
     * Retrieves a pair of vertices representing an edge with the specified weight in the network.
     * The edge is selected based on the weight matrix and the visited status of the vertices.
     * If no such edge is found, a pair of -1 values is returned.
     *
     * @param weight the weight of the edge to search for
     * @param visited an array indicating whether each vertex has been visited
     * @return an integer array of size 2 containing the indices of the vertices that form the edge
     *         with the specified weight, or {-1, -1} if no such edge is found
     */
    private int[] getEdgeWithWeightOf(double weight, boolean[] visited) {
        for (int i = 0; i < super.numVertices - 1; i++) {
            for (int j = 0; j < super.numVertices; j++) {
                if (this.weightMatrix[i][j] == weight) {
                    if ((visited[i] && !visited[j]) || (!visited[i] || visited[j])) {
                        return new int[]{i, j}; //Retorna a aresta encontrada
                    }
                }
            }
        }

        return new int[]{-1, -1}; //Nenhuma aresta encontrada
    }

    /**
     * Expands the storage capacity of the network when the current capacity is exceeded.
     * This method doubles the size of the adjacency (weight) matrix and vertex array,
     * transferring the existing data into the newly allocated storage.
     */
    private void expandCapacity() {
        int newCapacity = super.vertices.length * 2;

        double[][] newWeightedMatrix = new double[newCapacity][newCapacity];

        for (int i = 0; i < super.numVertices; i++) {
            System.arraycopy(this.weightMatrix[i], 0, newWeightedMatrix[i], 0, super.numVertices);
        }

        this.weightMatrix = newWeightedMatrix;

        T[] newVertices = (T[]) new Comparable[newCapacity];

        if (super.numVertices >= 0) {
            System.arraycopy(super.vertices, 0, newVertices, 0, super.numVertices);
        }

        super.vertices = newVertices;
    }

}

