package lib;

public class HeapNode<T> extends BinaryTreeNode<T> {

    protected HeapNode<T> parent;
    /**
     * Creates a new heap node with the specified data.
     *
     * @param obj the data to be contained within
     * the new heap nodes
     */
    public HeapNode (T obj) {
        super(obj);
        parent = null;
    }

    public HeapNode(T element, HeapNode<T> parent) {
        super(element);
        this.parent = parent;
    }

    public HeapNode(T element, HeapNode<T> left, HeapNode<T> right, HeapNode<T> parent) {
        super(element, left, right);
    }

    public HeapNode<T> getParent() {
        return parent;
    }

    public void setParent(HeapNode<T> parent) {
        this.parent = parent;
    }
}
