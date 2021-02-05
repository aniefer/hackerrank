package aniefer.graphs;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/ctci-connected-cell-in-a-grid/problem
 */
public class ConnectedCell extends TestCase {
	static int maxRegion(int[][] grid) {
		int max = 0;

		// Visit the grid depth first, turn off elements instead
		// of tracking visited
		for (int j = 0; j < grid.length; j++) {
			for (int i = 0; i < grid[0].length; i++) {
				max = Math.max(max, visit(i, j, grid));
			}
		}
		return max;
	}

	static private int visit(int x, int y, int[][] grid) {
		if (x < 0 || x >= grid[0].length || y < 0 || y >= grid.length || grid[y][x] == 0) {
			return 0;
		}

		grid[y][x] = 0;  // visited, turn it offf

		int count = 1;
		for (int j = y - 1; j <= y + 1; j++) {
			for (int i = x - 1; i <= x + 1; i++) {
				count += visit(i, j, grid);
			}
		}
		return count;
	}

	public void testCase() {
		int[][] grid = new int[][] { { 1, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 1, 0, 0, 0 } };
		assertEquals(5, maxRegion(grid));

		grid = new int[][] { { 0, 0, 1, 1 }, { 0, 0, 1, 0 }, { 0, 1, 1, 0 }, { 0, 1, 0, 0 }, { 1, 1, 0, 0 } };
		assertEquals(8, maxRegion(grid));
	}
}
