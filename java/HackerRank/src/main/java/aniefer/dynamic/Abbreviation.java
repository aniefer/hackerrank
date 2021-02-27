package aniefer.dynamic;

import junit.framework.TestCase;

/*
 * https://www.hackerrank.com/challenges/abbr/problem
 */
public class Abbreviation extends TestCase {

	static String abbreviation(String a, String b) {
		if (b.length() > a.length()) {
			return "NO";
		}

		boolean[] current = new boolean[a.length() + 1];
		boolean[] previous = new boolean[a.length() + 1];

		for (int j = 0; j <= b.length(); j++) {
			current[0] = (j == 0); // Empty A is abbreviatable if B is empty
			
			for (int i = 1; i <= a.length(); i++) {
				char cA = a.charAt(i - 1);
				if (j == 0) {
					// A is abbreviatable to empty B if it is lower case
					current[i] = Character.isLowerCase(cA);
				} else {
					char cB = b.charAt(j - 1);
					if (Character.toUpperCase(cA) == cB && previous[i - 1]) {
						// Is or can be made uppercase and matches B
						current[i] = previous[i - 1];
					} else if (Character.isLowerCase(cA)) {
						// can be deleted
						current[i] = current[i - 1];
					} else {
						// no match
						current[i] = false;
					}
				}
			}

			boolean[] t = previous;
			previous = current;
			current = t;
		}
		return previous[a.length()] ? "YES" : "NO";
	}

	
	public void testCase() {
		assertEquals("NO", abbreviation("XXVVnDEFYgYeMXzWINQYHAQKKOZEYgSRCzLZAmUYGUGILjMDET", "XXVVDEFYYMXWINQYHAQKKOZEYSRCLZAUYGUGILMDETQVWU"));
		assertEquals("YES", abbreviation("PVJSNVBDXABZYYGIGFYDICWTFUEJMDXADhqcbzva", "PVJSNVBDXABZYYGIGFYDICWTFUEJMDXAD"));
		assertEquals("YES", abbreviation("QOTLYiFECLAGIEWRQMWPSMWIOQSEBEOAuhuvo", "QOTLYFECLAGIEWRQMWPSMWIOQSEBEOA"));
		assertEquals("YES", abbreviation("DRFNLZZVHLPZWIupjwdmqafmgkg", "DRFNLZZVHLPZWI"));
		assertEquals("NO", abbreviation("SLIHGCUOXOPQYUNEPSYVDaEZKNEYZJUHFXUIL", "SLIHCUOXOPQYNPSYVDEZKEZJUHFXUIHMGFP"));
		assertEquals("YES", abbreviation("RYASPJNZEFHEORROXWZFOVDWQCFGRZLWWXJVMTLGGnscruaa", "RYASPJNZEFHEORROXWZFOVDWQCFGRZLWWXJVMTLGG"));
		assertEquals("YES", abbreviation("AVECtLVOXKPHIViTZViLKZCZAXZUZRYZDSTIHuCKNykdduywb", "AVECLVOXKPHIVTZVLKZCZAXZUZRYZDSTIHCKN"));
		assertEquals("YES", abbreviation("wZPRSZwGIMUAKONSVAUBUgSVPBWRSTJZECxMTQXXA", "ZPRSZGIMUAKONSVAUBUSVPBWRSTJZECMTQXXA"));
		assertEquals("YES", abbreviation("SYIHDDSMREKXOKRFDQAOZJQXRIDWXPYINFZCEFYyxu", "SYIHDDSMREKXOKRFDQAOZJQXRIDWXPYINFZCEFY"));
		assertEquals("YES", abbreviation("EIZGAWWDCSJBBZPBYVNKRDEWVZnSSWZIw", "EIZGAWWDCSJBBZPBYVNKRDEWVZSSWZI"));

	}
}
