package org.tangscode.algorithm.didi.arrange_balls;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/10/10
 */
/**
 * 描述：
 * 给定三种类型的小球 P、Q、R，每种小球的数量分别为 np、nq、nr 个。现在想将这些小球排成一条直线，但是不允许相同类型的小球相邻，问有多少种排列方法。如果无法组合出合适的结果，则输出 0。
 *
 * 格式：
 *
 *
 * 输入：
 * - 一行以空格相隔的三个数，分别表示为 np，nq，nr。
 * 输出：
 * - 排列方法的数量。
 * 示例：
 *
 *
 * 输入：2 1 1
 * 输出：6
 * 解释：如若 np=2，nq=1，nr=1 则共有 6 种排列方式：PQRP，QPRP，PRQP，RPQP，PRPQ 以及 PQPR。
 *
 * 链接：https://leetcode.cn/leetbook/read/didiglobal2/e7hh2i/
 */
public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int np = scanner.nextInt(); // P的数量
        int nq = scanner.nextInt(); // Q的数量
        int nr = scanner.nextInt(); // R的数量
        Solution solution = new Solution();
        // 动态规划，将复杂问题分解为诸多小问题
        // 定义dfs方法，输入：当前选择，及p，q，r对应的数量，输出排列可能个数
        HashMap<String,Long> map = new HashMap<>();
        long result = solution.dfs(0, np-1, nq, nr, map)
                + solution.dfs(1, np,nq-1,nr,map)
                + solution.dfs(2,np,nq,nr-1,map);
        System.out.println(result);
    }

    private long dfs(int choice, int p, int q, int r, HashMap<String, Long> map) {
        // 如果小球个数小于0，则返回0中可能
        if (p < 0 || q < 0 || r < 0) {
            return 0;
        }
        // 如果小球个数都为1，返回1种可能
        if (p + q + r ==0) {
            return 1;
        }

        // 如果当前路径计算过，返回缓存的值
        if (map.containsKey(choice + "-" + p + "-" + q + "-" + r)) {
            return map.get(choice + "-" + p + "-" + q + "-" + r);
        }

        // 复杂问题解析
        long result = 0;
        if (choice == 0) {
            result += dfs(1, p, q -1, r, map) + dfs(2, p, q, r-1, map);
        } else if (choice == 1) {
            result += dfs(0, p-1, q, r, map) + dfs(2, p, q, r-1, map);
        } else {
            result += dfs(0, p-1,q,r,map) + dfs(1, p,q-1,r,map);
        }
        // 缓存写入
        map.put(choice + "-" + p + "-" + q + "-" + r, result);
        return result;
    }
}
