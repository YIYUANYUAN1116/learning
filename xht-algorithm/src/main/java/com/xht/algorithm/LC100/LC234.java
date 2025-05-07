package com.xht.algorithm.LC100;

import com.xht.algorithm.common.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-24 10:36
 **/
public class LC234 {
    public static void main(String[] args) {

    }

    public boolean isPalindrome(ListNode head) {
        List<Integer> arrayList = new ArrayList<>();
        ListNode cur = head;
        while (cur != null){
            arrayList.add(cur.val);
            cur = cur.next;
        }

        int left = 0,right = arrayList.size()-1;
        while (left < right){
            if (arrayList.get(left).equals(arrayList.get(right))){
                left++;
                right--;
            }else {
                return false;
            }
        }
        return true;
    }
}
