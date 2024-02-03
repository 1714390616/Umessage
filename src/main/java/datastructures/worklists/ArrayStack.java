package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private int size;
    private int index;
    private E[] array;

    public ArrayStack() {
        this.size = 10;
        this.index = 0;
        this.array = (E[])new Object[size];
    }

    @Override
    public void add(E work) {
        if (this.index < this.size) {
            this.array[this.index] = work;
            index ++;
        } else {
            E[] newArray = (E[])new Object[2 * this.size];
            for (int i = 0; i < this.size; i++) {
                newArray[i] = this.array[i];
            }
            size = 2 * size;
            this.array = newArray;
            this.array[this.index] = work;
            index ++;
        }

    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return this.array[index - 1];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E result = this.array[index - 1];
        this.array[index - 1] = null;
        index--;
        return result;

    }

    @Override
    public int size() {
        return this.index;
    }

    @Override
    public void clear() {
        this.array = (E[]) new Object[this.size];
        this.index = 0;
    }
}
