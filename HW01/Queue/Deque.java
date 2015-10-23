/* File: Deque.java
 * Date: 1/30/14
 * Author:  Wayne Snyder
 * Edited by: Calvin Yung (cyung20@bu.edu)
 * Class: CS 112, HW01
 * Purpose: Contains methods that allow the user to enqueue and dequeue integers from either the
 *          front or the rear of an array. Also includes methods that adjusts the next and front pointers
 *          and changes the size of the array based on the number of integers within the array.
 */

public class Deque implements Dequeable {
    private final int SIZE = 10; 
    private int [] A = new int[SIZE]; 
    private int size = 0;
    private int front = 0; 
    private int next = 0;  
    
    // gives next index in array which wraps around in a ring; moves clockwise through indices
    public int nextSlot(int k) { 
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
    
    // enqueue in next slot clockwise around the ring
    public void enqueueRear(int n) { 
        if(size != A.length) {              
            A[next] = n; 
            next = nextSlot(next); 
            ++size;  
        } else {
            resize();
            next = nextSlot(next); 
            ++size;
        }
    }
    
    // enqueues a value in front of the queue and moves the front pointer back by one.
    public void enqueueFront(int n) {
        if(size != A.length) {
            front = prevSlot(front);
            A[front] = n;
            ++size;
        } else {
            resize();
            front = prevSlot(front);
            ++size;
        }
    }
    
    // dequeues a value from the front of the queue and moves the front pointer forwards by one.
    public int dequeueFront() { 
        shrink();
        int temp = A[front]; 
        front = nextSlot(front);      
        --size;
        return temp;
    } 
    
    // dequeues a value from the very end of the queue and moves the next pointer back by one.
    public int dequeueRear() {
        shrink();
        int temp = A[next - 1];
        next = prevSlot(next);
        --size;
        return temp;
    }
       
    // doubles the size of the array if a value is enqueued when the queue is already full
    public void resize() {
        int[] B = new int[2 * A.length];
        int j = front;
        for (int i = 0; i < A.length; ++i) {
            B[i] = A[j];
            j = nextSlot(j);
        }
        next = A.length;
        A = B;
        
    }
    
    // keeps track of how many values are in the array
    public int size() { 
        return size; 
    }
    
    // shrinks the array to half the size dequeuing when the number of elements is less 
    // than 1/4th the length of the array.
    public void shrink() {
        if (A.length != 10 && (next - 1) < (0.25 * A.length)) {
            int j = front;
            int[] C = new int[A.length / 2];
            for(int i = 0; i < A.length; ++i) {
                C[i] = A[j];
                j = nextSlot(j);
            }
            next = A.length;
            A = C;
            
        } else {
            return;
        }
    }
    
    // checks if the queue is empty
    public boolean isEmpty() { 
        return (size == 0); 
    } 
    
}