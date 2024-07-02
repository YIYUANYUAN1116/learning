package com.xht.algorithm.common;

public class AlgorithmUtil {

    public static void swap (int[] nums,int x,int y){
        int xNum = nums[x];
        nums[x] = nums[y];
        nums[y] = xNum;
    }
}
