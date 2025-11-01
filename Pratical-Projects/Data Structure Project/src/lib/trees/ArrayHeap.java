package lib.trees;

import lib.exceptions.EmptyCollectionException;
import lib.interfaces.HeapADT;

public class ArrayHeap<T> extends ArrayBinaryTree<T> implements HeapADT<T> {

    public ArrayHeap() {
        super();
    }

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
     * Reorders this heap to maintain the ordering property after adding a node.
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

    @Override
    public T findMin() throws EmptyCollectionException {
        if (super.isEmpty()) {
            throw new EmptyCollectionException("Empty Heap");
        }

        return super.tree[0];
    }

    private void expandCapacity() {
        T[] temp = (T[]) new Object[super.tree.length * 2];
        for (int i = 0; i < super.tree.length; i++) {
            temp[i] = super.tree[i];
        }

        super.tree = temp;
    }

}