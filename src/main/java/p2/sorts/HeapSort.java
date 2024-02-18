package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> minHeap = new MinFourHeap<>(comparator);
        for (E element: array) {
            minHeap.add(element);
        }

        int index = 0;
        while (minHeap.hasWork()) {
            array[index] = minHeap.next();
            index++;
        }
    }
}
