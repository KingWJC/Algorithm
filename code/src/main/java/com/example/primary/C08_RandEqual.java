package com.example.primary;

/**
 * 概率生成数字问题, 使用等概率函数f(x), (1-5的随机), 返回1-7等概率
 * 任意两个数之间的数, 等概率返回. a~b随机到c~d的随机.
 * 如 17-56 -》 (0-39)+17 -》 6位二进制数(0-63)
 */
public class C08_RandEqual {
    /**
     * 等概率返回 1-5的随机数.
     * 
     * @return
     */
    private static int randomFixed() {
        return (int) (Math.random() * 5) + 1;
    }

    /**
     * 随机机制只能是randomFixed方法.等概率返回0和1.(0,1发生器)
     * randomFixed方法中,1-5的随机数,每个数的概率是20%.
     * 
     * @return
     */
    private static int random01() {
        int ans = 0;
        do {
            ans = randomFixed();
        } while (ans == 3); // 将为3的概率平分给其它数.

        return ans < 3 ? 0 : 1;
    }

    /**
     * 等概率得到000~111的二进制数, 十进制为0-7.
     * 
     * @return
     */
    private static int random07() {
        // 位运算符优先级低.需要括号.
        return (random01() << 2) + (random01() << 1) + random01();
    }

    /**
     * 等概率得到0-6之间的数
     * 
     * @return
     */
    private static int random06() {
        int ans = 0;
        do {
            ans = random07();
        } while (ans == 7);
        return ans;
    }

    public static void main(String[] args) {
        int testTimes = 10000000;
        int count = 0;
        for (int i = 0; i < testTimes; i++) {
            if (random01() == 0) {
                count++;
            }
        }
        System.out.println("01发生器的概率测试: " + (double) count / (double) testTimes);

        int[] countArr = new int[8];
        for (int i = 0; i < testTimes; i++) {
            // 等概率得到1-7之间的数
            countArr[random06() + 1]++;
        }
        for (int i = 0; i < countArr.length; i++) {
            System.out.println(i + "这个数出现了" + countArr[i] + "次");
        }
    }
}
