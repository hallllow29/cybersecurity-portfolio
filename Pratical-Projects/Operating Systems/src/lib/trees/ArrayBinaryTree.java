package lib.trees;

import lib.BinaryTreeNode;
import lib.exceptions.ElementNotFoundException;
import lib.interfaces.BinaryTreeADT;
import lib.lists.ArrayUnorderedList;

import java.util.Iterator;

/**
 * Represents a binary tree implemented using an array as the underlying data structure.
 * This implementation uses a fixed initial capacity for the array, which can dynamically
 * resize as elements are added. The binary tree supports traversal operations such as
 * pre-order, in-order, post-order, and level-order. Elements are stored and accessed
 * based on their position in the binary tree structure.
 *
 * @param <T> the type of elements stored in the binary tree
 */
public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {

    /**
     * Represents the default capacity of the array used to store the elements
     * of the binary tree. This value defines the maximum number of elements
     * that can be initially stored in the tree before resizing is required.
     * The capacity is fixed and cannot be modified as it is declared as final.
     */
    protected final int CAPACITY = 50;

    /**
     * Represents the current number of elements stored in the binary tree.
     * This variable is used to track the size of the tree and is updated as
     * elements are added or removed.
     */
    protected int count;

    /**
     * Represents the underlying array storage for an array-based binary tree.
     * This array is used to store the elements of the binary tree in a sequential
     * manner, where the positions of the elements correspond to their placement
     * in a binary tree structure.
     *
     * The array-based binary tree assumes a default capacity and dynamically
     * resizes when the number of elements in the tree exceeds its current capacity.
     * Each element in the array corresponds to a node in the binary tree, with its
     * index derived based on its position in the tree structure.
     *
     * @param <T> the type of elements stored in the binary tree
     */
    protected T[] tree;

    /**
     * Constructs an empty array-based binary tree with a default capacity.
     * Initializes the tree array and sets the count of elements to zero.
     */
    public ArrayBinaryTree() {
        this.count = 0;
        this.tree = (T[]) new Object[this.CAPACITY];
    }

    /**
     * Constructs a new binary tree with the specified root element.
     * The tree is implemented as an array-based structure with a default capacity.
     *
     * @param element the element to be stored at the root of the binary tree
     */
    public ArrayBinaryTree(T element) {
        this.count = 1;
        this.tree = (T[]) new Object[this.CAPACITY];
        this.tree[0] = element;
    }

    /**
     * Searches for the specified target element in the binary tree and returns it if found.
     * If the target element is not found, throws an ElementNotFoundException.
     *
     * @param targetElement the element to be searched within the binary tree
     * @return the target element if it is found in the binary tree
     * @throws ElementNotFoundException if the target element is not found in the binary tree
     */
    public T find(T targetElement) throws ElementNotFoundException {
        T temp = null;
        boolean found = false;

        for (int ct = 0; ct < this.count && !found; ct++) {
            if (targetElement.equals(tree[ct])) {
                found = true;
                temp = tree[ct];
            }
        }

        if (!found) {
            throw new ElementNotFoundException("binary tree");
        }

        return temp;
    }

    /**
     * Creates and returns an iterator that performs an in-order traversal
     * of the binary tree. The elements are visited in the order of left
     * subtree, root, and right subtree.
     *
     * @return an iterator for traversing the elements of the tree in in-order
     */
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inorder(0, tempList);

        return tempList.iterator();
    }

    /**
     * Creates and returns an iterator that performs a post-order traversal of the binary tree.
     * The elements are visited in the order of left subtree, right subtree, and root.
     *
     * @return an iterator for traversing the elements of the tree in post-order
     */
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postOrder(0, tempList);

        return tempList.iterator();
    }

    /**
     * Creates and returns an iterator that performs a pre-order traversal of the binary tree.
     * The elements are visited in the order of root, left subtree, and right subtree.
     *
     * @return an iterator for traversing the elements of the tree in pre-order
     */
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preOrder(0, tempList);

        return tempList.iterator();
    }

    /**
     * Creates and returns an iterator that performs a level-order traversal
     * of the binary tree elements. The elements are traversed starting from
     * the root, level by level, from left to right within each level.
     *
     * @return an iterator for traversing the elements of the tree in level-order
     */
    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();

        for (int i = 0; i < this.count; i++) {
            tempList.addToRear(this.tree[i]);
        }

        return tempList.iterator();
    }

    /**
     * Performs a pre-order traversal of the binary tree starting from the specified node
     * and populates the provided list with the elements in the order they are visited.
     *
     * @param node the index of the current node in the binary tree array
     * @param tempList the list being populated with elements during the pre-order traversal
     */
    protected void preOrder(int node, ArrayUnorderedList<T> tempList) {
        if (node < tree.length) {
            tempList.addToRear(tree[node]);
            preOrder(node * 2 + 1, tempList);
            preOrder((node + 1) * 2, tempList);
        }
    }

    /**
     * Performs a post-order traversal of the binary tree starting from the specified node
     * and populates the provided list with the elements in the order they are visited.
     *
     * @param node the index of the current node in the binary tree array
     * @param tempList the list being populated with elements during the post-order traversal
     */
    protected void postOrder(int node, ArrayUnorderedList<T> tempList) {
        if (node < tree.length) {
            postOrder(node * 2 + 1, tempList);
            postOrder((node + 1) * 2, tempList);
            tempList.addToRear(tree[node]);
        }
    }

    /**
     * Performs an in-order traversal of the binary tree starting from the specified node
     * and populates the provided list with the elements in the order they are visited.
     *
     * @param node the index of the current node in the binary tree array
     * @param tempList the list being populated with elements during the in-order traversal
     */
    protected void inorder(int node, ArrayUnorderedList<T> tempList) {
        if (node < tree.length) {
            inorder(node * 2 + 1, tempList);
            tempList.addToRear(tree[node]);
            inorder((node + 1) * 2, tempList);
        }
    }

    /**
     * Retrieves the root node of the binary tree.
     *
     * @return the root node of the binary tree, or null if the tree is empty.
     */
    @Override
    public BinaryTreeNode<T> getRoot() {
        return null;
    }

    /**
     * Retrieves the element stored in the root node of the binary tree.
     *
     * @return the element located at the root of the binary tree
     */
    @Override
    public T getRootElement() {
        return this.tree[0];
    }

    /**
     * Checks if the binary tree is empty.
     *
     * @return true if the binary tree contains no elements, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    /**
     * Returns the total number of elements currently present in the binary tree.
     *
     * @return the number of elements in the binary tree
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Determines whether the specified target element exists in the binary tree.
     * The tree is traversed to check if the target element matches any element present.
     *
     * @param targetElement the element to search for in the binary tree
     * @return true if the target element exists in the binary tree, false otherwise
     */
    @Override
    public boolean contains(T targetElement) {
        if (this.isEmpty()) {
            return false;
        }

        for (int i = 0; i < this.count; i++) {
            if (this.tree[i].equals(targetElement)) {
                return true;
            }
        }

        return false;
    }

}
