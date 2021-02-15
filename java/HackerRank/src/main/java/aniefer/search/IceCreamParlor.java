package aniefer.search;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/ctci-ice-cream-parlor/problem
 */
public class IceCreamParlor extends TestCase {

	int[] whatFlavors(int[] costs, int money) {
		// cost -> flavors
		Map<Integer, List<Integer>> flavors = new HashMap<>();
		for (int i = 0; i < costs.length; i++) {
			List<Integer> f = flavors.computeIfAbsent(costs[i], c -> new ArrayList<>());
			f.add(i + 1);
		}

		for (int price : flavors.keySet()) {
			int otherPrice = money - price;
			if (flavors.containsKey(otherPrice)) {
				List<Integer> ice = flavors.get(otherPrice);
				if (price == otherPrice) {
					if (ice.size() > 1) {
						return new int[] { ice.get(0), ice.get(1) };
					}
				} else {
					int flavor = flavors.get(price).get(0);
					int otherFlavor = flavors.get(otherPrice).get(0);

					return new int[] { Math.min(flavor, otherFlavor), Math.max(flavor, otherFlavor) };
				}
			}
		}
		return null;
	}

	public void testCase() {
		assertArrayEquals(new int[] { 1, 4 }, whatFlavors(new int[] { 1, 4, 5, 3, 2 }, 4));
		assertArrayEquals(new int[] { 1, 2 }, whatFlavors(new int[] { 2, 2, 4, 3 }, 4));
		assertArrayEquals(new int[] { 1, 2 }, whatFlavors(new int[] { 2, 2, 4, 3 }, 4));
	}
}
