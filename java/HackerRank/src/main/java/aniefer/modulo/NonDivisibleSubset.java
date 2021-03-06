package aniefer.modulo;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/non-divisible-subset/problem
 */
public class NonDivisibleSubset extends TestCase {

	public static int nonDivisibleSubset(int k, List<Integer> s) {
		int[] mod = new int[k]; // k <= 100

		// find the remainder after dividing each number by k
		s.forEach(i -> mod[i % k]++);

		// special case, can only take 1 number divisible by k
		int result = (mod[0] > 0) ? 1 : 0;

		for (int i = 1; i < k / 2; i++) {
			// numbers with remainder i + numbers remainder (k - i) will be divisible by k
			// take the larger of the two sets, and none of the other
			result += Math.max(mod[i], mod[k - i]);
		}

		if (k > 1) {
			if (k % 2 == 0) {
				// if k is even, k / 2 is a special case, only take one of those 
				result += (mod[k % 2] > 0) ? 1 : 0;
			} else {
				result += Math.max(mod[k / 2], mod[k - k / 2]);
			}
		}

		return result;
	}

	public void testCase() {
		assertEquals(3, nonDivisibleSubset(3, Arrays.asList(1, 7, 2, 4)));
		assertEquals(1, nonDivisibleSubset(1, Arrays.asList(1, 2, 3, 4, 5)));
	}
}
