/**
 * 丢棋子问题(四边形不等式和最优尝试方法)
 * 测试链接：https://leetcode.com/problems/super-egg-drop
 */
package com.example.system.class27;

public class C06_ThrowChessPiecesProblem {
    /**
     * 暴力递归
     */
    public static int superEggDrop1(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }

        return process(nLevel, kChess);
    }

    /**
     * 在剩余的rest楼层中，使用k个棋子，要验证出最高的不会碎的楼层，、
     * 返回最坏情况下，至少需要扔几次
     */
    private static int process(int rest, int k) {
        // 已知棋子从第 0 层掉落肯定 不会摔碎，所以最少扔0次
        if (rest == 0) {
            return 0;
        }

        // 只有一个棋子时，最坏的情况下，还得扔rest楼层的次数
        if (k == 1) {
            return rest;
        }

        int min = Integer.MAX_VALUE;
        // 第一次扔的时候，仍在了i层，从1层开始
        for (int i = 1; i <= rest; i++) {
            min = Math.min(min, Math.max(process(i - 1, k - 1), process(rest - i, k)));
        }

        return min + 1;
    }

    /**
     * 动态规划
     */
    public static int superEggDrop2(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }

        if (kChess == 1) {
            return nLevel;
        }

        int[][] dp = new int[nLevel + 1][kChess + 1];
        for (int n = 1; n <= nLevel; n++) {
            dp[n][1] = n;
        }
        for (int k = 1; k <= kChess; k++) {
            dp[1][k] = 1;
        }
        for (int i = 2; i <= nLevel; i++) {
            for (int j = 2; j <= kChess; j++) {
                int min = Integer.MAX_VALUE;
                for (int cur = 1; cur <= i; cur++) {
                    min = Math.min(min, Math.max(dp[cur - 1][j - 1], dp[i - cur][j]));
                }
                dp[i][j] = min + 1;
            }
        }

        return dp[nLevel][kChess];
    }

    /**
     * 枚举优化：四边形不等式
     */
    public static int superEggDrop3(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }

        int[][] dp = new int[nLevel + 1][kChess + 1];
        // 记录第一次扔棋子的最优位置。
        int[][] bestChoose = new int[nLevel + 1][kChess + 1];
        for (int n = 1; n <= nLevel; n++) {
            dp[n][1] = n;
            bestChoose[n][1] = 0;
        }
        for (int k = 1; k <= kChess; k++) {
            dp[1][k] = 1;
            bestChoose[1][k] = 1;
        }
        for (int i = 2; i <= nLevel; i++) {
            for (int j = kChess; j > 1; j--) {
                int down = bestChoose[i - 1][j];
                int up = j == kChess ? i : bestChoose[i][j + 1];
                int ans = Integer.MAX_VALUE;
                int best = -1;
                for (int first = down; first <= up; first++) {
                    int leftTimes = dp[first - 1][j - 1];
                    int rightTimes = dp[i - first][j];
                    int cur = Math.max(leftTimes, rightTimes);
                    if (cur <= ans) {
                        ans = cur;
                        best = first;
                    }
                }
                dp[i][j] = ans + 1;
                bestChoose[i][j] = best;
            }
        }
        return dp[nLevel][kChess];
    }

    /**
     * 最优解：N个棋子，扔M次，所能搞定的最高楼层是多少。
     */
    public static int superEggDrop4(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }

        // 空间压缩
        int[] dp = new int[kChess];
        int times = 0;
        while (true) {
            times++;
            int previous = 0;
            // 0位置表示1个棋子。
            for (int i = 0; i < kChess; i++) {
                // 数组是引用类型，需要用变量接收
                int tmp = dp[i];
                // 依赖左和左上位置的格子
                dp[i] = dp[i] + previous + 1;
                previous = tmp;
                if (dp[i] >= nLevel) {
                    return times;
                }
            }
        }
    }

    /**
     * 最优解的常数优化。
     */
    public static int superEggDrop5(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }

        // 扔的次数最少的方法是2分，
        int bestTimes = log2N(nLevel) + 1;
        if (kChess >= bestTimes) {
            return bestTimes;
        }

        int[] dp = new int[kChess];
        int times = 0;
        while (true) {
            times++;
            int previous = 0;
            for (int i = 0; i < kChess; i++) {
                int tmp = dp[i];
                dp[i] = dp[i] + previous + 1;
                previous = tmp;
                if (dp[i] >= nLevel) {
                    return times;
                }
            }
        }
    }

    /**
     * 以2为底，n的对数
     */
    private static int log2N(int n) {
        int res = -1;
        while (n != 0) {
            res++;
            n >>>= 1;
        }
        return res;
    }

    public static void main(String[] args) {
        int maxN = 100;
        int maxK = 30;
        int testTimes = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int nLevel = (int) (Math.random() * maxN) + 1;
            int kChess = (int) (Math.random() * maxK) + 1;
            int ans1 = superEggDrop1(nLevel, kChess);
            int ans2 = superEggDrop2(nLevel, kChess);
            int ans3 = superEggDrop3(nLevel, kChess);
            int ans4 = superEggDrop4(nLevel, kChess);
            int ans5 = superEggDrop5(nLevel, kChess);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4 || ans4 != ans5) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
