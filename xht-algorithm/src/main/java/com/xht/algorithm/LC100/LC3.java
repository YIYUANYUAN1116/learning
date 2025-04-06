package com.xht.algorithm.LC100;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-18 15:37
 **/
public class LC3 {
    public static void main(String[] args) {

    }

    public int lengthOfLongestSubstring(String s) {
        int ans = 0, left = 0, right = 0;
        Map<Character, Integer> map = new HashMap<>();
        while (right < s.length()) {
            char c = s.charAt(right);
            if (map.containsKey(c)) {
                left = Math.max(left,map.get(c)+1);
            }
            map.put(c, right);
            ans = Math.max(ans, right - left + 1);
            right++;
        }
        return ans;
    }
}
