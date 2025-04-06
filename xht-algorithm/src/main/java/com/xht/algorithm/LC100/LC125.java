package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-17 11:27
 **/
public class LC125 {
    public static void main(String[] args) {

    }

    public static boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)){
                sb.append(Character.toLowerCase(c));
            }
        }

        int left = 0 , right = sb.length()-1;
        while (left <= right){
            if (sb.charAt(left) != sb.charAt(right))return false;
            left++;
            right--;
        }
        return true;
    }
}
