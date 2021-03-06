package aniefer.modulo;

import junit.framework.TestCase;

public class RepeatedString extends TestCase {
	static long repeatedString(String s, long n) {

		// the number of full copies of s that will be needed
		long repeats = n / s.length();

		// the number of characters of s needed at the end to make up the remainder
		long remainder = n % s.length();

		int numAs = 0;
		int numRemainder = 0;
		for (int i = 0; i < s.length(); i++) {
			if (i == remainder) {
				numRemainder = numAs;
			}

			if (s.charAt(i) == 'a') {
				numAs++;
			}
		}

		return (repeats * numAs) + numRemainder;
	}
	
	public void testCase() {
		assertEquals(7, repeatedString("aba", 10));
		assertEquals(1000000000000l, repeatedString("a", 1000000000000l));
		assertEquals(3, repeatedString("abacadefkgashlksj", 6));
	}

}
