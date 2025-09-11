package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-07 22:04
 **/
public class LC121 {
    public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        System.out.println(maxProfit(prices));
    }


    public static int maxProfit2(int[] prices) {
        int res = 0;
        int minIndex = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < prices[minIndex]) {
                minIndex = i;
            }else {
                res =  Math.max(res, prices[i] - prices[minIndex]);
            }
        }
        return res;
    }

    public static int maxProfit(int[] prices) {
        int res = 0;
        int minIndex = 0;
        for (int i = 0; i < prices.length-1; i++) {
            if (prices[i+1] < prices[minIndex]){
                minIndex = i+1;
            }else {
                res = Math.max(res, prices[i+1] - prices[minIndex]);
            }
        }
        return res;
    }
}
