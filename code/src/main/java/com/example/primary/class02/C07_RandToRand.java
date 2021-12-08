package com.example.primary.class02;

/**
 * 等概率. Java Math.random 随机函数.
 */
public class C07_RandToRand {
    public static void main(String[] args) {
        int testTimes = 10000000;

        randomTest(testTimes);

        randX(10, testTimes);

        randXPower2(testTimes, 0.7);
    }

    /**
     * Math.random 返回值[0,1), 等概率.
     * 
     * @param testTimes
     */
    private static void randomTest(int testTimes) {
        int count = 0;
        for (int i = 0; i < testTimes; i++) {
            // [0,1) -> [0,8) 等概率, 10段变为段.
            if (Math.random() * 8 < 0.5 * 8) {
                count++;
            }
        }
        System.out.println((double) count / (double) testTimes);
        System.out.println((double) 4 / (double) 8);
    }

    /**
     * 等概率公式
     */
    private static void randX(int x, int testTimes) {
        int[] counts = new int[x];
        for (int i = 0; i < testTimes; i++) {
            int ans = (int) (Math.random() * x);
            counts[ans]++;
        }

        for (int i : counts) {
            System.out.println("这个数出现了" + i + "次");
        }
    }

    /**
     * 任意的x, x属于[0,1),[0,x)范围上的数出现概率从 x 改为 x平方.
     * 平方的概率会出现4种情况.
     * @param times
     * @param x
     */
    private static void randXPower2(int times, double x) {
        int count = 0;
        for (int i = 0; i < times; i++) {
            // 两次都命中[0,x)范围上的数
            if (Math.max(Math.random(), Math.random()) < x) {
                count++;
            }
        }
        System.out.println((double) count / (double) times);
        System.out.println(Math.pow(x, 2));

        count = 0;
        for (int i = 0; i < times; i++) {
            // 有一次命中[0,x)范围上的数的概率.
            if (Math.min(Math.random(), Math.random()) < x) {
                count++;
            }
        }
        System.out.println((double) count / (double) times);
        System.out.println(1 - Math.pow((1-x), 2));
    }


}
