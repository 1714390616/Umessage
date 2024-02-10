package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    private static final int maxHeightDiff = 1;

    private class AVLNode extends BSTNode {
        private int height;
        public AVLNode(K key, V value) {
            super(key, value);
            root = null;
            this.height = 0;
        }
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V result = find(key);

        this.root = insert(key, value, castBSTtoAVL(this.root));
        castBSTtoAVL(this.root).height = getHeight(castBSTtoAVL(this.root));
        if (result == null) {
            this.root = balance(castBSTtoAVL(this.root), key);
        }

        return result;
    }

    // Private pair for insert method
    private AVLNode insert(K key, V newValue, AVLNode currNode) {
        // Base case for new insertion
        if(currNode == null) {
            currNode = new AVLNode(key, newValue);
            size++;
            return currNode;
        }

        int result = key.compareTo(currNode.key);
        if (result == 0) {
            // Base case for replacement
            currNode.value = newValue;
            return currNode;
        } else if (result < 0) {
            // Trace left side
            currNode.children[0] = insert(key, newValue, castBSTtoAVL(currNode.children[0]));
        } else if (result > 0) {
            // Trace right side
            currNode.children[1] = insert(key, newValue, castBSTtoAVL(currNode.children[1]));
        }
        return currNode;
    }

    // Check the balance of the AVL Tree and rotate if needed
    private AVLNode balance(AVLNode currNode, K key) {
        if (currNode.height > 1) {
            int result = key.compareTo(currNode.key);
            // Check along the path of insertion from bottom to top
            if (result < 0 && currNode.children[0] != null) {
                currNode.children[0] = balance(castBSTtoAVL(currNode.children[0]), key);
            } else if (result > 0 && currNode.children[1] != null) {
                currNode.children[1] = balance(castBSTtoAVL(currNode.children[1]), key);
            }
            if (getHeight(castBSTtoAVL(currNode.children[1])) - getHeight(castBSTtoAVL(currNode.children[0])) > maxHeightDiff) {
                if (key.compareTo(currNode.children[1].key) > 0) {
                    // RR case, rotate left once
                    currNode = rotateLeft(currNode);
                } else {
                    // RL case, rotate right first then rotate left
                    currNode.children[1] = rotateRight(castBSTtoAVL(currNode.children[1]));
                    currNode = rotateLeft(currNode);
                }
            } else if (getHeight(castBSTtoAVL(currNode.children[0])) - getHeight(castBSTtoAVL(currNode.children[1])) > maxHeightDiff) {
                if (key.compareTo(currNode.children[0].key) < 0) {
                    // LL case, rotate right once
                    currNode = rotateRight(currNode);
                } else {
                    // LR case, rotate left first then rotate right
                    currNode.children[0] = rotateLeft(castBSTtoAVL(currNode.children[0]));
                    currNode = rotateRight(currNode);
                }
            }
        }
        return currNode;
    }

    private AVLNode rotateLeft(AVLNode currNode) {
        AVLNode oldRoot = currNode;
        AVLNode temp = null;
        if (currNode.children[1].children[0] != null) {
            temp = castBSTtoAVL(currNode.children[1].children[0]);
        }

        currNode = castBSTtoAVL(currNode.children[1]);
        oldRoot.children[1] = null;
        currNode.children[0] = oldRoot;
        currNode.children[0].children[1] = temp;
        getHeight(currNode);

        return currNode;
    }

    private AVLNode rotateRight(AVLNode currNode) {
        AVLNode oldRoot = currNode;
        AVLNode temp = null;
        if (currNode.children[0].children[1] != null) {
            temp = castBSTtoAVL(currNode.children[0].children[1]);
        }

        currNode = castBSTtoAVL(currNode.children[0]);
        oldRoot.children[0] = null;
        currNode.children[1] = oldRoot;
        currNode.children[1].children[0] = temp;
        getHeight(currNode);

        return currNode;
    }

    // Update and return the height of current node and all nodes below it
    public int getHeight(AVLNode currNode) {
        if (currNode == null) {
            // Base Case
            return -1;
        }
        currNode.height =  Math.max(getHeight(castBSTtoAVL(currNode.children[0])), getHeight(castBSTtoAVL(currNode.children[1]))) + 1;
        return currNode.height;
    }

    private AVLNode castBSTtoAVL(BSTNode currNode) {
        return (AVLNode) currNode;
    }
}

