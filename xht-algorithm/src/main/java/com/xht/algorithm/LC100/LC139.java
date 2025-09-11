package com.xht.algorithm.LC100;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-20 10:25
 **/
public class LC139 {
    public static void main(String[] args) {
        String s = "abc";
        System.out.println(s.substring(0,1));
        boolean[] booleans = new boolean[10];
        System.out.println(booleans[1]);

        String s1 = "aaaaaaa";
        ArrayList<String> strings = new ArrayList<>();
        strings.add("aaaa");
        strings.add("aaa");
        System.out.println(wordBreak(s1,strings));
    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);

        boolean[] dp = new boolean[s.length() + 1];
        int length = s.length();
        dp[0] = true;
        for (int i = 1; i <= length; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && set.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[length];
    }


    public static boolean wordBreak2(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        Set<String> set = new HashSet<>(wordDict);
        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && set.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
