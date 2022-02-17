/**
 * Morris遍历：先序，中序，后序。
 */
package com.example.system.class18;

import com.example.utility.entity.BinaryTreeNode;

public class C01_MorrisTranversal {
    /**
     * 递归序
     */
    public static void process(BinaryTreeNode root) {
        if (root == null) {
            return;
        }

        // 先序
        System.out.println(root.value);
        process(root.left);
        // 中序
        // System.out.println(root.value);
        process(root.right);
        // 后序
        // System.out.println(root.value);
    }

    /**
     * Morris遍历
     */
    public static void morris(BinaryTreeNode root) {
        if (root == null) {
            return;
        }

        BinaryTreeNode cur = root;
        BinaryTreeNode mostRight = null;
        while (cur != null) {
            System.out.print(cur.value + ",");
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            cur = cur.right;
        }
    }

    /**
     * Morris先序
     */
    public static void morrisPre(BinaryTreeNode root) {
        if (root == null) {
            return;
        }

        BinaryTreeNode cur = root;
        BinaryTreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    System.out.print(cur.value + ",");
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else if (mostRight.right == cur) {
                    mostRight.right = null;
                }
            } else {
                System.out.print(cur.value + ",");
            }
            cur = cur.right;
        }
    }

    /**
     * Morris中序
     */
    public static void morrisIn(BinaryTreeNode root) {
        if (root == null) {
            return;
        }
        BinaryTreeNode cur = root;
        BinaryTreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else if (mostRight.right == cur) {
                    mostRight.right = null;
                }
            }
            System.out.print(cur.value + ",");
            cur = cur.right;
        }
    }

    /**
     * Morris后序
     */
    public static void morrisPos(BinaryTreeNode root) {
        if (root == null) {
            return;
        }

        BinaryTreeNode cur = root;
        BinaryTreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        printEdge(root);
    }

    /**
     * 打印节点的右边界
     */
    private static void printEdge(BinaryTreeNode head) {
        BinaryTreeNode tail = reverseEdge(head);
        BinaryTreeNode cur = tail;
        while (cur != null) {
            System.out.print(cur.value + ",");
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    /**
     * 反转右边界链表
     */
    private static BinaryTreeNode reverseEdge(BinaryTreeNode from) {
        BinaryTreeNode pre = null;
        BinaryTreeNode next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        BinaryTreeNode head = new BinaryTreeNode(4);
        head.left = new BinaryTreeNode(2);
        head.right = new BinaryTreeNode(6);
        head.left.left = new BinaryTreeNode(1);
        head.left.right = new BinaryTreeNode(3);
        head.right.left = new BinaryTreeNode(5);
        head.right.right = new BinaryTreeNode(7);
        morris(head);
        System.out.println("Morris遍历");
        morrisPre(head);
        System.out.println("先序");
        morrisIn(head);
        System.out.println("中序");
        morrisPos(head);
        System.out.println("后序");
        printTree(head);
    }

    private static void printTree(BinaryTreeNode head) {
        System.out.println("binary tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    private static void printInOrder(BinaryTreeNode head, int height, String to, int length) {
        if (head == null) {
            return;
        }

        printInOrder(head.right, height + 1, "v", length);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (length - lenM) / 2;
        int lenR = length - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * length) + val);
        printInOrder(head.left, height + 1, "^", length);
    }

    private static String getSpace(int num) {
        String space = " ";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            sb.append(space);
        }
        return sb.toString();
    }
}
