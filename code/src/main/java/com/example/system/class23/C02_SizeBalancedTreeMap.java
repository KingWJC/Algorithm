/**
 * Size Balanced Tree的结构
 * 平衡性调整有递归性
 */
package com.example.system.class23;

public class C02_SizeBalancedTreeMap {
    /**
     * SB树的节点（key必须可比较）
     */
    public static class SBTNode<K extends Comparable<K>, V> {
        // 节点的键值
        public K k;
        public V v;
        // 左右子树节点
        public SBTNode<K, V> l;
        public SBTNode<K, V> r;
        // 节点个数（平衡因子）
        public int size;

        public SBTNode(K key, V value) {
            k = key;
            v = value;
            size = 1;
        }
    }

    /**
     * SB树
     */
    public static class SizeBalancedTreeMap<K extends Comparable<K>, V> {
        private SBTNode<K, V> root;

        /**
         * 当前节点右旋
         */
        private SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> leftNode = cur.l;
            // 右旋
            cur.l = leftNode.r;
            leftNode.r = cur;
            // 更新节点个数（平衡因子）
            leftNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            // 返回新的头节点
            return leftNode;
        }

        /**
         * 当前节点左旋
         */
        private SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> rightNode = cur.r;
            cur.r = rightNode.l;
            rightNode.l = cur;
            rightNode.size = cur.size;
            cur.size = getSize(cur.l) + getSize(cur.r) + 1;
            return rightNode;
        }

        /**
         * 获取某个节点的节点个数
         */
        private int getSize(SBTNode<K, V> cur) {
            return cur != null ? cur.size : 0;
        }

        /**
         * 当前节点的平衡性调整-递归调用
         * 调整后，孩子节点变化的节点，再次调整
         */
        private SBTNode<K, V> maintain(SBTNode<K, V> cur) {
            if (cur == null) {
                return null;
            }

            int lSize = getSize(cur.l);
            int llSize = cur.l != null ? getSize(cur.l.l) : 0;
            int lrSize = cur.l != null ? getSize(cur.l.r) : 0;

            int rSize = getSize(cur.r);
            int rrSize = cur.r != null ? getSize(cur.r.r) : 0;
            int rlSize = cur.r != null ? getSize(cur.r.l) : 0;

            if (rSize < llSize) {// LL
                cur = rightRotate(cur);

                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (rSize < lrSize) {// LR
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                // 在旋转结束后，检查孩子节点变化的节点。
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (lSize < rrSize) {// RR
                cur = leftRotate(cur);

                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (lSize < rlSize) {// RL
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);

                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        /**
         * 增加节点
         * 
         * @param cur 以cur为头的树上，增加(key, value)这样的记录
         *            加完之后，会对cur做检查，该调整调整
         * @return 调整完之后，返回整棵树的新头部
         */
        private SBTNode<K, V> add(SBTNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new SBTNode<K, V>(key, value);
            } else {
                cur.size++;
                if (key.compareTo(cur.k) < 0) {
                    cur.l = add(cur.l, key, value);
                } else {
                    cur.r = add(cur.r, key, value);
                }
                return maintain(cur);
            }
        }

        /**
         * 在当前节点子树上，删除节点
         * 在cur这棵树上，删掉key所代表的节点
         * 返回cur这棵树的新头部
         */
        private SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
            // 删除的前提条件是key存在，所以key向上路径的所有节点个数都减一。
            cur.size--;
            if (key.compareTo(cur.k) < 0) {
                cur.l = delete(cur.l, key);
            } else if (key.compareTo(cur.k) > 0) {
                cur.r = delete(cur.r, key);
            } else {
                if (cur.l == null && cur.r == null) {
                    cur = null;
                } else if (cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else if (cur.l == null && cur.r != null) {
                    cur = cur.r;
                } else {
                    SBTNode<K, V> pre = null;
                    SBTNode<K, V> des = cur.r;
                    des.size--;
                    while (des.l != null) {
                        pre = des;
                        des = des.l;
                        des.size--;
                    }

                    // 用后继节点的右节点替换后继节点
                    if (pre != null) {
                        pre.l = des.r;
                        des.r = cur.r;
                    }

                    des.l = cur.l;
                    des.size = des.l.size + getSize(des.r) + 1;
                    cur = des;
                }
            }
            return cur;
        }

        /**
         * 获取第kth个的节点
         */
        private SBTNode<K, V> getIndex(SBTNode<K, V> cur, int kth) {
            int leftSize = cur.l != null ? cur.l.size : 0;
            if (kth == leftSize + 1) {
                return cur;
            } else if (kth <= leftSize) {
                return getIndex(cur.l, kth);
            } else {
                return getIndex(cur.r, kth - leftSize - 1);
            }
        }

        /**
         * 找到等于key或离它最近的节点
         */
        private SBTNode<K, V> findLastIndex(K key) {
            SBTNode<K, V> ans = root;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                ans = cur;
                if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else if (key.compareTo(cur.k) > 0) {
                    cur = cur.r;
                } else {
                    break;
                }
            }
            return ans;
        }

        /**
         * 找到大于等于key最近的节点（右边第一个）
         */
        private SBTNode<K, V> findLastNoSmallIndex(K key) {
            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.k) < 0) {
                    ans = cur;
                    cur = cur.l;
                } else if (key.compareTo(cur.k) > 0) {
                    cur = cur.r;
                } else {
                    ans = cur;
                    break;
                }
            }
            return ans;
        }

        /**
         * 找到小于等于key最近的节点（左边第一个）
         */
        private SBTNode<K, V> findLastNoBiglIndex(K key) {
            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else if (key.compareTo(cur.k) > 0) {
                    ans = cur;
                    cur = cur.r;
                } else {
                    ans = cur;
                    break;
                }
            }
            return ans;
        }

        // #region 有序表方法
        public int size() {
            return root != null ? root.size : 0;
        }

        public boolean containKey(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }

            SBTNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.k) == 0 ? true : false;
        }

        public void put(K key, V value) {
            if (key == null) {
                throw new RuntimeException("invlid parameter.");
            }
            SBTNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                lastNode.v = value;
            } else {
                root = add(root, key, value);
            }
        }

        public void remove(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }

            if (containKey(key)) {
                root = delete(root, key);
            }
        }

        public K getIndexKey(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid parameter.");
            }
            // 索引和节点个数转换，需要加1.
            return getIndex(root, index + 1).k;
        }

        public V getIndexValue(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid parameter.");
            }
            // 索引和节点个数转换，需要加1.
            return getIndex(root, index + 1).v;
        }

        public V get(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                return lastNode.v;
            } else {
                return null;
            }
        }

        public K firstKey() {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }
            return cur.k;
        }

        public K lastKey() {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.k;
        }

        public K floorKey(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }

            SBTNode<K, V> lastNoBigNode = findLastNoBiglIndex(key);
            return lastNoBigNode != null ? lastNoBigNode.k : null;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                throw new RuntimeException("invalid paramter.");
            }
            SBTNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
            return lastNoSmallNode != null ? lastNoSmallNode.k : null;
        }
        // #endregion
    }

    public static void main(String[] args) {
        SizeBalancedTreeMap<String, Integer> sbt = new SizeBalancedTreeMap<>();
        sbt.put("d", 4);
        sbt.put("c", 3);
        sbt.put("a", 1);
        sbt.put("b", 2);
        // sbt.put("e", 5);
        sbt.put("g", 7);
        sbt.put("f", 6);
        sbt.put("h", 8);
        sbt.put("i", 9);
        sbt.put("a", 111);

        System.out.println("测试添加和更新:");
        System.out.println(sbt.get("a"));
        sbt.put("a", 1);
        System.out.println(sbt.get("a"));
        for (int i = 0; i < sbt.size(); i++) {
            System.out.println(sbt.getIndexKey(i) + "," + sbt.getIndexValue(i));
        }

        System.out.println("测试键的获取:");
        System.out.println(sbt.firstKey());
        System.out.println(sbt.lastKey());
        System.out.println(sbt.floorKey("g"));
        System.out.println(sbt.ceilingKey("g"));
        System.out.println(sbt.floorKey("e"));
        System.out.println(sbt.ceilingKey("e"));
        System.out.println(sbt.floorKey(""));
        System.out.println(sbt.ceilingKey(""));
        System.out.println(sbt.floorKey("j"));
        System.out.println(sbt.ceilingKey("j"));

        // 测试有序列表的删除
        printAll(sbt.root);
        sbt.remove("d");
        printAll(sbt.root);
        sbt.remove("f");
        printAll(sbt.root);
    }

    private static void printAll(SBTNode<String, Integer> head) {
        System.out.println("Size Balanced Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    private static void printInOrder(SBTNode<String, Integer> head, int height, String to, int len) {
        if (head == null) {
            return;
        }

        printInOrder(head.r, height + 1, "R", len);

        String val = to + "(" + head.k + "," + head.v + ")" + to;
        // 在len长度的范围内，打印val的值。显示在中间。
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);

        printInOrder(head.l, height + 1, "L", len);
    }

    private static String getSpace(int len) {
        String space = " ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(space);
        }
        return sb.toString();
    }
}
