package com.example.primary.class02;

/**
 *  n-m 之间的数的和.
 */
public class C06_RangeSum {
    public static void main(String[] args) {
        int[] arr = new int[] { 1, 3, 6, 2, 8, 2, 0 };
        
        RangeSum1 sum1 = new RangeSum1(arr);
        int result1 = sum1.rangeSum(2, 6);
        System.out.println(result1);

        RangeSum2 sum2 = new RangeSum2(arr);
        int result2 = sum2.rangeSum(2, 6);
        System.out.println(result2);

        RangeSum3 sum3 = new RangeSum3(arr);
        int result3 = sum3.rangeSum(2, 6);
        System.out.println(result3);
    }

    /**
     * 即时计算
     */
    private static class RangeSum1 {
        private int[] arr;

        public RangeSum1(int[] arr) {
            this.arr = arr;
        }

        private int rangeSum(int n, int m) {
            int sum = 0;
            for (int i = n; i <= m; i++) {
                sum += arr[i];
            }
            return sum;
        }
    }

    /**
     * 矩齿数组, 二维表.
     */
    private static class RangeSum2 {
        private int[][] arrList;

        public RangeSum2(int[] arr) {
            arrList = new int[arr.length][];
            for (int e = 0; e < arr.length; e++) {
                arrList[e] = new int[e + 1];
                for (int b = 0; b <= e; b++) {
                    if (e - b > 0) {
                        arrList[e][b] = arrList[e - 1][b] + arr[e];
                    } else {
                        arrList[e][b] = arr[e];
                    }
                }
            }
        }

        private int rangeSum(int start, int end) {
            return arrList[end][start];
        }
    }

    /**
     * 前缀和数组.
     */
    private static class RangeSum3 {
        private int[] sumArr;

        public RangeSum3(int[] arr) {
            int count = arr.length;
            sumArr = new int[count];
            sumArr[0] = arr[0];
            for (int i = 1; i < count; i++) {
                sumArr[i] = sumArr[i - 1] + arr[i];
            }
        }

        private int rangeSum(int n, int m) {
            return n == 0 ? sumArr[m] : sumArr[m] - sumArr[n - 1];
        }
    }
}
