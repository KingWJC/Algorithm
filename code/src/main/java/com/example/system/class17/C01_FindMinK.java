/**
 * 在无序数组中求第K小的数
 * 三种方法：O(N),O(N),O(N*logN)
 */
package com.example.system.class17;

import java.util.Comparator;
import java.util.PriorityQueue;

public class C01_FindMinK {
    /**
     * 改写快排，O(N)
     */
    public static int minKth1(int[] arr, int K) {
        if (arr == null || arr.length < 1 || K > arr.length) {
            return -1;
        }
        // 数组下标从0开始，但第K小是从1开始，k-1完成对应
        return process(arr, 0, arr.length - 1, K - 1);
    }

    /**
     * arr[L..R]范围上，如果排序的话, 返回位于index的数
     * K一定在L和R之间，返回第K小的值
     */
    private static int process(int[] arr, int L, int R, int index) {
        // L==R==index
        if (L == R) {
            return arr[L];
        }
        // L + [0, R -L]
        int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return process(arr, L, range[0] - 1, index);
        } else {
            return process(arr, range[1] + 1, R, index);
        }
    }

    /**
     * 分组
     */
    private static int[] partition(int[] arr, int L, int R, int pivot) {
        // 记录小于pivot的区域
        int less = L - 1;
        // 记录大于pivot的区域
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, cur++, ++less);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }

    private static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    /**
     * 使用bfprt算法，O(N)
     */
    public static int minKth2(int[] arr, int K) {
        if (arr == null || arr.length < 1 || K > arr.length) {
            return -1;
        }

        return bfprt(arr, 0, arr.length - 1, K - 1);
    }

    /**
     * arr[L..R] 如果排序的话，返回位于index位置的数.
     */
    private static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[index];
        }

        int pivot = medianOfMedian(arr, L, R);
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return pivot;
        } else if (index < range[0]) {
            return bfprt(arr, L, range[1] - 1, index);
        } else {
            return bfprt(arr, range[0] + 1, R, index);
        }
    }

    /**
     * 获取数组中位数的值
     * 1.每5个分一组，取每组的中位数，得到中位数数组，
     * 2.获取中位数数组的中位数
     */
    private static int medianOfMedian(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];

        for (int i = 0; i < mArr.length; i++) {
            int groupFirt = L + 5 * i;
            // L ... L + 4
            // L +5 ... L +9
            // L +10....L+14
            int groupEnd = Math.min(R, groupFirt + 4);

            insertionSort(arr, groupFirt, groupEnd);
            mArr[i] = arr[(groupFirt + groupEnd) / 2];
        }
        // marr中，找到中位数
        // marr(0, marr.len - 1, mArr.length / 2 )
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    /**
     * 插入排序，常数项最小。
     */
    private static void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L; j--) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 使用大根堆，O(N*logK)
     */
    public static int minKth3(int[] arr, int K) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
        for (int i = 0; i < K; i++) {
            maxHeap.add(arr[i]);
        }
        for (int i = K; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    /**
     * 从大到小排
     */
    public static class MaxHeapComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer arg0, Integer arg1) {
            return arg1 - arg0;
        }
    }

    public static void main(String[] args) {
        int testTimes = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int K = (int) (Math.random() * arr.length) + 1;

            int[] arr1 = copyArray(arr);
            int ans1 = minKth1(arr1, K);
            int[] arr2 = copyArray(arr);
            int ans2 = minKth2(arr2, K);
            int[] arr3 = copyArray(arr);
            int ans3 = minKth2(arr3, K);
            if (ans1 != ans2 && ans2 != ans3) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finished");
    }

    private static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    private static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }
}
