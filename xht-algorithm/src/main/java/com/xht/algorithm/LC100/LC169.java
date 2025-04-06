package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-12 16:28
 **/
public class LC169 {
    public static void main(String[] args) {

    }
    public int majorityElement(int[] nums) {
        int res = 0,vote = 0;
        for (int num : nums) {
            if (vote == 0)res = num;
            vote += res==num?1:-1;
        }
        return res;
    }

}
