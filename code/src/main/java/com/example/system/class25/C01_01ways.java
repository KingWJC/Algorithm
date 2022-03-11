/**
 * N个0,N个1自由组合满足条件达标结果数
 * 条件：任何前缀串，1的数量都不少于0的数量
 */
package com.example.system.class25;

import java.util.LinkedList;

public class C01_01ways {
    /**
     * 暴力递归
     */
    public static long ways1(int N) {
        return process( N, 0, 0);
    }

    /**
     * index之后，选择m个0的方法数。
     */
    private static long process(int m, int sum0, int sum1) {
        if (sum0 < sum1) {
            return 0;
        }

        if (sum0 == m) {
            return 1;
        }

        long ans = process(m, sum0 + 1, sum1);
        ans += process( m, sum0, sum1 + 1);
        return ans;
    }

    /**
     * 套用卡兰特数的第二个通项公式
     */
    public static long ways2(int N) {
        if (N < 0) {
            return 0;
        }
        if (N < 2) {
            return 1;
        }

        // 一般阶乘会很大，用long类型，int类型最大正21亿，若超出则不计算。
        long mFactorial = 1;
        long nFactorial = 1;
        int m = N;
        long n = N << 1;
        // 组合数计算公式，先计算m和n到n-m+1的阶乘
        for (int i = 1; i <= n; i++) {
            if (i <= m) {
                mFactorial *= i;
            } else {
                nFactorial *= i;
            }
        }

        return (nFactorial / (m + 1)) / mFactorial;
    }

    /**
     * 课程中的方法，保存每一个组合的结果。
     */
    public static long ways3(int N)
    {
		int zero = N;
		int one = N;
		LinkedList<Integer> path = new LinkedList<>();
		LinkedList<LinkedList<Integer>> ans = new LinkedList<>();
		process(zero, one, path, ans);
		long count = 0;
		for (LinkedList<Integer> cur : ans) {
			int status = 0;
			for (Integer num : cur) {
				if (num == 0) {
					status++;
				} else {
					status--;
				}
                // 不合法的情况
				if (status < 0) {
					break;
				}
			}
			if (status == 0) {
				count++;
			}
		}
		return count;
    }

    public static void process(int zero, int one, LinkedList<Integer> path, LinkedList<LinkedList<Integer>> ans) {
		if (zero == 0 && one == 0) {
			LinkedList<Integer> cur = new LinkedList<>();
			for (Integer num : path) {
				cur.add(num);
			}
			ans.add(cur);
		} else {
			if (zero == 0) {
				path.addLast(1);
				process(zero, one - 1, path, ans);
				path.removeLast();
			} else if (one == 0) {
				path.addLast(0);
				process(zero - 1, one, path, ans);
				path.removeLast();
			} else {
				path.addLast(1);
				process(zero, one - 1, path, ans);
				path.removeLast();
				path.addLast(0);
				process(zero - 1, one, path, ans);
				path.removeLast();
			}
		}
	}

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 10; i++) {
            System.out.println(ways1(i) + "===" + ways2(i)+"===="+ways3(i));
        }
        System.out.println("test finish");
    }
}
