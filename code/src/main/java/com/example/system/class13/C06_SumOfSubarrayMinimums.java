/**
 * 所有子数组最小值的累加和
 */
package com.example.system.class13;

import java.util.Arrays;

public class C06_SumOfSubarrayMinimums {
    /**
     * 暴力解：O(N^3)
     */
    public static int subArrayMinSum1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int min = arr[i];
                for (int x = i + 1; x <= j; x++) {
                    min = Math.min(min, arr[x]);
                }
                ans += min;
            }
        }
        return ans;
    }

    /**
     * 优化：计算每个元素左边和右边最近小于的值。
     * 当前元素到它左右两侧最近的值的范围上的子数组个数：(X-L)*(R-X)*min
     * 重复值处理：左边相等停
     */
    public static int subArrayMinSum2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int ans = 0;

        // 针对重复值：右边不阉割，左边阉割
        // left[i]=x: arr[i]左边，离arr[i]最近，<=arr[i],位置在x
        int[] left = leftNearLessEqual(arr);
        // right[i]=x: arr[i]右边，离arr[i]最近，<arr[i],位置在x
        int[] right = rightNearLess(arr);

        for (int i = 0; i < arr.length; i++) {
            int start = i - left[i];
            int end = right[i] - i;
            ans += start * end * arr[i];
        }
        return ans;
    }

    private static int[] leftNearLessEqual(int[] arr) {
        int N = arr.length;
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            int leftless = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] <= arr[i]) {
                    leftless = j;
                    break;
                }
            }
            ans[i] = leftless;
        }
        return ans;
    }

    private static int[] rightNearLess(int[] arr) {
        int N = arr.length;
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            int rightLess = N;
            for (int j = i + 1; j < N; j++) {
                if (arr[j] < arr[i]) {
                    rightLess = j;
                    break;
                }
            }
            ans[i] = rightLess;
        }
        return ans;
    }

    /**
     * 使用单调栈，重复值处理：右边相等停
     */
    public static int subArrayMinSum3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        long ans = 0;
        int[] stack = new int[arr.length];
        // 针对重复值：左边不阉割，右边阉割
        int[] left = leftNearLess(arr, stack);
        int[] right = rightNearLessEqual(arr, stack);
        for (int i = 0; i < arr.length; i++) {
            int start = i - left[i];
            int end = right[i] - i;
            ans += start * end * (long) arr[i];
            ans %= 1000000007;
        }
        return (int) ans;
    }

    private static int[] leftNearLess(int[] arr, int[] stack) {
        int[] ans = new int[arr.length];
        int si = -1;
        int N = arr.length;
        for (int i = N - 1; i >= 0; i--) {
            while (si != -1 && arr[stack[si]] > arr[i]) {
                int index = stack[si--];
                ans[index] = i;
            }
            stack[++si] = i;
        }
        // 从后往前加入，剩下的都是左边没有比自己小的数
        while (si != -1) {
            int index = stack[si--];
            ans[index] = -1;
        }
        return ans;
    }

    private static int[] rightNearLessEqual(int[] arr, int[] stack) {
        int si = -1;
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            while (si != -1 && arr[stack[si]] >= arr[i]) {
                int index = stack[si--];
                ans[index] = i;
            }
            stack[++si] = i;
        }
        while (si != -1) {
            int index = stack[si--];
            ans[index] = arr.length;
        }
        return ans;
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 50;
        int testtimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testtimes; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = subArrayMinSum1(arr);
            int ans2 = subArrayMinSum2(arr);
            int ans3 = subArrayMinSum3(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("error");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(Arrays.toString(arr));
                break;
            }
        }
        System.out.println("finished");
    }

    private static int[] randomArray(int len, int maxValue) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue) + 1;
        }
        return ans;
    }
}
