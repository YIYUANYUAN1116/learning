package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-24 10:58
 **/
public class LC122 {
    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7,1,5,3,6,4}));
    }


    /**
     * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
     * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
     * 返回 你能获得的 最大 利润 。
     * @param prices
     * @return
     */
    public static int maxProfit2(int[] prices) {
        int res = 0;
        int buy =  prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > buy) {
                res += prices[i] - buy;
            }
            buy = prices[i];
        }
        return res;
    }



    public static int maxProfit(int[] prices) {
        int res = 0;
        int buy = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > buy){
                res += prices[i] - buy;
            }
            buy = prices[i];
        }
        return res;
    }
}
