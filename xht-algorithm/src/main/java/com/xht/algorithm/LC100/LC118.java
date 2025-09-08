package com.xht.algorithm.LC100;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-07 22:49
 **/
public class LC118 {
    public static void main(String[] args) {
        System.out.println(generate(5));
    }

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> arrayList = new ArrayList<>();
        arrayList.add(List.of(1));
        for (int i = 1; i < numRows; i++) {
            List<Integer> row = new ArrayList<>(i + 1);
            row.add(1);
            for (int j = 1; j < i; j++) {
                row.add(arrayList.get(i-1).get(j-1) +arrayList.get(i-1).get(j)) ;
            }
            row.add(1);
            arrayList.add(row);
        }

        return arrayList;

    }
}
