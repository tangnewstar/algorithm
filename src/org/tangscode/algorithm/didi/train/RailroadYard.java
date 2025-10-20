package org.tangscode.algorithm.didi.train;

import java.util.*;

/*
 * 铁路的货车编组站如上图，货车编组站，是利用人字形的铁路道岔，将货车车厢的顺序进行重新调整，以便于货车在不同的目的地进行车厢解挂。
 *
 *
 *
 * 现编组站有一人字形铁路道岔，A 点有 k 节车厢，所有车厢都须从 A 点进入，经 C 点道岔后，重新编组到 B 点。 例如，A 点有车厢 1-2-3(从左至右)，经 C 编组，到 B 点后，可以被编组成 1-2-3， 1-3-2，2-1-3，2-3-1，3-2-1 等几种可能的编组。
 *
 * 问:
 * A 有车厢编组 (从左至右)1-2-3-4，列车在经过编组后，能否在 B 点编组成 4-1-3-2 的顺序，请给出算法证明?
 *
 * 格式：
 *
 *
 * 输入：
 * - 输入 4 行，每行依次表示车厢编号。
 * 输出：
 * - Yes/No
 * 示例：
 *
 *
 * 输入：
 *      4
 *      1
 *      3
 *      2
 * 输出：No
* */
public class RailroadYard {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 读取目标顺序
        int[] target = new int[4];
        for (int i = 0; i < 4; i++) {
            target[i] = scanner.nextInt();
        }
        
        // 检查是否可以通过栈操作实现
        if (canArrange(target)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
    
    public static boolean canArrange(int[] target) {
        Stack<Integer> stack = new Stack<>();

        // current记录的是可以入栈的车次编号，即顺序的[1,2,3,4]
        int current = 1; // 下一个要进入的车厢编号
        int index = 0;   // 目标序列的当前位置

        // 分为3个步骤：
        // 1) 栈顶匹配弹出，索引位置+1
        // 2) 栈顶不匹配，还有车厢可入栈，入栈，同时记录车厢编号current
        // 3) 否则不匹配（既没有车厢，栈顶也不匹配）
        // 模拟车厢进出栈过程
        while (index < target.length) {
            if (!stack.isEmpty() && stack.peek() == target[index]) {
                // 栈顶车厢与目标匹配，弹出
                stack.pop();
                index++;
            } else if (current <= target.length) {
                // 还有车厢可以入栈
                stack.push(current);
                current++;
            } else {
                // 既不能匹配目标，也没有车厢可入栈，失败
                return false;
            }
        }
        
        return true;
    }
}