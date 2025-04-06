package com.xht.algorithm.LC100;

import com.xht.algorithm.common.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-25 09:31
 **/
public class LC138 {
    public static void main(String[] args) {

    }

    public  static  Map<Node, Node> cachedNode = new HashMap<Node, Node>();
    public static Node copyRandomList(Node head) {
        if (head == null) return null;

        if (!cachedNode.containsKey(head)){
            Node node = new Node(head.val);
            cachedNode.put(head,node);
            node.next = copyRandomList(head.next);
            node.random = copyRandomList(head.random);

        }

        return cachedNode.get(head);
    }
}
