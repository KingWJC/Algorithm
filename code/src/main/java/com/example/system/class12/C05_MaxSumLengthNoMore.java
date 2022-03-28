/**
 * 给定一个数组arr，和一个正数M
 * 返回在子数组长度不大于M的情况下，最大的子数组累加和
 */
package com.example.system.class12;

import java.util.LinkedList;

public class C05_MaxSumLengthNoMore {
    /**
     * 暴力解：O(N^2)
     */
    public static int maxSum1(int[] arr, int M) {
        if (arr == null || arr.length == 0 || M < 1) {
            return 0;
        }

        int N = arr.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            int sum = 0;
            for (int j = i; j < N; j++) {
                if (j - i + 1 > M) {
                    break;
                }
                // 数组中有负数。
                sum += arr[j];
                max = Math.max(max, sum);
            }
        }

        return max;
    }

    /**
     * 最优解：O(N),窗口内最大值的更新结构
     */
    public static int maxSum2(int[] arr, int M) {
        if (arr == null || arr.length == 0 || M < 1) {
            return 0;
        }

        int N = arr.length;
        int[] sum = new int[N];
        sum[0] = arr[0];
        for (int i = 1; i < N; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }

        // 单调双端队列
        LinkedList<Integer> qMax = new LinkedList<>();
        int i = 0;
        // 窗口初始化，右边界右移，更新窗口宽度为M。
        int end = Math.min(N, M);// 窗口右边界
        for (; i < end; i++) {
            while (!qMax.isEmpty() && sum[qMax.peekLast()] <= sum[i]) {
                qMax.pollLast();
            }
            qMax.add(i);
        }
        // 在初始窗口中，获取最大的前缀和
        int max = sum[qMax.peekFirst()];

        // 窗口整体右移
        int L = 0;
        for (; i < N; L++, i++) {
            if (qMax.peekFirst() == L) {
                qMax.pollFirst();
            }
            while (!qMax.isEmpty() && sum[qMax.peekLast()] <= sum[i]) {
                qMax.pollLast();
            }
            qMax.add(i);
            max = Math.max(max, sum[qMax.peekFirst()] - sum[L]);
        }

        // 窗口收缩，左边界右移
        for (; L < N - 1; L++) {
            if (qMax.peekFirst() == L) {
                qMax.pollFirst();
            }
            max = Math.max(max, sum[qMax.peekFirst()] - sum[L]);
        }
        return max;
    }

    public static void main(String[] args) {
        int maxN = 50;
        int maxValue = 100;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxN, maxValue);
            int M = (int) (Math.random() * maxValue) + 1;
            int ans1 = maxSum1(arr, M);
            int ans2 = maxSum2(arr, M);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

    private static int[] randomArray(int size, int max) {
        int len = (int) (Math.random() * size) + 1;
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }
}
