/*
 * ArticleTable.java
 * 
 * This is hash table of the articles.
 * 
 * Author: Calvin Yung, cyung20@bu.edu
 * Date: April 20, 2014
 */


import java.util.*;
import java.io.*;

public class ArticleTable implements Iterator<Article> {
    
    private static int numShuffles = 1000000;
    private static final int SIZE = 2521;         // prime number (table size)
    private Node[] N = new Node[SIZE];
    private Node Head;
    private Node p;
    private int i = 0;
    private int numItems = 0;
    private String directoryPath;
    private File[] children;

    
    public ArticleTable(String path) {
        this.directoryPath = path;
        this.children = findChildren(path);
    }
    
    
    // bucket node
    public static class Node {
        
        String title;
        Article key;
        Node next;
        Node next2;
        
        // Node constructors
        public Node(Article data) {
            this(data, null);
        }
        
        public Node(Article a, Node n) {
            key = a;
            next = n;
        }
        
        public Node(String str, Node n) {
            title = str;
            next = n;
        }    
    }
    
    
    // hash function
    public static int hash(String title) {
        int sum = 0;
        for (int i = 0; i < title.length(); i++) {
            sum += title.charAt(i);
        }
        return (sum*101891) % SIZE;
    }
    
    
    // initializes Article array
    public void initialize(Article[] A) {
        for(int i = 0; i < A.length; ++i) 
            insert(A[i]); 
    }
    
    
    /*
     * The following methods: insert, delete, lookup, printBucket, and printTable were
     * taken from http://www.cs.bu.edu/fac/snyder/cs112/CodeExamples/HashTableSC.java
     * and were modified accordingly to run this program.
     */
    
    
    // inserts Article a into the table using the title of a as the hash key
    public void insert(Article a) {     
        Head = insertHelper(a, Head);
        N[hash(a.getTitle())] = insertHelper(a, N[hash(a.getTitle())]);
    }  
    
    // insert Helper method
    public Node insertHelper(Article a, Node n) {
        if (n == null) {
            numItems++;
            return new Node(a, null);
        } else if (a == n.key) {
            return n;
        } else {
            n.next = insertHelper(a, n.next);
            return n;
        }
    }
    
    
    // deletes an Article using its title.
    public void delete(String t) {
        Head = deleteHelper(t, Head);
        N[hash(t)] = deleteHelper(t, N[hash(t)]);
    }
    
    // delete Helper method
    public Node deleteHelper(String t, Node n) {
        if (n == null) {
            return null;
        } else if (t == n.key.getTitle()) {
            --numItems;
            return n.next;
        } else {
            n.next = deleteHelper(t, n.next);
            return n;
        }
    }

    
    // returns the article with the given title or null if not found
    public Article lookup(String t) {
        return (lookupHelper(t, N[hash(t)]));
    }
    
    // lookup Helper method
    public Article lookupHelper(String t, Node n) {             
        if (n == null) {
            System.out.println("Article not found");
            return null;
        } else if (t == n.key.getTitle()) {
            return n.key;
        } else {
            return lookupHelper(t, n.next);
        }
    }
    
    
    // sets point p back at the front of the hash table
    public void reset() {  
        i = 0;
    }
    
    
    // returns true or false by checking if the pointer 
    // has exceeded the size of the array
    public boolean hasNext() {
        return i < children.length - 1;
    }
    
    
    // searches for the next Node with an Article inside of it
    // and returns the Article in the current node. Also increments i by 1.
    // Taken from Alexander Breen's DatabaseIterator program
    public Article next() {
        File thisFile = children[i];
        Scanner s = null;
        
        try {
            s = new Scanner(thisFile, "UTF-8");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("child does not exist -- " +
                                       "was it deleted?");
        }
        
        String title = s.nextLine();
        String body = "";

        while (s.hasNextLine())
            body += s.nextLine() + "\n";

        Article a = new Article(title, body);
        i++;
        return a;
        
    }
    
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    
    // prints the bucket of a hash table
    private static void printBucket(Node n) {
        if(n == null)
            System.out.println(" -> .");
        else {
            System.out.print(" -> [" + n.key.getTitle() + "]");
            printBucket(n.next);
        }
    }
    
    
    // prints out the hash table
    private void printTable() {
        for(int i = 0; i < N.length; ++i) {
            System.out.print("N["+i+"]");
            printBucket(N[i]); 
        }
        System.out.println();    
    }
    
    
    // Taken from Alexander Breen and Wayne Snyder's Minipedia program
    private static ArticleTable setupDatabase(String path) {
        return new ArticleTable(path);
    }
    
    
    // returns the number of articles
    // Taken from Alexander Breen's DatabaseIterator program
    public int getNumArticles() {
        return children.length;
    }
    
    
    // Taken from Alexander Breen's DatabaseIterator program
    private File[] findChildren(String path) throws IllegalArgumentException {
        File dir = new File(path);

        if (!dir.exists())
            throw new IllegalArgumentException("directory does not exist");

        if (!dir.isDirectory())
            throw new IllegalArgumentException("path does not refer to " +
                                               "a directory");

        File[] cs = dir.listFiles();

        if (cs == null)
            throw new RuntimeException("an error occured getting files " +
                                       "under directory");

        return cs;
    }
    
    
    // stores the articles into a list array, randomizes it, and returns the list.
    // Taken from Alexander Breen and Wayne Snyder's Minipedia program
    private static Article[] getArticleList(ArticleTable db) {
        
        int count = db.getNumArticles(); 
        
        Article[] list = new Article[count];
        for(int i = 0; i < count; ++i)
            list[i] = db.next();
        
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
    
    
    public static void main(String[] args) {
        
        String dbPath = "articles/";
        ArticleTable db = setupDatabase(dbPath);
        
        System.out.println("Read " + db.getNumArticles() + " articles from disk.");
        
        ArticleTable T = new ArticleTable(dbPath);
        Article[] A = getArticleList(db);
        T.initialize(A);
        T.printTable();
        
        // resets and prints all of the Articles
        // Left commented out because laptop could not
        
//        T.reset();
//        while(T.hasNext()) {
//            Article a = T.next(); 
//            System.out.println(a);
//        }
    
        
    }
    
    
    
}