/**
 * 二叉树: 2.二叉树的迭代先、序、后序遍历
 */
package com.example.system.class07;

import java.util.Stack;

import com.example.utility.entity.BinaryTreeNode;

public class C02_UnRecursiveTraversalBT {
    /**
     * 先序
     */
    public static void pre(BinaryTreeNode head) {
        if (head != null) {
            // 后进先出
            Stack<BinaryTreeNode> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                var node = stack.pop();
                System.out.print(node.value + ",");
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
            }
        }

    }

    /**
     * 中序
     * 重点: 整棵树的所有节点,都可以被子树的左边界分解掉.
     * 先放入cur节点的左边界的节点入栈, 直到遇到空
     * 栈中弹出节点打印, 节点右孩子为cur
     * 栈为空停
     */
    public static void in(BinaryTreeNode cur) {
        if (cur != null) {
            Stack<BinaryTreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || cur != null) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    System.out.print(cur.value + ",");
                    cur = cur.right;
                }
            }
        }
    }

    /**
     * 后序: 两个栈
     * 先序改为头右左放入另一个栈中, 再弹出就变成后序.
     */
    public static void post(BinaryTreeNode head) {
        if (head != null) {
            Stack<BinaryTreeNode> stack = new Stack<>();
            Stack<BinaryTreeNode> help = new Stack<>();
            stack.push(head);
            // 头 右 左
            while (!stack.isEmpty()) {
                var node = stack.pop();
                help.push(node);
                if (node.left != null) {
                    stack.push(node.left);
                }
                if (node.right != null) {
                    stack.push(node.right);
                }
            }

            // 左 右 头
            while (!help.isEmpty()) {
                var node = help.pop();
                System.out.print(node.value + ",");
            }
        }
    }

    /**
     * 后序: 一个栈
     */
    public static void postNew(BinaryTreeNode head) {
        if (head != null) {
            Stack<BinaryTreeNode> stack = new Stack<>();
            stack.push(head);
            BinaryTreeNode cur = null;
            while (!stack.isEmpty()) {
                cur = stack.peek();
                if (cur.left != null && cur.left != head && cur.right != head) {
                    stack.push(cur.left);
                } else if (cur.right != null && cur.right != head) {
                    stack.push(cur.right);
                } else {
                    cur = stack.pop();
                    System.out.print(cur.value + ",");
                    head = cur;
                }
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

        System.out.println("先序:");
        pre(head);
        System.out.println("========");
        System.out.println("中序:");
        in(head);
        System.out.println("========");
        System.out.println("后序:");
        post(head);
        System.out.println("========");
        postNew(head);
        System.out.println("========");
    }
}
