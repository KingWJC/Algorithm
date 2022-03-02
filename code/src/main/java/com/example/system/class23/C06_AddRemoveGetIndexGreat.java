/**
 * 设计复杂度为O(logN)高效数据结构
 * 包含：add,remove,get方法。
 */
package com.example.system.class23;

import java.util.ArrayList;

public class C06_AddRemoveGetIndexGreat {
    /**
     * 只保存值，（不需要比较，通过size得到加入时序）
     * key表示加入的时序，
     */
    private static class SBTNode<V> {
        public V value;
        public SBTNode<V> l;
        public SBTNode<V> r;
        public int size;

        public SBTNode(V v) {
            value = v;
            size = 1;
        }
    }

    private static class SBTList<V> {
        private SBTNode<V> root;

        private SBTNode<V> rightRotate(SBTNode<V> cur) {
            SBTNode<V> leftNode = cur.l;
            cur.l = leftNode.r;
            leftNode.r = cur;
            leftNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return leftNode;
        }

        private SBTNode<V> leftRotate(SBTNode<V> cur) {
            SBTNode<V> rightNode = cur.r;
            cur.r = rightNode.l;
            rightNode.l = cur;
            rightNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return rightNode;
        }

        private SBTNode<V> maintain(SBTNode<V> cur) {
            if (cur == null) {
                return null;
            }

            try {
                int lSize = cur.l != null ? cur.l.size : 0;
                int llSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
                int lrSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
                int rSize = cur.r != null ? cur.r.size : 0;
                int rlSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
                int rrSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;

                if (rSize < llSize) {
                    cur = rightRotate(cur);
                    cur.r = maintain(cur.r);
                    cur = maintain(cur);
                } else if (rSize < lrSize) {
                    cur.l = leftRotate(cur.l);
                    cur = rightRotate(cur);
                    cur.l = maintain(cur.l);
                    cur.r = maintain(cur.r);
                    cur = maintain(cur);
                } else if (lSize < rrSize) {
                    cur = leftRotate(cur);
                    cur.l = maintain(cur.l);
                    cur = maintain(cur);
                } else if (lSize < rlSize) {
                    cur.r = rightRotate(cur.r);
                    cur = leftRotate(cur);
                    cur.l = maintain(cur.l);
                    cur.r = maintain(cur.r);
                    cur = maintain(cur);
                }
            } catch (Exception e) {
                throw e;
            }

            return cur;
        }

        private SBTNode<V> add(SBTNode<V> cur, int index, SBTNode<V> val) {
            if (cur == null) {
                return val;
            }
            try {
                cur.size++;
                int leftSize = cur.l != null ? cur.l.size : 0;
                // 因为index是索引，所以index=3时，会把节点插入到当前3位置的节点之前。
                if (index < leftSize + 1) {
                    cur.l = add(cur.l, index, val);
                } else {
                    cur.r = add(cur.r, index - leftSize - 1, val);
                }
            } catch (Exception e) {
                throw e;
            }

            return maintain(cur);
        }

        private SBTNode<V> delete(SBTNode<V> cur, int index) {
            cur.size--;
            int leftSize = cur.l != null ? cur.l.size : 0;
            if (index < leftSize) {
                cur.l = delete(cur.l, index);
            } else if (index > leftSize) {
                cur.r = delete(cur.r, index - leftSize - 1);
            } else {// index==leftsize 索引值和节点数量相同，表示删除当前节点
                if (cur.l == null && cur.r == null) {
                    cur = null;
                } else if (cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else if (cur.l == null && cur.r != null) {
                    cur = cur.r;
                } else {
                    SBTNode<V> pre = null;
                    SBTNode<V> des = cur.r;
                    des.size--;
                    while (des.l != null) {
                        pre = des;
                        des = des.l;
                        des.size--;
                    }
                    if (pre != null) {
                        pre.l = des.r;
                        des.r = cur.r;
                    }
                    des.l = cur.l;
                    des.size = des.l.size + (des.r != null ? des.r.size : 0) + 1;
                    cur = des;
                }
            }
            return cur;
        }

        private SBTNode<V> get(SBTNode<V> cur, int index) {
            int leftSize = cur.l != null ? cur.l.size : 0;
            if (index == leftSize) {
                return cur;
            } else if (index < leftSize) {
                return get(cur.l, index);
            } else {
                return get(cur.r, index - leftSize - 1);
            }
        }

        public void add(int index, V v) {
            SBTNode<V> val = new SBTNode<>(v);
            if (root == null) {
                root = val;
            } else {
                if (index <= root.size) {
                    root = add(root, index, val);
                }
            }
        }

        // 移除时，需要对root重新赋值，否则之后的操作都会有异常
        public void remove(int index) {
            if (index >= 0 && index < root.size) {
                root = delete(root, index);
            }
        }

        public V get(int index) {
            SBTNode<V> node = get(root, index);
            return node != null ? node.value : null;
        }

        public int size() {
            return root != null ? root.size : 0;
        }
    }

    public static void main(String[] args) {
        functionTest();
        // 性能测试：LinkedList的插入、删除、get效率不如SbtList
        performanceTest();
    }

    private static void functionTest() {
        int testTimes = 50000;
        int max = 1000000;
        boolean pass = true;
        ArrayList<Integer> list = new ArrayList<>();
        SBTList<Integer> sbtList = new SBTList<>();
        for (int i = 0; i < testTimes; i++) {
            if (list.size() != sbtList.size()) {
                pass = false;
                break;
            }

            if (list.size() > 1 && Math.random() < 0.5) {
                int removeIndex = (int) (Math.random() * list.size());
                list.remove(removeIndex);
                sbtList.remove(removeIndex);
            } else {
                int randomIndex = (int) (Math.random() * (list.size() + 1));
                int randomValue = (int) (Math.random() * (max + 1));
                list.add(randomIndex, randomValue);
                sbtList.add(randomIndex, randomValue);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(sbtList.get(i))) {
                pass = false;
                break;
            }
        }
        System.out.println("功能测试是否通过 : " + pass);
    }

    private static void performanceTest() {
        int testTimes = 500000;
        int max = 100000;
        ArrayList<Integer> list = new ArrayList<>();
        SBTList<Integer> sList = new SBTList<>();

        long start = 0;
        long end = 0;

        // 插入
        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (list.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            list.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("arrayList插入时间总时长(毫秒):" + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (sList.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            sList.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("SBTList插入时间总时长(毫秒):" + (end - start));

        // 读取
        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * list.size());
            list.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("arrayList读取时间总时长(毫秒):" + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * sList.size());
            sList.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SBTList读取时间总时长(毫秒):" + (end - start));

        // 删除
        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * list.size());
            list.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("arrayList删除时间总时长(毫秒):" + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * sList.size());
            sList.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SBTList删除时间总时长(毫秒):" + (end - start));
    }
}
