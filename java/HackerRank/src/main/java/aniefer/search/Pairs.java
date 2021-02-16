package aniefer.search;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/pairs/problem
 */
public class Pairs extends TestCase {

	static int pairs(int k, int[] arr) {

		Set<Integer> set = new HashSet<>();
		IntStream.of(arr).forEach(set::add);

		int count = 0;
		for (Integer i : set) {
			if (set.contains(i + k)) {
				count++;
			}
		}
		return count;
	}
	
	public void testCase() {
		assertEquals(3, pairs(2, new int[] { 1, 5, 3, 4, 2 }));
	}
}
