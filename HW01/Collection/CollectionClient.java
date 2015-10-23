public class CollectionClient {
    public static void main(String [] args) { 
        Collectable C = new Collection();             
        
        System.out.println("Inserting 2, 3, and 13:"); 
        C.insert(2);  
        C.insert(3);  
        C.insert(13); 
        C.list();                           
        System.out.println("Deleting 2:"); 
        C.delete(2);
        C.list(); 
        System.out.println("Inserting 42 and 2:"); 
        C.insert(42); 
        C.insert(2); 
        C.list(); 
        System.out.println("Deleting 13:");
        C.delete(13);
        C.list(); 
        System.out.println("Deleting 13 again:");
        C.delete(13); 
        C.list(); 
        if(C.member(2)) 
            System.out.println("Member: " + 2); 
        else
            System.out.println("Not a member: " + 2); 
        if(C.member(12)) 
            System.out.println("Member: " + 12); 
        else
            System.out.println("Not a member: " + 12); 
        C.delete(3);
        C.delete(42);
        C.delete(2);
        System.out.println("Deleted all elements:");
        C.list();
        
        
    }    

}