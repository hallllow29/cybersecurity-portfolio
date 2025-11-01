package lib.lists;

import lib.DoubleNode;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.UnorderedListADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedUnorderedList<T extends Comparable<T>> implements UnorderedListADT<T> {

    private int count;
    private DoubleNode<T> tail;
    private DoubleNode<T> head;

    public DoubleLinkedUnorderedList() {
        this.head = this.head = null;
        this.count = 0;
    }

    @Override
    public void addToFront(T element) {
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.setNext(this.head);
            this.head.setPrev(newNode);
            this.head = newNode;

        }

        this.count++;
    }

    @Override
    public void addToRear(T element) {
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if (this.tail == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.setNext(newNode);
            newNode.setPrev(this.tail);
            this.tail = newNode;
        }

        this.count++;
    }

    @Override
    public void addAfter(T element, T target) {
        DoubleNode<T> current = this.head;

        while (current != null && !current.getElement().equals(target)) {
            current.getNext();
        }

        if (current == null) {
            throw new NoSuchElementException("Target not found");
        }

        DoubleNode<T> newNode = new DoubleNode<>(element);

        newNode.setNext(current.getNext());
        newNode.setPrev(current);

        if (current.getNext() != null) {
            current.getNext().setPrev(newNode);
        }

        current.setNext(newNode);

        this.count++;

    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        T data = this.head.getElement();

        this.head = head.getNext();

        if (this.head != null) {
            this.head.setPrev(null);
        } else {
            this.tail = null;
        }

        this.count--;

        return data;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        T data = this.tail.getElement();

        this.tail = this.tail.getPrev();

        if (this.tail != null) {
            this.tail.setNext(null);
        } else {
            this.head = null;
        }

        this.count--;

        return data;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        DoubleNode<T> current = this.head;

        while (current != null && !current.getElement().equals(element)) {
            current = current.getNext();
        }

        if (current == null) {
            throw new NoSuchElementException("Element not found");
        }

        if (current.getPrev() != null) {
            current.getPrev().setNext(current.getNext());

        } else {
            this.head = current.getNext();
        }

        if (current.getNext() != null) {
            current.getNext().setPrev(current.getPrev());
        } else {
            this.tail = current.getPrev();
        }

        this.count--;

        return current.getElement();
    }

    @Override
    public T first() {
        return this.head.getElement();
    }

    @Override
    public T last() {
        return this.tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        DoubleNode<T> current = this.head;

        while (current != null) {
            if (current.getElement().equals(target)) {
                return true;
            }

            current = current.getNext();

        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public Iterator<T> iterator() {
        return new DoubleLinkedUnorderedIterator();
    }

    private class DoubleLinkedUnorderedIterator implements Iterator<T> {

        private DoubleNode<T> current;

        public DoubleLinkedUnorderedIterator() {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("No more elements on the list");
            }

            T element = this.current.getElement();
            current = current.getNext();
            return element;
        }

    }
}
