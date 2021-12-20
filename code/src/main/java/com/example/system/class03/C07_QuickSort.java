/**
 * 随机快排
 * 荷兰国旗技巧优化：随机阙值
 */
package com.example.system.class03;

import java.util.Arrays;
import java.util.Stack;

import com.example.utility.helper.ArrayTestHelper;

public class C07_QuickSort {
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        // 随机阙值
        ArrayTestHelper.swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
        int[] equalArray = partition(arr, l, r);
        process(arr, l, equalArray[0] - 1);
        process(arr, equalArray[1] + 1, r);
    }

    private static int[] partition(int[] arr, int l, int r) {
        if (l > r)
            return new int[] { -1, -1 };
        if (l == r)
            return new int[] { l, r };

        int lessR = l - 1;
        int moreR = r;
        int index = l;
        while (index < moreR) {
            if (arr[index] == arr[r]) {
                index++;
            } else if (arr[index] < arr[r]) {
                ArrayTestHelper.swap(arr, index++, ++lessR);
            } else {
                ArrayTestHelper.swap(arr, index, --moreR);
            }
        }
        ArrayTestHelper.swap(arr, moreR, r);
        return new int[] { lessR + 1, moreR };
    }

    private static class Op {
        int l;
        int r;

        public Op(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    private static void quickSortLoop(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        Stack<Op> list = new Stack<>();
        list.push(new Op(0, arr.length - 1));
        while (!list.empty()) {
            Op o = list.pop();
            if (o.l < o.r) {
                ArrayTestHelper.swap(arr, o.l + (int) (Math.random() * (o.r - o.l + 1)), o.r);
                int[] equalArray = partition(arr, o.l, o.r);
                list.push(new Op(o.l, equalArray[0] - 1));
                list.push(new Op(equalArray[1] + 1, o.r));
            }
        }
    }

    public static void main(String[] args) {
        // int[] arr = new int[] { 11, -40, 74, 42, 87 };
        // quickSortLoop(arr);
        // System.out.print(Arrays.toString(arr));

        ArrayTestHelper.testSortMethod(C07_QuickSort::quickSort, C07_QuickSort::quickSortLoop);
    }
}
