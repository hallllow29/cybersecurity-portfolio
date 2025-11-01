package lib;

public class DoubleNode<T> {

    /**
     * Reference to the next node
     */
    private DoubleNode<T> next;

    /**
     * Reference to the previous node
     */
    private DoubleNode<T> prev;

    /**
     * element stored at this node
     */
    private T element;

    /**
     * constructor initialized null
     */
    public DoubleNode() {
        this.next = null;
        this.element = null;
    }

    public DoubleNode(T element) {
        this.element = element;
        this.next = null;
    }

    public DoubleNode<T> getNext() {
        return this.next;
    }

    public T getElement() {
        return this.element;
    }

    public DoubleNode<T> getPrev() {
        return this.prev;
    }

    public void setNext(DoubleNode<T> next) {
        this.next = next;
    }

    public void setPrev(DoubleNode<T> prev) {
        this.prev = prev;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public String toString() {
        return ((String) this.element);
    }

}
