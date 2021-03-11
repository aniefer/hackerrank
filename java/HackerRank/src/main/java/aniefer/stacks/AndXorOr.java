package aniefer.stacks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/and-xor-or/problem
 * 
 * S = ((M1 AND M2) XOR (M1 OR M2)) AND (M1 XOR M2)
 * Not required, but this is equivalent to S = M1 XOR M2
 */
public class AndXorOr extends TestCase {

	private static int s(int a, int b) {
		return a ^ b;
	}

	static int andXorOr(int[] a) {

		int max = 0;
		Deque<Integer> stack = new ArrayDeque<>();
		for (int i = 0; i < a.length; i++) {

			while (!stack.isEmpty() && stack.peek() >= a[i]) {
				int popped = stack.pop();
				max = Math.max(max, s(popped, a[i]));
			}

			if (!stack.isEmpty()) {
				// Top is stack is start of an interval, and a[i] is the next smallest
				max = Math.max(max, s(stack.peek(), a[i]));
			}
			stack.push(a[i]);
		}
		return max;
	}

	public void testCase() {
		assertEquals(15, andXorOr(new int[] { 9, 6, 3, 5, 2 }));
	}
	
	/*
	 * The provided test harness on hackerRank is not fast enough to pass all the test cases, so
	 * replace the default use of Scanner reading the entire line and using String.split
	 * with a custom parser to read integers from a BufferedReader
	 */
    private static int readInt(BufferedReader reader) throws IOException {
        int result = 0;
        boolean started = false;
        while(true) {
            int c = reader.read();
            if (c < '0' || c > '9') {
                if (started)
                    return result;
                continue;
            }
            
            started = true;
            result *= 10;
            result += c - (int)'0';
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int aCount = Integer.valueOf(reader.readLine());
        
        int[] a = new int[aCount];
        for (int aItr = 0; aItr < aCount; aItr++) {
            a[aItr] = readInt(reader);
        }

        int result = andXorOr(a);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}
