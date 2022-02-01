/**
 * 机器人, N位置: 1~5,  M起始位置: 2，P目标位置: 4，K步数: 6  求方法数。
 * 有哪些位置？1~N, 机器人从M出发，走过K步之后，最终停在P的方法数，是多少
 */
package com.example.system.class11;

public class C05_RobotWalk {
    /**
     * 暴力递归的尝试
     */
    public static int ways1(int N, int P, int M, int K) {
        if (N < 2 || P > N || P < 1 || M > N || M < 1 || K < 1)
            return -1;

        return process1(M, K, N, P);
    }

    private static int process1(int M, int K, int N, int P) {
        if (K == 0) {
            return M == P ? 1 : 0;
        }

        if (M == 1) {// 1 -> 2
            return process1(M + 1, K - 1, N, P);
        }

        if (M == N) { // N-1 <- N
            return process1(M - 1, K - 1, N, P);
        }

        return process1(M - 1, K - 1, N, P) + process1(M + 1, K - 1, N, P);
    }

    /**
     * 暴力递归的优化 - 记忆化搜索
     */
    public static int ways2(int N, int P, int M, int K) {
        if (N < 2 || P < 1 || P > N || M < 1 || M > N || K < 1)
            return -1;

        // cache就是缓存表
        // cache[M][K] == -1 -> process2(M, K)之前没算过！
        // cache[M][K] != -1 -> process2(M, K)之前算过！返回值，cache[M][K]
        // N+1 * K+1
        int[][] cache = new int[N + 1][K + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                cache[i][j] = -1;
            }
        }
        return process2(M, K, N, P, cache);
    }

    /**
     * M 范围：1~N
     * K 范围：0~K
     */
    private static int process2(int M, int K, int N, int P, int[][] cache) {
        if (cache[M][K] != -1) {
            return cache[M][K];
        }

        int ans = 0;
        if (K == 0) {
            ans = M == P ? 1 : 0;
        } else if (M == 1) {
            ans = process2(M + 1, K - 1, N, P, cache);
        } else if (M == N) {
            ans = process2(M - 1, K - 1, N, P, cache);
        } else {
            ans = process2(M - 1, K - 1, N, P, cache) + process2(M + 1, K - 1, N, P, cache);
        }

        cache[M][K] = ans;
        return ans;
    }

    /**
     * 创建动态二维规划表
     * 填充单元：列
     */
    public static int ways3(int N, int P, int M, int K) {
        if (N < 2 || M < 1 || M > N || P < 1 || P > N || K < 1)
            return -1;

        // 使用P，构建动态规划二维状态转移表
        int[][] dp = new int[N + 1][K + 1];
        dp[P][0] = 1; // 第一列
        for (int j = 1; j <= K; j++) {
            dp[1][j] = dp[2][j - 1]; // 第一行
            for (int i = 2; i < N; i++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i + 1][j - 1];
            }
            dp[N][j] = dp[N - 1][j - 1]; // 最后一行
        }
        // 输入当前位置和步数。
        return dp[M][K];
    }

    public static void main(String[] args) {
        System.out.println(ways1(5, 4, 2, 6));
        System.out.println(ways2(5, 4, 2, 6));
        System.out.println(ways3(5, 4, 2, 6));
    }
}
