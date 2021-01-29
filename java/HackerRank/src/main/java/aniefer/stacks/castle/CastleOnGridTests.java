package aniefer.stacks.castle;

import junit.framework.TestCase;

public class CastleOnGridTests extends TestCase {
	public void testGrids() {
		String[] grid = new String[] { ".X.", ".X.", "..." };
		assertEquals(3, CastleOnGrid.minimumMoves(grid, 0, 0, 0, 2));

		grid = new String[] { "...", ".X.", ".X." };
		assertEquals(3, CastleOnGrid.minimumMoves(grid, 2, 0, 2, 2));

		grid = new String[] { "...", ".X.", ".X." };
		assertEquals(2, CastleOnGrid.minimumMoves(grid, 2, 0, 0, 2));
	}
}
