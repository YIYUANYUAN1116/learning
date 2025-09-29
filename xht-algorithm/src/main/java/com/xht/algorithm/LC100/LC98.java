package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-31 11:47
 **/
public class LC98 {
    public static void main(String[] args) {

    }

    public boolean isValidBST(TreeNode root) {
        return dfs(root,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }

    public boolean dfs(TreeNode root,Integer lower,Integer upper){
        if (root == null) return true;
        if (root.val <= lower || root.val>=upper)return false;

        return dfs(root.left,lower,root.val) && dfs(root.right,root.val,upper);
    }

    public boolean dfs2(TreeNode root,Integer lower,Integer upper){
        if (root == null) return true;
        if (root.val <= lower || root.val>=upper)return false;
        return dfs2(root.left,lower,root.val) && dfs2(root.right,root.val,upper);
    }
}
