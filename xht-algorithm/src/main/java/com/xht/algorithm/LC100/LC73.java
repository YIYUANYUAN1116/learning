package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-06 10:58
 **/
public class LC73 {
    public static void main(String[] args) {

    }

    public void setZeroes(int[][] matrix) {
        int[][] ints = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0 && ints[i][j]==0){
                    int i1 = 0;
                    int j1 = 0;
                    while (i1 < matrix.length){
                        if ( matrix[i1][j] != 0){
                            matrix[i1][j] = 0;
                            ints[i1][j] = 1;
                        }
                        i1++;
                    }
                    while (j1 < matrix[i].length){
                        if (matrix[i][j1] != 0){
                            matrix[i][j1] = 0;
                            ints[i][j1] = 1;
                        }
                        j1++;
                    }
                }
            }
        }
    }

    public void setZeroes2(int[][] matrix){
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        boolean[] row = new boolean[rowLen];
        boolean[] col = new boolean[colLen];

        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (matrix[i][j] == 0){
                    row[i] = col[j] = true;
                }
            }
        }

        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (row[i] || col[j]){
                    matrix[i][j] = 0;
                }
            }
        }
    }
}
