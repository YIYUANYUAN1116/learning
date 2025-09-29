package com.xht.algorithm.LC100;
import java.util.ArrayList;
import java.util.List;

public class LC78 {
    public static void main(String[] args) {
        int[] ints = {1, 2, 3};
        System.out.println(subsets(ints));


    }

    /**
     * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
     * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backTrace(res,nums,0,new ArrayList<Integer>());
        return res;
    }

    public static void backTrace(List<List<Integer>> res, int[] nums,int start,List<Integer> temp) {
        res.add(new ArrayList<>(temp));
        for (int i = start; i < nums.length; i++) {
            temp.add(nums[i]);
            backTrace(res,nums,i+1,temp);
            temp.remove(temp.size()-1);
        }
    }

}
