package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-14 13:12
 **/
public class LC700 {
    public static void main(String[] args) {
        int[] dp = {2, 3, 4};
        System.out.println(deleteAndEarn(dp));
    }

    public static int deleteAndEarn(int[] nums) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(max,num);
        }
        int[] sum = new int[max + 1];
        for (int num : nums) {
            sum[num]+=num;
        }
        
        return rob(sum);
    }

    public static int rob(int[] sum) {
        if (sum.length == 0)return 0;
        if (sum.length==1)return sum[0];
        if (sum.length==2)return Math.max(sum[0],sum[1]);
        int[] dp = new int[sum.length];
        dp[0] = sum[0];
        dp[1] = Math.max(sum[0],sum[1]);
        for (int i = 2; i < sum.length; i++) {
            dp[i] = Math.max(dp[i-2]+sum[i],dp[i-1]);
        }
        return dp[dp.length-1];
    }
}
