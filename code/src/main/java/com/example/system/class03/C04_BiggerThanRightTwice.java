/**
 * 一个数组中，对于每个数num，求有多少个后面的数 * 2 依然<num，求总个数
 * (在merge过程中,关于左组上的数, 有多少个在右组上达标的)
 */
package com.example.system.class03;

import java.util.Arrays;

public class C04_BiggerThanRightTwice {
    public static int biggerTwice(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int l, int r) {
        if (l == r)
            return 0;

        int mid = l + (r - l) / 2;
        return process(arr, l, mid) + process(arr, mid + 1, r) + mergeData(arr, l, mid, r);
    }

    public static int mergeData(int[] arr, int l, int m, int r) {
        int result = 0;

        // 不回退的技巧. 所以复杂度是O(N)
        // 目前囊括进来的数，是从[M+1, windowR)
        int windowR = m + 1;
        for (int i = l; i <= m; i++) {
            // windowR右滑动, 保持左闭右开
            while (windowR <= r && arr[i] > arr[windowR] * 2) {
                windowR++;
            }
            result += windowR - m - 1;
        }

        int[] help = new int[r - l + 1];
        int index = help.length - 1;
        int r1 = m;
        int r2 = r;
        while (r1 >= l && r2 > m) {
            if (arr[r1] > arr[r2]) {
                // 不可以这样写,如果左组数大,会把左边界左移,少了和右组数的更小的数进行判断.
                // 如:[(1,3,7),(0,2)] 3>2, 左边界左移到1, 但3>0*2.
                // 也因为条件是大于后面的数 * 2,所以无法放入当前循环的条件下.
                // if (arr[r1] > arr[r2] * 2) {
                // System.out.println(arr[r1] + " " + arr[r2]+" 有"+(r2 - m));
                // result += r2 - m;
                // }

                // 从右往左合并, 谁大谁先放入help中.
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
            arr[i + l] = help[i];
        }
        return result;
    }

    public static int biggerTwiceByLoop(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] < arr[j] * 2)
                    result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 3, 1, 7, 0, 2 };
        System.out.println(biggerTwiceByLoop(arr));
        System.out.println(biggerTwice(arr) + " " + Arrays.toString(arr));
    }
}
