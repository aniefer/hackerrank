use std::cmp::PartialEq;
use std::collections::VecDeque;
use std::ops::AddAssign;

#[derive(Debug, Copy, Clone, PartialEq, PartialOrd)]
struct Pos(i32, i32);

impl AddAssign for Pos {
	#[inline(always)]
	fn add_assign(&mut self, other: Self) {
		self.0 += other.0;
		self.1 += other.1;
	}
}

impl Pos {
	fn in_bounds(&self, grid_size: &Pos) -> bool {
		self.0 >= 0 && self.1 >= 0 && self.0 < grid_size.0 && self.1 < grid_size.1
	}
	fn char_in(&self, grid: &[&str]) -> char {
		grid[self.0 as usize].as_bytes()[self.1 as usize] as char
	}
}

#[allow(dead_code)]
pub fn minimum_moves(grid: &[&str], start_x: i32, start_y: i32, goal_x: i32, goal_y: i32) -> i32 {
	let width = grid[0].len();
	let mut visited = vec![vec![false; width]; grid.len()];

	let start = Pos(start_x, start_y);
	let goal = Pos(goal_x, goal_y);
	bfs(grid, &mut visited, start, goal)
}

fn bfs(grid: &[&str], visited: &mut Vec<Vec<bool>>, start: Pos, goal: Pos) -> i32 {
	const SENTINEL: Pos = Pos(-1, -1);

	let mut queue: VecDeque<Pos> = VecDeque::new();
	let mut distance = 0;
	queue.push_back(start);
	queue.push_back(SENTINEL);
	while let Some(pos) = queue.pop_front() {
		if pos == SENTINEL {
			distance += 1;
			queue.push_back(SENTINEL);
		} else if pos == goal {
			return distance;
		} else {
			queue.append(&mut moves(&pos, 0,  1, &grid, visited));
			queue.append(&mut moves(&pos, 0, -1, &grid, visited));
			queue.append(&mut moves(&pos, 1,  0, &grid, visited));
			queue.append(&mut moves(&pos, -1, 0, &grid, visited));
		}
	}
	-1
}

fn moves(
	start: &Pos,
	dx: i32,
	dy: i32,
	grid: &[&str],
	visited: &mut Vec<Vec<bool>>,
) -> VecDeque<Pos> {
	
	let mut result: VecDeque<Pos> = VecDeque::new();
	let grid_size = Pos(grid.len() as i32, grid[start.0 as usize].len() as i32);
	let delta = Pos(dx, dy); 
	let mut pos = *start;
	loop {
		pos += delta;
		if !pos.in_bounds(&grid_size) || pos.char_in(&grid) == 'X' {
			return result;
		}
		let  is_visited = &mut visited[pos.0 as usize][pos.1 as usize];
		if !*is_visited {
			*is_visited = true;
			result.push_back(pos);
		}
	}
	//result
}

#[cfg(test)]
mod tests {
	use super::*;
	#[test]
	fn test_minimum_moves() {
		let grid = [".X.", ".X.", "..."];
		assert_eq!(3, minimum_moves(&grid, 0, 0, 0, 2));

		let grid = ["...", ".X.", ".X."];
		assert_eq!(3, minimum_moves(&grid, 2, 0, 2, 2));

		let grid = ["...", ".X.", ".X."];
		assert_eq!(2, minimum_moves(&grid, 2, 0, 0, 2));
	}
}
