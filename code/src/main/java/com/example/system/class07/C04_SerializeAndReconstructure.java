/**
 * 二叉树的序列化和反序列化
 * 可以通过先序、后序或者按层遍历的方式序列化和反序列化，
 */
package com.example.system.class07;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.example.utility.entity.BinaryTreeNode;
import com.example.utility.helper.TreeTestHelper;

public class C04_SerializeAndReconstructure {
    /**
     * 先序的序列化
     */
    public static Queue<String> preSerial(BinaryTreeNode head) {
        Queue<String> ans = new LinkedList<>();
        pres(head, ans);
        return ans;
    }

    private static void pres(BinaryTreeNode head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    /**
     * 先序的反序列化
     */
    public static BinaryTreeNode bulidByPreQueue(Queue<String> preList) {
        if (preList == null || preList.isEmpty())
            return null;

        return preb(preList);
    }

    private static BinaryTreeNode preb(Queue<String> preList) {
        String value = preList.poll();
        if (value == null)
            return null;

        BinaryTreeNode head = new BinaryTreeNode(Integer.valueOf(value));
        head.left = preb(preList);
        head.right = preb(preList);
        return head;
    }

    /**
     * 后序的序列化
     */
    public static Queue<String> postSerial(BinaryTreeNode head) {
        Queue<String> queue = new LinkedList<>();
        post(head, queue);
        return queue;
    }

    private static void post(BinaryTreeNode head, Queue<String> list) {
        if (head == null) {
            list.add(null);
        } else {
            post(head.left, list);
            post(head.right, list);
            list.add(String.valueOf(head.value));
        }
    }

    /**
     * 后序的反序列化（将队列放入堆中，就是先序反序列化）
     */
    public static BinaryTreeNode postb(Queue<String> postList) {
        if (postList == null || postList.isEmpty())
            return null;

        // 左右中 -> stack(中右左)
        Stack<String> stack = new Stack<>();
        while (!postList.isEmpty()) {
            stack.push(postList.poll());
        }
        return postNode(stack);
    }

    private static BinaryTreeNode postNode(Stack<String> stack) {
        String val = stack.pop();
        if (val == null) {
            return null;
        }

        // 中右左
        BinaryTreeNode head = new BinaryTreeNode(Integer.valueOf(val));
        head.right = postNode(stack);
        head.left = postNode(stack);
        return head;
    }

    /**
     * 层级遍历的序列化，先序列化父节点，迭代序列化它的子节点
     */
    public static Queue<String> levelSerial(BinaryTreeNode head) {
        Queue<String> levelList = new LinkedList<>();
        if (head == null) {
            levelList.add(null);
        } else {
            levelList.add(String.valueOf(head.value));
            Queue<BinaryTreeNode> queue = new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()) {
                head = queue.poll();
                // 每个父节点，序列化它的子节点
                if (head.left != null) {
                    levelList.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                } else {
                    levelList.add(null);
                }

                if (head.right != null) {
                    levelList.add(String.valueOf(head.right.value));
                    queue.add(head.right);
                } else {
                    levelList.add(null);
                }
            }
        }
        return levelList;
    }

    /**
     * 层级遍历的序列化，序列化每个节点
     */
    public static Queue<String> levelSerial1(BinaryTreeNode head) {
        Queue<String> levelList = new LinkedList<>();
        if (head == null) {
            levelList.add(null);
        } else {
            Queue<BinaryTreeNode> queue = new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()) {
                head = queue.poll();
                if (head == null) {
                    levelList.add(null);
                } else {
                    levelList.add(String.valueOf(head.value));
                    queue.add(head.left);
                    queue.add(head.right);
                }
            }
        }
        return levelList;
    }

    /**
     * 层级遍历的反序列化
     */
    public static BinaryTreeNode levelBuild(Queue<String> levelList) {
        if (levelList == null || levelList.isEmpty())
            return null;

        BinaryTreeNode head = generateNode(levelList.poll());
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        // 序列化中，头节点可能是null
        if (head != null) {
            queue.add(head);
        }

        BinaryTreeNode node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            node.left = generateNode(levelList.poll());
            if (node.left != null) {
                queue.add(node.left);
            }
            node.right = generateNode(levelList.poll());
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return head;
    }

    private static BinaryTreeNode generateNode(String val) {
        if (val == null)
            return null;
        return new BinaryTreeNode(Integer.valueOf(val));
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxvalue = 100;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = TreeTestHelper.generateRandomBTS(maxLevel, maxvalue);

            Queue<String> preList = preSerial(head);
            Queue<String> postList = postSerial(head);
            Queue<String> levelList = levelSerial1(head);

            BinaryTreeNode preNode = preb(preList);
            BinaryTreeNode postNode = postb(postList);
            BinaryTreeNode levelNode = levelBuild(levelList);

            if (!TreeTestHelper.isSameValueStructure(preNode, postNode)
                    || !TreeTestHelper.isSameValueStructure(postNode, levelNode)) {
                System.out.println("Error");
                TreeTestHelper.printTree(preNode);
                TreeTestHelper.printTree(postNode);
                break;
            }
        }
        System.out.println("finished");
    }
}
