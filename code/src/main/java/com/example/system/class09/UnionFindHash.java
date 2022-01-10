/**
 * 并查集(hashMap常数时间慢)
 * V是样本类型，用Inner包一层，即使V是基础类型也能按引用传递。
 */
package com.example.system.class09;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.example.utility.entity.Inner;

public class UnionFindHash<V> {
    // 用户使用只知道V,所以需要有对应关系。
    public HashMap<V, Inner<V>> nodes;
    // 父子指针关系用表存
    public HashMap<Inner<V>, Inner<V>> parents;
    // 只存代表元素的记录
    public HashMap<Inner<V>, Integer> sizeMap;

    public UnionFindHash(List<V> values) {
        nodes = new HashMap<>();
        parents = new HashMap<>();
        sizeMap = new HashMap<>();
        for (V v : values) {
            Inner<V> node = new Inner<>(v);
            nodes.put(v, node);
            // 代表元素的父节点是自己
            parents.put(node, node);
            sizeMap.put(node, 1);
        }
    }

    /**
     * 获取代表元素。
     */
    private Inner<V> find(Inner<V> cur) {
        Stack<Inner<V>> stack = new Stack<>();
        while (parents.get(cur) != cur) {
            cur = parents.get(cur);
            stack.push(cur);
        }
        while (!stack.isEmpty()) {
            parents.put(stack.pop(), cur);
        }
        return cur;
    }

    public void union(V a, V b) {
        Inner<V> aHead = find(nodes.get(a));
        Inner<V> bHead = find(nodes.get(b));
        if (aHead != bHead) {
            int aSize = sizeMap.get(aHead);
            int bSize = sizeMap.get(bHead);

            // 大小集合头部重定向
            Inner<V> bigHead = aSize >= bSize ? aHead : bHead;
            Inner<V> smallHead = bigHead == aHead ? bHead : aHead;
            // 小集合挂在大集合的下面,使链长度增长慢
            parents.put(smallHead, bigHead);
            sizeMap.put(bigHead, aSize + bSize);
            sizeMap.remove(smallHead);
        }
    }

    public boolean isSameSet(V v1, V v2) {
        return find(nodes.get(v1)) == find(nodes.get(v2));
    }

}
