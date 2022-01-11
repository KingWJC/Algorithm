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
     * 最优解：时间复杂度O(m*n),m，n是数组的长和宽。
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
     * 并查集(集合实现)
     * 也是最优解，但常数时间大
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

        // 对只使用1个for循环来说，使用3个循环的常数时间短。
        // 因为这三个循环和一个循环执行的内容一样，但第三个循环少了对上和左位置是否有数据的判断。
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

    /**
     * 使用表的并查集
     */
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

        public int size() {
            return sizes.size();
        }
    }

    /**
     * 并查集(数组实现)
     * 常数时间变小
     */
    public static int numIslands3(char[][] board) {

    }

    /**
     * 将二维数组A展开，使用一维数组B来表示中每个元素，提取为1的位置。
     * 则：B.size = A.row*A.col; 元素在二维数组中的位置(i,j) 在一维数据的位置是 i*row+j
     * 
     * 局限性：数组的申请长度只能是整数类型，如果数据量大，可以使用一个Position对象封装（行号和列号）
     */
    public static class UnionFindArray {
        int[] parents;
        int[] sizes;
        int[] help;
        int col;
        int sets;

        public UnionFindArray(char[][] board) {
            int row = board.length;
            col = board[0].length;
            int N = row * col;
            parents = new int[N];
            sizes = new int[N];
            help = new int[N];

            sets = 0;
            int index = 0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (board[i][j] == '1') {
                        index = getIndex(i, j);
                        parents[index] = index;
                        sizes[index] = 1;
                        sets++;
                    }
                }
            }
        }

        private int getIndex(int i, int j) {
            return i * col + j;
        }

        private int find(int index) {
            int hi = 0;
            while (parents[index] != index) {
                help[hi++] = parents[index];
                index = parents[index];
            }

            for (hi--; hi >= 0; hi--) {
                parents[help[hi]] = index;
            }

            return index;
        }

        public void union(int r1, int c1, int r2, int c2) {
            int p1 = find(getIndex(r1, c1));
            int p2 = find(getIndex(r2, c2));
            if (p1 != p2) {
                int s1 = sizes[p1];
                int s2 = sizes[p2];
                if (s1 >= s2) {
                    parents[p2] = p1;
                    sizes[p1] = s1 + s2;
                } else {
                    parents[p1] = p2;
                    sizes[p2] = s1 + s2;
                }
                sets--;
            }
        }

        public int size() {
            return sets;
        }
    }
}
