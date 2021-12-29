/**
 * 深度复制带有rand指针的链表:
 */
package com.example.system.class06;

import java.util.HashMap;

public class C04_CopyListWithRandom {
    /**
     * 带有rand随机指针的节点
     */
    public static class Node {
        int value;
        Node next;
        Node rand;

        Node(int val) {
            value = val;
        }
    }

    /**
     * 使用HashMap存储新旧节点
     */
    public static Node copyRandomListByHash(Node head) {
        // key 老节点 value 新节点
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }

        // 为新节点的next,rand赋值
        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }

        return map.get(head);
    }

    /**
     * 在原链表上增加新的复制节点
     */
    public static Node copyRandomList(Node head) {
        if (head == null)
            return null;

        Node cur = head;
        Node next = null;
        // 增加新的复制节点
        // 1 -> 2 -> 3 -> null
        // 1 -> 1' -> 2 -> 2' -> 3 -> 3'
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.value);
            cur.next.next = next;
            cur = next;
        }

        cur = head;
        Node copy = null;
        // 1 1' 2 2' 3 3'
        // 依次设置 1' 2' 3' random指针
        // 在next方向上，random正确
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            copy.rand = cur.rand != null ? cur.rand.next : null;
            cur = next;
        }

        Node res = head.next;
        cur = head;
        // 把新老链表分离
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;

            cur.next = next;
            copy.next = next != null ? next.next : null;

            cur = next;
        }
        return res;
    }

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.rand = head1.next.next;
        head1.next.rand = head1;
        head1.next.next.rand = head1.next;
        printLinkedList(head1);
        
        Node copy1 = copyRandomListByHash(head1);
        printLinkedList(copy1);
        Node copy2 = copyRandomList(head1);
        printLinkedList(copy2);
    }

    private static void printLinkedList(Node head) {
        System.out.print("linked list:");
        while (head != null) {
            if (head.rand != null)
                System.out.print("[" + head.value + "," + head.rand.value + "],");
            else
                System.out.print("[" + head.value + "],");
            head = head.next;
        }
        System.out.println();
    }
}
