/**
 * 数组实现不超过固定大小的栈和队列
 */
package com.example.system.class02;

public class C04_ArrayToStackAndQueue {
    /**
     * 正常使用
     */
    public static class MyStack {
        private int[] arr;
        private int index;

        public MyStack(int limit) {
            arr = new int[limit];
            index = -1;
        }

        public void add(int value) {
            if (index == arr.length - 1) {
                throw new RuntimeException("full");
            }
            arr[index++] = value;
        }

        public int pop() {
            if (index < 0) {
                throw new RuntimeException("empty");
            }
            int ans = arr[index];
            index--;
            return ans;
        }
    }

    /**
     * 环形数组
     */
    public static class MyQueue {
        int[] arr;
        int size;
        int pollIndex;
        int offerIndex;

        public MyQueue(int limit) {
            arr = new int[limit];
            size = 0;
            pollIndex = 0;
            offerIndex = 0;
        }

        public void offer(int val) {
            if (size == arr.length) {
                throw new RuntimeException("队列满了，不能再加了");
            }
            arr[offerIndex] = val;
            size++;
            offerIndex = (offerIndex + 1) % arr.length;
        }

        public int poll() {
            if (size == 0) {
                throw new RuntimeException("队列空了，不能再拿了");
            }
            int result = arr[pollIndex];
            size--;
            pollIndex = (pollIndex + 1) % arr.length;
            return result;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // 如果现在的下标是i，返回下一个位置
        private int nextIndex(int i) {
            return i < arr.length - 1 ? i + 1 : 0;
        }
    }
}
