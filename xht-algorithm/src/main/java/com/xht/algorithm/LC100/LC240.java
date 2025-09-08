package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-08 10:00
 **/
public class LC240 {
    public static void main(String[] args) {
        System.out.println(searchMatrix2(new int[][]{{1,1}}, 2));
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        for (int[] ints : matrix) {
            if (search(ints, target)) {
                return true;
            }
        }
        return false;
    }

    public boolean search(int[] nums, int target) {
        int right = nums.length;
        int left = 0;
        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (nums[mid] == target) {
                return true;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    public static boolean searchMatrix2(int[][] matrix, int target) {
        //从右上角开始
        int row = 0;
        int col = matrix[0].length - 1;
        while (col >= 0 && row < matrix.length){
            int val = matrix[row][col];
            if (val == target)return true;
            if (val < target){
                row++;
            }else {
                col--;
            }
        }
        return false;
    }
}
