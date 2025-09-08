package com.xht.algorithm.LC100;

import com.xht.algorithm.common.ListNode;

import java.util.Objects;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-24 10:19
 **/
public class LC160 {
    public static void main(String[] args) {

    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode A = headA;
        ListNode B = headB;
        while (A != B){
            A = A == null?headB:A.next;
            B = B == null?headA:B.next;
        }
        return A;
    }
}
