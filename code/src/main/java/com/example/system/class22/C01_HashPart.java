/**
 * 哈希分流
 */
package com.example.system.class22;

import java.util.Arrays;

public class C01_HashPart {
    /**
     * 测试哈希值的均匀分布性
     */
    private static void testHashPart() {
        int N = 1000;
        String[] strs = diffStr(N);
        int[] count = new int[10];
        for (String str : strs) {
            int hashCode = str.hashCode();
            count[Math.abs(hashCode) % 10]++;
        }

        System.out.println(Arrays.toString(count));
    }

    private static String[] diffStr(int N) {
        String[] ans = new String[N];
        for (int i = 0; i < N; i++) {
            ans[i] = "wangjicheng" + String.valueOf(i);
        }
        return ans;
    }

    public static void main(String[] args) {
        // java中string类型的哈希值计算，很简单且有规律，所以会均分，而不是近似均分。
        testHashPart();
    }
}
