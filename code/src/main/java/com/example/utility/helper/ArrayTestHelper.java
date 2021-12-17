/**
 * 对数器
 * 数组的测试帮助类
 */
package com.example.utility.helper;

import java.util.Arrays;
import java.util.HashSet;

import com.example.utility.function.*;

public class ArrayTestHelper {
    /**
     * 交换
     */
    public static void swap(int[] arr, int l, int r) {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }

    /**
     * i和j是一个位置的话，会出错
     */
    public static void swapBinary(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    /**
     * 比较两个数组的处理算法的结果是否一致
     * 
     * @param m1 排序1
     * @param m2 排序2
     */
    public static void testSortMethod(SortMethod m1, SortMethod m2) {
        int times = 50000;
        int maxLength = 100;
        int maxValue = 100;
        System.out.println("begin test");
        for (int i = 0; i < times; i++) {
            int[] arr = generateRandomArray(maxLength, maxValue);
            int[] copy1 = copyArray(arr);
            int[] copy2 = copyArray(arr);

            m1.sort(copy1);
            m2.sort(copy2);

            if (!isEquals(copy2, copy1)) {
                System.out.println("error");
                printArray(arr);
                printArray(copy1);
                printArray(copy2);
                break;
            }
        }
        System.out.println("finish");
    }

    /**
     * 比较有序数组的二分查找方法是否一致
     * 
     * @param m1 二分
     * @param m2 迭代
     */
    public static void testBinarySearch(BinarySearchMethod m1, BinarySearchMethod m2) {
        int times = 50000;
        int maxLength = 100;
        int maxValue = 100;
        System.out.println("begin test");
        for (int i = 0; i < times; i++) {
            int[] arr = generateRandomArray(maxLength, maxValue);
            Arrays.sort(arr);

            int num = (int) (Math.random() * (maxValue + 1));
            if (m1.search(arr, num) != m1.search(arr, num)) {
                System.out.println("error");
                printArray(arr);
                System.out.println(m1.search(arr, num));
                System.out.println(m2.search(arr, num));
                break;
            }
        }
        System.out.println("finish");
    }

    /**
     * 比较在数组中查找出现K次的数，结构是否一致
     * @param m1 额外空间复杂度O(1) int[32]
     * @param m2 额外空间复杂度O(N) HashMap
     */
    public static void testKMTimes(KMTimesMethod m1, KMTimesMethod m2) {
        int kinds = 5;
        int range = 200;
        int maxValue = 9;
        int testTimes = 100000;
        System.out.println("begin test");
        for (int i = 0; i < testTimes; i++) {
            int a = (int) (Math.random() * maxValue) + 1; // a 1 ~ 9
            int b = (int) (Math.random() * maxValue) + 1; // b 1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (k == m)
                m++;

            int[] arr = generateRandomArray(range, kinds, k, m);
            int ans1 = m1.onlyKTimes(arr, k, m);
            int ans2 = m2.onlyKTimes(arr, k, m);
            if (ans1 != ans2) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finished");
    }

    /**
     * 生成随机数组
     */
    public static int[] generateRandomArray(int maxLength, int maxValue) {
        int[] ans = new int[(int) (Math.random() * (maxLength + 1))];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * maxValue);
        }
        return ans;
    }

    /**
     * 生成随机arr
     * 
     * @param range 数组值的取值范围
     * @param kinds 包含多少种数
     * @param k     只有一种数出现了K次
     * @param m     其他数都出现了M次
     * @return
     */
    public static int[] generateRandomArray(int range, int kinds, int k, int m) {
        // 50%概率使用k, 否则使用 [1 ~ m-1]间的数.
        int times = Math.random() < 0.5 ? k : ((int) (Math.random() * (m - 1)) + 1);

        // 数组总共包含多少种数.(2-7)
        int numKinds = (int) (Math.random() * kinds) + 2;
        int[] arr = new int[times + (numKinds - 1) * m];

        // 添充出现k次的数
        int ktime = randomNumber(range);
        int index = 0;
        for (; index < times; index++) {
            arr[index] = ktime;
        }
        numKinds--;

        // 保证每种数都不同
        HashSet<Integer> set = new HashSet<>();
        set.add(ktime);
        while (numKinds != 0) {
            int cur = 0;
            do {
                cur = randomNumber(range);
            } while (set.contains(cur));
            set.add(cur);
            numKinds--;

            for (int i = 0; i < m; i++) {
                arr[index++] = cur;
            }
        }

        for (int i = 0; i < arr.length; i++) {
            // 随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);// 0 ~ N-1
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        return arr;
    }

    /**
     * [-range,range]区间的随机数
     */
    public static int randomNumber(int range) {
        return (int) ((Math.random() * range) + 1) - (int) ((Math.random() * range) + 1);
    }

    /**
     * 复制数组，脱离语言限制
     */
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    /**
     * 打印数组，脱离语言限制
     */
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    /**
     * 数组的是否相同
     */
    public static boolean isEquals(int[] a, int[] b) {
        if (a == null ^ b == null) {
            return false;
        }
        if (a == null && b == null) {
            return true;
        }
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}