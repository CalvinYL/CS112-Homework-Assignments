/*
 * MiniGoogle.java
 *
 * A client program that uses the ArticleTable, Article,
 * and TermFrequencyTable classes to allow a user to search 
 * the content of an article, which is done using cosine similarity
 * measure instead of looking for the keyword.
 *
 * Author: Alexander Breen (abreen@bu.edu) and Wayne Snyder (waysnyder@gmail.com)
 * Edited by: Calvin Yung (cyung20@bu.edu)
 * Date: April 20, 2014
 */

import java.util.*;
import java.io.*;


public class MiniGoogle{
    
    private final String [] blackList = { "the", "of", "and", "a", "to", "in", "is", 
        "you", "that", "it", "he", "was", "for", "on", "are", "as", "with", 
        "his", "they", "i", "at", "be", "this", "have", "from", "or", "one", 
        "had", "by", "word", "but", "not", "what", "all", "were", "we", "when", 
        "your", "can", "said", "there", "use", "an", "each", "which", "she", 
        "do", "how", "their", "if", "will", "up", "other", "about", "out", "many", 
        "then", "them", "these", "so", "some", "her", "would", "make", "like", 
        "him", "into", "time", "has", "look", "two", "more", "write", "go", "see", 
        "number", "no", "way", "could", "people",  "my", "than", "first", "water", 
        "been", "call", "who", "oil", "its", "now", "find", "long", "down", "day", 
        "did", "get", "come", "made", "may", "part" }; 
    
    private static int numShuffles = 1000000;
    private File[] children;
        
    private final int SIZE = 2521;
    private double[] A = new double[SIZE];
    private int next = 0;
    
     
    // adds an article
    private static void addArticle(Scanner s, ArticleTable D) {
        System.out.println();
        System.out.println("Add an article");
        System.out.println("==============");
        
        System.out.print("Enter article title: ");
        String title = s.nextLine();
        
        System.out.println("You may now enter the body of the article.");
        System.out.println("Press return two times when you are done.");
        
        String body = "";
        String line = "";
        do {
            line = s.nextLine();
            body += line + "\n";
        } while (!line.equals(""));
        
        //D.add(new Article(title, body));
    }
    
    
    // removes an article
    private static void removeArticle(Scanner s, ArticleTable D) {
        System.out.println();
        System.out.println("Remove an article");
        System.out.println("=================");
        
        System.out.print("Enter article title: ");
        String title = s.nextLine();
        
        //D.remove(title);
    }
    
    
    // searches for an article by title
    private static void titleSearch(Scanner s, ArticleTable D) {
        System.out.println();
        System.out.println("Search by article title");
        System.out.println("=======================");
        
        System.out.print("Enter article title: ");
        String title = s.nextLine();
        
        Article a = D.lookup(title);
        if(a != null)
            System.out.println(a);
        else {
            System.out.println("Article not found!"); 
            return; 
        }
        
        System.out.println("Press return when finished reading.");
        s.nextLine();
    }
    
    
    // searches for a keyword in the title
    private static void titleContentSearch(Scanner s, ArticleTable D) { 
        System.out.println();
        System.out.println("Search by keyword in article title");
        System.out.println("=========================");
        
        System.out.print("Enter keyword: ");
        CharSequence keyword = s.nextLine();
        
        //D.traverseTitle(D.root, keyword);
    }
    
    
    // sets point p back at the front of the hash table
    public void reset() {  
        next = 0;
    }
    
    
    // returns true or false by checking if the pointer 
    // has exceeded the size of the array
    public boolean hasNext() {
        return next < children.length - 1;
    }
    
    
    // searches for the next Node with an Article inside of it
    // and returns the Article in the current node. Also increments i by 1.
    // Taken from Alexander Breen's DatabaseIterator program
    public Article next() {
        File thisFile = children[next];
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
        next++;
        return a;
        
    }
    
    
    private static Article[] getArticleList(ArticleTable db) {
        
        int count = db.getNumArticles();
        
        Article[] list = new Article[count];
        for(int i = 0; i < count; ++i) {
            list[i] = db.next();
        }
        
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
    
    
    private static ArticleTable setupDatabase(String path) {
        return new ArticleTable(path);
    }
    
    
    // Takes a string, turns it into all lower case, and removes
    // all characters except for letters, digits, and white spaces.
    private String preprocess(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("[^a-zA-Z0-9\\s]", "");
        return s;
    }
    
    
    // Determines if the string s is a member of the blacklist
    private boolean blacklisted(String s) {
        for (int i = 0; i < blackList.length; i++) {
            if (s.equals(blackList[i])) {
                return true;
            }
        }
        
        return false;
    }
    
    
    private int j = 0;
    // takes two strings (e.g., the search phrase and the body of an article) and
    // preprocesses each to remove all but letters, digits, and whitespace, and then
    // uses the StringTokenizer class to extract each of the terms; create a TermFrequencyTable and 
    // insert each of the terms which is NOT in the blacklist into the table with its docNum 
    // (String s being document 0 and String t being document 1); 
    // finally extract the cosine similarity and return it.  
    private double getCosineSimilarity(String s, String t) {
        
        s = preprocess(s);
        t = preprocess(t);
        
        
        TermFrequencyTable T = new TermFrequencyTable();
        
        StringTokenizer TokenS = new StringTokenizer(s); 
        while (TokenS.hasMoreTokens()) {
            String token0 = TokenS.nextToken();
            if (blacklisted(token0) == false) {
                T.insert(token0, 0);
            }
        }
        
        StringTokenizer TokenT = new StringTokenizer(t);
        while (TokenT.hasMoreTokens()) {
            String token1 = TokenT.nextToken();
            if (blacklisted(token1) == false) {
                T.insert(token1, 1);
            }
        }
        
        return T.cosineSimilarity();
    }
    
    
    // Takes the inputted search phrase and finds the most relevant article
    // based on an article's cosine similarity to the phrase. The method then 
    // orders these article into a Max Heap and prints out the top three
    // articles with the highest cosine similarity measures with the phrase.
    private static void articleContentSearch(Scanner s, ArticleTable AT) {   
        System.out.println();
        System.out.println("Search the content of an article");
        System.out.println("=========================");
        
        System.out.print("Enter search phrase: ");
        String phrase = s.nextLine();
        
        MiniGoogle G = new MiniGoogle();
        
        AT.reset();
        double cosSim = 0;
        while(AT.hasNext()) {
            Article a = AT.next(); 
            cosSim = G.getCosineSimilarity(phrase, a.getBody());
            if (cosSim > 0.0) {
                G.insert(cosSim);
            }
        }
         
        G.heapSort();
        
        if (G.A[G.SIZE - 1] == 0) {
            System.out.println("No articles found!");
        } else {
            for (int i = G.SIZE - 1; i > G.SIZE - 4; i--) {
                System.out.println(G.A[i]);
            }
        }
        
    }
    
    /*
     * All methods from here to the main method were taken from
     * http://www.cs.bu.edu/fac/snyder/cs112/CodeExamples/MaxHeap.java
     * and were modified accordingly to run this program.
     */
    
    private int parent(int i) { 
        return (i-1) / 2; 
    }
    
    private int lchild(int i) {
        return 2 * i + 1; 
    }
    
    private int rchild(int i) {
        return 2 * i + 2;
    }
    
    private boolean isLeaf(int i) { 
        return (lchild(i) >= next); 
    }
    
    private boolean isRoot(int i) {
        return i == 0;
    }
    
    // inserts an integer into the array at the next available location
    public void insert(double k) {
        
        A[next] = k; 
        int i = next;
        int p = parent(i); 
        while(!isRoot(i) && A[i] > A[p]) {
            swap(i,p);
            i = p;
            p = parent(i); 
        }
        ++next;
    }
    
    
    // swaps the indices in the array
    private void swap(int i, int j) {
        double temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }
    
    public double getMax() {
        --next;
        swap(0,next);                // swap root with last element
        int i = 0;                   // i is location of new key as it moves down tree
        
        int mc = maxChild(i); 
        while(!isLeaf(i) && A[i] < A[mc]) { 
            swap(i,mc);
            i = mc; 
            mc = maxChild(i);
        }
        
        return A[next];
    }
    
    
    // return index of maximum child of i or -1 if i is a leaf node (no children)
    int maxChild(int i) {
        if(lchild(i) >= next)
            return -1;
        if(rchild(i) >= next)
            return lchild(i);
        else if(A[lchild(i)] > A[rchild(i)])
            return lchild(i);
        else
            return rchild(i); 
    }
    
    
    public void heapSort() {
        next = 0;
        for(int i = 0; i < A.length; ++i)      // turn A into a heap
            insert(A[i]);
        for(int i = 0; i < A.length; ++i)      // delete root A.length times, which swaps max into
            getMax();                           //  right side of the array
    }
    
    
    public static void main(String[] args) {
        Scanner user = new Scanner(System.in);
        
        String dbPath = "articles/";
        
        ArticleTable db = setupDatabase(dbPath);
        
        System.out.println("Read " + db.getNumArticles() + 
                           " articles from disk.");
        
        ArticleTable L = new ArticleTable(dbPath); 
        Article[] A = getArticleList(db);
        L.initialize(A);
        
        System.out.println("Created in-memory hash table.");
        
        int choice = -1;
        do {
            System.out.println();
            System.out.println("Welcome to Minipedia!");
            System.out.println("=====================");
            System.out.println("Make a selection from the " +
                               "following options:");
            System.out.println();
            System.out.println("Manipulating the database");
            System.out.println("-------------------------");
            System.out.println("    1. add a new article");
            System.out.println("    2. remove an article");
            System.out.println();
            System.out.println("Searching the database");
            System.out.println("----------------------");
            System.out.println("    3. search by exact article title");
            System.out.println("    4. keyword search in article title");
            System.out.println("    5. search the content of an article (GOOGLE)");
            System.out.println();
            
            System.out.print("Enter a selection (1-5, or 0 to quit): ");
            
            choice = user.nextInt();
            user.nextLine();
            
            switch (choice) {
                case 0:
                    return;
                    
                case 1:
                    addArticle(user, L);
                    break;
                    
                case 2:
                    removeArticle(user, L);
                    break;
                    
                case 3:
                    titleSearch(user, L);
                    break;
                    
                case 4:
                    titleContentSearch(user, L);
                    break;
                case 5:
                    articleContentSearch(user, L);
                    break;
                    
                    
                    
                default:
                    break;
            }
            
            choice = -1;
            
        } while (choice < 0 || choice > 6);
        
    }
    
    
}
                
                