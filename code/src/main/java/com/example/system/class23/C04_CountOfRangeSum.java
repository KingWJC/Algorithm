/**
 * 区间和达标的子数组数量 (同class03下的C05_CountOfRangeSum)
 */
package com.example.system.class23;

import java.util.Arrays;
import java.util.HashSet;

public class C04_CountOfRangeSum {
    /**
     * 改写归并O(N*logN)
     */
    public static int countRangeSum1(int[] nums, int lower, int upper) {
        if (nums == null || nums.length < 1) {
            return 0;
        }

        int n = nums.length;
        // 前缀和数组，从index=1开始
        long[] sums = new long[n];
        sums[0] = nums[0];
        for (int i = 0; i < n - 1; i++) {
            sums[i + 1] = sums[i] + nums[i + 1];
        }

        return process(sums, 0, n - 1, lower, upper);
    }

    /**
     * 在[l，r]闭区间的范围上，获取满足条件的数的个数。
     */
    private static int process(long[] sums, int l, int r, int lower, int upper) {
        if (r == l) {
            return sums[l] >= lower && sums[l] <= upper ? 1 : 0;
        }

        int mid = l + (r - l) / 2;
        int count = process(sums, l, mid, lower, upper) + process(sums, mid + 1, r, lower, upper);

        // 左组上的窗口，统计左组上满足右组的条件的个数。
        int windowL = l;
        int windowR = l;
        for (int i = mid + 1; i <= r; i++) {// 调试测试
            while (windowL <= mid && sums[windowL] < sums[i] - upper) {
                windowL++;
            }
            while (windowR <= mid && sums[windowR] <= sums[i] - lower) {
                windowR++;
            }
            count += windowR - windowL;
        }

        int index = 0;
        int l1 = l;
        int l2 = mid + 1;
        long[] help = new long[r - l + 1];
        // 归并排序
        while (l2 <= r && l1 <= mid) {
            if (sums[l1] > sums[l2]) {
                help[index++] = sums[l2++];
            } else {
                help[index++] = sums[l1++];
            }
        }
        while (l1 <= mid) {
            help[index++] = sums[l1++];
        }
        while (l2 <= r) {
            help[index++] = sums[l2++];
        }

        for (int i = 0; i < help.length; i++) {
            sums[l + i] = help[i];
        }

        return count;
    }

    /**
     * 课堂的归并版本（没搞明白）
     */
    public static int countRangeSum2(int[] nums, int lower, int upper) {
        if (nums == null || nums.length < 1) {
            return 0;
        }

        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }

        return countWhileMergeSort(sums, 0, n + 1, upper, lower);
    }

    /**
     * 在(start，end)开区间的范围上，
     * 若arr={1,2,1} sums={0,1,3,4}，求在[2,4]上的结果。
     * 1.计算(0,2)和(2,4)上的结果，count都为0.
     * 2.计算(0,4)上的结果：
     * 当start=0时，sums[0],在sums[2]的[3-4,3-2]上，也在sums[3]的[4-4,4-2]上，count=2
     * 当start=1时，sums[1],在sums[2]的[-1,1]上，也在sums[3]的[0,2]上，count=2
     * 正确结果：4.
     */
    private static int countWhileMergeSort(long[] sums, int start, int end, int upper, int lower) {
        // 只有两个数时，返回0
        if (end - start <= 1) {
            return 0;
        }

        int mid = (start + end) / 2;
        int count = countWhileMergeSort(sums, start, mid, upper, lower)
                + countWhileMergeSort(sums, mid, end, upper, lower);

        // 右组上的窗口，统计右组上满足左组的条件的个数。
        int windowL = mid;
        int windowR = mid;
        // 缓存的索引
        int index = 0;
        long[] cache = new long[end - start];
        // 右组的起始位置
        int r = mid;
        // sums的起始位置为0，是初始无效的前缀和，所以从++l开始。
        // 同时加sums加1个位置，保证mid是
        for (int l = start; l < mid; ++l) {
            while (windowL < end && sums[l] > sums[windowL] - lower) {
                windowL++;
            }
            while (windowR < end && sums[l] >= sums[windowR] - upper) {
                windowR++;
            }
            count += windowR - windowL;

            // 右组中哪些数要放入cache中重新排序，r记录右组的位置
            while (r < end && sums[r] < sums[l]) {
                cache[index++] = sums[r++];
            }
            cache[index++] = sums[l];
        }
        // 从start到r之间的数，从cache中复制到sums中
        System.arraycopy(cache, 0, sums, start, r - start);
        return count;
    }

    /**
     * 暴力解
     */
    public static int countRangeSum3(int[] nums, int lower, int upper) {
        int count = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int sum = 0;
                for (int s = i; s <= j; s++) {
                    sum += nums[s];
                }
                if (sum >= lower && sum <= upper) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 改写SB树结构。
     * 增加，可以接受重复数字, < num 有几个数？
     */
    public static int countRangeSum4(int[] nums, int lower, int upper) {
        SBTreeSet treeSet = new SBTreeSet();
        long sum = 0;
        int ans = 0;
        // 加入数字（前缀和），一个数都没有的时候，就已经有一个前缀和累加和为0，
        treeSet.add(sum);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            // [sum - upper, sum - lower]
            // [10, 20] ?
            // < 10 ? < 21 ?
            long a = treeSet.lessKeySize(sum - lower + 1);
            long b = treeSet.lessKeySize(sum - upper);
            ans += a - b;

            treeSet.add(sum);
        }
        return ans;
    }

    /**
     * 只有key
     */
    private static class SBTNode {
        public long key;
        public SBTNode left;
        public SBTNode right;
        // 节点个数，平衡因子
        public long size;
        // 节点经过或到达的数量,通过它来处理重复值
        public long pathCount;

        public SBTNode(Long k) {
            this.key = k;
            size = 1;
            pathCount = 1;
            left = null;
            right = null;
        }
    }

    private static class SBTreeSet {
        private SBTNode root;
        // 保存key值，判断key是否存在。
        private HashSet<Long> set = new HashSet<>();

        private SBTNode rightRomate(SBTNode cur) {
            // 当前节点包含的重复值个数
            long sameCount = cur.pathCount - (cur.left != null ? cur.left.pathCount : 0)
                    - (cur.right != null ? cur.right.pathCount : 0);

            SBTNode left = cur.left;
            cur.left = left.right;
            left.right = cur;
            left.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;

            // 更新pathCount
            left.pathCount = cur.pathCount;
            cur.pathCount = (cur.left != null ? cur.left.pathCount : 0) + (cur.right != null ? cur.right.size : 0)
                    + sameCount;
            return left;
        }

        private SBTNode leftRomate(SBTNode cur) {
            long sameCount = cur.pathCount - (cur.left != null ? cur.left.pathCount : 0)
                    - (cur.right != null ? cur.right.pathCount : 0);

            SBTNode right = cur.right;
            cur.right = right.left;
            right.left = cur;
            right.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;

            right.pathCount = cur.pathCount;
            cur.pathCount = (cur.left != null ? cur.left.pathCount : 0) + (cur.right != null ? cur.right.size : 0)
                    + sameCount;

            return right;
        }

        private SBTNode maintain(SBTNode cur) {
            if (cur == null) {
                return null;
            }

            long lSize = cur.left != null ? cur.left.size : 0;
            long llSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            long lrSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;

            long rSize = cur.right != null ? cur.right.size : 0;
            long rlSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            long rrSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;

            if (rSize < llSize) {// LL
                cur = rightRomate(cur);
                // 已右旋
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (rSize < lrSize) {
                cur.left = leftRomate(cur.left);
                cur = rightRomate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (lSize < rrSize) {
                cur = leftRomate(cur);

                cur.left = maintain(cur.left);
                cur = maintain(cur);
            } else if (lSize < rlSize) {
                cur.right = rightRomate(cur.right);
                cur = leftRomate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }
            return cur;
        }

        /**
         * key是否存在。返回头节点。
         */
        private SBTNode add(SBTNode cur, long key, boolean contains) {
            if (cur == null) {
                return new SBTNode(key);
            } else {
                cur.pathCount++;
                if (cur.key == key) {
                    return cur;
                } else {
                    if (!contains) {
                        cur.size++;
                    }

                    if (cur.key > key) {
                        cur.left = add(cur.left, key, contains);
                    } else {
                        cur.right = add(cur.right, key, contains);
                    }
                    return maintain(cur);
                }
            }
        }

        public void add(long sum) {
            boolean contains = set.contains(sum);
            root = add(root, sum, contains);
            set.add(sum);
        }

        /**
         * 查找小于key的个数
         */
        public long lessKeySize(long key) {
            long count = 0;
            SBTNode cur = root;
            while (cur != null) {
                // 若等于key，就加上左树的值
                if (cur.key == key) {
                    return count + (cur.left != null ? cur.left.pathCount : 0);
                } else if (cur.key > key) {
                    cur = cur.left;
                } else {
                    count += cur.pathCount - (cur.right != null ? cur.right.pathCount : 0);
                    cur = cur.right;
                }
            }

            return count;
        }

        public long moreKeySize(long key) {
            // SBTNode cur = root;
            // long count = 0;
            // while (cur != null) {
            // if (cur.key == key) {
            // return count + (cur.right != null ? cur.right.pathCount : 0);
            // } else if (cur.key > key) {
            // cur = cur.right;
            // } else {
            // count += cur.pathCount - (cur.left != null ? cur.left.pathCount : 0);
            // cur = cur.left;
            // }
            // }
            // return count;
            return root != null ? root.pathCount - lessKeySize(key + 1) : 0;
        }
    }

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3,2,1 };
        System.out.println(countRangeSum1(arr, 2, 4));
        System.out.println(countRangeSum4(arr, 2, 4));

        int len = 20;
        int varible = 50;
        System.out.println("test start:");
        for (int i = 0; i < 100000; i++) {
            int[] test = generateArray(len, varible);
            int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
            int upper = lower + (int) (Math.random() * varible);
            int ans1 = countRangeSum4(test, lower, upper);
            int ans2 = countRangeSum2(test, lower, upper);
            int ans3 = countRangeSum3(test, lower, upper);
            if (ans1 != ans2) {
                System.out.println("Error: ans1=" + ans1 + " ans2=" + ans2 + " ans3=" + ans3);
                System.out.println("lower:" + lower + " upper:" + upper + " " + Arrays.toString(test));
                break;
            }
        }
        System.out.println("finished");
    }

    private static int[] generateArray(int len, int varible) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * varible) - (int) (Math.random() * varible);
        }
        return arr;
    }
}
