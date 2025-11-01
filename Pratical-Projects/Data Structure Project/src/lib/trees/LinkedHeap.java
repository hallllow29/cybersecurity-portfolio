package lib.trees;

import lib.HeapNode;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.HeapADT;

public class LinkedHeap<T> extends LinkedBinaryTree<T> implements HeapADT<T> {
    public HeapNode<T> lastNode;

    public LinkedHeap() {
        super();
        this.lastNode = null;
    }

    public LinkedHeap(T element) {
        super(element);
        this.lastNode = (HeapNode<T>) getRoot();
    }

    @Override
    public void addElement(T obj) {
        HeapNode<T> node = new HeapNode<T>(obj);

        if (super.getRoot() == null) {
            super.setRoot(node);
        } else {
            HeapNode<T> next_parent = getNextParentAdd();
            if (next_parent.getLeft() == null)
                next_parent.setRight(node);
            else
                next_parent.setRight(node);

            ((HeapNode<T>) node).setParent(next_parent);
        }
        lastNode = node;
        super.setCount(super.getCount() + 1);
        if (super.getCount()>1)
            heapifyAdd();
    }

    private HeapNode<T> getNextParentAdd() {
        HeapNode<T> result = lastNode;

        while ((result != getRoot() && (((HeapNode<T>) result).getParent().getLeft() != result))) {
            result = result.getParent();

        }

        if (result != super.getRoot())
            if (result.getParent().getRight() == null)
                result = result.getParent();
            else
            {
                result = (HeapNode<T>) result.getParent().getRight();
                while (result.getLeft() != null)
                    result = (HeapNode<T>)result.getLeft();
            }
        else
            while (result.getLeft() != null)
                result = (HeapNode<T>)result.getLeft();

        return result;
    }

    private void heapifyAdd() {
        T temp;
        HeapNode<T> next = lastNode;

        temp = next.getElement();

        while ((next != super.getRoot()) && (((Comparable)temp).compareTo
                (next.getParent().getElement()) < 0))
        {
            next.setElement(next.getParent().getElement());
            next = next.getParent();
        }
        next.setElement(temp);
    }

    @Override
    public T removeMin() throws EmptyCollectionException {
        if (super.isEmpty()) {
            throw new EmptyCollectionException("Linked Heap");
        }

        T minElement = super.getRootElement();

        if (getCount() == 1) {
            super.setRoot(null);
            this.lastNode = null;
        } else {
            HeapNode<T> next_last = getNewLastNode();
            if (this.lastNode.getParent().getLeft() == this.lastNode) {
                this.lastNode.getParent().setLeft(null);
            } else {
                this.lastNode.getParent().setRight(null);
            }

            getRoot().setElement(this.lastNode.getElement());
            this.lastNode = next_last;
            heapifyRemove();

        }

        super.setCount(super.getCount() + -1);

        return minElement;
    }

    @Override
    public T findMin() throws EmptyCollectionException {
        if (super.isEmpty()) {
            throw new EmptyCollectionException("LinkedHeap");
        }

        return super.getRoot().getElement();
    }

    private void heapifyRemove() {
        T temp;
        HeapNode<T> node = (HeapNode<T>) super.getRoot();
        HeapNode<T> left = (HeapNode<T>) node.getLeft();
        HeapNode<T> right = (HeapNode<T>) node.getRight();
        HeapNode<T> next;

        if ((left == null) && (right == null)) {
            next = null;
        } else if(left == null) {
            next = right;
        } else if (right == null) {
            next = left;
        } else if (((Comparable)left.getElement()).compareTo(right.getElement()) < 0) {
            next = left;
        } else {
            next = right;
        }

        temp = node.getElement();

        while((next != null) && (((Comparable)next.getElement()).compareTo(temp)) < 0 ) {
            node.setElement(next.getElement());
            node = next;
            left = (HeapNode<T>) node.getLeft();
            right = (HeapNode<T>) node.getRight();

            if ((left == null) && (right == null)) {
                next = null;
            } else if (left == null) {
                next = right;
            } else if (right == null) {
                next = left;
            } else if (((Comparable)left.getElement()).compareTo(right.getElement()) < 0) {
                next = left;
            } else {
                next = right;
            }

        }

        node.setElement(temp);
    }

    private HeapNode<T> getNewLastNode()
    {
        HeapNode<T> result = lastNode;

        while ((result != super.getRoot()) && (result.getParent().getLeft() == result))
            result = result.getParent();

        if (result != super.getRoot())
            result = (HeapNode<T>) result.getParent().getLeft();

        while ( (result).getRight() != null)
            result = (HeapNode<T>) result.getRight();

        return result;
    }

}
