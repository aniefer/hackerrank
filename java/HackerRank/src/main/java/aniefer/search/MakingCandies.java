package aniefer.search;

import java.math.BigInteger;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/making-candies/problem
 * 
 * This problem was in the Search category, implying it should be solved 
 * by binary search on the number of passes.  
 * 
 * I'm not convinced this is better than just doing a greedy accumulation of workers/machines
 * because calculating whether a given number of passes is possible seems to be O(n), making this 
 * strategy O(nlog(n)), compared to just O(n) for the greedy approach
 */
public class MakingCandies extends TestCase {

	static long minimumPasses(long m, long w, long price, long goal) {

		long total = 0;
		long passes = 0;
		while (true) {
			BigInteger bigProduction = BigInteger.valueOf(w).multiply(BigInteger.valueOf(m));
			if (bigProduction.compareTo(BigInteger.valueOf(goal)) > 0) {
				return passes + 1;

			}
			long production = bigProduction.longValue();
			total += production;
			passes++;

			if (total >= goal) {
				return passes;
			}

			// do we have enough to buy more workers/machines?
			// and we won't meet our goal next round with current production rate
			if (price <= total && (total + production < goal)) {
				long buyItems = total / price;
				long buyMachines, buyWorkers;
				// max is when m == w
				if (m < w) {
					long diff = w - m;
					buyMachines = Math.min(buyItems, diff + ((buyItems - diff) / 2));
					buyWorkers = buyItems - buyMachines;
				} else {
					long diff = m - w;
					buyWorkers = Math.min(buyItems, diff + ((buyItems - diff) / 2));
					buyMachines = buyItems - buyWorkers;
				}

				// will this investment get us there faster?
				long newRate = (m + buyMachines) * (w + buyWorkers);

				long remainingAtCurrentRate = (long)Math.ceil((goal - total) / (double)production);
				long remainingAtNewRate = (long)Math.ceil((goal - (total - buyItems * price)) / (double) newRate);
				if (remainingAtNewRate <= remainingAtCurrentRate) {
					m += buyMachines;
					w += buyWorkers;
					total -= buyItems * price;
					continue;
				} else {
					return remainingAtCurrentRate + passes;
				}
				
			}
			// how many rounds at current production rate gets us to buying more, or the
			// goal
			long roundsToSkip = Math.min((price - total) / production, (goal - total) / production) - 1;
			if (roundsToSkip > 0) {
				passes += roundsToSkip;
				total += (roundsToSkip * production);
			}
		}
	}

	public void testCase() {
		assertEquals(3, minimumPasses(3, 1, 2, 12));
		assertEquals(16, minimumPasses(1, 1, 6, 45));
		assertEquals(1000000000000l, minimumPasses(1, 1, 1000000000000l, 1000000000000l));
		assertEquals(3584, minimumPasses(13, 2, 11216, 9767611550l));
		assertEquals(6583, minimumPasses(5, 2, 10302, 9133131738l));
		assertEquals(1, minimumPasses(123456789012l, 215987654321l, 50000000000l, 1000000000000l));
		assertEquals(10, minimumPasses(3, 13, 13, 1000000000000l));
		assertEquals(617737754l, minimumPasses(1, 100, 10000000000l, 1000000000000l));
		assertEquals(999999999999l, minimumPasses(1, 1, 499999999999l, 1000000000000l));
	}
}
