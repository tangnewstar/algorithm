package org.tangscode.algorithm.didi.arrange_balls;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/10/16
 */
public class Main {

    public static void main(String[] args) {
        // 读取PQR数量
        Scanner scanner = new Scanner(System.in);
        int np = scanner.nextInt();
        int nq = scanner.nextInt();
        int nr = scanner.nextInt();

        Map<String, Long> memory = new HashMap<>();
        long result = dfs(0, np -1,nq, nr, memory)
                + dfs(1,np,nq-1,nr, memory)
                + dfs(2, np,nq,nr-1,memory);
        System.out.print(result);
    }

    static long dfs(int choice, int p, int q, int r, Map<String,Long> memory) {
        if ( p < 0 || q < 0 || r <0) {
            return 0;
        }
        if (p + q + r == 0) {
            return 1;
        }

        // hit memory
        if (memory.containsKey(choice + "-" + p + "-" + q + "-" + r)) {
            return memory.get(choice + "-" + p + "-" + q + "-" + r);
        }
        
        long result = 0;
        if (choice == 0) {
            result += dfs(1, p, q-1, r, memory) + dfs(2, p,q,r-1, memory);
        } else if (choice == 1) {
            result += dfs(0, p - 1, q, r, memory) + dfs(2, p, q, r - 1, memory);
        } else {
            result += dfs(0, p-1, q, r, memory) + dfs(1, p, q-1,r, memory);
        }
        memory.put(choice + "-" + p + "-" + q + "-" + r, result);
        
        return result;
    }
}
