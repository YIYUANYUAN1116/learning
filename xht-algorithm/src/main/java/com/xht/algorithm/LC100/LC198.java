package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-20 09:58
 **/
public class LC198 {
    public static void main(String[] args) {

    }

    public int rob(int[] nums) {

        if (nums == null || nums.length == 0){
            return 0;
        }

        if (nums.length == 1){
            return nums[0];
        }
        int len = nums.length;
        int[] dp = new int[len];

        dp[0] = nums[0];
        dp[1] = Math.max(nums[0],nums[1]);

        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i-1],dp[i-2]+nums[i]);
        }
        return dp[len - 1];
    }


    public int rob2(int[] nums) {
        int len = nums.length;
        if (len == 0)return 0;
        if (len == 1)return nums[0];

        int[] dp = new int[len];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0],nums[1]);
        for (int i = 2; i < len; i++) {
            dp[i] = Math.max(dp[i-2]+nums[i],dp[i-1]);
        }
        return dp[len-1];

    }
}
