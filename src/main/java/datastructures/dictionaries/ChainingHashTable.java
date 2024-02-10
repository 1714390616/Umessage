package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import datastructures.worklists.ListFIFOQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * - You must implement a generic chaining hashtable. You may not
 *   restrict the size of the input domain (i.e., it must accept
 *   any key) or the number of inputs (i.e., it must grow as necessary).
 *
 * - ChainingHashTable should rehash as appropriate (use load factor as shown in lecture!).
 *
 * - ChainingHashTable must resize its capacity into prime numbers via given PRIME_SIZES list.
 *   Past this, it should continue to resize using some other mechanism (primes not necessary).
 *
 * - When implementing your iterator, you should NOT copy every item to another
 *   dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] hashTable;
    private int hashTableSize;
    private ListFIFOQueue<Integer> primeSize;

    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.primeSize = new ListFIFOQueue<>();
        for (int i = 0; i < PRIME_SIZES.length; i++) {
            primeSize.add(PRIME_SIZES[i]);
        }
        int newSize = primeSize.next();
        this.hashTableSize = newSize;
        this.hashTable = new Dictionary[newSize];
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        // Check if resize needed
        if (((double)this.size / (double)hashTableSize) > 0.7) {
            int newSize = primeSize.next();

            Dictionary<K, V>[] newHashTable = new Dictionary[newSize];
            for (int i = 0; i < hashTableSize; i++) {
                if (hashTable[i] != null) {
                    for (Item<K, V> element : hashTable[i]) {
                        int newTableIndex = Math.abs(element.key.hashCode()) % newSize;
                        if (newHashTable[newTableIndex] == null) {
                            newHashTable[newTableIndex] = newChain.get();
                        }
                        newHashTable[newTableIndex].insert(element.key, element.value);
                    }
                }
            }
            this.hashTable = newHashTable;
            this.hashTableSize = newSize;
        }

        int index = Math.abs(key.hashCode()) % hashTableSize;
        if (hashTable[index] == null) {
            hashTable[index] = newChain.get();
        }
        V result = hashTable[index].insert(key, value);
        if (result == null) {
            // Inserting a new key-value pair
            this.size++;
        }

        return result;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int index = Math.abs(key.hashCode()) % hashTableSize;

        if (hashTable[index] == null) {
            return null;
        }
        return hashTable[index].find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new CHTIterator();
    }

    private class CHTIterator implements Iterator<Item<K, V>> {
        private int nextBucketIndex = -1;
        private Iterator<Item<K, V>> nextDictIterator = null;

        public CHTIterator() {
            moveIterator();
        }

        private void moveIterator() {
            nextDictIterator = null;
            while (++nextBucketIndex < hashTable.length) {
                if (hashTable[nextBucketIndex] != null && hashTable[nextBucketIndex].iterator().hasNext()) {
                    nextDictIterator = hashTable[nextBucketIndex].iterator();
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return nextDictIterator != null;
        }

        @Override
        public Item<K, V> next() {
            if (nextDictIterator == null) {
                throw new NoSuchElementException();
            }
            Item<K, V> currentItem = nextDictIterator.next();
            if (!nextDictIterator.hasNext()) {
                moveIterator();
            }
            return currentItem;
        }
    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }
}