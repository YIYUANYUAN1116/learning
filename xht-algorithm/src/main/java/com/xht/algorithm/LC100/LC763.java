package com.xht.algorithm.LC100;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-24 10:12
 **/
public class LC763 {
    public static void main(String[] args) {
      String  s = "ababcbacadefegdehijhklij";
        System.out.println(partitionLabels(s));
    }
    public static List<Integer> partitionLabels(String s) {

        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        List<Integer> res = new ArrayList<>();
        int maxPosition = 0;
        int preLen = 0;
        for (int i = 0; i < s.length(); i++) {
            char charAt = s.charAt(i);
            maxPosition = Math.max(maxPosition, last[charAt]);
            if (i == maxPosition){
                res.add(i+1-preLen);
                preLen = i + 1;
            }
        }
        return res;
    }
}
