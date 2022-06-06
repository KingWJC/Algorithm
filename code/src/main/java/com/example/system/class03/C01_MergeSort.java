/**
 * 归并排序
 */
package com.example.system.class03;

import com.example.utility.helper.ArrayTestHelper;

public class C01_MergeSort {
    public static void mergeSortByRecursion(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        process(arr, 0, arr.length - 1);
    }

    // 请把arr[L..R]排有序
    // l...r N
    // T(N) = 2 * T(N / 2) + O(N)
    // O(N * logN)
    public static void process(int[] arr, int l, int r) {
        if (l == r)
            return;

        int mid = l + ((r - l) >> 1);
        process(arr, l, mid);
        process(arr, mid + 1, r);
        mergeData(arr, l, mid, r);
    }

    public static void mergeData(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int l1 = l;
        int l2 = mid + 1;
        int index = 0;
        while (l1 <= mid && l2 <= r) {
            // arr[l1] 大于,小于 arr[l2] 都可以.
            help[index++] = arr[l1] <= arr[l2] ? arr[l1++] : arr[l2++];
        }
        while (l1 <= mid) {
            help[index++] = arr[l1++];
        }
        while (l2 <= r) {
            help[index++] = arr[l2++];
        }

        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
    }

    /**
     * 非递归方法实现
     */
    public static void mergeSortByLoop(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int step = 1;
        int n = arr.length;
        while (step < n) {
            // 当前左组的，第一个位置
            int l = 0;
            while (l < n) {
                if (l + step >= n)
                    break;

                int m = l + step - 1;
                int r = m + Math.min(step, n - (m + 1));
                mergeData(arr, l, m, r);
                l = r + 1;
            }

            // 防止溢出, n/2向下取整, 如n=9, n/2=4, 如果是等于,则第九个数无法处理
            if (step > n / 2)
                break;

            step <<= 1;
        }
    }

    public static void main(String[] args) {
        ArrayTestHelper.testSortMethod(C01_MergeSort::mergeSortByRecursion, C01_MergeSort::mergeSortByLoop);
    }
}
