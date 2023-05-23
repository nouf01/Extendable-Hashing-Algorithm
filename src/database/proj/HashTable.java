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
    
    public void constructHash(int globalDepth){
        //printBuckets();
        System.out.println("** construct hash **");
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
        System.out.println("sec link finishes");
        //printBuckets();
    }
    public void printBuckets(){
        for(int h =0; h<bucketsList.size(); h++){
            Bucket b1 = bucketsList.get(h);
            System.out.println(b1.value + " Keys: ");
            for(int k =0; k<b1.bucketKeys.size(); k++){
                System.out.print(b1.bucketKeys.get(k) + " " );
            }
            System.out.println("");
        }
    }
    public void printHashInsta(){
        for(int h =0; h<hashList.size(); h++){
            Hash_Instance b1 = hashList.get(h);
            System.out.println(b1.getValue() + " hash  ");
            /*for(int k =0; k<b1.bucketKeys.size(); k++){
                System.out.print(b1.bucketKeys.get(k) + " " );
            }*/
            System.out.println("");
        }
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
        System.out.println("Firsl link finishes");
    }
    
    private void linkBucketToHash() {
        for (int i = 0; i < bucketsList.size(); i++) {
            Bucket current = bucketsList.get(i);
            String str = current.value; //0 or 1

            for (int h = 0; h < hashList.size(); h++) {
                Hash_Instance curH = hashList.get(h);
                String originalHash = curH.getValue();// 00 01 10 11
                String subHash = originalHash.substring(0, current.localDepth); //0 1
                System.out.println("Here Hash and bucket value");
                System.out.println(subHash);
                System.out.println(str);
                if (subHash.equals(str)) {
                    System.out.println("linked");
                    curH.setBucket(i);
                    break;
                }
            }
        }
        //re_Assign_Keys();
        //print output
    }
    public void printKeys(){
        System.out.println("printKeys() ");
        if(!keyList.isEmpty()){
            for(int i =0; i<keyList.size(); i++){
                System.out.println(keyList.get(i));
            }
        }
    }
    /*private void re_Assign_Keys(){
        System.out.println(" re_Assign_Keys() ");
        if(!keyList.isEmpty()){
            for(int i =0; i<keyList.size(); i++){
                addKeys(keyList.get(i), false);
            }
        }
    }*/
    
    /*private void constructHash2(int globalDepth){
        Bucket b1 = new Bucket(1, BucketSize, "0");
        Hash_Instance h1 = new Hash_Instance("0",0);
        Bucket b2 = new Bucket(1, BucketSize, "1");
        Hash_Instance h2 = new Hash_Instance("1",1);
        bucketsList.add(b1);
        bucketsList.add(b2);
        hashList.add(h1);
        hashList.add(h2);
    }*/
    
    public boolean addKeys(int key,boolean flag){
        System.out.println("AddKey() ");
        //if key already exist -> not added
        
        for(int i =0; i<keyList.size(); i++){
            if (key == keyList.get(i)){
                System.out.println("Already Exist");
                return false;
                //already exist
                
            }
        }
        
        String hashValue = hash(key);
        String subHash = hashValue.substring(0, globalD);
        System.out.println("Key "+key +" Hash Rep: " +hashValue +" Sub key rep "+subHash);
        for(int i =0; i<hashList.size(); i++){
            Hash_Instance hObject = hashList.get(i);
            if(subHash.equals(hObject.getValue())){
                System.out.println("is equal to hash value : " +hObject.getValue());
                int index = hObject.getBucket(); //return index of the bucket where overflow happened
                Bucket buc = bucketsList.get(index); //use the index to get bucket obj from buckList
                if(buc.isFull()){
                    System.out.println("bucket full");
                    increaseLocal(key,index);
                }  
                else{
                    buc.add(key);
                    keyList.add(key);
                }
                
            }
        }
        return true;
    }
    public boolean ReAdd(LinkedList<Integer> keys) {
        System.out.println("ReAdd() ");
        for (int k = 0; k < keys.size(); k++) {
            Integer key = keys.get(k);
            String hashValue = hash(key);
            String subHash = hashValue.substring(0, globalD);
            System.out.println("Key " + key + " Hash Rep: " + hashValue + " Sub key rep " + subHash);
            for (int i = 0; i < hashList.size(); i++) {
                Hash_Instance hObject = hashList.get(i);
                if (subHash.equals(hObject.getValue())) {
                    System.out.println("is equal to hash value : " + hObject.getValue());
                    int index = hObject.getBucket(); //return index of the bucket where overflow happened
                    Bucket buc = bucketsList.get(index); //use the index to get bucket obj from buckList
                    if (buc.isFull()) {
                        System.out.println("bucket full");
                        increaseLocal(key, index);
                    } else {
                        buc.add(key);
                        //keyList.add(key);
                    }

                }
            }
        }

        return true;
    }
    
    private void increaseLocal(int key, int index) {// index is index of over flow bucket, i index
        System.out.println("increaseLocal()");
        //To Do increase depth
        LinkedList<Integer> keys = bucketsList.get(index).bucketKeys;
        /*for(int q=0; q<keys.size(); q++){
            for(int z=0; z<keyList.size(); z++){
                if(keys.get(q) == keyList.get(z)){
                    keyList.remove(z);
                }
            }
        }*/
        int olddepth = bucketsList.get(index).localDepth;
        String oldValue = bucketsList.get(index).value;
        keys.add(key);
        bucketsList.remove(index);
        Bucket b1 = new Bucket(olddepth + 1, BucketSize, oldValue + "0");
        Bucket b2 = new Bucket(olddepth + 1, BucketSize, oldValue + "1");
        bucketsList.add(b1);
        bucketsList.add(b2);
        if (globalD == (olddepth)) {
            int oldGlobal = globalD;
            globalD++;
            constructHash(globalD);
            System.out.println("Global Depth Updated from "+oldGlobal +" to "+globalD);
        }
        else{
            linkBucketToHash();
        }
        ReAdd(keys);
        /*for (int j = 0; j < keys.size(); j++) {
            String hashValue2 = hash(keys.get(j));
            String subHash2 = hashValue2.substring(0, olddepth + 1);
            System.out.println("Key "+keys.get(j) +" Hash Rep: " +hashValue2 +" Sub key rep "+subHash2);
            if (subHash2.equals(b1.value)) {
                addKeys(key,true);
                //keys.remove(j);
            }
            else if (subHash2.equals(b2.value)){
                addKeys(key,true);
                
                //keys.remove(j);
            }
        }*/
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
        System.out.println("_____________________________________");
        System.out.println("printHash");
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
