/**
 * 判断二叉树是不是搜索二叉树
 */
package com.example.system.class07;

import java.util.ArrayList;

import com.example.utility.entity.BinaryTreeNode;
import com.example.utility.helper.TreeNodeTestHelper;

public class C10_IsBST {
    public static boolean isBST(BinaryTreeNode head) {
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }

    /**
     * 左右树返回参数要求不一样, 使用全集
     */
    private static Info process(BinaryTreeNode x) {
        if (x == null) {
            // 空树不好设置, 没有最大,最小的值. 返回null, 由上游处理空情况.
            return null;
        }

        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        boolean isBst = true;
        if (leftInfo != null && (!leftInfo.isBST || leftInfo.max >= x.value)) {
            isBst = false;
        }
        if (rightInfo != null && (!rightInfo.isBST || rightInfo.min <= x.value)) {
            isBst = false;
        }

        int max = x.value;
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
        }

        int min = x.value;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
        }

        return new Info(min, max, isBst);
    }

    public static class Info {
        int min;
        int max;
        boolean isBST;

        public Info(int min, int max, boolean b) {
            isBST = b;
            this.min = min;
            this.max = max;
        }
    }

    /**
     * 对数器
     * 使用中序遍历, 生成数组, 若数组是从小到大排序的,则是搜索二叉树
     */
    public static boolean isBSTUseInorder(BinaryTreeNode head) {
        if (head == null) {
            return true;
        }

        ArrayList<BinaryTreeNode> arr = new ArrayList<>();
        inorder(head, arr);

        boolean isBst = true;
        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i).value >= arr.get(i + 1).value) {
                isBst = false;
                break;
            }
        }
        return isBst;
    }

    private static void inorder(BinaryTreeNode head, ArrayList<BinaryTreeNode> arr) {
        if (head == null)
            return;

        inorder(head.left, arr);
        arr.add(head);
        inorder(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = TreeNodeTestHelper.generateRandomBTS(maxLevel, maxValue);
            if (isBST(head) != isBSTUseInorder(head)) {
                System.out.println("Oops! isBST=" + isBST(head) + "  isBSTUseInorder=" + isBSTUseInorder(head));
                TreeNodeTestHelper.printTree(head);
                break;
            }
        }
        System.out.println("finish!");
    }
}
