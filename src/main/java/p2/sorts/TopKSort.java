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

        for (int i = 0; i < k; i++) {
            minHeap.add(array[i]);
        }

        for (int i = k; i < array.length; i++) {
            E current = array[i];
            if (comparator.compare(current, minHeap.peek()) > 0) {
                minHeap.next();
                minHeap.add(current);
            }
        }

        int heapSize = minHeap.size();
        for (int i = 0; i < heapSize; i++) {
            array[i] = minHeap.next();
        }

        if (k < array.length) {
            for (int i = heapSize; i < array.length; i++) {
                array[i] = null;
            }
        }
    }
}
