/**
 * arr中的值可能为正，可能为负，可能为0，自由选择arr中的数字，能不能累加得到sum
 * 从暴力递归到动态规划。
 */
package com.example.system.class25;

import java.util.HashMap;
import java.util.HashSet;

public class C03_IsSum {
    /**
     * 暴力递归
     */
    public static boolean isSum1(int[] arr, int sum) {
        // 不选任何数，累加为0.
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        return process1(arr, arr.length - 1, sum);
    }

    /**
     * 自由使用arr[0...i]上的数字，能不能累加得到sum
     */
    public static boolean process1(int[] arr, int index, int sum) {
        if (sum == 0) {
            return true;
        }

        if (index == -1) {
            return false;
        }

        return process1(arr, index - 1, sum) || process1(arr, index - 1, sum - arr[index]);
    }

    /**
     * 记忆化搜索
     */
    public static boolean isSum2(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        return process2(arr, arr.length - 1, sum, new HashMap<>());
    }

    /**
     * 缓存表是二维表，因为是boolean类型，所以用嵌套HashMap代替二维数组
     */
    private static boolean process2(int[] arr, int index, int sum, HashMap<Integer, HashMap<Integer, Boolean>> cache) {
        if (cache.containsKey(index) && cache.get(index).containsKey(sum)) {
            return cache.get(index).get(sum);
        }

        boolean ans = false;
        if (sum == 0) {
            ans = true;
        } else if (index != -1) {
            ans = process2(arr, index - 1, sum, cache) || process2(arr, index - 1, sum - arr[index], cache);
        }

        if (!cache.containsKey(index)) {
            cache.put(index, new HashMap<>());
        }
        cache.get(index).put(sum, ans);
        return ans;
    }

    /**
     * 经典动态规划
     */
    public static boolean isSum3(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }

        // arr中有正数，负数和0，需要计算累加和范围
        int min = 0;
        int max = 0;
        for (int num : arr) {
            min += num < 0 ? num : 0;
            max += num > 0 ? num : 0;
        }
        // sum不在累计和的范围内。
        if (sum < min || sum > max) {
            return false;
        }

        int N = arr.length;
        // 动态规划表的列包含负数情况，最小值为第一列
        boolean[][] dp = new boolean[N][max - min + 1];
        dp[0][0 - min] = true;
        dp[0][arr[0] - min] = true;

        for (int i = 1; i < N; i++) {
            for (int j = min; j <= max; j++) {
                dp[i][j - min] = dp[i - 1][j - min];
                int next = j - min - arr[i];
                // 因为arr里有负值，增加边界判断
                if (next >= 0 && next <= max - min) {
                    dp[i][j - min] |= dp[i - 1][next];
                }
            }
        }
        return dp[N - 1][sum - min];
    }

    /**
     * 分治的方法
     * 如果arr中的数值特别大，动态规划方法依然会很慢
     * 此时如果arr的数字个数不算多(40以内)，哪怕其中的数值很大，分治的方法也将是最优解
     */
    public static boolean isSum4(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
    }

    private static boolean process4(int arr, int index, int end, int pre, HashSet<Integer> set) {

    }

    public static void main(String[] args) {
        int N = 20;
        int M = 100;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int size = (int) (Math.random() * (N + 1));
            int[] arr = generateArray(size, M);
            int sum = (int) (Math.random() * ((M << 1) + 1)) - M;
            boolean ans1 = isSum1(arr, sum);
            boolean ans2 = isSum2(arr, sum);
            boolean ans3 = isSum3(arr, sum);
            if (ans1 ^ ans2 || ans2 ^ ans3) {
                System.out.println("OOPs");
                break;
            }
        }
        System.out.println("test finish");
    }

    /**
     * 值在[-max, max]上随机
     */
    private static int[] generateArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * ((max << 1) + 1)) - max;
        }
        return arr;
    }
}
