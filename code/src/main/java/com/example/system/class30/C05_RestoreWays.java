/**
 * 还原数组丢失的数字
 * 整型数组arr长度为n(3 <= n <= 10^4)，最初每个数字是<=200的正数且满足如下条件：
 * 1. 0位置的要求：arr[0]<=arr[1] 
 * 2. n-1位置的要求：arr[n-1]<=arr[n-2]
 * 3. 中间i位置的要求：arr[i]<=max(arr[i-1],arr[i+1]) 
 */
package com.example.system.class30;

public class C05_RestoreWays {
    /**
     * 暴力递归：从左到右的尝试
     */
    public static int ways1(int[] arr) {
        return process1(arr, 0);
    }

    /**
     * 穷举每一个可能性，最后判断组成的数组是否符合条件
     * 返回0....index范围上的有效转化方式
     */
    private static int process1(int[] arr, int index) {
        if (index == arr.length) {
            return isValid(arr) ? 1 : 0;
        }

        int ways = 0;
        if (arr[index] != 0) {
            ways = process1(arr, index + 1);
        } else {
            for (int i = 1; i < 201; i++) {
                arr[index] = i;
                ways += process1(arr, index + 1);
            }
            arr[index] = 0;
        }
        return ways;
    }

    /**
     * 判断还原的数组，是否满足条件
     */
    private static boolean isValid(int[] arr) {
        if (arr[0] > arr[1]) {
            return false;
        }
        int N = arr.length;
        if (arr[N - 1] > arr[N - 2]) {
            return false;
        }
        for (int i = 1; i < N - 1; i++) {
            if (arr[i] > Math.max(arr[i - 1], arr[i + 1])) {
                return false;
            }
        }

        return true;
    }

    /**
     * 暴力递归：优良的尝试，
     * 从右往左的尝试模型，增加外部信息
     */
    public static int ways2(int[] arr) {
        int N = arr.length;
        if (arr[N - 1] != 0) {
            return process3(arr, N - 1, arr[N - 1], 2);
        } else {
            int ways = 0;
            for (int v = 1; v <= 200; v++) {
                ways += process3(arr, N - 1, v, 2);
            }
            return ways;
        }
    }

    /**
     * i位置的数字变成了v: arr[i]=v
     * arr[i]和arr[i+1]的关系为s:
     * s==0，代表arr[i] < arr[i+1] 右大
     * s==1，代表arr[i] == arr[i+1] 右=当前
     * s==2，代表arr[i] > arr[i+1] 右小
     * 返回0...i范围上有多少种有效的转化方式
     */
    private static int process2(int[] arr, int i, int v, int s) {
        // 只剩一个数了，0...0
        if (i == 0) {
            // 返回1，是因为上层遍历中v有枚举范围。
            return s != 2 && (arr[i] == 0 || arr[i] == v) ? 1 : 0;
        }
        // v是无效值。
        if (arr[i] != 0 && arr[i] != v) {
            return 0;
        }

        int ways = 0;
        // i位置的数真的可以变成V
        if (s == 0 || s == 1) {
            // 当arr[i+1]>arr[i]时，设置arr[i-1]的值
            for (int pre = 1; pre <= 200; pre++) {
                ways += process2(arr, i - 1, pre, pre < v ? 0 : (pre == v ? 1 : 2));
            }
        } else {
            for (int pre = v; pre <= 200; pre++) {
                ways += process2(arr, i - 1, pre, pre == v ? 1 : 2);
            }
        }
        return ways;
    }

    /**
     * 代码优化
     */
    private static int process3(int[] arr, int i, int v, int s) {
        if (i == 0) {
            return (s != 2 && (arr[i] == 0 || arr[i] == v)) ? 1 : 0;
        }
        if (arr[i] != 0 && arr[i] != v) {
            return 0;
        }

        int ways = 0;
        // 按s状态分块。
        if (s == 0 || s == 1) {
            for (int pre = 1; pre < v; pre++) {
                ways += process3(arr, i - 1, pre, 0);
            }
        }
        ways += process3(arr, i - 1, v, 1);
        for (int pre = v + 1; pre <= 200; pre++) {
            ways += process3(arr, i - 1, pre, 2);
        }

        return ways;
    }

    /**
     * 动态规划表
     */
    public static int ways3(int[] arr) {
        int N = arr.length;
        // dp表示arr[0....N]范围上，arr[N]=V时，有效的转化方式有多少种
        int[][][] dp = new int[N][201][3];
        // 左边界0位置，一定<=1位置。所以0，1表里有值。
        if (arr[0] != 0) {
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        } else {
            for (int v = 1; v < 201; v++) {
                dp[0][v][0] = 1;
                dp[0][v][1] = 1;
            }
        }

        for (int i = 1; i < N; i++) {
            for (int v = 1; v < 201; v++) {
                for (int s = 0; s < 3; s++) {
                    // 过滤无效的v值。
                    if (arr[i] == 0 || arr[i] == v) {
                        if (s == 0 || s == 1) {
                            for (int pre = 1; pre < v; pre++) {
                                dp[i][v][s] += dp[i - 1][pre][0];
                            }
                        }
                        dp[i][v][s] += dp[i - 1][v][1];
                        for (int pre = v + 1; pre < 201; pre++) {
                            dp[i][v][s] += dp[i - 1][pre][2];
                        }
                    }
                }
            }
        }

        if (arr[N - 1] != 0) {
            return dp[N - 1][arr[N - 1]][2];
        } else {
            int ways = 0;
            for (int v = 1; v < 201; v++) {
                ways += dp[N - 1][v][2];
            }
            return ways;
        }
    }

    /**
     * 动态规划表：前缀和的优化
     */
    public static int ways4(int[] arr) {
        int N = arr.length;
        int[][][] dp = new int[N][201][3];
        if (arr[0] != 0) {
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        } else {
            for (int v = 1; v < 201; v++) {
                dp[0][v][0] = 1;
                dp[0][v][1] = 1;
            }
        }

        int[][] preSum = new int[201][3];
        // N=0：第一行的前缀和。
        for (int v = 1; v < 201; v++) {
            for (int s = 0; s < 3; s++) {
                preSum[v][s] = preSum[v - 1][s] + dp[0][v][s];
            }
        }

        for (int i = 1; i < N; i++) {
            for (int v = 1; v < 201; v++) {
                for (int s = 0; s < 3; s++) {
                    if (arr[i] == 0 || arr[i] == v) {
                        if (s == 1 || s == 0) {
                            // 前缀和的差，L边界被包含，所以要减一.
                            // [1~v-1]的结果= preSum[v-1] - preSum[1-1].
                            dp[i][v][s] += preSum[v - 1][0] - preSum[0][0];
                        }
                        dp[i][v][s] += dp[i - 1][v][1];
                        dp[i][v][s] += preSum[200][2] - preSum[v][2];
                    }
                }
            }
            // 重新计算当前行的前缀结果和。
            for (int v = 1; v < 201; v++) {
                for (int s = 0; s < 3; s++) {
                    preSum[v][s] = preSum[v - 1][s] + dp[i][v][s];
                }
            }
        }

        if (arr[N - 1] != 0) {
            return dp[N - 1][arr[N - 1]][2];
        } else {
            return preSum[200][2] - preSum[0][2];
        }
    }

    public static void main(String[] args) {
        int len = 4;
        int testTime = 15;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len);
            int ans1 = ways1(arr);
            int ans2 = ways2(arr);
            int ans3 = ways3(arr);
            int ans4 = ways4(arr);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("功能测试结束");
        System.out.println("===========");
		int N = 100000;
		int[] arr = generateRandomArray(N);
		long begin = System.currentTimeMillis();
		ways4(arr);
		long end = System.currentTimeMillis();
		System.out.println("run time : " + (end - begin) + " ms");

    }

    public static int[] generateRandomArray(int len) {
        int[] ans = new int[len];
        for (int i = 0; i < ans.length; i++) {
            if (Math.random() < 0.5) {
                ans[i] = 0;
            } else {
                ans[i] = (int) (Math.random() * 200) + 1;
            }
        }
        return ans;
    }
}
