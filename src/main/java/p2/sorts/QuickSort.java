package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        if (array == null) {
            throw new IllegalArgumentException();
        }
        quickSort(array, comparator, 0, array.length - 1);
    }

    private static <E> void quickSort(E[] array, Comparator<E> comparator, int beginIndex, int endIndex) {
        if(beginIndex < endIndex) {
            int sortedIndex = partition(array, comparator, beginIndex, endIndex);
            quickSort(array,comparator,beginIndex,sortedIndex-1);
            quickSort(array,comparator,sortedIndex+1,endIndex);
        }
    }

    private static <E> int partition(E[] array, Comparator<E> comparator, int beginIndex, int endIndex) {
        if ((comparator.compare(array[beginIndex], array[endIndex/2]) < 0 && (comparator.compare(array[endIndex/2], array[endIndex]) < 0))
                || (comparator.compare(array[beginIndex], array[endIndex/2]) > 0 && (comparator.compare(array[endIndex/2], array[endIndex]) > 0))) {
            // Make array[endIndex/2] the pivot
            swap(array, beginIndex, endIndex/2);
        } else if ((comparator.compare(array[beginIndex], array[endIndex]) < 0 && (comparator.compare(array[endIndex/2], array[endIndex]) > 0))
                || (comparator.compare(array[beginIndex], array[endIndex]) > 0 && (comparator.compare(array[endIndex/2], array[endIndex]) < 0))) {
            // Make array[endIndex] the pivot
            swap(array, beginIndex, endIndex);
        }
        int currFront = beginIndex + 1;

        while (currFront != endIndex) {
            if (comparator.compare(array[currFront], array[beginIndex]) <= 0) {
                currFront++;
            } else {
                swap(array, currFront, endIndex);
                endIndex--;
            }
        }

        if (comparator.compare(array[currFront], array[beginIndex]) < 0) {
            swap(array, beginIndex, currFront);
            return currFront;
        } else {
            swap(array, beginIndex, currFront-1);
            return  currFront-1;
        }
    }

    private static <E> void swap(E[] array, int a, int b) {
        E temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}
