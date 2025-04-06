package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description: 更高温度
 * @Author: YIYUANYUAN
 * @Create: 2025-04-06 10:36
 **/
public class LC739 {
    public static void main(String[] args) {

    }

    public int[] dailyTemperatures(int[] temperatures) {
        int len = temperatures.length;
        int[] res = new int[len];
        for (int i = len - 2; i >= 0; i--) {
            for (int j = i+1; j < len; j += res[j]) {
                if (temperatures[i]<temperatures[j]){
                    res[i] = j-i;
                    break;
                }else if (res[j] == 0){
                    break;
                }
            }
        }
        return res;
    }
}
