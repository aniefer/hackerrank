package aniefer.dynamic;

import java.util.Arrays;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/common-child/problem
 * 
 * This is the Longest Common subsequence problem
 */
public class CommonChild extends TestCase {
	static int commonChild(String s1, String s2) {
		int[] current = new int[s1.length() + 1];
		int[] previous = new int[s1.length() + 1];

		// LCS(Xi, Yj) = { 0 								  if i = 0 or j = 0
		// 				 { LCS(Xi-1, Yj-1) + xi 			  if xi = yj
		// 				 { max[ LCS(Xi-1, Yj), LCS(Xi, Yj-1)] if xi != yj

		Arrays.fill(previous, 0); // j = 0
		for (int j = 1; j <= s2.length(); j++) {
			current[0] = 0; // i = 0
			for (int i = 1; i <= s1.length(); i++) {
				char x = s1.charAt(i - 1);
				char y = s2.charAt(j - 1);
				if (x == y) {
					current[i] = previous[i - 1] + 1;
				} else {
					current[i] = Math.max(current[i - 1], previous[i]);
				}
			}

			int[] temp = previous;
			previous = current;
			current = temp;
		}
		return previous[s1.length()];
	}
	
	public void testCase() {
		assertEquals(2, commonChild("HARRY", "SALLY"));
		assertEquals(3, commonChild("SHINCHAN", "NOHARAAA"));
		assertEquals(2, commonChild("OUDFRMYMAW", "AWHYFCCMQX"));
	}
}
