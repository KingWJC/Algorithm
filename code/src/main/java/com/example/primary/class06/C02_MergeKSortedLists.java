/**
 * 合并多个有序链表
 */
package com.example.primary.class06;

import java.util.Comparator;
import java.util.PriorityQueue;

public class C02_MergeKSortedLists {
    public static class ListNode {
        public int val;
        public ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static class MyComparator implements Comparator<ListNode> {
        @Override
        public int compare(ListNode o1, ListNode o2) {
            return o1.val - o2.val;
        }
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0)
            return null;

        PriorityQueue<ListNode> heap = new PriorityQueue<>(new MyComparator());
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null)
                heap.add(lists[i]);
        }

        ListNode head = null;
        ListNode pre = null;
        while (!heap.isEmpty()) {
            ListNode cur = heap.poll();
            if (head == null) {
                head = cur;
            } else {
                pre.next = cur;
            }

            pre = cur;
            if (cur.next != null)
                heap.add(cur.next);
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(4);
        head1.next.next = new ListNode(5);

        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(3);
        head2.next.next = new ListNode(4);

        ListNode head3 = new ListNode(2);
        head3.next = new ListNode(6);

        ListNode result = mergeKLists(new ListNode[] { head1, head2, head3 });
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
