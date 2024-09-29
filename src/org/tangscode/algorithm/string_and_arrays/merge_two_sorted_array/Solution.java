package org.tangscode.algorithm.string_and_arrays.merge_two_sorted_array;

import java.util.Arrays;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2024/9/26
 */
public class Solution {

    /*
    88. 合并两个有序数组
    提示
    给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
    请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
    注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。

    示例 1：
    输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
    输出：[1,2,2,3,5,6]
    解释：需要合并 [1,2,3] 和 [2,5,6] 。
    合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
    示例 2：

    输入：nums1 = [1], m = 1, nums2 = [], n = 0
    输出：[1]
    解释：需要合并 [1] 和 [] 。
    合并结果是 [1] 。
    示例 3：

    输入：nums1 = [0], m = 0, nums2 = [1], n = 1
    输出：[1]
    解释：需要合并的数组是 [] 和 [1] 。
    合并结果是 [1] 。
    注意，因为 m = 0 ，所以 nums1 中没有元素。nums1 中仅存的 0 仅仅是为了确保合并结果可以顺利存放到 nums1 中。

    提示：
    nums1.length == m + n
    nums2.length == n
    0 <= m, n <= 200
    1 <= m + n <= 200
    -109 <= nums1[i], nums2[j] <= 109
    */

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index = 0;
        for (int i = 0; i < n; i++) {

            //insert nums2[i] to num1[?]
            while(index < m + i) {
                if (nums1[index] > nums2[i]) {
                    break;
                }
                index++;
            }

            //shift right
            for (int j = m - 1 + i; j >= index; j--) {
                nums1[j + 1] = nums1[j];
            }
            //and index for nums2[n]
            nums1[index] = nums2[i];
        }
    }

    /*
    * 我的解法：
    *   复杂度：O(M*N)
    *   算法：遍历nums2,并移动nums1指针index，比较大小确定放入位置
    *
    * 官方解法：
    *   1. 直接合并后排序，将nums2放在nums1尾部，然后对nums1排序。
    *   时间复杂度：O((m+n)log(m+n)) 空间复杂度：O(log(m+n))，参考快排
    *
    *   2. 双指针，nums1和nums2已经排序。指针p1，p2作为队列头指针，比较头元素的值然后放入新的队列，再将新队列赋值到nums1
    *   时间复杂度：O(m+n) 空间复杂度：O(m+n)
    *
    *   3. 逆向双指针，nums1后面是空的，从尾部遍历数组，将nums1和nums2较大的值放入尾部。
    *   时间复杂度：O(m+n) 空间复杂度：O(1)
    *
    * 综上：最优解 => 逆向双指针
    *
    * */

    public static void main(String[] args) {
        Solution solution= new Solution();
        int[] nums1 = new int[]{1,2,3,0,0,0};
        int[] nums2 = new int[]{4,5,6};
        solution.merge(nums1, 3, nums2, 3);
        System.out.println(nums1);
    }
}
