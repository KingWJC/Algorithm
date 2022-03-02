/**
 * 根据身高重建队列
 * 链接：https://leetcode.com/problems/queue-reconstruction-by-height/
 */
package com.example.system.class23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class C07_QueueReconstructByHeight {
    /**
     * ArrayList结构
     */
    public static int[][] reconstructQueue1(int[][] people) {
        int N = people.length;
        Unit[] units = new Unit[N];
        for (int i = 0; i < N; i++) {
            units[i] = new Unit(people[i][0], people[i][1]);
        }
        Arrays.sort(units, new UnitComparapor());
        ArrayList<Unit> list = new ArrayList<>();
        for (Unit u : units) {
            list.add(u.k, u);
        }

        int[][] ans = new int[N][2];
        int index = 0;
        for (Unit u : list) {
            ans[index][0] = u.h;
            ans[index][1] = u.k;
            index++;
        }
        return ans;
    }

    /**
     * SizeBlanlenceTree结构
     */
    public static int[][] reconstructQueue2(int[][] people) {
        return null;
    }

    /**
     * 封装：身高h，前面有k个人大于等于h
     */
    private static class Unit {
        public int h;
        public int k;

        public Unit(int height, int greater) {
            h = height;
            k = greater;
        }
    }

    /**
     * 比较器：先按h倒序，若h相同，则按k顺序。
     */
    private static class UnitComparapor implements Comparator<Unit> {
        @Override
        public int compare(Unit o1, Unit o2) {
            return o1.h != o2.h ? o2.h - o1.h : o1.k - o2.k;
        }
    }

    public static void main(String[] args) {
        int[][] people = { { 7, 0 }, { 4, 4 }, { 7, 1 }, { 5, 0 }, { 6, 1 }, { 5, 2 } };
        int[][] ans = reconstructQueue1(people);
        printArray(ans);
    }

    private static void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(Arrays.toString(arr[i]) + ",");
        }
    }
}
