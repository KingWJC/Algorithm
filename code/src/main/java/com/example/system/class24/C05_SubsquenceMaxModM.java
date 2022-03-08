/**
 * 非负数组子序列中累加和%m的最大值
 * 数据规模分析，分治。
 */
package com.example.system.class24;

import java.util.HashSet;
import java.util.TreeSet;

public class C05_SubsquenceMaxModM {
    /**
     * 暴力尝试：arr数组的值不大，m较大。
     */
    public static int max1(int[] arr, int m) {
        HashSet<Integer> set = new HashSet<>();
        process1(arr, 0, 0, set);

        int maxValue = 0;
        for (Integer sum : set) {
            maxValue = Math.max(maxValue, sum % m);
        }
        return maxValue;
    }

    /**
     * arr中，到index位置时，sum的值是多少。
     */
    private static void process1(int[] arr, int sum, int index, HashSet<Integer> set) {
        if (index == arr.length) {
            set.add(sum);
        } else {
            process1(arr, sum + arr[index], index + 1, set);
            process1(arr, sum, index + 1, set);
        }
    }

    /**
     * 动态规划,背包解法: arr数组的值不大，m较大。
     */
    public static int max2(int[] arr, int m) {
        int N = arr.length;
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }

        // 0-index的数，能否组成累加和sum
        boolean[][] dp = new boolean[N][sum + 1];
        // 第一列：只要不选数字，累加和就为0。所以结果为true.
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        // 第一行：选择arr的0位置的数，则累加和为arr[0]位置为true.
        dp[0][arr[0]] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                // 不使用i位置的数字算累加和，之前i-1位置的累加和能不能等于j。
                dp[i][j] = dp[i - 1][j];
                // 边界检查
                if (j >= arr[i]) {
                    // 或的关系
                    // 使用i位置的数字算累加和，之前i-1位置的累加和能不能等于j-arr[i]。
                    dp[i][j] |= dp[i - 1][j - arr[i]];
                }

            }
        }

        int max = 0;
        for (int i = 0; i <= sum; i++) {
            if (dp[N - 1][i]) {
                max = Math.max(max, i % m);
            }
        }
        return max;
    }

    /**
     * 动态规划: arr数组的值很大大，m不大。
     */
    public static int max3(int[] arr, int m) {
        int N = arr.length;
        // 余数范围9-m-1
        boolean[][] dp = new boolean[N][m];
        // 第一列：只要不选数字，累加和就为0，%m的余数为0。所以结果为true.
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        // 第一行：选择arr的0位置的数，则累加和arr[0]%m位置的余数为true.
        dp[0][arr[0] % m] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = dp[i - 1][j];
                int val = arr[i] % m;
                if (val <= j) {
                    dp[i][j] |= dp[i - 1][j - val];
                } else {
                    dp[i][j] |= dp[i - 1][j + m - val];
                }
            }
        }

        // m取最大
        int ans = 0;
        for (int i = 0; i < m; i++) {
            if (dp[N - 1][i]) {
                ans = i;
            }
        }
        return ans;
    }

    /**
     * arr的累加和很大，m也很大：
     * 但是arr的长度相对不大，又不能在10的8次方内完成。
     */
    public static int max4(int[] arr, int m) {
        if (arr.length == 1) {
            return arr[0] % m;
        }

        int N = arr.length;
        int mid = (N - 1) / 2;
        TreeSet<Integer> leftPart = new TreeSet<>();
        process2(arr, m, 0, mid, 0, leftPart);
        TreeSet<Integer> rightPart = new TreeSet<>();
        process2(arr, m, mid + 1, N - 1, 0, rightPart);

        int ans = 0;
        // 遍历左部分，在右部分寻找配对，左右余数和离m最近。
        for (Integer leftMod : leftPart) {
            // m-1：左右余数和不能为m。
            ans = Math.max(ans, leftMod + rightPart.floor(m - 1 - leftMod));
        }
        return ans;
    }

    private static void process2(int[] arr, int m, int index, int end, int sum, TreeSet<Integer> set) {
        if (index == end + 1) {
            set.add(sum % m);
        } else {
            process2(arr, m, index + 1, end, sum + arr[index], set);
            process2(arr, m, index + 1, end, sum, set);
        }
    }

    public static void main(String[] args) {
        int[] arr = { 1, 3, 2 };
        System.out.println(max1(arr, 4));
        System.out.println(max2(arr, 4));
        System.out.println(max3(arr, 4));

        int len = 10;
        int value = 100;
        int m = 76;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            arr = generateRandomArray(len, value);
            int ans1 = max1(arr, m);
            int ans2 = max2(arr, m);
            int ans3 = max3(arr, m);
            int ans4 = max4(arr, m);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test finish!");
    }

    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value);
        }
        return ans;
    }
}
