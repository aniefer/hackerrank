package aniefer.graphs;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/ctci-bfs-shortest-reach/problem
 */
public class ShortestReach extends TestCase {
	static final int WEIGHT = 6;
	static int[] shortestDistances(int start, int numNodes, int[] us, int[] vs) {
		Map<Integer, List<Integer>> graph = new HashMap<>();
		for (int i = 0; i < us.length; i++) {
			int u = us[i];
			int v = vs[i];
			List<Integer> uNodes = graph.getOrDefault(u, new ArrayList<>());
			List<Integer> vNodes = graph.getOrDefault(v, new ArrayList<>());

			uNodes.add(v);
			vNodes.add(u);

			graph.put(u, uNodes);
			graph.put(v, vNodes);
		}

		// Breadth first search, use -1 to separate levels of the search
		Map<Integer, Integer> distances = new HashMap<>();
		Queue<Integer> qu = new LinkedList<>();
		qu.add(start);
		qu.add(-1); // signal a new level
		distances.put(start, 0);

		int distance = 1;
		Integer current = null;
		while ((current = qu.remove()) != null) {
			if (current == -1) {
				distance++;
				if (qu.isEmpty()) {
					break;
				} else {
					qu.add(-1);
					continue;
				}
			}

			List<Integer> connected = graph.get(current);
			if (connected != null) {
				for (Integer i : connected) {
					if (!distances.containsKey(i)) {
						distances.put(i, distance);
						qu.add(i);
					}
				}
			}
		}

		int[] result = new int[numNodes - 1];
		for (int i = 1; i <= numNodes; i++) {
			if (i == start) {
				continue;
			}
			Integer d = distances.get(i);
			result[i > start ? i - 2 : i - 1] = (d != null) ? d * WEIGHT : -1;
		}
		return result;
	}

	public void testCase() {
		assertArrayEquals(new int[] { 6, 6, -1 }, shortestDistances(1, 4, new int[] { 1, 1 }, new int[] { 2, 3 }));
		assertArrayEquals(new int[] { -1, 6 }, shortestDistances(2, 3, new int[] { 2 }, new int[] { 3 }));
	}
}
