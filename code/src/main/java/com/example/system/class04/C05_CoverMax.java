/**
 * 最大线段重合问题
 */
package com.example.system.class04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class C05_CoverMax {
    /**
     * 暴力循环
     * 时间复杂度: O((max-min)*N)
     */
    public static int maxCovertLoop(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }

        int convers = 0;
        for (double index = min + 0.5; index < max; index++) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < index && lines[i][1] > index)
                    cur++;
            }
            convers = Math.max(convers, cur);
        }

        return convers;
    }

    public static class Line {
        public int start;
        public int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static class StartComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    /**
     * 小根堆中每个线段的结尾都最多加入和弹出一次, N个数, 最多需要2N次.
     * 而小根堆每次调整的代价是logN
     * 时间复杂度: O(N*logN)
     * 最差情况: 1-99w, 2-98w :小根堆不弹出, O(N*logN)
     */
    public static int maxConvertHeap(int[][] arr) {
        Line[] lines = new Line[arr.length];
        for (int i = 0; i < arr.length; i++) {
            lines[i] = new Line(arr[i][0], arr[i][1]);
        }
        // 线段数组从小到大排序
        Arrays.sort(lines, new StartComparator());
        // 小根堆，每一条线段的结尾数值，使用默认的
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            // // lines[i] - cur 在黑盒中，把<=cur.start 东西都弹出
            while (!heap.isEmpty() && heap.peek() <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i].end);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    /**
     * 生成线段数组
     * 
     * @param N 数量
     * @param L 左边界
     * @param R 右边界
     * @return
     */
    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("start test");
        int testTimes = 100000;
        int N = 100;
        int L = 0;
        int R = 100;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = maxConvertHeap(lines);
            int ans2 = maxCovertLoop(lines);
            if (ans1 != ans2) {
                System.out.print("error");
                break;
            }
        }
        System.out.println("finished");
    }
}
