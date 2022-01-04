/**
 * 求二叉树最宽的层有多少个节点
 */
package com.example.system.class07;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.example.utility.entity.BinaryTreeNode;
import com.example.utility.helper.TreeNodeTestHelper;

public class C06_TreeMaxWidth {
    /**
     * 使用有限的变量，建立每一层结束的机制。
     */
    public static int maxWidthNoMap(BinaryTreeNode root) {
        int maxWidth = 0;
        if (root != null) {
            Queue<BinaryTreeNode> queue = new LinkedList<>();
            queue.add(root);
            int curWidth = 0;// 当前层的节点数
            BinaryTreeNode curEnd = root; // 当前层，最右节点是谁
            BinaryTreeNode nextEnd = null; // 下一层，最右节点是谁
            while (!queue.isEmpty()) {
                BinaryTreeNode node = queue.poll();

                // 准备好下一层节点数据
                if (node.left != null) {
                    queue.add(node.left);
                    nextEnd = node.left;
                }
                if (node.right != null) {
                    queue.add(node.right);
                    nextEnd = node.right;
                }

                curWidth++;

                // 当前层到达最右侧，进入下一层
                // （当前层的最右节点的子节点，是下一层的最右节点）
                if (node == curEnd) {
                    maxWidth = Math.max(maxWidth, curWidth);
                    curWidth = 0;
                    curEnd = nextEnd;
                }
            }
        }
        return maxWidth;
    }

    /**
     * 使用Map存储每个节点在哪一层，使用当前层与队列弹出的节点所对应的层来确认当前层是否结束
     */
    public static int maxWidthMap(BinaryTreeNode root) {
        int maxWidth = 0;
        if (root != null) {
            Queue<BinaryTreeNode> queue = new LinkedList<>();
            queue.add(root);

            int curLevelNodes = 0;
            int curLevel = 1;
            HashMap<BinaryTreeNode, Integer> map = new HashMap<>();
            map.put(root, curLevel);

            while (!queue.isEmpty()) {
                BinaryTreeNode node = queue.poll();
                int nodeLevel = map.get(node);
                if (node.left != null) {
                    queue.add(node.left);
                    map.put(node.left, curLevel + 1);
                }
                if (node.right != null) {
                    queue.add(node.right);
                    map.put(node.right, curLevel + 1);
                }

                if (curLevel == nodeLevel) {
                    curLevelNodes++;
                } else {
                    maxWidth = Math.max(maxWidth, curLevelNodes);
                    // 下一层第一个节点已弹出。
                    curLevelNodes = 1;
                    curLevel++;
                }
            }
        }
        return maxWidth;
    }

    public static void main(String[] args) {
        int maxLevel = 6;
        int maxValue = 100;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = TreeNodeTestHelper.generateRandomBTS(maxLevel, maxValue);
            if (maxWidthMap(head) != maxWidthNoMap(head)) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finished");
    }
}
