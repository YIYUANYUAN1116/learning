package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-29 10:17
 **/
public class LC53 {
    public static void main(String[] args) {

    }

    public int maxSubArray(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len];
        dp[0] = nums[0];
        int res = dp[0];
        for (int i = 1; i < len; i++) {
            dp[i] = dp[i-1]>=0?dp[i-1]+nums[i]:nums[i];
            res = Math.max(res,dp[i]);
        }
        return res;
    }
}
