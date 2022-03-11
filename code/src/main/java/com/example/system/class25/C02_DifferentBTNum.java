/**
 * 二叉树N个节点无差别能形成多少种不同的结构
 */
package com.example.system.class25;

public class C02_DifferentBTNum {
    /**
     * 暴力递归
     */
    public static long nums1(int N) {
        if (N < 0) {
            return 0;
        }
        return process(N);
    }

    /**
     * N个节点可以组成多少种结构
     */
    private static long process(int N) {
        if (N <= 1) {
            return 1;
        }

        long ans = 0;
        // 左子树在0~N-1范围上，每次的组合数就是左子树的组合数*右子树的组合数，右子树节点数需要减去头节点
        for (int leftSize = 0; leftSize < N; leftSize++) {
            ans += process(leftSize) * process(N - 1 - leftSize);
        }
        return ans;
    }

    /**
     * 动态规划
     */
    public static long nums2(int N) {
        if (N < 0) {
            return 0;
        }
        if (N < 2) {
            return 1;
        }

        int[] dp = new int[N + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= N; i++) {
            for (int leftSize = 0; leftSize < i; leftSize++) {
                dp[i] += dp[leftSize] * dp[i - 1 - leftSize];
            }
        }
        return dp[N];
    }

    public static long nums3(int N) {
        if (N < 0) {
            return 0;
        }
        if (N < 2) {
            return 1;
        }

        long mFactorial = 1;
        long nFactorial = 1;
        for (int i = 1, j = N + 1; i <= N; i++, j++) {
            mFactorial *= i;
            nFactorial *= j;

            // 同时除以公约数，减小阶乘值。
            long gcd = gcd(mFactorial, nFactorial);
            mFactorial /= gcd;
            nFactorial /= gcd;
        }

        return (nFactorial / (N + 1)) / mFactorial;
    }

    /**
     * 计算公约数
     */
    private static long gcd(long a, Long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 10; i++) {
            System.out.println(nums1(i) + "====" + nums2(i) + "====" + nums3(i));
        }
        System.out.println("test finish");
    }
}
