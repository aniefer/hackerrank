package aniefer.strings;

import java.util.stream.IntStream;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/bear-and-steady-gene/problem
 */
public class BearAndSteadyGene extends TestCase {

	static int toGene(char c) {
		switch (c) {
			case 'A': return 0;
			case 'C': return 1;
			case 'T': return 2;
			case 'G': return 3;
		}
		throw new IllegalArgumentException();
	}

	static int steadyGene(String gene) {
		int n = gene.length();
		int[] geneFreq = new int[] { 0, 0, 0, 0 };
		
		// first get the frequency of the letters in the gene
		for (int i = 0; i < n; i++) {
			geneFreq[toGene(gene.charAt(i))]++;
		}

		if (isSteady(geneFreq, n)) {
			return 0;
		}

		int minSize = Integer.MAX_VALUE;
		int windowStart = 0, windowEnd = 0;
		int[] windowFreq = new int[] { 0, 0, 0, 0 };
		while (windowEnd < n) {
			
			// slide the end forward until we have a steady substitution
			char c = gene.charAt(windowEnd++);
			windowFreq[toGene(c)]++;

			if (isSteadyAfterSub(geneFreq, windowFreq, n)) {
				// move the start forward while the gene remains steady
				while (windowStart < windowEnd) {
					char removed = gene.charAt(windowStart++);
					windowFreq[toGene(removed)]--;
					if (!isSteadyAfterSub(geneFreq, windowFreq, n)) {
						windowFreq[toGene(removed)]++;
						windowStart--;
						break;
					}
				}
				
				minSize = Math.min(minSize, windowEnd - windowStart);
			}
		}
		return minSize;
	}

	static boolean isSteadyAfterSub(int[] gene, int[] sub, int n) {
		int subs = IntStream.of(sub).reduce(0, (a, b) -> a + b);
		int steadySize = n / 4;
		for (int i = 0; i < gene.length; i++) {
			int f = gene[i] - sub[i];
			if (f > steadySize) {
				return false;
			}
			if (f + subs >= steadySize) {
				subs -= steadySize - f;
			} else {
				return false;
			}
		}
		return true;
	}

	static boolean isSteady(int[] freq, int n) {
		int steadySize = n / 4;
		return IntStream.of(freq).allMatch(i -> i == steadySize);
	}
	
	public void testCase() {
		assertEquals(5, steadyGene("GAAATAAA"));
		assertEquals(5, steadyGene("TGATGCCGTCCCCTCAACTTGAGTGCTCCTAATGCGTTGC"));
	}
}
