package aniefer.dynamic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/coin-change/problem
 */
public class CoinChangeProblem extends TestCase {

	private static class Amount {
		long c;
		int n;

		public Amount(int n, long c) {
			this.n = n;
			this.c = c;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Amount && ((Amount) o).c == c && ((Amount) o).n == n;
		}

		@Override
		public int hashCode() {
			return Objects.hash(c, n);
		}
	}

	static Map<Amount, Long> memo = new HashMap<>();
	public static long getWays(int n, List<Long> c) {
		if (n == 0) {
			return 1;
		}

		long denom = c.get(0);
		if (c.size() == 1) {
			return (n % denom == 0) ? 1 : 0; 
		}

		List<Long> others = c.subList(1, c.size());
		Amount a = new Amount(n, denom);
		if (memo.containsKey(a)) {
			return memo.get(a);
		}

		long ways = 0;
		for (int j = 0; j <= n; j += denom) {
			ways += getWays(n - j, others);
		}

		memo.put(a, ways);
		return ways;
	}

	public void testCase() {
		assertEquals(4, getWays(4, Arrays.asList(new Long[] { 1l, 2l, 3l })));
		assertEquals(5, getWays(10, Arrays.asList(new Long[] { 2l, 5l, 3l, 6l })));
	}
}
