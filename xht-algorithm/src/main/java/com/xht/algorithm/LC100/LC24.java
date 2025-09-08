package com.xht.algorithm.LC100;

import com.xht.algorithm.common.ListNode;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-22 11:34
 **/
public class LC24 {
    public static void main(String[] args) {

    }

    public ListNode swapPairs(ListNode head) {
        ListNode dump = new ListNode(0);
        dump.next = head;
        ListNode temp = dump;
        while (temp.next != null && temp.next.next!=null){ //0,1,2
            ListNode next1 = temp.next;
            ListNode next2 = temp.next.next;
            temp.next = next2;
            next1.next = next2.next;
            next2.next = next1;
            temp = next1;
        }
        return dump.next;
    }
}
