/**
 * 手动改写堆结构
 * 增加反向索引器，支持修改和删除堆中指定元素，且时间复杂度为O(logN)
 */
package com.example.utility.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class HeapCreater<T> {
    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap;
    private int heapSize;
    private Comparator<? super T> compare;

    public HeapCreater(int size, Comparator<? super T> com) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = size;
        compare = com;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    public T peek() {
        return heap.get(0);
    }

    public void push(T value) {
        heap.add(value);
        indexMap.put(value, heapSize);
        heapInsert(heapSize++);
    }

    public T poll() {

    }

    public void remove(T value) {

    }

    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T t : heap) {
            ans.add(t);
        }
        return ans;
    }

    private void heapInsert(int index) {
        // index > 0 &&
        // 比较器比较时，如果有一个为空，则空值小于非空值。
        while (compare.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void heapify(int index) {
        int left = index * 2 + 1;
    }

    private void swap(int a, int b) {
        T ta = heap.get(a);
        T tb = heap.get(b);
        heap.set(a, tb);
        heap.set(b, ta);

        indexMap.put(ta, b);
        indexMap.put(tb, a);
    }
}
