package org.tangscode.algorithm.string_and_arrays.rotate_array;

import java.util.Arrays;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2024/10/22
 */
public class Solution {

    /*
     189. 轮转数组
     中等
     相关标签
     相关企业
     提示
     给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。

     示例 1:
     输入: nums = [1,2,3,4,5,6,7], k = 3
     输出: [5,6,7,1,2,3,4]
     解释:
     向右轮转 1 步: [7,1,2,3,4,5,6]
     向右轮转 2 步: [6,7,1,2,3,4,5]
     向右轮转 3 步: [5,6,7,1,2,3,4]
     示例 2:
     输入：nums = [-1,-100,3,99], k = 2
     输出：[3,99,-1,-100]
     解释:
     向右轮转 1 步: [99,-1,-100,3]
     向右轮转 2 步: [3,99,-1,-100]
     */

    public void rotate(int[] nums, int k) {
        int offset = k % nums.length;

        int[] new_nums = new int[nums.length];
        if (offset > 0) {
            for (int i = 0; i < new_nums.length; i++) {
                if (i < offset) {
                    new_nums[i] = nums[nums.length - offset + i];
                } else {
                    new_nums[i] = nums[i - offset];
                }
            }
            System.arraycopy(new_nums, 0, nums, 0, nums.length);
        }
    }

    /**
     思路：
     - 先对k取余，得到移动的offset
     - 使用新的数组存储, 将nums[nums.length - offset]移动至队首，其余元素移至队尾
     - 最后将nums=new_nums
     复杂度分析：时间复杂度O(n)

     官方题解：
     1. 使用额外的数组 new[(i+k) mod n] = nums[i]，把i位置的元素移动值(i+k) mod n 的位置
        时间复杂度：O(n) 空间复杂度: O(n)

     2. 环状替换
     由于最终回到了起点，故该过程恰好走了整数数量的圈，不妨设为 a 圈；再设该过程总共遍历了 b 个元素。因此，我们有 an=bk，即 an 一定为 n,k 的公倍数。又因为我们在第一次回到起点时就结束，因此 a 要尽可能小，故 an 就是 n,k 的最小公倍数 lcm(n,k)，因此 b 就为 lcm(n,k)/k。
     这说明单次遍历会访问到 lcm(n,k)/k 个元素。为了访问到所有的元素，我们需要进行遍历的次数为
     n / lcm(n,k)/k = nk / lcm(n,k) = gcd(n,k)
     其中 gcd 指的是最大公约数。
        时间复杂度：O(n) 空间复杂度：O(1)

     3. 数组翻转
     该方法基于如下的事实：当我们将数组的元素向右移动 k 次后，尾部 k mod n 个元素会移动至数组头部，其余元素向后移动 k mod n 个位置。
     该方法为数组的翻转：我们可以先将所有元素翻转，这样尾部的 k mod n 个元素就被移至数组头部，然后我们再翻转 [0,k mod n−1] 区间的元素和 [k mod   n,n−1] 区间的元素即能得到最后的答案。
        时间复杂度：O(n) 空间复杂度：O(1)
     聪明的解法

     */

    public static  void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {1,2,3,4,5};
        System.out.println("修改前：" + Arrays.toString(nums));
        solution.rotate3(nums, 3);
        System.out.println("修改后：" + Arrays.toString(nums));
    }

    public void rotate3(int[] nums, int k) {
        reverse(nums, 0 , nums.length - 1);
        reverse(nums, 0 , k - 1);
        reverse(nums, k, nums.length - 1);
    }
    
    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}
