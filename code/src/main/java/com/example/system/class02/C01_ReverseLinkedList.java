/**
 * 单链表和双链表如何反转
 */
package com.example.system.class02;

import java.util.ArrayList;
import java.util.List;

import com.example.utility.entity.ListNode;
import com.example.utility.entity.ListNodeDouble;
import com.example.utility.helper.LinkedListTestHelper;

public class C01_ReverseLinkedList {

    public static ListNode revertLinkedList(ListNode head) {
        ListNode next = null;
        ListNode pre = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        // 最终head为null
        return pre;
    }

    /**
     * 使用集合记录每个节点
     */
    public static ListNode testRevertLinkedList(ListNode head) {
        if (head == null)
            return null;

        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }

        list.get(0).next = null;
        for (int i = 1; i < list.size(); i++) {
            list.get(i).next = list.get(i - 1);
        }

        return list.get(list.size() - 1);
    }

    public static ListNodeDouble revertDoubleLinkedList(ListNodeDouble head) {
        ListNodeDouble pre = null;
        ListNodeDouble next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.pre = next;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 使用集合记录每个节点
     */
    public static ListNodeDouble testRevertDoubleList(ListNodeDouble head) {
        if (head == null)
            return null;

        List<ListNodeDouble> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }

        list.get(0).next = null;
        for (int i = 1; i < list.size(); i++) {
            list.get(i - 1).pre = list.get(i);
            list.get(i).next = list.get(i - 1);
        }

        return list.get(list.size() - 1);
    }

    public static void main(String[] args) {
        LinkedListTestHelper.testReverseList(C01_ReverseLinkedList::revertLinkedList,
                C01_ReverseLinkedList::testRevertLinkedList);

        LinkedListTestHelper.testReverseDoubleList(C01_ReverseLinkedList::revertDoubleLinkedList,
                C01_ReverseLinkedList::revertDoubleLinkedList);
    }
}
