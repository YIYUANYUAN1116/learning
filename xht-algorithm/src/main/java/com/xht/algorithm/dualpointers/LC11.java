package com.xht.algorithm.dualpointers;

public class LC11 {
    public static void main(String[] args) {

    }

    /**
     * 11. 盛最多水的容器
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     * 返回容器可以储存的最大水量。
     * @param height 左右指针从两边向内走
     */
    public void LC11Method(int[] height){
        int left = 0,maxArea = 0;
        int right = height.length-1;
        while (left < right){
//            maxArea = Math.max(maxArea, (right - left) * Math.min(height[left],height[right]));
//            if (height[left] < height[right]){
//                left++;
//            }else {
//                right--;
//            }
            maxArea = Math.max(maxArea,height[left]<height[right]?(right - left)*height[left++]:(right - left)*height[right--]);
        }
    }
}
