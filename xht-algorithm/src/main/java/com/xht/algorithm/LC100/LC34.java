package com.xht.algorithm.LC100;

import java.util.Arrays;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-19 10:23
 **/
public class LC34 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(searchRange(new int[]{1}, 1)));
    }

    public static int[] searchRange(int[] nums, int target) {
        int min = 0,max = nums.length - 1;
        while (min <= max){
            int mid = (max - min)/2 +min;
            if (nums[mid] == target){
                int start = mid,end = mid;
                while (start >= 0 && nums[start] == target){
                    start--;
                }
                while (end < nums.length && nums[end] == target){
                    end++;
                }
                return new int[]{start+1,end-1};
            }else if (nums[mid] < target){
                min = mid + 1;
            }else {
                max = mid -1;
            }
        }

        return new int[]{-1,-1};
    }
}
