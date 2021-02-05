package aniefer.graphs;

import static org.junit.Assert.assertArrayEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import junit.framework.TestCase;

// https://www.hackerrank.com/challenges/friend-circle-queries/problem
public class FriendCircle extends TestCase {

	static int[] maxCircle(int[][] queries) {
		// disjoint sets, and sizes of those sets
		Map<Integer, Integer> parents = new HashMap<>();
		Map<Integer, Integer> sizes = new HashMap<>();

		int maxSize = 0;
		int[] results = new int[queries.length];
		for (int i = 0; i < queries.length; i++) {
			int[] query = queries[i];

			int group1 = root(query[0], parents);
			int group2 = root(query[1], parents);

			int size = join(group1, group2, parents, sizes);
			maxSize = Math.max(maxSize, size);
			results[i] = maxSize;
		}
		return results;
	}

	private static int root(int i, Map<Integer, Integer> parents) {
		int parent = parents.computeIfAbsent(i, Function.identity());
		while (parent != i) {
			int grandParent = parents.get(parent);
			if (parent != grandParent) {
				// do some compression
				parents.put(i, grandParent);
			}
			i = parent;
			parent = grandParent;
		}
		return i;
	}

	private static int join(int group1, int group2, Map<Integer, Integer> parents, Map<Integer, Integer> sizes) {
		if (group1 == group2) {
			return sizes.get(group1);
		}

		int s1 = sizes.computeIfAbsent(group1, k -> 1);
		int s2 = sizes.computeIfAbsent(group2, k -> 1);

		int bigger = s1 > s2 ? group1 : group2;
		int smaller = s1 <= s2 ? group1 : group2;

		parents.put(smaller, bigger);
		sizes.put(bigger, s1 + s2);
		sizes.remove(smaller);
		return s1 + s2;
	}

	public void testCase() {
		assertArrayEquals(new int[] { 2, 3 }, maxCircle(new int[][] { { 1, 2 }, { 1, 3 } }));
		assertArrayEquals(new int[] { 2, 2, 2, 4 },
				maxCircle(new int[][] { { 1000000000, 23 }, { 11, 3778 }, { 7, 47 }, { 11, 1000000000 } }));

		assertArrayEquals(new int[] { 2, 2, 3, 3, 6, 6, 8, 8 }, maxCircle(
				new int[][] { { 6, 4 }, { 5, 9 }, { 8, 5 }, { 4, 1 }, { 1, 5 }, { 7, 2 }, { 4, 2 }, { 7, 6 } }));
	}
}
