#[allow(dead_code)]
fn solve_me_first(a: i32, b:i32) -> i32 {
	a + b
}

#[cfg(test)]
mod tests {
	use super::*;
	
	#[test]
	fn test_solve_me_first() {
		assert_eq!(5, solve_me_first(2, 3));
	}
}
