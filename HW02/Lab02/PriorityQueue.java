/* File: PriorityQueue.java
 * Date: 2/7/14
 * Author:  Wayne Snyder (snyder@bu.edu)
 * Edited by: Calvin Yung, cyung20@hotmail.com
 * Class: CS 112, Lab02
 * Purpose: Arranges enqueued variables in order within a queue
 */

@SuppressWarnings("unchecked") 

    public class PriorityQueue {
    
    private final int SIZE = 10; 
    private Comparable [] A = new Comparable[SIZE]; 
    private int size = 0;
    private int front = 0; 
    private int next = 0;
    
    
    // gives next index in array which wraps around in a ring; moves clockwise through indices
    private int nextSlot(int k) { 
        return ((k + 1) % A.length); 
    } 
    
    // gives previous index in array; moves counter-clockwise
    public int prevSlot(int k) {
        if (k == 0) {
            return (A.length - 1);
        } else {
            return ((k - 1) % A.length);
        }
    }
    
    /*
     * Enqueues a variable at the end of the queue, then checks if the number preceeding it
     * is greater than it. This will swap places with every preceeding number until the preceeding
     * number is less than it.
     */
    
    public void enqueue(Comparable n) { 
        if (size == A.length) { 
            expand(); 
        }
        
        A[next] = n;
        
        if (next == front) {
            next = nextSlot(next);
            size++;
            return;
        }
        
        int j = next;
        while (j > front && n.compareTo(A[j - 1]) < 0) {  
            A[j] = A[j - 1];
            j = prevSlot(j);
        }
                
        A[j] = n;    
        next = nextSlot(next);
        ++size; 
    }
    
    // Dequeues the first variable at the front
    public Comparable dequeue() { 
        Comparable temp = A[front]; 
        front = nextSlot(front);  
        --size;  
        return temp; 
    } 
    
    // Doubles the size of an array if the enqueued values exceed the length of the array
    private void expand() {
        Comparable[] B = new Comparable[2 * A.length];  
        for(int i = 0, j = front; i < size; ++i,j = nextSlot(j)) {
            B[i] = A[j];
        }
        front = 0;               
        next = size; 
        A = B;
    }
    
    // Keeps track of the size of the queue
    public int size() { 
        return size; 
    }  
    
    // Checks if the queue is empty
    public boolean isEmpty() { 
        return (size == 0); 
    } 
    
    // debugging routine
    private void list() {
        System.out.println("\nListing of queue clockwise from 0 (on left) to " + (A.length - 1) + ":"); 
        for(int i = 0; i < A.length; ++i)
            System.out.print(A[i] + " ");
        System.out.println(); 
        System.out.println("next = " + next + " front = " + front);
        System.out.println(); 
        
    }
    
    // Unit Test for debugging this ADT
    
    public static void main(String [] args) {
        
        PriorityQueue PQ = new PriorityQueue(); 
        
        PQ.enqueue(2);
        PQ.enqueue(3);
        PQ.dequeue();
        PQ.enqueue(6);
        PQ.enqueue(7);      
        PQ.enqueue(9);
        PQ.enqueue(10); 
        PQ.dequeue();  
        
        System.out.println("Test isEmpty and size; should print out:\nfalse 4\n" + PQ.isEmpty() + " " + PQ.size());
        PQ.enqueue(4); 
        PQ.enqueue(5);  
        int x = (int) PQ.dequeue();
        PQ.enqueue(8); 
        PQ.enqueue(x + 3); 
        PQ.enqueue(5);
        int y = (int) PQ.dequeue();
        PQ.enqueue(8);
        PQ.enqueue(x * y); 
        PQ.enqueue(1); 
        
        
        System.out.println("\nTest enqueue and dequeue; should print out:\n10 8  8  8  7  6  5  5  4  2  1:");       
        while( !PQ.isEmpty() ) {
            System.out.print( PQ.dequeue() + "  "); 
        }
        System.out.println(); 
        
        System.out.println("\nTest isEmpty and size again; should print out:\ntrue 0\n" + PQ.isEmpty() + " " + PQ.size());
        
    } 
    
}

