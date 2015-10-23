/* File: MyString.java
 * Date: 2/28/14
 * Author:  Calvin Yung (cyunt20@bu.edu)
 * Class: CS 112, Spring 2014
 * Purpose: Uses recursive algorithms to manipulate linked lists to implement an important 
 *          subset of methods from the Java String class.
 */


public class MyString implements Stringy {
    
    public class Node {
        
        public char item;
        public Node next;
        
        Node() {
            item = 0; 
            next = null;     
        } 
        
        Node(char n) {
            item = n;
            next = null;
        }
        
        Node(char n, Node p) {
            item = n;
            next = p;
        }
    }
    
    private Node Head;
    private int size;
    
    // The constructor for a MyString; takes a normal Java String and constructs a MyString
    public MyString(String str) {
        size = str.length();
        Head = MyStringHelper(str, 0);
    }
    
    // MyString helper method
    private Node MyStringHelper(String str, int index) {
        if (index == size || str.equals("")) {
            return null;
        } else {
            Node Q = new Node(str.charAt(index));
            Q.next = MyStringHelper(str, index + 1);
            return Q;
        }
    }
    
    // Convert this MyString to a regular Java String -- thus, when you simply use your MyString
    // in a place where a String is expected, it will simply convert to a String automatically. 
    public String toString() {
        return toStringHelper(Head);
    }
    
    // toString helper method
    private String toStringHelper(Node p) {
        if (p == null) {
            return "";
        } else {
            return p.item + toStringHelper(p.next);
        }
    }
    
    
    // Returns the char value at the specified index.
    public char charAt(int index) {
        return charAtHelper(index, Head, 0);
    }
    
    // charAt helper method
    private char charAtHelper(int index, Node Head, int i) {
        if (index >= size) {
            throw new StringIndexOutOfBoundsException();
        } else if (i != index) {
            return charAtHelper(index, Head.next, i + 1);
        } else {
            return (char) Head.item;
        }
    }
    
    
    // Returns the index within this MyString of the first occurrence of 
    // the specified character or -1 if it does not occur.
    public int indexOf(char ch) {
        return indexOfChar(ch, Head, 0);
    }
    
    // indexOf helper for a char
    private int indexOfChar(char ch, Node p, int index) {
        if (index >= size) {
            return -1;
        } else if (ch == p.item) {
            return index;
        } else {
            return indexOfChar(ch, p.next, index + 1);
        }
    }
    
    
    // Returns the index within this MyString of the first occurrence 
    // of the specified substring or -1 if it does not occur.
    public int indexOf(MyString str) {
        return indexOfStr(str, Head, 0);
    }
    
    // indexOf helper for a String
    private int indexOfStr(MyString str, Node p, int index) {
        if (index >= size) {
            return -1;
        } else if (str.equals(p)) {
            return index;
        } else {
            return indexOfStr(str, p.next, index + 1);
        }
    }
    
    // Compares two MyStrings lexicographically. It should return -1, 0, or 1 
    // depending on whether this myString is less, equal, or greater than anotherMyString.
    public int compareTo(MyString anotherString) {
        return compareToHelper(anotherString, Head, 0);
    }

    // compareTo Helper method
    private int compareToHelper(MyString anotherString, Node p, int index) {
        if (index >= size) {
            return 0;
        }
        int thisMyString = (int) p.item;
        int aString = (int) anotherString.charAt(index);
        int n = thisMyString - aString;
        
        if (n < 0 || p.equals("")) {                       //A comes before B
            return -1;
        } else if (n > 0 || anotherString.equals("")) {    //A comes after B
            return 1;
        } else {                                    
            return compareToHelper(anotherString, p.next, index + 1);
        }
    }

     // Creates a copy of a list
    // Taken from http://www.cs.bu.edu/fac/snyder/cs112/CourseMaterials/LinkedListNotes.Recursion.LLs.html
    public Node copy(Node p) {
        if (p == null) {
            return null;
        } else {
            return new Node(p.item, copy(p.next));
        }
    }
    
    //  Concatenates a copy of the specified MyString to the end of this MyString.
    //  If str is empty, just return this MyString; otherwise, make a copy
    //  of this MyString and append a copy of str to it. 
    public MyString concat(MyString str) {
//        int strLength = str.length();
//        if (str.equals("")) {
//            return this;
//        } else {
//            String str1 = this.toString() + str.toString();
//            MyString newMyString = MyStringHelper(str, 0);
//            return newMyString;
//        }
        return this;
    }
    
    
    // if oldchar does not occur in this MyString, return this;
    // else make a copy of this MyString and replace all occurrences
    // of oldChar by newChar
    public MyString replace(char oldChar, char newChar) {
        return replaceHelper(oldChar, newChar, 0);
    }
    
    // replace Helper method
    public MyString replaceHelper(char oldChar, char newChar, int index) {
        if(oldChar == '\u0000' || this == null){
            return this;
        }
        char ch = this.charAt(index);
        
        if (ch == oldChar) {
            ch = newChar;
            return replaceHelper(oldChar, newChar, index + 1);
        } else {
            return replaceHelper(oldChar, newChar, index + 1);
        }
        
    }
    
     
    // Returns the length of this MyString.
    public int length() {
        return size;
    }
    
    
    // Returns a new MyString that is a substring of this string
    // the first index is inclusive and the second is exclusive, i.e., 
    // substring(2, 4) of a MyString of "abcdefg" would be a Mystring of "cd" NOT "cde".
    public MyString substring(int beginIndex, int endIndex) {
        return substringHelper(beginIndex, endIndex, 0);
    }
    
    // substring Helper method
    private MyString substringHelper(int beginIndex, int endIndex, int index) {
        if (beginIndex < 0 || endIndex > size || beginIndex > endIndex) {
            throw new StringIndexOutOfBoundsException();
        }
        
        Node Q = new Node();
        
        if (beginIndex == endIndex) {
            return this;
        } else if (index == beginIndex) {
            Q = new Node(this.charAt(index));
            return substringHelper(beginIndex + 1, endIndex, index + 1);
        } else {
            return substringHelper(beginIndex, endIndex, index + 1);
        }
    }
    
    
    // Returns the MyString representation of the int argument; note that the digits should be
    // in their normal order (NOT reversed as in the last assignment).
    //public static MyString valueOf(int i) {
    //    return ;
    //}
    
    
    
    // Returns the integer corresponding to the MyString argument
    // or an exception if input is ill-formed (i.e., does not represent an integer); note that
    // the integer may be positive or negative or 0; don't worry about the case of "+234" which
    // is a legal integer in Java.
    //  public static int parseInt(MyString s) throws NumberFormatException {
    
    // }
    
    
    public static void main(String[] args) {
        MyString s = new MyString("hi there");
        MyString t = new MyString("hi there folks");
        MyString u = new MyString("here");
        MyString v = new MyString("");
        System.out.println("s = \"" + s + "\"");
        System.out.println("t = \"" + t + "\"");
        System.out.println("u = \"" + u + "\"");
        System.out.println("v = \"" + v + "\"");
        
        System.out.println("\ns.charAt(0) = h");                
        System.out.println(s.charAt(0));
        System.out.println("\ns.charAt(6) = r");                
        System.out.println(s.charAt(6));
        
        System.out.println("\ns.length() = 8");                
        System.out.println(s.length());
        System.out.println("\nv.length() = 0");                
        System.out.println(v.length());
        
        System.out.println("\ns.indexOf('e') = 5");                
        System.out.println(s.indexOf('e'));
        System.out.println("\ns.indexOf('z') = -1");                
        System.out.println(s.indexOf('z'));
        
        System.out.println("\ns.indexOf(u) = 4");              // needs fixing  
        System.out.println(s.indexOf(u));
        System.out.println("\ns.indexOf(t) = -1");                
        System.out.println(s.indexOf(t));
        
        System.out.println("\ns.compareTo(s) = 0");
        System.out.println(s.compareTo(s));
        System.out.println("\ns.compareTo(t) = -1");
        System.out.println(s.compareTo(t));
        System.out.println("\ns.compareTo(u) = 1");
        System.out.println(s.compareTo(s));
        System.out.println("\nu.compareTo(v) = 1");
        System.out.println(u.compareTo(v));
        
//        System.out.println("\ns.concat(t) = hi therehi there folks");                
//        System.out.println(s.concat(t));
//        System.out.println("\nv.concat(s) = hi there");                
//        System.out.println(v.concat(s));
//            
//        System.out.println("\ns.replace('i', 'o') = ho there");                
//        System.out.println(s.replace('i', 'o'));
//        System.out.println("\ns.replace('z', 'y') = hi there");                
//        System.out.println(s.replace('z', 'y'));
//        
//        System.out.println("\ns.substring(3, 7) = ther");                
//        System.out.println(s.substring(3, 7));
//        
//        System.out.println("\nvalueOf(-456) = -456");                
//        System.out.println(valueOf(-456));
//        System.out.println("\nvalueOf(31415) = 31415");                
//        System.out.println(valueOf(31415));
//        
//        System.out.println("\nparseInt(new MyString("31415")) = 31415");                
//        System.out.println(parseInt(new MyString("31415")));
//        System.out.println("\nparseInt(new MyString("-456")) = -456");                
//        System.out.println(parseInt(new MyString("-456")));
//        System.out.println("\nparseInt(new MyString("3.1415")) = Error: Illegal Number Format!);                
//        System.out.println(parseInt(new MyString("3.1415")));
            
     
    }
    
}