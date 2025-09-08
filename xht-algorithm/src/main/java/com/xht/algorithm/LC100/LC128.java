package com.xht.algorithm.LC100;

import java.util.HashSet;
import java.util.Set;

/**
 * @Program: learning
 * @Description: 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。  请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * @Author: YIYUANYUAN
 * @Create: 2024-12-01 16:36
 **/
public class LC128 {
    public static void main(String[] args) {
        //nums = [100,4,200,1,3,2]
        int[] nums = {100, 4, 200, 1, 3, 2};
        System.out.println(longestConsecutive(nums));
    }

    /**
     * @Description:
     * @Param: [nums]
     * @Return: int
     * @Author: YIYUANYUAN
     * @Date: 2024/12/1
     */
    public static int longestConsecutive(int[] nums) {

        Set<Integer> hashSet = new HashSet<>();
        for (int num : nums) {
            hashSet.add(num);
        }
        int res = 0;
        for (int num : nums) {
//            int cur = num;
//            int count = 1;
//            while (hashSet.contains(cur - 1)) {
//                cur = cur - 1;
//                count++;
//            }
//            res = Math.max(res, count);
            // 4 3 2 1  上面每个数都跑了一边while。优化：当存在 num - 1 时，跳过
            if (hashSet.contains(num - 1)) {
                continue;
            }
            int cur = num;
            int count = 1;
            while (hashSet.contains(cur + 1)) {
                cur += 1;
                count += 1;
            }
            res = Math.max(res, count);
        }
        return res;
    }
}
