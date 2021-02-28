package aniefer.recursion;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/recursive-digit-sum/problem
 */
public class RecursiveDigitSum extends TestCase {

	static int superDigit(String n, int k) {
		if (n.length() == 1) {
			return Integer.valueOf(n);
		}

		// sum the digits of n
		long sum = 0;
		for (int i = 0; i < n.length(); i++) {
			sum += Integer.valueOf(n.substring(i, i + 1));
		}
		sum = sum * k;
		
		return superDigit(String.valueOf(sum), 1);
	}

	public void testCase() {
		assertEquals(3, superDigit("148", 3));
		assertEquals(9, superDigit("123", 3));
	}
}
