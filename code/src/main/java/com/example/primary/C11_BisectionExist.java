/**
 * 二分法: 有序数组中找到num
 */
package com.example.primary;

import java.util.Arrays;

public class C11_BisectionExist {
    public static int find(int[] arr, int value) {
        if (arr == null || arr.length > 0) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static int findByBisection(int[] arr, int value) {
        if (arr == null || arr.length > 0) {
            return -1;
        }

        int l = 0;
        int r = arr.length - 1;
        while (l <= r) {
            int mid = (r + l) / 2;
            if (arr[mid] < value) {
                l = mid + 1;
            } else if (arr[mid] > value) {
                r = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int testTimes = 100000;
        int maxLen = 50;
        int maxValue = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = C10_CompareArr.generateRandomArr(maxLen, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (find(arr, value) != findByBisection(arr, value)) {
                System.out.println("出错了:" + value);
                break;
            }
        }
    }
}
