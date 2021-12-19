/**
 * 递归
 * 求arr中的最大值
 */
package com.example.system.class02;

public class C08_Recursion {
    public static int getMax(int[] arr) {
        if (arr == null || arr.length == 0)
            return -1;

        return process(arr, 0, arr.length - 1);
    }

    /**
     * arr[L..R]范围上求最大值 L ... R N
     */
    private static int process(int[] arr, int l, int r) {
        if (l == r) {
            return arr[l];
        }

        int mid = l + (r - l) / 2;
        // mid 向下取整, 所以左边界为mid, 而不是mid-1.
        return Math.max(process(arr, l, mid), process(arr, mid + 1, r));
    }

    public static void main(String[] args) {
        int[] arr = { 1, 8, 3, 24, 5, 6, 7 };
        System.out.println(getMax(arr));
    }
}
