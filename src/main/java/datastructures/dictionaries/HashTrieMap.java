package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
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
            if (!currNode.pointers.containsKey(charType)) {
                currNode.pointers.put(charType, new HashTrieNode());
            }
            currNode = currNode.pointers.get(charType);
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
        HashTrieNode currNode = (HashTrieNode) this.root;
        for (A charType: key){
            if (!currNode.pointers.containsKey(charType)) {
                return null;
            }
            currNode = currNode.pointers.get(charType);
        }
        V result = currNode.value;
        return result;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode currNode = (HashTrieNode) this.root;
        for (A charType: key){
            if (!currNode.pointers.containsKey(charType)) {
                return false;
            }
            currNode = currNode.pointers.get(charType);
        }
        return true;
    }

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
    }

    @Override
    public void clear() {
        this.root = new HashTrieNode();
    }
}
