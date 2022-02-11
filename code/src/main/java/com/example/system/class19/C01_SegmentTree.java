/**
 * 线段树：支持范围整体修改和范围整体查询。
 */
package com.example.system.class19;

public class C01_SegmentTree {

    /**
     * 暴力实现，O(N)
     */
    public static class SegArray {
        private int[] arr;

        /**
         * 原始数组转换为从1开始存储的数组，0位置舍弃
         */
        public SegArray(int[] origin) {
            int N = origin.length + 1;
            arr = new int[N];
            for (int i = 1; i < N; i++) {
                arr[i] = origin[i - 1];
            }
        }

        public void add(int L, int R, int num) {
            for (int i = L; i <= R; i++) {
                arr[i] += num;
            }
        }

        public void update(int L, int R, int num) {
            for (int i = L; i <= R; i++) {
                arr[i] = num;
            }
        }

        public long query(int L, int R) {
            int sum = 0;
            for (int i = L; i <= R; i++) {
                sum += arr[i];
            }
            return sum;
        }
    }

    /**
     * 线段树结构：O(logN)
     */
    public static class SegTree {
        // 原序列的信息从0开始，但在arr里是从1开始的
        private int[] arr;
        // 模拟线段树，维护区间的累加和
        private int[] sum;

        // 增加的懒惰标记和值(不为0，则增加)
        // 某一范围内，有没有往下传递的累加任务
        private int[] lazy;

        // 更新的懒惰标记
        // 某一范围内，有没有往下传递的更新任务
        private boolean[] update;
        // 记录更新的值(为0，无法分辨是否要改为0)
        // 某一个范围更新任务，更新成了什么
        private int[] change;

        public SegTree(int[] origin) {
            int N = origin.length + 1;
            // arr[0] 不用 从1开始使用
            arr = new int[N];
            for (int i = 1; i < N; i++) {
                arr[i] = origin[i - 1];
            }

            lazy = new int[N << 2];
            sum = new int[N << 2];
            change = new int[N << 2];
            update = new boolean[N << 2];
        }

        /**
         * 在arr[l~r]范围上，初始化1~N之间的累加和信息sum（树结构）
         * rt : 这个范围在sum中的下标
         */
        public void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }

            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        /**
         * 计算父节点的累加和
         */
        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        /**
         * 增加任务[L~R,V]
         * arr范围和sum树的下标
         */
        public void add(int L, int R, int V, int l, int r, int rt) {
            // 任务如果把此时的范围全包了
            if (L <= l && R >= r) {
                lazy[rt] += V;
                sum[rt] += (r - l + 1) * V;
                return;
            }

            // 任务没有把你全包
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);

            // 默认左侧包含mid
            if (L <= mid) {// 左侧有任务范围的数据
                add(L, R, V, l, mid, rt << 1);
            }

            if (R > mid) {// 右侧有任务范围的数据
                add(L, R, V, mid + 1, r, rt << 1 | 1);
            }

            pushUp(rt);
        }

        /**
         * 之前的，所有懒增加，和懒更新，从父范围，发给左右两个子范围
         * ln表示左子树元素结点个数，
         * rn表示右子树结点个数
         */
        private void pushDown(int rt, int ln, int rn) {
            // 更新的分发策略要在增加的之前，因为更新策略会覆盖掉更新之后的增加任务。

            // 更新时的分发策略
            if (update[rt]) {
                update[rt << 1] = true;
                change[rt << 1] = change[rt];
                sum[rt << 1] = ln * change[rt];

                update[rt << 1 | 1] = true;
                change[rt << 1 | 1] = change[rt];
                sum[rt << 1 | 1] = rn * change[rt];

                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;

                update[rt] = false;
            }

            // 增加时的分发策略
            if (lazy[rt] != 0) {
                lazy[rt << 1] += lazy[rt];
                sum[rt << 1] += ln * lazy[rt];

                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1 | 1] += rn * lazy[rt];

                lazy[rt] = 0;
            }
        }

        /**
         * 更新任务[L~R,V]，L~R的所有的值变成C
         * arr范围和sum树的下标
         */
        public void update(int L, int R, int V, int l, int r, int rt) {
            if (L <= l && R >= r) {
                update[rt] = true;
                change[rt] = V;
                sum[rt] = (r - l + 1) * V;
                lazy[rt] = 0;
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

        /**
         * 范围内累加和是多少
         * rt : 这个范围在sum中的下标
         */
        public long query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return sum[rt];
            }

            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);

            int sum = 0;
            if (L <= mid) {
                sum += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                sum += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegTree tree = new SegTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        tree.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        tree.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        tree.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = tree.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }

    private static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTime = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = generateRandomArray(len, max);

            int start = 1;
            int end = origin.length;
            int root = 1;
            SegTree tree = new SegTree(origin);
            tree.build(start, end, root);

            SegArray array = new SegArray(origin);

            for (int n = 0; n < addOrUpdateTime; n++) {
                int num1 = (int) (Math.random() * end) + 1;
                int num2 = (int) (Math.random() * end) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int V = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    array.add(L, R, V);
                    tree.add(L, R, V, start, end, root);
                } else {
                    array.update(L, R, V);
                    tree.update(L, R, V, start, end, root);
                }
            }

            for (int m = 0; m < queryTimes; m++) {
                int num1 = (int) (Math.random() * end) + 1;
                int num2 = (int) (Math.random() * end) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);

                long ans1 = array.query(L, R);
                long ans2 = tree.query(L, R, start, end, root);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int[] generateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }
}
