package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private final int capacity;
    private E[] array;
    private int workSize;
    private int front;
    private int back;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.capacity = capacity;
        this.array = (E[])new Comparable[capacity];
        this.workSize = 0;
        this.front = 0;
        this.back = 0;

    }

    @Override
    public void add(E work) {
        if (isFull()) {
            throw new IllegalStateException();
        }
        array[back] = work;
        back = (back + 1) % capacity;
        workSize ++;
    }

    @Override
    public E peek() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        return array[front];
    }

    @Override
    public E peek(int i) {
        if (!hasWork()){
            throw new NoSuchElementException();
        } else if (i >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int index = (front + i) % capacity;
        return array[index];
    }

    @Override
    public E next() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        E result = array[front];
        workSize --;
        front = (front + 1) % capacity;
        return result;
    }

    @Override
    public void update(int i, E value) {
        if (!hasWork()){
            throw new NoSuchElementException();
        } else if (i >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int index = (front + i) % capacity;
        array[index] = value;
    }

    @Override
    public int size() {
        return workSize;
    }

    @Override
    public void clear() {
        this.array = (E[])new Comparable[capacity];
        this.workSize = 0;
        this.front = 0;
        this.back = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        for (int i = 0; i < Math.min(this.size(), other.size()); i++){
            if (!this.peek(i).equals(other.peek(i))) {
                return this.peek(i).compareTo(other.peek(i));
            }
        }
        return this.size() - other.size();

    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            if (this.compareTo(other) == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for (int i = 0; i < workSize; i++) {
            if (array[i] == null) {
                result = prime * result + 0;
            } else {
                result = prime * result + array[i].hashCode();
            }
        }
        return result;
    }
}
