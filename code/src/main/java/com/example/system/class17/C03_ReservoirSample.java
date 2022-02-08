/**
 * 蓄水池算法：机器所有吐出的球都等概率放进袋子里
 */
package com.example.system.class17;

import java.util.Arrays;

public class C03_ReservoirSample {
    /**
     * 测试17个球是否等概率放入袋子中
     */
    private static void testProbability() {
        int testTimes = 10000000;
        int ballNum = 17;
        // 记录每个球被放入袋中的次数
        int[] count = new int[18];
        for (int i = 0; i < testTimes; i++) {
            int[] box = new int[10];
            int bagi = 0;
            // 从1号开始
            for (int j = 1; j <= ballNum; j++) {
                if (j <= 10) {
                    box[bagi++] = j;
                } else {
                    // [1-j]位置上等概率获取一个值
                    int num = (int) (Math.random() * j) + 1;
                    if (num <= 10) {
                        // [1-10]位置上选任一个数
                        bagi = (int) (Math.random() * 10);
                        box[bagi] = j;
                    }
                }
            }

            for (int n : box) {
                count[n]++;
            }
        }
        System.out.println(Arrays.toString(count));
    }

    /**
     * 等概率的随机盒子-抽象
     */
    public static class RandomBox {
        private int[] bag;
        private int N;
        // 编号，第几个进入盒子
        private int count;

        public RandomBox(int capacity) {
            N = capacity;
            bag = new int[N];
            count = 0;
        }

        // 返回[1-max]
        private int rand(int max) {
            return (int) (Math.random() * max) + 1;
        }

        public void add(int num) {
            count++;
            if (count <= N) {
                bag[count - 1] = num;
            } else {
                if (rand(count) <= N) {
                    bag[rand(N) - 1] = num;
                }
            }
        }

        public int[] choices() {
            return bag;
        }
    }

    public static void main(String[] args) {
        testProbability();

        int all = 100;
        int choose = 10;
        int testTimes = 1000000;
        int[] counts = new int[all + 1];
        for (int i = 0; i < testTimes; i++) {
            RandomBox box = new RandomBox(choose);
            for (int num = 1; num <= all; num++) {
                box.add(num);
            }

            int[] ans = box.choices();
            for (int j = 0; j < ans.length; j++) {
                counts[ans[j]]++;
            }
        }

        for (int i = 0; i < counts.length; i++) {
			System.out.println(i + " times : " + counts[i]);
		}
    }
}
