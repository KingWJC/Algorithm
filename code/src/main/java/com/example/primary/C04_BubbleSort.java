package com.example.primary;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class C04_BubbleSort {
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    swap(arr, j, j + 1);
                }
            }
        }

        int n = arr.length; 
        for (int end = n - 1; end >= 0; end--) {
            for (int second = 1; second < end; second++) {
                if (arr[second - 1] > arr[second]) {
                    swap(arr, second - 1, second);
                }
            }
        }
    }

    private static void swap(int[] arr, int index, int minValueIndex) {
        int temp = arr[index];
        arr[index] = arr[minValueIndex];
        arr[minValueIndex] = temp;
    }

    public static void main(String[] args) {
        int[] arr = { 7, 2, 3, 1, 5, 8, 6, 0 };
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
