/*
 * DumbList.java
 * 
 * http://www.cs.bu.edu/~snyder/cs112/CourseMaterials/BinaryTreeCode.html
 * This is an unordered list of articles. Dumb, dumb, dumb. You will rewrite this as a binary tree. 
 *
 * Author: Alexander Breen (abreen@bu.edu) and Wayne Snyder (waysnyder@gmail.com)
 * Date: March 24, 2014
 */

public class DumbList {
    
    public static class Node {
        public Article data;
        public Node next;
        
        public Node(Article data, Node n) {
            this.data = data;
            this.next = n;
        }
        
        public Node(Article data) {
            this(data, null);
        }
        
        int key;
        Node left;
        Node right;
        
        public Node(int k) {
            left = null;
            right = null;
            key = k;
        }
        
        public Node(int k, Node left, Node right) {
            key = k;
            this.left = left;
            this.right = right;
            
        }
    }
    
    public Node root = null;
    
    public void initialize(Article[] A) {
        for(int i = 0; i < A.length; ++i) 
            add(A[i]); 
    }
    
    public void add(Article a) {
        root = new Node(a, root); 
    } 
    
    
    public boolean member(Article a) {
        return (lookup(root,a.getTitle()) != null); 
    }
    
    public boolean member(String title) {
        return (lookup(root,title) != null); 
    }
    
    public Article lookup(String title) {
        Node n = lookup(root,title); 
        if(n != null)
            return n.data; 
        return null; 
    }
    
    private  Node lookup(Node t, String key) {
        if (t == null)
            return null;
        else if (key.compareTo(t.data.getTitle()) == 0) {
            return t;
        } else 
            return lookup(t.next,key); 
    }
    
    public  Node lookup(Node t, Article a) {
        if (t == null)
            return null;
        else if (a.getTitle().compareTo(t.data.getTitle()) == 0) {
            return t;
        } else 
            return lookup(t.next, a);
    }
    
    public void remove(String t) {
        root = delete(t, root); 
    }
    
    public int length() {
        return length(root); 
    }
    
    public int length(Node t) {
        if(t == null)
            return 0;
        else
            return 1 + length(t.next); 
    }
    
    // Recursively reconstructs tree without the key n in it
    
    public Node delete(String title, Node t) {
        if (t == null)  {                           
            return t;
        } else if (title.compareTo(t.data.getTitle()) < 0) { 
            t.left = delete(title, t.left);
            return t;
        } else if (title.compareTo(t.data.getTitle()) > 0) {
            t.right = delete(title, t.right); 
            return t;
        } else {                                 
            if (t.left == null)  {             
                return t.right;
            } else if (t.right == null)  {      
                return t.left;
            } else {                        
                Node min = findMin(t.right);             
                t.right = deleteMin(t.right);            
                min.left = t.left;                       
                min.right = t.right;
                return min;
            }
        }
    }
    
    // return a pointer to the minimal element in r
    
    public Node findMin(Node r) {
        if (r.left == null) {
            return r;
        } else {
            return findMin(r.left);
        }
    }
    
    // reconstruct the tree r without its minimal element
    
    public Node deleteMin(Node r) {
        if (r.left == null) {
            return r.right;
        } else {
            r.left = deleteMin(r.left);
            return r; 
        } 
    }
    
    // Measures height of a Binary Search Tree
    public int height(Node t) {
        if (t == null) {
            return -1;
        } else { 
            return 1 + maximum( height(t.left), height(t.right) ); 
        }
    }
    
    private int maximum(int n, int m) {
        if( m > n ) {
            return m;
        } else {
            return n;
        }
    }
    
    
    
}
