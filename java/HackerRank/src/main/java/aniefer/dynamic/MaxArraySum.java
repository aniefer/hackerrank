package aniefer.dynamic;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/max-array-sum/problem
 * 
 * A variation on Max sub array, similar to Kadane's algorithm
 */
public class MaxArraySum extends TestCase {

	static int maxSubsetSum(int[] arr) {
		if (arr.length == 1) {
			return Math.max(0, arr[0]);
		}

		// Max(i) = max { max(0, arr[i]) 		current value
		// 				{ max(i - 2) + arr[i]	2 back + current
		// 				{ max(i-1) 				max value so far

		// need access up to i - 2, we'll use % 3 to index into mv
		int[] mv = new int[3]; 
		mv[0] = Math.max(0, arr[0]);
		mv[1] = Math.max(mv[0], Math.max(0, arr[1]));

		for (int i = 2; i < arr.length; i++) {
			int maxSum = Math.max(0, arr[i]);
			maxSum = Math.max(maxSum, mv[(i - 2) % 3] + arr[i]);
			mv[i % 3] = Math.max(mv[(i - 1) % 3], maxSum);
		}
		return mv[(arr.length - 1) % 3];
	}

	public void testCase() {
		assertEquals(11, maxSubsetSum(new int[] { 2, 1, 5, 8, 4 }));
		assertEquals(15, maxSubsetSum(new int[] { 5, -7, 8, 10 }));
	}

}
