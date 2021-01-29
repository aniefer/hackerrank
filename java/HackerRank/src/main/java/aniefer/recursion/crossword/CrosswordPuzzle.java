package aniefer.recursion.crossword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * https://www.hackerrank.com/challenges/crossword-puzzle/problem
 */
public class CrosswordPuzzle {

	int puzzleWidth;
	int puzzleHeight;

	char[][] puzzle;
	boolean adjustBackground = false;

	public CrosswordPuzzle(String[] puzzle) {
		if (puzzle == null || puzzle.length == 0 || puzzle[0].length() != puzzle.length) {
			throw new IllegalArgumentException();
		}

		puzzleWidth = puzzle[0].length();
		puzzleHeight = puzzle.length;

		// Convert the puzzle to char[][] to make it easier to replace pieces
		// normalize to use "+" and not "X" for the background
		adjustBackground = puzzle[0].contains("X");
		this.puzzle = new char[puzzleHeight][];
		for (int i = 0; i < puzzleHeight; i++) {
			if (adjustBackground)
				this.puzzle[i] = puzzle[i].replaceAll("X", "+").toCharArray();
			else
				this.puzzle[i] = puzzle[i].toCharArray();
		}
	}

	public static String[] crosswordPuzzle(String[] crossword, String words) {
		CrosswordPuzzle c = new CrosswordPuzzle(crossword);
		return c.solve(words);
	}
	
	public String[] solve(String words) {
		List<String> wordList = new ArrayList<>(Arrays.asList(words.split(";")));

		solve(0, 0, puzzle, wordList);

		String[] result = new String[puzzleHeight];
		for (int i = 0; i < puzzleHeight; i++) {
			result[i] = String.valueOf(puzzle[i]);
			if (adjustBackground) {
				result[i] = result[i].replaceAll("\\+", "X");
			}
		}
		return result;
	}

	private static enum Direction {
		HORIZONTAL(1, 0), VERTICAL(0, 1);

		public int dx, dy;
		private Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}
	}
	
	private static class Entry {
		public String pattern;
		public Direction direction;

		public Entry(String pattern, Direction dir) {
			this.pattern = pattern;
			this.direction = dir;
		}
	}
	
	/**
	 * Solve the puzzle.
	 * 
	 * Iterate over each element, looking for word start location
	 * Attempt to match a word:
	 * 	match: recurse to continue
	 *  no matches: return false to backtrack and try a different word in some previous location  
	 * 
	 * @return true if puzzle solved
	 */
	private boolean solve(int x, int y, char[][] puzzle, List<String> words) {
		for (int j = y; j < puzzleHeight; j++) {
			for (int i = x; i < puzzleWidth; i++) {
				Entry next = nextEntry(i, j);
				if (next != null) {
					for (String word : words) {
						if (Pattern.matches(next.pattern, word)) { // Match!
							String oldContent = replace(i, j, next.direction, word);

							// remove the match from the list of words, recurse to match more
							List<String> newWords = words.stream().filter(s -> !s.equals(word)).collect(Collectors.toList());
							if (newWords.isEmpty() || solve(i, j, puzzle, newWords)) {
								return true;
							} else {
								// no match, put the old content back and try another word
								replace(i, j, next.direction, oldContent);
							}
						}
					}
					// unable to match anything to this entry, a previous match must have been
					// wrong, back-track
					return false;
				}
			}
			x = 0;
		}
		return false;
	}

	/**
	 * Return an puzzle entry to match at this location, or null if there is nothing to match here
	 */
	private Entry nextEntry(int x, int y) {
		for (Direction d : Direction.values()) {
			if (isEntryStart(x, y, d)) {
				String regex = getRegExPattern(x, y, d);
				if (regex.contains(".")) { // are there still letters to replace?
					return new Entry(regex, d);
				}
			}
		}
		
		// not a start location, or words have already been replaced
		return null;
	}
	
	private boolean isEntryStart(int x, int y, Direction d) {
		if (puzzle[y][x] == '+')
			return false;

		if ((x + d.dx >= puzzleWidth  || puzzle[y][x + d.dx] == '+') ||
			(y + d.dy >= puzzleHeight || puzzle[y + d.dy][x] == '+')) {
			return false; // next character was a background char
		}
		// check Previous character
		if ((d.dx == 1 && x > 0 && puzzle[y][x - 1] != '+') ||
			(d.dy == 1 && y > 0 && puzzle[y - 1][x] != '+')) {
			return false;
		}
		return true;
	}

	private String getRegExPattern(int x, int y, Direction d) {
		char c = puzzle[y][x];
		StringBuilder pattern = new StringBuilder();
		pattern.append((c == '-') ? "." : c);

		while (x + d.dx < puzzleWidth && y + d.dy < puzzleHeight) {
			c = puzzle[y + d.dy][x + d.dx];
			if (c == '+')
				break;
			pattern.append((c == '-') ? "." : c);

			x += d.dx;
			y += d.dy;
		}
		return pattern.toString();
	}

	/**
	 * Insert the given word into the puzzle at the given location and direction
	 * @return the old contents of the puzzle.
	 */
	private String replace(int x, int y, Direction d, String word) {
		String old = "";
		for (int i = 0; i < word.length(); i++) {
			old += puzzle[y + i * d.dy][x + i * d.dx];
			puzzle[y + i * d.dy][x + i * d.dx] = word.charAt(i);
		}
		return old;
	}
}
