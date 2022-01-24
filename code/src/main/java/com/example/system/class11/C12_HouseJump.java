/**
 * 象棋问题-马只走K步跳到指定位置的方法数
 */
package com.example.system.class11;

public class C12_HouseJump {
    /**
     * 暴力尝试
     * 马”从(0,0)位置出发，必须走完k步，最后落在(a,b)上的方法数有多少种
     */
    public static int getJumpType(int a, int b, int k) {
        return process(k, 0, 0, a, b);
    }

    /**
     * 
     * @param rest 还剩下rest步需要跳
     * @param x    当前来到的位置的行 x
     * @param y    当前来到的位置的列 y
     * @param a    目标位置(a,b)
     * @param b    目标位置(a,b)
     * @return 跳完rest步，正好跳到a，b的方法数是多少
     */
    private static int process(int rest, int x, int y, int a, int b) {
        // 棋盘就是横坐标上9条线（列）、纵坐标上10条线的区域(行)
        if (x < 0 || x > 9 || y < 0 || y > 8)
            return 0;
        if (rest == 0) {
            return x == a && y == b ? 1 : 0;
        }

        int ways = process(rest - 1, x + 1, y + 2, a, b);
        ways += process(rest - 1, x + 2, y + 1, a, b);
        ways += process(rest - 1, x - 1, y + 2, a, b);
        ways += process(rest - 1, x - 2, y + 1, a, b);
        ways += process(rest - 1, x - 1, y - 2, a, b);
        ways += process(rest - 1, x - 2, y - 1, a, b);
        ways += process(rest - 1, x + 1, y - 2, a, b);
        ways += process(rest - 1, x + 2, y - 1, a, b);
        return ways;
    }

    /**
     * 动态规划：三维数组
     */
    public static int useDP(int a, int b, int k) {
        
        int[][][] dp = new int[10][9][k + 1];
        dp[a][b][0] = 1;
        for (int rest = 1; rest < k + 1; rest++) { // 按层来
            for (int x = 0; x < 10; x++) { // 行
                for (int y = 0; y < 9; y++) {  // 列
                    int ways = pick(dp, rest - 1, x + 1, y + 2);
                    ways += pick(dp, rest - 1, x + 2, y + 1);
                    ways += pick(dp, rest - 1, x - 1, y + 2);
                    ways += pick(dp, rest - 1, x - 2, y + 1);
                    ways += pick(dp, rest - 1, x - 1, y - 2);
                    ways += pick(dp, rest - 1, x - 2, y - 1);
                    ways += pick(dp, rest - 1, x + 1, y - 2);
                    ways += pick(dp, rest - 1, x + 2, y - 1);
                    dp[x][y][rest] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    // 如果(i，j)位置越界的话，返回0；
    private static int pick(int[][][] dp, int rest, int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][rest];
    }

    public static void main(String[] args) {
        int x = 1;
        int y = 2;
        int step = 3;
        System.out.println(getJumpType(x, y, step));
        System.out.println(useDP(x, y, step));
    }
}
