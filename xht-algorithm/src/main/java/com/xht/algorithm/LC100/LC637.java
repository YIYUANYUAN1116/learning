package com.xht.algorithm.LC100;

import com.xht.algorithm.common.TreeNode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class LC637 {
    public static void main(String[] args) {

    }

    public List<Double> averageOfLevels(TreeNode root) {
        Queue<TreeNode> stack = new LinkedList<>();
        List<Double> res = new ArrayList<>();
        stack.offer(root);
        while(!stack.isEmpty()){
            int size = stack.size();
            double sum = 0.0;
            for(int i = 0; i < size; i++){
                TreeNode pop = stack.poll();
                sum += pop.val;
                if(pop.left != null){
                    stack.offer(pop.left);
                }
                if(pop.right != null){
                    stack.offer(pop.right);
                }
            }
            res.add(sum/size);
        }
        return res;
    }

    public List<Double> averageOfLevels2(TreeNode root) {
        List<Double> averages = new ArrayList<Double>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        LinkedList<Object> objects = new LinkedList<>();
        objects.push(root);
        queue.offer(root);
        while (!queue.isEmpty()) {
            double sum = 0;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sum += node.val;
                TreeNode left = node.left, right = node.right;
                if (left != null) {
                    queue.offer(left);
                }
                if (right != null) {
                    queue.offer(right);
                }
            }
            averages.add(sum / size);
        }
        return averages;
    }

}
