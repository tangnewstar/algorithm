package org.tangscode.algorithm.didi.multi_region_dispatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/10/15
 */

/**
 * 多区域订单动态调度（贪心 + 优先级队列）
 *
 * 题干：滴滴国际化某区域覆盖M个城市（跨 3 个时区），每个城市实时数据如下：①driver_cnt（当前可用司机数）；②order_cnt（当前待接单数量）；③premium_rate（订单溢价系数，溢价越高，司机接该城市订单收益越高）。现在需要从 “全局最优” 出发，将部分城市的可用司机调度到其他城市（调度成本忽略，司机调度后立即可用），要求满足：
 * 每个城市调度后，driver_cnt ≥ order_cnt × 0.5（避免司机严重不足）；
 * 总 “收益指数” 最大（收益指数 =Σ（城市订单数 × 溢价系数 × 调度后司机数与订单数的最小比值），即司机越多、溢价越高，收益指数越高）。
 */
public class Solution {

    public static void main(String[] args) {
        List<City> cities = Arrays.asList(
                new City(30, 100, 1.8, "Tokyo"),
                new City(80, 50, 1.2, "London"),
                new City(20, 30,1.5, "New York")
        );

        double totalProfit = 0;
        for (City city: cities) {
            totalProfit += city.calculateProfit();
            System.out.printf("城市：%s 调度前司机数：%d 订单数: %d 溢价系数：%.2f 收益：%.2f%n",
                    city.cityName, city.driverCnt, city.orderCnt, city.premiumRate,
                    city.calculateProfit());
        }
        System.out.println("调度前总收益：" + String.format("%.2f",totalProfit));

        Solution solution = new Solution();
        double profit = solution.dispatch(cities);
        System.out.println("调度后总收益：" + String.format("%.2f",profit));
    }

    public double dispatch(List<City> cities) {

        // 溢价率高的城市排在前
        PriorityQueue<City> needDriverQueue = new PriorityQueue<>((a,b) ->
            Double.compare(a.premiumRate, b.premiumRate)
        );

        // 能提供司机多的城市排在前
        PriorityQueue<City> fullDriverQueue = new PriorityQueue<>((a,b) -> {
            double aDriverOffer = Math.max(0, -a.gap());
            double bDriverOffer = Math.max(0, -b.gap());
            return Double.compare(bDriverOffer, aDriverOffer);
        });

        // 将城市分为两个队列：缺司机 & 多司机
        for (City city: cities) {
            double gap = city.gap();
            if (gap > 1e-9) {
                // 如果大于0，意味着需要司机，1e-9约等于0浮点数精度问题
                needDriverQueue.add(city);
            } else if (gap < -1e-9) {
                fullDriverQueue.add(city);
            }
            // 如果没有gap不需要处理
        }

        // 贪心分配
        while(!needDriverQueue.isEmpty() && !fullDriverQueue.isEmpty()) {
            City needDriverCity = needDriverQueue.poll();
            City fullDriverCity = fullDriverQueue.poll();

            // 计算可调配司机数：取 需求差的最小值
            double need = needDriverCity.gap();
            double excess = Math.max(0, -fullDriverCity.gap());
            int dispatch = (int) Math.min(Math.ceil(need), Math.floor(excess));
            if (dispatch <= 0) {
                continue;
            }

            // 执行
            needDriverCity.driverCnt += dispatch;
            fullDriverCity.driverCnt -= dispatch;

            // 重新放入队列（如果还缺司机）
            double newNeedGap = needDriverCity.gap();
            if (newNeedGap > 1e-9) {
                needDriverQueue.add(needDriverCity);
            }

            // 重新放入队列（如果司机过剩）
            double newExcessGap = fullDriverCity.gap();
            if (newExcessGap < -1e-9) {
                fullDriverQueue.add(fullDriverCity);
            }
        }

        // 分配结束，计算总收益
        double totalProfit = 0;
        for (City city: cities) {
            totalProfit += city.calculateProfit();
            System.out.printf("城市：%s 调度后司机数：%d 收益：%.2f%n", city.cityName, city.driverCnt, city.calculateProfit());
        }

        return totalProfit;
    }
    
    static class City {
        int driverCnt;
        int orderCnt;
        /**
         * 溢价系数
         */
        double premiumRate;
        String cityName;

        public City(int driverCnt, int orderCnt, double premiumRate, String cityName) {
            this.driverCnt = driverCnt;
            this.orderCnt = orderCnt;
            this.premiumRate = premiumRate;
            this.cityName = cityName;
        }

        // 需求差，>0需要司机，<0司机过剩
        public double gap() {
            double minNeed = 0.5 * orderCnt;
            return minNeed - driverCnt;
        }

        // 计算收益指数：订单数 * 溢价系数 * min(司机数/订单数, 1)
        public double calculateProfit() {
            if (orderCnt == 0) {
                return 0;
            }
            double driverOrderCntRatio = (double) driverCnt / orderCnt;
            return orderCnt * premiumRate * Math.min(driverOrderCntRatio, 1);
        }
    }
}
