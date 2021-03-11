package aniefer.stacks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.IntStream;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/waiter/problem
 */
public class Waiter extends TestCase {

	// Sieve of Eratosthenes
	static private List<Integer> getPrimes(int numPrimes) {
		SortedSet<Integer> candidates = new TreeSet<>();
		IntStream.iterate(3, i -> i + 2).limit(5000).forEach(candidates::add);
		List<Integer> result = new ArrayList<>(numPrimes);
		result.add(2);
		while (!candidates.isEmpty() && result.size() < numPrimes) {
			Integer p = candidates.first();
			candidates.remove(p);
			result.add(p);
			for (int i = p * p; i <= candidates.last(); i += p) {
				candidates.remove(i);
			}
		}
		return result;
	}

	/*
	 * Complete the waiter function below.
	 */
	static List<Integer> waiter(int[] number, int q) {
		List<Integer> primes = getPrimes(q);

		List<Integer> answers = new ArrayList<>();

		Deque<Integer> stack = new ArrayDeque<>();
		IntStream.of(number).forEach(stack::push);

		Deque<Integer> A = new ArrayDeque<>();
		Deque<Integer> B = new ArrayDeque<>();

		for (int p : primes) {
			while (!stack.isEmpty()) {
				int n = stack.pop();
				if (n % p == 0) {
					// divisible by the prime
					B.push(n);
				} else {
					A.push(n);
				}
			}
			// store the values from B in the answers
			while (!B.isEmpty()) {
				answers.add(B.pop());
			}

			Deque<Integer> t = stack;
			stack = A;
			A = t;
		}
		while (!stack.isEmpty()) {
			answers.add(stack.pop());
		}
		return answers;
	}

	public void testCase() {
		assertEquals(Arrays.asList(4, 6, 3, 7, 5), waiter(new int[] { 3, 4, 7, 6, 5 }, 1));
		assertEquals(Arrays.asList(4, 4, 9, 3, 3), waiter(new int[] { 3, 3, 4, 4, 9 }, 2));
	}
}
