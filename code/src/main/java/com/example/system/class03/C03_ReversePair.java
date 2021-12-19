/**
 * 数组中，任何一个前面的数a，和任何一个后面的数b，如果(a,b)是降序的，就称为逆序对，
 * 返回数组中所有逆序对的个数
 * (在merge过程中,关于左组上的数, 有多少个在右组上达标的)
 */
package com.example.system.class03;

import com.example.utility.helper.ArrayTestHelper;

public class C03_ReversePair {
    public static int reverPairNumber(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int l, int r) {
        if (l == r)
            return 0;

        int mid = l + ((r - l) >> 1);
        int sumL = process(arr, l, mid);
        int sumR = process(arr, mid + 1, r);
        return sumL + sumR + mergeData(arr, l, mid, r);
    }

    /**
     * 从左往右合并.
     */
    public static int mergeData(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int result = 0;
        int l1 = l;
        int l2 = m + 1;
        int index = 0;
        while (l1 <= m && l2 <= r) {
            if (arr[l1] > arr[l2]) {
                result += m - l1 + 1;
                help[index++] = arr[l2++];
            } else {
                help[index++] = arr[l1++];
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
     * 从右往左合并.
     */
    public static int mergeData1(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int result = 0;
        int r1 = m;
        int r2 = r;
        int index = help.length - 1;
        while (r1 >= l && r2 >= m + 1) {
            if (arr[r1] > arr[r2]) {
                result += r2 - (m + 1) + 1;
                help[index--] = arr[r1--];
            } else {
                help[index--] = arr[r2--];
            }
        }
        while (r1 >= l) {
            help[index--] = arr[r1--];
        }
        while (r2 > m) {
            help[index--] = arr[r2--];
        }
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return result;
    }

    public static int reverPairNumberByLoop(int[] arr) {
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j])
                    result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {

        // int[] arr = new int[] { 5, 4, 3, 2, 1 };
        // System.out.println(reverPairNumber(arr) + " " + Arrays.toString(arr));
        // int[] copy = new int[] { 5, 4, 3, 2, 1 };
        // System.out.println(reverPairNumberByLoop(copy) + " " +
        // Arrays.toString(copy));

        ArrayTestHelper.testMergeResult(C03_ReversePair::reverPairNumber,
                C03_ReversePair::reverPairNumberByLoop);
    }
}
