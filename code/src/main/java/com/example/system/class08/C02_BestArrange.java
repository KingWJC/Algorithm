/**
 * 贪心算法:2. 会议室能容纳最多的宣讲场次
 */
package com.example.system.class08;

import java.util.Arrays;
import java.util.Comparator;

public class C02_BestArrange {
    private static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" + start + "," + end + "]";
        }
    }

    /**
     * 贪心策略：总是先选择结束时间最小项目。
     */
    public static int bestArrange(Program[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Arrays.sort(arr, new MyComparator());

        // 会议的开始时间和结束时间，都是数值，不会 < 0
        int max = 0;
        int timeLine = 0;
        // 依次遍历每一个会议，结束时间早的会议先遍历
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].start >= timeLine) {
                max++;
                timeLine = arr[i].end;
            }
        }
        return max;
    }

    private static class MyComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    /**
     * 暴力！所有情况都尝试！
     */
    public static int bestArrangeTest(Program[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        return process(arr, 0, 0);
    }

    /**
     * 先序遍历
     * 目前来到timeLine的时间点，已经安排了done多的会议，剩下的会议programs可以自由安排
     * 返回能安排的最多会议数量
     */
    private static int process(Program[] arr, int done, int timeLine) {
        if (arr.length == 0)
            return done;

        int max = done;
        // 当前安排的会议是什么会，每一个都枚举
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].start >= timeLine) {
                Program[] next = copyButExcept(arr, i);
                max = Math.max(max, process(next, done + 1, arr[i].end));
            }
        }
        return max;
    }

    private static Program[] copyButExcept(Program[] arr, int index) {
        Program[] copy = new Program[arr.length - 1];
        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != index) {
                copy[j++] = arr[i];
            }
        }
        return copy;
    }

    public static void main(String[] args) {
        int maxSize = 10;
        int maxTime = 100;
        int testTimes = 10000000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            Program[] arr = generatePrograms(maxSize, maxTime);
            int max1 = bestArrange(arr);
            int max2 = bestArrangeTest(arr);
            if (max1 != max2) {
                System.out.println(max1 + " error " + max2);
                System.out.println(Arrays.toString(arr));
                break;
            }
        }
        System.out.println("finished");
    }

    private static Program[] generatePrograms(int maxSize, int maxTime) {
        int size = (int) (Math.random() * maxSize) + 1;
        Program[] ans = new Program[size];
        for (int i = 0; i < ans.length; i++) {
            int start = (int) (Math.random() * maxTime) + 1;
            int end = (int) (Math.random() * maxTime) + 1;
            if (start == end) {
                ans[i] = new Program(start, start + 1);
            } else {
                ans[i] = new Program(Math.min(start, end), Math.max(start, end));
            }
        }
        return ans;
    }
}
