package com.example.primary.class03;

import java.util.HashMap;
import java.util.TreeMap;

public class C14_HashTreeMap {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        // 新增
        map.put("wjc", "王吉成");
        System.out.println(map.containsKey("wjc"));
        System.out.println(map.get("wjc"));

        // 更新.
        map.put("wjc", "王燕");
        System.out.println(map.get("wjc"));

        map.remove("wjc");
        System.out.println(map.get("wjc"));

        // 基础类型按值传递
        HashMap<Integer, String> map2 = new HashMap<>();
        map2.put(123456, "i'm 123456");
        Integer a = 123456;
        Integer b = 123456;
        System.out.println("引用不一样" + (a == b));
        System.out.println(map2.containsKey(a));
        System.out.println(map2.containsKey(b));

        // 非基础类型按引用传递
        Node node1=new Node(1);
        Node node2=new Node(1);
        HashMap<Node,String> map3=new HashMap<>();
        map3.put(node1,"i'm in");
        System.out.println(map3.containsKey(node1));
        System.out.println(map3.containsKey(node2));

        TreeMap<Integer, String> treeMap1=new TreeMap<>();
        treeMap1.put(3,"i'm 3");
        treeMap1.put(8,"i'm 8");
        treeMap1.put(2,"i'm 2");
        System.out.println(treeMap1.containsKey(3));
        System.out.println(treeMap1.get(8));

        treeMap1.put(8,"i'm new 8");
        System.out.println(treeMap1.get(8));

        treeMap1.remove(2);
        System.out.println(treeMap1.containsKey(2));

        System.out.println(treeMap1.firstKey());
        System.out.println(treeMap1.lastKey());

        // <=7 离7最近的key
        System.out.println(treeMap1.floorKey(7));

        // >=7 离7最近的key
        System.out.println(treeMap1.ceilingKey(7));
    }

    public static class Node {
        private int value;

        public Node(int value) {
            this.value = value;
        }
    }
}
