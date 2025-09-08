package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-01 09:50
 **/
public class LC105 {
    public static void main(String[] args) {

    }

    private Map<Integer,Integer> indexMap = new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i],i);
        }
        return myBuildTree(preorder,inorder,0,preorder.length-1,0,inorder.length-1);
    }

    public TreeNode myBuildTree(int[] preorder, int[] inorder,int pl,int pr,int il,int ir) {
        if (pl > il)return null;

        int rootVal = preorder[pl];
        TreeNode root = new TreeNode(rootVal);
        Integer rootIndex = indexMap.get(rootVal);
        int rootLeftSize = rootIndex - il;

        root.left = myBuildTree(preorder,inorder,pl+1,pl+rootLeftSize,il,rootIndex-1);
        root.right = myBuildTree(preorder,inorder,pl+rootLeftSize+1,pr,rootIndex+1,ir);
        return root;
    }
}
