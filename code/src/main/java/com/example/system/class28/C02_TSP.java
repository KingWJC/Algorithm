/**
 * N个城市绕一圈，返回最短的总距离
 * 无向图，邻接矩阵
 */
package com.example.system.class28;

import java.util.ArrayList;
import java.util.List;

public class C02_TSP {
    /**
     * 暴力递归
     * 任何两座城市之间的距离，可以在matrix里面拿到
     */
    public static int minDistance1(int[][] matrix) {
        int N = matrix.length;
        // set.get(i) == 1 i这座城市在集合里
        // set.get(i) == null i这座城市不在集合里
        List<Integer> set = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            set.add(1);
        }
        // 以0号城市，作为出发点和最后的回归点。
        return process1(matrix, set, 0);
    }

    /**
     * start这座城一定在set里
     * 从start出发，要把set中所有的城市过一遍，最终回到0这座城市，最小距离是多少
     */
    private static int process1(int[][] matrix, List<Integer> set, int start) {
        int exist = 0;
        for (Integer city : set) {
            if (city != null) {
                exist++;
            }
        }

        if (exist == 1) {
            return matrix[start][0];
        }

        int min = Integer.MAX_VALUE;
        set.set(start, null);
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i) != null) {
                min = Math.min(min, matrix[start][i] + process1(matrix, set, i));
            }
        }
        // 恢复现场
        set.set(start, 1);
        return min;
    }

    /**
     * 状态压缩：位信息表示城市状态
     */
    public static int minDistance2(int[][] matrix) {
        int N = matrix.length;
        // 7座城：1<<7=10 000 000，再-1，就是1 111 111.
        int allCity = (1 << N) - 1;
        return process2(matrix, allCity, 0);
    }

    /**
     * cityState 位值为1 表示未经过。
     */
    private static int process2(int[][] matrix, int cityState, int start) {
        // cityState位信息中只有一个1，就等于它最右的1.
        if (cityState == (cityState & (~cityState + 1))) {
            return matrix[start][0];
        }

        int min = Integer.MAX_VALUE;
        // 去掉start位置的1.
        cityState &= (~(1 << start));
        for (int move = 0; move < matrix.length; move++) {
            // cityState在move位是否为1，若为1则大于0，不是等于1.
            if ((cityState & (1 << move)) != 0) {
                int cur = matrix[start][move] + process2(matrix, cityState, move);
                min = Math.min(min, cur);
            }
        }
        cityState |= (1 << start);
        return min;
    }

    /**
     * 记忆化搜索，有重复值
     */
    public static int minDistance3(int[][] matrix) {
        int N = matrix.length;
        int allCity = (1 << N) - 1;
        // dp[i]=-1,表示未计算
        int[][] dp = new int[1 << N][N];
        for (int i = 0; i < (1 << N); i++) {
            for (int j = 0; j < N; j++) {
                dp[i][j] = -1;
            }
        }
        return process3(matrix, allCity, 0, dp);
    }

    private static int process3(int[][] matrix, int cityState, int start, int[][] dp) {
        if (dp[cityState][start] != -1) {
            return dp[cityState][start];
        }

        if (cityState == (cityState & (~cityState + 1))) {
            dp[cityState][start] = matrix[start][0];
        } else {
            int min = Integer.MAX_VALUE;
            cityState &= (~(1 << start));
            for (int i = 0; i < matrix.length; i++) {
                if ((cityState & (1 << i)) != 0) {
                    int cur = matrix[start][i] + process3(matrix, cityState, i, dp);
                    min = Math.min(min, cur);
                }
            }
            cityState |= (1 << start);
            dp[cityState][start] = min;
        }
        return dp[cityState][start];
    }

    /**
     * 动态规划表
     */
    public static int minDistance4(int[][] matrix) {
        int N = matrix.length;
        int stateNums = 1 << N;
        int[][] dp = new int[stateNums][N];
        for (int state = 0; state < stateNums; state++) {
            for (int start = 0; start < N; start++) {
                if ((state & (1 << start)) != 0) {
                    if (state == (state & (~state + 1))) {
                        dp[state][start] = matrix[start][0];
                    } else {
                        int min = Integer.MAX_VALUE;
                        int preState = state & (~(1 << start));
                        for (int i = 0; i < N; i++) {
                            if ((preState & (1 << i)) != 0) {
                                min = Math.min(min, matrix[start][i] + dp[preState][i]);
                            }
                        }
                        dp[state][start] = min;
                    }
                }
            }
        }
        return dp[stateNums - 1][0];
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 50;
        int testTimes = 1000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[][] matrix = generateGraph(maxLen, maxValue);
            int ans1 = minDistance1(matrix);
            int ans2 = minDistance2(matrix);
            int ans3 = minDistance3(matrix);
            int ans4 = minDistance4(matrix);
            if (ans1 != ans2 || ans3 != ans2 || ans3 != ans4) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("功能测试结束");

        int len = 22;
        int value = 100;
        System.out.println("性能测试开始，数据规模 : " + len);
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * value) + 1;
            }
        }
        for (int i = 0; i < len; i++) {
            matrix[i][i] = 0;
        }
        long start = System.currentTimeMillis();
        minDistance4(matrix);
        long end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " 毫秒");
        System.out.println("性能测试结束");
    }

    /**
     * 生成无向图的邻接矩阵
     */
    private static int[][] generateGraph(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int value = (int) (Math.random() * maxValue) + 1;
                matrix[i][j] = value;
                matrix[j][i] = value;
            }
        }
        for (int i = 0; i < N; i++) {
            matrix[i][i] = 0;
        }
        return matrix;
    }

    /**
     * 指定旅行的起始点
     */
    public static int tsp1(int[][] matrix, int origin) {
        if (matrix == null || matrix.length < 2 || origin < 0 || origin >= matrix.length) {
            return 0;
        }
        // 要考虑的集合
        ArrayList<Integer> cities = new ArrayList<>();
        // cities[0] != null 表示0城在集合里
        // cities[i] != null 表示i城在集合里
        for (int i = 0; i < matrix.length; i++) {
            cities.add(1);
        }
        // null,1,1,1,1,1,1
        // origin城不参与集合
        cities.set(origin, null);
        return process(matrix, origin, cities, origin);
    }

    // matrix 所有距离，存在其中
    // aim 固定参数，唯一的目标
    // cities 要考虑的集合，一定不含有origin（和上面方法的区别）
    // 当前来到的城市是谁，cur
    public static int process(int[][] matrix, int aim, ArrayList<Integer> cities, int cur) {
        boolean hasCity = false; // 集团中还是否有城市
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i) != null) {
                hasCity = true;
                cities.set(i, null);
                // matrix[cur][i] + f(i, 集团(去掉i) )
                ans = Math.min(ans, matrix[cur][i] + process(matrix, aim, cities, i));
                cities.set(i, 1);
            }
        }
        return hasCity ? ans : matrix[cur][aim];
    }

    /**
     * 动态规划
     */
    public static int tsp2(int[][] matrix, int origin) {
        if (matrix == null || matrix.length < 2 || origin < 0 || origin >= matrix.length) {
            return 0;
        }
        int N = matrix.length - 1; // 除去origin之后是n-1个点
        int S = 1 << N; // 状态数量
        int[][] dp = new int[S][N];
        int icity = 0;
        int kcity = 0;
        for (int i = 0; i < N; i++) {
            // 排除起始点
            icity = i < origin ? i : i + 1;
            // 00000000 i
            dp[0][i] = matrix[icity][origin];
        }
        for (int status = 1; status < S; status++) {
            // 尝试每一种状态 status = 0 0 1 0 0 0 0 0 0
            // 下标 8 7 6 5 4 3 2 1 0
            for (int i = 0; i < N; i++) {
                // i 枚举的出发城市
                dp[status][i] = Integer.MAX_VALUE;
                if ((1 << i & status) != 0) {
                    // 如果i这座城是可以枚举的，i = 6 ， i对应的原始城的编号，icity
                    icity = i < origin ? i : i + 1;
                    for (int k = 0; k < N; k++) { // i 这一步连到的点，k
                        if ((1 << k & status) != 0) { // i 这一步可以连到k
                            kcity = k < origin ? k : k + 1; // k对应的原始城的编号，kcity
                            dp[status][i] = Math.min(dp[status][i], dp[status ^ (1 << i)][k] + matrix[icity][kcity]);
                        }
                    }
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            icity = i < origin ? i : i + 1;
            ans = Math.min(ans, dp[S - 1][i] + matrix[origin][icity]);
        }
        return ans;
    }
}
