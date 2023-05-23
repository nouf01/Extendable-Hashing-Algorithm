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
       //Main m = new Main();
       HashTable t1 = new HashTable(3);
       t1.addKeys(255,true);
       t1.addKeys(40,true);
       t1.addKeys(10,true);
       t1.addKeys(30,true);
       t1.addKeys(15,true);
       t1.addKeys(35,true);
       t1.addKeys(7,true);
       t1.addKeys(26,true);
       t1.addKeys(18,true);
       t1.addKeys(22,true);
       t1.addKeys(5,true);
       t1.addKeys(42,true);
       t1.addKeys(13,true);
       t1.addKeys(128,true);
       t1.printHash();
       /*t1.deleteKey(30);
       t1.deleteKey(13);
       t1.deleteKey(26);
       t1.deleteKey(7);
       t1.deleteKey(5);
       t1.deleteKey(10);
       t1.deleteKey(15);
       t1.deleteKey(20);
       t1.deleteKey(18);
       t1.deleteKey(22);
       //System.out.println("*************************************");
       t1.printHash();
       //t1.lookUp(26);
       //System.out.println("*************************************");
      // t1.printBuckets();
       //t1.printHashInsta();
       t1.printKeys();*/
       
    }
    
    
}
