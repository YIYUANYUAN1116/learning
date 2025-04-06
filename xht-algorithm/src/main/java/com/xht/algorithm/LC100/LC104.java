package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-04 16:08
 **/
public class LC104 {
    public static void main(String[] args) {

    }
    public int maxDepth(TreeNode root) {
        if (root == null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }

    public int maxDepth2(TreeNode root) {
        if (root == null){
            return 0;
        }
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(root);
        int depth = 0;
        while (!nodeQueue.isEmpty()){
            int size = nodeQueue.size();
            while (size>0){
                TreeNode poll = nodeQueue.poll();
                if (poll != null && poll.left != null){
                    nodeQueue.offer(poll.left);
                }
                if (poll != null && poll.right != null){
                    nodeQueue.offer(poll.right);
                }
                size--;
            }
            depth++;
        }

        return depth;

    }
}
