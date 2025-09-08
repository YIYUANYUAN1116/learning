package com.xht.algorithm.LC100;

import com.xht.algorithm.common.ListNode;

/**
 * @Program: learning
 * @Description: 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 * @Author: YIYUANYUAN
 * @Create: 2025-02-18 19:53
 **/
public class LC142 {
    public static void main(String[] args) {

    }

    /**
     * 142. 环形链表 II
     * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head,slow = head;
        do {
            if (fast == null || fast.next == null) return null;
            fast = fast.next.next;
            slow = slow.next;
        } while (fast != slow);
        fast = head;
        while (slow.next != null){
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
