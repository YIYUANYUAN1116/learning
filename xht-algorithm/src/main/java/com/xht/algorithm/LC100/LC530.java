package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;


public class LC530 {
    public static void main(String[] args) {

    }

    int res = Integer.MAX_VALUE;
    Integer pre = -1;

    public int getMinimumDifference(TreeNode root) {
       dfs(root);
       return res;
    }

    public void dfs(TreeNode root) {
        if (root == null)return;
        dfs(root.left);
        if (pre != -1) {
            res = Math.min(res, root.val - pre);
        }
        pre = root.val;
        dfs(root.right);
    }
}
