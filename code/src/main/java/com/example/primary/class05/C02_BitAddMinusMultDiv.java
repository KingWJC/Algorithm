/**
 * 用位运算实现整数的+ - * /加减乘除
 */
package com.example.primary.class05;

public class C02_BitAddMinusMultDiv {
    /**
     * 加法
     */
    public static int add(int a, int b) {
        int sum = a;
        // 循环,等进位信息消失, 无进位相加就是最后结果.
        while (b != 0) {
            sum = a ^ b; // 无进位相加信息, 差异(1,0为1, 其它为0)
            b = (a & b) << 1; // 进位信息,同为1的位，要进位.
            a = sum;
        }
        return sum;
    }

    /**
     * 负数(相反数, 求补码. 取反加1)
     * 
     * @param n
     * @return
     */
    public static int negativeNum(int n) {
        return add(~n, 1);
    }

    /**
     * 减法
     * 
     * @param a
     * @param b
     * @return
     */
    public static int minus(int a, int b) {
        return add(a, negativeNum(b));
    }

    /**
     * 二进制乘法: 支持负数(补码).
     * 
     * @param a
     * @param b
     * @return
     */
    public static int multi(int a, int b) {
        int res = 0;
        while (b != 0) {
            // 若b的最后一位是1, 则结果加1个a
            if ((b & 1) != 0) {
                res = add(res, a);
            }
            a <<= 1; // a 进1位.
            b >>>= 1; // 无符号右移. b退1位.
        }
        return res;
    }

    /**
     * 除法
     * b左移有可能占了符号位. 所以右移.
     * 除不尽, 向下取整.
     */
    public static int div(int a, int b) {
        int res = 0;
        int x = isNegative(a) ? negativeNum(a) : a;
        int y = isNegative(b) ? negativeNum(b) : b;
        // i=30的原因: 符号位31不算,
        for (int i = 30; i >= 0; i = minus(i, 1)) {
            if ((x >> i) >= y) {
                res |= (1 << i);
                x = minus(x, y << i);
            }
        }

        return isNegative(a) ^ isNegative(b) ? negativeNum(res) : res;
    }

    /**
     * 是否是负数
     * 
     * @param n
     * @return
     */
    private static boolean isNegative(int n) {
        return n < 0;
    }

    /**
     * 系统最小值无法转成绝对值.
     * 
     * @param a
     * @param b
     * @return
     */
    public static int divide(int a, int b) {
        if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE)
            return 1;
        else if (b == Integer.MIN_VALUE)
            return 0;
        else if (a == Integer.MIN_VALUE) {
            if (b == negativeNum(1)) {
                // 系统最小值除-1,有符号整数为Integer.MAX_VALUE+1,leetcode规定如下:
                return Integer.MAX_VALUE;
            } else {
                /**
                 * a/b = res
                 * (a + 1) / b = c a为最小值, 加1,绝对值变小,可以用除法div.
                 * a - (b * c) = d
                 * d / b = e; 补尝值
                 * res = c + e
                 * 如 -10/2(-10为最小值) -》 (-10+1)/2 = -4 -》 -10-(-4*2)=-2 -》 (补尝值)-2/2 + -4=-5.
                 */
                int c = div(add(a, 1), b);
                return add(c, div(minus(a, multi(c, b)), b));
            }
        } else {
            return div(a, b);
        }
    }

    public static void main(String[] args) {
        int a = -7;
        int b = -3;
        System.out.println("-7*-3 multi:" + multi(a, b));

        a = 9;
        b = 4;
        System.out.println("9/4 div:" + div(a, b));

        a = Integer.MIN_VALUE;
        System.out.println("Integer.MIN_VALUE:" + negativeNum(a));

        System.out.println("Integer.MIN_VALUE / 4:" + divide(a, b));
    }
}
