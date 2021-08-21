package com.jasi;/*
 *Project: Blockchain
 * Author: Ryan
 * Date: 15/08/2021 16:31
 */

import java.security.MessageDigest;

class StringUtil {
    /* Applies Sha256 to a string and returns a hash. */
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String fillWithZeros(int numberOfZeros){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<numberOfZeros; i++){
            stringBuilder.append("0");
        }
        return stringBuilder.toString();
    }
}