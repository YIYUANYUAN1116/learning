package com.xht.algorithm.LC100;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-04-09 11:04
 **/
public class LC994 {
    public static void main(String[] args) {

    }

    public int orangesRotting(int[][] grid) {

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        Deque<int[]> deque = new ArrayDeque<>();
        int rowLen = grid.length;
        int colLen = grid[0].length;
        int fresh = 0;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (grid[i][j] == 1) {
                    fresh++;
                }
                if (grid[i][j] == 2) {
                    deque.offer(new int[]{i, j});
                }
            }
        }

        int step = 0;
        while (!deque.isEmpty()) {
            step++;
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                int[] poll = deque.poll();
                for (int j = 0; j < 4; j++) {
                    int x = dx[j] + poll[0];
                    int y = dy[j] + poll[1];
                    //边界校验
                    if (x >= 0 && x < rowLen && y >= 0 && y < colLen && grid[x][y] == 1) {
                        deque.offer(new int[]{x, y}); //腐烂橘子入队
                        grid[x][y] = 2;
                        fresh--;
                    }
                }
            }
        }

        //感染完了之后如果还有新鲜橘子
        if (fresh > 0) {
            return -1;
        } else {
            return step;
        }
    }
}
