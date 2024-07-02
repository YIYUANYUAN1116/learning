package com.xht.algorithm.dualpointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LC15 {
    public static void main(String[] args) {

    }

    /**
     * 15. 三数之和
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
     * 你返回所有和为 0 且不重复的三元组。
     * @param nums
     * @return
     */
    public List<List<Integer>> LC15Method(int[] nums){
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] > 0 )return res;
            if(i > 0 && nums[i] == nums[i-1]) continue;
            int l = i + 1,r = nums.length-1;
            while (l<r){
                int sum = nums[l]+nums[i]+nums[r];
                if (sum == 0){
                    res.add(Arrays.asList(nums[l], nums[i], nums[r]));
                    while (l < r && nums[l]==nums[l+1])l++;
                    while (l < r && nums[r]==nums[r-1])r--;
                    l++;
                    r--;
                }else if (sum < 0){
                    l++;
                }else {
                    r--;
                }
            }
        }
        return res;
    }
}
