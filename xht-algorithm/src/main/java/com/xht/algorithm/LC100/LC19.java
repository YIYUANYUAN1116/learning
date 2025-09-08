package com.xht.algorithm.LC100;

import com.xht.algorithm.common.ListNode;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-22 11:03
 **/
public class LC19 {
    public static void main(String[] args) {

    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode res = new ListNode(0);
        ListNode slow =  res;
        slow.next = head;
        ListNode fast = head;
        int count = 1;
        while (fast != null){
            if (count < n){
                slow = slow.next;
            }
            count++;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return res.next;
    }
}
