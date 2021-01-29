#[allow(dead_code)]
fn alternating_characters(s : &str) -> i32 {

    if s.is_empty() {
		return 0;
	}

	let mut i = s.chars();

	let mut deletes = 0;
	let mut c = i.next().unwrap();
    for n in i {
		if n == c {
			deletes += 1;
		} else {
			c = n;
		}
    }

    deletes
}

#[cfg(test)]
mod tests {
	use super::*;
	
	#[test]
	fn test_alternating_chars() {
		assert_eq!(3, alternating_characters("AAAA"));
		assert_eq!(0, alternating_characters("BABABA"));
		assert_eq!(6, alternating_characters("AAABBBAABB"));
	}
}