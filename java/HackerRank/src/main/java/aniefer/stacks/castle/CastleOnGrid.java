package aniefer.stacks.castle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://www.hackerrank.com/challenges/castle-on-the-grid/problem
 * Solve the castle on grid using a breadth first search
 */
public class CastleOnGrid {
	private static final Position SENTINEL = new Position(-1, -1);

	private static class Position {
		int x;
		int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static int minimumMoves(String[] grid, int startX, int startY, int goalX, int goalY) {
        CastleOnGrid castle = new CastleOnGrid(grid);
        return castle.bfs(startX, startY, goalX, goalY);
    }

	private String[] grid;
	private boolean[][] visited;

	public CastleOnGrid(String[] grid) {
		if (grid == null || grid.length == 0) {
			throw new IllegalArgumentException();
		}
			
		this.grid = grid;
		visited = new boolean[grid.length][grid[0].length()];
	}

	/**
	 * A breadth first search from start to goal
	 * @return
	 */
	private int bfs(int startX, int startY, int goalX, int goalY) {
		Queue<Position> q = new LinkedList<>();
		q.add(new Position(startX, startY));
		q.add(SENTINEL);

		int distance = 0;
		while (!q.isEmpty()) {
			Position p = q.remove();
			if (p == SENTINEL) {
				distance++;
				q.add(SENTINEL);
				continue;
			}
			if (p.x == goalX && p.y == goalY) {
				return distance;
			}

			q.addAll(moves(p, 0, 1));
			q.addAll(moves(p, 0, -1));
			q.addAll(moves(p, 1, 0));
			q.addAll(moves(p, -1, 0));
		}
		return -1;

	}

	/**
	 * Return a list of candidate moves starting at the given position
	 * moving in the given direction
	 */
	private List<Position> moves(Position p, int dx, int dy) {
		List<Position> result = new ArrayList<>();
		int x = p.x;
		int y = p.y;
		while (true) {
			x += dx;
			y += dy;
			
			if (x < 0 || x >= grid.length ||		// Hit the edge of the grid
				y < 0 || y >= grid[x].length() || 
				grid[x].charAt(y) == 'X') {			// or hit an 'X'
				return result;
			}
			if (!visited[x][y]) {
				visited[x][y] = true;
				result.add(new Position(x, y));
			}
		}
	}
}
