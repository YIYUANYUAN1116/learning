package com.xht.algorithm.LC100;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-16 20:16
 **/
public class LC560 {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 1, 1};
        System.out.println(subarraySum3(arr, 2));
    }

    public static int subarraySum(int[] nums, int k) {
        int count = 0;
        for (int left = 0; left < nums.length; left++) {
            int sum = 0;
            for (int right = left; right >= 0; right--) {
                sum += nums[right];
                if (sum == k) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int subarraySum2(int[] nums, int k) {
        int count = 0, len = nums.length;
        int[] pre = new int[len + 1];
        pre[0] = 0;
        for (int i = 0; i < len; i++) {
            pre[i + 1] = nums[i] + pre[i];
        }

        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                if (pre[j+1] - pre[i] == k){
                    count++;
                }
            }
        }
        return count;
    }

    public static int subarraySum3(int[] nums, int k) {
        int count = 0;
        Map<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(0,1);//刚好满足前缀和等于K的初始化值。比如 [1,1,1....] 前三个加起来等于3,k=3 时。
        int sum = 0;
        for (int num : nums) {
            sum+=num;
            if (hashMap.containsKey(sum - k)){
                count+=hashMap.get(sum-k);
            }
            hashMap.put(sum,hashMap.getOrDefault(sum,0)+1);
        }
        return  count;
    }

}
