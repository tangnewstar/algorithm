package org.tangscode.algorithm.string_and_arrays.best_time_to_buy_and_sell_stock_ii;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2024/11/19
 */
public class Solution {

    /*
    * 122. 买卖股票的最佳时机 II
    给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
    在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
    返回 你能获得的 最大 利润 。

    示例 1：
    输入：prices = [7,1,5,3,6,4]
    输出：7
    解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4。
    随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3。
    最大总利润为 4 + 3 = 7 。
    示例 2：
    输入：prices = [1,2,3,4,5]
    输出：4
    解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4。
    最大总利润为 4 。
    示例 3：
    输入：prices = [7,6,4,3,1]
    输出：0
    解释：在这种情况下, 交易无法获得正利润，所以不参与交易可以获得最大利润，最大利润为 0。


    提示：
    1 <= prices.length <= 3 * 104
    0 <= prices[i] <= 104
    * */

    public int maxProfit(int[] prices) {
        /*
        * 低买高卖策略
        * - 记录低价lowPrice, 如果price线性增加，则不断抬高highPrice
        * - 如果中途price降低，则卖出并记录新的lowPrice，highPrice
        * */
        int lowPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int i = 0; i < prices.length; i++) {
            //price grow or decline
            if (i > 0) {
                if (prices[i] > prices[i-1]) {
                    //if can get profit then sell out
                    if (i == prices.length - 1 && prices[i] > lowPrice) {
                        maxProfit += prices[i] - lowPrice;
                    }
                } else {
                    //sell out to get profit on (i-1) day
                    maxProfit += prices[i-1] - lowPrice;
                    lowPrice = prices[i];
                }
            }
            if (prices[i] < lowPrice) {
                lowPrice = prices[i];
            }
        }

        return maxProfit;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] prices = {7,1,5,3,6,4};
        int maxProfit = solution.maxProfit(prices);
        assert maxProfit == 7;

        int[] prices2 = {1,2,3,4,5};
        int profit2 = solution.maxProfit(prices2);
        assert profit2 == 4;

        int[] prices3 = {7,6,4,3,1};
        int profit3 = solution.maxProfit(prices3);
        assert profit3 == 0;
    }

    /**
     * 题解分析：
     *
     * 1. 低买高卖（我的解法）
     * 记录阶段的lowPrice，当触发卖出条件时卖出累计profit
     *  - 当price增长趋势逆转时
     *  - 当prices遍历结束时
     *  最后累计全部的profit，即maxProfit
     * 时间复杂度：O(n)
     */
}
