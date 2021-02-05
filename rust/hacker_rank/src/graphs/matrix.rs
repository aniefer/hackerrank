/*
 * https://www.hackerrank.com/challenges/matrix/problem
 */

use std::collections::HashMap;
use std::collections::HashSet;

#[derive(Copy, Clone)]
struct Group {
	node_id: i32,
	has_machines: bool,
}

impl Group {
	fn new(node_id: i32, has_machines: bool) -> Self {
		Self { node_id, has_machines }
	}
}

#[allow(dead_code)]
pub fn min_time(roads: &mut Vec<[i32; 3]>, machines: Vec<i32>) -> i32 {
	let infected: HashSet<i32> = machines.into_iter().collect();
	roads.sort_unstable_by(|a, b| a[2].partial_cmp(&b[2]).unwrap().reverse());
	let mut time = 0;
	let mut graph: HashMap<i32, Group> = HashMap::new();
	for road in roads {
		let node1: i32 = road[0];
		let node2: i32 = road[1];
		let weight: i32 = road[2];

		let g1 = *graph.entry(node1).or_insert_with(|| Group::new(node1, infected.contains(&node1)));
		let g2 = *graph.entry(node2).or_insert_with(|| Group::new(node2, infected.contains(&node2)));

		if g1.node_id != g2.node_id {
			// not the same group
			if g1.has_machines && g2.has_machines {
				// both have machines
				time += weight;
			} else {
				// merge the groups
				for (_, node) in graph.iter_mut() {
					node.has_machines = g1.has_machines || g2.has_machines;
					if node.node_id == g2.node_id {
						node.node_id = g1.node_id;
					}
				}
			}
		}
	}
	time
}

#[cfg(test)]
mod tests {
	use super::*;
	#[test]
	fn test_case() {
		let mut roads = vec![[2, 1, 8], [1, 0, 5], [2, 4, 5], [1, 3, 4]];
		assert_eq!(10, min_time(&mut roads, vec![2, 4, 0]));

		roads = vec![[0, 1, 4], [1, 2, 3], [1, 3, 7], [0, 4, 2]];
		assert_eq!(5, min_time(&mut roads, vec![2, 3, 4]));
	}
}
