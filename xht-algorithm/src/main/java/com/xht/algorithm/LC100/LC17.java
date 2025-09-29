package com.xht.algorithm.LC100;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LC17 {
    public static void main(String[] args) {
        System.out.println(letterCombinations(""));
    }

    public static List<String> letterCombinations(String digits) {
        Map<Character, List<String>> map = new HashMap<>();
        map.put('2', List.of("a","b","c"));
        map.put('3', List.of("d","e","f"));
        map.put('4', List.of("g","h","i"));
        map.put('5', List.of("j","k","l"));
        map.put('6', List.of("m","n","o"));
        map.put('7', List.of("p","q","r","s"));
        map.put('8', List.of("t","u","v"));
        map.put('9', List.of("w","x","y","z"));
        List<String> res = new ArrayList<>();
        if (digits.isEmpty()) {
            return res;
        }
        char[] charArray = digits.toCharArray();
        List<List<String>> lists = new ArrayList<>();
        for (char c : charArray) {
            lists.add(map.getOrDefault(c, new ArrayList<>()));
        }
        backTrace(lists,res,new StringBuffer(),0);
        return res;
    }

    private static void backTrace(List<List<String>> digitStrings, List<String> res,StringBuffer temp,int start) {
        if (temp.length() == digitStrings.size()) {
            res.add(temp.toString());
            return;
        }
        List<String> strings = digitStrings.get(start);
        for (String string : strings) {
            temp.append(string);
            backTrace(digitStrings, res, temp, start + 1);
            temp.deleteCharAt(temp.length() - 1);
        }
    }
}
