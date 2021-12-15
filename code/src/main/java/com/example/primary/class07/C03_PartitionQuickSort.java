/**
 * 分区,快速排序
 */
package com.example.primary.class07;

import java.util.Arrays;
import java.util.Stack;

public class C03_PartitionQuickSort {
    /**
     * 数组最后一个值x, <=x的整体放在左边, >x的整体放在右边,
     * 不要用辅助数组, 时间复杂度O(N) 完成这个调整
     */
    public static void splitNum(int[] arr) {
        int index = arr.length - 1;
        int p = -1;
        int cur = 0;
        while (cur <= index) {
            if (arr[cur] <= arr[index]) {
                swap(arr, ++p, cur);
            }
            cur++;
        }
    }

    /**
     * 拿数组最右侧的数P做划分, 做到:Px的整体放在左边, =P的整体放在中间, >P的整体放在右边
     */
    public static void splitNumThree(int[] arr) {
        int index = arr.length - 1;
        int lessEqual = -1;
        int moreEqual = arr.length - 1;
        int cur = 0;
        while (cur < moreEqual) {
            if (arr[cur] < arr[index]) {
                // 小于区向右扩, 当前数跳到下一个
                swap(arr, ++lessEqual, cur++);
            } else if (arr[cur] > arr[index]) {
                // 大于区向左扩, 当前数不动
                swap(arr, --moreEqual, cur);
            } else {
                cur++;
            }
        }
        swap(arr, cur, index);
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * 快速排序-递归
     */
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int l, int r) {
        if (l >= r)
            return;

        int[] equalArr = partition(arr, l, r);
        process(arr, l, equalArr[0] - 1);
        process(arr, equalArr[1] + 1, r);
    }

    /**
     * arr[L...R]范围上，拿arr[R]做划分值，
     * L....R < = >
     * 返回等于区域的范围
     */
    private static int[] partition(int[] arr, int l, int r) {
        // 小于区域的右边界
        int lessR = l - 1;
        // 大于区域的左边界
        int moreL = r;
        int index = l;
        while (index < moreL) {
            if (arr[index] < arr[r]) {
                swap(arr, ++lessR, index++);
            } else if (arr[index] > arr[r]) {
                swap(arr, --moreL, index);
            } else {
                index++;
            }
        }
        swap(arr, index, r);
        return new int[] { lessR + 1, moreL };
    }

    /**
     * 快速排序-迭代
     */
    public static void quickSortLoop(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        Stack<Job> taskList = new Stack<>();
        taskList.push(new Job(0, arr.length - 1));
        while (!taskList.isEmpty()) {
            Job cur = taskList.pop();
            int[] equals = partition(arr, cur.L, cur.R);
            if (equals[0] > cur.L) { // 有< 区域
                taskList.push(new Job(cur.L, equals[0] - 1));
            }
            if (equals[1] < cur.R) { // 有 > 区域
                taskList.push(new Job(equals[1] + 1, cur.R));
            }
        }
    }

    public static class Job {
        public int L;
        public int R;

        public Job(int l, int r) {
            L = l;
            R = r;
        }
    }

    public static void main(String[] args) {
        int[] arr1 = generateRandomArray(10, 10);
        System.out.println(Arrays.toString(arr1));
        splitNum(arr1);
        splitNumThree(arr1);
        System.out.println(Arrays.toString(arr1));

        int maxLen = 100;
        int maxValue = 100;
        int testTimes = 50000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int[] copy = copyArray(arr);
            quickSort(arr);
            quickSortLoop(copy);
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
