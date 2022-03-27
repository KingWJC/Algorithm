/**
 * 拼接最大数
 * 测试链接: https://leetcode.com/problems/create-maximum-number/
 */
package com.example.system.class29;

import java.util.Arrays;

public class C03_CreateMaxinumNumber {
    /**
     * 答案解
     */
    public static int[] maxNumber1(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        if (k < 0 || k > len1 + len2) {
            return null;
        }

        int[] res = new int[k];
        int[][] dp1 = getdp(nums1);
        int[][] dp2 = getdp(nums2);
        // get1 从arr1里拿的数量
        for (int get1 = Math.max(0, k - len2); get1 <= Math.min(k, len1); get1++) {
            int[] pick1 = maxPick(nums1, dp1, get1);
            int[] pick2 = maxPick(nums2, dp2, k - get1);
            int[] merge = merge(pick1, pick2);
            res = perMoreThanLast(res, 0, merge, 0) ? res : merge;
        }
        return res;
    }

    /**
     * 预处理结构：dp[i][j]表示从i开始一直往后，挑选j个数
     * 返回选择的数的开始的位置。
     */
    private static int[][] getdp(int[] nums) {
        // 挑选数字的个数小于等于数组长度。
        int N = nums.length;
        int[][] dp = new int[N][N + 1];
        for (int i = 1; i < N; i++) {
            dp[i][N - i] = i;
        }
        for (int j = 1; j <= N; j++) {
            for (int i = N - j - 1; i >= 0; i--) {
                dp[i][j] = nums[i] < nums[dp[i + 1][j]] ? dp[i + 1][j] : i;
            }
            // for (int i = N - 2; i >= 0; i--) {
            //     if ((N-i) >= j) {
            //         dp[i][j] = nums[i] < nums[dp[i + 1][j]] ? dp[i + 1][j] : i;
            //     }
            // }
        }
        return dp;
    }

    /**
     * 在数组上选择pick个数，组成的最大结果
     */
    private static int[] maxPick(int[] nums, int[][] dp, int pick) {
        int[] res = new int[pick];
        int dpRow = 0;
        int resIndex = 0;
        while (pick > 0) {
            res[resIndex++] = nums[dp[dpRow][pick]];
            dpRow = dp[dpRow][pick--] + 1;
        }
        return res;
    }

    /**
     * 合并两个数组，组成的最大结果。
     */
    private static int[] merge(int[] nums1, int[] nums2) {
        int k = nums1.length + nums2.length;
        int[] res = new int[k];
        for (int i = 0, j = 0, index = 0; index < k; index++) {
            res[index] = perMoreThanLast(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
        }
        return res;
    }

    /**
     * 两数组比较范围上的字典序，返回是否左边nums1[i....]的字典序大
     */
    private static boolean perMoreThanLast(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }

    /**
     * 最优解
     */
    public static int[] maxNumber2(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        if (k < 0 || k > len1 + len2) {
            return null;
        }

        int[][] dp1 = getdpT(nums1);
        int[][] dp2 = getdpT(nums2);
        int[] res = new int[k];
        for (int get = Math.max(0, k - len2); get <= Math.min(k, len1); get++) {
            int[] pick1 = maxPick(nums1, dp1, get);
            int[] pick2 = maxPick(nums2, dp2, k - get);
            int[] merge = mergeBySuffixArray(pick1, pick2);
            res = perMoreThanLast(res, merge) ? res : merge;
        }
        return res;
    }

    private static int[][] getdpT(int[] nums) {
        int n = nums.length;// 0~N-1
        int m = n + 1;// 1 ~ N
        int[][] dp = new int[n][m];
        // i 不从0开始，因为拿0个无意义
        for (int j = 1; j < m; j++) {
            int maxIndex = n - j;
            // i~N-1,且i+j=n
            for (int i = n - j; i >= 0; i--) {
                if (nums[i] >= nums[maxIndex]) {
                    maxIndex = i;
                }

                dp[i][j] = maxIndex;
            }
        }
        return dp;
    }

    /**
     * 合并使用后缀数组DC3
     */
    private static int[] mergeBySuffixArray(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        // 连接两数组，增加分隔符
        int[] nums = new int[len1 + len2 + 1];
        for (int i = 0; i < len1; i++) {
            nums[i] = nums1[i] + 2;
        }
        nums[len1] = 1;
        for (int i = 0; i < len2; i++) {
            nums[len1 + 1 + i] = nums2[i] + 2;
        }
        // 最大值是9，处理后加2，是11
        DC3 dc = new DC3(nums, 11);
        int[] rank = dc.rank;

        int[] ans = new int[len1 + len2];
        int i = 0, j = 0, index = 0;
        while (i < len1 && j < len2) {
            ans[index++] = rank[i] > rank[len1 + 1 + j] ? nums1[i++] : nums2[j++];
        }
        while (i < len1) {
            ans[index++] = nums1[i++];
        }
        while (j < len2) {
            ans[index++] = nums2[j++];
        }
        return ans;
    }

    private static boolean perMoreThanLast(int[] pre, int[] last) {
        int i = 0, j = 0;
        while (i < pre.length && j < last.length && pre[i] == last[j]) {
            i++;
            j++;
        }
        return j == last.length || (i < pre.length && pre[i] > last[j]);
    }

    public static void main(String[] args) {
        int[] nums1 = { 3, 4, 6, 5 };
        int[] nums2 = { 9, 1, 2, 5, 8, 3 };
        int k = 5;
        int[] ans1 = maxNumber1(nums1, nums2, k);
        int[] ans2 = maxNumber2(nums1, nums2, k);
        System.out.println(Arrays.toString(ans1));
        System.out.println(Arrays.toString(ans2));
    }
}
