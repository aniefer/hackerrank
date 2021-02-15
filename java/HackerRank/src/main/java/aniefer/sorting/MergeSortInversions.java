package aniefer.sorting;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/ctci-merge-sort/problem
 */
public class MergeSortInversions extends TestCase {

	long countInversions(int[] arr) {
		return sort(0, arr.length, arr);
	}

	private long sort(int start, int end, int[] arr) {
		if (start == end - 1) {
			return 0;
		}

		int middle = ((end - start) / 2) + start;

		long left = sort(start, middle, arr);
		long right = sort(middle, end, arr);
		long inversions = merge(start, middle, end, arr);
		return left + right + inversions;
	}

	private long merge(int start, int mid, int end, int[] arr) {
		int[] sorted = new int[end - start];

		long inversions = 0;
		int i = start;
		int j = mid;
		int idx = 0;
		while (i < mid || j < end) {
			if (i >= mid) {
				sorted[idx++] = arr[j++];
			} else if (j >= end) {
				sorted[idx++] = arr[i++];
			} else if (arr[i] <= arr[j]) {
				sorted[idx++] = arr[i++];
			} else {
				sorted[idx++] = arr[j++];
				inversions += mid - i;
			}
		}
		System.arraycopy(sorted, 0, arr, start, end - start);
		return inversions;
	}

	public void testCase() {
		assertEquals(0, countInversions(new int[] { 1, 1, 1, 2, 2 }));
		assertEquals(4, countInversions(new int[] { 2, 1, 3, 1, 2 }));
	}
}
