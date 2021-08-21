package com.jasi;/*
 *Project: Blockchain
 * Author: Ryan
 * Date: 15/08/2021 16:38
 */

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static com.jasi.StringUtil.applySha256;


public class Block implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int id;
    private long timeStamp = new Date().getTime();
    private int magicNumber;
    private String hash;
    private String previousHash = "0";
    private Block  next;
    private float secondUsed;
    private static final String path = "C:\\Users\\Ryan\\OneDrive\\Documentos\\blockchain\\block.txt";

    private static int lastMagicNumber;
    private static int numberOfZerosInHash;

    //Constructors, getters and setters
    public Block(int id) {
        this.id = id;
        this.hash = createHash();
    }

    public Block(int id, String previousHash) {
        this.id = id;
        this.hash = createHash();
        this.previousHash = previousHash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public Block getNext() {
        return next;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return id + timeStamp + magicNumber + hash +  previousHash;
    }

    //Other methods
    private String createHash(){
        String hash  = applySha256("");
        while (!(hash.substring(0,numberOfZerosInHash).equals(StringUtil.fillWithZeros(numberOfZerosInHash)))){
            lastMagicNumber = ++lastMagicNumber;
            this.magicNumber = lastMagicNumber;
            hash = applySha256(String.valueOf(this.toString()));
        }
        return hash;
    }

    public void print() throws IOException {
        Block block = this;
        while (block != null){
            System.out.println("\nBlock:");
            System.out.println("Id: " + block.id);
            System.out.println("Timestamp: " + block.timeStamp);
            System.out.println("Magic number: " + block.magicNumber);
            System.out.println("Hash of the previous block:");
            System.out.println(block.previousHash);
            System.out.println("Hash of the block:");
            System.out.println(block.hash);
            System.out.println("Block was generating for "+ (int)block.secondUsed + " seconds");
            block = block.next;
        }
    }

    public static Block generateBlockChain(int numberOfBlocks, int numberOfZeros) throws IOException, ClassNotFoundException {
        int counter;
        Block root;
        Block block;

        if(!Files.exists(Paths.get(Block.path))){
            counter = 1;
            block = new Block(1);
            root = block;
        }else{
            counter = 0;
            root = (Block) SerializeUtil.deserialize(Block.path);
            root.isValid();
            block = root;
            while (!(block.next == null)){
                block = block.next;
            }
        }

        numberOfZerosInHash = numberOfZeros;

        for(int i = counter; i<numberOfBlocks; i++){
            long start = System.currentTimeMillis();
            block.next = new Block(block.id + 1, block.hash);
            block = block.next;
            long end = System.currentTimeMillis();
            block.secondUsed = (end - start) / 1000F;
            SerializeUtil.serialize(root, Block.path);
        }
        return root;
    }

    //validate blockchain
    public boolean isValid(){
        Block block = this;
        while (block != null){
            if(!this.previousHash.equals("0")){
                if(!applySha256(this.toString()).equals(this.next.previousHash)){
                    throw new RuntimeException("Error Invalid Blockchain");
                }
            }
            block = block.next;
        }//while
        return true;
    }
}
