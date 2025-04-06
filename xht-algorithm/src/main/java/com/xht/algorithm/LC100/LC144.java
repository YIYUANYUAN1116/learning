package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-01 09:24
 **/
public class LC144 {
    public static void main(String[] args) {

    }

    public void flatten(TreeNode root) {
        while (root != null) {
            if (root.left == null) {
                root = root.right;
            } else {
                TreeNode rootRight = root.right;
                TreeNode temp = root.left;
                while (temp.right != null) {
                    temp = temp.right;
                }
                temp.right = rootRight;
                root.right = root.left;
                root.left = null;
                root = root.right;
            }
        }
    }


}
