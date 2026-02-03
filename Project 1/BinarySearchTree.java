/*
* Connor Furby
* cfurby@wisc.edu
* CS 400, Spring 2026
* P101.BinarySearchTree
* Professor Florian Heimerl
*/

/** 
 * A Binary Search Tree implementation that uses Generic Comparable values and sorts them in order 
 * This version of a BST allows duplicate values that get inserted in left subtree of equal valued nodes
 * Each node keeps references to its parent and childrens and the operations in the class
 * use the root reference to traverse the tree to insert and search for values, 
 * as well as clearing out the tree and checking if it's empty
 * The class also includes three tester methods to ensure expected behaviors
 * 
 * @param <T> the type of Comparable data to be stored in the BST
 */
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
     * 
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

        if (newNode.getData().compareTo(subtree.getData()) <= 0) { // move left
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
     * 
     * @param find the value to check for in the collection
     * @return true if the collection contains data one or more times, 
     * and false otherwise
     */
    @Override
    public boolean contains(Comparable<T> find) {
        
        if (find == null) {
            throw new NullPointerException("The value to be found cannot be null."); // interface doesn't specify so I threw an exception to be consistent with insert method.
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
     * 
     * @return the number of values in the collection, including duplicates
     */
    @Override
    public int size() {
        return sizeHelper(root);
    }

    /**
     * Recursive method that helps with the size method and counts all of the nodes in the BST
     * 
     * @param node the current node examined
     * @return zero if node is null, adds one for every new node crossed through and recurses
     */
    private int sizeHelper(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        }

        return 1 + sizeHelper(node.getLeft()) + sizeHelper(node.getRight());
    }

    /**
     * Checks if the collection is empty.
     * 
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
        root = null;
    }



    /** TESTER METHODS **/

    /**
     * Test 1 inserts Integers into a BST and create left and right children.
     * Checks that the insert method properly inserts values and contains method properly finds them
     * Searches for a root, interior, left leaf, and right leaf node, as well as a value that shouldn't belong in the BST
     * 
     * @return true if all tests pass properly, false if not
     */
    public boolean test1() {
        boolean testsPassed = true;
        BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
        testTree.insert(10);
        testTree.insert(6);
        testTree.insert(14);
        testTree.insert(4);
        testTree.insert(8);
        testTree.insert(12);
        testTree.insert(16);

        if (testTree.size() != 7 || testTree.isEmpty()) {
            testsPassed = false;
        }

        if (!testTree.contains(10) || // root node
            !testTree.contains(6) || // interior node
            !testTree.contains(4) || // left leaf node
            !testTree.contains(16) || // right leaf node
            testTree.contains(100)) { // shouldn't be in tree
            testsPassed = false;
        }
        
        return testsPassed;
    }

    /**
     * Test 2 inserts String objects instead and includes nodes with duplicate data
     * Verifies that the duplicates are factored into the size and contains works for these values.
     * 
     * @return true if all tests pass properly, false if not
     */
    public boolean test2() {
        boolean testsPassed = true;
        BinarySearchTree<String> testTree = new BinarySearchTree<>();

        testTree.insert("n");
        testTree.insert("b");
        testTree.insert("u");
        testTree.insert("a");
        testTree.insert("f");
        testTree.insert("b"); // duplicate value
        testTree.insert("n"); // duplicate value

        if (testTree.size() != 7 ||
            !testTree.contains("n") ||
            !testTree.contains("b") ||
            !testTree.contains("a") ||
            !testTree.contains("u") ||
            !testTree.contains("f") ||
            testTree.contains("m")) {
            testsPassed = false;
            }

        return testsPassed;
    }

    /**
     * Test 3 builds up a tree, checks its size, clears the tree, checks if the tree is empty
     * Finally it rebuilds the tree to check if the tree functions have been maintained.
     * 
     * @return true if all tests pass properly, false if not
     */
    public boolean test3() {
        boolean testsPassed = true;
        BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
        
        testTree.insert(200);
        testTree.insert(100);
        testTree.insert(300);

        if (testTree.size() != 3 ||
            !testTree.contains(200) ||
            !testTree.contains(100) ||
            !testTree.contains(300)) {
            testsPassed = false;
        }

        testTree.clear();

        if (testTree.size() != 0 ||
            !testTree.isEmpty() ||
            testTree.contains(200)) {
            testsPassed = false;
        }

        testTree.insert(150);
        testTree.insert(50);
        
        if (testTree.size() != 2 ||
            !testTree.contains(150) ||
            !testTree.contains(50) ||
            testTree.contains(1)) {
            testsPassed = false;
        }

        return testsPassed;
    }

    /**
     * Runs all tester methods.
     * 
     * @param args
     */
    public static void main(String[] args) {
        BinarySearchTree<Integer> testRunner = new BinarySearchTree<>();
        System.out.println("Test 1 Results: " + testRunner.test1());
        System.out.println("Test 2 Results: " + testRunner.test2());
        System.out.println("Test 3 Results: " + testRunner.test3());

    }

}


