/**
 * arr中有多少个子数组的累加和在[lower,upper]范围上
 * O(N^3) -> O(N^2) -> O(N*logN)
 */
package com.example.system.class03;

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
            for (int j = i + 1; j < sum.length; j++) {
                int temp = sum[j] - sum[i];
                if (temp > lower && temp < upper) {
                    System
                    result++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 0, 1, 2, 3, 4 };
        System.out.println(countRangeSumByLoop(arr, 3, 6));
    }
}
