/**
 * 堆排序
 * 额外空间复杂度O(1)
 */
package com.example.system.class04;

import java.util.Arrays;

import com.example.utility.helper.ArrayTestHelper;

public class C03_HeapSort {
    /**
     * 从上向下生成堆
     */
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        // O(N*logN)
        for (int i = 0; i < arr.length; i++) { // O(N)
            heapInsert(arr, i); // O(LogN)
        }

        // O(N*logN)
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, 0, i);
        }
    }

    /**
     * 从下向上生成堆
     */
    public static void heapSortNew(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        // O(N)
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        int heapSize = arr.length;
        swap(arr, 0, --heapSize);

        // O(N*logN)
        while (heapSize > 0) {// O(N)
            heapify(arr, 0, heapSize);// O(logN)
            swap(arr, 0, --heapSize);// O(1)
        }
    }

    /**
     * arr[index]刚来的数，往上
     */
    private static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * arr[index]位置的数，能否往下移动
     * 为什么left 和 left+1 不能是等于 heapSize。
     * heapSize表示heap中元素的数量.
     */
    private static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) { // 下方还有孩子的时候
            // 两个孩子中，谁的值大，把下标给largest
            // 1）只有左孩子，left -> largest
            // 2) 同时有左孩子和右孩子，右孩子的值<= 左孩子的值，left -> largest
            // 3) 同时有左孩子和右孩子并且右孩子的值> 左孩子的值， right -> largest
            int largest = left + 1 < heapSize && arr[left] < arr[left + 1] ? left + 1 : left;
            // 父和较大的孩子之间，谁的值大，把下标给largest
            largest = arr[index] < arr[largest] ? largest : index;
            if (largest == index)
                break;
            swap(arr, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * 测试排序
     */
    public static void testSort(int[] arr) {
        Arrays.sort(arr);
    }

    public static void main(String[] args) {
        ArrayTestHelper.testSortMethod(C03_HeapSort::testSort, C03_HeapSort::heapSortNew);
    }
}
