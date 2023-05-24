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
                    //break;
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
        if (globalD == (olddepth) && globalD < 8) {
            int oldGlobal = globalD;
            globalD++;
            constructHash(globalD);
            System.out.println("Global Depth Updated from "+oldGlobal +" to "+globalD);
        }else if(globalD == 8){
            System.out.println("Cant expand more than 8");
        }
        else{
            linkBucketToHash();
        }
        ReAdd(keys);
        keyList.add(key);
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
        for (int i = 0; i < 8-x.length(); i++) {
            value += "0";
        }
        return value.concat(x);
    }
    
    public void printHash() {
        System.out.println("_____________________________________");
        System.out.println("printHash");
        LinkedList<String> pointToB = new LinkedList<String>();
        for (int h = 0; h < hashList.size(); h++) {
            Hash_Instance h1 = hashList.get(h);
            pointToB.add(h1.getValue());
            Bucket b1 = bucketsList.get(h1.getBucket());
            if (h != (hashList.size() - 1) && h1.getBucket() != hashList.get(h + 1).getBucket()) {
                System.out.println("");
                for (int p = 0; p < pointToB.size(); p++) {
                    System.out.print(" " + pointToB.get(p));
                }
                System.out.print(" points to " + b1.value + " Keys: ");
                System.out.println("");
                for (int k = 0; k < b1.bucketKeys.size(); k++) {
                    System.out.print(b1.bucketKeys.get(k) + " ");
                }
                pointToB.clear();
                System.out.println("");
            } else if (h == hashList.size() - 1) {
                System.out.println("");
                for (int p = 0; p < pointToB.size(); p++) {
                    System.out.print(" " + pointToB.get(p));
                }
                System.out.print(" points to " + b1.value + " Keys: ");
                System.out.println("");
                for (int k = 0; k < b1.bucketKeys.size(); k++) {
                    System.out.print(b1.bucketKeys.get(k) + " ");
                }
                pointToB.clear();
                System.out.println("");
            }
        }
    }
    public String output() {
        String output = new String();
        output = output.concat("\n__________________________________________");
        output = output.concat("\n_________________ OutPut __________________");
        output = output.concat("\n Global Depth is: "+globalD);
        //System.out.println("printHash");
        LinkedList<String> pointToB = new LinkedList<String>();
        for (int h = 0; h < hashList.size(); h++) {
            Hash_Instance h1 = hashList.get(h);
            pointToB.add(h1.getValue());
            Bucket b1 = bucketsList.get(h1.getBucket());
            if (h != (hashList.size() - 1) && h1.getBucket() != hashList.get(h + 1).getBucket()) {
                output = output.concat("\n");
                for (int p = 0; p < pointToB.size(); p++) {
                    output = output.concat(" " + pointToB.get(p));
                }
                output = output.concat(" points to " + b1.value + " Keys: ");
                output = output.concat("\n");
                for (int k = 0; k < b1.bucketKeys.size(); k++) {
                    output = output.concat(b1.bucketKeys.get(k) + " ");
                }
                pointToB.clear();
                output = output.concat("\n");
            } else if (h == hashList.size() - 1) {
                output = output.concat("\n");
                for (int p = 0; p < pointToB.size(); p++) {
                    output = output.concat(" " + pointToB.get(p));
                }
                output = output.concat(" points to " + b1.value + " Keys: ");
                output = output.concat("\n");
                for (int k = 0; k < b1.bucketKeys.size(); k++) {
                    output = output.concat(b1.bucketKeys.get(k) + " ");
                }
                pointToB.clear();
                output = output.concat("\n");
            }
        }
        return output;
    }
    public void printTable(){
        System.out.println("_____________________________________");
        System.out.println("printTable");
        for(int b =0; b<bucketsList.size(); b++){
            Bucket curBuck = bucketsList.get(b);
            LinkedList<String> pointToB = new LinkedList<String>();
            for(int h =0; h<hashList.size(); h++){
                String pointer = bucketsList.get(hashList.get(h).getBucket()).value;
                if(pointer.equals(curBuck.value)){
                    pointToB.add(hashList.get(h).getValue());
                }
            }
            for(int p=0; p<pointToB.size(); p++){
                System.out.print(" "+pointToB.get(p));
            }
            System.out.println(" points to " +curBuck.value + " Keys: ");
            for(int k =0; k<curBuck.bucketKeys.size(); k++){
                System.out.print(curBuck.bucketKeys.get(k) + " " );
            }
        }
    }
    public String lookUp(int key) {
        System.out.println("lookUp() ");
        String hashValue = hash(key);
        String subHash = hashValue.substring(0, globalD);
        //System.out.println("Key "+key +" Hash Rep: " +hashValue +" Sub key rep "+subHash);
        for (int i = 0; i < hashList.size(); i++) {
            Hash_Instance hObject = hashList.get(i);
            if (subHash.equals(hObject.getValue())) {
                //System.out.println("is equal to hash value : " +hObject.getValue());
                int index = hObject.getBucket(); //return index of the bucket where overflow happened
                Bucket buc = bucketsList.get(index); //use the index to get bucket obj from buckList
                LinkedList<Integer> keys = buc.bucketKeys;
                for (int k = 0; k < keys.size(); k++) {
                    if (keys.get(k) == key) {
                        return "Key " +key +" Found in Hash "+hObject.getValue()+" which points Bucket: "+buc.value;
                    }
                }
            }
        }
        return"Key is not found ";
    }
    public boolean deleteKey(int key) {
        System.out.println("delete( ");
        boolean found = false;
        int whereInKeyList = 0;
        for (int i = 0; i < keyList.size(); i++) {//check if it even exists
            if (key == keyList.get(i)) {
                System.out.println(key + " does actually Exist");
                whereInKeyList = i;
                found = true;
            }
        }
        if (!found) {
            System.out.println("this key does not exist");
            return false;

        }
        String hashValue = hash(key);
        String subHash = hashValue.substring(0, globalD);
        System.out.println("Key "+key +" Hash Rep: " +hashValue +" Sub key rep "+subHash);
        boolean buddyFound = false;
        for (int i = 0; i < hashList.size(); i++) {
            Hash_Instance hObject = hashList.get(i);
            if (subHash.equals(hObject.getValue())) {
                System.out.println("is equal to hash value : " +hObject.getValue());
                int index = hObject.getBucket(); //return index of the bucket where overflow happened
                Bucket buc = bucketsList.get(index); //use the index to get bucket obj from buckList
                LinkedList<Integer> keys = buc.bucketKeys;
                for (int k = 0; k < keys.size(); k++) {
                    if (keys.get(k) == key) {
                        System.out.println("Key " +key +" Found in Bucket: "+buc.value);
                        buc.remove(key);
                        keyList.remove(whereInKeyList);
                        break;
                    }
                }
                System.out.println("After Break");
                if(keys.isEmpty()){
                    int numOfBit = buc.localDepth - 1;
                    String bucValue = buc.value;
                    String subBuck = bucValue.substring(0, numOfBit);
                    for (int s = 0; s < hashList.size(); s++){// to find buddy bucket
                        Hash_Instance h = hashList.get(s);
                        String hashTableVal =  h.getValue();
                        String subVal = hashTableVal.substring(0, numOfBit);
                        if(subBuck.equals(subVal) && ! hashTableVal.equals( bucValue)){
                            Bucket hashBuck = bucketsList.get(h.getBucket());
                            if(hashBuck.localDepth == buc.localDepth && bucketsList.size()!= 2 ){
                                hashBuck.localDepth--;
                                String subVal2 = hashTableVal.substring(0, hashBuck.localDepth);
                                hashBuck.setValue(subVal2);
                                bucketsList.remove(index);
                                linkBucketToHash();
                                hObject.setBucket(h.getBucket());
                                buddyFound = true;
                            }
                        }
                    }
                }
            }
        }
        if(buddyFound && hashList.size()!= 2 ){
            
            boolean equalFound = false;
            for(int r =0; r<bucketsList.size(); r++){
                Bucket curBuck = bucketsList.get(r);
                if(curBuck.localDepth == globalD){
                    equalFound = true;
                }
            }
            if(!equalFound && hashList.size()!= 2){
                globalD--;
                constructHash(globalD);
            }
        }
        
        return true;
    }
}
