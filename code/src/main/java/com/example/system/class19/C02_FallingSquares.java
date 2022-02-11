/**
 * 方块掉落之后的最大高度
 * 测试：https://leetcode.com/problems/falling-squares/
 */
package com.example.system.class19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class C02_FallingSquares {
    /**
     * 更新和查询的线段树，最大值
     */
    private static class SegmentTree {
        int[] max;
        int[] change;
        boolean[] update;

        /**
         * 不用保留原数组，直接计算最大值的线段树
         */
        public SegmentTree(int size) {
            int N = size + 1;
            max = new int[N << 2];
            update = new boolean[N << 2];
            change = new int[N << 2];
        }

        public void update(int L, int R, int V, int l, int r, int rt) {
            if (L <= r && R >= r) {
                change[rt] = V;
                update[rt] = true;
                max[rt] = V;
                return;
            }

            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                update(L, R, V, l, mid, rt << 1);
            }

            if (R > mid) {
                update(L, R, V, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        private void pushDown(int rt, int ln, int rn) {
            if (update[rt]) {
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];

                update[rt << 1] = true;
                update[rt << 1 | 1] = true;

                max[rt << 1] = change[rt];
                max[rt << 1 | 1] = change[rt];

                update[rt] = false;
            }
        }

        private void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

        private int query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return max[rt];
            }

            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);

            int left = 0;
            int right = 0;
            if (L <= mid) {
                left = query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                right = query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return Math.max(left, right);
        }
    }

    /**
     * 给所有下落方块的边界，标记索引位置，从1开始
     * [a,b] ->代表一个边长为b的正方形积木，积木左边缘沿着X=a这条线从上方掉落
     */
    private static HashMap<Integer, Integer> getIndex(int[][] positions) {
        // 落在X轴上的积木边界位置
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] arr : positions) {
            pos.add(arr[0]);
            // 减一是防止积木贴边
            pos.add(arr[1] + arr[0] - 1);
        }
        int count = 1;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer index : pos) {
            map.put(index, count++);
        }
        return map;
    }

    /**
     * 获取每一次积木掉落后的最大高度
     */
    public static List<Integer> fallingSquares(int[][] positions) {
        HashMap<Integer, Integer> map = getIndex(positions);
        int N = map.size();
        SegmentTree tree = new SegmentTree(N);
        int max = 0;
        List<Integer> ans = new ArrayList<>();
        for (int[] arr : positions) {
            // arr[1] 表示边长
            int L = map.get(arr[0]);
            int R = map.get(arr[0] + arr[1] - 1);
            int height = tree.query(L, R, 1, N, 1) + arr[1];
            max = Math.max(max, height);
            ans.add(max);
            tree.update(L, R, height, 1, N, 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        /**
         * 注意：
         * 1 <= positions.length <= 1000.
         * 1 <= positions[i][0] <= 10^8.
         * 1 <= positions[i][1] <= 10^6.
         */
        // int[][] positions = { { 1, 2 }, { 2, 3 }, { 6, 1 } };
        int[][] positions = { { 100, 100 }, { 200, 100 } };
        System.out.println(Arrays.toString(fallingSquares(positions).toArray()));
    }
}
