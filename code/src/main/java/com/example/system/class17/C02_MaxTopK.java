/**
 * 无序数组中求Top K
 */
package com.example.system.class17;

import java.util.Arrays;

public class C02_MaxTopK {
    /**
     * 排序后取前K O(N*logN)
     */
    public static int[] maxTopK1(int[] arr, int K) {
        if (arr == null || arr.length < 1 || K > arr.length) {
            return new int[0];
        }

        int[] ans = new int[K];
        Arrays.sort(arr);
        for (int i = 0, j = arr.length - 1; i < K; i++, j--) {
            ans[i] = arr[j];
        }
        return ans;
    }

    /**
     * 堆排序 O(N+K*LogN)
     * 把一个无序数组，用从底向上heapify，建立大根堆O(N)
     */
    public static int[] maxTopK2(int[] arr, int K) {
        if (arr == null || arr.length < 1 || K > arr.length) {
            return new int[0];
        }
        // 从底向上建堆，时间复杂度O(N)
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
        // 堆中取前K个最大值，不是在数组中的前K个，
        // 大根堆每次弹出最大，需要把结尾的数交换到0位置，并heapify
        // 相当于poll后，移除的数移到后面，重新heapify
        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        int count = 1;
        while (heapSize > 0 && count < K) {
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
            count++;
        }

        int[] ans = new int[K];
        for (int i = 0, j = arr.length - 1; i < K; i++, j--) {
            ans[i] = arr[j];
        }
        return ans;
    }

    /**
     * 向下比较，取最大上旋，大根堆
     * 有边界限制，需要传入堆在数组上的实际大小。
     */
    private static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) {
                break;
            }
            swap(arr, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

    /**
     * 向上比较，大的上旋
     */
    private static void heapInsert(int[] arr, int index) {
        int father = (index - 1) / 2;
        while (father >= 0 && arr[father] < arr[index]) {
            swap(arr, father, index);
            index = father;
            father = (index - 1) / 2;
        }
    }

    private static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    /**
     * 修改快排
     */
    public static int[] maxTopK3(int[] arr, int K) {
        if (arr == null || arr.length < 1 || K > arr.length) {
            return new int[0];
        }

        // O(N)
        int kValue = minKth(arr, arr.length - K);
        int[] ans = new int[K];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > kValue) {
                ans[index++] = arr[i];
            }
        }
        for (; index < K; index++) {
            ans[index] = kValue;
        }

        // O(K*logK) 从小到大排序
        Arrays.sort(ans);
        // 从大到小排。
        for (int L = 0, R = K - 1; L < R; L++, R--) {
            swap(ans, L, R);
        }
        return ans;
    }

    private static int minKth(int[] arr, int index) {
        int[] range = null;
        int L = 0;
        int R = arr.length - 1;
        int pivot = 0;
        while (L < R) {
            pivot = arr[L + (int) (Math.random() * (R - L + 1))];
            range = partition(arr, L, R, pivot);
            if (index >= range[0] && index <= range[1]) {
                return pivot;
            } else if (index > range[1]) {
                L = range[1] + 1;
            } else {
                R = range[0] - 1;
            }
        }
        return arr[L];
    }

    private static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
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

    public static void main(String[] args) {
        int testTimes = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test start");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            // K 保证大于0，且小于等于arr.length
            int K = (int) (Math.random() * arr.length) + 1;

            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);

            int[] ans1 = maxTopK1(arr1, K);
            int[] ans2 = maxTopK2(arr2, K);
            int[] ans3 = maxTopK3(arr3, K);

            if (!isEqual(ans1, ans2) || !isEqual(ans3, ans2)) {
                System.out.println("error K=" + K);
                break;
            }
        }
        System.out.println("finished");
    }

    private static int[] generateRandomArray(int maxLength, int maxValue) {
        int length = (int) (Math.random() * maxLength) + 1;
        int[] ans = new int[length];
        for (int i = 0; i < length; i++) {
            // [-? , +?]
            ans[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
        }
        return ans;
    }

    private static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    private static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}
