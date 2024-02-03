package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import datastructures.worklists.ListFIFOQueue;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.List;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private ListNode front;
    public MoveToFrontList() {
        front = null;
    }

    @Override
    public V insert(K key, V value) {
        if (front == null) {
            // The list is empty
            front = new ListNode(key, value);
            size ++;
            return null;
        } else {
            if (find(key) != null) {
                // key already exists
                V result = front.data.value;
                front.data.value = value;
                return result;
            } else {
                ListNode temp = new ListNode(key, value);
                temp.next = front;
                front = temp;
                size ++;
                return null;
            }
        }
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        // Check first ListNode
        if (front == null) {
            return null;
        } else if (front.data.key.equals(key)){
            return front.data.value;
        }

        ListNode curr = front;
        while (curr.next != null) {
            if (curr.next.data.key.equals(key)){
                if (curr.next.next == null) {
                    ListNode temp = curr.next;
                    curr.next = null;
                    temp.next = front;
                    front = temp;
                } else {
                    ListNode temp = curr.next;
                    curr.next = curr.next.next;
                    temp.next = front;
                    front = temp;
                }
                return front.data.value;
            }
            curr = curr.next;
        }
        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new s1mpleIterator();
    }

    private class s1mpleIterator extends SimpleIterator<Item<K, V>> {
        public ListNode curr;

        public s1mpleIterator() {
            if (MoveToFrontList.this.front != null) {
                this.curr = MoveToFrontList.this.front;
            }
        }

        @Override
        public Item<K, V> next() {
            if (curr == null) {
                return null;
            }
            Item<K, V> result = curr.data;
            curr = curr.next;
            return result;
        }

        @Override
        public boolean hasNext(){
            if (curr.next != null) {
                return true;
            } else {
                return false;
            }
        }

    }

    private class ListNode{
        private Item<K, V> data;
        private ListNode next;

        public ListNode() {
            this.data = null;
            this.next = null;
        }
        public ListNode(K key, V value) {
            this.data = new Item(key, value);
            this.next = null;
        }
    }
}