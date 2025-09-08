package com.xht.algorithm.LC100;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-04 10:18
 **/
public class LC155 {
    public static void main(String[] args) {

    }

    Deque<Integer> minDeq;
    Deque<Integer> stack;

    public LC155() {
        minDeq = new LinkedList<>();
        stack = new LinkedList<>();
        minDeq.push(Integer.MAX_VALUE);
    }

    public void push(int val) {
        stack.push(val);
        minDeq.push(Math.min(minDeq.peek(),val));
    }

    public void pop() {
        stack.pop();
        minDeq.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minDeq.peek();
    }
}
