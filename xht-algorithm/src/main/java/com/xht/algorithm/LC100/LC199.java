package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-01 09:04
 **/
public class LC199 {
    public static void main(String[] args) {

    }

    private List<Integer> res = new ArrayList<>();

    public List<Integer> rightSideView(TreeNode root) {
        dfs(root,0);
        return res;

    }

    private void dfs(TreeNode root,int deep) {
        if (root == null) return;
        if (res.size() == deep){
            res.add(root.val);
        }
        dfs(root.right,deep+1);
        dfs(root.left,deep+1);
    }

    private void dfs2(TreeNode root,int deep){
        if (root == null) return;
        if (res.size() == deep){
            res.add(root.val);
        }
        dfs2(root.right,deep+1);
        dfs2(root.left,deep+1);
    }
}
