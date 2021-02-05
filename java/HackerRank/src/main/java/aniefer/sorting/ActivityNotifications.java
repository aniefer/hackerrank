package aniefer.sorting;

import java.util.Arrays;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/fraudulent-activity-notifications/problem
 */
public class ActivityNotifications extends TestCase {

	static int activityNotifications(int[] expenditure, int d) {
		int[] count = new int[201];
		Arrays.fill(count, 0);

		int notifications = 0;
		for (int i = 0; i < expenditure.length; i++) {
			if (i >= d) {
				float med = median(count, d);
				if (expenditure[i] >= 2 * med) {
					notifications++;
				}
			}

			count[expenditure[i]]++;
			if (i >= d) {
				count[expenditure[i - d]]--;
			}
		}
		return notifications;
	}

	private static float median(int[] counts, int days) {
		boolean odd = (days % 2 != 0);
		int target = (int) Math.ceil(days / 2f);

		int total = 0;
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] == 0) {
				continue;
			}
			total += counts[i];
			if (total > target || (odd && total == target)) {
				return i;
			} else if (total == target) {
				for (int j = i + 1; j < counts.length; j++) {
					if (j == 0) {
						continue;
					}
					return (i + j) / 2f;
				}
			}
		}
		return -1;
	}
	
	public void testCase() {
		assertEquals(2, activityNotifications(new int[] {2,3,4,2,3,6,8,4,5}, 5));
		assertEquals(1, activityNotifications(new int[] { 10, 20, 30, 40, 50 }, 3));
	}
	
}
