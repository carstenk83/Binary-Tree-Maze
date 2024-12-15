package project5;

import java.util.Iterator;
import java.util.Arrays;
import java.util.Stack;
import java.util.NoSuchElementException;

/**
* BST class with code used from Lab 10
* 
* @author Joanna Klukowska
* @author Carsten Kaiser
*/ 
public class BST<E extends Comparable<E>> implements Iterable<E>{
    //Intentionally protected to guarantee access from any inheriting class 
    protected Node root;   //reference to the root node of the tree 
    protected int size;    
    
    /**
    * Constructs a new empty tree sorted according to the natural ordering of its elements
    */
    public BST() {
        root = null;
        size = 0;
    }

    /**
    * Constructs a new tree containing the elements in the specified collection, 
    * sorted according to the natural ordering of its elements.
    * 
    * @param collection the collection whose elements will comprise the new tree
    * @throws NullPointerException if the specified collection is null
    */
    public BST(E[] collection) {
        if (collection == null) {
            throw new NullPointerException("Collection cannot be null");
        }

        Arrays.sort(collection);

        root = buildBalancedTree(collection, 0, collection.length - 1);
    }

    /**
    * Builds a balanced binary search tree from a sorted collection
    * 
    * @param collection the sorted collection of elements
    * @param left the starting index of the collection
    * @param right the ending index of the collection
    * @return the root node of the balanced tree
    */
    private Node buildBalancedTree(E[] collection, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = (left + right) / 2;
        Node node = new Node(collection[mid]);

        node.left = buildBalancedTree(collection, left, mid - 1);
        node.right = buildBalancedTree(collection, mid + 1, right);

        return node;
    }

    /**
    * Adds the specified element to this tree if it is not already present
    * If this tree already contains the element, the call leaves the 
    * tree unchanged and returns false
    *
    * @param data element to be added to this tree 
    * @return true if this tree did not already contain the specified element
    */
    public boolean add ( E data ) { 
        if(data == null){
            throw new NullPointerException("Specified element can not be null");
        }

        if (root == null ) {// create the first node 
            root = new Node (data);
            size++;
            return true;
        }

        Node current = root;
        while (current != null ) {
            int compare = current.data.compareTo(data);
            if (compare > 0) { //add in the left subtree
                if (current.left == null ) {
                    current.left = new Node (data);
                    size++;
                    updateHeight(current);
                    return true; 
                }
                else {
                    current = current.left; 
                }
            } else if (compare < 0 ) {//add in the right subtree
                if (current.right == null ) {
                    current.right = new Node (data);
                    size++;
                    updateHeight(current);
                    return true;
                }
                else {
                    current = current.right; 
                }
            } else { //duplicate 
                return false; 
            }
        }

        //we should never get to this line 
        return false; 
    }

    /**
    * Updates the height of all nodes affected by an insertion or deletion
    * Propagates the height adjustment upward from the specified node
    * 
    * @param node the node at which to start updating heights
    */
    private void updateHeight(Node node) {
        while (node != null) {
            int leftHeight = 0;
            int rightHeight = 0;

            if (node.left != null) {
                leftHeight = node.left.height;
            }

           
            if (node.right != null) {
                rightHeight = node.right.height;
            }

            
            if (leftHeight > rightHeight) {
                node.height = leftHeight + 1;
            } else {
                node.height = rightHeight + 1;
            }

            
            node = getParent(node);
        }
    }

    /**
    * Iterates through tree to parent node of the paramater node
    *
    * @param node node to get parent from
    * @return parent parent node 
    */
    public Node getParent(Node child){
        if(root == null || root == child){
            return null;
        }

        Node current = root;
        while(current != null){
            if(current.left == child || current.right == child){
                return current;

            }

            int compare = current.data.compareTo(child.data);
            if(compare > 0){
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    /**
    * Returns the least element in this tree greater than or equal 
    * to the given element, or null if there is no such element
    *
    * @param data the value to match
    * @throws NullPointerException if specefied element is null
    */
    public E ceiling(E data){
        if(data == null){
            throw new NullPointerException("Data can not equal null");
        }

        Node current = root;
        Node ceilingNode = null; 
        while(current != null){
            int compare = current.data.compareTo(data);
            if(compare == 0){
                return current.data;
            } else if(compare > 0){
                ceilingNode = current;
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if(ceilingNode != null){
            return ceilingNode.data;
        } else {
            return null;
        }
    }

    /**
    * Removes all of the elements from this set, leaving it empty
    */
    public void clear(){
        root = null;
        size = 0;
    }

    /**
    * Returns true if this set contains the specified element
    * 
    * @param object to be checked for containment in this set
    * @return true if this set contains the specified element, false if not
    * @throws NullPointerException if the specified element is null
    */
    public boolean contains(Object o){
        if(o == null){
            throw new NullPointerException("Data can not be null");
        }

        E data = (E) o;

        Node current = root; 
        while(current != null){
            int compare = current.data.compareTo(data);

            if(compare == 0){
                return true;
            } else if(compare > 0){
                current = current.left;
            } else if(compare < 0){
                current = current.right;
            }
        }

        return false;
    }
    
    /**
    * Compares the specified object with this tree for equality 
    * Returns true if the given object is also a tree, the two trees have 
    * the same size, and every member of the given tree is contained in this tree
    *
    * @param obj object to be compared for equality with this tree
    * @return true obj is equal to this tree, false if not
    */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof BST<?>)){
            return false;
        }

        BST<E> other = (BST<E>) obj;

        if(this.size != other.size){
            return false;
        }
        
        Iterator<E> otherIterator = other.iterator();
        Iterator<E> thisIterator = this.iterator();
        
        while(thisIterator.hasNext() && otherIterator.hasNext()){
            E thisElement = thisIterator.next();
            E otherElement = otherIterator.next();

            if (thisElement == null || otherElement == null){
                if (thisElement != otherElement){
                    return false;
                }
            } else if (!thisElement.equals(otherElement)){
                return false; 
            }
        }

        return true;
    }

    /**
    * Returns the first (lowest) element currently in this tree
    *
    * @return current.data first (lowest) element currently in this tree
    * @throws NoSuchElementException if this set is empty
    */
    public E first(){
        if(root == null){
            throw new NoSuchElementException("This set is empty");
        }

        Node current = root;
        while(current.left != null){
            current = current.left;
        }
        
        return current.data;
    }

   /**
   * Returns the greatest element in this set less than or 
   * equal to the given element, or null if there is no such element
   *
   * @param data the value to match
   * @return floor.data the greatest element less than or equal to e, or null if there is no such element
   * @throws NullPointerException if the specified element is null
   */
    public E floor(E data){
        if(data == null){
            throw new NullPointerException("Value can not be null");
        }

        if(root == null){
            return null;
        }

        Node current = root;
        Node floor = null;
        while(current != null){
            int compare = ((Comparable<E>) data).compareTo(current.data);

            if(compare == 0) {
                return current.data;
            } else if(compare < 0){
                current = current.left;
            } else{
                floor = current;
                current = current.right;
            }
        }

        if (floor != null){
            return floor.data;
        } else{
            return null;
        }
    }

    /**
    * Returns the element at the specified position in this tree
    *
    * @param index index of the element to return
    * @return currentElement the element at the specified position in this tree
    * @throws IndexOutOfBoundsException if the index is out of range
    */
    public E get(int index){
        if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException("This index is out of rage");
        }

        Iterator<E> iterator = this.iterator();
        int currentIndex = 0;

        while(iterator.hasNext()){
            E currentElement = iterator.next();

            if(currentIndex == index){
                return currentElement;
            }
            currentIndex++;
        }
        
        return null;
    }

    /**
    * Returns the height of this tree. The height of a leaf is 1. 
    * The height of the tree is the height of its root node.
    *
    * @return root.height the height of this tree or zero if the tree is empty
    */
    public int height(){
        if(root == null){
            return 0;
        }
        return root.height;
    }
    
    /**
    * Returns the least element in this tree strictly greater than 
    * the given element, or null if there is no such element
    *
    * @param e the value to match
    * @return element.data the least element greater than e, or null if there is no such element
    * @throws NullPointerException if the specified element is null
    */
    public E higher(E e){
        if(e == null){
            throw new NullPointerException("Specified element can not be null");
        }

        if(root == null){
            return null;
        }

        Node current = root;
        Node element = null;
        while(current != null){
            int compare = current.data.compareTo(e);
            if(compare > 0){
                element = current;
                current = current.left;
            } else{
                current = current.right;
            }
            
        }

        if(element != null){
            return element.data;
        } else{
            return null;
        }
    }

    /**
    * Returns true if this set contains no elements
    *
    * @return true if tree is empty, false if not
    */
    public boolean isEmpty(){
        if(root == null && size == 0){
            return true;
        }
        return false;
    }

    /**
    * Returns an iterator over the elements in this tree in ascending order
    *
    * @return ArrayIterator array of elements in ascending order
    */
    @Override
    public Iterator<E> iterator() {
        Object[] elements = new Object[size];
        int[] index = new int[] {0};
        inOrderTraversal(root, elements, index);
        return new ArrayIterator(elements, size);
    }

    /**
    * Performs an in-order traversal of the tree, visiting nodes in ascending order,
    * and fills the specified array with the elements in this order
    * 
    * @param node the current node being visited
    * @param elements the array to store the elements during traversal
    * @param index a single-element array used to track the current index in the elements array
    */
    private void inOrderTraversal(Node node, Object[] elements, int[] index) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, elements, index);
        elements[index[0]] = node.data;
        index[0]++;
        inOrderTraversal(node.right, elements, index);
    }

    /**
    * Creates and returns an iterator for traversing the tree in pre-order
    * 
    * @return an iterator over the elements of the tree in pre-order
    */
    public Iterator<E> preorderIterator() {
        E[] elements = (E[]) new Object[size];
        int[] index = new int[] {0};
        preOrderTraversal(root, elements, index);
        return new ArrayIterator(elements, size);
    }

    /**
    * Performs a pre-order traversal of the tree and 
    * fills the specified array with elements.
    * 
    * @param node the current node being visited
    * @param elements the array to store the elements during traversal
    * @param index a single element array used to track the current index in the elements array
    */
    private void preOrderTraversal(Node node, E[] elements, int[] index) {
        if (node == null) {
            return;
        }
        elements[index[0]] = node.data;
        index[0]++;
        preOrderTraversal(node.left, elements, index);
        preOrderTraversal(node.right, elements, index);
    }

    // Post-order Iterator
    public Iterator<E> postorderIterator() {
        E[] elements = (E[]) new Object[size];
        int[] index = new int[] {0};
        postOrderTraversal(root, elements, index);
        return new ArrayIterator(elements, size);
    }

    // Helper: Post-order Traversal
    private void postOrderTraversal(Node node, E[] elements, int[] index) {
        if (node == null) {
            return;
        }
        postOrderTraversal(node.left, elements, index);
        postOrderTraversal(node.right, elements, index);
        elements[index[0]] = node.data;
        index[0]++;
    }

    /**
    * Returns the last (highest) element currently in this tree.
    *
    * @return current.data last (highest) element currently in this tree
    * @throws NoSuchElementException if this set is empty
    */
    public E last(){
        if(root == null){
            throw new NoSuchElementException("This set is empty");
        }

        Node current = root;
        while(current.right != null){
            current = current.right;
        }

        return current.data;
    }
   
   /**
   * Returns the greatest element in this set strictly 
   * less than the given element, or null if there is no such element.
   *
   * @param e the value to match
   * @return element.data 
   * the greatest element strictly less than the given element, or null if no such element exists
   * @throws NullPointerException if the specified element is null
   */
   public E lower(E e){
        if(e == null){
            throw new NullPointerException("Specified element can not be null");
        }

        if(root == null){
            return null;
        }

        Node current = root;
        Node element = null;
        while(current != null){
            int compare = current.data.compareTo(e);
            if(compare > 0){
                current = current.left;
            } else{
                element = current;
                current = current.right;
            }
            
        }

        if(element != null){
            return element.data;
        } else{
            return null;
        }
   }

   /**
   * Removes the specified element from this tree if it is present.
   * Returns true if this tree contained the element and removed it, false if not
   *
   * @param o object to be removed from this set, if present
   * @return true if this set contained the specified element, false if not
   * @throws NullPointerException if the specified element is null
   */
   public boolean remove(Object o){
        if(o == null){
            throw new NullPointerException("Specified element can not be null");
        }

        if(root == o){

        }

        Node current = root;
        Node parent = null;
        while(current != null){
            int compare = current.data.compareTo((E) o);
            
            if (compare > 0) {
                parent = current;
                current = current.left;
            } else if (compare < 0) {
                parent = current;
                current = current.right;
            } else {
                //Removing leaf node or root if only element
                if (current.left == null && current.right == null) {
                    if (parent == null) {
                        root = null;
                    } else if (parent.left == current) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }
                }

                //Node has one child
                else if (current.left == null) {
                    if (parent == null) {
                        root = current.right;
                    } else if (parent.left == current) {
                        parent.left = current.right;
                    } else {
                        parent.right = current.right;
                    }
                } else if (current.right == null) {
                    if (parent == null) {
                        root = current.left;
                    } else if (parent.left == current) {
                        parent.left = current.left;
                    } else {
                        parent.right = current.left;
                    }
                } 
            
                //Node has two children
                else {
                    Node minNodeParent = current;
                    Node minNode = current.right;
                    while (minNode.left != null) {
                        minNodeParent = minNode;
                        minNode = minNode.left;
                    }

                    current.data = minNode.data;

                    if (minNodeParent.left == minNode) {
                        minNodeParent.left = minNode.right;
                    } else {
                    minNodeParent.right = minNode.right;
                    }
                }

                size--;
                return true;
            }
        }

        return false;
   }
   
    /**
    * Returns the number of elements in this tree.
    *
    * @return size size of tree
    */
    public int size(){
        return size;
    }

    /**
    * Returns a string representation of this tree. The string representation 
    * consists of a list of the tree's elements in the order they are returned by 
    * its iterator (inorder traversal), enclosed in square brackets ("[]"). 
    * Adjacent elements are separated by the characters ", "
    *
    * @return result a string representation of this tree
    */
    @Override
    public String toString() {
        String result = "[";
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            result += iterator.next();
            if (iterator.hasNext()) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }


    /** 
     * Computes and returns a multi-line string representing 
     * the structure of this binary search tree. 
     * @return the string representing this tree 
     */
    public String toStringTreeFormat( ) {
        StringBuffer sb = new StringBuffer(); 
        toStringTree(sb, root, 0);
        return sb.toString();
    }

    //uses preorder traversal to display the tree 
    //WARNING: will not work if the data.toString returns more than one line 
    //WARNING: may not work well for very large trees 
    private void toStringTree( StringBuffer sb, Node node, int level ) {
        //display the node 
        if (level > 0 ) {
            for (int i = 0; i < level-1; i++) {
                sb.append("   ");
            }
            sb.append("|--");
        }
        if (node == null) {
            sb.append( "->\n"); 
            return;
        }
        else {
            sb.append( node.data + "\n"); 
        }

        //display the left subtree 
        toStringTree(sb, node.left, level+1); 
        //display the right subtree 
        toStringTree(sb, node.right, level+1); 
    }


    /**
    * A custom iterator for iterating over an array of elements
    * This iterator is designed to work specifically with an array representation of 
    * the elements of the BST
    * 
    * @param <E> the type of elements being iterated over, matching the type of the BST.
    */
    private class ArrayIterator implements Iterator<E> {
        private final Object[] elements;
        private final int size;
        private int index;

        /**
        * Constructs an ArrayIterator with the specified array and size
        * 
        * @param elements the array containing the elements to iterate over
        * @param size the number of valid elements in the array
        */
        public ArrayIterator(Object[] elements, int size) { 
            this.elements = elements;
            this.size = size;
            this.index = 0;
        }

        /**
        * Checks if there are more elements to iterate over
        * 
        * @return true if the iterator has not yet reached the end of the valid elements
        */
        @Override
        public boolean hasNext() {
            if(index < size){
                return true;
            }
            return false;
        }

        /**
        * Returns the next element in the iteration.
        * 
        * @return the next element in the array.
        * @throws NoSuchElementException if there are no more elements to return.
        */
        @Override
        public E next() {
            if(!hasNext()){
                throw new NoSuchElementException("No more elements in the iterator.");
            }
            return (E) elements[index++];
        }
    }


    /* 
     * Node class for this BST 
     * 
     * used from Lab 10
     * @author Joanna Klukowska
     * @author Carsten Kaiser
     */ 
    protected class Node implements Comparable <E> {

        E data;
        Node  left;
        Node  right;
        int height;

        public Node ( E data ) {
            this.data = data;
            this.height = 1;
        }

        public int compareTo(E other){
            if(other == null || data == null){
                throw new IllegalArgumentException("Data can not be null");
            }
            
            return ((Comparable<E>) data).compareTo(other);
        }
    }

}
