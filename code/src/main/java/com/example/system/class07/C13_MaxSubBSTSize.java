/**
 * 二叉树中最大的二叉搜索子树的大小(节点个数)
 */
package com.example.system.class07;

import java.util.ArrayList;

import com.example.utility.entity.BinaryTreeNode;
import com.example.utility.helper.TreeTestHelper;

public class C13_MaxSubBSTSize {
    /**
     * 递归遍历每一个子树(后序)
     */
    public static int getMaxBSTSize(BinaryTreeNode head) {
        if (head == null)
            return 0;
        return process(head).maxSubBSTSize;
    }

    public static class Info {
        int max;
        int min;
        int allSize;
        int maxSubBSTSize;

        public Info(int max, int min, int allSize, int maxSubBSTSize) {
            this.min = min;
            this.max = max;
            this.allSize = allSize;
            this.maxSubBSTSize = maxSubBSTSize;
        }
    }

    private static Info process(BinaryTreeNode node) {
        if (node == null) {
            return null;
        }

        Info left = process(node.left);
        Info right = process(node.right);

        int min = node.value;
        int max = node.value;
        int allSize = 1;
        if (left != null) {
            min = Math.min(left.min, min);
            max = Math.max(left.max, max);
            allSize += left.allSize;
        }
        if (right != null) {
            min = Math.min(right.min, min);
            max = Math.max(right.max, max);
            allSize += right.allSize;
        }
        // 左子树有最大的二叉搜索子树
        int p1 = -1;
        if (left != null) {
            p1 = left.maxSubBSTSize;
        }
        // 右子树有最大的二叉搜索子树
        int p2 = -1;
        if (right != null) {
            p2 = right.maxSubBSTSize;
        }
        // 当前节点是二叉搜索子树
        int p3 = -1;
        // 左右子树都是二叉搜索子树
        boolean leftBST = left == null || left.allSize == left.maxSubBSTSize;
        boolean rightBST = right == null || right.allSize == right.maxSubBSTSize;
        if (leftBST && rightBST) {
            // 若它的左子树不空，则左子树上所有结点的最大值小于它的根结点的值；
            // 若它的右子树不空，则右子树上所有结点的最小值大于它的根结点的值；
            boolean leftMaxLessX = left == null || left.max < node.value;
            boolean rightMinMoreX = right == null || right.min > node.value;
            if (leftMaxLessX && rightMinMoreX) {
                int leftSize = left == null ? 0 : left.allSize;
                int rightSize = right == null ? 0 : right.allSize;
                // p3必地重新赋值, 不用+=.++
                p3 = leftSize + rightSize + 1;
            }
        }

        return new Info(max, min, allSize, Math.max(p1, Math.max(p2, p3)));
    }

    /**
     * 对数器
     * 从根节点开始,判断是否为二叉搜索树(先序)
     */
    public static int getMaxBSTSizeUseList(BinaryTreeNode head) {
        if (head == null)
            return 0;

        int maxSubBTSSize = getBSTSize(head);
        // 如果当前节点是二叉搜索树,直接返回
        if (maxSubBTSSize > 0)
            return maxSubBTSSize;

        return Math.max(getMaxBSTSizeUseList(head.left), getMaxBSTSizeUseList(head.right));
    }

    /**
     * 中序遍历判断是否为二叉搜索树
     */
    private static int getBSTSize(BinaryTreeNode node) {
        ArrayList<BinaryTreeNode> arr = new ArrayList<>();
        inorder(node, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value)
                return 0;
        }
        return arr.size();
    }

    private static void inorder(BinaryTreeNode node, ArrayList<BinaryTreeNode> arr) {
        if (node == null)
            return;

        inorder(node.left, arr);
        arr.add(node);
        inorder(node.right, arr);
    }

    public static void main(String[] args) {
        int maxValue = 100;
        int maxLevel = 5;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
        BinaryTreeNode head = TreeTestHelper.generateRandomBTS(maxLevel,
        maxValue);
        int size1 = getMaxBSTSize(head);
        int size2 = getMaxBSTSizeUseList(head);
        if (size1 != size2) {
        System.out.println(size1 + " error " + size2);
        TreeTestHelper.printTree(head);
        break;
        }
        }
        System.out.println("finish");
    }
}
