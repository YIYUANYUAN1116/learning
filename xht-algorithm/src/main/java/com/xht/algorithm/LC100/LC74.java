package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-19 10:04
 **/
public class LC74 {
    public static void main(String[] args) {
        int[][] ints = {{1,3,5,7}, {10,11,16,20},{23,30,34,60}};
        System.out.println(searchMatrix(ints,13));
    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        int maxRow = matrix.length-1,minRow = 0;
        while (minRow <= maxRow){
            int midRow = (minRow+maxRow)/2;
            int maxCol = matrix[midRow].length-1;
            if (target < matrix[midRow][0]){
                maxRow = midRow-1;
            }else if (target > matrix[midRow][maxCol]){
                minRow = midRow+1;
            }else {
                int minCol = 0;
                while (minCol <= maxCol){
                    int midCol = (minCol + maxCol)/2;
                    if (target == matrix[midRow][midCol]){
                        return true;
                    }else if (target < matrix[midRow][midCol]){
                        maxCol = midCol -1;
                    }else {
                        minCol = midCol +1;
                    }
                }
                return false;
            }
        }
        return false;
    }
}
