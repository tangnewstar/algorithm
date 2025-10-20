package org.tangscode.algorithm.didi.train;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/10/20
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int[] train_nums = new int[4];
        for (int i = 0; i < train_nums.length; i++) {
            train_nums[i] = scanner.nextInt();
        }

        boolean result = canArrange(train_nums);
        System.out.println(result ? "Yes" : "No");
    }

    static boolean canArrange(int[] nums) {

        Stack<Integer> stack = new Stack<>();

        int current = 1; // 记录车厢位置，即[1,2,3,4]
        int index = 0; // 匹配的目标序列的位置，即nums[i]的位置

        // 逻辑：
        // 1. 栈非空，栈顶匹配，出栈并移动目标序列索引位置
        // 2. 车厢元素还有，继续入栈
        // 3. 既无车厢，栈顶元素还不匹配，则失败不可排列
        while(index < nums.length) {
            // 从栈顶取出一个元素，如果是当前值则直接匹配
            if (!stack.isEmpty() && stack.peek() == nums[index]) {
                // 出栈并移动目标位置
                stack.pop();
                index++;
            } else if (current <= nums.length) {
                // 不匹配且还有车厢则继续入栈
                stack.push(current);
                current++;
            } else {
                // 栈顶不匹配，且无元素入栈
                return false;
            }
        }

        return true;
    }
}
