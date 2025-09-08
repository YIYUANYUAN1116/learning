package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-05 11:54
 **/
public class LC35 {
    public static void main(String[] args) {

    }

    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (nums[mid] <= target) {
               left = mid + 1;
            }else {
                right =  mid - 1;
            }
        }
        return left;
    }
}
