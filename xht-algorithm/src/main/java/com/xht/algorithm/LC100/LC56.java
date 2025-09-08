package com.xht.algorithm.LC100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-29 10:31
 **/
public class LC56 {
    public static void main(String[] args) {
        merge(new int[][]{{1,4},{4,5}});
    }

    public static int[][] merge(int[][] intervals) {
        if (intervals.length <= 1)return intervals;


        //排序
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        List<int[]> res = new ArrayList<>();

        for (int[] interval : intervals) {
            int L = interval[0], R = interval[1];
            if (res.size() == 0 || res.get(res.size() - 1)[1] < L) {
                res.add(new int[]{L, R});
            } else {
                res.get(res.size() - 1)[1] = Math.max(res.get(res.size() - 1)[1], R);
            }
        }

        return res.toArray(new int[res.size()][]);
    }
}
