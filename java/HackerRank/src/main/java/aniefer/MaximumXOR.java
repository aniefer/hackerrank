package aniefer;

import static org.junit.Assert.assertArrayEquals;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/maximum-xor/problem
 */
public class MaximumXOR extends TestCase {

	public static class Node {
		public int value;
		public Node[] child = new Node[2];
	}

	static int[] maxXor(int[] arr, int[] queries) {
		// build a tree according to the bits of the input numbers
		// left for 0s, right for 1s.  Put the value at the leaf node.
		
		Node root = new Node();
		for (int i : arr) {
			insert(i, root);
		}

		// The max xor is the path that is the most opposite
		// of the bits of the query number
		int[] results = new int[queries.length];
		for (int i = 0; i < queries.length; i++) {
			results[i] = query(queries[i], root);
		}
		return results;
	}

	private static int query(int value, Node node) {
		for (int i = 31; i >= 0; i--) {
			int childIdx = (((1 << i) & value) != 0) ? 0 : 1; // opposite
			if (node.child[childIdx] != null) {
				node = node.child[childIdx];
			} else {
				// nothing in that direction, have to go the other way
				node = node.child[(childIdx + 1) % 2];
			}
		}
		return node.value ^ value;
	}

	private static void insert(int value, Node node) {
		for (int i = 31; i >= 0; i--) {
			int childIdx = (((1 << i) & value) != 0) ? 1 : 0;
			if (node.child[childIdx] == null) {
				node.child[childIdx] = new Node();
			}
			node = node.child[childIdx];
		}
		node.value = value;
	}

	public void testCase() {
		assertArrayEquals(new int[] { 3, 7, 3 }, maxXor(new int[] { 0, 1, 2 }, new int[] { 3, 7, 2 }));
		assertArrayEquals(new int[] { 7, 7 }, maxXor(new int[] { 5, 1, 7, 4, 3 }, new int[] { 2, 0 }));
		assertArrayEquals(new int[] { 22, 7 }, maxXor(new int[] { 1, 3, 5, 7 }, new int[] { 17, 6 }));
	}
}
