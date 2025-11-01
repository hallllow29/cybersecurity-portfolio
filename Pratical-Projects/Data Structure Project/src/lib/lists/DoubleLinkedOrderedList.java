package lib.lists;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lib.DoubleNode;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.OrderedListADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The DoubleLinkedOrderedList class represents a doubly linked ordered list.
 * This list maintains elements in a sorted order as they are added.
 * It provides various operations to manipulate and access elements in the list.
 *
 * @param <T> the type of elements maintained by this list; elements must be comparable
 */
public class DoubleLinkedOrderedList<T extends Comparable<T>> implements OrderedListADT<T> {

    /**
     * The variable 'count' represents the number of elements currently stored
     * in the DoubleLinkedOrderedList. It is used to track the size of the list
     * and helps in operations such as checking if the list is empty or
     * retrieving the list's size.
     */
    private int count;
    /**
     * A reference to the first node (head) in the doubly linked ordered list.
     * It points to the starting position of the list and plays a crucial role
     * in enabling operations such as traversal, addition, and removal of elements
     * from the head of the list.
     *
     * This node serves as an entry point for the doubly linked structure,
     * holding elements in an ordered manner, based on their natural
     * or defined comparison rules.
     *
     * If the list is empty, this reference is null.
     */
    private DoubleNode<T> head;
    /**
     * A reference to the tail (last node) of the doubly linked list.
     * This node represents the ending point of the list and is used to optimize
     * operations such as adding elements at the end of the list or accessing the
     * last element in constant time.
     */
    private DoubleNode<T> tail;

    /**
     * Constructs an empty instance of a DoubleLinkedOrderedList.
     * The list is initialized with no elements, and the head and tail references are set to null.
     * The count, representing the number of elements in the list, is set to zero.
     */
    public DoubleLinkedOrderedList() {
        this.head = this.tail = null;
        this.count = 0;
    }

    /**
     * Adds the specified element to the list at its correct position in sorted order.
     * If the list is empty, the element will be added as the first node. If the element
     * is smaller than the first element, it will be added to the beginning of the list.
     * If the element is larger than the last element, it will be added to the end of the list.
     * Otherwise, the element will be inserted at the appropriate position in the middle of the list.
     *
     * @param element the element to be added to this list
     */
    @Override
    public void add(T element) {
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if (this.head == null) { // Lista vazia
            this.head = newNode;
            this.tail = newNode;
        } else {
            DoubleNode<T> current = head;

            while (current != null && current.getElement().compareTo(element) < 0) {
                current = current.getNext();
            }

            // Se for para adicionar no início da lista
            if (current == this.head) {
                newNode.setNext(this.head);
                this.head.setPrev(newNode);
                this.head = newNode;

                // Se for para adicionar no fim
            } else if (current == null) {
                this.tail.setNext(newNode);
                newNode.setPrev(this.tail);
                this.tail = newNode;

                // Se for para adicionar no meio
            } else {
                newNode.setNext(current);
                newNode.setPrev(current.getPrev());
                if (current.getPrev() != null) {
                    current.getPrev().setNext(newNode);
                }
                current.setPrev(newNode);
            }
        }

        this.count++;
    }

    /**
     * Removes and returns the first element from this doubly linked ordered list.
     *
     * This method operates in constant time, directly modifying the head reference to point
     * to the next node in the list. If the list becomes empty after the operation, the tail
     * reference is also updated to null. The count of elements in the list is decremented.
     *
     * @return the first element of type T that was removed from the list
     * @throws EmptyCollectionException if the list is empty
     */
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

    /**
     * Removes and returns the last element from this doubly linked ordered list.
     *
     * This method retrieves the last element (tail) of the list, updates the tail
     * reference to point to the previous node, and adjusts the references accordingly.
     * If the updated list becomes empty, the head reference is also set to null.
     * The count of elements in the list is decremented by one.
     *
     * @return the last element of type T that was removed from the list
     * @throws EmptyCollectionException if the list is empty
     */
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

    /**
     * Removes the specified element from the doubly linked ordered list, if it exists.
     * The element is searched for and, upon locating it, removed from the list. The
     * references of neighboring nodes are adjusted to maintain the structure of the list.
     * If the element is not found, a NoSuchElementException is thrown. If the list is
     * empty, an EmptyCollectionException is thrown.
     *
     * @param element the element to be removed from the list
     * @return the removed element of type T
     * @throws EmptyCollectionException if the list is empty
     * @throws NoSuchElementException if the specified element is not found in the list
     */
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

    /**
     * Retrieves the first element stored in the doubly linked ordered list.
     * This operation provides direct access to the element contained within
     * the head node of the list without modifying the structure of the list.
     *
     * @return the first element of type T in the list, or null if the list is empty
     */
    @Override
    public T first() {
        return this.head.getElement();
    }

    /**
     * Retrieves the last element stored in the doubly linked ordered list.
     * This operation provides direct access to the element contained within
     * the tail node of the list without modifying the structure of the list.
     *
     * @return the last element of type T in the list, or null if the list is empty
     */
    @Override
    public T last() {
        return this.tail.getElement();
    }

    /**
     * Checks if the specified element is present in the doubly linked ordered list.
     * This method traverses the list from the head node and compares each element
     * to the target until a match is found or the end of the list is reached.
     *
     * @param target the element to search for in the list
     * @return true if the target element is found in the list, false otherwise
     */
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

    /**
     * Checks if the doubly linked ordered list is empty.
     *
     * This method determines whether the list contains any elements by
     * checking if the count of elements in the list is zero.
     *
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }


    /**
     * Retrieves the number of elements currently stored in the doubly linked ordered list.
     *
     * This method provides the current count of elements in the list.
     *
     * @return the integer value representing the total number of elements in the list
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Provides an iterator to traverse the elements of the doubly linked ordered list sequentially.
     * The iterator starts at the head of the list and proceeds to the tail.
     *
     * @return an Iterator of type T for the doubly linked ordered list
     */
    @Override
    public Iterator<T> iterator() {
        return new DoubleLinkedListIterator<>(this.head);
    }

    /**
     * Reverses the order of the elements in the current doubly linked ordered list
     * and returns a new list with the reversed order.
     *
     * This method creates a new instance of DoubleLinkedOrderedList and iterates
     * through the current list backward, starting from the tail. Each element is
     * added to the new list in its reversed order using the add method.
     *
     * @return a new DoubleLinkedOrderedList containing the elements in reversed order
     */
    public DoubleLinkedOrderedList<T> reverse() {
        DoubleLinkedOrderedList<T> newList = new DoubleLinkedOrderedList<>();

        DoubleNode<T> current = this.tail;

        while (current != null) {
            newList.add(current.getElement());
            current = current.getPrev();
        }

        return newList;
    }

    /**
     * Converts the contents of the doubly linked ordered list into a string representation.
     *
     * @return a string representation of the list elements or "Lista vazia" if the list is empty
     */
    public String toString() {
        StringBuilder string = new StringBuilder();

        DoubleNode<T> current = head;

        if (current == null) {
            return "Lista vazia";
        }

        while (current != null) {
            string.append(current.getElement().toString());

            current = current.getNext();

            if (current != null) {
                string.append(" -> ");
            }
        }

        return string.toString();

    }
}
