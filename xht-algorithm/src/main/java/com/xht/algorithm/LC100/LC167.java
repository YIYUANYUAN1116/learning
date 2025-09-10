package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-17 12:07
 **/
public class LC167 {
    public static void main(String[] args) {

    }



    public int[] twoSum2(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left+1,right+1};
            }else if (sum < target) {
                left++;
            }else  {
                right--;
            }
        }
        return new int[]{};
    }

    public int[] twoSum(int[] numbers, int target) {
        int left = 0,right = numbers.length-1;
        while (left < right){
            int sum = numbers[left] + numbers[right];
            if (sum == target){
                return new int[]{left+1,right+1};
            }else if (sum < target){
                left++;
            }else {
                right--;
            }
        }
        return new int[]{};
    }
}
