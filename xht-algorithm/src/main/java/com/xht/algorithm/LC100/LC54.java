package com.xht.algorithm.LC100;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-06 11:23
 **/
public class LC54 {
    public static void main(String[] args) {

    }

    public List<Integer> spiralOrder(int[][] matrix) {
        int r = matrix.length - 1;
        int b = matrix[0].length - 1;
        int t = 0;
        int l = 0;
        List<Integer> res = new ArrayList<>();
        while (true){
            for (int i = l; i <= r; i++)res.add(matrix[t][i]);
            if (++t > b)break;
            for (int i = t; i <= b; i++)res.add(matrix[i][r]);
            if (--r < l)break;
            for (int i = r; i >= l; i--)res.add(matrix[b][i]);
            if (--b < t)break;
            for (int i = b; i >= t; i--)res.add(matrix[i][l]);
            if (++l > r)break;
        }
        return res;

    }
}
