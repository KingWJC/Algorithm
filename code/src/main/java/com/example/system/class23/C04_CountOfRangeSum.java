/**
 * 区间和达标的子数组数量 (同class03下的C05_CountOfRangeSum)
 */
package com.example.system.class23;

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

        int windowL = l;
        int windowR = l;
        int l2 = mid + 1;
        long[] help = new long[r - l + 1];
        int index = 0;
        for (int i = l; i <= mid; i++) {
            // 计算满足的个数。
            while (windowL <= mid && sums[windowL] < sums[i] - upper) {
                windowL++;
            }
            while (windowR <= mid && sums[windowR] <= sums[i] - lower) {
                windowR++;
            }
            count += windowR - windowL;

            // 归并排序
            while (l2 <= r && sums[i] > sums[l2]) {
                help[index++] = sums[l2++];
            }
            help[index++] = sums[i];
        }

        return count;
    }

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3 };
        System.out.println(countRangeSum1(arr, 2, 5));
    }
}
