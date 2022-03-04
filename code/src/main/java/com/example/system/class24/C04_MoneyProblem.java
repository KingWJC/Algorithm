/**
 * 打怪兽需要花的最小钱数
 */
package com.example.system.class24;

public class C04_MoneyProblem {
    /**
     * 暴力递归尝试
     * 
     * @param power 怪兽的能力
     * @param money 雇佣怪兽的费用
     * @return 需要花的最少钱数
     */
    public static long minMoney1(int[] power, int[] money) {
        return process1(power, money, 0, 0);
    }

    /**
     * 目前，你的能力是ability，你来到了index号怪兽的面前，如果要通过后续所有的怪兽，
     * 请返回需要花的最少钱数
     */
    private static long process1(int[] power, int[] money, int ability, int index) {
        if (index == power.length) {
            return 0;
        }

        long minValue = 0;
        // process1调用时，index++和index+1有区别，index++会改变当前的index的值。
        if (ability < power[index]) {
            minValue = money[index] + process1(power, money, ability + power[index], index + 1);
        } else {// ability >= power[index] 可以雇佣，也可以不雇佣
            long hire = money[index] + process1(power, money, ability + power[index], index + 1);
            long noHire = process1(power, money, ability, index + 1);
            minValue = Math.min(hire, noHire);
        }
        return minValue;
    }

    public static void main(String[] args) {
        int[] power = { 5, 2, 7 };
        int[] money = { 3, 1, 10 };
        System.out.println(minMoney1(power, money));
    }
}
