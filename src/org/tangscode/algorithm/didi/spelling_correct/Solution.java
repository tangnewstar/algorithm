package org.tangscode.algorithm.didi.spelling_correct;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/10/13
 */

import java.util.*;
import java.util.stream.Collectors;

/**
 * 英文单词拼写的时候可能会出现拼写错误的情况(typo)。下面的题目，我们尝试实现拼写纠错推荐的功能。
 * 字串编辑距离是衡量字串间相似度的常见手段。
 *
 * 字串编辑距离：是指利用字符操作，把字符串 A 转换成字符串B所需要的最少操作数。
 *
 * 字串操作包括：删除字符(removal)、插入字符(insertion)、修改字符(substitution)。
 *
 * 使用以下规则对推荐纠错选项进行相似度排序。得分越高，认为相似度越低 只涉及到 26 个英文字符、不区分大小写。
 *
 * 删除(removal)：3 分
 * 插入(insertion)：3 分
 * 修改(substitution)：
 * (q w e r t a s d f g z x c v ) (y u i o p h j k l b n m)
 * 以上两个分组内的字符修改 1 分，两个分组间字符修改 2 分。
 * 格式：
 *
 *
 * 输入：
 * - 每行一个输入。空格分割参数。
 * - 第一个参数是目标单词(可能存在 typo)。后面若干空格分割参数(个数不定)是字典单词，作为候选纠错项（也可能和第一个参数重复）。
 * 输出：
 * - 按照上面的纠错规则字串相似度最小编辑距离进行排序，给出3个(如有)对应的纠错候选。
 * - 如得分相同，则按照输入顺序进行排序。
 * 示例：
 *
 *
 * 输入：slep slap sleep step shoe shop snap slep
 * 输出：slep slap step
 *
 */
public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] words = input.split(" ");

        Solution solution = new Solution();
        List<CandidateEntry> candidateEntries = new ArrayList<>();
        for (int i = 1; i < words.length; i++) {
            candidateEntries.add(new CandidateEntry(words[i], solution.score(words[0], words[i])));
        }

        candidateEntries.sort(Comparator.comparingInt(a -> a.score));

        List<String> top3 = candidateEntries.stream().limit(3).map(c -> c.candidate).collect(Collectors.toList());
        System.out.println(String.join(" ", top3));
    }

    private static Set<Character> GROUP1 = new HashSet<>();
    private static Set<Character> GROUP2 = new HashSet<>();
    static {
        // 初始化分组：第一组(q w e r t a s d f g z x c v)
        String group1 = "qwertasdfgzxcv";
        for (char c : group1.toCharArray()) {
            GROUP1.add(c);
        }
        // 第二组(y u i o p h j k l b n m)
        String group2 = "yuiophjklbnm";
        for (char c : group2.toCharArray()) {
            GROUP2.add(c);
        }
    }

    static class CandidateEntry {
        private String candidate;
        private int score;

        public CandidateEntry(String candidate, int score) {
            this.candidate = candidate;
            this.score = score;
        }
    }

    public int score(String typo, String candidate) {

        // 动态规划二维矩阵dp[i][j]，意为typo前i位与candidate前j位编辑距离得分

        int m = typo.length();
        int n = candidate.length();

        int[][] dp = new int[m+1][n+1];

        // 计算边界（插入|删除）
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i * 3;
        }
        for(int j = 1; j <= n; j++) {
            dp[0][j] = j * 3;
        }

        for (int i = 1; i <= m ; i++) {
            char c1 = typo.charAt(i - 1);
            for (int j = 1; j <= n ; j++) {
                char c2 = candidate.charAt(j - 1);
                // 字符相同
                if (c1 == c2) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    // 计算替换，删除，插入成本
                    int substituteCost = (GROUP1.contains(c1) && GROUP1.contains(c2))
                            || (GROUP2.contains(c1) && GROUP2.contains(c2)) ? 1 : 2;
                    int substitute = dp[i-1][j-1] + substituteCost;
                    int deletion = dp[i-1][j] + 3;
                    int insertion = dp[i][j-1] + 3;
                    dp[i][j] = Math.min(Math.min(substitute, deletion), insertion);
                }
            }
        }
        return dp[m][n];
    }
}
