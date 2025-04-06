package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-18 15:18
 **/
public class LC209 {
    public static void main(String[] args) {

    }

    public int minSubArrayLen(int target, int[] nums) {
        int res = Integer.MAX_VALUE, right = 0, left = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum >= target) {
                    res = Math.min(res, j - i + 1);
                    break;
                }
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    public int minSubArrayLen2(int target, int[] nums) {
        int len = nums.length;
        if (len == 0) return 0;
        int res = Integer.MAX_VALUE, right = 0, left = 0,sum = 0;
        while (right < len){
            sum += nums[right];
            while (sum >= target ){
                res = Math.min(res,right-left+1);
                sum -= nums[left];
                left++;
            }

            right++;
        }

        return res == Integer.MAX_VALUE ? 0 : res;
    }
}
