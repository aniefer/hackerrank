package aniefer.graphs;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/swap-nodes-algo/problem
 */
public class SwapNodes extends TestCase {

	static class Node {
		int value;
		Node left;
		Node right;

		public Node(int v) {
			this.value = v;
		}
	}

	static int[][] swapNodes(int[][] indexes, int[] queries) {
		Map<Integer, Node> nodes = new HashMap<>();

		for (int i = 0; i < indexes.length; i++) {
			Node node = nodes.computeIfAbsent(i + 1, v -> new Node(v));
			if (indexes[i][0] != -1) {
				node.left = nodes.computeIfAbsent(indexes[i][0], v -> new Node(v));
			}
			if (indexes[i][1] != -1) {
				node.right = nodes.computeIfAbsent(indexes[i][1], v -> new Node(v));
			}
		}

		int[][] results = new int[queries.length][indexes.length];
		for (int i = 0; i < queries.length; i++) {
			List<Integer> traversal = new ArrayList<>(indexes.length);
			visit(nodes.get(1), queries[i], 1, traversal);
			results[i] = traversal.stream().mapToInt(Integer::intValue).toArray();
		}
		return results;
	}

	static private void visit(Node node, int k, int h, List<Integer> traversal) {
		if (node == null) {
			return;
		}

		if ((h % k) == 0) {
			Node t = node.left;
			node.left = node.right;
			node.right = t;
		}

		visit(node.left, k, h + 1, traversal);
		traversal.add(node.value);
		visit(node.right, k, h + 1, traversal);
	}

	public void testCase() {
		int[][] results = new int[][] { { 4, 2, 1, 5, 3 } };
		int[][] indexes = new int[][] { { 2, 3 }, { -1, 4 }, { -1, 5 }, { -1, -1 }, { -1, -1 } };

		assertArrayEquals(results, swapNodes(indexes, new int[] { 2 }));

		results = new int[][] { { 2, 9, 6, 4, 1, 3, 7, 5, 11, 8, 10 }, { 2, 6, 9, 4, 1, 3, 7, 5, 10, 8, 11 } };
		indexes = new int[][] { { 2, 3 }, { 4, -1 }, { 5, -1 }, { 6, -1 }, { 7, 8 }, { -1, 9 }, { -1, -1 }, { 10, 11 },
				{ -1, -1 }, { -1, -1 }, { -1, -1 } };

		assertArrayEquals(results, swapNodes(indexes, new int[] { 2, 4 }));
	}
}
