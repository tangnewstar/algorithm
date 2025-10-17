package org.tangscode.algorithm.didi.spelling_correct;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/10/17
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
public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        List<CandidateEntry> list = new ArrayList<>();
        String[] words = line.split(" ");
        for (int i = 1; i < words.length; i++) {
            list.add(new CandidateEntry(words[i], score(words[0], words[i])));
        }

        // 排序
        list.sort((a,b) -> Integer.compare(a.score, b.score));
        List<String> top3 = list.stream().limit(3).map(c -> c.candidate).collect(Collectors.toList());
        System.out.println(String.join(" ", top3));
    }

    private static Set<Character> GROUP1_CHAR = new HashSet<>();
    private static Set<Character> GROUP2_CHAR = new HashSet<>();

    static {
        char[] group1 = "qwertasdfgzxcv".toCharArray();
        for (char c: group1) {
            GROUP1_CHAR.add(c);
        }
        char[] group2 = "yuiophjklbnm".toCharArray();
        for(char c: group2) {
            GROUP2_CHAR.add(c);
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

    static int score(String target, String candidate) {

        // 动态规划，定义二位举证
        int m = target.length();
        int n = candidate.length();

        int[][] df = new int[m+1][n+1];
        // 计算边界
        for (int i = 0; i < m; i++) {
            df[i][0] = i * 3;
        }
        for (int i = 0; i < n; i++) {
            df[0][i] = i * 3;
        }

        // 由左至右计算分数
        for (int i = 1; i <= m; i++) {
            char a = target.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char b = candidate.charAt(j - 1);

                // 比较字符相同则取上个字符比较结果
                if ( a== b) {
                    df[i][j] = df[i-1][j-1];
                } else {
                    // 分别计算插入，删除，替换的分数
                    int substituteCost = (GROUP1_CHAR.contains(a) && GROUP1_CHAR.contains(b))
                            || (GROUP2_CHAR.contains(a) && GROUP2_CHAR.contains(b)) ? 1 : 2;
                    int substitute = df[i-1][j-1] + substituteCost;
                    // 删除的意思是删除target的第i个字符，相当于df[i-1][j]分值 + 删除分数
                    int deletion = df[i-1][j] + 3;
                    int insertion = df[i][j-1] + 3;

                    df[i][j] = Math.min(Math.min(substitute, deletion), insertion);
                }
            }
        }

        return df[m][n];
    }
}
