package com.xht.algorithm.dualpointers;

public class LC392 {
    public static void main(String[] args) {
        String s = "aaaaa";
        String t = "bbaaa";
        System.out.println(isSubsequence(s,t));
    }

    /**
     * 392. 判断子序列
     * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
     * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
     * @param s
     * @param t
     * @return
     */
    public static boolean isSubsequence(String s, String t) {
        if (s.length()>t.length())return false;
        if (s.length()==0)return true;
        int sIndex = 0;
        int tIndex = 0;
        while (sIndex<s.length() && tIndex < t.length() ){
            if (s.charAt(sIndex) == t.charAt(tIndex)){
                if (sIndex == s.length()-1){
                    return true;
                }
                sIndex++;
            }
            tIndex++;
        }
        return false;
    }
}
