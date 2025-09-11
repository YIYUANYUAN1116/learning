package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-23 16:17
 **/
public class LC55 {
    public static void main(String[] args) {
        int[] dp = {3, 1, 2, 0, 4};
        System.out.println(canJump(dp));
    }

    /**
     * 给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
     * @param nums
     * @return
     */
    public boolean canJump2(int[] nums) {
        int len = nums.length;
        int rightMax = 0;
        for (int i = 0; i < len; i++) {
            if (i > rightMax) { // 关键：当前位置不可达
                return false;
            }
            rightMax = Math.max(rightMax, i + nums[i]);
            if (rightMax >= len - 1) {
                return true;
            }
        }
        return false;
    }


    public static boolean canJump(int[] nums) {
        int len = nums.length;
        int rightMost = 0;
        for (int i = 0; i < len; i++) {
            if (i <= rightMost){
                rightMost = Math.max(rightMost,i+nums[i]);
                if (rightMost  >= len - 1){
                    return true;
                }
            }
        }
        return false;
    }

}
