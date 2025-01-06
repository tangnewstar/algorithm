package org.tangscode.algorithm.string_and_arrays.h_index;

class Solution {
    public int hIndex(int[] citations) {

        int h = 0;
        while (h < citations.length) {
            h++;
            int greaterOrEqualHCount = 0;
            for (int i = 0; i < citations.length; i++) {
                if (h <= citations[i]) {
                    greaterOrEqualHCount++;
                }
                if (greaterOrEqualHCount >= h) {
                    break;
                }
            }
            if (greaterOrEqualHCount < h) {
                h--;
                break;
            }
        }

        return h;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] inputs = {3,0,6,1,5,4,5};
        int hIndex = solution.hIndex(inputs);
        System.out.println(hIndex);
    }
    //https://leetcode.cn/problems/h-index/?envType=study-plan-v2&envId=top-interview-150

    /**
     * 解法1：从0至citations.length比较出h最大值。
     */
}
