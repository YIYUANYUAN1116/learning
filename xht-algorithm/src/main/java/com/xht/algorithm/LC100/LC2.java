package com.xht.algorithm.LC100;

import com.xht.algorithm.common.ListNode;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-22 10:49
 **/
public class LC2 {
    public static void main(String[] args) {

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = new ListNode(0);
        ListNode head = res;
        ListNode head1 = l1;
        ListNode head2 = l2;
        int plus = 0;
        while (head1!=null && head2!=null){
            int sum = head1.val + head2.val + plus;
            plus = sum/10;
            head1 = head1.next;
            head2 = head2.next;
            head.next = new ListNode(sum%10);
            head = head.next;
        }

        while (head1 != null){
            int sum = head1.val+plus;
            plus = sum/10;
            head1 = head1.next;
            head.next = new ListNode(sum%10);
            head = head.next;
        }

        while (head2 != null){
            int sum = head2.val+plus;
            plus = sum/10;
            head2 = head2.next;
            head.next = new ListNode(sum%10);
            head = head.next;
        }

        if (plus != 0){
            head.next = new ListNode(plus);
        }

        return res.next;
    }
}
