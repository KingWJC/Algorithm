/**
 * 正数数组分割为个数跟累加和接近的两个集合
 */
package com.example.system.class11;

public class C23_SplitSumClosedHalf {
    /**
     * 递归尝试，获取最接近的最小累计和
     * 偶数时，两个集合的长度相同，
     * 奇数时，两个集合的长度相差1：从两种长度中，选最大(最接近)
     */
    public static int getMinSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        sum /= 2;

        int N = arr.length;
        int ans = 0;
        // if (N % 2 == 1) { 使用二进制&，最后一位是否为1.
        if ((N & 1) == 1) { // 奇偶判断
            int ans1 = process(arr, 0, N / 2, sum);
            int ans2 = process(arr, 0, N / 2 + 1, sum);
            ans = Math.max(ans1, ans2);
        } else {
            ans = process(arr, 0, N / 2, sum);
        }
        return ans;
    }

    /**
     * arr[index.....]上，自由选择数值加入集合，但不能超过picks
     * 思考方式：可以从index考虑，或者pick的边界考虑，因为pick包含在index的范围内。
     */
    private static int process(int[] arr, int index, int picks, int rest) {
        // picks一定小于arr.Length
        // if (picks == 0 || index == arr.length) {
        // return 0;
        // }
        if (index == arr.length) {
            return picks == 0 ? 0 : -1;
        }
        // 不选择当前位置的数值
        int p1 = process(arr, index + 1, picks, rest);

        int p2 = -1;
        int next = -1;
        if (arr[index] <= rest) {
            next = process(arr, index + 1, picks - 1, rest - arr[index]);
        }
        if (next != -1) {
            p2 = arr[index] + next;
        }
        return Math.max(p1, p2);
    }

    /**
     * 动态规划, 三个可变参数组成三维表
     */
    public static int useDP(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int N = arr.length;
        int midSum = 0;
        for (int i : arr) {
            midSum += i;
        }
        midSum = midSum / 2;

        // 当为奇数时，向上取整
        // int picks = (N & 1) == 0 ? N / 2 : N / 2 + 1;
        int picks = (N + 1) / 2;

        int[][][] dp = new int[N + 1][picks + 1][midSum + 1];
        for (int index = 0; index <= N; index++) {
            for (int pick = 0; pick <= picks; pick++) {
                for (int rest = 0; rest <= midSum; rest++) {
                    dp[index][pick][rest] = -1;
                }
            }
        }

        for (int rest = 0; rest <= midSum; rest++) {
            dp[N][0][rest] = 0;
        }

        for (int index = N - 1; index >= 0; index--) {
            for (int pick = 0; pick <= picks; pick++) {
                for (int rest = 0; rest <= midSum; rest++) {
                    int p1 = dp[index + 1][pick][rest];
                    int p2 = -1;
                    int next = -1;
                    if (pick - 1 >= 0 && arr[index] <= rest) {
                        next = dp[index + 1][pick - 1][rest - arr[index]];
                    }
                    if (next != -1) {
                        p2 = arr[index] + next;
                    }
                    dp[index][pick][rest] = Math.max(p1, p2);
                }
            }
        }

        int ans = 0;
        if ((N & 1) == 0) {
            ans = dp[0][N / 2][midSum];
        } else {
            ans = Math.max(dp[0][N / 2][midSum], dp[0][N / 2 + 1][midSum]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int maxLength = 20;
        int maxValue = 30;
        int testTimes = 10000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxLength, maxValue);
            int ans1 = getMinSum(arr);
            int ans2 = useDP(arr);
            if (ans1 != ans2) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finished");
    }

    private static int[] generateRandomArray(int maxLength, int maxValue) {
        int length = (int) (Math.random() * maxLength) + 1;
        int[] ans = new int[length];
        for (int i = 0; i < length; i++) {
            ans[i] = (int) (Math.random() * maxValue);
        }
        return ans;
    }
}
