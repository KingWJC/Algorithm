/**
 * 时间复杂度：在最差情况的，常熟时间的操作。
 * 三种排序方法的时间复杂度计算。
 */
package com.example.system.class01;

public class C01_TimeComplexity {

    /**
     * 选择排序，时间复杂度为等差数组之和，O(N^2)
     */
    public static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        // 0 ~ N-1 找到最小值，在哪，放到0位置上
        // 1 ~ n-1 找到最小值，在哪，放到1 位置上
        // 2 ~ n-1 找到最小值，在哪，放到2 位置上
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            int selectIndex = i;
            for (int j = i + 1; j < length; j++) {
                if (arr[j] < arr[selectIndex]) {
                    selectIndex = j;
                }
            }

            swap(arr, selectIndex, i);
        }
    }

    /**
     * 冒泡排序，时间复杂度为等差数组之和，O(N^2)
     */
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        // 0 ~ N-1
        // 0 ~ N-2
        // 0 ~ N-3
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    /**
     * 插入排序，时间复杂度为等差数组之和，O(N^2)
     */
    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int length = arr.length;
        for (int j = 1; j < length; j++) {
            for (int i = j; i > 0 && arr[i - 1] > arr[i]; i--) {
                swap(arr, i - 1, i);
            }
        }
    }

    /**
     * 交换
     */
    private static void swap(int[] arr, int l, int r) {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }

    public static void main(String[] args) {
        int times = 50000;
        int maxLength = 10;
        int maxValue = 10;
        System.out.println("begin test");
        for (int i = 0; i < times; i++) {
            int[] arr = generateRandomArray(maxLength, maxValue);
            int[] copy1 = copyArray(arr);
            int[] copy2 = copyArray(arr);
            int[] copy3 = copyArray(arr);

            selectSort(copy3);
            bubbleSort(copy1);
            insertSort(copy2);

            if (!isEquals(copy3, copy1) || !isEquals(copy3, copy2)) {
                System.out.println("error");
                printArray(arr);
                printArray(copy3);
                printArray(copy1);
                printArray(copy2);
                break;
            }
        }
        System.out.println("finish");
    }

    public static int[] generateRandomArray(int maxLength, int maxValue) {
        int[] ans = new int[(int) (Math.random() * (maxLength + 1))];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * maxValue);
        }
        return ans;
    }

    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static boolean isEquals(int[] a, int[] b) {
        if (a == null ^ b == null) {
            return false;
        }
        if (a == null && b == null) {
            return true;
        }
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}