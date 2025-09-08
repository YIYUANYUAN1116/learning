package com.xht.algorithm.LC100;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-25 10:16
 **/
public class LRUCache {

    class DLinkedNode {
        private int key;

        private int value;
        private DLinkedNode next;
        private DLinkedNode prev;

        public DLinkedNode() {}
        public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
    }

    public  Map<Integer, DLinkedNode> cachedNode = new HashMap<Integer, DLinkedNode>();
    private Integer capacity;
    private Integer size;

    private DLinkedNode head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        // 使用伪头部和伪尾部节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;

    }

    public int get(int key) {
        DLinkedNode dLinkedNode = cachedNode.get(key);
        if (dLinkedNode == null){
            return -1;
        }
        moveToHead(dLinkedNode);

        return dLinkedNode.value;
    }



    public void put(int key, int value) {
        DLinkedNode dLinkedNode = cachedNode.get(key);
        if (dLinkedNode == null){
            DLinkedNode linkedNode = new DLinkedNode(key, value);
            cachedNode.put(key,linkedNode);
            addToHead(linkedNode);
            size++;
            if (size>capacity){
                DLinkedNode tail = removeTail();
                cachedNode.remove(tail.key);
                size--;
            }
        }else {
            dLinkedNode.value = value;
            moveToHead(dLinkedNode);
        }
    }

    private DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }

    private void moveToHead(DLinkedNode dLinkedNode) {
        removeNode(dLinkedNode);
        addToHead(dLinkedNode);
    }

    private void removeNode(DLinkedNode dLinkedNode){
        dLinkedNode.prev.next = dLinkedNode.next;
        dLinkedNode.next.prev = dLinkedNode.prev;
    }

    private void addToHead(DLinkedNode dLinkedNode){
        DLinkedNode next = head.next;
        head.next = dLinkedNode;
        dLinkedNode.prev = head;
        dLinkedNode.next = next;
        next.prev = dLinkedNode;
    }
}
