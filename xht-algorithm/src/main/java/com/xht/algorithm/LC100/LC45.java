package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-24 09:15
 **/
public class LC45 {
    public static void main(String[] args) {
        System.out.println(jump(new int[]{1,2,1,1,1}));
    }

    public static int jump(int[] nums) {
        int step = 0,maxPosition = 0,end = 0;
        for (int i = 0; i < nums.length-1; i++) {
            maxPosition = Math.max(maxPosition,nums[i] + i);
            if (i >= end){
                //i到num[i]这个区间，只需要跳一次
                end = maxPosition;
                step++;
            }
        }
        return step;
    }
}
