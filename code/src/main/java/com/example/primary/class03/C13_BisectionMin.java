/**
 * 无序且相邻不等的数组, 找一个局部最小值(比前后都小).
 * 二分不一定有序.
 */
package com.example.primary.class03;

import java.util.Arrays;

public class C13_BisectionMin {
    /**
     * 生成无序且相邻不等的数组
     * 
     * @param maxLen
     * @param maxValue
     * @return
     */
    private static int[] generateNoEqualArr(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen);
        int[] arr = new int[len];
        if (len > 0) {
            arr[0] = (int) (Math.random() * maxValue);
            for (int i = 1; i < len; i++) {
                do {
                    arr[i] = (int) (Math.random() * maxValue);
                } while (arr[i] == arr[i - 1]);
            }
        }
        return arr;
    }

    /**
     * 判断是否局部最小.
     * 
     * @param arr
     * @param minIndex
     * @return
     */
    private static boolean check(int[] arr, int minIndex) {
        int left = minIndex - 1;
        int right = minIndex + 1;
        boolean leftBigger = left >= 0 ? arr[minIndex] < arr[left] : true;
        boolean rightBigger = right < arr.length ? arr[minIndex] < arr[right] : true;
        return leftBigger && rightBigger;
    }

    /**
     * 二分获取局部最小值.(引发边界问题)
     * 如: 3 2 3 2 3
     *     l   m   r 
     *     l r  第三步只有两个数参与, 所以midIndx-1越界.
     */
    private static int getMinIndex(int[] arr)  {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int len = arr.length;
        if (len == 1)
            return 0;

        // 若arr[0]<arr[1] 上升 则arr[0]为局部最小
        if (arr[0] < arr[1])
            return 0;
        // 若arr[n-2]> arr[n-1] 下降 则arr[len-1]为局部最小
        if (arr[len - 1] < arr[len - 2])
            return len - 1;

        int l = 0;
        int r = len - 1;
        int ans = -1;
        while (l <= r) {
            int midIndex = l + ((r - l) >> 1);
            // ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 3
            if (arr[midIndex] < arr[midIndex - 1] && arr[midIndex] < arr[midIndex + 1]) {
                ans = midIndex;
                break;
            } else {
                // 1 左 < minIndx < 右
                // 2 左 < minIndx > 右
                // 3 左 > minIndx > 右
                if (arr[midIndex] > arr[midIndex - 1]) {
                    r = midIndex - 1;
                } else {
                    l = midIndex + 1;
                }
            }
        }
        return ans;
    }

    /**
     * 解决边界问题.
     * 
     * @param arr
     * @return
     */
    private static int getMinIndexNew(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int len = arr.length;
        if (len == 1)
            return 0;

        // 若arr[0]<arr[1] 上升 则arr[0]为局部最小
        if (arr[0] < arr[1])
            return 0;
        // 若arr[n-2]> arr[n-1] 下降 则arr[len-1]为局部最小
        if (arr[len - 1] < arr[len - 2])
            return len - 1;

        int l = 0;
        int r = len - 1;
        // 保证最少3个数.
        while (l < r - 1) {
            int midIndex = l + ((r - l) >> 1);
            if (arr[midIndex] < arr[midIndex - 1] && arr[midIndex] < arr[midIndex + 1]) {
                return midIndex;
            } else {
                // 1 左 < minIndx < 右
                // 2 左 < minIndx > 右
                // 3 左 > minIndx > 右
                if (arr[midIndex] > arr[midIndex - 1]) {
                    r = midIndex - 1;
                } else {
                    l = midIndex + 1;
                }
            }
        }

        return arr[l] < arr[r] ? l : r;
    }

    public static void main(String[] args) {
        int testTimes = 100000;
        int maxLen = 5;
        int maxValue = 20;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateNoEqualArr(maxLen, maxValue);
            int minIndex = getMinIndexNew(arr);
            if (!check(arr, minIndex)) {
                System.out.println(Arrays.toString(arr));
                System.out.println("错误的minIndex:" + minIndex);
                break;
            }
        }
    }
}
