package com.xht.algorithm.LC100;

public class LC36 {

    public static void main(String[] args) {

    }

    public boolean isValidSudoku(char[][] board) {
        int[][] rowHash = new int[9][9];
        int[][] colHash = new int[9][9];
        int[][][] boxHash = new int[3][3][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c != '.') {
                    int index = c - '0' + 1;
                    rowHash[i][index]++;
                    colHash[j][index]++;
                    boxHash[i / 3][j / 3][index]++;
                    if (rowHash[i][index] >1 || colHash[j][index] > 1 || boxHash[i / 3][j / 3][index] > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
