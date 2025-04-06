package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-05 10:15
 **/
public class LC101 {
    public static void main(String[] args) {

    }
    public boolean isSymmetric(TreeNode root) {
        return check(root.left,root.right);
    }

    private boolean check(TreeNode left, TreeNode right) {
        if (left == null && right == null){
            return true;
        }
        if (left == null || right == null){
            return false;
        }
        return left.val == right.val && check(left.left,right.right) && check(left.right,right.left);
    }

    private boolean check2(TreeNode left, TreeNode right){
        if (left == null  && right == null){
            return true;
        }

        if (left == null  || right == null){
            return false;
        }

        return left.val==right.val && check2(left.left,right.right) && check2(left.right,right.left);
    }
}
