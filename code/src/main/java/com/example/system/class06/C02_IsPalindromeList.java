/**
 * 链表问题：给定一个单链表的头节点head，请判断该链表是否为回文结构。
 */
package com.example.system.class06;

import java.util.Stack;

import com.example.utility.entity.ListNode;

public class C02_IsPalindromeList {
    /**
     * 使用额外空间O(N)
     */
    public static boolean isPalindromeStack(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur.val);
            cur = cur.next;
        }

        boolean result = true;
        while (head != null) {
            if (head.val != stack.pop()) {
                result = false;
                break;
            }
            head = head.next;
        }
        return result;
    }

    /**
     * 使用额外空间O(N/2)和快慢指针
     */
    public static boolean isPalindromePointerStack(ListNode head) {
        if (head == null || head.next == null)
            return true;

        // 奇数长度返回中点，偶数长度返回下中点
        ListNode slow = head.next;
        ListNode fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Stack<Integer> stack = new Stack<>();
        while (slow != null) {
            stack.push(slow.val);
            slow = slow.next;
        }

        while (!stack.isEmpty()) {
            if (stack.pop() != head.val)
                return false;
            head = head.next;
        }
        return true;
    }

    /**
     * 使用额外空间O(1), 链表反转
     */
    public static boolean isPalindromePointerReverse(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        // 奇数长度返回中点，偶数长度返回上中点
        ListNode n1 = head;
        ListNode n2 = head;
        while (n2.next != null && n2.next.next != null) {
            n1 = n1.next;
            n2 = n2.next.next;
        }

        // 反转上中点或中点的下一个节点之后的链表。
        n2 = n1.next;
        n1.next = null;
        ListNode n3 = null;
        while (n2 != null) {
            n3 = n2.next;
            n2.next = n1;
            n1 = n2;
            n2 = n3;
        }

        n3 = n1; // 链表尾节点
        n2 = head; // 链表首节点
        // 检查是否为回文结构
        boolean res = true;
        while (n1 != null && n2 != null) {
            if (n1.val != n2.val) {
                res = false;
                break;
            }
            n1 = n1.next;
            n2 = n2.next;
        }

        // 反转right to mid。
        n2 = null;
        while (n3 != null) {
            n1 = n3.next;
            n3.next = n2;
            n2 = n3;
            n3 = n1;
        }

        return res;
    }

    public static void main(String[] args) {
		ListNode head = null;
		printLinkedList(head);
		System.out.print(isPalindromeStack(head) + " | ");
		System.out.print(isPalindromePointerStack(head) + " | ");
		System.out.println(isPalindromePointerReverse(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

        head = new ListNode(1);
		printLinkedList(head);
		System.out.print(isPalindromeStack(head) + " | ");
		System.out.print(isPalindromePointerStack(head) + " | ");
		System.out.println(isPalindromePointerReverse(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

        head = new ListNode(1);
        head.next=new ListNode(2);
		printLinkedList(head);
		System.out.print(isPalindromeStack(head) + " | ");
		System.out.print(isPalindromePointerStack(head) + " | ");
		System.out.println(isPalindromePointerReverse(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

        head = new ListNode(1);
        head.next=new ListNode(1);
		printLinkedList(head);
		System.out.print(isPalindromeStack(head) + " | ");
		System.out.print(isPalindromePointerStack(head) + " | ");
		System.out.println(isPalindromePointerReverse(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

        head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(1);
		printLinkedList(head);
		System.out.print(isPalindromeStack(head) + " | ");
		System.out.print(isPalindromePointerStack(head) + " | ");
		System.out.println(isPalindromePointerReverse(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

        head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(1);
		printLinkedList(head);
		System.out.print(isPalindromeStack(head) + " | ");
		System.out.print(isPalindromePointerStack(head) + " | ");
		System.out.println(isPalindromePointerReverse(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");
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
