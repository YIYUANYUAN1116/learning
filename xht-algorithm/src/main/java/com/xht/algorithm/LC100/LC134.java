package com.xht.algorithm.LC100;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-24 11:12
 **/
public class LC134 {
    public static void main(String[] args) {
        System.out.println(canCompleteCircuit2(new int[]{4,5,3,1,4},new int[]{5,4,3,4,2}));
    }

    public static int canCompleteCircuit(int[] gas, int[] cost) {

        int res = -1;
        for (int i = 0; i < gas.length; i++) {
            int curPosition = i;
            int curGas = 0;
            do {
                curGas = curGas + gas[curPosition] - cost[curPosition];
                curPosition++;
                if (curPosition == gas.length) curPosition = 0;
                if (curPosition == i && curGas>=0) return i;

            } while (curGas > 0);
            i+=curPosition-1;
        }
        return res;
    }

    public static int canCompleteCircuit2(int[] gas, int[] cost) {
        int n=gas.length;
        int sum=0;
        int count=0;
        int res=0;
        for(int i=0;i<n;i++){
            sum+=gas[i]-cost[i];
            count+=gas[i]-cost[i];
            if(sum<0){
                sum=0;
                res=1+i;
            }
        }
        if(count>=0){
            return res;
        }
        return -1;
    }
}
