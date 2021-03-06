package aniefer.strings;

import java.util.ArrayDeque;
import java.util.Arrays;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/string-function-calculation/problem
 * 
 * DFS on a Suffix tree, which can be constructed in O(n)
 *      https://en.wikipedia.org/wiki/Ukkonen%27s_algorithm
 * Or      
 * 1) Make a Suffix Array  https://en.wikipedia.org/wiki/Suffix_array
 * 		- possible in O(n) : SA-IS  https://zork.net/~st/jottings/sais.html
 *  	- naively O(n^2logn)
 *  2) LCP - Longest Common Prefix https://en.wikipedia.org/wiki/LCP_array
 *  	O(N) from suffix array with Kasai
 *  3) Variation on Area under histogram 
 *  https://stackoverflow.com/questions/4311694/maximize-the-rectangular-area-under-histogram
 */

public class StringFunctionCalculation extends TestCase {

	private static class Suffix implements Comparable<Suffix>{
		String original;
		int idx;
		
		public Suffix(String original, int i) {
			this.original = original;
			this.idx = i;
		}
		@Override
		public String toString() {
			return idx + ":" + original.substring(idx);
		}
		private int length() {
			return original.length() - idx;
		}
		@Override
		public int compareTo(Suffix o) {
			for (int i = idx, j = o.idx; i < original.length() && j < original.length(); i++, j++) {
				int diff = (original.charAt(i) - original.charAt(j));
				if (diff != 0) {
					return diff;
				}
			}
			return o.idx - idx;
		}
	}
	
	static int maxValue(String t) {
		Suffix[] suffixes = suffixArray(t);

		int[] lcp = lcp_kasai(t, suffixes);

		int max = 0;
		ArrayDeque<Integer> stack = new ArrayDeque<>();
		ArrayDeque<Integer> pos = new ArrayDeque<>();
		
		for(int i = 0; i < lcp.length; i++) {
			int posStart = lcp.length;

			while (!stack.isEmpty() && lcp[i] < stack.peek()) {
				// rectangles height common prefix
				posStart = pos.pop();
				int width = i - posStart + 1;
				max = Math.max(max, stack.pop() * width);
			}
		
			// single instance of this prefix
			max = Math.max(max, suffixes[i].length());
		
			if (stack.isEmpty() || lcp[i] > stack.peek()) {
				stack.push(lcp[i]);
				pos.push(Math.min(i, posStart));
			}
		}
		
		return max;
	}

	static private Suffix[] suffixArray(String t) {
		Suffix[] suffixes = new Suffix[t.length() + 1];
		for (int i = t.length(); i >= 0; i--) {
			suffixes[t.length() - i] = new Suffix(t, i);
		}
		Arrays.sort(suffixes);
		return suffixes;
	}

	/**
	 * Longest Common prefix between i & i + 1
	 */
	static private int[] lcp_kasai(String t, Suffix[] suffixes) {
		int[] lcp = new int[suffixes.length];

		int[] rank = new int[suffixes.length];
		for (int i = 0; i < suffixes.length; i++) {
			rank[suffixes[i].idx] = i;
		}

		int h = 0;
		for (int i = 0; i < suffixes.length; i++) {
			if (rank[i] < suffixes.length - 1) {
				int j = suffixes[rank[i] + 1].idx;
				while (i + h < t.length() && j + h < t.length() && 
						t.charAt(i + h) == t.charAt(j + h)) {
					h++;
				}
				lcp[rank[i]] = h;
				if (h > 0) {
					h--;
				}
			}
		}
		return lcp;
	}
	
	public void testCase() {
		assertEquals(12, maxValue("aaaaaa"));
		assertEquals(6, maxValue("banana"));
		assertEquals(9, maxValue("abcabcddd"));
	}
}
