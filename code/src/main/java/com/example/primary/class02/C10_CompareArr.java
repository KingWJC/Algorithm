package com.example.primary.class02;

import java.util.Arrays;

import com.example.primary.class01.C03_SelectionSort;
import com.example.primary.class01.C04_BubbleSort;
import com.example.primary.class01.C05_InsertionSort;

/**
 * 对数器. 生成随机样本, 自己进行比对.
 */
public class C10_CompareArr {
    /**
     * 生成数组.
     * 数组的长度[0,maxLen-1]和大小[0,maxVaule-1]随机
     * 
     * @param maxLen
     * @param maxValue
     */
    public static int[] generateRandomArr(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen);
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    /**
     * 判断是否排序成功.
     * 
     * @param arr
     * @return
     */
    private static boolean isSorted(int[] arr) {
        if (arr.length < 2)
            return true;
        int maxValue = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (maxValue > arr[i])
                return false;
            maxValue = arr[i];
        }
        return true;
    }

    public static int[] copyArray(int[] arr) {
        int[] copyArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copyArr[i] = arr[i];
        }
        return copyArr;
    }

    /**
     * 对比两数组的值, arr1,arr2等长.
     * 
     * @param arr1 选择排序
     * @param arr2 冒泡排序
     * @return
     */
    public static boolean compareArr(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i])
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int maxLen = 50;
        int maxValue = 1000;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomArr(maxLen, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = Arrays.copyOf(arr1, arr1.length);

            int[] temp = copyArray(arr1);

            C03_SelectionSort.selectSort(arr1);
            if (!isSorted(arr1)) {
                System.out.println("选择排序错误!");
                System.out.println("原始:" + Arrays.toString(temp));
                System.out.println("排序:" + Arrays.toString(arr1));
                break;
            }

            C04_BubbleSort.bubbleSort(arr2);
            if (!isSorted(arr2)) {
                System.out.println("冒泡排序错误!");
                System.out.println("原始:" + Arrays.toString(temp));
                System.out.println("排序:" + Arrays.toString(arr2));
                break;
            }

            C05_InsertionSort.insertSort(arr3);
            if (!isSorted(arr3)) {
                System.out.println("插入排序错误!");
                System.out.println("原始:" + Arrays.toString(temp));
                System.out.println("排序:" + Arrays.toString(arr3));
                break;
            }

            if (!compareArr(arr1, arr2)) {
                System.out.println("选择排序和冒泡排序不同!");
                System.out.println("选择:" + Arrays.toString(arr1));
                System.out.println("冒泡:" + Arrays.toString(arr2));
                break;
            }
        }

    }
}
