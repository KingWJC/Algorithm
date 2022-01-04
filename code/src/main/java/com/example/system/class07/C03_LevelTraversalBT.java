/**
 * 实现二叉树的按层遍历
 */
package com.example.system.class07;

import java.util.LinkedList;
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
    }
}
