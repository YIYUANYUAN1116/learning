package com.xht.algorithm.LC100;

import java.util.ArrayList;
import java.util.List;

public class LC46 {
    public static void main(String[] args) {
        int[] res = {1, 2, 3};
        List<List<Integer>> permute = permute(res);
        System.out.println(permute);
    }

    /**
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
     * [[0,1],[1,0]]
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        backTrace(res,new ArrayList<Integer>(),nums);

        return res;
    }

    public static void backTrace(List<List<Integer>> res, List<Integer> temp, int[] nums) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
        }
        for (int num : nums) {
            if (temp.contains(num)) continue;
            temp.add(num);
            backTrace(res, temp, nums);
            temp.remove(temp.size() - 1);
        }
    }
}
