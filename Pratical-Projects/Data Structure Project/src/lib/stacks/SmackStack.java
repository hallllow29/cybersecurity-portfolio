package lib.stacks;

import lib.exceptions.EmptyCollectionException;
import lib.interfaces.SmackStackADT;

public class SmackStack<T> extends ArrayStack<T> implements SmackStackADT<T> {

    public SmackStack() {
        super();
    }

    @Override
    public T smack() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("stack");
        }

        T result = super.stack[0];

        for (int i = 1; i < super.top; i++) {
            this.stack[i - 1] = this.stack[i];
        }

        this.stack[--this.top] = null;

        return result;
    }
}