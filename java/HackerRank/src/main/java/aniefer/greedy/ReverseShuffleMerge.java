package aniefer.greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/reverse-shuffle-merge/problem
 * 
 * Note
 * s = merge(reverse(A), shuffle(A)) <==> reverse(s) = merge(A, shuffle(A))
 * 
 * A is half length of S, and it has half of each character frequency
 */
@SuppressWarnings("unchecked")
public class ReverseShuffleMerge extends TestCase {
	static String reverseShuffleMerge(String s) {
		HashMap<Character, Integer> shuffleFreq = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			shuffleFreq.put(c, shuffleFreq.getOrDefault(c, 0) + 1);
		}

		shuffleFreq.replaceAll((k, v) -> v / 2);
		Map<Character, Integer> reverseFreq = (Map<Character, Integer>) shuffleFreq.clone();
		
		List<Character> result = new ArrayList<>();
		for(int i = s.length() - 1; i >= 0; i--) {
			char c = s.charAt(i);
			
			if (reverseFreq.get(c) > 0) {
				// put it in the reverse string, already there that is bigger, should be moved to shuffle instead
				// if possible
				ListIterator<Character> iterator = result.listIterator(result.size());
				while(iterator.hasPrevious()) {
					char p = iterator.previous();
					if (p > c && shuffleFreq.get(p) > 0) {
						iterator.remove();
						reverseFreq.put(p, reverseFreq.get(p) + 1);
						shuffleFreq.put(p, shuffleFreq.get(p) - 1);
						continue;
					}
					break;
				}

				result.add(c);
				reverseFreq.put(c, reverseFreq.get(c) - 1);
			} else {
				// this char is from the shuffled string
				shuffleFreq.put(c, shuffleFreq.get(c) - 1);
			}
		}
		return result.stream().map(String::valueOf).collect(Collectors.joining());
	}

	public void testCase() {
		assertEquals("egg", reverseShuffleMerge("eggegg"));
		assertEquals("agfedcb", reverseShuffleMerge("abcdefgabcdefg"));
		assertEquals("aeiou", reverseShuffleMerge("aeiouuoiea"));
		assertEquals("aaaaabccigicgjihidfiejfijgidgbhhehgfhjgiibggjddjjd", reverseShuffleMerge("djjcddjggbiigjhfghehhbgdigjicafgjcehhfgifadihiajgciagicdahcbajjbhifjiaajigdgdfhdiijjgaiejgegbbiigida"));
	}
}
