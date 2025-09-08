package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-19 11:14
 **/
public class LC153 {
    public static void main(String[] args) {
        System.out.println(findMin(new int[]{3,1,2}));
    }

    public static int findMin(int[] nums) {
        int min = 0,max = nums.length -1;
        while (min < max){
            int mid = (max - min)/2 +min;
            if (nums[mid] < nums[max]){
                max = mid;
            }else {
                min = mid + 1;
            }
        }
        return nums[min];
    }
}
