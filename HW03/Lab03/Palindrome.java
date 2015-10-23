/*
 * Palindrome.java
 * 
 * Author: Cody Doucette <doucette@bu.edu>
 * Boston University CS 112, Summer I 2013
 * Editor: Calvin Yung, cyung20@bu.edu
 * Purpose: This is an exercise in debugging by implementing a
 * palindrome-checker using a stack data structure.
 */

import java.util.Stack;               // this is the built-in Java generic Stack

public class Palindrome {
    
    /**
     * palindrome: returns a boolean representing whether @str
     * is a palindrome. str should contain only alphanumeric
     * characters.
     */
     private static boolean palindrome(String str) {
        
        Stack<Character> s = new Stack<Character>();
        
        /* Push first half of str onto stack. */
        for (int i = 0; i < str.length() / 2; i++) {
            //System.out.println(s.push(str.charAt(i)));
            s.push(str.charAt(i));
        }
        
        int count = 0;
        int secondHalf = str.length() / 2;
        
        if (str.length() % 2 == 1) {
            secondHalf++;
        }
        
        /* Pop second half of str from stack and compare. */
        for (int i = secondHalf; i < str.length(); i++) {
            
            char c = s.pop();
            //System.out.println("c = " + c);
            //System.out.println("str.charAt(i) = " + str.charAt(i));
            if (c == str.charAt(i)) {
                count++;
            }
            
        }
        
        if (count == str.length() / 2) {
            return true;
        } else {
            return false;
        }
        
    }   
     
    
     public static void main(String[] args) {
         
         System.out.println("Is redder a palindrome? Should be true:");
         System.out.println(palindrome("redder"));
         System.out.println();
         
         System.out.println("Is reddest a palindrome? Should be false:");
         System.out.println(palindrome("reddest"));
         System.out.println();
            
         System.out.println("Is racecar a palindrome? Should be true:");
         System.out.println(palindrome("racecar"));
         System.out.println();
         
         System.out.println("Is civil a palindrome? Should be false:");
         System.out.println(palindrome("civil"));
         System.out.println();
         
         System.out.println("Is civic a palindrome? Should be true:");
         System.out.println(palindrome("civic"));
         System.out.println();
         
         System.out.println("Is halloween a palindrome? Should be false:");
         System.out.println(palindrome("halloween"));
         System.out.println();
         
         //System.out.println("Is _______ a palindrome? Should be ____:");
         //System.out.println(palindrome(_______));
         
     }
    
}