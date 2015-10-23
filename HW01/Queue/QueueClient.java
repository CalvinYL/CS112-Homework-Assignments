/* File: QueueClient.java
 * Date: 1/28/14
 * Author:  Wayne Snyder (snyder@bu.edu)
 * Class: CS 112, Fall 2014
 * Purpose: Example of integer queue represented by a ring buffer
 */

public class QueueClient {
    public static void main(String [] args) {
      Queueable Q = new Queue();
      System.out.println("\nShould print out:\n3  4  5  6  7  8  9  10  11  12:"); 

      Q.enqueue(1); 
      Q.enqueue(2);
      Q.enqueue(3);
      
      Q.dequeue();
      Q.dequeue(); 
      
      Q.enqueue(4); 
      Q.enqueue(5);  
      Q.enqueue(6);
      Q.enqueue(7);
      Q.enqueue(8);  
      Q.enqueue(9);
      Q.enqueue(10); 
      Q.enqueue(11); 
      Q.enqueue(12); 
      
      
      while( !Q.isEmpty() ) {
         System.out.print( Q.dequeue() + "  "); 
      }
      System.out.println(); 
      
      
   } 
   
}
