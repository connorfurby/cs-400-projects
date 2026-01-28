
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
    
    /** Root Node of the Binary Search Tree, set to null when tree is empty */
    protected BinaryNode<T> root;

    /** 
     * Constuctor that initalizes a new BinarySearchTree object
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts a new data value into the sorted collection.
     * @param data the new value being inserted
     * @throws NullPointerException if data argument is null, we do not allow
     * null values to be stored within a SortedCollection
     */
    @Override
    public void insert(T data) throws NullPointerException {
        
        if (data == null) {
            throw new NullPointerException("Null data cannot be inserted into the BST.");
        }

        BinaryNode<T> newNode = new BinaryNode<>(data);

        if (root == null) {
            root = newNode;
            return;
        }

        insertHelper(newNode, root);
    }

    /**
     * Performs the naive binary search tree insert algorithm to recursively
     * insert the provided newNode (which has already been initialized with a
     * data value) into the provided tree/subtree. When the provided subtree
     * is null, this method does nothing. 
     */
    protected void insertHelper(BinaryNode<T> newNode, BinaryNode<T> subtree) {

        if (subtree == null) {
            return;
        }

        if (newNode.getData().compareTo(subtree.getData()) < 0) { // move left
            if (subtree.getLeft() == null) {
                subtree.setLeft(newNode);
                newNode.setUp(subtree);
            } else {
                insertHelper(newNode, subtree.getLeft()); // recurse on the left node
            }
        }
        else { // move right
            if (subtree.getRight() == null) {
                subtree.setRight(newNode);
                newNode.setUp(subtree);
            } else {
                insertHelper(newNode, subtree.getRight());
            }
        }
    }

    /**
     * Check whether data is stored in the tree.
     * @param find the value to check for in the collection
     * @return true if the collection contains data one or more times, 
     * and false otherwise
     */
    @Override
    public boolean contains(Comparable<T> find) {
        
        if (find == null) {
            return false; // interface doesn't define any exceptions to be thrown
        }

        BinaryNode<T> currNode = root;

        while (currNode != null) {
            int compareNodes = find.compareTo(currNode.getData());
            if (compareNodes < 0) { 
                currNode = currNode.getLeft();
            } else if (compareNodes > 0) {
                currNode = currNode.getRight();
            }
            else {
                return true; // found node
            }
        }

        return false; // node was not found
    }

    /**
     * Counts the number of values in the collection, with each duplicate value
     * being counted separately within the value returned.
     * @return the number of values in the collection, including duplicates
     */
    @Override
    public int size() {
        int size = 0; 
        

    }

    /**
     * Checks if the collection is empty.
     * @return true if the collection contains 0 values, false otherwise
     */
    @Override
    public boolean isEmpty() {
        if (root == null) {
            return true;
        }

        return false;
    }

    /**
     * Removes all values and duplicates from the collection.
     */
    @Override
    public void clear() {

    }

}
