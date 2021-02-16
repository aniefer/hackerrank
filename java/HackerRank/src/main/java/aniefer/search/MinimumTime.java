package aniefer.search;

import java.util.Arrays;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/minimum-time-required/problem
 */
public class MinimumTime extends TestCase {

	static long minTime(long[] machines, long goal) {
		Arrays.sort(machines);

		long lowest = (long) Math.ceil(goal / (double) machines.length) * machines[0];
		long highest = (long) Math.ceil(goal / (double) machines.length) * machines[machines.length - 1];

		return search(lowest, highest, goal, machines);
	}

	static long search(long minDays, long maxDays, long goal, long[] machines) {
		if (minDays == maxDays)
			return minDays;
		long mid = (minDays + maxDays) / 2;
		long midProduction = produced(mid, machines, goal);

		if (goal <= midProduction) {
			// when we meet production, it may still be possible to use fewer days
			return search(minDays, mid, goal, machines);
		} else {
			return search(mid + 1, maxDays, goal, machines);
		}

	}

	static long produced(long days, long[] machines, long goal) {
		long total = 0;
		for (int i = 0; i < machines.length; i++) {
			total += days / machines[i];
			if (total > goal) {
				return total; // if we will exceed the goal, no need to keep calculating
			}
		}
		return total;
	}

	public void testCase() {
		assertEquals(6, minTime(new long[] { 2, 3 }, 5));
		assertEquals(20, minTime(new long[] { 4, 5, 6 }, 12));
	}
}
