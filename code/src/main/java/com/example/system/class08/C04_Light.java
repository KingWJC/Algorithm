/**
 * 贪心算法:4. 点亮str中所有需要点亮的位置至少需要几盏灯
 */
package com.example.system.class08;

import java.util.HashSet;

public class C04_Light {
    /**
     * 贪心策略: 若三个位置都是".", 则灯放中间。
     */
    public static int getMinLight(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }

        int lights = 0;
        char[] ans = road.toCharArray();
        int i = 0;
        while (i < ans.length) {
            if (ans[i] == 'X') {
                i = i + 1;
            } else {
                lights++;
                if (i + 1 == ans.length) {
                    break;
                } else {
                    if (ans[i + 1] == 'X') {
                        i = i + 2;
                    } else {
                        // 若i,i+1都是'.', 则i+2是X或.都没问题，所以直接跳到i+3。
                        i = i + 3;
                    }
                }
            }
        }

        return lights;
    }

    /**
     * 暴力递归！所有情况都尝试！
     */
    public static int getMinLightTest(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    /**
     * str[index....]位置，自由选择放灯还是不放灯
     * str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
     * 要求选出能照亮所有.的方案，并且在这些有效的方案中，返回最少需要几个灯
     */
    private static int process(char[] str, int index, HashSet<Integer> lights) {
        if (index == str.length) {
            for (int i = 0; i < str.length; i++) {
                // 检查lights中灯若不能照亮所有'.' 返回MAX_VALUE，方案无效。
                if (str[i] == '.' && !lights.contains(i - 1)
                        && !lights.contains(i) && !lights.contains(i + 1)) {
                    return Integer.MAX_VALUE;
                }
            }
            return lights.size();
        } else {
            // 不在当前位置index放灯, 获取str[index+1....]中最少需要几个灯
            int no = process(str, index + 1, lights);
            int yes = Integer.MAX_VALUE;
            if (str[index] == '.') {
                lights.add(index);
                // 在当前位置index放灯, 获取str[index+1....]中最少需要几个灯
                yes = process(str, index + 1, lights);
                lights.remove(index);
            }
            return Math.min(no, yes);
        }
    }

    public static void main(String[] args) {
        int maxLength = 20;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            String road = getRandomString(maxLength);
            int count1 = getMinLight(road);
            int count2 = getMinLightTest(road);
            if (count1 != count2) {
                System.out.println(count1 + " error " + count2);
                System.out.println(road);
                break;
            }
        }
        System.out.println("finished");
    }

    private static String getRandomString(int maxLength) {
        int length = (int) (Math.random() * maxLength) + 1;
        char[] ans = new char[length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = Math.random() < 0.5 ? '.' : 'X';
        }
        return String.valueOf(ans);
    }
}
