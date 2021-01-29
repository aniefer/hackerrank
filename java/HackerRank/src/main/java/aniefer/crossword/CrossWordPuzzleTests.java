package aniefer.crossword;

import static org.junit.Assert.assertArrayEquals;

import junit.framework.TestCase;

public class CrossWordPuzzleTests extends TestCase {

	public void testLondonPuzzle() {
		String[] puzzle =   new String[] { "+-++++++++", "+-++++++++", "+-++++++++", "+-----++++", "+-+++-++++",
							  			   "+-+++-++++", "+++++-++++", "++------++", "+++++-++++", "+++++-++++" };

		String[] solution = new String[] { "+L++++++++", "+O++++++++", "+N++++++++", "+DELHI++++", "+O+++C++++",
										   "+N+++E++++", "+++++L++++", "++ANKARA++", "+++++N++++", "+++++D++++" };

		String[] result = CrosswordPuzzle.crosswordPuzzle(puzzle, "LONDON;DELHI;ICELAND;ANKARA");
		assertArrayEquals(solution, result);
	}

	public void testAgraPuzzle() {

		String[] puzzle   = new String[] { "+-++++++++", "+-++++++++", "+-------++", "+-++++++++", "+-++++++++",
										   "+------+++", "+-+++-++++", "+++++-++++", "+++++-++++", "++++++++++" };
		String[] solution = new String[] { "+E++++++++", "+N++++++++", "+GWALIOR++", "+L++++++++", "+A++++++++",
										   "+NORWAY+++", "+D+++G++++", "+++++R++++", "+++++A++++", "++++++++++" };

		String[] result = CrosswordPuzzle.crosswordPuzzle(puzzle, "AGRA;NORWAY;ENGLAND;GWALIOR");
		assertArrayEquals(solution, result);
	}

	public void testIceland() {
		String[] puzzle = new String[]   { "XXXXXX-XXX", "XX------XX", "XXXXXX-XXX", "XXXXXX-XXX", "XXX------X",
										   "XXXXXX-X-X", "XXXXXX-X-X", "XXXXXXXX-X", "XXXXXXXX-X", "XXXXXXXX-X" };
		String[] solution = new String[] { "XXXXXXIXXX", "XXMEXICOXX", "XXXXXXEXXX", "XXXXXXLXXX", "XXXPANAMAX",
										   "XXXXXXNXLX", "XXXXXXDXMX", "XXXXXXXXAX", "XXXXXXXXTX", "XXXXXXXXYX" };
		
		String[] result = CrosswordPuzzle.crosswordPuzzle(puzzle, "ICELAND;MEXICO;PANAMA;ALMATY");
		assertArrayEquals(solution, result);
	}

}
