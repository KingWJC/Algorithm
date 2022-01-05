/**
 * 给定一个输入参数N，代表纸条都从下边向上方连续对折N次。 请从上到下打印所有折痕的方向。
 */
package com.example.system.class07;

public class C08_PaperFolding {
    public static void printAllFolds(int N) {
        process(1, true, N);
    }
    
    // 这个节点如果是凹的话，down = T
    // 这个节点如果是凸的话，down = F
    // 函数的功能：中序打印以你想象的节点为头的整棵树！
    private static void process(int index, Boolean flag, int N) {
        if (index > N)
            return;

        process(index + 1, true, N);
        System.out.println(Boolean.TRUE.equals(flag) ? "down" : "up");
        process(index + 1, false, N);
    }

    public static void main(String[] args) {
        int N = 3;
        printAllFolds(N);
    }
}
