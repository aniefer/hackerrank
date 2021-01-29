use std::collections::HashMap;

#[allow(dead_code)]
fn make_anagram(a: &str, b: &str) -> i32 {
	let mut freq = HashMap::new();
	
	for c in a.chars() {
		*freq.entry(c).or_insert(0) += 1;
	}
	
	let mut deleted_b = 0;
	for c in b.chars() {
		match freq.get_mut(&c) {
			Some(f) =>
			 	if *f > 0 {  
					*f -= 1;
				} else {
					deleted_b += 1;
				}
			None => deleted_b += 1
		}
	}
	
	let deleted_a: i32 = freq.values().sum();
	deleted_a + deleted_b
}

#[cfg(test)]
mod tests {
	use super::*;
	#[test]
	fn test_make_anagram() {
		assert_eq!(4, make_anagram("cde", "abc"));
		assert_eq!(30, make_anagram("fcrxzwscanmligyxyvym", "jxwtrhvujlmrpdoqbisbwhmgpmeoke"));
	}
}
