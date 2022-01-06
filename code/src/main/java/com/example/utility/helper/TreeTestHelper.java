/**
 * 对数器
 * 二叉树的测试帮助类
 */
package com.example.utility.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.utility.entity.BinaryTreeNode;

public class TreeTestHelper {
    /**
     * 生成随机二叉树
     */
    public static BinaryTreeNode generateRandomBTS(int maxLevel, int maxValue) {
        return generateNode(1, maxLevel, maxValue);
    }

    private static BinaryTreeNode generateNode(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.25) {
            return null;
        }

        BinaryTreeNode head = new BinaryTreeNode((int) (Math.random() * maxValue));
        head.left = generateNode(level + 1, maxLevel, maxValue);
        head.right = generateNode(level + 1, maxLevel, maxValue);
        return head;
    }

    /**
     * 判断两个二叉树的结构和值，是否相同。
     */
    public static boolean isSameValueStructure(BinaryTreeNode node1, BinaryTreeNode node2) {
        if (node1 == null && node2 == null)
            return true;
        if (node1 == null ^ node2 == null)
            return false;
        // 判断节点值是否相同
        if (node1.value != node2.value)
            return false;

        boolean leftFlag = isSameValueStructure(node1.left, node2.left);
        boolean rightFlag = isSameValueStructure(node1.right, node2.right);
        return leftFlag & rightFlag;
    }

    /**
     * 打印整棵树（横向）
     */
    public static void printTree(BinaryTreeNode head) {
        printInOrder(head, 0, "H", 17);
    }

    /**
     * 中序遍历
     * 
     * @param head   节点
     * @param height 节点高度
     * @param to     代表根节点，左节点和右节点的标识
     * @param len    节点的宽度
     */
    private static void printInOrder(BinaryTreeNode head, int height, String to, int len) {
        if (head == null)
            return;

        printInOrder(head.right, height + 1, "u", len);

        // 节点显示宽度是len，让每个节点的值，显示在中间。
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpaceString(lenL) + val + getSpaceString(lenR);
        System.out.println(getSpaceString(height * len) + val);

        printInOrder(head.left, height + 1, "n", len);
    }

    /**
     * 获取指定长度的空格字符串
     */
    private static String getSpaceString(int num) {
        String space = " ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(space);
        }
        return sb.toString();
    }

    /**
     * 获取树的随机节点
     */
    public static BinaryTreeNode pickRandomOne(BinaryTreeNode head) {
        if (head == null)
            return null;

        List<BinaryTreeNode> arr = new ArrayList<>();
        preorder(head, arr);
        int index = (int) (Math.random() * arr.size());
        return arr.get(index);
    }

    private static void preorder(BinaryTreeNode head, List<BinaryTreeNode> arr) {
        if (head == null)
            return;

        arr.add(head);
        preorder(head.left, arr);
        preorder(head.right, arr);
    }
}
