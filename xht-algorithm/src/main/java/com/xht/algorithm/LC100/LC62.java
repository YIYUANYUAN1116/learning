package com.xht.algorithm.LC100;

import java.util.Arrays;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-15 10:42
 **/
public class LC62 {
    public static void main(String[] args) {
        System.out.println(uniquePaths(3,7));
    }

    public static int res ;
    public static int uniquePaths(int m, int n) {
        int[][] grid = new int[m][n];
        dfs(grid,0,0);
        return res;
    }

    public static void dfs(int[][] grid,int row,int col){
        if (row > grid.length - 1 || row < 0 || col>grid[0].length - 1 || col < 0 || grid[row][col] == 1)return;
        grid[row][col] = 1;
        if (row == grid.length -1 && col == grid[0].length -1)res++;
        dfs(grid,row+1,col);
        dfs(grid,row,col+1);
        grid[row][col] = 0;
    }


    public static int uniquePaths2(int m, int n) {
        int[][] dp = new int[m][n];

        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }
}
