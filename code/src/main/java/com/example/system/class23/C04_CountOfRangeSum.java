/**
 * 区间和达标的子数组数量 (同class03下的C05_CountOfRangeSum)
 */
package com.example.system.class23;

import java.util.Arrays;

public class C04_CountOfRangeSum {
    /**
     * 改写归并O(N*logN)
     */
    public static int countRangeSum1(int[] nums, int lower, int upper) {
        if (nums == null || nums.length < 1) {
            return 0;
        }

        int n = nums.length;
        // 前缀和数组，从index=1开始
        long[] sums = new long[n];
        sums[0] = nums[0];
        for (int i = 0; i < n - 1; i++) {
            sums[i + 1] = sums[i] + nums[i + 1];
        }

        return process(sums, 0, n - 1, lower, upper);
    }

    /**
     * 在l-r范围上，获取满足条件的数的个数。
     */
    private static int process(long[] sums, int l, int r, int lower, int upper) {
        if (r == l) {
            return sums[l] >= lower && sums[l] <= upper ? 1 : 0;
        }

        int mid = l + (r - l) / 2;
        int count = process(sums, l, mid, lower, upper) + process(sums, mid + 1, r, lower, upper);

        // 计算满足的个数。
        int windowL = l;
        int windowR = l;
        for (int i = mid + 1; i <= r; i++) {// 调试测试
            while (windowL <= mid && sums[windowL] < sums[i] - upper) {
                windowL++;
            }
            while (windowR <= mid && sums[windowR] <= sums[i] - lower) {
                windowR++;
            }
            count += windowR - windowL;
        }

        int index = 0;
        int l1 = l;
        int l2 = mid + 1;
        long[] help = new long[r - l + 1];
        // 归并排序
        while (l2 <= r && l1 <= mid) {
            if (sums[l1] > sums[l2]) {
                help[index++] = sums[l2++];
            } else {
                help[index++] = sums[l1++];
            }
        }
        while (l1 <= mid) {
            help[index++] = sums[l1++];
        }
        while (l2 <= r) {
            help[index++] = sums[l2++];
        }

        for (int i = 0; i < help.length; i++) {
            sums[l + i] = help[i];
        }

        return count;
    }

    /**
     * 课堂的归并版本（没搞明白）
     */
    public static int countRangeSum2(int[] nums, int lower, int upper) {
        if (nums == null || nums.length < 1) {
            return 0;
        }

        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }

        return countWhileMergeSort(sums, 0, n + 1, upper, lower);
    }

    private static int countWhileMergeSort(long[] sums, int start, int end, int upper, int lower) {
        // 只有一个数时，返回0
        if (end - start <= 1) {
            return 0;
        }

        int mid = (start + end) / 2;
        int count = countWhileMergeSort(sums, start, mid, upper, lower)
                + countWhileMergeSort(sums, mid, end, upper, lower);

        int windowL = mid;
        int windowR = mid;

        int index = 0;
        long[] cache = new long[end - start];
        // sums的起始位置为0，是初始无效的前缀和，所以从++l开始。
        for (int l = start, r = mid; l < mid; ++l, r++) {
            while (windowL < end && sums[l] > sums[windowL] - lower) {
                windowL++;
            }
            while (windowR < end && sums[l] >= sums[windowR] - upper) {
                windowR++;
            }
            count += windowR - windowL;

            while (r < end && sums[r] < sums[l]) {
                cache[index++] = sums[r++];
            }
            cache[index++] = sums[l];
        }
        System.arraycopy(cache, 0, sums, start, end - start);
        return count;
    }

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3 };
        System.out.println(countRangeSum1(arr, 2, 5));

        int len = 200;
        int varible = 50;
        System.out.println("test start:");
        for (int i = 0; i < 100000; i++) {
            int[] test = generateArray(len, varible);
            int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
            int upper = lower + (int) (Math.random() * varible);
            int ans1 = countRangeSum1(test, lower, upper);
            int ans2 = countRangeSum2(test, lower, upper);
            if (ans1 != ans2) {
                System.out.println("Error: ans1=" + ans1 + " ans2=" + ans2);
                System.out.println(Arrays.toString(test));
                break;
            }
        }
        System.out.println("finished");
    }

    private static int[] generateArray(int len, int varible) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * varible) - (int) (Math.random() * varible);
        }
        return arr;
    }
}
