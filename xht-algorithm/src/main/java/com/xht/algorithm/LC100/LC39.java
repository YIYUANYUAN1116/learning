package com.xht.algorithm.LC100;

import java.util.ArrayList;
import java.util.List;

public class LC39 {
    public static void main(String[] args) {
        int[] ints = {2, 3, 6, 7};
        System.out.println(combinationSum(ints, 7));
    }

    /**
     * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
     * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
     * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
     * @param candidates
     * @param target
     * @return
     */
    public static   List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < candidates.length; i++) {
            dfs(res,candidates,target,new ArrayList<>(),i);
        }
        return res;
    }

    private static void dfs(List<List<Integer>> res, int[] candidates, int target, ArrayList<Integer> temp,int curIndex) {
        if(curIndex > candidates.length-1 || target < 0)return;
        int candidate = candidates[curIndex];

        if(target == 0){
            res.add(new ArrayList<>(temp));
            return;
        }
        if (candidate - target >= 0){
            temp.add(candidate);
            dfs(res,candidates,target-candidate,temp,curIndex+1);
            temp.remove(temp.size()-1);
        }

    }
}
