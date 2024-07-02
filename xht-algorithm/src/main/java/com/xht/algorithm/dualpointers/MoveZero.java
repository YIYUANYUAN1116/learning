package com.xht.algorithm.dualpointers;

import com.xht.algorithm.common.AlgorithmUtil;

import java.util.Arrays;

public class MoveZero {
    public static void main(String[] args) {
        int[] nums = {0,1,0,3,12};
        int[] nums2 = {1,2,0,3,12};
        int[] nums3 = {1,2,4,3,12};
        moveZero(nums);
        moveZero(nums2);
        moveZero(nums3);
        System.out.println(Arrays.toString(nums));
        System.out.println(Arrays.toString(nums2));
        System.out.println(Arrays.toString(nums3));
    }

    /**
     * 283. 移动零
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * @param nums
     */
    public static void moveZero(int[] nums){
        int zeroIndex = 0;
        for(int i = 0;i < nums.length; i++){
            if (nums[i] != 0){
               AlgorithmUtil.swap(nums,zeroIndex++,i);
            }
        }
    }
}
