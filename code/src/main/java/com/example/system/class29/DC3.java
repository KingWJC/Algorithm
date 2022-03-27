/**
 * DC3算法模板（论文转换版本）
 */
package com.example.system.class29;

public class DC3 {
    // 后缀数组的有序列表
    // sa[0]=3：表示以数组中3位置开始的后缀，字典序最小。
    public int[] sa;
    // 后缀数组的排名列表
    // rank[0]=1,表示数组中0位置开始的后缀，字典序的排名为1.
    public int[] rank;
    public int[] height;

    /**
     * 约定：数组中，最小值>=1（因为分隔符会补0）
     * 如果不满足，处理成满足的，都加一个正值，也不会影响使用
     * @param nums 若是字符串，请转成整型数组nums（ACSII码）
     * @param max  nums里面最大值，用于准备基数排序的桶数量
     */
    public DC3(int[] nums, int max) {
        sa = createSA(nums, max);
        rank = createRank();
        height = createHeight(nums);
    }

    private int[] createSA(int[] nums, int max) {
        int n = nums.length;
        // 增加3个空位，用于补空。
        int[] arr = new int[n + 3];
        for (int i = 0; i < n; i++) {
            arr[i] = nums[i];
        }

        return skew(arr, n, max);
    }

    private int[] skew(int[] nums, int n, int k) {
        int n0 = (n + 2) / 3;
        int n1 = (n + 1) / 3;
        int n2 = n / 3;
        int n02 = n0 + n2;
        int[] s12 = new int[n02 + 3];
        int[] sa12 = new int[n02 + 3];
        for (int i = 0, j = 0; i < n + (n0 - n1); ++i) {
            if (0 != i % 3) {
                s12[j++] = i;
            }
        }
        // 三维数据的基数排序
        radixPass(nums, s12, sa12, 2, n02, k);
        radixPass(nums, sa12, s12, 1, n02, k);
        radixPass(nums, s12, sa12, 0, n02, k);
        // 查看是否分出严格排序
        int name = 0, c0 = -1, c1 = -1, c2 = -1;
        for (int i = 0; i < n02; ++i) {
            if (c0 != nums[sa12[i]] || c1 != nums[sa12[i] + 1] || c2 != nums[sa12[i] + 2]) {
                name++;
                c0 = nums[sa12[i]];
                c1 = nums[sa12[i] + 1];
                c2 = nums[sa12[i] + 2];
            }
            if (1 == sa12[i] % 3) {
                s12[sa12[i] / 3] = name;
            } else {
                s12[sa12[i] / 3 + n0] = name;
            }
        }

        if (name < n02) {
            // 有重复排名，递归调用
            sa12 = skew(s12, n02, name);
            for (int i = 0; i < n02; i++) {
                s12[sa12[i]] = i + 1;
            }
        } else {
            for (int i = 0; i < n02; i++) {
                sa12[s12[i] - 1] = i;
            }
        }
        // 对S0进行排名
        int[] s0 = new int[n0], sa0 = new int[n0];
        for (int i = 0, j = 0; i < n02; i++) {
            if (sa12[i] < n0) {
                s0[j++] = 3 * sa12[i];
            }
        }
        radixPass(nums, s0, sa0, 0, n0, k);
        // S0和S12进行合并
        int[] sa = new int[n];
        for (int p = 0, t = n0 - n1, r = 0; r < n; r++) {
            int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
            int j = sa0[p];
            if (sa12[t] < n0 ? leq(nums[i], s12[sa12[t] + n0], nums[j], s12[j / 3])
                    : leq(nums[i], nums[i + 1], s12[sa12[t] - n0 + 1], nums[j], nums[j + 1], s12[j / 3 + n0])) {
                sa[r] = i;
                t++;
                if (t == n02) {
                    for (r++; p < n0; p++, r++) {
                        sa[r] = sa0[p];
                    }
                }
            } else {
                sa[r] = j;
                p++;
                if (p == n0) {
                    for (r++; t < n02; t++, r++) {
                        sa[r] = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                    }
                }
            }
        }
        return sa;
    }

    /**
     * 基数排序
     */
    private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int k) {
        int[] cnt = new int[k + 1];
        for (int i = 0; i < n; ++i) {
            cnt[nums[input[i] + offset]]++;
        }
        for (int i = 0, sum = 0; i < cnt.length; ++i) {
            int t = cnt[i];
            cnt[i] = sum;
            sum += t;
        }
        for (int i = 0; i < n; ++i) {
            output[cnt[nums[input[i] + offset]]++] = input[i];
        }
    }

    /**
     * 比较二维数据的大小
     */
    private boolean leq(int a1, int a2, int b1, int b2) {
        return a1 < b1 || (a1 == b1 && a2 <= b2);
    }

    /**
     * 比较三维数据的大小
     */
    private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
        return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
    }

    private int[] createRank() {
        int n = sa.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[sa[i]] = i;
        }
        return ans;
    }

    private int[] createHeight(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        // 依次求h[i] , k = 0(存储上一个h[i-1]的值)
        // 空间压缩：没用h数组，直接用k来暂存h数组的值。
        for (int i = 0, k = 0; i < n; ++i) {
            if (rank[i] != 0) {
                if (k > 0) {
                    --k;
                }
                int j = sa[rank[i] - 1];
                while (i + k < n && j + k < n && nums[i + k] == nums[j + k]) {
                    ++k;
                }
                // h[i] = k
                ans[rank[i]] = k;
            }
        }
        return ans;
    }
}
