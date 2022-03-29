/**
 * 最大网络流
 * 测试链接:https://lightoj.com/problem/internet-bandwidth
 */
package com.example.system.class31;

import java.util.Scanner;

public class C02_DinicAlgorithm {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int cases = cin.nextInt();
        for (int i = 1; i <= cases; i++) {
            int nums = cin.nextInt();
            int start = cin.nextInt();
            int end = cin.nextInt();
            int m = cin.nextInt();
            Dinic dinic = new Dinic(nums);
            for (int j = 0; j < m; j++) {
                int from = cin.nextInt();
                int to = cin.nextInt();
                int weight = cin.nextInt();
                // 无向图是特殊的有向图，需要加两次边
                // 加上反向边，总共加4条边，AB(20),BA(0),BA(20),AB(0)，为0的是反向边
                dinic.addEdge(from, to, weight);
                dinic.addEdge(to, from, weight);
            }
            int ans = dinic.maxFlow(start, end);
            System.out.println("Case " + i + ": " + ans);
        }
        cin.close();
    }
}
