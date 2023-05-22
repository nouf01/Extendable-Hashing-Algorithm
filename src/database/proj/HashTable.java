/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.proj;

import java.util.*;

/**
 *
 * @author nouf3
 */
public class HashTable {
    
    public int BucketSize;
    public int globalD;
    LinkedList<Bucket> bucketsList;
    LinkedList<Hash_Instance> hashList;
    LinkedList<Integer> keyList;
    
    public HashTable(int BucketSize) {
        this.BucketSize = BucketSize;
        this.globalD = 1;
        this.bucketsList = new LinkedList<Bucket>();
        this.hashList = new LinkedList<Hash_Instance>();
        this.keyList = new LinkedList<Integer>();
        constructHash2();
    }
    
    private void constructHash(int globalDepth){
        System.out.println("here 4");
        hashList.clear();
        double x = Math.pow(2,globalDepth);
        for(int i =0; i<x; i++){
            String binaryRep = hash(i);//Integer.toBinaryString(i);
            //String subRep = binaryRep.split(globalDepth -1)[1];
            String newRep = "";
            for(int b=0; b<binaryRep.length(); b++){
                if(b >= binaryRep.length() - globalDepth){
                    newRep = newRep + binaryRep.charAt(b);
                }
            }
            Hash_Instance h2 = new Hash_Instance(newRep,0);
            hashList.add(h2);
        }
        linkBucketToHash();
    }
    
    private void constructHash2(){
        Bucket b1 = new Bucket(1, BucketSize,"0");
        Hash_Instance h1 = new Hash_Instance("0",0);
        Bucket b2 = new Bucket(1, BucketSize,"1");
        Hash_Instance h2 = new Hash_Instance("1",1);
        bucketsList.add(b1);
        bucketsList.add(b2);
        hashList.add(h1);
        hashList.add(h2);
        linkBucketToHash();
    }
    
    private void linkBucketToHash(){
        for(int i =0; i<bucketsList.size(); i++){
            Bucket current = bucketsList.get(i);
            String str = current.value;
            for(int h=0; h<hashList.size(); h++){
                Hash_Instance curH = hashList.get(i);
                if(curH.equals(str)){
                    curH.setBucket(i);
                }
            }
        }
        //print output
    }
    
    private void constructHash2(int globalDepth){
        Bucket b1 = new Bucket(1, BucketSize, "0");
        Hash_Instance h1 = new Hash_Instance("0",0);
        Bucket b2 = new Bucket(1, BucketSize, "1");
        Hash_Instance h2 = new Hash_Instance("1",1);
        bucketsList.add(b1);
        bucketsList.add(b2);
        hashList.add(h1);
        hashList.add(h2);
    }
    
    public boolean addKeys(int key,boolean flag){
        if(flag){
        for(int i =0; i<keyList.size(); i++){
            if (key == keyList.get(i)){
                System.out.println("here 1");
                return false;
                //already exist
            }
        }}
        keyList.add(key);
        String hashValue = hash(key);
        String subHash = hashValue.substring(0, globalD);
        for(int i =0; i<hashList.size(); i++){
            System.out.println("here 2");
            Hash_Instance hObject = hashList.get(i);
            if(subHash.equals(hObject.getValue())){
                int index = hObject.getBucket();
                Bucket buc = bucketsList.get(index);
                if(buc.isFull()){
                    
                    System.out.println("here 3");
                    increaseLocal(key,index);
                }  
                else{
                    System.out.println("here 5");
                    buc.add(key);
                
                    //print output
                    
                }
                
            }
        }
        return true;
    }
    
    private void increaseLocal(int key, int index) {// index is index of over flow bucket, i index
        //To Do increase depth
        LinkedList<Integer> keys = bucketsList.get(index).bucketKeys;
        int olddepth = bucketsList.get(index).localDepth;
        String oldValue = bucketsList.get(index).value;
        keys.add(key);
        bucketsList.remove(index);
        Bucket b1 = new Bucket(olddepth + 1, BucketSize, oldValue + "0");
        Bucket b2 = new Bucket(olddepth + 1, BucketSize, oldValue + "1");
        for (int j = 0; j < keys.size(); j++) {
            String hashValue2 = hash(keys.get(j));
            String subHash2 = hashValue2.substring(0, olddepth + 1);
            if (subHash2.equals(b1.value)) {
                addKeys(key,false);
                keys.remove(j);
            }
            else /* (subHash2.equals(b2.value))*/{
                addKeys(key,false);
                
                keys.remove(j);
            }
        }
        if (globalD == (olddepth)) {
            globalD++;
            constructHash(globalD);
            linkBucketToHash();
        }
        else{
            linkBucketToHash();
        }
    }
    
    
    public static String hash(int key){
        //return key % 10;
        String x = Integer.toBinaryString(key);
        String value = ""; //1100000
        for (int i = 0; i < 6-x.length(); i++) {
            value += "0";
        }
        return value.concat(x);
    }
    
    public void printHash(){
        for(int h =0; h<hashList.size(); h++){
            Hash_Instance h1 = hashList.get(h);
            Bucket b1 = bucketsList.get(h1.getBucket());
            System.out.println(h1.getValue() + " points to " +b1.value + " Keys: ");
            for(int k =0; k<b1.bucketKeys.size(); k++){
                System.out.print(b1.bucketKeys.get(k) + " " );
            }
            System.out.println("");
        }
    }
    
}
