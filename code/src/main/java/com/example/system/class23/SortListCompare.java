/**
 * avl、sbt、skiplist三种结构的测试文件
 */
package com.example.system.class23;

import java.util.TreeMap;

import com.example.system.class23.C01_AVLTreeMap.AVLTreeMap;
import com.example.system.class23.C02_SizeBalancedTreeMap.SizeBalancedTreeMap;
import com.example.system.class23.C03_SkipListMap.SkipListMap;

public class SortListCompare {
    public static void main(String[] args) {
        functionTest();
        System.out.println("======");
        performanceTest();
    }

    private static void functionTest() {
        System.out.println("功能性测试：");

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        AVLTreeMap<Integer, Integer> avl = new AVLTreeMap<>();
        SizeBalancedTreeMap<Integer, Integer> sbt = new SizeBalancedTreeMap<>();
        SkipListMap<Integer, Integer> skip = new SkipListMap<>();

        int maxK = 500;
        int maxV = 50000;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int addK = (int) (Math.random() * maxK);
            int addV = (int) (Math.random() * maxV);
            treeMap.put(addK, addV);
            avl.put(addK, addV);
            sbt.put(addK, addV);
            skip.put(addK, addV);

            int removeK = (int) (Math.random() * maxK);
            treeMap.remove(removeK);
            avl.remove(removeK);
            sbt.remove(removeK);
            skip.remove(removeK);

            int queryK = (int) (Math.random() * maxK);
            if (treeMap.containsKey(queryK) != avl.containKey(queryK)
                    || avl.containKey(queryK) != sbt.containKey(queryK)
                    || sbt.containKey(queryK) != skip.containKey(queryK)) {
                System.out.println("Oops: ContainKey");
                System.out.println(treeMap.containsKey(queryK));
                System.out.println(avl.containKey(queryK));
                System.out.println(sbt.containKey(queryK));
                System.out.println(skip.containKey(queryK));
            }

            if (treeMap.containsKey(queryK)) {
                int v1 = treeMap.get(queryK);
                int v2 = avl.get(queryK);
                int v3 = sbt.get(queryK);
                int v4 = skip.get(queryK);
                if (v1 != v2 || v2 != v3 || v3 != v4) {
                    System.out.println("Oops: get");
                    System.out.println(treeMap.get(queryK));
                    System.out.println(avl.get(queryK));
                    System.out.println(sbt.get(queryK));
                    System.out.println(skip.get(queryK));
                }

                Integer f1 = treeMap.floorKey(queryK);
                Integer f2 = avl.floorKey(queryK);
                Integer f3 = sbt.floorKey(queryK);
                Integer f4 = skip.floorKey(queryK);
                if ((f1 == null && (f2 != null || f3 != null || f4 != null))
                        || (f1 != null && (f2 == null || f3 == null || f4 == null))) {
                    System.out.println("Oops: floorkey");
                    System.out.println(treeMap.floorKey(queryK));
                    System.out.println(avl.floorKey(queryK));
                    System.out.println(sbt.floorKey(queryK));
                    System.out.println(skip.floorKey(queryK));
                } else if (f1 != null) {
                    int ans1 = f1;
                    int ans2 = f2;
                    int ans3 = f3;
                    int ans4 = f4;
                    if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
                        System.out.println("Oops: floorkey");
                        System.out.println(treeMap.floorKey(queryK));
                        System.out.println(avl.floorKey(queryK));
                        System.out.println(sbt.floorKey(queryK));
                        System.out.println(skip.floorKey(queryK));
                    }
                }

                f1 = treeMap.ceilingKey(queryK);
                f2 = avl.ceillingKey(queryK);
                f3 = sbt.ceilingKey(queryK);
                f4 = skip.ceilingKey(queryK);
                if ((f1 == null && (f2 != null || f3 != null || f4 != null))
                        || (f1 != null && (f2 == null || f3 == null || f4 == null))) {
                    System.out.println("Oops: ceilingKey");
                    System.out.println(treeMap.ceilingKey(queryK));
                    System.out.println(avl.ceillingKey(queryK));
                    System.out.println(sbt.ceilingKey(queryK));
                    System.out.println(skip.ceilingKey(queryK));
                } else if (f1 != null) {
                    int ans1 = f1;
                    int ans2 = f2;
                    int ans3 = f3;
                    int ans4 = f4;
                    if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
                        System.out.println("Oops: ceilingKey");
                        System.out.println(treeMap.ceilingKey(queryK));
                        System.out.println(avl.ceillingKey(queryK));
                        System.out.println(sbt.ceilingKey(queryK));
                        System.out.println(skip.ceilingKey(queryK));
                    }
                }
            }

            Integer f1 = treeMap.firstKey();
            Integer f2 = avl.firstKey();
            Integer f3 = sbt.firstKey();
            Integer f4 = skip.firstKey();
            if ((f1 == null && (f2 != null || f3 != null || f4 != null))
                    || (f1 != null && (f2 == null || f3 == null || f4 == null))) {
                System.out.println("Oops: firstKey");
                System.out.println(treeMap.firstKey());
                System.out.println(avl.firstKey());
                System.out.println(sbt.firstKey());
                System.out.println(skip.firstKey());
            } else if (f1 != null) {
                int ans1 = f1;
                int ans2 = f2;
                int ans3 = f3;
                int ans4 = f4;
                if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
                    System.out.println("Oops: firstKey");
                    System.out.println(treeMap.firstKey());
                    System.out.println(avl.firstKey());
                    System.out.println(sbt.firstKey());
                    System.out.println(skip.firstKey());
                }
            }

            f1 = treeMap.lastKey();
            f2 = avl.lastKey();
            f3 = sbt.lastKey();
            f4 = skip.lastKey();
            if ((f1 == null && (f2 != null || f3 != null || f4 != null))
                    || (f1 != null && (f2 == null || f3 == null || f4 == null))) {
                System.out.println("Oops: lastKey");
                System.out.println(treeMap.lastKey());
                System.out.println(avl.lastKey());
                System.out.println(sbt.lastKey());
                System.out.println(skip.lastKey());
            } else if (f1 != null) {
                int ans1 = f1;
                int ans2 = f2;
                int ans3 = f3;
                int ans4 = f4;
                if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
                    System.out.println("Oops: lastKey");
                    System.out.println(treeMap.lastKey());
                    System.out.println(avl.lastKey());
                    System.out.println(sbt.lastKey());
                    System.out.println(skip.lastKey());
                }
            }
        }

        System.out.println("功能测试结束");
    }

    private static void performanceTest() {
        System.out.println("性能测试：");
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        AVLTreeMap<Integer, Integer> avl = new AVLTreeMap<>();
        SizeBalancedTreeMap<Integer, Integer> sbt = new SizeBalancedTreeMap<>();
        SkipListMap<Integer, Integer> skip = new SkipListMap<>();

        long start;
        long end;
        int testTimes = 100000;

        System.out.println("顺序递增加入测试，数据规模 : " + testTimes);
        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            treeMap.put(i, i);
        }
        end = System.currentTimeMillis();
        System.out.println("treeMap 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            avl.put(i, i);
        }
        end = System.currentTimeMillis();
        System.out.println("avl 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            sbt.put(i, i);
        }
        end = System.currentTimeMillis();
        System.out.println("sbt 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            skip.put(i, i);
        }
        end = System.currentTimeMillis();
        System.out.println("skip 运行时间:" + (end - start) + "ms");

        System.out.println("顺序递增删除测试，数据规模 : " + testTimes);
        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            treeMap.remove(i);
        }
        end = System.currentTimeMillis();
        System.out.println("treeMap 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            avl.remove(i);
        }
        end = System.currentTimeMillis();
        System.out.println("avl 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            sbt.remove(i);
        }
        end = System.currentTimeMillis();
        System.out.println("sbt 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            skip.remove(i);
        }
        end = System.currentTimeMillis();
        System.out.println("skip 运行时间:" + (end - start) + "ms");

        System.out.println("顺序递减加入测试，数据规模 : " + testTimes);
        start = System.currentTimeMillis();
        for (int i = testTimes; i >= 0; i--) {
            treeMap.put(i, i);
        }
        end = System.currentTimeMillis();
        System.out.println("treeMap 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = testTimes; i >= 0; i--) {
            avl.put(i, i);
        }
        end = System.currentTimeMillis();
        System.out.println("avl 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = testTimes; i >= 0; i--) {
            sbt.put(i, i);
        }
        end = System.currentTimeMillis();
        System.out.println("sbt 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = testTimes; i >= 0; i--) {
            skip.put(i, i);
        }
        end = System.currentTimeMillis();
        System.out.println("skip 运行时间:" + (end - start) + "ms");

        System.out.println("顺序递减删除测试，数据规模 : " + testTimes);
        start = System.currentTimeMillis();
        for (int i = testTimes; i >= 0; i--) {
            treeMap.remove(i);
        }
        end = System.currentTimeMillis();
        System.out.println("treeMap 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = testTimes; i >= 0; i--) {
            avl.remove(i);
        }
        end = System.currentTimeMillis();
        System.out.println("avl 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = testTimes; i >= 0; i--) {
            sbt.remove(i);
        }
        end = System.currentTimeMillis();
        System.out.println("sbt 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = testTimes; i >= 0; i--) {
            skip.remove(i);
        }
        end = System.currentTimeMillis();
        System.out.println("skip 运行时间:" + (end - start) + "ms");

        System.out.println("随机加入测试，数据规模 : " + testTimes);
        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            treeMap.put((int)(Math.random()*i), i);
        }
        end = System.currentTimeMillis();
        System.out.println("treeMap 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            avl.put((int)(Math.random()*i), i);
        }
        end = System.currentTimeMillis();
        System.out.println("avl 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            sbt.put((int)(Math.random()*i), i);
        }
        end = System.currentTimeMillis();
        System.out.println("sbt 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            skip.put((int)(Math.random()*i), i);
        }
        end = System.currentTimeMillis();
        System.out.println("skip 运行时间:" + (end - start) + "ms");

        System.out.println("随机删除测试，数据规模 : " + testTimes);
        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            treeMap.remove((int)(Math.random()*i));
        }
        end = System.currentTimeMillis();
        System.out.println("treeMap 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            avl.remove((int)(Math.random()*i));
        }
        end = System.currentTimeMillis();
        System.out.println("avl 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            sbt.remove((int)(Math.random()*i));
        }
        end = System.currentTimeMillis();
        System.out.println("sbt 运行时间:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            skip.remove((int)(Math.random()*i));
        }
        end = System.currentTimeMillis();
        System.out.println("skip 运行时间:" + (end - start) + "ms");


        System.out.println("性能测试结束");
    }
}
