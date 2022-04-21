/**
 * 每次一个用户购买或退回商品后，返回当前事件发生后的得奖名单
 */
package com.example.system.class04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.example.utility.entity.HeapGreater;

public class C06_EveryStepGetWinner {
    /**
     * 模拟实现，不优化
     * 操作时间O(N), 排序, 移除元素.
     */
    public static List<List<Integer>> testTopK(int[] arr, boolean[] op, int k) {
        // 每次的得奖名单
        List<List<Integer>> ans = new ArrayList<>();
        // 候选区
        ArrayList<Customer> candidates = new ArrayList<>();
        // 得奖区
        ArrayList<Customer> winners = new ArrayList<>();
        // 用户ID
        HashMap<Integer, Customer> customers = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];

            // 用户不存在 且 操作是退货 则得奖名单不变.(用户购买数为0并且又退货)
            if (!buyOrRefund && !customers.containsKey(id)) {
                ans.add(getCurAns(winners));
                continue;
            }

            // 用户之前购买数是0，此时买货事件
            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0, 0));
            }

            // 用户之前购买数>0， 此时买货
            // 用户之前购买数>0, 此时退货
            Customer c = customers.get(id);
            // 某用户发生购买商品事件，购买商品数+1，发生退货事件，购买商品数-1
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            // 某用户购买数==0, 彻底离开
            if (c.buy == 0) {
                customers.remove(id);
            }

            if (!candidates.contains(c) && !winners.contains(c)) {
                c.enterTime = i;
                // 最初时如果得奖区没有到达K个用户，那么新来的用户直接进入得奖区
                if (winners.size() < k) {
                    winners.add(c);
                } else {
                    candidates.add(c);
                }
            }

            // 用户购买数==0, 不管在哪个区域都删除，并重排序.
            candidates.removeIf(cus -> cus.buy == 0);
            winners.removeIf(cus -> cus.buy == 0);
            // 如果候选区中购买数最多的用户有多个，机会会给最早进入候选区的用户
            candidates.sort(new CandidateComparator());
            // 如果得奖区中购买数最少的用户有多个，就替换最早进入得奖区的用户
            winners.sort(new WinnerComparator());
            move(candidates, winners, k, i);

            ans.add(getCurAns(winners));
        }

        return ans;
    }

    /**
     * 当前事件的得奖名单 用户ID
     */
    private static List<Integer> getCurAns(ArrayList<Customer> winners) {
        List<Integer> ans = new ArrayList<>();
        for (Customer cus : winners) {
            ans.add(cus.id);
        }
        return ans;
    }

    /**
     * 候选区购买数最多的用户，进入得奖区
     */
    private static void move(ArrayList<Customer> candidates, ArrayList<Customer> winners, int k, int i) {
        if (candidates.isEmpty()) {
            return;
        }

        if (winners.size() < k) {
            Customer customer = candidates.get(0);
            customer.enterTime = i;
            winners.add(customer);

            candidates.remove(0);
        } else {
            Customer candidate = candidates.get(0);
            Customer winner = winners.get(0);
            if (candidate.buy > winner.buy) {
                candidates.remove(0);
                winners.remove(0);

                candidate.enterTime = i;
                winner.enterTime = i;

                candidates.add(winner);
                winners.add(candidate);
            }
        }

    }

    /**
     * 用户
     */
    public static class Customer {
        int id; // 用户ID
        int buy; // 购买数
        int enterTime; // 进入当前事件的时间

        public Customer(int id, int buy, int enterTime) {
            this.id = id;
            this.buy = buy;
            this.enterTime = enterTime;
        }
    }

    /**
     * 按购买数从大到小, 候选区时间从小(早)到大(晚) 排序
     */
    public static class CandidateComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy == o2.buy ? o1.enterTime - o2.enterTime : o2.buy - o1.buy;
        }
    }

    /**
     * 按购买数从小到大, 候选区时间从小(早)到大(晚) 排序
     */
    public static class WinnerComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy == o2.buy ? o1.enterTime - o2.enterTime : o1.buy - o2.buy;
        }
    }

    /**
     * 使用改写的堆.
     * 操作时间O(logN)
     */
    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        WhoIsWinner winner = new WhoIsWinner(k);
        for (int i = 0; i < arr.length; i++) {
            winner.operate(i, arr[i], op[i]);
            ans.add(winner.getWinners());
        }
        return ans;
    }

    /**
     * 封装得奖名单的获取
     */
    public static class WhoIsWinner {
        private HeapGreater<Customer> winners;
        private HeapGreater<Customer> candidates;
        private HashMap<Integer, Customer> customers;
        private final int limitK;

        public WhoIsWinner(int K) {
            limitK = K;
            winners = new HeapGreater<>(new WinnerComparator());
            candidates = new HeapGreater<>(new CandidateComparator());
            customers = new HashMap<>();
        }

        public void operate(int time, int id, boolean buyOrReturn) {
            if (!customers.containsKey(id) && !buyOrReturn) {
                return;
            }

            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0, 0));
            }

            Customer c = customers.get(id);
            if (buyOrReturn) {
                c.buy++;
            } else {
                c.buy--;
            }
            // 用户购买数==0，不管在哪个区域都离开
            if (c.buy == 0) {
                customers.remove(id);
                if (candidates.contains(c)) {
                    candidates.remove(c);
                } else if (winners.contains(c)) {
                    winners.remove(c);
                }
            } else {
                if (!winners.contains(c) && !candidates.contains(c)) {
                    c.enterTime = time;
                    if (winners.size() < limitK) {
                        winners.push(c);
                    } else {
                        candidates.push(c);
                    }
                } else if (candidates.contains(c)) {
                    candidates.resign(c);
                } else {
                    winners.resign(c);
                }
            }

            move(time);
        }

        /**
         * 候选区购买数最多的用户，已经足以进入得奖区
         */
        private void move(int time) {
            if (!candidates.isEmpty()) {
                if (winners.size() < limitK) {
                    Customer cc = candidates.poll();
                    cc.enterTime = time;
                    winners.push(cc);
                } else {
                    if (candidates.peek().buy > winners.peek().buy) {
                        Customer loser = winners.poll();
                        Customer cc = candidates.poll();
                        loser.enterTime = time;
                        candidates.push(loser);
                        cc.enterTime = time;
                        winners.push(cc);
                    }
                }
            }
        }

        public List<Integer> getWinners() {
            List<Integer> ans = new ArrayList<>();
            List<Customer> cus = winners.getAllElements();
            for (Customer customer : cus) {
                ans.add(customer.id);
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        // int[] arr = new int[] { 0, 0, 6, 8, 4, 5, 8, 2, 0, 4 };
        // boolean[] op = new boolean[] { true, true, true, true, true, true, true,
        // false, false, true };
        // int k = 2;
        // List<List<Integer>> result = testTopK(arr, op, k);
        // for (List<Integer> list : result) {
        // System.out.print(Arrays.toString(list.toArray()) + ",");
        // }

        int maxValue = 10;
        int maxLength = 10;
        int maxK = 6;
        int testTimes = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            EventData data = generateRandomData(maxValue, maxLength);
            int k = (int) (Math.random() * maxK) + 1;

            List<List<Integer>> result1 = testTopK(data.arr, data.op, k);
            List<List<Integer>> result2 = topK(data.arr, data.op, k);
            if (!isSameAnswer(result1, result2)) {
                System.out.println("error");
                System.out.println(Arrays.toString(data.arr));
                System.out.println(Arrays.toString(data.op));
                for (List<Integer> list : result1) {
                    System.out.print(Arrays.toString(list.toArray()) + ",");
                }
                System.out.println("");
                for (List<Integer> list : result2) {
                    System.out.print(Arrays.toString(list.toArray()) + ",");
                }
                break;
            }
        }
        System.out.println("测试结束");
    }

    /**
     * 客户事件
     */
    private static class EventData {
        int[] arr;
        boolean[] op;

        public EventData(int[] arr, boolean[] op) {
            this.arr = arr;
            this.op = op;
        }
    }

    /**
     * 生成客户事件数据
     */
    private static EventData generateRandomData(int maxValue, int maxLength) {
        int length = (int) (Math.random() * maxLength) + 1;
        int[] arr = new int[length];
        boolean[] op = new boolean[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) (Math.random() * maxValue);
            op[i] = Math.random() < 0.5;
        }
        return new EventData(arr, op);
    }

    /**
     * 比较结果
     */
    private static boolean isSameAnswer(List<List<Integer>> arr1, List<List<Integer>> arr2) {
        if (arr1.size() != arr2.size())
            return false;

        for (int i = 0; i < arr1.size(); i++) {
            List<Integer> cur1 = arr1.get(i);
            List<Integer> cur2 = arr2.get(i);
            if (cur1.size() != cur2.size())
                return false;

            cur1.sort((a, b) -> a - b);
            cur2.sort((a, b) -> a - b);
            for (int j = 0; j < cur1.size(); j++) {
                if (cur1.get(j) != cur2.get(j))
                    return false;
            }
        }

        return true;
    }
}
