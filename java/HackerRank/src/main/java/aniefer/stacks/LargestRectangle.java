package aniefer.stacks;

import java.util.ArrayDeque;
import java.util.Deque;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/largest-rectangle/problem
 */
public class LargestRectangle extends TestCase {

	private static class Rect {
		int height;
		int start;

		public Rect(int height, int start) {
			this.height = height;
			this.start = start;
		}
	}

	static long largestRectangle(int[] heights) {

		Deque<Rect> stack = new ArrayDeque<>();
		long maxArea = 0;
		for (int i = 0; i <= heights.length; i++) {
			int height = (i < heights.length) ? heights[i] : -1;
			if (stack.isEmpty() || height > stack.peek().height) {
				stack.push(new Rect(height, i));
			} else if (height < stack.peek().height) {
				Rect popped = null;
				while (!stack.isEmpty() && height < stack.peek().height) {
					popped = stack.pop();

					maxArea = Math.max(maxArea, popped.height * (long) (i - popped.start));
				}
				stack.push(new Rect(height, popped.start));
			}
		}
		return maxArea;
	}

	public void testCase() {
		assertEquals(9, largestRectangle(new int[] { 1, 2, 3, 4, 5 }));
		assertEquals(18, largestRectangle(new int[] { 1, 3, 5, 9, 11 }));
		assertEquals(18060, largestRectangle(new int[] { 6320, 6020, 6098, 1332, 7263, 672, 9472, 2838, 3401, 9494 }));
	}
}
