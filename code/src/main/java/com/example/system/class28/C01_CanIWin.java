/**
 * 我能赢吗:
 * 一组整数，A和B轮流选，当选择的数的累加和超过指定值100时，当前选择的人赢。
 * 测试链接：https://leetcode-cn.com/problems/can-i-win
 */
package com.example.system.class28;

public class C01_CanIWin {
    /**
     * 暴力递归
     * 
     * @param choose 1~choose之间的整数
     * @param total  指定的累加和
     * @return 先手会不会赢
     */
    public static boolean canIWin1(int choose, int total) {
        // 谁也没选的时候，先手选择任一个数，肯定能大于0，所以先手赢
        if (total == 0) {
            return true;
        }
        // 高斯公式，等差数列累加和，比指定累计和小，则说明先手不可能赢
        if (((choose * (choose + 1)) >> 1) < total) {
            return false;
        }

        int[] arr = new int[choose];
        for (int i = 0; i < choose; i++) {
            arr[i] = i + 1;
        }

        return process1(arr, total);
    }

    /**
     * arr整数选择池，若值为-1,表示数字被拿走。
     * rest表示剩余的值。
     */
    private static boolean process1(int[] arr, int rest) {
        // 上一轮选择后，剩余值小于等于0，则本轮先手输。
        if (rest <= 0) {
            return false;
        }
        // 先手去尝试所有的情况
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {
                int cur = arr[i];
                arr[i] = -1;
                // 下一轮的后手就是本轮的先手
                boolean next = process1(arr, rest - cur);
                // 深度遍历，要恢复现场，之前不能return或break。
                arr[i] = cur;
                if (!next) {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * 状态压缩
     */
    public static boolean canIWin2(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }

        return process2(choose, 0, total);
    }

    /**
     * status 用整型的位信息存储数字的状态
     * i位如果为0，代表没拿，当前可以拿
     * i位为1，代表已经拿过了，当前不能拿
     */
    private static boolean process2(int choose, int state, int rest) {
        if (rest <= 0) {
            return false;
        }
        // 拿1~choose中的任何一个数字
        for (int i = 1; i <= choose; i++) {
            // 判断i位置的位信息是否位0.
            if ((state & (1 << i)) == 0) {
                if (!process2(choose, state | (1 << i), rest - i)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 记忆化搜索，有重复值。
     */
    public static boolean canIWin3(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        // choose有多大，就需要多少位表示每个数的状态
        // 只需要一维表，因为rest可由state推出，同一个state，其rest一定相同。
        // 加1，让dp的索引就是选择的整数
        int[] dp = new int[1 << (choose + 1)];
        return process3(choose, 0, total, dp);
    }

    /**
     * dp缓存表，0表示没有计算，1表示先手赢，-1表示先手输
     */
    private static boolean process3(int choose, int state, int rest, int[] dp) {
        if (dp[state] != 0) {
            return dp[state] == 1;
        }

        boolean ans = false;
        if (rest > 0) {// 别忘了加base条件
            for (int i = 1; i <= choose; i++) {
                if ((state & (1 << i)) == 0) {
                    if (!process3(choose, state | (1 << i), rest - i, dp)) {
                        ans = true;
                        break;
                    }
                }
            }
        }
        dp[state] = ans ? 1 : -1;
        return ans;
    }

    public static void main(String[] args) {
        int choose = 10;
        int total = 11;
        System.out.println(canIWin1(choose, total));
        System.out.println(canIWin2(choose, total));
        System.out.println(canIWin3(choose, total));
    }
}
