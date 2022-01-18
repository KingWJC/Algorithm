/**
 * 打印n层汉诺塔从最左边移动到最右边的全部过程
 * 三种方法。
 */
package com.example.system.class11;

import java.util.Stack;

public class C01_Hanoi {
    /**
     * 递归依赖
     */
    public static void hanoi1(int n) {
        if (n > 0)
            leftToRight(n);
    }

    private static void leftToRight(int n) {
        // base case：只有一个时，直接move
        if (n == 1) {
            System.out.println("Move 1 from left to right");
            return;
        }

        // 把1~N-1层圆盘 从左 -> 中
        leftToMiddle(n - 1);
        // 左只剩第n个盘子，直接move到右。
        System.out.println("Move " + n + " from left to right");
        // 把1~N-1层圆盘 从中 -> 右
        middleToRight(n - 1);
    }

    private static void leftToMiddle(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to middle");
            return;
        }

        leftToRight(n - 1);
        System.out.println("Move " + n + " from left to middle");
        rightToMiddle(n - 1);
    }

    private static void middleToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from middle to right");
            return;
        }

        middleToLeft(n - 1);
        System.out.println("Move " + n + " from middle to right");
        leftToRight(n - 1);
    }

    private static void rightToMiddle(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to middle");
            return;
        }

        rightToLeft(n - 1);
        System.out.println("Move " + n + " from right to middle");
        leftToMiddle(n - 1);
    }

    private static void middleToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from middle to left");
            return;
        }

        middleToRight(n - 1);
        System.out.println("Move " + n + " from middle to left");
        rightToLeft(n - 1);
    }

    private static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to left");
            return;
        }

        rightToMiddle(n - 1);
        System.out.println("Move " + n + " from right to left");
        middleToLeft(n - 1);
    }

    /**
     * 优化递归
     */
    public static void hanoi2(int n) {
        if (n > 1) {
            process(n, "left", "right", "middle");
        }
    }

    private static void process(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("Move 1 from " + from + " to " + to);
            return;
        }

        process(n - 1, from, other, to);
        System.out.println("Move " + n + " from " + from + " to " + to);
        process(n - 1, other, to, from);
    }

    /**
     * 迭代
     */
    public static void hanoi3(int n) {
        if (n < 1)
            return;

        Stack<Record> stack = new Stack<>();
        stack.push(new Record(false, n, "left", "right", "middle"));
        while (!stack.isEmpty()) {
            Record cur = stack.pop();
            if (cur.index == 1) {
                System.out.println("Move 1 from " + cur.from + " to " + cur.to);
                if (!stack.isEmpty()) {
                    stack.peek().finished = true;
                }
            } else {
                if (!cur.finished) {
                    stack.push(cur);
                    stack.push(new Record(false, cur.index - 1, cur.from, cur.other, cur.to));
                } else {
                    System.out.println("Move " + cur.index + " from " + cur.from + " to " + cur.to);
                    stack.push(new Record(false, cur.index - 1, cur.other, cur.to, cur.from));
                }
            }
        }
    }

    public static class Record {
        int index;
        String from;
        String to;
        String other;
        boolean finished;

        public Record(boolean finished, int index, String from, String to, String other) {
            this.finished = finished;
            this.index = index;
            this.from = from;
            this.to = to;
            this.other = other;
        }
    }

    public static void main(String[] args) {
        int n = 3;
		hanoi1(n);
		System.out.println("============");
		hanoi2(n);
		System.out.println("============");
		hanoi3(n);
    }
}
