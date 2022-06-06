/**
 * 整形数组中子数组达标的个数
 */
package com.example.system.class12;

import java.util.Arrays;
import java.util.LinkedList;

public class C02_AllLessNumSubArray {
    /**
     * 暴力，遍历
     * 
     * @param arr 整型数组
     * @param num sub中最大值 – sub中最小值 <= num
     * @return 返回arr中达标子数组的数量
     */
    public static int getCount1(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 0) {
            return 0;
        }

        int N = arr.length;
        int count = 0;
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                // [L...R]上的最大，小值。
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= num) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 窗口最大，最小值的更新结构
     */
    public static int getCount2(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 0) {
            return 0;
        }

        LinkedList<Integer> maxQ = new LinkedList<>();
        LinkedList<Integer> minQ = new LinkedList<>();
        int N = arr.length;
        int count = 0;
        int R = 0; // 所有L共用一个R，且不回退
        for (int L = 0; L < N; L++) {
            // 窗口的范围[L，R) 左开右闭，所以[0，0)表示窗口内无数据
            // [L..R)扩到初次不达标，停止。
            while (R < N) {
                // 最大，最小值的更新结构
                while (!maxQ.isEmpty() && arr[maxQ.peekLast()] <= arr[R]) {
                    maxQ.pollLast();
                }
                maxQ.addLast(R);
                while (!minQ.isEmpty() && arr[minQ.peekLast()] >= arr[R]) {
                    minQ.pollLast();
                }
                minQ.addLast(R);

                if (arr[maxQ.peekFirst()] - arr[minQ.peekFirst()] <= num) {
                    R++;
                } else {// 不达标
                    break;
                }
            }
            // 上次达标的子数组个数
            count += R - L;

            // L++窗口缩小，最大，最小值可能有过期情况，过期就滚蛋。
            if (maxQ.peekFirst() == L) {
                maxQ.pollFirst();
            }

            if (minQ.peekFirst() == L) {
                minQ.pollFirst();
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 10;
        int maxValue = 10;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArr(maxSize, maxValue);
            int num = (int) (Math.random() * (maxValue + 1));
            int ans1 = getCount1(arr, num);
            int ans2 = getCount2(arr, num);
            if (ans1 != ans2) {
                System.out.println("num=" + num + " ans1=" + ans1 + " ans2=" + ans2);
                System.out.println("Oops!" + Arrays.toString(arr));
                break;
            }
        }
        System.out.println("test finish");
    }

    private static int[] generateRandomArr(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

}
