package aniefer.greedy;

import java.util.stream.IntStream;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/equal/problem
 * 
 * Under dynamic section, but solved greedily.
 * See https://en.wikipedia.org/wiki/Change-making_problem#Greedy_method, other denominations of
 * steps might not work greedily.
 * 
 * Adding to everyone except one is equivalent to subtracting from one.
 * So reduce everyone to the target which will be in the range [min(arr), min(arr) - 5)
 * because 5 is our biggest denomination. (Getting from 4 -> 0 is (-2, -2), but 4-> -1 is (-5))
 */
public class EqualChocolate extends TestCase {

	static int equal(int[] arr) {
		int minValue = IntStream.of(arr).min().getAsInt();

		int minOps = Integer.MAX_VALUE;
		for (int i = 0; i < 5; i++) {
			int target = minValue - i;
			minOps = Math.min(minOps, IntStream.of(arr).reduce(0, (ops, a) -> ops + requiredOperations(a, target)));
		}
		return minOps;
	}

	/**
	 * Required number of operations to get from value to target using steps of 5,
	 * 2, or 1
	 * 
	 * @param value
	 * @param target
	 * @return
	 */
	static int requiredOperations(int value, int target) {
		if (value <= target) {
			return 0; // already there (or can't get there, shouldn't happen)
		}
		int n = value - target;
		int modFive = n % 5;
		return n / 5 + modFive / 2 + modFive % 2;
	}

	public void testCase() {
		assertEquals(2, equal(new int[] { 2, 2, 3, 7 }));
	}
}
