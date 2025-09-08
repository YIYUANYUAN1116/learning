package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-31 12:25
 **/
public class LC230 {
    public static void main(String[] args) {

    }

    public int k,res;
    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        return res;
    }

    private void dfs(TreeNode root) {
        if (root == null)return;
        dfs(root.left);
        if (k == 0) return;
        if (--k == 0) res = root.val;
        dfs(root.right);
    }


    private void dfs2(TreeNode root){
        if (root == null)return;
        dfs(root.left);
        if (k == 0) return;
        if (--k == 0)res = root.val;
        dfs(root.right);
    }
}
