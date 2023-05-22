/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.proj;
import java.util.*;

public class DatabaseProj {
    
    public static void main(String[] args) {
       //Enter The Bucket Size
       HashTable t1 = new HashTable(3);
       t1.addKeys(20,true);
       t1.addKeys(40,true);
       t1.addKeys(10,true);
       t1.addKeys(30,true);
       t1.addKeys(15,true);
       t1.printHash();
       
    }
    
    
}
