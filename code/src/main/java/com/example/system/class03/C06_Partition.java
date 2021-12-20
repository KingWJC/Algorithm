/**
 * 快速排序1.0和2.0
 * 1. Partition过程
 * 2. 荷兰国旗问题
 */
package com.example.system.class03;

import com.example.utility.helper.ArrayTestHelper;

public class C06_Partition {
    public static void quickSortPartition(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int l, int r) {
        // partition会返回-1,所以 l会>r /
        if (l >= r)
            return;

        // 若[0,1]: l=0,r=1,m=1, 则会有m+1>r
        int m = partition(arr, l, r);
        // m不参加
        process(arr, l, m - 1);
        process(arr, m + 1, r);
    }

    /**
     * arr[L..R]上，以arr[R]位置的数做划分值,
     * 小于等于arr[R]的放在左组.
     * 求得将数组分为左右两部份的中间值M.
     */
    public static int partition(int[] arr, int l, int r) {
        if (l > r) {
            return -1;
        }
        if (l == r) {
            return l;
        }

        int lessEqualR = l - 1;
        int index=l;
        while (index < r) {
            if (arr[index] <= arr[r]) {
                ArrayTestHelper.swap(arr, index, ++lessEqualR);
            }
            index++;
        }
        ArrayTestHelper.swap(arr, r, ++lessEqualR);
        return lessEqualR;
    }

    public static void quickSortFlag(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        process(arr, 0, arr.length - 1);
    }

    public static void processFlag(int[] arr, int l, int r) {
        if (l >= r)
            return;

        int[] m = netherlandsFlag(arr, l, r);
        // equalArea的前后的位置.
        processFlag(arr, l, m[0] - 1);
        processFlag(arr, m[1] + 1, r);
    }

    public static int[] netherlandsFlag(int[] arr, int l, int r) {
        if (l > r)
            return new int[] { -1, -1 };
        if (l == r)
            return new int[] { l, r };

        int lessEqualR = l - 1;
        int moreEqualL = r;
        while (l < moreEqualL) {
            if (arr[l] < arr[r]) {
                ArrayTestHelper.swap(arr, l++, ++lessEqualR);
            } else if (arr[l] > arr[r]) {
                ArrayTestHelper.swap(arr, l, --moreEqualL);
            } else {
                l++;
            }
        }
        ArrayTestHelper.swap(arr, r, moreEqualL);
        return new int[] { ++lessEqualR, moreEqualL };
    }

    public static void main(String[] args) {
        ArrayTestHelper.testSortMethod(C06_Partition::quickSortPartition,
        C06_Partition::quickSortFlag);
    }
}
