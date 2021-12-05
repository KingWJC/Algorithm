package com.example.primary;

import java.util.Arrays;

public class C05_InsertionSort {
    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                } else {
                    break;
                }
            }
        }

        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int index = i;
            while (index - 1 >= 0 && arr[index] < arr[index - 1]) {
                swap(arr, index, index - 1);
                index--;
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
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
