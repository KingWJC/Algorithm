package com.example.primary.class02;
/**
 * 01不等概率随机, 到01等概率随机
 */
public class C09_RandNoEqual {
    /**
     * 不等概率返回 0-1的随机数, 但概率固定.
     * 
     * @return
     */
    private static int randomNoEqual() {
        return Math.random() < 0.84 ? 0 : 1;
    }

    /**
     * 等概率返回 0-1的随机数
     * (0,1),(1,0)
     * @return
     */
    private static int random01() {
        // int a = 0;
        // int b = 0;
        // do {
        //     a = randomNoEqual();
        //     b = randomNoEqual();
        // } while (a == b);
        // return a < b ? 0 : 1;

        int ans = 0;
        do {
            ans = randomNoEqual();
        } while (ans == randomNoEqual());
        return ans;
    }

    public static void main(String[] args) {
        int[] countArr = new int[2];
        for (int i = 0; i < 10000000; i++) {
            countArr[random01()]++;
        }
        for (int i = 0; i < countArr.length; i++) {
            System.out.println(i + "这个数出现了" + countArr[i] + "次");
        }
    }
}
