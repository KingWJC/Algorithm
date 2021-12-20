/**
 * arr中有多少个子数组的累加和在[lower,upper]范围上
 * O(N^3) -> O(N^2) -> O(N*logN)
 */
package com.example.system.class03;

import java.util.Arrays;

public class C05_CountOfRangeSum {
    public static int countRangeSumByLoop(int[] arr, int lower, int upper) {
        if (arr == null || arr.length < 2)
            return 0;

        int[] sum = new int[arr.length];
        sum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sum[i] = arr[i] + sum[i - 1];
        }

        int result = 0;
        for (int i = 0; i < sum.length; i++) {
            for (int j = i; j < sum.length; j++) {
                int temp = i - 1 < 0 ? sum[j] : sum[j] - sum[i - 1];
                if (temp >= lower && temp <= upper) {
                    System.out.println(j + "-" + i + " =" + temp);
                    result++;
                }
            }
        }
        return result;
    }

    public static int countRangeSumByRecursion(int[] arr, int lower, int upper) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int[] sum = new int[arr.length];
        sum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sum[i] = arr[i] + sum[i - 1];
        }
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    public static int process(int[] arr, int l, int r, int lower, int upper) {
        // 处理0到当前的和是否达标：只有一个数，表示原始数组的0-l范围的前缀和，如sum[4]=10.
        if (l == r)
            return arr[l] >= lower && arr[l] <= upper ? 1 : 0;

        // 左右组有数，所以无法处理只有一个数的情况。
        int m = l + (r - l) / 2;
        int a = process(arr, l, m, lower, upper);
        int b = process(arr, m + 1, r, lower, upper);
        return a + b + mergeData(arr, l, m, r, lower, upper);
    }

    public static int mergeData(int[] arr, int l, int m, int r, int lower, int upper) {
        int result = 0;
        for (int i = m + 1; i <= r; i++) {
            for (int j = l; j <= m; j++) {
                if (arr[j] >= arr[i] - upper && arr[j] <= arr[i] - lower) {
                    System.out.println(j + " = " + arr[j]);
                    result++;
                }
            }
        }

        // int windowL = l;
        // int windowR = l;
        // // [windowL, windowR) 下限和上限都不会回退。
        // 因为有序，所以右组如[3,7,8]在[4,6]范围上,左组中前缀和范围是：(-3,-1),(1,3),(2,4)
        // windowL, windowR 递增不退。
        // for (int i = m + 1; i <= r; i++) {
        // while (windowL <= m && arr[windowL] > (arr[i] - upper)) {
        // windowL++;
        // }
        // windowR++;
        // }
        // result += windowR - windowL;
        // }

        int[] help = new int[r - l + 1];
        int index = 0;
        int l1 = l;
        int l2 = m + 1;
        while (l1 <= m && l2 <= r) {
            if (arr[l1] <= arr[l2]) {
                help[index++] = arr[l1++];
            } else {
                help[index++] = arr[l2++];
            }
        }
        while (l1 <= m) {
            help[index++] = arr[l1++];
        }
        while (l2 <= r) {
            help[index++] = arr[l2++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 0, 1, 2, 3, 4 };
        System.out.println("result1=" + countRangeSumByLoop(arr, 3, 6));
        System.out.println("result2=" + countRangeSumByRecursion(arr, 3, 6));
        arr = new int[] { 3, 2, 0, 1, 4 };
        System.out.println("result1=" + countRangeSumByLoop(arr, 3, 6));
        System.out.println("result2=" + countRangeSumByRecursion(arr, 3, 6));
    }
}
