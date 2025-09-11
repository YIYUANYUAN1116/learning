package com.xht.algorithm.LC100;

import java.util.Arrays;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-29 11:31
 **/
public class LC189 {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,6,7};
        rotate(nums,3);
        System.out.println(Arrays.toString(nums));
    }

    public void rotate2(int[] nums, int k) {
        int len = nums.length;
        int[] temp = new int[len];
        for (int i = 0; i < len; i++) {
            temp[(i+k)%len] = nums[i];
        }
        System.arraycopy(temp,0,nums,0,len);
    }

    public static void rotate(int[] nums, int k) {
        int len = nums.length;
        int[] temp = new int[len];
        for (int i = 0; i < len; i++) {
            temp[(i+k)%len] = nums[i];
        }
        System.arraycopy(temp,0,nums,0,len);
    }
}
