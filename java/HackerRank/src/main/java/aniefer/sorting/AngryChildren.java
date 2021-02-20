package aniefer.sorting;

import java.util.Arrays;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/angry-children/problem
 */
public class AngryChildren extends TestCase {

	static int maxMin(int k, int[] arr) {
		Arrays.sort(arr);

		int minUnfair = Integer.MAX_VALUE;

		for (int i = 0; (i + k - 1) < arr.length; i++) {
			int unFair = arr[i + k - 1] /* max */ - arr[i] /* min */;
			minUnfair = Math.min(unFair, minUnfair);
		}
		return minUnfair;
	}

	public void testCase() {
		assertEquals(20, maxMin(3, new int[] { 10, 100, 300, 200, 1000, 20, 30 }));
		assertEquals(3, maxMin(4, new int[] { 1, 2, 3, 4, 10, 20, 30, 40, 100, 200 }));
		assertEquals(2, maxMin(3, new int[] { 100, 200, 300, 350, 400, 401, 402 }));
	}
}
