package aniefer.graphs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/matrix/problem
 */
public class Matrix extends TestCase {

	@SuppressWarnings({ "serial", "unused" })
	private static class Group extends HashSet<Integer> {
		private final int node; // helps debugging
		private boolean hasMachines;

		public Group(int node, boolean hasMachines) {
			this.node = node;
			this.hasMachines = hasMachines;
			add(node);
		}

		public void merge(Group group) {
			this.hasMachines |= group.hasMachines;
			addAll(group);
		}

		public boolean hasMachines() {
			return hasMachines;
		}
	}

	static int minTime(int[][] roads, int[] machines) {
		Set<Integer> infected = new HashSet<>();
		IntStream.of(machines).forEach(infected::add);

		// Sort the roads descending weight so that we 
		// connect groups with expensive roads first, later
		// we cut cheap ones when both groups have machines
		Arrays.sort(roads, (int[] r1, int[] r2) -> {
			return -1 * (r1[2] - r2[2]);
		});

		int time = 0;
		// connected components via find/merge
		Map<Integer, Group> graph = new HashMap<>();
		for (int i = 0; i < roads.length; i++) {
			int node1 = roads[i][0];
			int node2 = roads[i][1];
			int weight = roads[i][2];

			Group g1 = graph.computeIfAbsent(node1, id -> new Group(id, infected.contains(id)));
			Group g2 = graph.computeIfAbsent(node2, id -> new Group(id, infected.contains(id)));
			
			if (g1 != g2) {
				if (g1.hasMachines() && g2.hasMachines()) {
					// both are infected, this road must be cut
					time += weight;
				} else {
					g1.merge(g2);
					g2.forEach(n -> graph.put(n, g1));
				}
			}
		}
		return time;
	}

	public void testCase() {
		int[][] roads = new int[][] { { 2, 1, 8 }, { 1, 0, 5 }, { 2, 4, 5 }, { 1, 3, 4 } };
		assertEquals(10, minTime(roads, new int[] { 2, 4, 0 }));

		roads = new int[][] { { 0, 1, 4 }, { 1, 2, 3 }, { 1, 3, 7 }, { 0, 4, 2 } };
		assertEquals(5, minTime(roads, new int[] { 2, 3, 4 }));
	}
}
