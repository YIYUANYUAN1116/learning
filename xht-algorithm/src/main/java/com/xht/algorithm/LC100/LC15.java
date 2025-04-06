package com.xht.algorithm.LC100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-17 13:09
 **/
public class LC15 {
    public static void main(String[] args) {
        int[] ints = {-1, 0, 1, 2, -1, -4};
        System.out.println(threeSum(ints));
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i-1])continue; //去重

            int left = i+1,right = nums.length-1;
            int target = -nums[i];
            while (left < right){
                int sum = nums[left] + nums[right];
                if (sum == target){
                    res.add(Arrays.asList(nums[i],nums[left],nums[right]));
                    left++;
                    right--;
                    while (left < right && nums[left] == nums[left-1])left++; //去重
                    while (left < right && nums[right] == nums[right+1])right--; //去重
                }else if (sum < target){
                    left++;
                }else {
                    right--;
                }
            }
        }
        return res;
    }
}
