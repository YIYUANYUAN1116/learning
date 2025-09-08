package com.xht.algorithm.common;

/**
 * @Program: learning
 * @Description: 通用工具类
 * @Author: YIYUANYUAN
 * @Create: 2024-12-01 17:09
 **/
public class CommonUtil {

    /**
    * @Description: 交换数组位置
    * @Param: [nums, x, y]
    * @Return: void
    * @Author: YIYUANYUAN
    * @Date: 2024/12/1
    */
    public static void swap(int[] nums, int x, int y){
        int num = nums[x];
        nums[x] = nums[y];
        nums[y] = num;
    }
}
