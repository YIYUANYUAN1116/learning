package com.xht.algorithm.LC100;

import java.util.List;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-15 11:58
 **/
public class LC120 {
    public static void main(String[] args) {

    }


    public int minimumTotal(List<List<Integer>> triangle) {
        int high = triangle.size();
        int[][] dp = new int[high][high];
        dp[0][0] = triangle.get(0).get(0);

        for (int i = 1; i < high; i++) {
            dp[i][0] = dp[i-1][0] + triangle.get(i).get(0);
            for (int j = 1; j < i; j++) {
                dp[i][j] = Math.min(dp[i-1][j-1],dp[i-1][j+1]) + triangle.get(i).get(j);
            }
            dp[i][i] = dp[i-1][i-1] + triangle.get(i).get(i);
        }

        int res = dp[high - 1][0];
        for (int i = 1; i < high; i++) {
            res = Math.min(res,dp[high-1][i]);
        }

        return res;
    }
}
