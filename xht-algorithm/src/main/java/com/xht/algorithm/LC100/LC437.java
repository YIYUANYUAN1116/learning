package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-03 10:24
 **/
public class LC437 {
    public static void main(String[] args) {

    }

    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) return 0;

        int res = rootSum(root, targetSum);
        res += pathSum(root.left, targetSum);
        res += pathSum(root.right, targetSum);

        return res;

    }

    public int rootSum(TreeNode treeNode, int sum) {
        int ret = 0;

        if (treeNode == null) return 0;

        if (treeNode.val == sum) {
            ret++;
        }

        ret += rootSum(treeNode.left, sum - treeNode.val);
        ret += rootSum(treeNode.right, sum - treeNode.val);

        return ret;
    }


    public int pathSum2(TreeNode root, int targetSum) {
        if (root == null) return 0;
        Map<Long, Integer> hashMap = new HashMap<>();
        hashMap.put(0L, 1);


        return dfs(root, targetSum, hashMap, 0);

    }

    public int dfs(TreeNode root, long targetSum, Map<Long, Integer> prefix, long cur) {
        int res = 0;
        if (root == null) return 0;
        int val = root.val;
        cur += val;
        res +=prefix.getOrDefault(targetSum - cur,0);
        prefix.put(cur,prefix.getOrDefault(cur,0)+1);
        res += dfs(root.left,targetSum,prefix,cur);
        res += dfs(root.right,targetSum,prefix,cur);
        prefix.put(cur,prefix.getOrDefault(cur,0)-1);


        return res;
    }
}
