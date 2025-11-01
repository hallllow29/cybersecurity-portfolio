package lib.trees;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.HeapADT;

/**
 * The {@code ArrayHeap} class implements a min-heap data structure using an array.
 * It extends the {@code ArrayBinaryTree} class and implements the {@code HeapADT} interface.
 * This class provides methods to add elements, remove the smallest element,
 * retrieve the smallest element, and maintain the heap property.
 *
 * A min-heap is a complete binary tree where the value of each node is less than
 * or equal to the values of its children. The smallest value is always located at the root.
 *
 * This implementation dynamically resizes the internal array when it becomes full and
 * guarantees logarithmic time complexity for adding and removing elements.
 *
 * The following key operations are supported:
 * - Adding an element to the heap while maintaining the min-heap property.
 * - Removing the smallest element (at the root) and reorganizing the heap.
 * - Finding the smallest element without removing it.
 */
public class ArrayHeap<T> extends ArrayBinaryTree<T> implements HeapADT<T> {

    public ArrayHeap() {
        super();
    }

    /**
     * Adds a new element to the heap. This method ensures that the heap structure
     * is maintained after the addition of the element by expanding the capacity
     * of the internal array if necessary and reorganizing the heap to uphold the
     * min-heap property.
     *
     * @param obj the element to be added to the heap
     */
    @Override
    public void addElement(T obj) {
        if (super.count == super.tree.length) {
            this.expandCapacity();
        }

        super.tree[super.count] = obj;
        super.count++;

        if (super.count > 1) {
            this.heapifyAdd();
        }
    }

    /**
     * Reorganizes the heap after adding a new element to maintain the min-heap property.
     * This method ensures that the smallest value remains at the root of the heap
     * by comparing the newly added element with its parent nodes and swapping them
     * as necessary. The process continues until the correct position for the new
     * element is found or the root is reached.
     *
     * The method operates using the following steps:
     * 1. The newly added element is stored temporarily.
     * 2. The index of the element is compared with its parent index.
     * 3. If the new element is smaller than its parent, the parent is moved down,
     *    and the process iteratively continues up the heap.
     * 4. The new element is placed in its correct position once the min-heap
     *    property is satisfied.
     *
     * This method assumes that the heap property may be violated only by the
     * newly added element and resolves the issue locally in O(log n) time.
     */
    private void heapifyAdd() {
        T temp;
        int next = super.count - 1;

        temp = super.tree[next];

        while ((next != 0) && (((Comparable) temp).compareTo(super.tree[(next - 1) / 2]) < 0)) {
            super.tree[next] = super.tree[(next - 1) / 2];
            next = (next - 1) / 2;
        }

        super.tree[next] = temp;
    }

    /**
     * Removes and returns the element with the smallest value from the heap.
     * This method throws an EmptyCollectionException if the heap is empty.
     *
     * The algorithm replaces the root (the smallest value) with the last element of
     * the heap, then reduces the size of the heap and reorders it to maintain the
     * min-heap property using the `heapifyRemove` method.
     *
     * @return the smallest element in the heap
     * @throws EmptyCollectionException if the heap is empty
     */
    @Override
    public T removeMin() throws EmptyCollectionException {
        if (super.isEmpty()) {
            throw new EmptyCollectionException("Empty Heap");
        }

        T minElement = super.tree[0];
        super.tree[0] = super.tree[super.count - 1];
        this.heapifyRemove();
        super.count--;

        return minElement;

    }

    /**
     * Reorders the heap to maintain the min-heap property after the removal
     * of the root element. This method restores the heap structure by iteratively
     * comparing the current node with its child nodes and swapping values to
     * ensure the smallest element bubbles up to the correct position.
     *
     * This implementation considers both left and right child nodes at each step
     * to determine which child has the smallest value. The comparison is performed
     * using the {@code Comparable} interface. The method assumes that the tree
     * satisfies the heap property except at the root, and it repositions elements
     * as needed to restore the overall heap order.
     *
     * The algorithm continues until the correct position for the removed element
     * is found or the bottom of the heap is reached.
     */
    private void heapifyRemove() {
        T temp;
        int node = 0;
        int left = 1;
        int right = 2;
        int next;

        if ((super.tree[left] == null) && (super.tree[right] == null)) {
            next = super.count;
        } else if (super.tree[left] == null) {
            next = right;
        } else if (super.tree[right] == null) {
            next = left;
        } else if (((Comparable) super.tree[left]).compareTo(super.tree[right]) < 0) {
            next = left;
        } else {
            next = right;
        }
        temp = super.tree[node];

        while ((next < super.count) && (((Comparable) super.tree[next]).compareTo(temp) < 0)) {
            super.tree[node] = super.tree[next];
            node = next;
            left = 2 * node + 1;
            right = 2 * (node + 1);
            if ((super.tree[left] == null) && (tree[right] == null)) {
                next = super.count;
            } else if (super.tree[left] == null) {
                next = right;
            } else if (super.tree[right] == null) {
                next = left;
            } else if (((Comparable) super.tree[left]).compareTo(super.tree[right]) < 0) {
                next = left;
            } else {
                next = right;
            }
        }

        super.tree[node] = temp;
    }

    /**
     * Retrieves the element with the smallest value from this heap without
     * removing it. If the heap is empty, this method throws an EmptyCollectionException.
     *
     * @return the element with the smallest value in the heap
     * @throws EmptyCollectionException if the heap is empty
     */
    @Override
    public T findMin() throws EmptyCollectionException {
        if (super.isEmpty()) {
            throw new EmptyCollectionException("Empty Heap");
        }

        return super.tree[0];
    }

    /**
     * Doubles the capacity of the internal array that represents the binary tree.
     * This method is called when the current array is full and additional
     * space is required to store new elements. It creates a new array with
     * double the size of the current array, copies the elements from the
     * current array to the new array, and then updates the internal reference
     * to point to the new array.
     */
    private void expandCapacity() {
        T[] temp = (T[]) new Object[super.tree.length * 2];
        for (int i = 0; i < super.tree.length; i++) {
            temp[i] = super.tree[i];
        }

        super.tree = temp;
    }

}