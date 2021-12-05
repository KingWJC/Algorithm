package com.example.primary;

import java.util.Arrays;

/**
 * 选择排序
 */
public class C03_SelectionSort {
    public static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            int minValueIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                minValueIndex = arr[j] < arr[minValueIndex] ? j : minValueIndex;
            }
            swap(arr, i, minValueIndex);
        }
    }

    private static void swap(int[] arr, int index, int minValueIndex) {
        if (minValueIndex != index) {
            int temp = arr[index];
            arr[index] = arr[minValueIndex];
            arr[minValueIndex] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arr = { 7, 2, 3, 1, 5, 8, 6, 0 };
        selectSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
