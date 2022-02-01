/**
 * 给定一个正整数n, 求裂开的方法数
 */
package com.example.system.class11;

public class C21_SplitNumber {
    /**
     * 递归尝试
     */
    public static int ways(int n) {
        if (n <= 0) {
            return 0;
        }

        // 因为规定：后面的数不能比前面的数小，所以先拆的数为1
        return process(1, n);
    }

    /**
     * 自我思考: 尝试选择要拆额值的个数，从0开始。
     * 
     * @param cur  要拆的值
     * @param rest 还剩下要拆的数
     * @return 拆解的方法数
     */
    private static int process(int cur, int rest) {
        if (rest == 0) {
            return 1;
        }

        int ways = 0;
        // 当前要拆的值1，要拆出几个来，从0....rest范围内的尝试
        for (int i = 0; cur <= rest && i * cur <= rest; i++) {
            ways += process(cur + 1, rest - cur * i);
        }
        return ways;
    }

    public static int ways1(int n) {
        if (n <= 0) {
            return 0;
        }
        return process2(1, n);
    }

    /**
     * 样本对应的尝试模型的思考方式
     * 两个样本的结尾，它两结合的可能性,共3种。
     * 
     * @param pre  上一个拆出的数（推出一个前置条件）
     * @param rest 还剩下要拆的数
     * @return 拆解的方法数
     */
    private static int process1(int pre, int rest) {
        if (pre > rest) {
            return 0;
        }
        if (pre == rest) { // 不包含 rest = 0 的情况
            // 2, 5 => (2,3),(3,2),(4,1),(5,0)
            // 3, 6 => (3,3),(4,2),(5,1),(6,0)
            return 1;
        }

        int ways = 0;
        // 以1为第一个拆分值的拆解方法数
        // 以2为第一个拆分值的拆解方法数
        for (int first = pre; first < rest; first++) {
            ways += process1(first, rest - first);
        }
        ways++; // 补上 first = rest 的情况，也就是rest=0的情况。
        return ways;
    }

    /**
     * 课堂最终版本
     */
    private static int process2(int pre, int rest) {
        if (rest == 0) {
            return 1;
        }
        // 作第二个剪枝的条件，当rest==0时，pre肯定大于rest
        if (pre > rest) {
            return 0;
        }

        int ways = 0;
        for (int first = pre; first <= rest; first++) {
            ways += process2(first, rest - first);
        }
        return ways;
    }

    /**
     * 动态规划：尝试当前拆分值，使用了多少个。
     */
    public static int useCustomDP(int n) {
        if (n < 1) {
            return 0;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int cur = 1; cur <= n; cur++) {
            dp[cur][0] = 1;
            dp[cur][cur] = 1; // 观察得出对角线的值为1.
        }
        for (int cur = n - 1; cur > 0; cur--) {
            for (int rest = cur + 1; rest <= n; rest++) {
                int ways = 0;
                for (int i = 0; i * cur <= rest; i++) {
                    ways += dp[cur + 1][rest - cur * i];
                }
                dp[cur][rest] = ways;
            }
        }
        return dp[1][n];
    }

    /**
     * 动态规划：前一个拆分值为[1....
     * 初始化表结构后，从下而上，从左往右填充
     */
    public static int useDP(int n) {
        if (n <= 0) {
            return 0;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }

        for (int pre = n - 1; pre > 0; pre--) {
            // pre==rest的单元格已赋值。
            for (int rest = pre + 1; rest <= n; rest++) {
                int ways = 0;
                for (int first = pre; first <= rest; first++) {
                    ways += dp[first][rest - first];
                }
                dp[pre][rest] = ways;
            }
        }
        return dp[1][n];
    }

    /**
     * 动态规划：斜率优化
     */
    public static int useDP1(int n) {
        if (n < 1) {
            return 0;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int cur = 1; cur <= n; cur++) {
            dp[cur][0] = 1;
            dp[cur][cur] = 1; // 观察得出对角线的值为1.
        }
        for (int cur = n - 1; cur > 0; cur--) {
            for (int rest = cur + 1; rest <= n; rest++) {
                dp[cur][rest] = dp[cur + 1][rest];
                dp[cur][rest] += dp[cur][rest - cur];
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        int test = 39;
        System.out.println(ways(test));
        System.out.println(ways1(test));
        System.out.println(useCustomDP(test));
        System.out.println(useDP(test));
        System.out.println(useDP1(test));
    }
}
