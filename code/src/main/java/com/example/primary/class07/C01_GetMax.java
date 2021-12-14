/**
 * 不要用任何比较判断，返回两个数中较大的数
 * 使用位运算.
 */
package com.example.primary.class07;

public class C01_GetMax {

    // 如果n为0返回1，如果n为1返回0.
    public static int flip(int n) {
        return n ^ 1;
    }

    // 返回整数n的符号，正数和0则返回1，负数返回0
    public static int getSign(int n) {
        return flip((n >> 31) & 1);
    }

    // 得到a-b的值的符号，就可以知道是返回a还是返回b。
    // 存在一定的局限性，那就是如果a - b的值发生溢出，返回的结果就会不正确
    public static int getMax1(int a, int b) {
        int c = a - b;
        int scA = getSign(c);
        int scB = flip(scA);
        return a * scA + b * scB;
    }

    /**
     * 如果a和b的符号不同（difSab = 1, sameSab = 0），则有：             (difSab * sa)
     *  如果a为0或正，那么b为负（sa = 1, sb = 0），应该返回a    
     *  如果b为0或正，那么a为负（sb = 1, sa = 0），应该返回b              (flip(returnA);)
     * 如果a和b的符号相同（difSab = 0, sameSab = 1），此时一定不会发生溢出:(sameSab * sc;)
     *  如果a - b为0或者为正(sc = 1)，返回a    
     *  如果a - b为负(sc = 0 )，返回b                                  (flip(returnA);)
     */
    public static int getMax2(int a, int b) {
        int sa = getSign(a);
        int sb = getSign(b);
        int difSab = sa ^ sb;
        int sameSab = flip(difSab);

        int sc = getSign(a - b);
        int returnA = difSab * sa + sameSab * sc;
        int returnB = flip(returnA);

        return a * returnA + b * returnB;
    }

    public static void main(String[] args) {
        int a = -16;
        int b = -19;
        System.out.println("-16 和 -19 的最大值 : " + getMax1(a, b));
        System.out.println("-16 和 -19 的最大值 : " + getMax2(a, b));
        a = 2147483647;
		b = -2147480000;
        System.out.println("2147483647 和 -2147480000 的最大值 : " + getMax1(a, b));
        System.out.println("2147483647 和 -2147480000 的最大值 : " + getMax2(a, b));
    }
}
