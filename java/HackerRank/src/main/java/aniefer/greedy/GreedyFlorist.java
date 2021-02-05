package aniefer.greedy;

import java.util.Arrays;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/greedy-florist/problem
 */
public class GreedyFlorist extends TestCase {
	static int getMinimumCost(int k, int[] c) {
		Arrays.sort(c);

		int cost = 0;
		// most expensive to least, the multiple is how many rounds of buying there's
		// been
		for (int i = c.length - 1; i >= 0; i--) {
			int multiple = ((c.length - i - 1) / k) + 1;
			cost += (multiple * c[i]);
		}
		return cost;
	}

	public void testCase() {
		assertEquals(29, getMinimumCost(3, new int[] { 1, 3, 5, 7, 9 }));
		assertEquals(13, getMinimumCost(3, new int[] { 2, 5, 6 }));
	}
}
