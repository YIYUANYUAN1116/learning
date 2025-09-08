package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-15 11:23
 **/
public class LC64 {
    public static void main(String[] args) {

    }

    public int minPathSum(int[][] grid) {
        int rowLen = grid.length;
        int colLen = grid[0].length;
        int[][] dp = new int[rowLen][colLen];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < colLen; i++) {
            dp[0][i] = dp[0][i-1] + grid[0][i];
        }
        for (int i = 1; i < rowLen; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for (int i = 1; i < rowLen; i++) {
            for (int j = 1; j < colLen; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i-1][j],dp[i][j-1]);
            }
        }

        return dp[rowLen -1][colLen-1];
    }
}
