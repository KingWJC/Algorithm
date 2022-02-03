/**
 * 单调栈结构的实现
 * 记录两个信息：左边最近小于（大于）的数，右边最近小于（大于）的数
 */
package com.example.system.class13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class C01_MonotonousStack {

    /**
     * 暴力解：时间复杂度O(N^2)
     */
    public static int[][] getNearLessN2(int[] arr) {
        int[][] ans = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int cur = i;
            int leftless = -1;
            while (cur > 0) {
                if (arr[i] > arr[cur - 1]) {
                    leftless = cur - 1;
                    break;
                }
                cur--;
            }

            cur = i;
            int rightless = -1;
            while (cur < arr.length - 1) {
                if (arr[i] > arr[cur + 1]) {
                    rightless = cur + 1;
                    break;
                }
                cur++;
            }

            ans[i][0] = leftless;
            ans[i][1] = rightless;
        }
        return ans;
    }

    /**
     * 获取左右最近且小于的数，（数组无重复）
     */
    public static int[][] getNearLessNoRepeat(int[] arr) {
        // 二维数组，存储数组中每个索引位置的两个信息（左侧最近，右侧最近）
        int[][] ans = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                // 处理弹出位置的数字
                int index = stack.pop();
                ans[index][0] = stack.isEmpty() ? -1 : stack.peek();
                ans[index][1] = i;
            }
            stack.push(i);
        }
         // 在数组中，右边没有小于它的数，只计算左边
        while (!stack.isEmpty()) {
            int index = stack.pop();
            ans[index][0] = stack.isEmpty() ? -1 : stack.peek();
            ans[index][1] = -1;
        }
        return ans;
    }

    /**
     * 获取左右最近且小于的数， O(N)
     */
    public static int[][] getNearLess(int[] arr) {
        int[][] ans = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> data = stack.pop();
                int leftLess = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer index : data) {
                    ans[index][0] = leftLess;
                    ans[index][1] = i;
                }
            }
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(i);
            } else {
                List<Integer> list = new ArrayList<Integer>();
                list.add(i);
                stack.add(list);
            }
        }

        while (!stack.isEmpty()) {
            List<Integer> list = stack.pop();
            int leftLess = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (int index : list) {
                ans[index][0] = leftLess;
                ans[index][1] = -1;
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 2000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(getNearLessNoRepeat(arr1), getNearLessN2(arr1))) {
                System.out.println("error");
                System.out.println(Arrays.toString(arr1));
                break;
            }

            if (!isEqual(getNearLess(arr2), getNearLessN2(arr2))) {
                System.out.println("error");
                System.out.println(Arrays.toString(arr2));
                break;
            }
        }
        System.out.println("测试结束");
    }

    private static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int temp = arr[i];
            arr[i] = arr[swapIndex];
            arr[swapIndex] = temp;
        }
        return arr;
    }

    private static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    private static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }
}
