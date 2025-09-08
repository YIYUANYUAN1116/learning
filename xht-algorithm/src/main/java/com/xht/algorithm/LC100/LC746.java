package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-14 12:33
 **/
public class LC746 {
    public static void main(String[] args) {

    }
    public int minCostClimbingStairs(int[] cost) {

        if (cost.length==1){
            return cost[0];
        }
        if (cost.length == 2){
            return Math.min(cost[0],cost[1]);
        }

        int len = cost.length + 1;
        int[] dp = new int[len];//顶楼的费用
        dp[0] = dp[1] = 0;
        dp[2] = Math.min(cost[0],cost[1]);
        for (int i = 3; i < len; i++) {
            dp[i] = Math.min(dp[i-2]+cost[i-2],dp[i-3]+cost[i-3]+cost[i-1]);
        }
        return dp[len-1];
    }

}
