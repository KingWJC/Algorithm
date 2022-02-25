/**
 * AVL树的结构实现
 * 不需要将递归改为迭代，因为树的高度最大为LogN，递归层数很少。
 */
package com.example.system.class23;

public class C01_AVLTreeMap {
    /**
     * AVL树节点
     * 节点用于有序表，所以必须要求key可比较，所以继承Comparable泛型接口
     */
    public static class AVLNode<K extends Comparable<K>, V> {
        // 节点键值
        public K k;
        public V v;
        // 左右节点
        public AVLNode<K, V> l;
        public AVLNode<K, V> r;
        // 树的高度（平衡因子），
        // 每次左旋或右旋，都要调整平衡因子，保证每个节点的数据都正确。
        int h;

        public AVLNode(K key, V value) {
            k = key;
            v = value;
            h = 1;
        }

    }

    /**
     * AVL树
     */
    public static class AVLTreeMap<K extends Comparable<K>, V> {
        // 根节点
        private AVLNode<K, V> root;
        // 树大小
        private int size;

        public AVLTreeMap() {
            root = null;
            size = 0;
        }

        /**
         * 当前节点右旋
         * 旋转操作会换头节点，所以要返回换好的头节点。
         */
        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;

            // 更新平衡因子
            cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
            left.h = Math.max(left.l != null ? left.l.h : 0, left.r != null ? left.r.h : 0) + 1;
            return left;
        }

        /**
         * 当前节点左旋
         */
        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            // 更新平衡因子，树的高度。
            cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
            right.h = Math.max(right.l != null ? right.l.h : 0, right.r != null ? right.r.h : 0) + 1;

            return right;
        }

        /**
         * 递归，返回值用于方便上一层抓取。
         * 在当前的节点的子树种，增加新节点的记录。
         * 搜索二叉树不接收重复的key。
         */
        private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new AVLNode<K, V>(key, value);
            } else {
                // 递归执行，cur就是栈中数据。
                if (key.compareTo(cur.k) < 0) {
                    // 如果key值小，则加在当前节点的左子树上。
                    // 当前节点的左节点，有可能被调整平衡后换掉，所以需要重新赋值。
                    cur.l = add(cur.l, key, value);
                } else {// 不会有等于的情况
                        // 如果key值大，则加在当前节点的右子树上。
                    cur.r = add(cur.r, key, value);
                }
                cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
                return maintain(cur);
            }
        }

        /**
         * cur从head开始，递归实现从底层改动的位置，其向上的路径中每个节点都需要调整平衡性，直到head节点。
         * 在cur这棵子树上，删掉key所代表的节点，返回cur这棵树的新头部
         * 删除的前提，是key一定存在于cur的子树中。
         */
        private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
            // 递归删除
            if (key.compareTo(cur.k) < 0) {
                cur.l = delete(cur.l, key);
            } else if (key.compareTo(cur.k) > 0) {
                cur.r = delete(cur.l, key);
            } else {
                // 删除key相同的节点
                if (cur.l == null && cur.r == null) {
                    cur = null;
                } else if (cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else if (cur.l == null && cur.r != null) {
                    cur = cur.r;
                } else {
                    // 获取当前节点的后继节点：右子树的最左节点。
                    AVLNode<K, V> del = cur.r;
                    while (del.l != null) {
                        del = del.l;
                    }
                    // 在右子树上删除后继节点，并重新调整右子树的平衡性。
                    cur.r = delete(cur.r, del.k);
                    // 用后继节点替换当前节点
                    del.l = cur.l;
                    del.r = cur.r;
                    cur = del;
                }
            }

            if (cur != null) {
                cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
            }
            // 递归删除时，每一层都检查平衡性，达到所受影响的点都查一遍的目的。
            return maintain(cur);
        }

        /**
         * 在当前节点上，进行平衡性调整
         */
        private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            int leftHeight = cur.l != null ? cur.l.h : 0;
            int rightHeight = cur.r != null ? cur.r.h : 0;
            // 左右子树失衡
            if (Math.abs(leftHeight - rightHeight) > 1) {
                // 左子树失衡
                if (leftHeight > rightHeight) {
                    int llheight = cur.l != null && cur.l.l != null ? cur.l.l.h : 0;
                    int lrheight = cur.l != null && cur.l.r != null ? cur.l.r.h : 0;
                    // LL和LR的情况
                    if (llheight >= lrheight) {
                        // 旋转会换头，重新赋值
                        cur = rightRotate(cur);
                    } else {
                        // LR的情况
                        cur.l = leftRotate(cur.l);
                        cur = rightRotate(cur);
                    }
                } else {
                    // 右子树失衡
                    int rlheight = cur.r != null && cur.r.l != null ? cur.r.l.h : 0;
                    int rrheight = cur.r != null && cur.r.r != null ? cur.r.r.h : 0;
                    // RR和RL的情况
                    if (rrheight >= rlheight) {
                        cur = leftRotate(cur);
                    } else {
                        // RL的情况
                        cur.r = rightRotate(cur.r);
                        cur = leftRotate(cur);
                    }
                }
            }
            return cur;
        }

        /**
         * 找到小于等于key最左的。
         */
        private AVLNode<K, V> findLastIndex(K key) {
            AVLNode<K, V> pre = root;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.k) == 0) {
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return pre;
        }

        /**
         * 找到大于等于key,且离key最近
         */
        private AVLNode<K, V> findLastNoSmallIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.k) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    ans = cur;
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
                ans = cur;
            }
            return ans;
        }

        /**
         * 找到小于等于key,且离key最近
         */
        private AVLNode<K, V> findLastNoBigIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.k) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.k) > 0) {
                    ans = cur;
                    cur = cur.r;
                } else {
                    cur = cur.l;
                }
            }
            return ans;
        }

        // #region 有序表的常见方法
        public int size() {
            return size;
        }

        public boolean containKey(K key) {
            if (key == null) {
                return false;
            }

            AVLNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.k) == 0 ? true : false;
        }

        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && lastNode.k.compareTo(key) == 0) {
                lastNode.v = value;
            } else {
                size++;
                root = add(root, key, value);
            }
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containKey(key)) {
                size--;
                root = delete(root, key);
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && lastNode.k.compareTo(key) == 0) {
                return lastNode.v;
            }
            return null;
        }

        /**
         * 有序表最左的键
         */
        public K firstKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }
            return cur.k;
        }

        /**
         * 有序表最右的键
         */
        public K lastKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.k;
        }

        /**
         * 小于等于key的位置
         */
        public K floorKey(K key) {
            if (key == null) {
                return null;
            }

            AVLNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }

        /**
         * 大于等于key的位置
         */
        public K ceillingKey(K key) {
            if (key == null) {
                return null;
            }

            AVLNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
            return lastNoSmallNode == null ? null : lastNoSmallNode.k;
        }

        // #endregion
    }
}
