package com.xht.algorithm.LC100;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-16 11:49
 **/
public class LC5 {
    public static void main(String[] args) {

    }

    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }

        int maxLen = 1;
        int begin = 0;
        //dp[i][j] 表是 字符串  i 到 j 是否是回文
        Boolean[][] dp = new Boolean[len][len];
        char[] array = s.toCharArray();
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        for (int L = 2; L <= len; L++) { //字符串长度
            for (int i = 0; i < len; i++) { //左边界
                // j是右边界  j-i+1=L
                int j = i + L - 1;

                if (j >= len)break;

                if (array[i] != array[j]){
                    dp[i][j] = false;
                }else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                if (dp[i][j] && j-i+1>maxLen){
                    maxLen = j - i + 1;
                    begin = i;
                }

            }
        }

        return s.substring(begin,begin+maxLen);

    }
}
