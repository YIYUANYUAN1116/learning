package com.xht.algorithm.dualpointers;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.List;

public class LC125 {
    public static void main(String[] args) {
        String s = "A man, a plan, a canal: Panama";
        System.out.println(isPalindrome(s));
    }

    /**
     * 125. 验证回文串
     * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
     * 字母和数字都属于字母数字字符。
     * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
     */
    public static boolean isPalindrome(String s){

        String lowerCase = s.toLowerCase();
        char[] charArray = lowerCase.toCharArray();
        List<Character> characters = new ArrayList<>();
        for (char c : charArray) {
            if (Character.isLetterOrDigit(c)) {
                characters.add(c);
            }
        }
        int left = 0;
        int right = characters.size()-1;
        while (left <= right){
            if (characters.get(left).equals(characters.get(right))){
                left++;
                right--;
            }else {
                return false;
            }
        }
        return true;
    }
}
