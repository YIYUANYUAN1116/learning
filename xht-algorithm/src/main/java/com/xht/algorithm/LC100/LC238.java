package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-22 12:12
 **/
public class LC238 {
    public static void main(String[] args) {

    }

    public int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        if (len == 0)return new int[0];
        int[] ans = new int[len];
        ans[0] = 1;
        int temp = 1;
        for (int i = 1; i < len; i++) {
            ans[i] = ans[i-1]*nums[i-1];
        }

        for (int i = len - 2; i >= 0; i--) {
            temp *= nums[i+1];
            ans[i] *= temp;
        }
        return ans;
    }
}
