/**
 * 数组中，一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组小和。
 * 求数组小和
 * (在merge过程中,关于左组上的数, 有多少个在右组上达标的)
 */
package com.example.system.class03;

import com.example.utility.helper.ArrayTestHelper;

public class C02_SmallSum {
    public static int smallSumByMerge(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }

    // arr[L..R]既要排好序，也要求小和返回
    // 所有merge时，产生的小和，累加
    // 左 排序 merge
    // 右 排序 merge
    // merge
    public static int process(int[] arr, int l, int r) {
        if (l == r)
            return 0;

        int mid = l + (r - l) / 2;
        int a = process(arr, l, mid);
        int b = process(arr, mid + 1, r);
        return a + b + mergeDate(arr, l, mid, r);
    }

    public static int mergeDate(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int l1 = l;
        int l2 = m + 1;
        int index = 0;
        int result = 0;
        while (l1 <= m && l2 <= r) {
            // 等于时,右组先拷贝,方便计算右组有多少个数比arr[l1]大.
            if (arr[l1] < arr[l2]) {
                // 右组有多少个数比arr[l1]大, 可通过下标计算, 因为右组已经有序.
                result += arr[l1] * (r - l2 + 1);
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

    /**
     * 迭代,每个数与其左组数进行对比.
     */
    public static int smallSumByLoop(int[] arr) {
        if (arr == null && arr.length < 2) {
            return 0;
        }

        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                res += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        ArrayTestHelper.testMergeResult(C02_SmallSum::smallSumByMerge, C02_SmallSum::smallSumByLoop);
    }
}
