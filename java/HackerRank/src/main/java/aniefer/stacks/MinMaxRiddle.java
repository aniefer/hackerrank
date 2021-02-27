package aniefer.stacks;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/min-max-riddle/problem
 */
public class MinMaxRiddle extends TestCase {

	static long[] riddle(long[] arr) {
		// for each element in arr, find the index of the previous and next smaller elements
		int[] nextSmaller = findSmaller(arr, arr.length - 1);
		int[] prevSmaller = findSmaller(arr, 0);

		long[] windows = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			// the interval length for which arr[i] is the minimum
			int length = nextSmaller[i] - prevSmaller[i] - 1;
			// the max value in a window of that size
			windows[length - 1] = Math.max(windows[length - 1], arr[i]);
		}

		for (int i = arr.length - 2; i >= 0; i--) {
			windows[i] = Math.max(windows[i], windows[i + 1]);
		}
		return windows;
	}

	static private int[] findSmaller(long[] arr, int start) {
		int[] smaller = new int[arr.length];
		Arrays.fill(smaller, (start == 0) ? -1 : arr.length);

		Deque<Integer> stack = new ArrayDeque<>();
		for (int i = start; i >= 0 && i < arr.length; i += (start == 0) ? 1 : -1) {
			while (stack.size() > 0 && arr[i] <= arr[stack.peek()]) {
				stack.pop();
			}
			if (stack.size() > 0) {
				smaller[i] = stack.peek();
			}
			stack.push(i);
		}
		return smaller;
	}

	public void testCase() {
		assertArrayEquals(new long[] { 12, 2, 1, 1 }, riddle(new long[] { 2, 6, 1, 12 }));
		assertArrayEquals(new long[] { 13, 3, 2, 1, 1, 1, 1 }, riddle(new long[] { 1, 2, 3, 5, 1, 13, 3 }));
	}
}
