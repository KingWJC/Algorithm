/**
 * 岛屿数量|岛问题: 给定一个二维数组matrix，里面的值不是1就是0，
 * 上、下、左、右相邻的1认为是一片岛，返回matrix中岛的数量.
 */
package com.example.system.class09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class C02_NumberOfIslands {
    /**
     * 递归遍历标记
     * 时间复杂度O(m*n),m，n是数组的长和宽。
     * 递归是m*n次，感染是4*m*n,因为每个位置最多被上下左右的邻居碰4遍。
     */
    public static int numIslands1(char[][] board) {
        int island = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == '1') {
                    island++;
                    infect(board, i, j);
                }
            }
        }
        return island;
    }

    /**
     * 感染：把所有连成一片的'1'字符重新标记。
     * 不能只查右下,否则左下部分无法感染。
     * 不能不查上，否则U型岛的左上部分无法感染。
     */
    private static void infect(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != '1') {
            return;
        }

        // 把'1'字符，变成ASSIC为0的字符。不改的话是死循环。
        board[i][j] = 0;
        // 递归判断左右上下的字符。
        infect(board, i - 1, j);
        infect(board, i + 1, j);
        infect(board, i, j - 1);
        infect(board, i, j + 1);

    }

    /**
     * 并查集
     */
    public static int numIslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> values = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '1') {
                    dots[i][j] = new Dot();
                    values.add(dots[i][j]);
                }
            }
        }
        UnionFindTable uFind = new UnionFindTable(values);

        for (int i = 1; i < row; i++) {
            if (dots[i][0] != null && dots[i - 1][0] != null)
                uFind.union(dots[i][0], dots[i - 1][0]);
        }

        for (int j = 1; j < col; j++) {
            if (board[0][j] == '1' && board[0][j - 1] == '1')
                uFind.union(dots[0][j], dots[0][j]);
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i - 1][j] == '1')
                        uFind.union(dots[i][j], dots[i - 1][j]);
                    if (board[i][j - 1] == '1')
                        uFind.union(dots[i][j], dots[i][j - 1]);

                }
            }
        }

        return uFind.size();
    }

    /**
     * 代替字符，每个位置的数据都是不一样胡引用。
     */
    public static class Dot {
    }

    public static class UnionFindTable {
        HashMap<Dot, Dot> parents;
        HashMap<Dot, Integer> sizes;
        HashSet<Dot> nodes;

        public UnionFindTable(List<Dot> values) {
            parents = new HashMap<>();
            sizes = new HashMap<>();
            nodes = new HashSet<>();
            for (Dot d : values) {
                nodes.add(d);
                parents.put(d, d);
                sizes.put(d, 1);
            }
        }

        private Dot find(Dot val) {
            Stack<Dot> stack = new Stack<>();
            while (parents.get(val) != val) {
                stack.push(val);
                val = parents.get(val);
            }

            while (!stack.isEmpty()) {
                parents.put(stack.pop(), val);
            }

            return val;
        }

        public void union(Dot a, Dot b) {
            Dot p1 = find(a);
            Dot p2 = find(b);
            if (p1 != p2) {
                int size1 = sizes.get(p1);
                int size2 = sizes.get(p2);
                Dot father = size1 >= size2 ? p1 : p2;
                Dot child = father == p1 ? p2 : p1;
                parents.put(child, father);
                sizes.put(father, size1 + size2);
                sizes.remove(child);
            }
        }

        public int size()
        {
            return sizes.size();
        }
    }

}
