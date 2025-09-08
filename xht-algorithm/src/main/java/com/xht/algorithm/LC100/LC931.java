package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-16 10:12
 **/
public class LC931 {
    public static void main(String[] args) {

    }

    public int minFallingPathSum(int[][] matrix) {
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int[][] dp = new int[rowLen][colLen];
        for (int i = 0; i < colLen; i++) {
            dp[0][i] = matrix[0][i];
        }

        for (int i = 1; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (j == 0){
                    dp[i][j] = Math.min(dp[i-1][j],dp[i-1][j+1])+matrix[i][j];
                }else if (j == colLen -1){
                    dp[i][j] = Math.min(dp[i-1][j],dp[i-1][j-1])+matrix[i][j];
                }else {
                    int min = Math.min(dp[i-1][j-1],dp[i-1][j]);
                    min = Math.min(min,dp[i-1][j+1]);
                    dp[i][j] = min + matrix[i][j];
                }
            }
        }
        
        int res = dp[rowLen-1][0];
        for (int i = 1; i < colLen; i++) {
            res = Math.min(dp[rowLen-1][i] ,res);
        }
        
        return res;
    }
}
