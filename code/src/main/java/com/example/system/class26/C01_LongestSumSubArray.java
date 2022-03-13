/**
 * 整数组子数组累加和等于K的最大长度
 */
package com.example.system.class26;

import java.util.HashMap;

public class C01_LongestSumSubArray {
    /**
     * 数组的值是正整数。
     * 窗口滑动，累加和有单调性,O(N)
     */
    public static int getMaxLengthPositive(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }
        int ans = 0;
        int l = 0;
        int r = 0;
        int pre = arr[0];
        while (r < arr.length) {
            if (pre < k) {
                r++;
                if (r != arr.length) {
                    pre += arr[r];
                }
            } else {
                if (pre == k) {
                    ans = Math.max(ans, r - l + 1);
                }

                pre -= arr[l++];
            }
        }
        return ans;

    }

    /**
     * 数组的值是整数：值可能正、可能负、可能0。
     * 预处理结构和子数组以每个位置结尾的情况。
     */
    public static int getMaxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }

        // key:前缀和
        // value : 0~value这个前缀和是最早出现key这个值的索引位置
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);// important
        int preSum = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            preSum += arr[i];
            if (map.containsKey(preSum - k)) {
                ans = Math.max(ans, i - map.get(preSum - k));
            }
            if (!map.containsKey(preSum)) {
                map.put(preSum, i);
            }
        }
        return ans;
    }

    /**
     * 暴力解： O(N^2)
     */
    public static int getMaxLengthTest(int[] arr, int k) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                int sum = 0;
                for (int l = i; l <= j; l++) {
                    sum += arr[l];
                }
                if (sum == k) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        testPositive();
        test();
    }

    /**
     * 测试正整数数组
     */
    private static void testPositive() {
        int size = 50;
        int value = 100;
        int testTimes = 100000;
        System.out.println("start positive test");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generatePostiveArray(size, value);
            int K = (int) (Math.random() * value) + 1;
            int ans1 = getMaxLengthPositive(arr, K);
            int ans2 = getMaxLengthTest(arr, K);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finished");
    }

    /**
     * 测试整数数组
     */
    private static void test() {
        int size = 50;
        int value = 100;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateArray(size, value);
            int K = (int) (Math.random() * value) + 1;
            int ans1 = getMaxLength(arr, K);
            int ans2 = getMaxLengthTest(arr, K);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finished");
    }

    /**
     * 生成随机正整数数组。
     */
    private static int[] generatePostiveArray(int size, int value) {
        int N = (int) (Math.random() * size) + 1;
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    /**
     * 生成随机数组。
     */
    private static int[] generateArray(int size, int value) {
        int N = (int) (Math.random() * size) + 1;
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            ans[i] = (int) (Math.random() * (value << 1)) - value;
        }
        return ans;
    }
}
