package com.xht.algorithm.LC100;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : YIYUANYUAN
 * @date: 2024/11/30  12:05
 */
public class LC1 {
    public static void main(String[] args) {
//        nums = [2,7,11,15], target = 9
        int[] nums = new int[] {2,7,11,15};
        int target = 9;
        int[] res = twoSum(nums, target);
        System.out.println(Arrays.toString(res));
    }
    
    /**
     * @Author: yzd
     * @Date: 2024/11/30
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
    */
    public static int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        Map<Integer,Integer> hashMap = new HashMap<>();
        for(int i = 0;i<nums.length;i++){
            if(hashMap.containsKey(target - nums[i])){
                res[0] = i;
                res[1] = (int) hashMap.get(target - nums[i]);
            }
            hashMap.put(nums[i],i);
        }
        return res;
    }

}
