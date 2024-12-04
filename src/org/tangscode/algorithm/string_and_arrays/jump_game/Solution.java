package org.tangscode.algorithm.string_and_arrays.jump_game;

import java.util.Arrays;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2024/11/28
 */
public class Solution {
    /*
    55. 跳跃游戏

    给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
    判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。

    示例 1：
    输入：nums = [2,3,1,1,4]
    输出：true
    解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
    示例 2：
    输入：nums = [3,2,1,0,4]
    输出：false
    解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。

    提示：
    1 <= nums.length <= 104
    0 <= nums[i] <= 105
    * */
    public boolean canJump(int[] nums) {
        if (nums[0] >= nums.length - 1) {
            return true;
        }

        //reverse loop
        for (int i = nums.length - 2; i >= 0; i--) {
            //can not jump to end
            if (nums[i] + i < nums.length - 1) {
                continue;
            }
            //can jump to end here, whether start can jump here
            int[] startToHere = Arrays.copyOf(nums, i + 1);
            boolean canJumpHere = canJump(startToHere);
            return canJumpHere;
        }

        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {2,3,1,1,4};
        boolean canJump = solution.canJump(nums);
        System.out.println(canJump);

        int[] nums2 = {3,2,1,0,4};
        boolean canJump2 = solution.canJump(nums2);
        System.out.println(canJump2);
    }

    /**
     * 解题思路
     * 逆序递归，将canJump拆解成两个问题，存在这样一个点
     * - 从这个点可以跳跃至最终点
     * - 从开始点可以跳跃至该点
     * 那么问题也就分解成了：canJump(nums) = canJump(nums[0,i]) & canJump(nums[i,nums.length-1])
     * 从最后点的前面一个点逆序去遍历nums
     *  - 如果当前点不能跳跃到结束，那么继续
     *  - 如果可以，则递归执行canJumpHere=canJump(nums[0,here])
     *
     * 复杂度分析：时间复杂度O(n^2) 空间复杂度O(n)
     *
     *
     */
}
