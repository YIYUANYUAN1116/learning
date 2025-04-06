package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-03 11:39
 **/
public class LC94 {
    public static void main(String[] args) {

    }

    /**
     * 中序遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

    private void inorder(TreeNode root, List<Integer> res) {
        if (root == null)return;
        inorder(root.left,res);
        res.add(root.val);
        inorder(root.right,res);
    }
}
