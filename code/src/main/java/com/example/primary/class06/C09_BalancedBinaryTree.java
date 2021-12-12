/**
 * 平衡搜索二叉树
 * 高度平衡二叉树定义为：一个二叉树每个节点的左右两个子树的高度差的绝对值不超过 1 。
 *    搜索二叉树定义为:一个二叉树每个节点的左树的最大值比节点的小,右树的最小值比节点的大
 *          方法1: 中序遍历的结果是递增的数列.
 *          方法2: 遍历
 */
package com.example.primary.class06;

import com.example.primary.class06.C07_ConstructBinaryTreeByTraversal.TreeNode;

public class C09_BalancedBinaryTree {
    public static class Info {
        // 节点的左右两个子树的高度差是否超过1,
        public boolean isBalanced;
        // 节点子树的高度.
        public int height;

        public boolean isValidBST;
        public int maxvalue;
        public int minvalue;

        public Info(boolean i, int h) {
            isBalanced = i;
            height = h;
        }

        public Info(boolean j, int min, int max) {
            isValidBST = j;
            maxvalue = max;
            minvalue = min;
        }
    }

    public static boolean isBalanced(TreeNode root) {
        return processBalance(root).isBalanced;
    }

    /**
     * 平衡二叉树
     * 
     * @param root
     * @return
     */
    public static Info processBalance(TreeNode root) {
        if (root == null) {
            return new Info(true, 0);
        }

        Info leftInfo = processBalance(root.left);
        Info rightInfo = processBalance(root.right);

        // 深度加当前的节点.所以加1.
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalanced = leftInfo.isBalanced && rightInfo.isBalanced
                && Math.abs(leftInfo.height - rightInfo.height) < 2;

        return new Info(isBalanced, height);
    }

    /**
     * 搜索二叉树
     * 节点的左子树只包含 小于 当前节点的数。
     * 节点的右子树只包含 大于 当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     * 
     * @param root
     * @return
     */
    public static Info processSearch(TreeNode root) {
        if (root == null) {
            // 最大值和最小值不能设为0或integer.MIN_VALUE.
            // return new Info(true, Integer.MIN_VALUE, Integer.MAX_VALUE);
            return null;
        }

        Info leftInfo = processSearch(root.left);
        Info rightInfo = processSearch(root.right);

        int minvalue = leftInfo == null ? root.val : leftInfo.minvalue;
        int maxvalue = rightInfo == null ? root.val : rightInfo.maxvalue;
        // boolean isValidBST = true;
        // if (leftInfo != null && (!leftInfo.isValidBST || leftInfo.maxvalue >
        // root.val)) {
        // isValidBST = false;
        // }
        // if(rightInfo != null && (!rightInfo.isValidBST || rightInfo.minvalue <
        // root.val)) {
        // isValidBST = false;
        // }

        boolean isValidBST = false;
        boolean leftIsBst = leftInfo == null ? true : leftInfo.isValidBST;
        boolean leftMaxLessX = leftInfo == null ? true : leftInfo.maxvalue > root.val;
        boolean rightIsBst = rightInfo == null ? true : rightInfo.isValidBST;
        boolean rightMinMoreX = rightInfo == null ? true : rightInfo.minvalue < root.val;
        if (leftIsBst && leftMaxLessX && rightIsBst && rightMinMoreX) {
            isValidBST = true;
        }

        return new Info(isValidBST, minvalue, maxvalue);
    }

    public static void main(String[] args) {
        TreeNode head1 = new TreeNode(5);
        head1.left = new TreeNode(1);
        head1.right = new TreeNode(4);
        head1.right.left = new TreeNode(3);
        head1.right.right = new TreeNode(6);

        System.out.println(processSearch(head1).isValidBST);
    }
}
