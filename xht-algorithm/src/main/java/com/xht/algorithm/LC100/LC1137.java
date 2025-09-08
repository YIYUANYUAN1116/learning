package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-14 11:53
 **/
public class LC1137 {
    public static void main(String[] args) {
        System.out.println(tribonacci(4));
    }

    public static int tribonacci(int n) {
        if (n==0)return 0;
        if (n == 1)return 1;
        if (n==2)return 1;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1]=1;
        dp[2]=1;
        for (int i = 3; i <= n; i++) {
            dp[n] = dp[i-1]+dp[i-2]+dp[i-3];
        }
        return dp[n];
    }
}
