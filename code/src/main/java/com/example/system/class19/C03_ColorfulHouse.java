/**
 * 范围内房子染色的种类
 */
package com.example.system.class19;

public class C03_ColorfulHouse {
    private static class SegmentTree {
        // 用位信息表示某一种颜色，
        // 56种颜色用64位的long类型标识
        long[] color;
        long[] update;

        public SegmentTree(int size) {
            int N = size + 1;
            color = new long[N << 2];
            update = new long[N << 2];
        }

        public void update(int L, int R, long V, int l, int r, int rt) {
            if (L <= l && R >= r) {
                color[rt] = V;
                update[rt] = V;
                return;
            }

            pushDown(rt);

            int mid = (l + r) >> 1;
            if (L <= mid) {
                update(L, R, V, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, V, mid + 1, r, rt << 1 | 1);
            }

            pushUp(rt);
        }

        private void pushUp(int rt) {
            color[rt] = color[rt << 1] | color[rt << 1 | 1];
        }

        /**
         * 将之前满足条件的范围，分发给左右两侧，都执行更新。
         * 就是上次rt代表的范围是满足条件的，所以左右两侧的房子都刷成同样的颜色
         */
        private void pushDown(int rt) {
            if (update[rt] > 0) {
                update[rt << 1] = update[rt];
                update[rt << 1 | 1] = update[rt];

                // 染色后左右两侧的颜色种类更新
                color[rt << 1] = update[rt];
                color[rt << 1 | 1] = update[rt];

                update[rt] = 0;
            }
        }

        public long query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return color[rt];
            }

            pushDown(rt);

            int mid = (l + r) >> 1;
            long left = 0;
            long right = 0;
            if (L <= mid) {
                left = query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                right = query(L, R, mid + 1, r, rt);
            }
            return left | right;
        }
    }

    /**
     * 获取颜色种类
     */
    private static int getCount(long num) {
        int count = 0;
        while (num != 0) {
            if ((num & 1) != 0) {
                count++;
            }
            num >>= 1;
        }
        return count;
    }

    public static void main(String[] args) {
        SegmentTree tree = new SegmentTree(10);
        tree.update(2, 3, 1, 1, 10, 1);
        tree.update(3, 4, 1 << 1, 1, 10, 1);
        tree.update(4, 5, 1 << 3, 1, 10, 1);
        
        tree.update(2, 5, 1 << 6, 1, 10, 1);
        long num = tree.query(2, 5, 1, 10, 1);
        System.out.println(getCount(num));
    }

}
