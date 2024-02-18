package p2.sorts;

import datastructures.worklists.MinFourHeap;
import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }


    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        if (array == null || k <= 0) {
            throw new IllegalArgumentException();
        }

        k = Math.min(k, array.length);
        MinFourHeap<E> minHeap = new MinFourHeap<>(comparator);

        for (E element: array) {
            minHeap.add(element);
        }

        for (int i = k; i < array.length; i++) {
            minHeap.next();
            array[i] = null;
        }
        for (int i = 0; i < k; i++) {
            array[i] = minHeap.next();
        }
    }
}
