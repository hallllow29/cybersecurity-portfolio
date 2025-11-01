package lib.lists;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.UnorderedListADT;
import lib.LinearNode;

public class LinkedUnorderedList<T> extends LinkedList<T> implements UnorderedListADT<T> {

    @Override
    public void addToFront(T element) {
        if (element == null) {
            throw new IllegalArgumentException("LinkedUnoredredList");
        }

        LinearNode<T> newNode = new LinearNode<>(element);

        if (super.isEmpty()) {
            setFront(newNode);
            setRear(newNode);
        } else {
            newNode.setNext(getFront());
            setFront(newNode);
        }

        super.setSize(super.getSize() + 1);
        super.setModCount(super.getModCount() + 1);

    }

    @Override
    public void addToRear(T element) {
        if (element == null) {
            throw new IllegalArgumentException("LinkedUnorderedList");
        }

        LinearNode<T> newNode = new LinearNode<>(element);

        if (super.isEmpty()) {
            super.setFront(newNode);
            super.setRear(newNode);
        } else {
            getRear().setNext(newNode);
            setRear(newNode);
        }

        super.setSize(super.getSize() + 1);
        super.setModCount(super.getModCount() + 1);

    }

    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException, EmptyCollectionException {
            if (element == null) {
                throw new IllegalArgumentException("LinkedUnorderedList");
            }

            if (super.isEmpty()) {
                throw new EmptyCollectionException("LinkedUnorderedList");
            }

            LinearNode<T> newNode = new LinearNode<>(element);
            LinearNode<T> current = super.getFront();

            while(current != null && !current.getElement().equals(target)) {
                current = current.getNext();
            }

            if (current == null) {
                throw new IllegalArgumentException("LinkedUnorderedList");
            }

            newNode.setNext(current.getNext());
            current.setNext(newNode);

            if (current == getRear()) {
                setRear(newNode);
            }

            super.setSize(super.getSize() + 1);
            super.setModCount(super.getModCount() + 1);
    }
}
