/**
 * N个皇后问题：时间复杂度O(N^N)
 */
package com.example.system.class11;

public class C24_NQueues {
    /**
     * 在N*N的棋盘上要摆N个皇后，
     * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上 给定一个整数n，
     * 返回n皇后的摆法有多少种。
     */
    public static int getTotalNum(int n) {
        if (n < 1) {
            return 0;
        }

        return process(new int[n], 0, n);
    }

    /**
     * 
     * @param record record[x] = y 之前的第x行的皇后，放在了y列上
     * @param index  当前来到i行，一共是0~N-1行
     * @param n      总共N行N列
     * @return 不关心i之前发生了什么，i.... 后续有多少合法的方法数
     */
    private static int process(int[] record, int index, int n) {
        if (n == index) {
            return 1;
        }

        int ways = 0;
        // 不同行，符合行限制。
        // 在index行上放皇后，所有列都尝试
        for (int j = 0; j < n; j++) {
            if (isValid(record, index, j)) {
                record[index] = j;
                ways += process(record, index + 1, n);
            }
        }
        return ways;
    }

    /**
     * 皇后摆放是否符合要求，
     */
    private static boolean isValid(int[] record, int i, int j) {
        boolean ans = true;
        // i之前，表示已摆放皇后的行数。而且记录record的值默认都为0.
        for (int r = 0; r < i; r++) {
            // 列限制，和左下，右下的斜线限制
            if (j == record[r] || Math.abs(r - i) == Math.abs(j - record[r])) {
                ans = false;
                break;
            }
        }
        return ans;
    }

    /**
     * 常数时间的优化,
     * 使用整数代替轨迹数组，使用位运算判断状态。
     * 只适用于n<=32的情况，因为若大于会超出整数的表示范围
     */
    public static int getTotalNum1(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }

        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process1(limit, 0, 0, 0);
    }

    /**
     * 
     * @param limit        若为1，记录多少个皇后需要摆放
     * @param colLim       列限制
     * @param leftdownLim  左下限制
     * @param rightdownLim 右下限制
     * @return
     */
    private static int process1(int limit, int colLim, int leftdownLim, int rightdownLim) {
        // 每一列都放置了符合要求的皇后
        if (limit == colLim) {
            return 1;
        }

        int ways = 0;
        int pos = limit & (~(colLim | leftdownLim | rightdownLim));
        int mostRightOne = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            ways += process1(limit, colLim | mostRightOne, (leftdownLim | mostRightOne) << 1,
                    (rightdownLim | mostRightOne) >> 1);
        }
        return ways;
    }

    public static void main(String[] args) {
        int n = 15;
        long start = System.currentTimeMillis();
        System.out.println("暴力递归：" + getTotalNum(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println("位运算优化：" + getTotalNum1(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");
    }
}
