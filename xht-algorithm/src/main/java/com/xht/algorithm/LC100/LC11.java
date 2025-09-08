package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description: 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。  找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。  返回容器可以储存的最大水量。
 * @Author: YIYUANYUAN
 * @Create: 2024-12-07 17:28
 **/
public class LC11 {
    public static void main(String[] args) {
//        int[] nums = {1,8,6,2,5,4,8,3,7};
        int[] nums = {1, 1};
        System.out.println(maxArea(nums));
    }

    public static int maxArea(int[] height) {
        int res = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            int curArea = Math.min(height[left], height[right]) * (right-left);
            res = Math.max(res, curArea);
            if (height[left] > height[right]) {
                right--;
            } else {
                left++;
            }
        }
        return res;
    }
}
