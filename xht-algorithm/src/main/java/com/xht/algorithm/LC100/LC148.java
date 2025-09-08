package com.xht.algorithm.LC100;

import com.xht.algorithm.common.ListNode;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-25 09:55
 **/
public class LC148 {
    public static void main(String[] args) {

    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null)return head;
        ListNode fast = head.next,slow = head.next;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        ListNode mid = slow.next;
        slow.next = null;
        ListNode node1 = sortList(head);
        ListNode node2 = sortList(mid);
        ListNode temp = new ListNode(0);
        ListNode res = temp;
        while (node1 != null && node2 != null){
            if (node1.val < node2.val){
                temp.next = node1;
                node1 = node1.next;
            }else {
                temp.next = node2;
                node2 = node2.next;
            }
            temp = temp.next;
        }
        temp.next = node1 == null?node2:node1;
        return res.next;
    }
}
