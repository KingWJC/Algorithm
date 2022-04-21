/**
 * 二分搜索，查找结果
 * 在无序的数组中。
 */
package com.example.system.class01;

public class C03_BSUnorder {
    /**
     * 局部最小值， 无序且相邻不等的数组, 找一个局部最小值(比前后都小).
     */
    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length < 2)
            return -1;
        if (arr.length == 1 || arr[0] < arr[1])
            return 0;
        if (arr[arr.length - 1] < arr[arr.length - 2])
            return arr.length - 1;

        int l = 1;
        int r = arr.length - 2;
        int m = 0;
        // L..R 至少两个数的时候，
        while (l < r) {
            m = l + ((r - l) >> 1);
            if (arr[m] > arr[m - 1]) {
                r = m - 1;
            } else if (arr[m] > arr[m + 1]) {
                l = m + 1;
            } else {
                // arr[m-1] > m < arr[m+1]
                return m;
            }
        }

        // 返回L..R只有一个数的情况
        return l;
    }
}