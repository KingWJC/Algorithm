/**
 * 双向链表的随机快速排序
 * 和数组的经典快速排序在算法上没有区别,但是coding需要更小心,注意边界。
 */
package com.example.system.class03;

import java.util.ArrayList;
import java.util.Comparator;

import com.example.utility.entity.ListNodeDouble;
import com.example.utility.helper.LinkedListTestHelper;

public class C08_QSDoubleLinkedList {
    public static ListNodeDouble quickSort(ListNodeDouble head) {
        if (head == null)
            return null;

        int N = 0;
        ListNodeDouble currentNode = head;
        ListNodeDouble endNode = null;
        while (currentNode != null) {
            N++;
            endNode = currentNode;
            currentNode = currentNode.next;
        }

        return process(head, endNode, N).head;
    }

    public static class HeadTail {
        ListNodeDouble head;
        ListNodeDouble tail;

        public HeadTail(ListNodeDouble h, ListNodeDouble t) {
            this.head = h;
            this.tail = t;
        }
    }

    /**
     * L...R是一个双向链表的头和尾,一共有N个节点
     * L的last指针指向null，R的next指针指向null,也就是说L的左边没有，R的右边也没节点
     * 用随机快排的方式排好序, 返回排好序之后的双向链表的头和尾(HeadTail)
     */
    public static HeadTail process(ListNodeDouble L, ListNodeDouble R, int N) {
        if (L == null) {
            return null;
        }

        if (L == R) {
            return new HeadTail(L, R);
        }

        // L..R上不只一个节点, 随机得到一个随机下标, 范围[0,N-1]
        int randomIndex = (int) (Math.random() * N);

        // 根据随机下标得到随机节点
        ListNodeDouble randomNode = L;
        while (randomIndex-- != 0) {
            randomNode = randomNode.next;
        }

        // 把随机节点从原来的环境里分离出来
        // 比如 a(L) -> b -> c -> d(R), 如果randomNode = c，
        // 那么调整之后: a(L) -> b -> d(R), c会被挖出来，randomNode = c
        if (randomNode == L) {
            L = randomNode.next;
            L.pre = null;
        } else if (randomNode == R) {
            randomNode.pre.next = null;
        } else {// randomNode一定是中间的节点
            randomNode.pre.next = randomNode.next;
            randomNode.next.pre = randomNode.pre;
        }
        randomNode.pre = null;
        randomNode.next = null;

        Info info = partition(L, randomNode);
        // <randomNode的部分去排序
        HeadTail lht = process(info.lh, info.lt, info.ls);
        // >randomNode的部分去排序
        HeadTail rht = process(info.rh, info.rt, info.rs);

        // 把排好序的左部分和右部分串在一起
        if (lht != null) {
            lht.tail.next = info.eh;
            info.eh.pre = lht.tail;
        }
        if (rht != null) {
            info.et.next = rht.head;
            rht.head.pre = info.et;
        }

        // 返回排好序之后总的头和总的尾
        ListNodeDouble head = lht != null ? lht.head : info.eh;
        ListNodeDouble tail = rht != null ? rht.tail : info.et;
        return new HeadTail(head, tail);
    }

    public static class Info {
        public ListNodeDouble lh;
        public ListNodeDouble lt;
        public int ls;
        public ListNodeDouble rh;
        public ListNodeDouble rt;
        public int rs;
        public ListNodeDouble eh;
        public ListNodeDouble et;

        public Info(ListNodeDouble lH, ListNodeDouble lT, int lS, ListNodeDouble rH, ListNodeDouble rT, int rS,
                ListNodeDouble eH, ListNodeDouble eT) {
            lh = lH;
            lt = lT;
            ls = lS;
            rh = rH;
            rt = rT;
            rs = rS;
            eh = eH;
            et = eT;
        }
    }

    /**
     * (L....一直到空)，是一个双向链表
     * pivot是一个不在(L....一直到空)的独立节点，它作为划分值
     * 根据荷兰国旗问题的划分方式，把(L....一直到空)划分成:
     * <pivot 、 =pivot 、 >pivot 三个部分，然后把pivot融进=pivot的部分
     * 比如 4(L)->6->7->1->5->0->9->null pivot=5(这个5和链表中的5，是不同的节点)
     * 调整完成后:
     * 4->1->0 小于的部分\
     * 5->5 等于的部分
     * 6->7->9 大于的部分
     * 三个部分是断开的, 然后返回Info：
     * 小于部分的头、尾、节点个数 : lh,lt,ls
     * 大于部分的头、尾、节点个数 : rh,rt,rs
     * 等于部分的头、尾 : eh,et
     */
    public static Info partition(ListNodeDouble L, ListNodeDouble pivot) {
        ListNodeDouble lh = null;
        ListNodeDouble lt = null;
        int ls = 0;
        ListNodeDouble rh = null;
        ListNodeDouble rt = null;
        int rs = 0;
        ListNodeDouble eh = pivot;
        ListNodeDouble et = pivot;

        ListNodeDouble tmp = null;

        while (L != null) {
            tmp = L.next;
            L.next = null;
            L.pre = null;
            if (L.val < pivot.val) {
                ls++;
                if (lh == null) {
                    lh = L;
                    lt = L;
                } else {
                    lt.next = L;
                    L.pre = lt;
                    lt = L;
                }
            } else if (L.val > pivot.val) {
                rs++;
                if (rh == null) {
                    rh = L;
                    rt = L;
                } else {
                    rt.next = L;
                    L.pre = rt;
                    rt = L;
                }
            } else {
                et.next = L;
                L.pre = et;
                et=L;
            }
            L = tmp;
        }

        return new Info(lh, lt, ls, rh, rt, rs, eh, et);
    }

    public static ListNodeDouble sortLoop(ListNodeDouble head) {
        if (head == null)
            return null;
        ArrayList<ListNodeDouble> arr = new ArrayList<>();
        while (head != null) {
            arr.add(head);
            head = head.next;
        }
        arr.sort(new ListNodeCompare());

        ListNodeDouble h = arr.get(0);
        h.pre = null;

        // 重新按顺序绑定节点
        ListNodeDouble p = h;
        for (int i = 1; i < arr.size(); i++) {
            ListNodeDouble cur = arr.get(i);
            p.next = cur;
            cur.pre = p;
            cur.next = null;
            p = cur;
        }

        return h;
    }

    public static class ListNodeCompare implements Comparator<ListNodeDouble> {
        @Override
        public int compare(ListNodeDouble o1, ListNodeDouble o2) {
            return o1.val - o2.val;
        }
    }

    public static void main(String[] args) {
        LinkedListTestHelper.testQuickSort(C08_QSDoubleLinkedList::quickSort, C08_QSDoubleLinkedList::sortLoop);
    }
}
