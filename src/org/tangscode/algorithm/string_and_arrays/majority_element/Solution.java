package org.tangscode.algorithm.string_and_arrays.majority_element;

import java.util.Arrays;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2024/10/12
 */
public class Solution {

    /*
     169. 多数元素

     给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     你可以假设数组是非空的，并且给定的数组总是存在多数元素。



     示例 1：
     输入：nums = [3,2,3]
     输出：3
     示例 2：
     输入：nums = [2,2,1,1,1,2,2]
     输出：2

     提示：
     n == nums.length
     1 <= n <= 5 * 104
     -109 <= nums[i] <= 109
     */

    //link: https://leetcode.cn/problems/majority-element/description/?envType=study-plan-v2&envId=top-interview-150


    public int majorityElement(int[] nums) {

//        // solution 1: 数组排序
//        Arrays.sort(nums);
//        return nums[nums.length/2];

        // solution 3: Boyer-Moore
        int count = 0;
        Integer candidate = null;

        for (int num: nums) {
            if (count == 0) {
                candidate = num;
            }
            if (candidate == num) {
                count++;
            } else {
                count--;
            }
        }

        return candidate;
    }

    /*
    * 思路1：sort array first, then the n/2 index value

    * 思路2：HashMap统计，key为数字，value为出现次数

    * 最佳思路：Boyer-Moore，统计majority element
    *
    * 官方题解还有：
    *   - 随机化（随机抽取索引位置去查询出现次数），每次都有1/2的可能找到
    *   - 分治：二分数组，分别统计majority，如果相等则返回
    *
    *
     */

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {3,2,3};
        int result = solution.majorityElement(nums);
        assert result == 3;

        int[] nums2 = {2,2,1,1,1,2,2};
        int result2 = solution.majorityElement(nums2);
        assert result2 == 2;
    }
}
