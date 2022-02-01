/**
 * 正数数组分割为累加和最接近的两个集合
 */
package com.example.system.class11;

import java.util.Arrays;

public class C22_SplitSumClosed {
    /**
     * 递归尝试
     */
    public static int getMinSum(int[] arr) {
        // 分成两个集合，arr必须大于1。
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int totle = Arrays.stream(arr).sum();
        // 两集合的累加和最接近，则最小集合的累加和去接近总数的一半。
        return process(arr, 0, totle / 2);
    }

    /**
     * 每次在数组中选择后，对目标值的影响，再执行下一次选择，直到边界条件。
     * arr[index.....] 从index开始，自由选择，
     * 组成的集合的累加和最接近中值，且不超过的情况下，返回较小集合的累计值
     */
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            // 上游进行处理，会加上当前选择的数值，所以直接返回0.
            return 0;
        }

        // 不选择当前的数值
        int p1 = process(arr, index + 1, rest);
        int p2 = 0;
        if (arr[index] <= rest) {
            // 选择了当前的数值，下次的尝试结果，需要加上当前选择的数值。
            p2 = process(arr, index + 1, rest - arr[index]) + arr[index];
        }

        return Math.max(p1, p2);
    }

    /**
     * 动态规划：因为尝试过程是一个二叉树，有重复值，
     */
    public static int useDP(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int N = arr.length;
        int midSum = 0;
        for (int i : arr) {
            midSum += i;
        }
        midSum = midSum / 2;

        int[][] dp = new int[N + 1][midSum + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= midSum; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = 0;
                if (arr[index] <= rest) {
                    p2 = arr[index] + dp[index + 1][rest - arr[index]];
                }
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][midSum];
    }

    public static void main(String[] args) {
        int test = -1;
        // 有符号右移，为-1的时候出错。
        System.out.println(test >> 1);
        System.out.println(test / 2);

        System.out.println(20 >>> 1);
        System.out.println((-20) >> 1);
        // 无符号右移，负数情况下出错
        System.out.println((-20) >>> 1);

        int length = 10;
        int maxValue = 20;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = getRandomArray(length, maxValue);
            int ans1 = getMinSum(arr);
            int ans2 = useDP(arr);
            if (ans1 != ans2) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finished");
    }

    private static int[] getRandomArray(int length, int maxValue) {
        int[] ans = new int[length];
        for (int i = 0; i < length; i++) {
            ans[i] = (int) (Math.random() * maxValue);
        }
        return ans;
    }
}
