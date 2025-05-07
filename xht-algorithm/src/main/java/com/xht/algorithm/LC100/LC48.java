package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-07 10:27
 **/
public class LC48 {
    public static void main(String[] args) {

    }

    /**
     * 第i行  变成  len-1-i 列
     * 第j列  变成  第j行
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int len = matrix.length;
        int[][] temp = new int[len][len];
        for (int i = 0; i < len; i++) {
            temp[i] = matrix[i].clone();
        }

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[j][len - 1 - i] = temp[i][j];
            }
        }
    }


    public void rotate2(int[][] matrix) {
        int len = matrix.length;
        for (int i = 0; i < len/2; i++) {
            for (int j = 0; j < (len + 1)/2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[len - 1- j][i];
                matrix[len - 1- j][i] = matrix[len - 1- i][len - 1- j];
                matrix[len - 1- i][len - 1- j] = matrix[j][len - 1- i];
                matrix[j][len - 1- i] = temp;
            }
        }
    }
}
