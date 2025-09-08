package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-19 11:50
 **/
public class LC162 {
    public static void main(String[] args) {
        System.out.println(findPeakElement(new int[]{1,2,3,1}));
    }

    public static int findPeakElement(int[] nums) {
        int min = 0, max = nums.length-1;
        while (min < max){
            int mid = (max - min)/2 + min;
            if (nums[mid] < nums[mid+1]){
                min = mid + 1;
            }else {
                max = mid;
            }
        }
        return min;
    }
}
