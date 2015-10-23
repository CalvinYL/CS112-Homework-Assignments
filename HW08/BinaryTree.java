/*
 * BinaryTree.java
 * 
 * This is binary tree of the articles.
 *
 * Author: Alexander Breen (abreen@bu.edu) and Wayne Snyder (waysnyder@gmail.com)
 * Edited by: Calvin Yung, cyung20@bu.edu
 * Date: April 3, 2014
 */

import java.util.*;

public class BinaryTree {
    
    private static int numShuffles = 1000000;
    
    public static class Node {
        
        //declared fields
        public Article data;
        Article key;
        Node left;
        Node right;
        
        // Node constructors
        public Node(Article data, Node n) {
            this.data = data;
            left = null;
            right = null;
        }
        
        public Node(Article data) {
            this(data, null);
        }
        
        public Node(Article a, Node left, Node right) {
            key = a;
            this.left = left;
            this.right = right;
            
        }
    }
    
    public static Node root = null;
    
    // initializes Article array
    public void initialize(Article[] A) {
        for(int i = 0; i < A.length; ++i) 
            add(A[i]); 
    }
    
    // inserts an Article into the Binary Tree
    public void add(Article a) {
        root = insert(root, a); 
    } 
    
    // method that inserts an article into the Binary Tree
    public static Node insert(Node t, Article a) {
        if (t == null)
            return new Node(a);
        else if (a.getTitle().compareTo(t.data.getTitle()) < 0) {
            t.left = insert(t.left, a);
            return t;
        } else if (a.getTitle().compareTo(t.data.getTitle()) > 0) {
            t.right = insert(t.right, a);
            return t;
        } else
            return t;
    }
    
    // Checks if the article passed in is in the binary tree
    public boolean member(Article a) {
        return (lookup(root,a.getTitle()) != null); 
    }
    
    // Checks if the title passed in is in the binary tree
    public boolean member(String title) {
        return (lookup(root,title) != null); 
    }
    
    // lookups the title
    public static Article lookup(String title) {
        Node n = lookup(root,title); 
        if(n != null)
            return n.data; 
        return null; 
    }
    
    private static Node lookup(Node t, String key) {
        if (t == null)
            return null;
        else if (key.compareTo(t.data.getTitle()) < 0) {
            return lookup(t.left, key);
        } else if (key.compareTo(t.data.getTitle()) > 0) {
            return lookup(t.right, key);
        } else 
            return t; 
    }
    
    public static Node lookup(Node t, Article a) {
        if (t == null)
            return null;
        else if (a.getTitle().compareTo(t.data.getTitle()) < 0) {
            return lookup(t.left, a);
        } else if (a.getTitle().compareTo(t.data.getTitle()) > 0) {
            return lookup(t.right, a);
        } else 
            return t; 
    }
    
    public void remove(String t) {
        root = delete(t, root); 
    }
    
    
    // deletes a title from the binary tree
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
    public static int height(Node root) {
        if (root == null) {
            return -1;
        } else { 
            return 1 + maximum( height(root.left), height(root.right) ); 
        }
    }
    
    // returns the larger number of the two integers passed in
    private static int maximum(int n, int m) {
        if( m > n ) {
            return m;
        } else {
            return n;
        }
    }
    
    private static DatabaseIterator setupDatabase(String path) {
        return new DatabaseIterator(path);
    }
    
    // stores the articles into a list array, randomizes it, and returns the list
    private static Article[] getArticleList(DatabaseIterator db) {
        
        // count how many articles are in the directory
        int count = db.getNumArticles(); 
        
        // now create array
        Article[] list = new Article[count];
        for(int i = 0; i < count; ++i)
            list[i] = db.next();
        
        // now shuffle the array: generate two random indices and swap
        // doing it 1M times should be enough!
        
        Random R = new Random(); 
        
        for(int i = 0; i < numShuffles; ++i) {
            int j = R.nextInt(count);
            int k = R.nextInt(count);
            Article temp = list[j];
            list[j] = list[k];
            list[k] = temp; 
        }
        
        return list;
    }
    
        
    public static int count = 0;
    
    
    // prints title and article. Also converts the keyword to a String and prints it in uppercase letters.
    public static void visit(Node t, CharSequence keyword) {
        count += 1; 
        System.out.println(t.data.getTitle());
        System.out.println("====================");
        String body = t.data.getBody();
        String upperKey = keyword.toString();
        System.out.println(body.replaceAll(upperKey, upperKey.toUpperCase()));
        System.out.println();
    }
    
    
    // traverses the binary tree for titles that contain the passed in keyword
    public static void traverseTitle(Node root, CharSequence keyword) {
        if (count == 10 || root == null) {
            return;
        }
        
        if (!root.data.getTitle().contains(keyword)) {
            traverseTitle(root.right, keyword);
            traverseTitle(root.left, keyword);
        } else {
            visit(root, keyword);
            traverseTitle(root.right, keyword);
            traverseTitle(root.left, keyword);
        }
    }
    
    // traverses the binary tree for articles that contain the passed in keyword
    public static void traverseArticle(Node root, CharSequence keyword) {
        if (count == 10 || root == null) {
            return;
        }
        
        String body = root.data.getBody();
        if (!body.contains(keyword)) {
            traverseArticle(root.right, keyword);
            traverseArticle(root.left, keyword);
        } else {
            visit(root, keyword);
            traverseArticle(root.right, keyword);
            traverseArticle(root.left, keyword);
        }
    }
    
    public static void main(String[] args) {
        
        String dbPath = "articles/";
        DatabaseIterator db = setupDatabase(dbPath);
        
        System.out.println("Read " + db.getNumArticles() + " articles from disk.");
        
        BinaryTree B = new BinaryTree();
        Article[] A = getArticleList(db);
        B.initialize(A);
        
        System.out.println("height = " + height(root));
    }
    
}
