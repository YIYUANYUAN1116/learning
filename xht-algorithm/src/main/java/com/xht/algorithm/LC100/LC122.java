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
