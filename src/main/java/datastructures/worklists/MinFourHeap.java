package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int length;
    private int size;
    private Comparator<E> comparator;

    public MinFourHeap(Comparator<E> comparator) {
        this.data = (E[])new Object[10];
        this.length = 10;
        this.size = 0;
        this.comparator = comparator;
    }

    @Override
    public boolean hasWork() {
        return this.size > 0;
    }

    @Override
    public void add(E work) {
        // Resize if needed
        if (size == length) {
            E[] newArray = (E[])new Object[length * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = data[i];
            }
            this.data = newArray;
            this.length = length * 2;
        }

        data[size] = work;
        if (size != 0) {
            boolean indicator = false;
            int currIndex = size;
            // Check for swapping
            while (indicator == false) {
                if (currIndex % 4 == 0) {
                    // Case 1
                    if (comparator.compare(data[currIndex],(data[(currIndex / 4) - 1])) < 0) {
                        // Current data is less than its parent, keep swapping.
                        E currData = data[currIndex];
                        data[currIndex] = data[(currIndex / 4) - 1];
                        data[(currIndex / 4) - 1] = currData;
                        currIndex = (currIndex / 4) - 1;
                        if (currIndex == 0) {
                            indicator = true;
                        }
                    } else {
                        // Current data is greater than its parent, stop swapping.
                        indicator = true;
                    }
                } else {
                    // Case 2
                    if (comparator.compare(data[currIndex],(data[currIndex / 4])) < 0) {
                        // Current data is less than its parent, keep swapping.
                        E currData = data[currIndex];
                        data[currIndex] = data[currIndex / 4];
                        data[currIndex / 4] = currData;
                        currIndex = currIndex / 4;
                        if (currIndex == 0) {
                            indicator = true;
                        }
                    } else {
                        // Current data is greater than its parent, stop swapping.
                        indicator = true;
                    }
                }
            }
        }
        size ++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return this.data[0];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        E result = data[0];
        size --;
        if (size == 0) {
            return result;
        }
        data[0] = data[size];

        int currIndex = 0;
        while ((4 * currIndex) + 1 < size) {
            int minIndex = (4 * currIndex) + 1;
            E minData = data[minIndex];
            for (int i = 2; i <= 4; i++) {
                if (((4 * currIndex) + i) < size) {
                    // Make sure we don't check empty element
                    if (comparator.compare(minData,(data[(4 * currIndex) + i])) > 0) {
                        // Find minimum index among all possible children
                        minIndex = (4 * currIndex) + i;
                        minData = data[minIndex];
                    }
                }
            }
            if (comparator.compare(data[currIndex],(data[minIndex])) > 0) {
                // Check if the current element is indeed bigger than its min children, for edge cases
                E tempWork = data[currIndex];
                data[currIndex] = data[minIndex];
                data[minIndex] = tempWork;
                currIndex = minIndex;
            } else {
                // Current element is already smaller than all of its children
                return result;
            }
        }
        return result;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.data = (E[])new Object[10];
        this.length = 10;
        this.size = 0;
    }
}