package com.xht.algorithm.LC100;

import com.xht.algorithm.common.CommonUtil;

import java.util.Arrays;

/**
 * @Program: learning
 * @Description: 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。  请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 * @Author: YIYUANYUAN
 * @Create: 2024-12-01 17:03
 **/
public class LC283 {
    public static void main(String[] args) {
        // nums = [0,1,0,3,12]
        int[] nums = {0,1,0,3,12};
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void moveZeroes(int[] nums) {
        int zeroIndex = 0;
        for(int i = 0 ; i < nums.length;i ++){
            if(nums[i] != 0){
                CommonUtil.swap(nums,zeroIndex++,i);
            }
        }
    }
}
