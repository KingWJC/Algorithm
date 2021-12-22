/**
 * 堆(完全二叉树)
 */
package com.example.system.class04;

import java.util.Comparator;
import java.util.PriorityQueue;

public class C02_Heap {
    /**
     * 模拟大根堆
     */
    public static class TestMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public TestMaxHeap(int limit) {
            arr = new int[limit];
            size = 0;
            this.limit = limit;
        }

        public void push(int val) {
            if (isFull()) {
                throw new RuntimeException("your heap is full!");
            }
            arr[size++] = val;
        }

        public int pop() {
            if (isEmpty()) {
                throw new RuntimeException("your heap is empty");
            }

            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex])
                    maxIndex = i;
            }

            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }
    }

    /**
     * 实现大根堆
     */
    public static class MyMaxHeap {
        private int[] arr;
        private final int limit;
        private int heapSize;

        public MyMaxHeap(int limit) {
            this.limit = limit;
            arr = new int[limit];
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        public void push(int val) {
            if (isFull())
                throw new RuntimeException("your heap is full");

            arr[heapSize++] = val;
            // 新加进来的数, 停在了最后的位置
            heapInsert(heapSize - 1);
        }

        /**
         * index位置的数，依次往上移动
         * 移动到0位置，或者小于等于父节点的值，停！
         * 0-index全是大根堆.
         */
        private void heapInsert(int index) {
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void swap(int l, int r) {
            int temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
        }

        /**
         * 返回最大值，并且在大根堆中，把最大值删掉, 剩下的数，依然保持大根堆组织
         */
        public int pop() {
            if (isEmpty())
                throw new RuntimeException("your heap is empty");

            int ans = arr[0];
            // 把最大值删掉
            swap(0, --heapSize);
            heapify(0);
            return ans;
        }

        /**
         * 从index位置，往下看，不断的下沉
         * 停：较大的子节点不比index位置的数大；已经没子节点.
         */
        private void heapify(int index) {
            int left = index * 2 + 1;
            // 如果有左孩子
            while (left < heapSize) {
                // 有没有右孩子, 并把较大孩子的下标，给largest
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
                largest = arr[largest] > arr[index] ? largest : index;
                if (largest == index)
                    break;

                // index和较大孩子，要互换
                swap(index, largest);

                index = largest;
                left = index * 2 + 1;
            }
        }
    }

    /**
     * 从大到小排序
     */
    public static class PriorityComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    /**
     * 优先级队列结构，就是堆结构,默认小根堆
     */
    public static void testPriorityQueue() {
        // 改为大根堆.
        PriorityQueue<Integer> heap = new PriorityQueue<>(new PriorityComparator());
        heap.add(5);
        heap.add(5);
        heap.add(5);
        heap.add(3);
        // 5 , 7
        System.out.println(heap.peek());
        heap.add(7);
        heap.add(0);
        heap.add(7);
        heap.add(0);
        heap.add(7);
        heap.add(0);
        System.out.println(heap.peek());
        System.out.println("打印------");
        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }
    }

    public static void main(String[] args) {
        testPriorityQueue();

        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap my = new MyMaxHeap(curLimit);
            TestMaxHeap test = new TestMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Error!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Error!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Error!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
    }
}