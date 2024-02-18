package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private ListNode front;
    private ListNode end;
    private int size;

    public ListFIFOQueue() {
        this.front = new ListNode();
        this.end = this.front;
        this.size = 0;

    }

    @Override
    public void add(E work) {
        if (this.size == 0){
            this.front = new ListNode(work);
            end = front;
        } else {
            end.next = new ListNode(work);
            end = end.next;
        }
        this.size ++;

    }


    @Override
    public E peek() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        return front.data;
    }

    @Override
    public E next() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        E result = front.data;
        this.front = front.next;
        this.size --;
        return result;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        this.front = null;
        this.end = null;
        this.size = 0;
    }

    private class ListNode{
        private E data;
        private ListNode next;

        public ListNode() {
            this.data = null;
            this.next = null;
        }

        public ListNode(E data) {
            this.data = data;
            this.next = null;
        }
    }
}


