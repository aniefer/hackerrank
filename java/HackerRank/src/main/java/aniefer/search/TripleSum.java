package aniefer.search;

import java.util.Arrays;

import junit.framework.TestCase;

public class TripleSum extends TestCase {

	static long triplets(int[] a, int[] b, int[] c) {
		a = Arrays.stream(a).distinct().sorted().toArray();
		b = Arrays.stream(b).distinct().sorted().toArray();
		c = Arrays.stream(c).distinct().sorted().toArray();

		long count = 0;
		for (int i = 0; i < b.length; i++) {
			long a_idx = binarySearch(0, a.length, b[i], a);
			long c_idx = binarySearch(0, c.length, b[i], c);
			count += a_idx * c_idx;
		}
		return count;
	}

	// return the number of elements <= the given x
	static int binarySearch(int start, int end, int x, int[] toSearch) {
		int middle = ((end - start) / 2) + start;

		if (x < toSearch[middle]) {
			return (middle > start) ? binarySearch(start, middle, x, toSearch) : start;
		} else if (x > toSearch[middle]) {
			return (middle < (end - 1)) ? binarySearch(middle, end, x, toSearch) : end;
		} else {
			return middle + 1;
		}
	}

	public void testSearch() {
		int a[] = new int[] { 1, 3, 5 };
		assertEquals(0, TripleSum.binarySearch(0, 3, 0, a));
		assertEquals(1, TripleSum.binarySearch(0, 3, 1, a));
		assertEquals(1, TripleSum.binarySearch(0, 3, 2, a));
		assertEquals(2, TripleSum.binarySearch(0, 3, 3, a));
		assertEquals(2, TripleSum.binarySearch(0, 3, 4, a));
		assertEquals(3, TripleSum.binarySearch(0, 3, 5, a));
		assertEquals(3, TripleSum.binarySearch(0, 3, 6, a));
	}

	public void testCase() {
		assertEquals(8, triplets(new int[] { 1, 3, 5 }, new int[] { 2, 3 }, new int[] { 1, 2, 3 }));
		assertEquals(5, triplets(new int[] { 1, 4, 5 }, new int[] { 2, 3, 3 }, new int[] { 1, 2, 3 }));
		assertEquals(12, triplets(new int[] { 1, 3, 5, 7 }, new int[] { 5, 7, 9 }, new int[] { 7, 9, 11, 13 }));
	}
}
