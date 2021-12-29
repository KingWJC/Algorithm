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
        int less = -1;
        int more = nodeArr.length - 1;
        int index = 0;
        while (index < more) {
            if (nodeArr[index].val < pivot) {
                swap(nodeArr, ++less, index++);
            } else if (nodeArr[index].val > pivot) {
                swap(nodeArr, index, more--);
            } else {
                index++;
            }
        }
    }

    private static void swap(ListNode[] arr, int a, int b) {
        if (a == b)
            return;
        ListNode temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * 使用指针,分成小、中、大三个链表
     */
    public static ListNode linkedListPatition(ListNode head, int pivot) {
        if (head == null)
            return head;

        ListNode sH = null; // small head
        ListNode sT = null; // small tail
        ListNode eH = null; // equal head
        ListNode eT = null; // equal tail
        ListNode mH = null; // big head
        ListNode mT = null; // big tail
        ListNode next = null; // save next node
        // every node distributed to three lists
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.val < pivot) {
                if (sH == null) {
                    sH = head;
                    sT = head;
                } else {
                    sT.next = head;
                    sT = head;
                }
            } else if (head.val > pivot) {
                if (mH == null) {
                    mH = head;
                    mT = head;
                } else {
                    mT.next = head;
                    mT = head;
                }
            } else {
                if (eH == null) {
                    eH = head;
                    eT = head;
                } else {
                    eT.next = head;
                    eT = head;
                }
            }

            head = next;
        }

        // 拼接链表,小于区域的尾巴，连等于区域的头，等于区域的尾巴连大于区域的头
        if (sT != null) {
            sT.next = eH;
            // 谁去连大于区域的头，谁就变成eT
            eT = eT == null ? sT : eT;
        }

        // 需要用eT 去接 大于区域的头
		// 有等于区域，eT -> 等于区域的尾结点
		// 无等于区域，eT -> 小于区域的尾结点
        if (eT != null) {
            eT.next = mH;
        }

        if (sH != null)
            return sH;
        return eH != null ? eH : mH;
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
        head = linkedListPartitionByArray(head, 5);
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
