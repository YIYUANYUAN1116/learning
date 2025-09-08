package com.xht.algorithm.LC100;

import java.util.Arrays;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-08 00:14
 **/
public class LC136 {
    public static void main(String[] args) {

    }
    public static int singleNumber(int[] nums) {
        int res = 0;
        for (int num : nums) {
            res ^= num;
        }
        return res;
    }
}
