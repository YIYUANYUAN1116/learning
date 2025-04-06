package com.xht.algorithm.LC100;

import java.util.Arrays;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-21 20:04
 **/
public class LC152 {
    public static void main(String[] args) {
        System.out.println( maxProduct(new int[]{-2,0,-1}));
    }

    public static int maxProduct(int[] nums) {
        //由于负负得正，所有需要维护一个最小值,当出现负数是，需要交换最小值和最大值
        int res = Integer.MIN_VALUE,min = 1,max = 1;
        for (int num : nums) {
            if (num < 0) {
                int temp = max;
                max = min;
                min = temp;
            }
            min = Math.min(num, min * num);
            max = Math.max(num, max * num);

            res = Math.max(res, max);
        }

        return res;
    }
}
