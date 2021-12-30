/**
 * 两个可能有环的单链表相交的第一个节点
 */
package com.example.system.class06;

import com.example.utility.entity.ListNode;

public class C05_FindFirstIntersectNode {
    /**
     * 获取单链表相交的第一个节点
     */
    public static ListNode getIntersectNode(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        ListNode loop1 = getLoopNode(head1);
        ListNode loop2 = getLoopNode(head2);

        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        } else if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        } else {
            // 只有一个有环,必不相交.
            return null;
        }
    }

    /**
     * 获取链表第一个入环节点，如果无环，返回null
     */
    public static ListNode getLoopNode(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        ListNode slow = head.next;
        ListNode fast = head.next.next;
        // slow fast 第一次相遇,证明有环.
        while (fast != slow) {
            // 无环，返回null
            if (fast.next == null || fast.next.next == null)
                return null;
            slow = slow.next;
            fast = fast.next.next;
        }

        // slow fast 第二次相遇,在第一个入环节点
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }

    /**
     * 如果两个链表都无环，返回第一个相交节点，如果不相交，返回null
     */
    public static ListNode noLoop(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        ListNode cur1 = head1;
        ListNode cur2 = head2;
        // 计算两个链表的长度差.
        int count = 0;
        while (cur1.next != null) {
            cur1 = cur1.next;
            count++;
        }
        while (cur2.next != null) {
            cur2 = cur2.next;
            count--;
        }

        // 两链表的尾节点不同, 则不相交，返回null
        if (cur1 != cur2)
            return null;

        // 最长的链表向后count个节点.
        cur1 = count > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        count = Math.abs(count);
        while (count != 0) {
            count--;
            cur1 = cur1.next;
        }

        // 两链表相遇,在第一个入环节点
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 两个有环链表，返回第一个相交节点，如果不相交返回null
     */
    public static ListNode bothLoop(ListNode head1, ListNode loop1, ListNode head2, ListNode loop2) {
        ListNode cur1 = null;
        ListNode cur2 = null;

        if (loop1 == loop2) {
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1.next != loop1) {
                cur1 = cur1.next;
                n++;
            }
            while (cur2.next != loop2) {
                cur2 = cur2.next;
                n--;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0) {
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            cur1 = loop1.next;
            // 入环节点不同,遍历环
            while (cur1 != loop1) {
                // 入环节点在同一环上
                if (cur1 == loop2)
                    return loop1;
                cur1 = cur1.next;
            }
            return null;
        }

    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);

        // 0->9->8->6->7->null
        ListNode head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).val);

        // 1->2->3->4->5->6->7->4...
        head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).val);

        // 0->9->8->6->4->5->6..
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).val);
    }
}
