package com.xht.algorithm.LC100;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-22 11:15
 **/
public class LC438 {
    public static void main(String[] args) {

    }

    public List<Integer> findAnagrams(String s, String p) {
        int sLen = s.length(), pLen = p.length();

        if (sLen < pLen) {
            return new ArrayList<Integer>();
        }
        int[] sCount = new int[26];
        int[] pCount = new int[26];

        for (int i = 0; i < pLen; i++) {
            ++sCount[s.charAt(i)-'a'];
            ++pCount[p.charAt(i)-'a'];
        }
        List<Integer> res = new ArrayList<>();
        if (Arrays.equals(sCount,pCount)){
            res.add(0);
        }

        for (int i = 0; i < sLen-pLen; i++) {
            --sCount[s.charAt(i) - 'a'];
            ++sCount[s.charAt(i + pLen) - 'a'];
            if (Arrays.equals(sCount,pCount)){
                res.add(i+1);
            }
        }

        return res;

    }
}
