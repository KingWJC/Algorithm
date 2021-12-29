/**
 * 链表问题：使用快慢指针方法
 * 通过快慢指针的初始位置，控制指针移动范围，来获取想要的最终位置
 */
package com.example.system.class06;

import java.util.ArrayList;
import java.util.List;

import com.example.utility.entity.ListNode;

public class C01_LinkedListMid {
    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
     */
    public static ListNode midOrUpMidNode(ListNode head) {
        // 链表有2个节点或以上
        // if (head == null || head.next == null)
        // return head;

        // ListNode slow = head;
        // ListNode fast = head;
        // while (fast != null && fast.next != null) {
        // fast = fast.next.next;
        // if (fast != null) {
        // slow = slow.next;
        // }
        // }

        // 链表有3个节点或以上
        if (head == null || head.next == null || head.next.next == null)
            return head;
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
     */
    public static ListNode midOrDownMidNode(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode slow = head.next;
        ListNode fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
     */
    public static ListNode midOrUpMidPreNode(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return null;

        ListNode slow = head;
        ListNode fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
     */
    public static ListNode midOrDownMidPreNode(ListNode head) {
        if (head == null || head.next == null)
            return null;
        if (head.next.next == null)
            return head;

        ListNode slow = head;
        ListNode fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * 使用容器查找，
     */
    public static ListNode useList1(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode cur = head;
        List<ListNode> list = new ArrayList<>();
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }

        // 索引位置从0开始，需要用size-1.
        return list.get((list.size() - 1) / 2);
    }

    public static ListNode useList2(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode cur = head;
        List<ListNode> list = new ArrayList<>();
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }

        return list.get(list.size() / 2);
    }

    public static ListNode useList3(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return null;

        ListNode cur = head;
        List<ListNode> list = new ArrayList<>();
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }

        // 索引位置从0开始，需要用size-1.
        return list.get((list.size() - 1) / 2 - 1);
    }

    public static ListNode useList4(ListNode head) {
        if (head == null || head.next == null)
            return null;

        ListNode cur = head;
        List<ListNode> list = new ArrayList<>();
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }

        // 索引位置从0开始，需要用size-1.
        return list.get(list.size() / 2 - 1);
    }

    public static void main(String[] args) {
        ListNode head = null;
        head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next.next = new ListNode(6);
        head.next.next.next.next.next.next.next = new ListNode(7);
        head.next.next.next.next.next.next.next.next = new ListNode(8);

        ListNode ans1 = null;
        ListNode ans2 = null;

        ans1 = midOrUpMidNode(head);
        ans2 = useList1(head);
        System.out.println(ans1 != null ? ans1.val : "null");
        System.out.println(ans2 != null ? ans2.val : "null");

        ans1 = midOrDownMidNode(head);
        ans2 = useList2(head);
        System.out.println(ans1 != null ? ans1.val : "null");
        System.out.println(ans2 != null ? ans2.val : "null");

        ans1 = midOrUpMidPreNode(head);
        ans2 = useList3(head);
        System.out.println(ans1 != null ? ans1.val : "null");
        System.out.println(ans2 != null ? ans2.val : "null");

        ans1 = midOrDownMidPreNode(head);
        ans2 = useList4(head);
        System.out.println(ans1 != null ? ans1.val : "null");
        System.out.println(ans2 != null ? ans2.val : "null");
    }
}