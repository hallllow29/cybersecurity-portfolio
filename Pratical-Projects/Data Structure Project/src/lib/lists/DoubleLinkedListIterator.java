package lib.lists;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lib.DoubleNode;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator implementation for a doubly linked list. This iterator provides
 * sequential traversal of the list elements in the order they are linked.
 *
 * @param <T> the type of elements to be iterated over
 */
public class DoubleLinkedListIterator<T> implements Iterator<T> {

    /**
     * Represents the current position of the iterator within the doubly linked list.
     * Points to the current node being accessed or traversed.
     * Initially set to the head node when the iterator is created.
     */
    private DoubleNode<T> current;

    /**
     * Constructs a new iterator for a doubly linked list, starting from the specified head node.
     *
     * @param head the starting node (head) of the doubly linked list
     */
    public DoubleLinkedListIterator(DoubleNode<T> head) {
        this.current = head;
    }

    /**
     * Checks if there are more elements to iterate over in the doubly linked list.
     *
     * @return true if the current node is not null, indicating that the iteration
     *         can continue; false otherwise.
     */
    @Override
    public boolean hasNext() {
        return current != null;
    }

    /**
     * Retrieves the next element in the iteration and advances the iterator
     * to the subsequent node in the doubly linked list.
     *
     * @return the next element of type T in the iteration
     * @throws NoSuchElementException if there are no more elements to iterate over
     */
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
