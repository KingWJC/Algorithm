/**
 * 二叉树的递归套路:7.二叉树中最大的二叉搜索子树的头节点
 */
package com.example.system.class07;

import java.util.ArrayList;
import java.util.List;

import com.example.utility.entity.BinaryTreeNode;
import com.example.utility.helper.TreeTestHelper;

public class C15_MaxSubBSTHead {
    /**
     * 递归后序
     */
    public static BinaryTreeNode maxSubBSTHead(BinaryTreeNode head) {
        if (head == null)
            return null;
        return process(head).maxSubBSTHead;
    }

    public static class Info {
        int min;
        int max;
        int maxSubBSTSize;
        BinaryTreeNode maxSubBSTHead;

        public Info(int min, int max, int maxSubBSTSize, BinaryTreeNode maxSubBSTHead) {
            this.min = min;
            this.max = max;
            this.maxSubBSTHead = maxSubBSTHead;
            this.maxSubBSTSize = maxSubBSTSize;
        }
    }

    /**
     * 三种情况
     */
    private static Info process(BinaryTreeNode node) {
        if (node == null) {
            return null;
        }

        Info left = process(node.left);
        Info right = process(node.right);

        int max = node.value;
        int min = node.value;
        int maxSubBSTSize = -1;
        BinaryTreeNode maxSubBSTHead = null;
        // 左子树有最大二叉搜索树
        if (left != null) {
            max = Math.max(left.max, max);
            min = Math.min(left.min, min);
            maxSubBSTSize = left.maxSubBSTSize;
            maxSubBSTHead = left.maxSubBSTHead;
        }
        // 右子树有最大二叉搜索树
        if (right != null) {
            max = Math.max(right.max, max);
            min = Math.min(right.min, min);
            // maxSubBSTSize相同时,优先左节点
            if (right.maxSubBSTSize > maxSubBSTSize) {
                maxSubBSTSize = right.maxSubBSTSize;
                maxSubBSTHead = right.maxSubBSTHead;
            }
        }

        // 当前树是二叉搜索树，其左右子树都是二叉搜索树
        boolean leftIsBST = left == null || left.maxSubBSTHead == node.left;
        boolean rightIsBST = right == null || right.maxSubBSTHead == node.right;
        if (leftIsBST && rightIsBST) {
            boolean leftMaxLessX = left == null || left.max < node.value;
            boolean rightMinMoreX = right == null || right.min > node.value;
            if (leftMaxLessX && rightMinMoreX) {
                int leftSize = left == null ? 0 : left.maxSubBSTSize;
                int rightSize = right == null ? 0 : right.maxSubBSTSize;
                maxSubBSTSize = leftSize + rightSize + 1;
                maxSubBSTHead = node;
            }
        }
        return new Info(min, max, maxSubBSTSize, maxSubBSTHead);
    }

    /**
     * 对数器
     * 先序递归遍历，找到的最高层的二叉搜索树，就是最大二叉搜素树
     */
    public static BinaryTreeNode maxSubBSTHeadTest(BinaryTreeNode head) {
        if (head == null)
            return null;

        int maxSubBSTSize = getBSTSize(head);
        if (maxSubBSTSize > 0) {
            return head;
        }

        BinaryTreeNode leftNode = maxSubBSTHeadTest(head.left);
        BinaryTreeNode rightNode = maxSubBSTHeadTest(head.right);
        int leftSize = getBSTSize(leftNode);
        int rightSize = getBSTSize(rightNode);
        // maxSubBSTSize相同时,优先左节点
        if (leftSize >= rightSize) {
            return leftNode;
        } else {
            return rightNode;
        }
    }

    private static int getBSTSize(BinaryTreeNode head) {
        List<BinaryTreeNode> arr = new ArrayList<>();
        inorder(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value)
                return 0;
        }
        return arr.size();
    }

    private static void inorder(BinaryTreeNode head, List<BinaryTreeNode> arr) {
        if (head == null)
            return;

        inorder(head.left, arr);
        arr.add(head);
        inorder(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = TreeTestHelper.generateRandomBTS(maxLevel,
                    maxValue);
            BinaryTreeNode node1 = maxSubBSTHead(head);
            BinaryTreeNode node2 = maxSubBSTHeadTest(head);
            if (node1 != node2) {
                TreeTestHelper.printTree(head);
                System.out.println(node1.value + " error " + node2.value);
                break;
            }
        }
        System.out.println("finished");
    }
}
