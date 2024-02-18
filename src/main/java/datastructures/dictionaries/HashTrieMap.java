package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return new HTNIterator();
        }

        private class HTNIterator extends SimpleIterator<Entry<A, HashTrieNode>> {
            private final Iterator<Item<A, HashTrieNode>> iterator;

            public HTNIterator() {
                iterator = HashTrieNode.this.pointers.iterator();
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Entry<A, HashTrieMap<A, K, V>.HashTrieNode> next() {
                Item<A, HashTrieMap<A, K, V>.HashTrieNode> item = iterator.next();
                return new AbstractMap.SimpleEntry<>(item.key, item.value);
            }
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode currNode = (HashTrieNode) this.root;
        for (A charType: key){
            if (currNode.pointers.find(charType) == null) {
                currNode.pointers.insert(charType, new HashTrieNode());
            }
            currNode = currNode.pointers.find(charType);
        }
        V result = currNode.value;
        if (result == null) {
            this.size++;
        }
        currNode.value = value;
        return result;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        if (size == 0) {
            return null;
        }

        HashTrieNode currNode = (HashTrieNode) this.root;
        for (A charType: key){
            if (currNode.pointers.find(charType) == null) {
                return null;
            }
            currNode = currNode.pointers.find(charType);
        }
        V result = currNode.value;
        return result;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        if (size == 0) {
            return false;
        }

        HashTrieNode currNode = (HashTrieNode) this.root;
        for (A charType: key){
            if (currNode.pointers.find(charType) == null) {
                return false;
            }
            currNode = currNode.pointers.find(charType);
        }
        return true;
    }

    /*
    @Override
    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        V validKey = find(key);
        if (validKey != null) {
            HashTrieNode tempNode = null;
            A tempChar = null;

            // Record the first charType for key
            int firstIndex = 0;
            A firstChar = null;

            HashTrieNode currNode = (HashTrieNode) this.root;
            for (A charType: key){
                if (firstIndex == 0) {
                    firstChar = charType;
                    firstIndex = 1;
                }
                if (currNode.value != null || currNode.pointers.size() > 1) {
                    tempNode = currNode;
                    tempChar = charType;
                }
                currNode = currNode.pointers.get(charType);
            }
            if (!currNode.pointers.isEmpty()) {
                currNode.value = null;
            } else if (tempNode == null) {
                tempNode = (HashTrieNode) this.root;
                tempNode.pointers.remove(firstChar);
            } else {
                tempNode.pointers.remove(tempChar);
            }
            this.size --;
        }
    }*/

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}