/**
 * 哈希算法：Java内部实现的Hash算法
 */
package com.example.system.class21;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.xml.bind.DatatypeConverter;

public class Code01_Hash {
    private static class Hash {
        private MessageDigest hash;

        public Hash(String algorithm) {
            try {
                hash = MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        public String hashCode(String input) {
            byte[] data = hash.digest(input.getBytes());
            return DatatypeConverter.printHexBinary(data).toUpperCase();
        }
    }

    public static void main(String[] args) {
        System.out.println("支持的算法：");
        for (String str : Security.getAlgorithms("MessageDigest")) {
            System.out.println(str);
        }
        System.out.println("=======");

        String algorithm = "MD5";
        Hash hash = new Hash(algorithm);

        String input1 = "wangjichengwangjicheng1";
        String input2 = "wangjichengwangjicheng2";
        String input3 = "wangjichengwangjicheng3";
        String input4 = "wangjichengwangjicheng4";
        String input5 = "wangjichengwangjicheng5";
        System.out.println(hash.hashCode(input1));
        System.out.println(hash.hashCode(input2));
        System.out.println(hash.hashCode(input3));
        System.out.println(hash.hashCode(input4));
        System.out.println(hash.hashCode(input5));
    }
}
