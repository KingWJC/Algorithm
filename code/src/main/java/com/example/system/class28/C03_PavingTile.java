/**
 * 铺瓷砖: 解决N*M铺地的问题
 */
package com.example.system.class28;

public class C03_PavingTile {
    /**
     * 暴力递归
     * 每个砖块有两种铺法：向上或向右
     */
    public static int ways1(int N, int M) {
        // 无法铺满的情况：放不下或面积为奇数。
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        // 只能横着放，或竖着放，所以只有一种方法。
        if (N == 1 || M == 1) {
            return 1;
        }

        int[] preStatus = new int[M];
        for (int i = 0; i < M; i++) {
            preStatus[i] = 1;
        }
        return process1(preStatus, 0, N);
    }

    /**
     * 在行上做递归（深度优先遍历）
     * rowindex-2及之上的行都铺满
     * 
     * @param preStatus rowIndex-1行的铺满状态
     * @param rowIndex  正在rowIndex行做决定
     * @param N         一共有多少行
     * @return 让所有区域都满，方法数返回
     */
    private static int process1(int[] preStatus, int rowIndex, int N) {
        // 已铺完N行，若最后一行没铺满，返回铺法0.
        if (rowIndex == N) {
            for (int status : preStatus) {
                if (status == 0) {
                    return 0;
                }
            }
            return 1;
        }

        // 获取当前行的可铺设位置的状态（上一行若为0，就只有竖着放一种方法）
        // status[i] == 1 只能竖着向上
        // status[i] == 0 可以考虑摆砖
        int[] status = new int[preStatus.length];
        for (int i = 0; i < status.length; i++) {
            // 异或：（不进位加法）为1时，返回0；为0时，返回1.
            status[i] = preStatus[i] ^ 1;
        }

        return dfs1(status, 0, rowIndex, N);
    }

    /**
     * 在列上做递归（深度优先遍历）
     * 
     * @param status   当前行的状态(只考虑status==0的情况)
     * @param colIndex 正在rowIndex列上做决定
     * @param rowIndex 正在rowIndex行做决定
     * @param N        一共有多少行
     * @return
     */
    private static int dfs1(int[] status, int colIndex, int rowIndex, int N) {
        // 当col来到终止列，当前行的列铺的情况已枚举完，进入下一行
        if (colIndex == status.length) {
            return process1(status, rowIndex + 1, N);
        }

        // col位置不横摆
        int ans = dfs1(status, colIndex + 1, rowIndex, N);
        // col位置横摆, 向右
        if (colIndex + 1 < status.length && status[colIndex] == 0 && status[colIndex + 1] == 0) {
            status[colIndex] = 1;
            status[colIndex + 1] = 1;
            ans += dfs1(status, colIndex + 2, rowIndex, N);
            status[colIndex] = 0;
            status[colIndex + 1] = 0;
        }
        return ans;
    }

    /**
     * 状态压缩
     */
    public static int ways2(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }

        return process2((1 << M) - 1, 0, N, M);
    }

    /**
     * 使用整型preStatus和列数M，来处理行状态。
     */
    private static int process2(int preStatus, int rowIndex, int N, int M) {
        if (rowIndex == N) {
            return preStatus == ((1 << M) - 1) ? 1 : 0;
        }

        int status = preStatus ^ ((1 << M) - 1);
        // int status = ((~preStatus) ^ ((1 << M) - 1));
        // colIndex位信息要从左往右，所以是M-1,不是0.
        return dfs2(status, M - 1, rowIndex, N, M);
    }

    private static int dfs2(int status, int colIndex, int rowIndex, int N, int M) {
        if (colIndex == -1) {
            return process2(status, rowIndex + 1, N, M);
        }

        int ans = dfs2(status, colIndex - 1, rowIndex, N, M);
        // 下一列还在区域内。
        if (colIndex - 1 >= 0 && (status & (1 << colIndex)) == 0 && (status & (1 << (colIndex - 1))) == 0) {
            // 将colIndex和colIndex-1得位置更新为1.
            ans += dfs2(status | (3 << (colIndex - 1)), colIndex - 2, rowIndex, N, M);
        }
        return ans;
    }

    /**
     * 记忆化搜索：记录行递归的每个分支的计算结果
     * Min(N,M)不超过32位
     */
    public static int ways3(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) > 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }

        // 让值小的做列，值大的做行
        int max = Math.max(N, M);
        int min = Math.min(N, M);
        int[][] dp = new int[1 << min][max + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }

        return process3((1 << min) - 1, 0, max, min, dp);
    }

    private static int process3(int preStatus, int rowIndex, int N, int M, int[][] dp) {
        if (dp[preStatus][rowIndex] != -1) {
            return dp[preStatus][rowIndex];
        }

        int ans = 0;
        if (rowIndex == N) {
            ans = preStatus == ((1 << M) - 1) ? 1 : 0;
        } else {
            int status = ((~preStatus) & ((1 << M) - 1));
            ans = dfs3(status, M - 1, rowIndex, N, M, dp);
        }
        dp[preStatus][rowIndex] = ans;
        return ans;
    }

    private static int dfs3(int status, int colIndex, int rowIndex, int N, int M, int[][] dp) {
        if (colIndex == -1) {
            return process3(status, rowIndex + 1, N, M, dp);
        }

        int ans = dfs3(status, colIndex - 1, rowIndex, N, M, dp);
        if (colIndex >= 1 && (status & (1 << colIndex)) == 0 && (status & (1 << (colIndex - 1))) == 0) {
            ans += dfs3(status | (3 << (colIndex - 1)), colIndex - 2, rowIndex, N, M, dp);
        }
        return ans;
    }

    /**
     * 严格表依赖的动态规划：从上往下推。
     */
    public static int ways4(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) > 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }

        int max = N > M ? N : M;
        int min = max == N ? M : N;

        int rowState = (1 << min);
        // 空间压缩：上一行的铺满的方法数和当前行的铺满的方法
        int[] dp = new int[rowState];
        int[] cur = new int[rowState];

        int limit = rowState - 1;
        dp[limit] = 1;

        for (int i = 0; i < max; i++) {
            for (int j = 0; j < rowState; j++) {
                if (dp[j] != 0) {
                    int status = (~j) & limit;
                    dfs4(dp[j], status, 0, min - 1, cur);
                }
            }
            for (int k = 0; k < rowState; k++) {
                dp[k] = 0;
            }

            int[] tmp = dp;
            dp = cur;
            cur = tmp;
        }
        return dp[limit];
    }

    /**
     * 列状态的遍历从右往左，所以每次往左移动1.
     */
    private static void dfs4(int ways, int status, int colIndex, int end, int[] cur) {
        if (colIndex == end) {
            cur[status] += ways;
        } else {
            dfs4(ways, status, colIndex + 1, end, cur);
            if (((3 << colIndex) & status) == 0) {
                dfs4(ways, status | (3 << colIndex), colIndex + 1, end, cur);
            }
        }
    }

    public static void main(String[] args) {
        int N = 8;
        int M = 6;
        System.out.println(ways1(N, M));
        System.out.println(ways2(N, M));
        System.out.println(ways3(N, M));
        System.out.println(ways4(N, M));
    }
}
