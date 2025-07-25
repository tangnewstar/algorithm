package org.tangscode.algorithm.practical;

import java.util.*;

public class TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
        // 1. 使用哈希表统计每个元素的出现频率
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // 2. 使用优先队列（最小堆）来存储频率最高的k个元素
        // 队列中的元素按照频率从小到大排序，保持队列大小不超过k
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(
            (a, b) -> a.getValue() - b.getValue() // 比较器：按频率升序排列
        );
        
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            pq.offer(entry);
            // 如果队列大小超过k，移除频率最小的元素
            if (pq.size() > k) {
                pq.poll();
            }
        }
        
        // 3. 从优先队列中提取结果
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = pq.poll().getKey();
        }
        
        return result;
    }
    
    // 测试方法
    public static void main(String[] args) {
        TopKFrequentElements solution = new TopKFrequentElements();
        
        // 测试用例1
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int k1 = 2;
        int[] result1 = solution.topKFrequent(nums1, k1);
        System.out.println("测试用例1结果: " + Arrays.toString(result1)); // 预期 [1, 2] 或 [2, 1]
        
        // 测试用例2
        int[] nums2 = {1};
        int k2 = 1;
        int[] result2 = solution.topKFrequent(nums2, k2);
        System.out.println("测试用例2结果: " + Arrays.toString(result2)); // 预期 [1]
        
        // 测试用例3
        int[] nums3 = {4, 1, -1, 2, -1, 2, 3};
        int k3 = 2;
        int[] result3 = solution.topKFrequent(nums3, k3);
        System.out.println("测试用例3结果: " + Arrays.toString(result3)); // 预期 [-1, 2] 或 [2, -1]
    }
}
