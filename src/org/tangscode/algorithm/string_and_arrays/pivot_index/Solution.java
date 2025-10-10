package org.tangscode.algorithm.string_and_arrays.pivot_index;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/10/10
 */
public class Solution {

    /**
     * 描述：寻找数组的中心索引（pivotIndex），中心索引左边与右边元素相加的和相等
     */

    public static void main(String[] args) {
        int[] array = new int[]{1, -1, 2};

        Solution solution = new Solution();
        System.out.println(solution.pivotIndex(array));
    }

    public int pivotIndex(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        int sumLeft = 0;
        for (int i = 0; i < nums.length; i++) {
            sum -= nums[i];
            // left equals right at index i
            // 这里的中心索引位置是不计算当前位置i的，所以sum先减然后移动到下一索引再计算左边
            if (sumLeft == sum) {
                return i;
            }
            sumLeft += nums[i];
        }

        return -1;
    }
}
