package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-06 15:28
 **/
public class LC108 {
    public static void main(String[] args) {

    }

    /**
     * 有序数组转为二叉搜索树
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return dfs(nums,0,nums.length-1);
    }

    private TreeNode dfs(int[] nums, int left, int right) {
        if (left > right)return null;
        int mid = (right - left) / 2 + left;
        TreeNode treeNode = new TreeNode(nums[mid]);
        treeNode.left = dfs(nums,left,mid-1);
        treeNode.right = dfs(nums,mid+1,right);
        return treeNode;
    }

    public TreeNode sortedArrayToBST2(int[] nums) {
        return dfs2(nums,0,nums.length-1);
    }

    private TreeNode dfs2(int[] nums, int left, int right) {
        if (left > right)return null;
        int mid = (right - left) / 2 + left;
        TreeNode treeNode = new TreeNode(nums[mid]);
        treeNode.left = dfs2(nums,left,mid-1);
        treeNode.right = dfs2(nums,mid+1,right);
        return treeNode;
    }
}
