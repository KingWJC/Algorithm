/**
 * 二分搜索，查找结果
 * 在有序的数组中。
 */
package com.example.system.class01;

import java.util.Arrays;

import com.example.utility.helper.ArrayTestHelper;

public class C02_BinarySearch {
    /**
     * 在一个有序数组中，找某个数是否存在(
     * 二分查找
     */
    public static boolean exists(int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0) {
            return false;
        }

        int l = 0;
        int r = sortedArr.length - 1;
        int m = 0;
        // L..R 至少两个数的时候，
        while (l < r) {
            m = l + (r - l) / 2;
            if (sortedArr[m] == num) {
                return true;
            } else if (sortedArr[m] > num) {
                r = m - 1;
            } else if (sortedArr[m] < num) {
                l = m + 1;
            }
        }

        // L..R 只有一个数的情况。
        return sortedArr[l] == num;
    }

    /**
     * 迭代查找
     */
    public static boolean testExistes(int[] sortedArr, int num) {
        for (int i = 0; i < sortedArr.length; i++) {
            if (sortedArr[i] == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * 在一个有序数组中，找大于等于某个数最左侧的位置
     * 二分查找
     */
    public static int nearestLeft(int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0) {
            return -1;
        }

        int l = 0;
        int r = sortedArr.length;
        int index = -1;
        // 至少一个数的时候, 只有一个数，也要判断条件，确定index
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (sortedArr[m] >= num) {
                index = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return index;
    }

    /**
     * 迭代查找
     */
    public static int testnearestLeft(int[] sortedArr, int num) {
        for (int i = 0; i < sortedArr.length; i++) {
            if (sortedArr[i] >= num) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 在一个有序数组中，找小于等于某个数最右侧的位置
     * 二分查找
     */
    public static int nearestRight(int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0) {
            return -1;
        }

        int l = 0;
        int r = sortedArr.length;
        int index = -1;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (sortedArr[m] <= num) {
                index = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return index;
    }

    /**
     * 迭代查找
     */
    public static int testnearestRight(int[] sortedArr, int num) {
        for (int i = sortedArr.length - 1; i > -1; i--) {
            if (sortedArr[i] <= num) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        existTest();

        ArrayTestHelper.testBinarySearch(
                (sortedArr, value) -> {
                    return testnearestLeft(sortedArr, value);
                }, (sortedArr, value) -> {
                    return nearestLeft(sortedArr, value);
                });

        ArrayTestHelper.testBinarySearch(
                (sortedArr, value) -> {
                    return testnearestRight(sortedArr, value);
                }, (sortedArr, value) -> {
                    return nearestRight(sortedArr, value);
                });
    }

    private static void existTest() {
        int times = 50000;
        int maxLength = 100;
        int maxValue = 100;
        System.out.println("begin test");
        for (int i = 0; i < times; i++) {
            int[] arr = TestHelper.generateRandomArray(maxLength, maxValue);
            Arrays.sort(arr);

            int num = (int) (Math.random() * (maxValue + 1));
            if (testExistes(arr, num) != exists(arr, num)) {
                System.out.println("error");
                TestHelper.printArray(arr);
                System.out.println(testExistes(arr, num));
                System.out.println(exists(arr, num));
                break;
            }
        }
        System.out.println("finish");
    }
}
