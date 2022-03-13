/**
 * 整数数组子数组累加和小于等于K的最大长度
 */
package com.example.system.class26;

public class C02_LongestLessSumSubArray {
    /**
     * 使用辅助数组，淘汰可能性。
     * 最优解：O(N)
     */
    public static int maxLengthAwesome(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }

        int N = arr.length;
        int[] minSum = new int[N];
        int[] minSumEnd = new int[N];
        for (int i = N - 1; i >= 0; i--) {
            if (i != N - 1 && minSum[i + 1] < 0) {
                minSum[i] = arr[i] + minSum[i + 1];
                minSumEnd[i] = minSumEnd[i + 1];
            } else {
                minSum[i] = arr[i];
                minSumEnd[i] = i;
            }
        }

        // 窗口累加和
        int sum = 0;
        // 窗口右边界的下一个位置(迟迟扩不进来那一块儿的开头位置)
        // 初始窗口[-1,-1],所以end为0.
        int end = 0;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            // while循环结束之后：
            // 1) 如果以i开头的情况下，累加和<=k的最长子数组是arr[i..end-1]，看看这个子数组长度能不能更新ans；
            // 2) 如果以i开头的情况下，累加和<=k的最长子数组比arr[i..end-1]短，更新还是不更新ans都不会影响最终结果，所以直接算end；
            while (end < N && sum + minSum[end] <= k) {
                sum += minSum[end];
                end = minSumEnd[end] + 1;
            }
            ans = Math.max(ans, end - i);

            if (end > i) {
                // 窗口左缩1位
                sum -= arr[i];
            } else {
                // 窗口为空，没有数字 [i~end) [6,6)，说明以i开头的所有子数组的累加和，不可能<=k，窗口整体右移1位
                // 即将 i++, 若i > end, 此时窗口概念维持不住了，所以end跟着i一起加1
                end = i + 1;
            }

        }
        return ans;
    }

    /**
     * 使用前缀和数组和有序表的查找方法
     * 次优解：O(N*logN)
     */
    public static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }

        int N = arr.length;
        // [0-i]范围内的最大前缀和。
        int[] preSum = new int[N + 1];
        // 空数组的前缀和为0
        preSum[0] = 0;
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
            preSum[i + 1] = Math.max(sum, preSum[i]);
        }

        // 每个以i为结束位置的子数组的前缀和
        sum = 0;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
            // 以i为结束位置的子数组，若在0-pre范围的前缀和<=sum-k，则【pre，i】为以i为结束位置的最长子数组。
            int pre = getLessIndex(preSum, sum - k);
            int len = pre == -1 ? 0 : i - pre + 1;
            ans = Math.max(ans, len);
        }
        return ans;
    }

    /**
     * 二分查找，数组中大于等于num，最小索引（最近的位置）。
     */
    private static int getLessIndex(int[] arr, int num) {
        int l = 0;
        int r = arr.length - 1;
        int ans = -1;
        while (l <= r) {
            int mid = (r + l) / 2;
            if (arr[mid] >= num) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        int len = 10;
        int maxValue = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            // [-6,14)
            int[] arr = generateRandomArray(len, maxValue);
            // [-5,15)
            int k = (int) (Math.random() * maxValue) - 5;
            if (maxLength(arr, k) != maxLengthAwesome(arr, k)) {
                System.out.println("Oops");
                break;
            }
        }
        System.out.println("test finish");
    }

    private static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }
}
