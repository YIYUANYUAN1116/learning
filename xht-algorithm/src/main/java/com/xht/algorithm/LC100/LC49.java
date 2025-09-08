package com.xht.algorithm.LC100;

import java.util.*;

/**
 * @author : YIYUANYUAN
 * @date: 2024/11/30  12:20
 */

public class LC49 {

    public static void main(String[] args) {
//        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        String[] strs = {"eat"};
        List<List<String>> lists = groupAnagrams(strs);
        System.out.println(lists.toString());
    }


    /**
     * @Description: 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     * @Param: [strs]
     * @Return: java.util.List<java.util.List < java.lang.String>>
     * @Author: YIYUANYUAN
     * @Date: 2024/11/30
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> hashMap = new HashMap<>();
        for (String str : strs) {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            String sortStr = Arrays.toString(charArray);
            List<String> list = hashMap.getOrDefault(sortStr, new ArrayList<>());
            list.add(str);
            hashMap.put(sortStr,list);
        }
        return new ArrayList<>(hashMap.values());
    }
}
