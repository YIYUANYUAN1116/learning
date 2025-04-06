package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-23 16:17
 **/
public class LC55 {
    public static void main(String[] args) {
        int[] dp = {3, 2, 1, 0, 4};
        System.out.println(canJump(dp));
    }

    public static boolean canJump(int[] nums) {
        int len = nums.length;
        int rightMost = 0;
        for (int i = 0; i < len; i++) {
            if (i <= rightMost){
                rightMost = Math.max(rightMost,i+nums[i]);
                if (rightMost  > len - 1){
                    return true;
                }
            }
        }
        return false;
    }

}
