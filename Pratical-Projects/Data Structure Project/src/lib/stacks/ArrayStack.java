package lib.stacks;

import lib.exceptions.EmptyCollectionException;
import lib.interfaces.StackADT;

public class ArrayStack<T> implements StackADT<T> {

    private final int DEFAULT_CAPACITY = 100;
    protected int top;
    protected T[] stack;

    public ArrayStack() {
        this.top = 0;
        this.stack = (T[]) (new Object[this.DEFAULT_CAPACITY]);
    }

    public ArrayStack(int initialCapacity) {
        this.top = 0;
        this.stack = (T[]) (new Object[initialCapacity]);
    }

    @Override
    public void push(T element) {
        if (this.size() == this.stack.length) {
            expandCapacity();
        }

        this.stack[top++] = element;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        this.top--;
        T result = this.stack[this.top];
        this.stack[top] = null;

        return result;
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        return this.stack[top - 1];
    }

    @Override
    public boolean isEmpty() {
        return this.top == 0;
    }

    @Override
    public int size() {
        return this.top;
    }

    private void expandCapacity() {

        T[] tempArray = (T[]) (new Object[this.top * 2]);
        int count = 0;

        while (count < this.top) {
            tempArray[count] = this.stack[count];
            count++;
        }

        this.stack = tempArray;
    }

    @Override
    public String toString() {

        String retval = "";
        String sep = "";

        int count = this.top - 1;
        while (count >= 0) {
            retval += sep + this.stack[count].toString();
            sep = ",";
            count--;
        }

        return retval;
    }
}