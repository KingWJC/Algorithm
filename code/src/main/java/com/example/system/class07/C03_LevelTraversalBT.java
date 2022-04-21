/**
 * 二叉树: 3.实现二叉树的按层遍历
 */
package com.example.system.class07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.example.utility.entity.BinaryTreeNode;

public class C03_LevelTraversalBT {
    /**
     * 宽度优先遍历，用队列存储每层的节点
     */
    public static void level(BinaryTreeNode head) {
        if (head == null)
            return;

        // 一边按层的顺序存储节点, 一边打印最早放入的节点.
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            var BinaryTreeNode = queue.poll();
            System.out.print(BinaryTreeNode.value + ",");

            if (BinaryTreeNode.left != null) {
                queue.add(BinaryTreeNode.left);
            }

            if (BinaryTreeNode.right != null) {
                queue.add(BinaryTreeNode.right);
            }
        }
        System.out.println();
    }

    /**
     * 按层打印，增加变量，记录当前层和下一层的结束节点
     */
    public static void levelPrint1(BinaryTreeNode head) {
        if (head == null) {
            return;
        }
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(head);
        BinaryTreeNode curLast = head;
        BinaryTreeNode nextLast = null;
        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();
            System.out.print(node.value + " ");
            if (node.left != null) {
                queue.add(node.left);
                nextLast = node.left;
            }
            if (node.right != null) {
                queue.add(node.right);
                nextLast = node.right;
            }
            // 判断是否到达当前层的结束节点，开始换行
            if (node == curLast) {
                curLast = nextLast;
                System.out.println();
            }
        }
    }

    /**
     * 按层打印，增加变量，记录当前层和下一层的节点数量
     */
    public static void levelPrint2(BinaryTreeNode head) {
        if (head == null) {
            return;
        }
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(head);
        int curSize = 1;
        int nextSize = 0;
        while (!queue.isEmpty()) {
            while (curSize > 0) {
                curSize--;
                BinaryTreeNode node = queue.poll();
                System.out.print(node.value + " ");
                if (node.left != null) {
                    queue.add(node.left);
                    nextSize++;
                }
                if (node.right != null) {
                    queue.add(node.right);
                    nextSize++;
                }
            }
            curSize = nextSize;
            nextSize = 0;
            System.out.println();
        }
    }

    /**
     * 按层打印，增加变量，记录当前层深度，设置每个节点的深度
     */
    public static void levelPrint3(BinaryTreeNode head) {
        if (head == null) {
            return;
        }
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        int curDepth = 1;
        head.depth = 1;
        queue.add(head);
        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();

            if (node.depth > curDepth) {
                curDepth++;
                System.out.println();
            }

            System.out.print(node.value + " ");
            if (node.left != null) {
                node.left.depth = curDepth + 1;
                queue.add(node.left);
            }
            if (node.right != null) {
                node.right.depth = curDepth + 1;
                queue.add(node.right);
            }
        }
    }

    /**
     * 递归，从左到右的尝试模型
     */
    public static List<List<Integer>> levelPrint4(BinaryTreeNode head) {
        if (head == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> ans = new ArrayList<>();
        process(head, 1, ans);
        return ans;
    }

    private static void process(BinaryTreeNode node, int depth, List<List<Integer>> list) {
        if (node == null) {
            return;
        }

        if (depth > list.size()) {
            list.add(new ArrayList<>());
        }
        list.get(depth - 1).add(node.value);

        process(node.left, depth + 1, list);
        process(node.right, depth + 1, list);
    }

    public static void main(String[] args) {
        BinaryTreeNode head = new BinaryTreeNode(1);
        head.left = new BinaryTreeNode(2);
        head.right = new BinaryTreeNode(3);
        head.left.left = new BinaryTreeNode(4);
        head.left.right = new BinaryTreeNode(5);
        head.right.left = new BinaryTreeNode(6);
        head.right.right = new BinaryTreeNode(7);

        level(head);
        System.out.println("==================");
        levelPrint1(head);
        System.out.println("==================");
        levelPrint2(head);
        System.out.println("==================");
        levelPrint3(head);
        System.out.println("==================");
        List<List<Integer>> ans = levelPrint4(head);
        for (int i = 0; i < ans.size(); i++) {
            System.out.println(Arrays.toString(ans.get(i).toArray()));
        }
    }
}
