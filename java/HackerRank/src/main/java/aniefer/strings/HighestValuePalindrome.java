package aniefer.strings;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/richie-rich/problem
 */
public class HighestValuePalindrome extends TestCase {

	static String highestValuePalindrome(String s, int n, int k) {
		char[] str = s.toCharArray();

		boolean[] changed = new boolean[(int) Math.ceil(s.length() / 2f)];
		
		// part 1, make it a palindrom
		int i = -1, j = s.length();
		while (++i < --j) {
			char a = str[i];
			char b = str[j];

			if (a != b) {
				if (k > 0) {
					// replace the lower with the higher
					str[i] = str[j] = (char) Math.max(a, b);
					k -= 1;
					changed[i] = true;
				} else {
					return "-1";
				}
			}
		}

		// part two, maximize it while we have k's left
		i = -1;
		j = s.length();
		while (++i <= --j && k > 0) {
			char a = str[i];

			if (i == j) {
				if (a < '9') {
					str[i] = '9';
					k--;
				}
			} else {
				// if we have already changed something at this index, we can pretend we 
				// had changed it to 9 so it only costs 1 more change to make the other 9
				int cost = changed[i] ? 1 : 2;
				if (a < '9' && k >= cost) {
					str[i] = str[j] = '9';
					k -= cost;
				}
			}
		}

		return String.valueOf(str);
	}

	public void testCase() {
		assertEquals("3993", highestValuePalindrome("3943", 4, 1));
		assertEquals("992299", highestValuePalindrome("092282", 6, 3));
	}
}
