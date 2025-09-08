package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-19 10:50
 **/
public class LC33 {
    public static void main(String[] args) {

    }

    public int search(int[] nums, int target) {
        int min = 0,max = nums.length-1;
        while (min <= max){
            int mid = (max - min)/2 + min;
            if (nums[mid] == target){
                return mid;
            }else if (nums[0] <= nums[mid]){//说明0到mid是有序的
                if (nums[0]<=target && target < nums[mid]){ //说明target在这个区间
                    max = mid - 1;
                }else {
                    min = mid + 1;
                }
            }else {
                if (nums[nums.length - 1]>=target && target > nums[mid]){ //说明target在这个区间
                    min = mid + 1;
                }else {
                    max = mid - 1;
                }
            }
        }
        return -1;
    }
}
