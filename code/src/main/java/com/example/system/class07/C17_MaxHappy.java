/**
 * 给定一棵多叉树的头节点X，请返回派对的最大快乐值。
 */
package com.example.system.class07;

import com.example.utility.entity.NaryTreeNode;
import com.example.utility.helper.TreeTestHelper;

public class C17_MaxHappy {
    /**
     * 递归套路，封装
     */
    public static int getMaxHappy(NaryTreeNode head) {
        Info ans = process(head);
        return Math.max(ans.withX, ans.withoutX);
    }

    /**
     * 包含头节点X时的最大快乐值
     * 不包含头节点X时的最大快乐值
     */
    public static class Info {
        int withX;
        int withoutX;

        public Info(int withX, int withoutX) {
            this.withX = withX;
            this.withoutX = withoutX;
        }
    }

    public static Info process(NaryTreeNode X) {
        if (X == null)
            return new Info(0, 0);

        int withX = X.val;
        int withoutX = 0;
        for (NaryTreeNode node : X.children) {
            Info info = process(node);
            withX += info.withoutX;
            withoutX += Math.max(info.withX, info.withoutX);
        }

        return new Info(withX, withoutX);
    }

    /**
     * 对数器
     * 递归多参数
     */
    public static int getMaxHappyUseList(NaryTreeNode head) {
        if (head == null)
            return 0;

        // 根节点没有父节点，所以with为false
        return process(head, false);
    }

    /**
     * 当前来到的节点叫cur
     * with: 对于当前节点head，是否包含上级节点的值
     * 为true，表示在cur上级已经确定来
     * 为false，表示在cur上级已经确定不来
     */
    private static int process(NaryTreeNode cur, boolean with) {
        if (with) {
            int ans = 0;
            for (NaryTreeNode node : cur.children) {
                ans += process(node, false);
            }
            return ans;
        } else { // 如果cur的上级不来的话，cur可以选，可以来也可以不来
            // 对于子节点，包含当前节点
            int withX = cur.val;
            // 对于子节点，不包含当前节点
            int withoutX = 0;
            for (NaryTreeNode node : cur.children) {
                withX += process(node, true);
                withoutX += process(node, false);
            }
            return Math.max(withX, withoutX);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNext = 6;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            NaryTreeNode head = TreeTestHelper.generateRandomNT(maxLevel, maxValue, maxNext);
            int max1 = getMaxHappy(head);
            int max2 = getMaxHappyUseList(head);
            if (max1 != max2) {
                System.out.println(max1 + " error " + max2);
                break;
            }
        }
        System.out.println("finished");
    }
}
