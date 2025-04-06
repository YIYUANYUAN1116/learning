package com.xht.algorithm.OD;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-10 17:23
 **/
public class ZhaoPengYou {
    public static void main(String[] args) {
        System.out.println(taxiFares(49));
    }

    /**
     * 出租车计费
     *
     * @param n
     * @return
     */
    public static int taxiFares(int n) {
        //1.个位跳： +1，2。十位跳：+10
        int ans = n, temp = 0, k = 0, j = 1; //k代表跳了几次，j代表当前位数
        while (n > 0) {
            if (n % 10 > 4) {
                temp += (n % 10 - 1) * k + j;
            } else {
                temp += (n % 10) * k;
            }
            k = k * 9 + j;//k代表跳了多少次4，多收了多少个1元
            j *= 10;//j代表位数，1代表个位，10代表十位
            n /= 10;//相当于将N整体右移一位
        }
        return ans - temp;
    }


    public static int taxiFares2(int n) {
        int ans = n, temp = 0, k = 0, j = 1; //k代表跳了几次，j代表当前位数

        while (n > 0) {
            if (n % 10 > 4) {
                temp = (n % 10 - 1) * k + j;
            } else {
                temp = (n % 10) * k;
            }
            k = k * 9 + j;
            j *= 10;
            n /= 10;
        }
        return ans - temp;
    }
}
