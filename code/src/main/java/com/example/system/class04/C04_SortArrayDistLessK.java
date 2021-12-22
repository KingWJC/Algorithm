/**
 * 排序一个几乎有序的数组。
 * 几乎有序是指，如果把数组排好顺序的话，
 * 每个元素移动的距离一定不超过k，并且k相对于数组长度来说是比较小的
 */
package com.example.system.class04;

import java.util.Arrays;
import java.util.PriorityQueue;

import com.example.utility.helper.ArrayTestHelper;

public class C04_SortArrayDistLessK {
    public static void sortedArrDistanceLessK(int[] arr, int k) {
        if (k == 0 || arr == null || arr.length < 2)
            return;

        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        for (; index < Math.min(arr.length, k); index++) {
            heap.add(arr[index]);
        }

        int i = 0;
        for (; index < arr.length; i++, index++) {
            // 每个元素移动K个位置, 则heap保持k+1个数. 所以heap先add再poll
            heap.add(arr[index]);
            arr[i] = heap.poll();
        }

        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }

        // 不能使用K进行记录，有可能K比arr.Length大
        // int index = 0;
        // while (!heap.isEmpty()) {
        // arr[index++] = heap.poll();
        // if (index + k < arr.length) {
        // heap.add(arr[index + k]);
        // }
        // }
    }

    public static void testSort(int[] arr) {
        Arrays.sort(arr);
    }

    /**
     * 生成一个几乎有序的数组，移动范围不超过K
     */
    public static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int K) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        Arrays.sort(arr);

        // 然后开始随意交换，但是保证每个数距离不超过K
        // swap[i] == true, 表示i位置已经参与过交换
        // swap[i] == false, 表示i位置没有参与过交换
        boolean[] isSwap = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            // 获取K范围内的需要交换的随机索引, i为起始位置.
            int swapIndex = i + (int) (Math.random() * (K + 1));
            swapIndex = Math.min(swapIndex, arr.length - 1);
            if (!isSwap[i] && !isSwap[swapIndex]) {
                isSwap[i] = true;
                isSwap[swapIndex] = true;
                swap(arr, i, swapIndex);
            }
        }
        return arr;
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr1 = randomArrayNoMoveMoreK(maxSize, maxValue, k);
            int[] arr2 = ArrayTestHelper.copyArray(arr1);
            // System.out.println(Arrays.toString(arr1));
            sortedArrDistanceLessK(arr1, k);
            testSort(arr2);
            if (!ArrayTestHelper.isEquals(arr1, arr2)) {
                succeed = false;
                System.out.println("K=" + k);
                System.out.println(Arrays.toString(arr1));
                System.out.println(Arrays.toString(arr2));
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
