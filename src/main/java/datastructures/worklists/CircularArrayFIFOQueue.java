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
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            // FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
