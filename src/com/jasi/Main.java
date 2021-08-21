package com.jasi;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Scanner sc = new Scanner(System.in);
        Block.generateBlockChain(20,5).print();
    }
}

