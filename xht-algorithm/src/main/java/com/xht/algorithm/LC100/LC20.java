package com.xht.algorithm.LC100;

import java.util.*;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-06 14:50
 **/
public class LC20 {
    public static void main(String[] args) {
        String s = "([])";
        System.out.println( isValid(s));

    }

    public static boolean isValid(String s) {
        if (s.length()%2 == 1)return false;
        Map<Character, Character> hashMap = new HashMap<>();
        hashMap.put('(', ')');
        hashMap.put('[', ']');
        hashMap.put('{', '}');
        Stack<Character> stack = new Stack<>();
        for (Character c : s.toCharArray()) {
            if (hashMap.containsKey(c)){
                stack.push(c);

            }else {
                if (stack.isEmpty() || !hashMap.get(stack.pop()).equals(c)){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
