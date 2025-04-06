package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-17 11:47
 **/
public class LC392 {
    public static void main(String[] args) {

    }

    public boolean isSubsequence(String s, String t) {
        int left = 0,right = 0;
        while (left < s.length() && right < t.length()){
            if (s.charAt(left) == t.charAt(right)){
                left++;
            }
            right++;
        }
        return left == s.length();
    }
}
