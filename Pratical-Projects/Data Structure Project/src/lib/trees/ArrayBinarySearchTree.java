package lib.trees;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.BinarySearchTreeADT;
import lib.lists.ArrayUnorderedList;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArrayBinarySearchTree<T> extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {

    protected int height;
    protected int maxIndex;

    public ArrayBinarySearchTree() {
        super();
        this.height = 0;
        this.maxIndex = -1;
    }

    @Override
    public void addElement(T element) {
        if (this.tree.length < maxIndex * 2 + 3) {
            this.expandCapacity();
        }

        Comparable<T> tempelement = (Comparable<T>) element;

        if (this.isEmpty()) {
            this.tree[0] = element;
            this.maxIndex = 0;
        } else {
            boolean added = false;
            int currentIndex = 0;
            while (!added) {
                if (tempelement.compareTo((this.tree[currentIndex])) < 0) {
                    /**
                     * go left
                     */
                    if (this.tree[currentIndex * 2 + 1] == null) {
                        this.tree[currentIndex * 2 + 1] = element;
                        added = true;
                        if (currentIndex * 2 + 1 > this.maxIndex) {
                            this.maxIndex = currentIndex * 2 + 1;
                        }
                    } else {
                        currentIndex = currentIndex * 2 + 1;
                    }
                } else {
                    /**
                     * Go right
                     */
                    if (super.tree[currentIndex * 2 + 2] == null) {
                        super.tree[currentIndex * 2 + 2] = element;
                        added = true;
                        if (currentIndex * 2 + 2 > this.maxIndex) {
                            this.maxIndex = currentIndex * 2 + 2;
                        }
                    } else {
                        currentIndex = currentIndex * 2 + 2;
                    }
                }
            }
        }

        this.height = (int) (Math.log(this.maxIndex + 1) / Math.log(2)) + 1;
        super.count++;
    }

    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        if (isEmpty()) {
            throw new ElementNotFoundException("binary search tree");
        }

        Comparable<T> tempElement = (Comparable<T>) targetElement;

        int targetIndex = findIndex(tempElement, 0);

        T result = tree[targetIndex];
        try {
            replace(targetIndex);
        } catch (EmptyCollectionException ex) {
            Logger.getLogger(ArrayBinarySearchTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        count--;

        int temp = maxIndex;
        maxIndex = -1;
        for (int i = 0; i <= temp; i++) {
            if (tree[i] != null) {
                maxIndex = i;
            }
        }

        height = (int) (Math.log(maxIndex + 1) / Math.log(2)) + 1;

        return result;
    }

    /**
     * Removes the node specified for removal and shifts the tree array
     * accordingly.
     *
     * @param targetIndex the node to be removed
     */
    protected void replace(int targetIndex) throws EmptyCollectionException {
        int currentIndex, parentIndex, temp, oldIndex, newIndex;
        ArrayUnorderedList<Integer> oldlist = new ArrayUnorderedList<>();
        ArrayUnorderedList<Integer> newlist = new ArrayUnorderedList<>();
        ArrayUnorderedList<Integer> templist = new ArrayUnorderedList<>();
        Iterator<Integer> oldIt, newIt;

        /**
         * if target node has no children
         */
        if ((targetIndex * 2 + 1 >= tree.length) || (targetIndex * 2 + 2 >= tree.length)) {
            tree[targetIndex] = null;
        } /**
         * if target node has no children
         */
        else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] == null)) {
            tree[targetIndex] = null;
        } /**
         * if target node only has a left child
         */
        else if ((tree[targetIndex * 2 + 1] != null) && (tree[targetIndex * 2 + 2] == null)) {
            /**
             * fill newlist with indices of nodes that will replace the
             * corresponding indices in oldlist
             */
            currentIndex = targetIndex * 2 + 1;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                newlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * fill oldlist
             */
            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                oldlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * do replacement
             */
            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();
                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } /**
         * if target node only has a right child
         */
        else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] != null)) {
            /**
             * fill newlist with indices of nodes that will replace the
             * corresponding indices in oldlist
             */
            currentIndex = targetIndex * 2 + 2;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                newlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * fill oldlist
             */
            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                oldlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * do replacement
             */
            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();

                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } /**
         * if target node has two children
         */
        else {
            currentIndex = targetIndex * 2 + 2;

            while (tree[currentIndex * 2 + 1] != null) {
                currentIndex = currentIndex * 2 + 1;
            }

            tree[targetIndex] = tree[currentIndex];

            /**
             * the index of the root of the subtree to be replaced
             */
            int currentRoot = currentIndex;

            /**
             * if currentIndex has a right child
             */
            if (tree[currentRoot * 2 + 2] != null) {
                /**
                 * fill newlist with indices of nodes that will replace the
                 * corresponding indices in oldlist
                 */
                currentIndex = currentRoot * 2 + 2;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    currentIndex = (templist.removeFirst());
                    newlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                }

                /**
                 * fill oldlist
                 */
                currentIndex = currentRoot;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    currentIndex = (templist.removeFirst());
                    oldlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                }

                /**
                 * do replacement
                 */
                oldIt = oldlist.iterator();
                newIt = newlist.iterator();
                while (newIt.hasNext()) {
                    oldIndex = oldIt.next();
                    newIndex = newIt.next();

                    tree[oldIndex] = tree[newIndex];
                    tree[newIndex] = null;
                }
            } else {
                tree[currentRoot] = null;
            }
        }
    }

    @Override
    public void removeAllOccurrences(T targetElement) throws EmptyCollectionException {
        if (super.isEmpty()) {
            throw new EmptyCollectionException("binary search tree");
        }

        boolean found = true;

        while (found) {
            try {
                this.removeElement(targetElement);
            } catch (ElementNotFoundException ex) {
                found = false;
            }
        }
    }

    @Override
    public T removeMin() throws EmptyCollectionException {
        if (super.isEmpty()) {
            throw new EmptyCollectionException("binary search tree");
        }

        T min = this.findMin();

        try {
            this.removeElement(min);
        } catch (ElementNotFoundException ex) {
            Logger.getLogger(ArrayBinarySearchTree.class.getName()).log(Level.SEVERE, null, ex);
        }

        return min;
    }

    @Override
    public T removeMax() throws EmptyCollectionException, ElementNotFoundException {
        if (super.isEmpty()) {
            throw new EmptyCollectionException("binary search tree");
        }
        return this.removeElement(findMax());
    }

    @Override
    public T findMin() throws EmptyCollectionException {
        if (super.isEmpty()) {
            throw new EmptyCollectionException("binary search tree");
        }

        T current = super.tree[0];
        int index = 0;

        while (index < super.tree.length && super.tree[index] != null) {
            current = super.tree[index];
            index = 2 * index + 1;

        }

        return current;
    }

    @Override
    public T findMax() throws EmptyCollectionException {
        if (super.isEmpty()) {
            throw new EmptyCollectionException("binary search tree");
        }

        T current = super.tree[0];
        int index = 0;

        while (index < super.tree.length && super.tree[index] != null) {
            current = super.tree[index];
            index = 2 * index + 2;
        }

        return current;
    }

    private void expandCapacity() {
        T[] newTree = (T[]) (new Object[tree.length * 2]);
        for (int i = 0; i < tree.length; i++) {
            newTree[i] = tree[i];
        }
        tree = newTree;
    }

    private int findIndex(Comparable<T> element, int index) {
        if (index >= super.tree.length || super.tree[index] == null) {
            return -1;
        }

        if (element.compareTo(super.tree[index]) == 0) {
            return index;
        } else if (element.compareTo(super.tree[index]) < 0) {
            return this.findIndex(element, 2 * index + 1); //Filho esquerdo
        } else {
            return this.findIndex(element, 2 * index + 2); //Filho direito
        }
    }

}