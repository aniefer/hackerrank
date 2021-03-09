package aniefer.stacks;

import java.util.ArrayDeque;
import java.util.Deque;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/poisonous-plants/problem
 */
public class PoisonousPlants extends TestCase {

	private static class Plant {
		int poison;
		int days;

		public Plant(int poison, int days) {
			this.poison = poison;
			this.days = days;
		}
	}

	static int poisonousPlants(int[] p) {
		int maxDays = 0;

		Deque<Plant> stack = new ArrayDeque<>();
		// from Right to Left, track the total days the ith
		// plant needs to kill the plants to its right
		for (int i = p.length - 1; i >= 0; i--) {
			int daysToKill = 0;
			int poison = p[i];
			// if we have less poison than our neighbour, we kill it
			while (!stack.isEmpty() && poison < stack.peek().poison) {
				daysToKill = Math.max(daysToKill + 1, stack.pop().days);
			}
			stack.push(new Plant(poison, daysToKill));

			maxDays = Math.max(maxDays, daysToKill);
		}
		return maxDays;
	}

	public void testCase() {
		assertEquals(2, poisonousPlants(new int[] { 6, 5, 8, 4, 7, 10, 9 }));
	}
}
