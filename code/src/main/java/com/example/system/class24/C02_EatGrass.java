/**
 * 牛羊吃N份青草谁会赢
 */
package com.example.system.class24;

public class C02_EatGrass {
    /**
     * 暴力尝试
     * 
     * @param n N份草
     * @return 先手赢，返回先手；后手赢，返回后手
     */
    public static String whoWins1(int n) {
        if (n < 5) {
            // 谁最先把草吃完，谁获胜
            return n == 0 || n == 2 ? "后手" : "先手";
        }

        int want = 1;
        while (want <= n) {
            // 进到这个过程里来，当前的先手，先选
            if (whoWins1(n - want).equals("后手")) {
                return "先手";
            }

            if (want < n / 4) {
                want *= 4;
            } else {
                break;
            }
        }

        // 先手不能赢，则后手赢
        return "后手";
    }

    /**
     * 规律：每5个一组，组内0和2的索引位置的结果为 后手。
     */
    public static String whoWins2(int n) {
        if (n % 5 == 0 || n % 5 == 2) {
            return "后手";
        } else {
            return "先手";
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.println(i + ":" + whoWins1(i) + " " + whoWins2(i));
        }
    }
}
