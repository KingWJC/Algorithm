/**
 * 将单向链表按某值划分成左边小、中间相等、右边大的形式
 */
package com.example.system.class06;

import com.example.utility.entity.ListNode;

public class C03_SmallerEqualBigger {
    /**
     * 把链表放入数组里，在数组上做partition
     */
    public static ListNode linkedListPartitionByArray(ListNode head, int pivot) {
        if (head == null)
            return head;

        ListNode cur = head;
        int index = 0;
        while (cur != null) {
            cur = cur.next;
            index++;
        }

        ListNode[] nodeArr = new ListNode[index];
        cur = head;
        index = 0;
        while (cur != null) {
            nodeArr[index] = cur;
            cur = cur.next;
            index++;
        }

        arrPartition(nodeArr, pivot);

        for (index = 0; index < nodeArr.length - 1; index++) {
            nodeArr[index].next = nodeArr[index + 1];
        }
        nodeArr[index].next = null;

        return nodeArr[0];
    }

    private static void arrPartition(ListNode[] nodeArr, int pivot) {
        if (nodeArr == null || nodeArr.length < 2)
            return;
        int l = 0;
        int r = nodeArr.length - 1;
        int index = 0;
        while (l < r) {

        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(7);
        head.next = new ListNode(9);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(8);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(2);
        head.next.next.next.next.next.next = new ListNode(5);
        printLinkedList(head);
    }

    private static void printLinkedList(ListNode head) {
        System.out.print("linked List:");
        while (head != null) {
            System.out.print(head.val + ",");
            head = head.next;
        }
        System.out.println();
    }
}
