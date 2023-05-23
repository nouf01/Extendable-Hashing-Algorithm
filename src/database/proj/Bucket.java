/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.proj;

import java.util.LinkedList;

/**
 *
 * @author nouf3
 */
public class Bucket {
    int localDepth;
    LinkedList<Integer> bucketKeys;
    int numKeys;
    String value;
    int size;

    public Bucket(int localDepth, int size, String value) {
        this.localDepth = localDepth;
        this.bucketKeys = new LinkedList<Integer>();
        this.size = size;
        this.value = value;
        numKeys = 0;
    }
    
    public boolean isFull(){
        return numKeys == size;
    }
    
    public void add(int newKey){
        
        bucketKeys.add(newKey);
        numKeys++;
    }
    
    public void remove(int newKey){
        for(int i =0; i<numKeys; i++){
            if (newKey == bucketKeys.get(i)){
                bucketKeys.remove(i);
            }
        }
        numKeys--;
    }
    
    
    
}
