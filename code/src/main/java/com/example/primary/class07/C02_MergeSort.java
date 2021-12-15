/**
 * 归元排序.
 */
package com.example.primary.class07;

import java.util.Arrays;

public class C02_MergeSort {
    /**
     * 递归方法实现
     */
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    /**
     * arr[L...R]范围上，请让这个范围上的数，有序！
     */
    public static void process(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + (r - l) / 2;
        process(arr, l, mid);
        process(arr, mid + 1, r);
        mergeData(arr, l, mid, r);
    }

    /**
     * 非递归方法实现
     */
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 步长
        int step = 1;
        int n = arr.length;
        while (step < n) {
            int l = 0;
            while (l < n) {
                // 最后一个左组是否凑够step, 否则l + step - 1可能越界
                if (step >= n - l) {
                    break;
                }

                int mid = l + step - 1;

                // 右组是否能凑够step,
                // if (N - 1 - M >= step) {
                // R = M + step;
                // } else {
                // R = N - 1;
                // }
                int r = mid + Math.min(step, n - mid - 1);
                mergeData(arr, l, mid, r);

                // 防止r+1越界
                if (r == n - 1)
                    break;
                l = r + 1;
            }

            // 防止step*2溢出, 如n=2^31-2, step=2^30.
            // 不能是等于, 因为若n=17,step=8, n/2向下取整, 不会处理第17位的数.
            if (step > (n / 2)) {
                break;
            }
            step <<= 1;
        }
    }

    /**
     * 合并两个有序数组.
     */
    private static void mergeData(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];

        int i = 0;
        int p1 = l;
        int p2 = mid + 1;
        // p1,p2只有一个会++, 所以不可能出现：共同越界
        while (p1 <= mid && p2 <= r) {
            help[i++] = arr[p1] > arr[p2] ? arr[p2++] : arr[p1++];
        }
        // 要么p1越界
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        // 要么p2越界
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }

        for (i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 100;
        int testTimes = 50000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int[] copy = copyArray(arr);
            mergeSort(arr);
            mergeSort2(copy);
            if (!isEquals(arr, copy)) {
                System.out.println("error");
                System.out.println(Arrays.toString(arr));
                System.out.println(Arrays.toString(copy));
                break;
            }
        }
        System.out.println("test end");
    }

    public static int[] generateRandomArray(int maxLenth, int maxValue) {
        int[] arr = new int[(int) (Math.random() * (maxLenth + 1))];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue));
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null)
            return null;

        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }

        // System.arraycopy(arr, 0, res, 0, arr.length);

        // int[] res = Arrays.copyOf(arr, arr.length);

        return res;
    }

    public static boolean isEquals(int[] arr1, int[] arr2) {
        if (arr1 == null ^ arr2 == null) {
            return false;
        }

        if (arr1 == null && arr2 == null) {
            return true;
        }

        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i])
                return false;
        }

        return true;
    }
}
