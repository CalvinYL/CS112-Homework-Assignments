/* File: IntQueue.java
 * Date: 1/27/14
 * Author:  Wayne Snyder (snyder@bu.edu)
 * Edited by: Calvin Yung (cyung20@bu.edu)
 * Class: CS 112, Lab01
 * Purpose: Contains methods that allow the user to add and remove items from a stack.
 */

public class IntStack implements IntStackable {
    private int[] array2 = new int[20];         
    private int next = 0;
    
    // This method inserts a variable on the top of the stack when called.
    public void push(int pushNum) {
        array2[next] = pushNum;
        ++next;
    }
    
    // This method removes a variable from the stack when called.
    public int pop() {
        return array2[--next];
    }
    
    //This method checks if the array is empty.
    public boolean isEmptyarray2() {
        return(next == 0);
    }
}

interface IntStackable {
    public void push(int pushNum);
    public int pop();
    public boolean isEmptyarray2();
}