/**
 * 有序数组中找到＞=num最左的位置 和 ＜=num最右的位置]
 */
package com.example.primary;

import java.util.Arrays;

public class C12_BisectionNear {
    public static int find(int[] arr, int value) {
        if (arr == null || arr.length > 0) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= value) {
                return i;
            }
        }
        return -1;
    }

    public static int findNearestIndexL(int[] arr, int value) {
        if (arr == null || arr.length > 0) {
            return -1;
        }

        int l = 0;
        int r = arr.length - 1;
        int nearestIndex = -1; // 没有找到,返回-1.
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if (arr[mid] >= value) {
                // ＞= value的位置
                nearestIndex = mid;
                r = mid - 1;
            } else if (arr[mid] < value) {
                l = mid + 1;
            }
        }
        return nearestIndex;
    }

    public static int findNearestIndexR(int[] arr, int value) {
        if (arr == null || arr.length > 0) {
            return -1;
        }

        int l = 0;
        int r = arr.length - 1;
        int nearestIndex = -1; // 没有找到,返回-1.
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if (arr[mid] <= value) {
                // <= value的位置
                nearestIndex = mid;
                l = mid + 1;
            } else if (arr[mid] < value) {
                r = mid - 1;
            }
        }
        return nearestIndex;
    }

    public static void main(String[] args) {
        int testTimes = 100000;
        int maxLen = 50;
        int maxValue = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = C10_CompareArr.generateRandomArr(maxLen, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (find(arr, value) != findNearestIndexL(arr, value)) {
                System.out.println(find(arr, value));
                System.out.println(findNearestIndexL(arr, value));
                System.out.println("出错了:" + value);
                break;
            }
        }
    }
}
