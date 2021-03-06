/* File: IntQueue.java
 * Date: 1/27/14
 * Author:  Wayne Snyder (snyder@bu.edu)
 * Edited by: Calvin Yung (cyung20@bu.edu)
 * Class: CS 112, Lab01
 * Purpose: Creates two arrays - one based on how values are added into a queue 
 *          and the other based on a stack.
 */

public class QueueStackClient {
    public static void main(String [] args) {
        IntQueueable Q = new IntQueue(); 
        IntStackable S = new IntStack();
        
        for(int i = 1; i < 10; ++i) {
            Q.enqueue(i);
        }
        
        Q.list();
        while(!Q.isEmptyarray()) {
            S.push(Q.dequeue());
        } 
        
        while(!S.isEmptyarray2()) {
            Q.enqueue(S.pop());
        } 
        
        Q.list();          
    } 
}
