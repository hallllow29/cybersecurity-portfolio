/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib.lists;

import lib.interfaces.UnorderedListADT;

import java.util.NoSuchElementException;

/**
 * The ArrayUnorderedList class represents an implementation of an unordered list
 * using an array as the underlying data structure. This class extends ArrayList
 * and implements the UnorderedListADT interface.
 *
 * @param <T> the type of elements stored in this list
 */
public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    /**
     * Adds the specified element to the front of the unordered list.
     * If the underlying array has reached its capacity, the method
     * expands the array's capacity before shifting elements to make
     * space at the front.
     *
     * @param element the element to be added to the front of the list
     */
    @Override
    public void addToFront(T element) {
        if (this.count == this.list.length) {
            this.expandCapacity();
        }

        for (int i = this.count; i > 0; i--) {
            this.list[i] = this.list[i - 1];
        }

        this.list[0] = element;

        this.count++;
    }

    /**
     * Adds the specified element to the rear of the unordered list.
     * If the underlying array has reached its capacity, this method
     * expands the array's capacity before adding the element.
     *
     * @param element the element to be added to the rear of the list
     */
    @Override
    public void addToRear(T element) {
        if (this.count == this.list.length) {
            this.expandCapacity();
        }

        this.list[this.count++] = element;
    }

    /**
     * Inserts the specified element immediately after the target element in the list.
     * If the target element does not exist in the list, a {@code NoSuchElementException} is thrown.
     * If the underlying array has reached its capacity, this method expands the array's capacity
     * before shifting elements to insert the specified element.
     *
     * @param element the element to be added to the list
     * @param target the element after which the new element should be inserted
     * @throws NoSuchElementException if the target element is not found in the list
     */
    @Override
    public void addAfter(T element, T target) {
        if (this.count == this.list.length) {
            this.expandCapacity();
        }

        int index = -1;

        for (int i = 0; i < this.count; i++) {
            if (this.list[i].equals(target)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new NoSuchElementException("Element no found");
        }

        for (int j = this.count; j > index + 1; j--) {
            this.list[j] = this.list[j - 1];
        }

        this.list[index + 1] = element;

        this.count++;

    }

}
