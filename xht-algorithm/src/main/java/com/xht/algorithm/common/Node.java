package com.xht.algorithm.common;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-25 09:32
 **/
public class Node {
    public int val;
    public Node next;
    public Node random;

    public Node(int x) {
        val = x;
        next = null;
        random = null;
    }
    Node(int val, Node next , Node random) { this.val = val; this.next = next; this.random = random;}
}
