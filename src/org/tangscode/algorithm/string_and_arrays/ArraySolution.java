package org.tangscode.algorithm.string_and_arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author tangxinxing
 * @version 1.0
 * @description solution
 * @date 2023/10/7
 */
public class ArraySolution {

    /**
     * 给你一个整数数组 nums ，请计算数组的 中心下标 。
     *
     * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
     *
     * 如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。这一点对于中心下标位于数组最右端同样适用。
     *
     * 如果数组有多个中心下标，应该返回 最靠近左边 的那一个。如果数组不存在中心下标，返回 -1
     * @param nums
     * @return
     */
    public int pivotIndex(int[] nums) {
        //calc sum
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        int sum_left = 0;
        for (int i = 0; i < nums.length; i++) {
            //calc sum right
            sum -= nums[i];
            //left equals right at position i
            if (sum_left == sum) {
                return i;
            }
            //calc sum left
            sum_left += nums[i];
        }

        return -1;
    }

    /**
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     *
     * 请必须使用时间复杂度为 O(log n) 的算法。
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (target <= nums[i]) {
                return i;
            }
        }

        return nums.length;
    }

    /**
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals){

        if (intervals.length == 1) {return intervals;}
        //sort array asc
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        //merge array
        List<int[]> result = new ArrayList<>();
        int[] temp = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            if (temp[1] >= intervals[i][0]) {
                //two range has overlap
                temp[1] = Math.max(temp[1],intervals[i][1]);
            } else{
                result.add(temp);
                //new range has no overlap with others
                temp = intervals[i];
            }
        }
        result.add(temp);
        return result.toArray(new int[result.size()][2]);
    }


    /**
     * 耗时125ms
     */
    public int[][] merge_v1(int[][] intervals) {

        int[][] result = new int[][]{};
        int[] array = new int[]{};

        for (int i = 0; i < intervals.length; i++) {

            int start_pos = searchInsert(array, intervals[i][0]);
            int end_pos = searchInsert(array, intervals[i][1]);

            int index_start = start_pos, index_end = end_pos;
            int start_val = intervals[i][0], end_val = intervals[i][1];

            //group element by insert position
            int max_group = array.length / 2 - 1;
            int start_group = start_pos / 2;
            int end_group = end_pos / 2;

            if (max_group > 0) {
                //end_group is over right
                if (end_group > max_group) {
                    end_group = end_group - 1;
                } else {
                    if (end_group > 0 && intervals[i][1] < array[end_group*2]) {
                        end_group = end_group - 1;
                    }
                }
            }

            //if start value between the original range
            if (start_group <= max_group) {
                start_val = Math.min(array[start_group*2], intervals[i][0]);
            }
            try {
                if (end_group <= max_group && intervals[i][1] >= array[end_group*2]) {
                    end_val = Math.max(array[end_group*2+1], intervals[i][1]);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }


            //left group + current + right group
            if (start_group >= 0) {
                index_start = start_group*2;
            }
            if (end_group <= max_group && intervals[i][1] >= array[end_group*2]) {
                index_end = (end_group+1) * 2;
            }

            //merge the new element
            int[] left_array = Arrays.copyOfRange(array, 0 , index_start);
            int[] right_array = Arrays.copyOfRange(array, index_end , array.length);
            array = new int[left_array.length+right_array.length+2];
            System.arraycopy(left_array, 0 , array, 0, left_array.length);
            System.arraycopy(new int[]{start_val, end_val}, 0, array, left_array.length, 2);
            System.arraycopy(right_array, 0, array, left_array.length + 2, right_array.length);
        }

        int step = 2;
        for (int i = 0; i < array.length; i= i + step) {
            result = Arrays.copyOf(result, result.length+1);
            result[result.length-1] = new int[]{array[i], array[i+1]};
        }

        return result;
    }

    /**
     * 给你一幅由 N × N 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。
     * 不占用额外内存空间能否做到？
     * 解题思路：
     * n*n矩阵，顺时针旋转90degree，第x行变为第n-x列
     * matrix[i,j] -> matrix[j,n-i]
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        //rotate around center
        int row = matrix.length % 2 == 0 ? matrix.length / 2 - 1 :matrix.length / 2;
        int n = matrix.length - 1;
        for (int i = 0; i <= row; i++) {
            int col = matrix.length % 2 == 0 ? row : row - 1;
            for (int j = 0; j <= col; j++) {
                //360 degree contains four element
                //matrix[i,j],matrix[j,n-i],matrix[n-i,n-j],matrix[n-j,i]
                int last = matrix[n-j][i];
                matrix[n-j][i] = matrix[n-i][n-j];
                matrix[n-i][n-j] = matrix[j][n-i];
                matrix[j][n-i] = matrix[i][j];
                matrix[i][j] = last;
            }
        }
    }

    /**
     * 编写一种算法，若M × N矩阵中某个元素为0，则将其所在的行与列清零。
     * 思路：
     * 便利行列，将包含0的行列加入其中
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        if (matrix.length == 0) {return;}

        boolean[] rows = new boolean[matrix.length];
        boolean[] cols = new boolean[matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0){
                    rows[i] = true;
                    cols[j] = true;
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (rows[i] || cols[j]) {
                    matrix[i][j]  = 0;
                }
            }
        }
    }

    /**
     * 给你一个大小为 m x n 的矩阵 mat ，请以对角线遍历的顺序，用一个数组返回这个矩阵中的所有元素。
     * 对角线遍历思路：
     * 1、计算元素总和 m*n
     * 2、遍历次数为m+n-1
     * 3、偶数遍历(i%2==0)右上（row-1，col+1），奇数遍历(i%2==0)左下（row+1，col-1）。
     * 4、开始和结束点。
     *  a) 偶数遍历，开始点左下到右上，i < m, (i,0) -> (0,i), i>=m, (m-1,i-(m-1)) -> ()
     *  b) 奇数遍历，右上到左下，i < n, (0,i) -> (i,0), i>n, (0,i) -> (m-1,i-(m-1))
     * @param mat
     * @return
     */
    public int[] findDiagonalOrder(int[][] mat) {

        if (mat.length == 0 || mat[0].length == 0) {
            return new int[0];
        }

        int[] result = new int[]{};
        int times = mat.length + mat[0].length-1;
        for (int i = 0; i < times; i++) {
            if (i % 2 == 0) {
                int row = i < mat.length ? i : mat.length - 1;
                int col = i < mat.length ? 0 : i - (mat.length - 1);
                while (row >= 0 && col < mat[0].length) {
                    result = Arrays.copyOf(result, result.length + 1);
                    result[result.length-1] = mat[row][col];
                    row = row - 1;
                    col = col + 1;
                }
            } else {
                int row = i < mat[0].length ?  0 : i - (mat[0].length-1);
                int col = i < mat[0].length ? i : mat[0].length-1;
                while (row < mat.length && col >= 0) {
                    result = Arrays.copyOf(result, result.length + 1);
                    result[result.length-1] = mat[row][col];
                    row = row + 1;
                    col = col - 1;
                }
            }
        }
        return result;
    }

    /**
     * 给你一个大小为 m x n 的矩阵 mat ，请以对角线遍历的顺序，用一个数组返回这个矩阵中的所有元素。
     * @param mat
     * @return
     */
    public int[] findDiagonalOrderV1(int[][] mat) {

        int[] result = new int[]{};
        boolean positive = true;
        int[] start_point = {0,0};
        boolean end = false;
        result = new int[]{mat[0][0]};

        while (!end) {
            //three direction choice, the start point is (0,0)
            int[][] directions = positive ? new int[][] {{1, -1},{1, 0},{0, 1}}
                    : new int[][] {{-1, 1}, {0, 1}, {1, 0}};
            for (int i = 0; i < directions.length; i++) {
                int[] direction = directions[i];
                int x = start_point[0] + direction[0];
                int y = start_point[1] + direction[1];
                if (x>= 0 && y>= 0 && x < mat[0].length && y < mat.length) {
                    if (i > 0) { positive = !positive; }
                    start_point = new int[]{x,y};
                    result = Arrays.copyOf(result,result.length+1);
                    result[result.length-1] = mat[y][x];
                    break;
                } else {
                    if (i == 2) { end = true;}
                }
            }
        }

        return result;
    }
}
