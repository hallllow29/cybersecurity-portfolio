package lib.trees;

import lib.BinaryTreeNode;
import lib.exceptions.ElementNotFoundException;
import lib.interfaces.BinaryTreeADT;
import lib.lists.ArrayUnorderedList;

import java.util.Iterator;

public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {

    protected final int CAPACITY = 50;
    protected int count;
    protected T[] tree;

    public ArrayBinaryTree() {
        this.count = 0;
        this.tree = (T[]) new Object[this.CAPACITY];
    }

    public ArrayBinaryTree(T element) {
        this.count = 1;
        this.tree = (T[]) new Object[this.CAPACITY];
        this.tree[0] = element;
    }

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

    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inorder(0, tempList);

        return tempList.iterator();
    }

    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postOrder(0, tempList);

        return tempList.iterator();
    }

    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preOrder(0, tempList);

        return tempList.iterator();
    }

    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();

        for (int i = 0; i < this.count; i++) {
            tempList.addToRear(this.tree[i]);
        }

        return tempList.iterator();
    }

    protected void preOrder(int node, ArrayUnorderedList<T> tempList) {
        if (node < tree.length) {
            tempList.addToRear(tree[node]);
            preOrder(node * 2 + 1, tempList);
            preOrder((node + 1) * 2, tempList);
        }
    }

    protected void postOrder(int node, ArrayUnorderedList<T> tempList) {
        if (node < tree.length) {
            postOrder(node * 2 + 1, tempList);
            postOrder((node + 1) * 2, tempList);
            tempList.addToRear(tree[node]);
        }
    }

    protected void inorder(int node, ArrayUnorderedList<T> tempList) {
        if (node < tree.length) {
            inorder(node * 2 + 1, tempList);
            tempList.addToRear(tree[node]);
            inorder((node + 1) * 2, tempList);
        }
    }

    @Override
    public BinaryTreeNode<T> getRoot() {
        return null;
    }

    @Override
    public T getRootElement() {
        return this.tree[0];
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
