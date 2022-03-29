package com.example.system.class31;

import java.util.HashMap;
import java.util.Map.Entry;

public class C01_HuffmanTreeTest {
    public static void main(String[] args) {
        HuffmanTree ht = new HuffmanTree();
        testHuffmanForm(ht);
        test(ht);
        testBigLenth(ht);
    }

    /**
     * 根据词频表生成哈夫曼编码表
     */
    private static void testHuffmanForm(HuffmanTree ht) {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('A', 60);
        map.put('B', 45);
        map.put('C', 13);
        map.put('D', 69);
        map.put('E', 14);
        map.put('F', 5);
        map.put('G', 3);
        HashMap<Character, String> huffmanForm = ht.getHuffmanForm(map);
        for (Entry<Character, String> entry : huffmanForm.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    private static void test(HuffmanTree ht) {
        System.out.println("====================");
        // str是原始字符串
        String str = "CBBBAABBACAABDDEFBA";
        System.out.println(str);
        // countMap是根据str建立的词频表
        HashMap<Character, Integer> countMap = ht.getCountMap(str);
        // hf是根据countMap生成的哈夫曼编码表
        HashMap<Character, String> hf = ht.getHuffmanForm(countMap);
        // huffmanEncode是原始字符串转译后的哈夫曼编码
        String huffmanEncode = ht.huffmanEncode(str, hf);
        System.out.println(huffmanEncode);
        // huffmanDecode是哈夫曼编码还原成的原始字符串
        String huffmanDecode = ht.huffmanDecode(huffmanEncode, hf);
        System.out.println(huffmanDecode);
        System.out.println("====================");
    }

    /**
     * 大样本随机测试
     */
    private static void testBigLenth(HuffmanTree ht) {
        System.out.println("大样本随机测试开始");
        // 字符串最大长度
        int len = 500;
        // 所含字符种类
        int range = 26;
        // 随机测试进行的次数
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String test = randomString(len, range);
            HashMap<Character, Integer> counts = ht.getCountMap(test);
            HashMap<Character, String> form = ht.getHuffmanForm(counts);
            String encode = ht.huffmanEncode(test, form);
            String decode = ht.huffmanDecode(encode, form);
            if (!test.equals(decode)) {
                System.out.println(test);
                System.out.println(encode);
                System.out.println(decode);
                System.out.println("出错了!");
            }
        }
        System.out.println("大样本随机测试结束");
    }

    private static String randomString(int size, int range) {
        int len = (int) (Math.random() * size) + 1;
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * range) + 'a');
        }
        return String.valueOf(str);
    }
}
