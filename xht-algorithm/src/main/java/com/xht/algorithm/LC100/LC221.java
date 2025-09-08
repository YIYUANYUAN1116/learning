package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-16 10:32
 **/
public class LC221 {
    public static void main(String[] args) {
        char[][] ints = {{'0', '1'}, {'1', '0'}};
        System.out.println(maximalSquare(ints));
    }

    public static int maximalSquare(char[][] matrix) {
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int[][] dp = new int[rowLen + 1][colLen + 1];

        int sqLen = 0;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (matrix[i][j] == '1') {
                    int min = Math.min(dp[i][j], Math.min(dp[i + 1][j], dp[i][j + 1]));
                    dp[i + 1][j + 1] = min + 1;
                }
                sqLen = Math.max(sqLen, dp[i + 1][j + 1]);

            }
        }

        //["0","1"],
        //["1","0"]

        return sqLen * sqLen;
    }
}
