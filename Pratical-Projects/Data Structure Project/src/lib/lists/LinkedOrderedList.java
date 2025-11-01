package lib.lists;

import lib.exceptions.NotElementComparableException;
import lib.interfaces.OrderedListADT;

public class LinkedOrderedList<T> extends LinkedList<T> implements OrderedListADT<T> {

    public void add(T element) throws NotElementComparableException {
        if (element == null) {
            throw new IllegalArgumentException("LinkedOrderedList");
        }

        if (!(element instanceof Comparable)) {
            throw new NotElementComparableException("LinkedOrderedList");
        }

        Comparable<T> comparableElement = (Comparable<T>) element;

        LinearNode<T> newNode = new LinearNode<>(element);

        if (super.isEmpty()) {
            setFront(newNode);
            setRear(newNode);
        } else if (comparableElement.compareTo(super.getFront().getElement()) <= 0) {
            newNode.setNext(getFront());
            setFront(newNode);
        } else if (comparableElement.compareTo(super.getRear().getElement()) >= 0) {
            getRear().setNext(newNode);
            setRear(newNode);
        } else {
            LinearNode<T> current = this.getFront();
            LinearNode<T> previous = null;

            while (current != null && comparableElement.compareTo(current.getElement()) > 0) {
                previous = current;
                current = current.getNext();
            }
            newNode.setNext(current);
            if (previous != null) {
                previous.setNext(newNode);
            }
        }


        super.setSize(super.getSize() + 1);
        super.setModCount(super.getModCount() + 1);

    }

}
