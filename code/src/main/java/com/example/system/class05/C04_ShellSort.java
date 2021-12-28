/**
 * 希尔排序
 */
package com.example.system.class05;

import java.util.Arrays;

import com.example.utility.helper.ArrayTestHelper;

public class C04_ShellSort {
    public static void shellSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int len = arr.length;
        int gap = len / 2;
        while (gap > 0) {
            // 插入排序
            for (int i = gap; i < len; i++) {
                int temp = arr[i];
                int j = i - gap;
                for (; j >= 0 && temp < arr[j]; j -= gap) {
                    // 替换arr[i]的值.
                    arr[j + gap] = arr[j];
                }
                arr[j + gap] = temp;
            }

            gap /= 2;
        }
    }

    public static void testSort(int[] arr) {
        Arrays.sort(arr);
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 6, 5, 37, 49 };
        shellSort(arr);
        System.out.print(Arrays.toString(arr));
        
        ArrayTestHelper.testSortMethod(C04_ShellSort::testSort, C04_ShellSort::shellSort);
    }
}
