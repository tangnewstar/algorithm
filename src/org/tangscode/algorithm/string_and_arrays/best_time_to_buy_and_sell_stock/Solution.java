package org.tangscode.algorithm.string_and_arrays.best_time_to_buy_and_sell_stock;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2024/11/18
 */
public class Solution {

    /*
     121. 买卖股票的最佳时机
    简单
    给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
    你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
    返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。

    示例 1：
    输入：[7,1,5,3,6,4]
    输出：5
    解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
         注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
    示例 2：
    输入：prices = [7,6,4,3,1]
    输出：0
    解释：在这种情况下, 没有交易完成, 所以最大利润为 0。

    提示：
    1 <= prices.length <= 105
    0 <= prices[i] <= 104
     */

    public static void main(String[] args) {
        Solution solution = new Solution();
        int result = solution.maxProfit(new int[]{7,1,5,3,6,4});
        assert result == 5;
        int result1 = solution.maxProfit(new int[]{7,6,4,3,1});
        assert result1 == 0;
    }

    public int maxProfit(int[] prices) {
        //遍历元素，获取最大的差值。profit = prices[i] - lowestPriceHistory
        int lowestPriceHistory = prices[0];
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            int profit = i > 0 ? prices[i] - lowestPriceHistory : 0;
            if (profit > maxProfit) {
                maxProfit = profit;
            }
            if (lowestPriceHistory > prices[i]) {
                lowestPriceHistory = prices[i];
            }
        }
        return maxProfit;
    }

    /**
     * 官方题解分析
     * 1. 暴力法（超时）
     * 我们需要找出给定数组中两个数字之间的最大差值（即，最大利润）第二个数字（卖出价格）必须大于第一个数字（买入价格）。
     * 即：对于每组 i 和 j（其中 j>i）我们需要找出 max(prices[j]−prices[i])。
     * - i * j 去遍历数组，计算profit
     * - 找出最大的 maxProfit
     * 复杂度分析：时间：O(n^2) 空间：O(1)
     *
     * 2. 一次遍历（我的解法）
     * - 遍历prices, 将当前价格prices[i]和历史最低价格lowestPrice相比，计算maxProfit
     *
     *复杂度分析：时间：O(n) 空间：O(1)
     */
}
