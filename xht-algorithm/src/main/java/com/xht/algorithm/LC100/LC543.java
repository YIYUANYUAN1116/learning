package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-05 10:31
 **/
public class LC543 {
    public static void main(String[] args) {

    }

    public int maxDepth = 0;
    public int diameterOfBinaryTree(TreeNode root) {
       return dfs(root);
    }

    private int dfs(TreeNode root) {
        if (root == null)return 0;
        int left = dfs(root.left);
        int right = dfs(root.right);
        maxDepth = Math.max(maxDepth,left+right);
        return Math.max(left,right)+1;
    }

    private int maxDepth2(TreeNode root) {
        if (root == null) return  0;
        int left = maxDepth2(root.left);;
        int right = maxDepth2(root.right);
        maxDepth = Math.max(right+left,maxDepth);
        return Math.max(left,right)+1;
    }
}
