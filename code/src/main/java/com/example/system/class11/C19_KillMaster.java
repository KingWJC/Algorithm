/**
 * 英雄砍死怪兽的概率
 * 怪兽有n滴血，等着英雄来砍自己
 * 英雄每一次打击，都会让怪兽流失[0~M]的血量,每一次在[0~M]上等概率的获得一个值
 * K次打击
 */
package com.example.system.class11;

public class C19_KillMaster {
    /**
     * 暴力尝试，从左到右的尝试模型
     */
    public static double getKillRate(int N, int K, int M) {
        if (N < 1 || K < 1 || M < 1) {
            return 0;
        }

        return (double) process(N, K, M) / Math.pow(M + 1, K);
    }

    /**
     * 当还剩times次攻击，返回砍死有HP滴血的怪兽的情况数！
     */
    private static long process(int hp, int times, int M) {
        if (times == 0) {
            return hp <= 0 ? 1 : 0;
        }

        // 通过动态规划，推出的减枝策略
        if (hp <= 0) {
            return (long) Math.pow(M + 1, times);
        }

        long ways = 0;
        for (int i = 0; i <= M; i++) {
            ways += process(hp - i, times - 1, M);
        }
        return ways;
    }

    /**
     * 动态规划:有枚举行为
     * 表中注意边界问题。
     */
    public static double useDP1(int HP, int K, int M) {
        if (HP < 1 || K < 1 || M < 1) {
            return 0;
        }
        // 压缩空间：忽略HP<0的左边区域。
        long[][] dp = new long[K + 1][HP + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= K; times++) {
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= HP; hp++) {
                long ways = 0;
                for (int i = 0; i <= M; i++) {
                    if (hp - i > 0) { // >=0,>=1 结果都一样。
                        ways += dp[times - 1][hp - i];
                    } else {
                        // 攻击一次后，若HP为负数，则用公式计算死亡次数。
                        ways += Math.pow(M + 1, times - 1);
                    }
                }
                dp[times][hp] = ways;
            }
        }

        return (double) dp[K][HP] / Math.pow(M + 1, K);
    }

    /**
     * 动态规划：斜率优化
     */
    public static double useDP2(int HP, int K, int M) {
        if (HP < 1 || K < 1 || M < 1) {
            return 0;
        }
        long[][] dp = new long[K + 1][HP + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= K; times++) {
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= HP; hp++) {
                dp[times][hp] = dp[times - 1][hp] + dp[times][hp - 1];
                if (hp - 1 - M > 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - M];
                } else {
                    dp[times][hp] -= Math.pow(M + 1, times - 1);
                }
            }
        }
        return (double) dp[K][HP] / Math.pow(M + 1, K);
    }

    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 20000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = getKillRate(N, K, M);
            double ans2 = useDP1(N, K, M);
            double ans3 = useDP2(N, K, M);
            if (ans1 != ans2 && ans2 != ans3) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
