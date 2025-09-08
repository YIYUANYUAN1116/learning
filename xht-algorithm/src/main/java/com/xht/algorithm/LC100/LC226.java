package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-04 16:18
 **/
public class LC226 {
    public static void main(String[] args) {

    }
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;

        return root;
    }
}
