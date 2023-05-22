/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.proj;

/**
 *
 * @author nouf3
 */
public class Hash_Instance {
    private String value;
    private int indexOfbucket; //pointer to bucket 

    public Hash_Instance(String value, int indexBuc) {
        this.value = value;
        this.indexOfbucket = indexBuc;
    }
    
    public void setBucket(int newB){
        this.indexOfbucket = newB;
    }
    
    public int getBucket(){
        return this.indexOfbucket;
    }

    public String getValue() {
        return value;
    }
}
