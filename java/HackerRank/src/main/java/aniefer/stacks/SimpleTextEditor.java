package aniefer.stacks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Deque;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/simple-text-editor/problem
 */
public class SimpleTextEditor extends TestCase {

	public interface Command {
		public void undo();
	}

	public static void process(BufferedReader reader) throws NumberFormatException, IOException {
		// Maintain a stack of commands that can undo themselves
		Deque<Command> stack = new ArrayDeque<>();
		// And the current state of the buffer
		StringBuilder buffer = new StringBuilder();

		int q = Integer.valueOf(reader.readLine());
		for (int i = 0; i < q; i++) {
			String[] command = reader.readLine().split(" ");

			switch (command[0]) {
			case "1":
				// append W
				buffer.append(command[1]);
				final int length = command[1].length(); 
				stack.push(() -> {
					// undo by deleting the number of characters we added.
					buffer.delete(buffer.length() - length, buffer.length());
				});
				break;
			case "2":
				// delete k characters
				int k = Integer.valueOf(command[1]);
				final String removed = buffer.substring(buffer.length() - k, buffer.length());
				buffer.delete(buffer.length() - k, buffer.length());
				stack.push(() -> {
					buffer.append(removed);
				});
				break;

			case "3":
				// print
				int idx = Integer.valueOf(command[1]);
				System.out.println(buffer.charAt(idx - 1));
				break;

			case "4":
				// undo
				Command c = stack.pop();
				c.undo();
				break;
			}
		}
	}

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			process(reader);
		} catch (Exception e) {
		}
	}

	public static void process(String commands) throws Exception {
		process(new BufferedReader(new StringReader(commands)));
	}

	public void testCase() throws Exception {
		process("8\r\n" + "1 abc\r\n" + "3 3\r\n" + "2 3\r\n" + "1 xy\r\n" + "3 2\r\n" + "4 \r\n" + "4 \r\n" + "3 1");
	}
	
    private static int readInt(BufferedReader reader) throws IOException {
        int result = 0;
        while(true) {
            int c = (char)reader.read();
            if (c < '0' || c > '9') {
                return result;
            }
            result *= 10;
            result += Integer.valueOf(String.valueOf((char)c));
        }
    }
    
    public void testF() throws Exception {
        BufferedReader reader = new BufferedReader(new StringReader("76969694 71698884 32888447 31877010 65564584 87864180 7850891 1505323 17879621 15722446"));
        int aCount = readInt(reader);
        assertEquals(76969694, aCount);
    }
}
